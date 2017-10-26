package com.vs.ConsignmentTrackingSystem.utilities;

public class Constants {

	public static final String USER_TABLE_NAME = "login_details";

	public static final String USER_ID = "user_id";

	public static final String TASK_TABLE_NAME = "task_details";

	public static final String TASK_AUTO_ID = "id";

	public static final String TASK_ID = "task_id";

	public static final String JOB_TABLE_NAME = "job_details";

	public static final String JOB_ID = "job_id";

	public static final String USERJOBTASK_TABLE_NAME = "user_job_task_details";

	public static final String USER_TASK_ID = "user_task_id";

	public static final String API_PATH = "/api";

	public static final String USERS_API_TAG = "User";
	
	public static final String USERS_API_TAG_DESCRIPTION = "Apis for user";
	
	public static final String TASKS_API_TAG = "Task";
	
	public static final String TASKS_API_TAG_DESCRIPTION = "Apis for task";

	public static final String JOBS_API_TAG = "Job";
	
	public static final String JOBS_API_TAG_DESCRIPTION = "Apis for job";
	
	public static final String CATEGORY_API_TAG = "Category";
	
	public static final String CATEGORY_API_TAG_DESCRIPTION = "Apis for Category";
	
	public static final String FORWARDER_API_TAG= "Forwarder";
	
	public static final String FORWARDER_API_TAG_DESCRIPTION= "Apis for Forwarder";
	
	public static final String COUNTRY_API_TAG= "Country";
	
	public static final String COUNTRY_API_TAG_DESCRIPTION= "Apis for Country";
		
	public static final String AUTH_PARAM = "authorization";

	public static final String AUTH_PARAM_DESCRIPTION = "The auth token of the logged in validated user";

	public static final String USER_PARAM = "Requires the user info";
	
	public static final String TASK_ID_PARAM = "Requires the taskId info";
	
	public static final String FORWARDER_PARAM = "Requires the forwarder info";
	
	public static final String COUNTRY_PARAM = "Requires the country info";
	
	public static final String USER_ID_PARAM = "Id of the user";
	
	public static final String COUNTRY_ID_PARAM = "Id of the country";
	
	public static final String FORWARDER_ID_PARAM = "Id of the forwarder";
	
	public static final String USER_ROLE_PARAM = "Role of the user ";
	
	public static final String USER_TYPE_PARAM = "Type of the user";
	
	public static final String START_TASK_DETAILS = "details to start task";
	
	public static final String CLOSE_TASK_DETAILS = "details to close task";
	
	public static final String ADD_REMARK_DETAILS ="details to add a remark";
	
	public static final String EDIT_TASK_DETAILS_DESCRIPTION ="details to edit for a task";
	
	public static final String JOB_PARAM = "Requires the job info";
	
	public static final String JOB_ID_PARAM = "Id of the job";
	
	public static final String JOB_SCROLL_NUMBER_PARAM = "Id of the job";
	
	public static final String JOB_STATUS_PARAM = "job status";
	
	public static final String JOB_NUMBER_PARAM = "job no.";
	
	public static final String JOB_ATTRIBUTE_PARAM = "Search for Attributes";
	
	public static final String LOGIN = "/login";
	
	public static final String FORWARDER = "/forwarder";

	public static final String LOGIN_DESCRIPTION = "Api for login";

	public static final String LOGIN_PARAM = "Requires the user info for login .";

	public static final String LOGOUT = "/logout";

	public static final String LOGOUT_DESCRIPTION = "Api for logout";

	public static final String CREATE_USER = "/user";

	public static final String CREATE_USER_DESCRIPTION = "Creates a user";

	public static final String DELETE_USER = "/user/{id}";
	
	public static final String DELETE_COUNTRY = "/country/{id}";
	
	public static final String DELETE_FORWARDER = "/forwarder/{id}";
	
	public static final String DELETE_USER_DESCRIPTION = "Deletes a user";
	
	public static final String DELETE_FORWARDER_DESCRIPTION = "Delete a forwarder";
	
	public static final String DELETE_COUNTRY_DESCRIPTION = "Delete a country";
	
	public static final String UPDATE_USER = "/user/{id}";

	public static final String UPDATE_USER_DESCRIPTION = "Updates a user";

	public static final String LIST_USERS = "/user";

	public static final String LIST_USERS_DESCRIPTION = "List all the users";
	
	public static final String PRIVILEDGE_REPORT = "/priviledgeReport";
	
	public static final String PRIVILEDGE_REPORT_DESCRIPTION = "Priviledges for Report";
	
	public static final String PRIVILEDGE_REPORT_PARAM = "Requires the report access info"; 
	
	public static final String GENERATE_REPORT = "/generateReport";
	
	public static final String GENERATE_REPORT_DESCRIPTION = "Generate Report";

	public static final String GENERATE_REPORT_PARAM = "Requires the generate report info";
	
	public static final String CREATE_JOB = "/job";

	public static final String CREATE_JOB_DESCRIPTION = "Creates a new job";
	
	public static final String DELETE_JOB = "/job/{id}";
	
	public static final String UPDATE_JOB_SCROLL_NUMBER="/job/scrollNumber";
	
	public static final String UPDATE_JOB_SCROLL_NUMBER_DESCRIPTION="Set scroll number for the job ";
	
	public static final String DELETE_JOB_DESCRIPTION = "Deletes a job";
	
	public static final String UPDATE_JOB = "job/{jobNo}";
	
	public static final String UPDATE_JOB_DESCRIPTION = "Updates a job";
	
	public static final String TASKS_FOR_JOB = "/job/{jobNo}";
	
	public static final String JOB_Info = "/getJob/{jobNo}";
	
	public static final String TASKS_FOR_JOB_DESCRIPTION = "Get Tasks details for a job";
	
	public static final String JOB_DESCRIPTION = "Get job details";
	
	public static final String LIST_JOBS = "/job";

	public static final String LIST_JOBS_DESCRIPTION = "List all the jobs";

	public static final String LIST_JOBS_BY_ATTRIBUTE = "/getJobs";
	
	public static final String LIST_JOBS_BY_ATTRIBUTE_DESCRIPTION = "List all the jobs by searched attributes";
	
	public static final String DELAY_JOBS_OF_USER_DESCRIPTION = "List all the delay jobs of user";
	
	public static final String DELAY_JOB_OF_USER = "/delayJob/{userType}/{userId}";

	public static final String LIST_TASKS = "/task";

	public static final String LIST_TASKS_DESCRIPTION = "List all the tasks";

	public static final String START_TASK = "/startTask";

	public static final String START_TASK_DESCRIPTION = "Start task";

	public static final String CLOSE_TASK = "/closeTask";

	public static final String CLOSE_TASK_DESCRIPTION = "Close task";
	
	public static final String TASKS_BY_USERTYPE = "/task/{userType}/{userId}";
	
	public static final String TASKS_BY_USERTYPE_DESCRIPTION = "Get tasks by userType";
	
	public static final String COMPLETED_JOB = "/completeJob/{jobNo}";
	
	public static final String COMPLETED_JOB_DESCRIPTION = "Complete the all task of a job";
	
	public static final String USER_TASK_DESCRIPTION = "task of a user";
	
	public static final String TASK_STATUS = "/taskByUserType/{userType}";
	
	public static final String  ADD_REMARK = "/addRemark";
	
	public static final String ADD_REMARK_DESCRIPTION ="Adds remark for a task";
	
	public static final String EDIT_TASK = "/task";
	
	public static final String EDIT_TASK_DETAIL_JOB= "/editTaskDetails";
	
	public static final String EDIT_TASK_DESCRIPTION = "Updates task details for particular task of a job";
	
	public static final String CREATE_CATEGORY = "/category";
	 
	public static final String CREATE_FORWARDER = "/forwarder";
	
	public static final String CREATE_COUNTRY = "/country";
	
	public static final String CREATE_CATEGORY_DESCRIPTION = "Creates a new category";
	
	public static final String LIST_CATEGORY = "/category";
	
	public static final String LIST_FORWARDER = "/forwarder";
	
	public static final String LIST_COUNTRY = "/country";
	
	public static final String LIST_CATEGORY_DESCRIPTION = "List all the categories";
	
	public static final String CREATE_FORWARDER_DESCRIPTION = "Creates a new forwarders";
	
	public static final String LIST_COUNTRY_DESCRIPTION = "List all the countries";
	
	public static final String CREATE_COUNTRY_DESCRIPTION = "Creates a new country";
	
	public static final String UPDATE_CATEGORY_DESCRIPTION = "Updates a category";
	
	public static final String UPDATE_FORWARDER_DESCRIPTION = "Updates a forwarder";
	
	public static final String UPDATE_COUNTRY_DESCRIPTION = "Updates a country";
	
	public static final String UPDATE_CATEGORY = "/category/{id}";
	
	public static final String UPDATE_FORWARDER = "/forwarder/{id}";
	
	public static final String UPDATE_COUNTRY = "/country/{id}";
	
	public static final String LOGOUT_SUCCESS_RESPONSE = "SUCCESFULLY LOGGED OUT";
	
	public static final String LOGOUT_SESSION_EXPIRED_RESPONSE = "SESSION EXPIRED! PLEASE LOG IN AGAIN";

	public static final String SUCCESS_RESPONSE = "SUCCESS";
	
	public static final String FAILURE_RESONSE = "FAILURE";
	
	public static final String PATH_TASK_LIST_JSON = "classpath:json/task.json";

	public static final String EDIT_TASK_DETAIL_DESCRIPTION = "Update task details";

	public static final String EDIT_TASK_DETAILS = "/editTask";

	public static final String LIST_FORWARDER_DESCRIPTION = "List all the forwarder";
	
	public static final String JOB_BY_TASK_ID_DESCRIPTION = "Job By taskId";

	public static final String JOB_BY_TASK_ID = "/jobBytaskId/{taskId}";	

}