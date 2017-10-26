package com.vs.ConsignmentTrackingSystem.db.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vs.ConsignmentTrackingSystem.models.Request.AddRemarkRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.EditTaskRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.GenerateReportRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.StartTaskRequest;
import com.vs.ConsignmentTrackingSystem.models.Response.GenerateAllClientReport;
import com.vs.ConsignmentTrackingSystem.models.Response.GenerateFinalReportResponse;
import com.vs.ConsignmentTrackingSystem.models.Response.GenerateReportResponse;

import java.util.logging.Logger;

@Repository
@Transactional
public class UserJobTaskEntityDAO {

	private final static Logger LOGGER = Logger.getLogger(UserJobTaskEntityDAO.class.getName());

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(UserJobTaskEntity userJobTaskEntity) {
		getSession().save(userJobTaskEntity);

	}
	

	public boolean updateOnStart(StartTaskRequest startTaskRequest) {
		boolean flag = false;
		Query query = getSession().createQuery("from UserJobTaskEntity where jobNo =:jobNo and taskId =:taskId");
		query.setParameter("jobNo", startTaskRequest.getJobNo());
		query.setParameter("taskId", startTaskRequest.getTaskId());
		UserJobTaskEntity userJobTaskEntity = (UserJobTaskEntity) query.uniqueResult();

		if (userJobTaskEntity != null) {
			userJobTaskEntity.setStatus("ACTIVE");
			userJobTaskEntity.setStartDate(startTaskRequest.getStartDate());
			userJobTaskEntity.setUserId(startTaskRequest.getUserId());
			userJobTaskEntity.setExpectedDate(
					calculateExpectedDate(startTaskRequest.getStartDate(), getDuration(startTaskRequest.getTaskId())));
			getSession().saveOrUpdate(userJobTaskEntity);
			flag = true;
			return flag;
		} else
			return flag;

	}
	

	public boolean updateOnEdit(EditTaskRequest editTaskRequest) {
		boolean flag = false;
		Query query = getSession().createQuery("from UserJobTaskEntity where jobNo =:jobNo and taskId =:taskId");
		query.setParameter("jobNo", editTaskRequest.getJobNo());
		query.setParameter("taskId", editTaskRequest.getTaskId());
		UserJobTaskEntity userJobTaskEntity = (UserJobTaskEntity) query.uniqueResult();
		if (userJobTaskEntity != null) 
		{
		    userJobTaskEntity.setTaskDate(editTaskRequest.getTaskDate());
			userJobTaskEntity.setExpectedDate(
					calculateExpectedDate(editTaskRequest.getTaskDate(), getDuration(editTaskRequest.getTaskId())));
			getSession().saveOrUpdate(userJobTaskEntity);
			List<UserJobTaskEntity> tasks= getTaskDetailsForJob(editTaskRequest.getJobNo());
			{  if(tasks!=null)
			   { for (int i = 0; i < tasks.size(); i++) 
			     {  if(tasks.get(i).getTaskPendingFrom()== editTaskRequest.getTaskId())
			        {  tasks.get(i).setTaskDate(editTaskRequest.getTaskDate());
			    	   tasks.get(i).setExpectedDate(
					   calculateExpectedDate(editTaskRequest.getTaskDate(), getDuration(tasks.get(i).getTaskId())));
			           getSession().saveOrUpdate(tasks.get(i));	
			           Query subQuery = getSession().createQuery("from UserJobTaskEntity where jobNo =:jobNo and task_pending_from=:taskPendingFromParameter");
			           subQuery.setParameter("jobNo", tasks.get(i).getJobNo());
			           subQuery.setParameter("taskPendingFromParameter", tasks.get(i).getTaskId());
			           List<UserJobTaskEntity> subList= subQuery.list();
			           if(subList!=null)
			           {  EditTaskRequest editTaskRequest1=new EditTaskRequest();
			              editTaskRequest1.setJobNo(tasks.get(i).getJobNo());
			              editTaskRequest1.setTaskDate(tasks.get(i).getTaskDate());
			              editTaskRequest1.setTaskText(tasks.get(i).getTaskText());
			              editTaskRequest1.setTaskStatus(tasks.get(i).getStatus());
			              editTaskRequest1.setTaskId(tasks.get(i).getTaskId());
			        	   updateOnEdit(editTaskRequest1);
			           }
			           
			        } 	
				 }
			    
				
			   }
			}
			flag = true;
		} 
		else
		{ flag=false;
		}	
		return flag;

	}
	
	

	public int getDuration(long taskId) {
		Query query = getSession().createQuery("from TaskEntity where taskId =:taskId");
		query.setParameter("taskId", taskId);
		TaskEntity task = (TaskEntity) query.uniqueResult();
		return task.getDuration();
	}

	public Date calculateExpectedDate(Date dt, int duration) {
		Date expectedDate = new Date(dt.getTime() + duration * 24 * 60 * 60 * 1000);
		return expectedDate;

	}
	
	//This Function is Schedule to implememt everyday at 1AM daily
//	@Scheduled(cron = "0 0 1 * * *")
//	public void updateTaskStatus() {
//		
//		LOGGER.info("CALLING AUTO UPDATE TASK STATUS");
//		Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
//		Query query = getSession().createQuery(
//				"Update UserJobTaskEntity set status = 'PENDING' where expectedDate < :currentDate and status in ('ACTIVE')");
//		query.setParameter("currentDate", currentDate);
//		query.executeUpdate();
//		
//		query = getSession().createQuery("from UserJobTaskEntity where status IN('PENDING')");
//
//		if (!query.list().isEmpty()) {
//
//			List<UserJobTaskEntity> userJobTaskEntityList = query.list();
//
//			for (int i = 0; i < userJobTaskEntityList.size(); i++) {
//				Query subQuery = getSession().createQuery("Update JobEntity set jobStatus = 'PENDING' where jobNo = :jobNo");
//				subQuery.setParameter("jobNo", userJobTaskEntityList.get(i).getJobNo());
//				subQuery.executeUpdate();
//			}
//		}
//		
//		query = getSession().createQuery(
//				"Update UserJobTaskEntity set status = 'NEW,PENDING' where expectedDate < :currentDate and status in ('NEW')");
//		query.setParameter("currentDate", currentDate);
//		query.executeUpdate();
//	}

	public UserJobTaskEntity findByJobNo(int jobNo) {
		Query query = getSession().createQuery("from UserJobTaskEntity where jobNo =:jobNo");
		query.setParameter("jobNo", jobNo);
		return (UserJobTaskEntity) query.uniqueResult();
	}
	
	public UserJobTaskEntity findById(long id) {
		Query query = getSession().createQuery("from UserJobTaskEntity where id =:id");
		query.setParameter("id", id);
		return (UserJobTaskEntity) query.uniqueResult();
	}

	public UserJobTaskEntity getDetailsByJobNoAndTaskId(int jobNo, long taskId) {

		Query query = getSession().createQuery("from UserJobTaskEntity where jobNo =:jobNo and taskId = :taskId");
		query.setParameter("jobNo", jobNo);
		query.setParameter("taskId", taskId);
		return (UserJobTaskEntity) query.uniqueResult();
	}
	
	public UserJobTaskEntity updateOnCloseById(int jobNo, long id) {

		Query query = getSession().createQuery("from UserJobTaskEntity where jobNo =:jobNo and id = :id");
		query.setParameter("jobNo", jobNo);
		query.setParameter("id", id);
		return (UserJobTaskEntity) query.uniqueResult();
	}
	
	public void updateExporter(long id,String name){
		
		Query query=getSession().createQuery("Update UserJobTaskEntity set exporterName =:name where exporterId=:id");
		query.setParameter("id", id);
		query.setParameter("name", name);
		query.executeUpdate();
	}

	public List<UserJobTaskEntity> getTaskDetailsForJob(int jobNo) {

		Query query = getSession().createQuery("from UserJobTaskEntity where jobNo = :jobNo order by taskId");
		query.setParameter("jobNo", jobNo);
		return query.list();

	}
	
	public List<UserJobTaskEntity> getTaskDetailsByExpectedDate(int jobNo) {
		Query query = getSession().createQuery("from UserJobTaskEntity where expectedDate = '0000-00-00' and jobNo = :jobNo");
		query.setParameter("jobNo", jobNo);
		return query.list();
	}

	public boolean jobAllTaskDelete(int jobNo) {
		Query query = getSession().createQuery("from UserJobTaskEntity where jobNo =:jobNo");
		query.setParameter("jobNo", jobNo);
		if (!query.list().isEmpty()) {
			List<UserJobTaskEntity> userJobTaskEntity = query.list();
			for (int i = 0; i < userJobTaskEntity.size(); i++) {
				getSession().delete(userJobTaskEntity.get(i));
			}
			return true;
		} else {
			return false;
		}
	}

	public void update(UserJobTaskEntity userJobTaskEntity) {
		getSession().update(userJobTaskEntity);

	}

	public List<UserJobTaskEntity> getTaskByUser(String userType, long userId) {

		Query query = getSession().createQuery(
				"from UserJobTaskEntity where userType =:userType and status IN('PENDING','ACTIVE') and (userId = 0 OR userId =:userId) order by jobNo");
		query.setParameter("userType", userType);
		query.setParameter("userId", userId);
		return query.list();

	}
	public List<UserJobTaskEntity> getTaskByUserType(String userType) {

		Query query = getSession().createQuery(
				"from UserJobTaskEntity where userType =:userType and status IN('PENDING','ACTIVE')");
		query.setParameter("userType", userType);
		return query.list();

	}



	public void checkPendingTask() {
		
		LOGGER.info("CALLING checkPendingTask  UPDATE TASK STATUS");
		Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		Query query = getSession().createQuery(
				"Update UserJobTaskEntity set status = 'PENDING' where expectedDate < :currentDate and status in ('ACTIVE')");
		query.setParameter("currentDate", currentDate);
		query.executeUpdate();
		
		query = getSession().createQuery("from UserJobTaskEntity where status IN('PENDING')");

		if (!query.list().isEmpty()) {

			List<UserJobTaskEntity> userJobTaskEntityList = query.list();

			for (int i = 0; i < userJobTaskEntityList.size(); i++) {
				Query subQuery = getSession().createQuery("Update JobEntity set jobStatus = 'PENDING' where jobNo = :jobNo");
				subQuery.setParameter("jobNo", userJobTaskEntityList.get(i).getJobNo());
				subQuery.executeUpdate();
			}
		}
		
		query = getSession().createQuery(
				"Update UserJobTaskEntity set status = 'NEW,PENDING' where expectedDate < :currentDate and status in ('NEW')");
		query.setParameter("currentDate", currentDate);
		query.executeUpdate();
	}

	//decision for getting reports of all clients or specific client
	public List decision(GenerateReportRequest reportRequest)
	{
		if(reportRequest.getExportedId()==1234)
		{
			return generateAllClientReport();
		}
		else{
			return generateReport(reportRequest);
		}
	}
	
	//for getting all client reports together
	public List generateAllClientReport() {
		
		List list=new ArrayList();
		
		Query query=getSession().createQuery("select exporterId,exporterName from UserJobTaskEntity group by exporterId order by exporterId");
		if(!query.list().isEmpty())
		{
			
			List<Object[]> exporterList=new ArrayList<Object[]>(); 	//for getting exporter id and exporter name
			exporterList=query.list();
			for(Object[] e: exporterList){
				
				Long id = (Long)e[0];
				String exporterName = (String)e[1];
			
				GenerateAllClientReport generateAllClientReport=new GenerateAllClientReport();
			
				GenerateReportRequest generateReportRequest=new GenerateReportRequest();
				
				generateReportRequest.setExportedId(id);
				generateReportRequest.setReportFields("");
				List listo=generateReport(generateReportRequest);
				
				generateAllClientReport.setClientName(exporterName);
				generateAllClientReport.setJobData(listo);
				list.add(generateAllClientReport);
			}
			return list;
		}
		else {
			return null;
		}
	}
	
	
	
	//for getting specific client reports
	public List generateReport(GenerateReportRequest reportRequest) {
		
		Query categoryQuery = getSession().createQuery("select categoryId, categoryName from UserJobTaskEntity group by categoryId order by categoryId ");
		if(!categoryQuery.list().isEmpty())
		{									
			List finalReportResponseList=new ArrayList();
		
			List<Object[]> categoryList=new ArrayList<Object[]>(); 	//for getting categories id and categories name
			categoryList=categoryQuery.list();
			for(Object[] c: categoryList){
				
				Long id = (Long)c[0];
				String categoryName = (String)c[1];

				GenerateFinalReportResponse generateFinalReportResponse=new GenerateFinalReportResponse();
				generateFinalReportResponse.setCategory(categoryName);

				List reportList = new ArrayList();

				Date endDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
				Date startDate = new Date(endDate.getTime() - 15 * 24 * 60 * 60 * 1000);


				Query query = getSession().createQuery("select jobNo from UserJobTaskEntity where exporterId = :exporterId and taskId = 127 and categoryId = :categoryId and ((task_date >= :startDate and taskDate <= :endDate)  or task_date is NULL)) group by jobNo order by jobNo");
				query.setParameter("exporterId", reportRequest.getExportedId());
				query.setParameter("startDate", startDate);
				query.setParameter("endDate", endDate);
				query.setParameter("categoryId", id);


				if (!query.list().isEmpty()) {

					List<Integer> jobListArray = query.list();
					String jobList = "";

					for(int i = 0; i < jobListArray.size(); i++) {

						jobList = jobList + "," +  Integer.toString(jobListArray.get(i));
					}
					jobList = jobList.substring(1, jobList.length());

					query = getSession().createQuery("from JobEntity where jobNo IN ( :jobList ) order by jobNo");
					query.setParameterList("jobList", jobListArray);

					if (!query.list().isEmpty()) {

						List<JobEntity> jobEntityList = query.list();

						GenerateReportResponse generateReportResponse = new GenerateReportResponse();
						generateReportResponse.setId(0);
						generateReportResponse.setJobNo(0);
						generateReportResponse.setExporterId(0);
						generateReportResponse.setExporterName("");
						generateReportResponse.setConsignee("");
						generateReportResponse.setInvoiceNo("");
						generateReportResponse.setDate(null);
						generateReportResponse.setQuantity("");
						generateReportResponse.setDestination("");
						generateReportResponse.setMode("");
						generateReportResponse.setForwarder("");
						generateReportResponse.setPmv("");
						generateReportResponse.setBuyerName("");
						generateReportResponse.setJobStatus("");
						generateReportResponse.setRemark("");
						generateReportResponse.setCommodity("");

						if (reportRequest.getReportFields().isEmpty()) {

							Query subQuery = getSession().createQuery("from UserJobTaskEntity where jobNo IN ( :jobList ) group by taskId");
							subQuery.setParameterList("jobList", jobListArray);

							List<UserJobTaskEntity> list = subQuery.list();
							generateReportResponse.setUserJobTaskEntity(list);

						} else {
							String queryString = "from UserJobTaskEntity where jobNo IN ( :jobList )  and taskId IN (";
							String[] ids = reportRequest.getReportFields().split(",");

							for (int j = 0; j < ids.length; j++) {
								if (j == ids.length - 1)
									queryString += Long.parseLong(ids[j]);
								else
									queryString += Long.parseLong(ids[j]) + ",";
							}
							queryString += ") group by taskId";
							Query subQuery = getSession().createQuery(queryString);
							subQuery.setParameterList("jobList", jobListArray);

							List<UserJobTaskEntity> list = subQuery.list();
							generateReportResponse.setUserJobTaskEntity(list);
						}

						reportList.add(generateReportResponse);

						for (int i = 0; i < jobEntityList.size(); i++) {

							generateReportResponse = new GenerateReportResponse();
							generateReportResponse.setId(jobEntityList.get(i).getId());
							generateReportResponse.setJobNo(jobEntityList.get(i).getJobNo());
							generateReportResponse.setExporterId(jobEntityList.get(i).getExporterId());
							generateReportResponse.setExporterName(jobEntityList.get(i).getExporterName());
							generateReportResponse.setConsignee(jobEntityList.get(i).getConsignee());
							generateReportResponse.setInvoiceNo(jobEntityList.get(i).getInvoiceNo());
							generateReportResponse.setDate(jobEntityList.get(i).getDate());
							generateReportResponse.setQuantity(jobEntityList.get(i).getQuantity());
							generateReportResponse.setDestination(jobEntityList.get(i).getDestination());
							generateReportResponse.setMode(jobEntityList.get(i).getMode());
							generateReportResponse.setForwarder(jobEntityList.get(i).getForwarder());
							generateReportResponse.setPmv(jobEntityList.get(i).getPmv());
							generateReportResponse.setBuyerName(jobEntityList.get(i).getBuyerName());
							generateReportResponse.setJobStatus(jobEntityList.get(i).getJobStatus());
							generateReportResponse.setRemark(jobEntityList.get(i).getRemark());
							generateReportResponse.setCommodity(jobEntityList.get(i).getCommodity());

							if (reportRequest.getReportFields().isEmpty()) {
								Query subQuery = getSession().createQuery("from UserJobTaskEntity where jobNo = :jobList order by jobNo");
								subQuery.setParameter("jobList", jobEntityList.get(i).getJobNo());

								List<UserJobTaskEntity> list = subQuery.list();
								generateReportResponse.setUserJobTaskEntity(list);

							} else {
								String queryString = "from UserJobTaskEntity where jobNo = :jobList  and taskId IN (";
								String[] ids = reportRequest.getReportFields().split(",");

								for (int j = 0; j < ids.length; j++) {
									if (j == ids.length - 1)
										queryString += Long.parseLong(ids[j]);
									else
										queryString += Long.parseLong(ids[j]) + ",";
								}
								queryString += ") order by jobNo";
								Query subQuery = getSession().createQuery(queryString);
								subQuery.setParameter("jobList", jobEntityList.get(i).getJobNo());

								List<UserJobTaskEntity> list = subQuery.list();
								generateReportResponse.setUserJobTaskEntity(list);
							}
							reportList.add(generateReportResponse);
						}
						generateFinalReportResponse.setTaskData(reportList);
					}
					
				}

				else {
					continue;
				}
				finalReportResponseList.add(generateFinalReportResponse);
			}
			return finalReportResponseList;
		}
		else {
			return null;
	}
}
	
	
	public boolean isAllTaskClosedForUser(String userType, int jobNo) {
		Query query = getSession().createQuery(
				"from UserJobTaskEntity where userType =:userType and jobNo =:jobNo and status NOT IN('CLOSE')");
		query.setParameter("userType", userType);
		query.setParameter("jobNo", jobNo);
		if (!query.list().isEmpty()) {
			return false;
		} else
			return true;

	}

	public void completeTask(int jobNo) {
		
		Query query = getSession().createQuery(
				"Update UserJobTaskEntity set status = 'CLOSE' where jobNo = :jobNo");
		query.setParameter("jobNo", jobNo);
		query.executeUpdate();
		
		query = getSession().createQuery(
				"Update JobEntity set jobStatus = 'COMPLETED' where jobNo = :jobNo");
		query.setParameter("jobNo", jobNo);
		query.executeUpdate();
	}

	public void findByJobNoAndTaskId(AddRemarkRequest addRemarkRequest) {
		Query query = null;
		if(addRemarkRequest.getType().equalsIgnoreCase("USER"))
		{   query = getSession().createQuery("update UserJobTaskEntity set  user_remark=:user_remark where jobNo =:jobNo and taskId =:taskId");
        	query.setParameter("user_remark",addRemarkRequest.getRemark());
    	}
        else
        {   query = getSession().createQuery("update UserJobTaskEntity set  admin_remark=:ad_remark where jobNo =:jobNo and taskId =:taskId");
        	query.setParameter("ad_remark",addRemarkRequest.getRemark());
		}


		query.setParameter("jobNo", addRemarkRequest.getJobNo());
		query.setParameter("taskId", addRemarkRequest.getTaskId());
		query.executeUpdate();
		//return (UserJobTaskEntity) query.uniqueResult();
	}
	
	public List<UserJobTaskEntity> searchDelayJobOfUser(String userType, long userId) {
		
		Query query = getSession().createQuery(
				"from UserJobTaskEntity where userType =:userType and status IN('PENDING','ACTIVE') and (userId = 0 OR userId =:userId) order by jobNo");
		query.setParameter("userType", userType);
		query.setParameter("userId", userId);
		return query.list();
	}

	public UserJobTaskEntity findByJobAndTask(int jobNo, long taskId) {
		Query query = getSession().createQuery("from UserJobTaskEntity where jobNo =:jobNo and taskId =:taskId");
		query.setParameter("jobNo",jobNo);
		query.setParameter("taskId", taskId);
		return (UserJobTaskEntity) query.uniqueResult();
	}
	
	public List<UserJobTaskEntity>findJobByTaskId(long taskId) {
		
		
		Query query = getSession().createQuery("from UserJobTaskEntity where taskId >=:taskId and (status='ACTIVE' or status='PENDING')");
		query.setParameter("taskId", taskId);
		
	    return query.list();
	}
}
