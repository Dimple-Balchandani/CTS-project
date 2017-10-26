package com.vs.ConsignmentTrackingSystem.services.impl;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vs.ConsignmentTrackingSystem.db.entities.CategoryEntity;
import com.vs.ConsignmentTrackingSystem.db.entities.CategoryEntityDAO;
import com.vs.ConsignmentTrackingSystem.db.entities.JobEntity;
import com.vs.ConsignmentTrackingSystem.db.entities.JobEntityDAO;
import com.vs.ConsignmentTrackingSystem.db.entities.TaskEntity;
import com.vs.ConsignmentTrackingSystem.db.entities.TaskEntityDAO;
import com.vs.ConsignmentTrackingSystem.db.entities.UserEntityDAO;
import com.vs.ConsignmentTrackingSystem.db.entities.UserJobTaskEntity;
import com.vs.ConsignmentTrackingSystem.db.entities.UserJobTaskEntityDAO;
import com.vs.ConsignmentTrackingSystem.models.Request.AddRemarkRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.CloseTaskRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.EditTaskDetailsRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.EditTaskRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.StartTaskRequest;
import com.vs.ConsignmentTrackingSystem.models.Response.GetResponse;
import com.vs.ConsignmentTrackingSystem.models.Response.TaskResponse;
import com.vs.ConsignmentTrackingSystem.services.TaskDetailsService;
import com.vs.ConsignmentTrackingSystem.utilities.Constants;
import java.util.logging.Logger;

@Component
public class TaskDetailsServiceImpl implements TaskDetailsService {

	private final static Logger LOGGER = Logger.getLogger(TaskDetailsServiceImpl.class.getName());

	@Autowired
	private UserEntityDAO userEntityDAO;

	@Autowired
	private TaskEntityDAO taskEntityDAO;

	@Autowired
	private JobEntityDAO jobEntityDAO;

	@Autowired
	private UserJobTaskEntityDAO userJobTaskEntityDAO;

	@Autowired
	private CategoryEntityDAO categoryEntityDAO;

	public final static long MILLISECONDS_IN_DAY = 24 * 60 * 60 * 1000;

	public static int DifferenceInDays(Date from, Date to) {
		return (int)((to.getTime() - from.getTime()) / MILLISECONDS_IN_DAY);
	}


	public GetResponse getTasks() {
		List<TaskEntity> tasks = taskEntityDAO.getTasks();
		GetResponse getAllTasksResponse = new GetResponse();
		getAllTasksResponse.setData(tasks);
		getAllTasksResponse.setMessage(Constants.SUCCESS_RESPONSE);
		return getAllTasksResponse;
	}

	public TaskResponse startTask(StartTaskRequest startTaskRequest) {
		TaskResponse taskResponse = new TaskResponse();
		JobEntity jobEntity = jobEntityDAO.findByJobNo(startTaskRequest.getJobNo());
		jobEntity.setJobStatus("ACTIVE");
		jobEntityDAO.update(jobEntity);
		if (userJobTaskEntityDAO.updateOnStart(startTaskRequest)) {
			taskResponse.setMessage(Constants.SUCCESS_RESPONSE);
		} else {
			taskResponse.setMessage(Constants.FAILURE_RESONSE);
		}
		return taskResponse;

	}

	public TaskResponse closeTask(CloseTaskRequest closeTaskRequest) {

		TaskResponse taskResponse = new TaskResponse();
		JobEntity jobEntity = jobEntityDAO.findByJobNo(closeTaskRequest.getJobNo());

		UserJobTaskEntity userJobTaskEntity = userJobTaskEntityDAO.getDetailsByJobNoAndTaskId(closeTaskRequest.getJobNo(),closeTaskRequest.getTaskId());

		if (userJobTaskEntity != null) {

			userJobTaskEntity.setStatus("CLOSE");
			userJobTaskEntity.setEndDate(closeTaskRequest.getEndDate());
			userJobTaskEntity.setTaskDate(closeTaskRequest.getTaskDate());
			userJobTaskEntity.setTaskText(closeTaskRequest.getTaskText());
			userJobTaskEntityDAO.update(userJobTaskEntity);

			CategoryEntity categories = categoryEntityDAO.findById(userJobTaskEntity.getCategoryId());
			String taskIdList = categories.getTaskIdList();

			long nextTaskId = 0;

			String[] taskIdArray = taskIdList.split(","); 
			String id = Long.toString(closeTaskRequest.getTaskId());

			for(int i = 0; i < taskIdArray.length ; i++) {

				if(taskIdArray[i].equals(id)) {
					i = i + 1;
					nextTaskId = Long.parseLong(taskIdArray[i]);
					break;
				}
			}

			userJobTaskEntity = userJobTaskEntityDAO.getDetailsByJobNoAndTaskId(closeTaskRequest.getJobNo(),nextTaskId);

			if(userJobTaskEntity == null)
			{
				jobEntity.setJobStatus("COMPLETED");
				jobEntityDAO.update(jobEntity);
				taskResponse.setMessage(Constants.SUCCESS_RESPONSE);
				return taskResponse;
			}
			else {

				userJobTaskEntity.setStartDate(closeTaskRequest.getEndDate());

				Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
				Date expectedDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

				if(userJobTaskEntity.getStatus().equals("NEW")) {
					userJobTaskEntity.setStatus("ACTIVE");
				}
				else if(userJobTaskEntity.getStatus().equals("NEW,PENDING")) {
					userJobTaskEntity.setStatus("PENDING");
				}

				int taskId = (int) userJobTaskEntity.getTaskId(); 

				if(userJobTaskEntity.getTaskPendingFrom() == 0) {
					//Shipping Bill Number & Shipping Bill Date
					expectedDate = userJobTaskEntity.getAssignedDate();
				}
				else if(taskId == 104)
				{
					//Docs to Customs
					expectedDate = userJobTaskEntityDAO.calculateExpectedDate(closeTaskRequest.getTaskDate(), 2);
				}
				else {
					UserJobTaskEntity updateJobTaskEntity = userJobTaskEntityDAO.getDetailsByJobNoAndTaskId(closeTaskRequest.getJobNo(),userJobTaskEntity.getTaskPendingFrom());
					if(updateJobTaskEntity !=null)
						expectedDate = userJobTaskEntityDAO.calculateExpectedDate(updateJobTaskEntity.getTaskDate(), userJobTaskEntity.getDuration());
				}

				userJobTaskEntity.setExpectedDate(expectedDate);
				if(DifferenceInDays(currentDate,expectedDate) < 0)
				{
					userJobTaskEntity.setStatus("PENDING");
					jobEntity.setJobStatus("PENDING");
				}
				else {
					jobEntity.setJobStatus("ACTIVE");
				}

				jobEntityDAO.update(jobEntity);
				userJobTaskEntityDAO.update(userJobTaskEntity);

				taskResponse.setMessage(Constants.SUCCESS_RESPONSE);
				return taskResponse;
			}
		}
		taskResponse.setMessage(Constants.FAILURE_RESONSE);
		return taskResponse;
	}

	public GetResponse getTaskByUser(String type, long userId) {
		List<UserJobTaskEntity> list = userJobTaskEntityDAO.getTaskByUser(type, userId);
		GetResponse getAllTasksResponse = new GetResponse();
		getAllTasksResponse.setData(list);
		getAllTasksResponse.setMessage(Constants.SUCCESS_RESPONSE);
		return getAllTasksResponse;
	}

	public TaskResponse completeTask(int jobNo) {

		userJobTaskEntityDAO.completeTask(jobNo);

		TaskResponse taskResponse = new TaskResponse();
		taskResponse.setMessage(Constants.SUCCESS_RESPONSE);
		taskResponse.setData(null);
		return taskResponse;
	}

	public TaskResponse addRemark(AddRemarkRequest addRemarkRequest) {
		userJobTaskEntityDAO.findByJobNoAndTaskId(addRemarkRequest);
		TaskResponse taskResponse = new TaskResponse();
		taskResponse.setMessage(Constants.SUCCESS_RESPONSE);
		taskResponse.setData(null);
		return taskResponse;

	}


	public TaskResponse editTask(EditTaskRequest editTaskRequest) {
		UserJobTaskEntity entity = userJobTaskEntityDAO.findByJobAndTask(editTaskRequest.getJobNo(),editTaskRequest.getTaskId());
		TaskResponse taskResponse = new TaskResponse();
		if(entity!=null){
			entity.setStatus(editTaskRequest.getTaskStatus());
			entity.setTaskDate(editTaskRequest.getTaskDate());
			entity.setTaskText(editTaskRequest.getTaskText());
			userJobTaskEntityDAO.update(entity);
			if (userJobTaskEntityDAO.updateOnEdit(editTaskRequest)) {
				taskResponse.setMessage(Constants.SUCCESS_RESPONSE);
			}
			else {
				taskResponse.setMessage(Constants.FAILURE_RESONSE);
			}
			taskResponse.setData(null);
			return taskResponse;
		}
		else{
			taskResponse.setMessage(Constants.SUCCESS_RESPONSE);
			taskResponse.setData(null);
			return taskResponse;
		}
	}
	
	public TaskResponse editTaskDetails(EditTaskDetailsRequest editTaskDetailsRequest)
	{
		TaskEntity taskEntity = taskEntityDAO.findByTaskId(editTaskDetailsRequest.getTaskId());
		TaskResponse taskResponse = new TaskResponse();

		if(taskEntity != null) {
			if(taskEntity.getDuration() != editTaskDetailsRequest.getDuration())
				taskEntity.setDuration(editTaskDetailsRequest.getDuration()); 
			if(taskEntity.getTaskTitle() != editTaskDetailsRequest.getTaskTitle())
				taskEntity.setTaskTitle(editTaskDetailsRequest.getTaskTitle());
			if (taskEntity.getTaskPendingFrom() != editTaskDetailsRequest.getTaskPendingFrom())
				taskEntity.setTaskPendingFrom(editTaskDetailsRequest.getTaskPendingFrom());
			if (taskEntity.getTaskType() != editTaskDetailsRequest.getTaskType())
				taskEntity.setTaskType(editTaskDetailsRequest.getTaskType());

			taskResponse.setMessage(Constants.SUCCESS_RESPONSE);
			taskEntityDAO.update(taskEntity);
		}
		else {
			taskResponse.setMessage(Constants.FAILURE_RESONSE);
		}
		return taskResponse;
	}

	public GetResponse getAllTaskByUserType(String userType)
	{
		List<UserJobTaskEntity> jobs=userJobTaskEntityDAO.getTaskByUserType(userType);
		GetResponse getResponse=new GetResponse();
		getResponse.setData(jobs);
		getResponse.setMessage(Constants.SUCCESS_RESPONSE);
		return getResponse;
	}		   

}
