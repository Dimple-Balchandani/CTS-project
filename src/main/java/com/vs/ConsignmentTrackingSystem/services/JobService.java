package com.vs.ConsignmentTrackingSystem.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vs.ConsignmentTrackingSystem.models.Request.CreateJobRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.JobScrollNumberRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.JobSearchRequest;
import com.vs.ConsignmentTrackingSystem.models.Response.GetResponse;
import com.vs.ConsignmentTrackingSystem.models.Response.JobResponse;

@Service
public interface JobService {

	public JobResponse createJob(CreateJobRequest job);

	public JobResponse deleteJob(long id);

	public JobResponse updateJob(int jobNo, CreateJobRequest job);
	
	public JobResponse updateJobScrollNumber(JobScrollNumberRequest job);

	public GetResponse getTaskDetailsForJob(int jobNo);
	
	public GetResponse getJobDetails(int jobNo);

	public GetResponse getJobs(String status);

	public GetResponse getJobsByAttributes(JobSearchRequest searchRequest);

	public GetResponse getDelayJobsOfUser(String userType, long userId);
	
	public GetResponse getJobByTaskId(long taskId);
}
