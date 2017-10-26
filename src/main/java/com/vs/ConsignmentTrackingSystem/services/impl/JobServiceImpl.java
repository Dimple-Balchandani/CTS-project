package com.vs.ConsignmentTrackingSystem.services.impl;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vs.ConsignmentTrackingSystem.db.entities.CategoryEntity;
import com.vs.ConsignmentTrackingSystem.db.entities.CategoryEntityDAO;
import com.vs.ConsignmentTrackingSystem.db.entities.JobEntity;
import com.vs.ConsignmentTrackingSystem.db.entities.JobEntityDAO;
import com.vs.ConsignmentTrackingSystem.db.entities.TaskEntity;
import com.vs.ConsignmentTrackingSystem.db.entities.TaskEntityDAO;
import com.vs.ConsignmentTrackingSystem.db.entities.UserEntity;
import com.vs.ConsignmentTrackingSystem.db.entities.UserEntityDAO;
import com.vs.ConsignmentTrackingSystem.db.entities.UserJobTaskEntity;
import com.vs.ConsignmentTrackingSystem.db.entities.UserJobTaskEntityDAO;
import com.vs.ConsignmentTrackingSystem.models.Request.CreateJobRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.JobScrollNumberRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.JobSearchRequest;
import com.vs.ConsignmentTrackingSystem.models.Response.GetResponse;
import com.vs.ConsignmentTrackingSystem.models.Response.JobResponse;
import com.vs.ConsignmentTrackingSystem.services.JobService;
import com.vs.ConsignmentTrackingSystem.utilities.Constants;

import java.util.logging.Logger;

@Component
public class JobServiceImpl implements JobService {

	private final static Logger LOGGER = Logger.getLogger(JobServiceImpl.class.getName());

	@Autowired
	private JobEntityDAO jobEntityDAO;

	@Autowired
	private UserEntityDAO userEntityDAO;

	@Autowired
	private TaskEntityDAO taskEntityDAO;

	@Autowired
	private UserJobTaskEntityDAO userJobTaskEntityDAO;
	
	@Autowired
	private CategoryEntityDAO categoryEntityDAO;

	@Autowired
	private SessionFactory sessionFactory;
	
	public final static long MILLISECONDS_IN_DAY = 24 * 60 * 60 * 1000;

	public static int DifferenceInDays(Date from, Date to) {
	    return (int)((to.getTime() - from.getTime()) / MILLISECONDS_IN_DAY);
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}


	public JobResponse createJob(CreateJobRequest newjob) {

		JobResponse jobResponse = new JobResponse();
		if(userEntityDAO.calculateUserCount() == 9) {
			if (jobEntityDAO.findByJobNo(newjob.getJobNo()) != null) {
				jobResponse.setMessage("JOB ALREADY EXISTS");
				return jobResponse;
			} else {
				JobEntity jobentity = new JobEntity();
				jobentity.setJobNo(newjob.getJobNo());
				jobentity.setExporterId(newjob.getExporterId());
				jobentity.setExporterName(newjob.getExporterName());
				jobentity.setConsignee(newjob.getConsignee());
				jobentity.setInvoiceNo(newjob.getInvoiceNo());
				jobentity.setDate(newjob.getDate());
				jobentity.setQuantity(newjob.getQuantity());
				jobentity.setDestination(newjob.getDestination());
				jobentity.setMode(newjob.getMode());
				jobentity.setForwarder(newjob.getForwarder());
				jobentity.setPmv(newjob.getPmv());
				jobentity.setBuyerName(newjob.getBuyerName());
				jobentity.setJobStatus("ACTIVE");
				jobentity.setRemark(newjob.getRemark());
				jobentity.setCommodity(newjob.getCommodity());
				jobentity.setCategoryId(newjob.getCategoryId());
				jobentity.setCategoryName(newjob.getCategoryName());
				jobentity.setAgent(newjob.getAgent());
				jobentity.setJobCreatedBy(newjob.getJobCreatedBy());
				jobentity.setCountry(newjob.getCountry());
				
				jobEntityDAO.save(jobentity);
				
				CategoryEntity categoryEntity = categoryEntityDAO.findById(newjob.getCategoryId());
				List<TaskEntity> taskList = taskEntityDAO.fetchAllTask(categoryEntity.getTaskIdList());
				
				if(taskList.size() > 0)
				{
					for (int i = 0; i < taskList.size(); i++) {
						
						UserJobTaskEntity userJobTaskEntity = new UserJobTaskEntity();
						UserEntity userDetails = userEntityDAO.findByType(taskList.get(i).getUserType());
						
						userJobTaskEntity.setAssignedDate(newjob.getDate());
						userJobTaskEntity.setDuration(taskList.get(i).getDuration());
						userJobTaskEntity.setTaskType(taskList.get(i).getTaskType());
						userJobTaskEntity.setTaskPendingFrom(taskList.get(i).getTaskPendingFrom());
						userJobTaskEntity.setExporterId(newjob.getExporterId());
						userJobTaskEntity.setExporterName(newjob.getExporterName());
						userJobTaskEntity.setJobNo(newjob.getJobNo());
						userJobTaskEntity.setTaskId(taskList.get(i).getTaskId());
						userJobTaskEntity.setTaskTitle(taskList.get(i).getTaskTitle());
						userJobTaskEntity.setUserType(taskList.get(i).getUserType());
						userJobTaskEntity.setUserId(userDetails.getId());
						userJobTaskEntity.setCategoryId(newjob.getCategoryId());
						userJobTaskEntity.setCategoryName(newjob.getCategoryName());
						if(i == 0)
						{
							userJobTaskEntity.setStatus("ACTIVE");
							userJobTaskEntity.setStartDate(newjob.getDate());
							userJobTaskEntity.setExpectedDate(newjob.getDate());
						}
						else
						{
							userJobTaskEntity.setExpectedDate(null);
							userJobTaskEntity.setStatus("NEW");
							userJobTaskEntity.setStartDate(null);
						}
						userJobTaskEntityDAO.save(userJobTaskEntity);
					}
				}
				else
				{
					jobResponse.setMessage("Please Create Tasks First");
					return jobResponse;
				}
				
				jobResponse.setMessage(Constants.SUCCESS_RESPONSE);
				return jobResponse;
			}
		}
		else
		{
			jobResponse.setMessage("Please Create All User's First Before Creating Job");
			return jobResponse;
		}
	}
	
	public JobResponse deleteJob(long id) {
		JobEntity jobEntity = jobEntityDAO.findById(id);
		boolean isTaskDeleted = userJobTaskEntityDAO.jobAllTaskDelete(jobEntity.getJobNo());
		boolean isJobDeleted = jobEntityDAO.delete(id);
		
		JobResponse jobResponse = new JobResponse();
		if (isJobDeleted && isTaskDeleted)
			jobResponse.setMessage(Constants.SUCCESS_RESPONSE);
		else
			jobResponse.setMessage(Constants.FAILURE_RESONSE);
		return jobResponse;
	}

	public JobResponse updateJob(int jobNo, CreateJobRequest job) {
		JobEntity jobEntity = jobEntityDAO.findByJobNo(jobNo);
		JobResponse jobResponse = new JobResponse();
		if (jobEntity == null)
			jobResponse.setMessage(Constants.FAILURE_RESONSE);
		else {
			if(job.getExporterId() != jobEntity.getExporterId())
				jobEntity.setExporterId(job.getExporterId());
			if(job.getExporterName() != jobEntity.getExporterName())
				jobEntity.setExporterName(job.getExporterName());
			if (job.getConsignee() != jobEntity.getConsignee())
				jobEntity.setConsignee(job.getConsignee());
			if (job.getInvoiceNo() != jobEntity.getInvoiceNo())
				jobEntity.setInvoiceNo(job.getInvoiceNo());
			if (job.getDate() != jobEntity.getDate())
				jobEntity.setDate(job.getDate());
			if (job.getQuantity() != jobEntity.getQuantity())
				jobEntity.setQuantity(job.getQuantity());
			if (job.getDestination() != jobEntity.getDestination())
				jobEntity.setDestination(job.getDestination());
			if (job.getMode() != jobEntity.getMode())
				jobEntity.setMode(job.getMode());
			if (job.getForwarder() != jobEntity.getForwarder())
				jobEntity.setForwarder(job.getForwarder());
			if (job.getBuyerName() != jobEntity.getBuyerName())
				jobEntity.setBuyerName(job.getBuyerName());
			if(job.getPmv() != jobEntity.getPmv())
				jobEntity.setPmv(job.getPmv());
			if(job.getRemark() != jobEntity.getRemark())
				jobEntity.setRemark(job.getRemark());
			if(job.getCommodity() != jobEntity.getCommodity())
				jobEntity.setCommodity(job.getCommodity());
			if(job.getAgent() != jobEntity.getAgent())
				jobEntity.setAgent(job.getAgent());
			if(job.getCountry()!=jobEntity.getCountry())
				jobEntity.setCountry(job.getCountry());
			if(job.getScrollNumber()!=jobEntity.getScrollNumber())
				jobEntity.setScrollNumber(job.getScrollNumber());
			
			jobEntityDAO.update(jobEntity);
			jobResponse.setMessage(Constants.SUCCESS_RESPONSE);
		}
		return jobResponse;
	}

	public GetResponse getTaskDetailsForJob(int jobNo) {
		List<UserJobTaskEntity> tasksforjob = userJobTaskEntityDAO.getTaskDetailsForJob(jobNo);
		GetResponse getAllJobsResponse = new GetResponse();
		getAllJobsResponse.setData(tasksforjob);
		getAllJobsResponse.setMessage(Constants.SUCCESS_RESPONSE);
		return getAllJobsResponse;
	}
	
	public GetResponse getJobDetails(int jobNo) {
		
		List<JobEntity> jobDetails = jobEntityDAO.getJobDetails(jobNo);
		GetResponse getAllJobsResponse = new GetResponse();
		getAllJobsResponse.setData(jobDetails);
		getAllJobsResponse.setMessage(Constants.SUCCESS_RESPONSE);
		return getAllJobsResponse;
	}

	public GetResponse getJobs(String status) {
		
		//startBlockJob();
		List<JobEntity> jobs = jobEntityDAO.getJobs(status);
		GetResponse getAllJobsResponse = new GetResponse();
		getAllJobsResponse.setData(jobs);
		getAllJobsResponse.setMessage(Constants.SUCCESS_RESPONSE);
		return getAllJobsResponse;
	}

	@Override
	public GetResponse getJobsByAttributes(JobSearchRequest searchRequest) {
		List<JobEntity> jobs = jobEntityDAO.searchByAttributes(searchRequest);
		GetResponse getAllJobsResponse = new GetResponse();
		getAllJobsResponse.setData(jobs);
		getAllJobsResponse.setMessage(Constants.SUCCESS_RESPONSE);
		return getAllJobsResponse;
	}
	
	public GetResponse getDelayJobsOfUser(String userType, long userId) {
		
		List<UserJobTaskEntity> jobs = userJobTaskEntityDAO.searchDelayJobOfUser(userType,userId);
		GetResponse getAllJobsResponse = new GetResponse();
		getAllJobsResponse.setData(jobs);
		getAllJobsResponse.setMessage(Constants.SUCCESS_RESPONSE);
		return getAllJobsResponse;
	}
	
	public GetResponse getJobByTaskId(long taskId){
	   List<UserJobTaskEntity>userJobTaskEntity=userJobTaskEntityDAO.findJobByTaskId(taskId);
	    GetResponse getResponse=new GetResponse();
	    getResponse.setMessage(Constants.SUCCESS_RESPONSE);
	    getResponse.setData(userJobTaskEntity);
	    return getResponse;
	}

	@Override
	public JobResponse updateJobScrollNumber(JobScrollNumberRequest job) {
		long jobId=job.getId();
		JobEntity jobEntity = jobEntityDAO.findById(jobId);
		JobResponse jobResponse = new JobResponse();
		if (jobEntity == null)
			jobResponse.setMessage(Constants.FAILURE_RESONSE);
		else {
					jobEntity.setScrollNumber(job.getScrollNumber());	
					jobEntity.setJobStatus("COMPLETED");
					jobEntityDAO.update(jobEntity);
					jobResponse.setMessage(Constants.SUCCESS_RESPONSE);
		}
		return jobResponse;
	}
}
