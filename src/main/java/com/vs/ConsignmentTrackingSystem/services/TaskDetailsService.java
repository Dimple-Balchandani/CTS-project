package com.vs.ConsignmentTrackingSystem.services;

import org.springframework.stereotype.Service;
import com.vs.ConsignmentTrackingSystem.models.Request.AddRemarkRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.CloseTaskRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.EditTaskDetailsRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.EditTaskRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.StartTaskRequest;
import com.vs.ConsignmentTrackingSystem.models.Response.GetResponse;
import com.vs.ConsignmentTrackingSystem.models.Response.TaskResponse;

@Service
public interface TaskDetailsService {

	public GetResponse getTasks();

	public TaskResponse startTask(StartTaskRequest startTaskRequest);

	public TaskResponse closeTask(CloseTaskRequest closeTaskRequest);

	public GetResponse getTaskByUser(String type, long userId);
	
	public TaskResponse completeTask(int jobNo);

	public TaskResponse addRemark(AddRemarkRequest addRemarkRequest);

	public TaskResponse editTask(EditTaskRequest editTaskRequest);
	
	public TaskResponse editTaskDetails(EditTaskDetailsRequest editTaskDetailsRequest);
	
	public GetResponse getAllTaskByUserType(String userType);
}
