package com.vs.ConsignmentTrackingSystem.db.entities;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vs.ConsignmentTrackingSystem.models.Request.JobSearchRequest;

import java.util.logging.Logger;

@Repository
@Transactional
public class JobEntityDAO {
	
	private final static Logger LOGGER = Logger.getLogger(JobEntityDAO.class.getName()); 
	
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(JobEntity jobentity) {
		getSession().save(jobentity);
	}
	
	public List<JobEntity> getJobs(String searchString) {
		Query query = getSession().createQuery("from JobEntity where jobStatus =:searchString OR exporterName =:searchString OR consignee =:searchString OR destination =:searchString OR mode =:searchString OR forwarder =:searchString OR buyerName =:searchString order by jobNo desc");
		query.setParameter("searchString", searchString);
		return query.list();
	}
	
	public List<JobEntity> 	getJobDetails(int jobNo) {
		Query query = getSession().createQuery("from JobEntity where jobNo = :jobNo");
		query.setParameter("jobNo", jobNo);
		return query.list();
	}
	
	public JobEntity findById(long id) {
		Query query = getSession().createQuery("from JobEntity where id = :id");
		query.setParameter("id", id);
		return (JobEntity) query.uniqueResult();
	}
	
	public List<JobEntity> getAllJobs() {
		Query query = getSession().createQuery("from JobEntity");
		return query.list();
	}

	public boolean delete(long id) {
		JobEntity jobEntity = findById(id);
		if (jobEntity == null)
			return false;
		else {
			getSession().delete(jobEntity);
			return true;
		}
	}

	public void update(JobEntity jobEntity) {
		getSession().update(jobEntity);

	}

	public List<String> listTaskIds(long jobId) {
		Query query = getSession().createQuery("select taskIdList from JobEntity where id =:jobId");
		query.setParameter("jobId", jobId);
		return query.list();
	}

	public JobEntity findByJobNo(int jobNo) {
		Query query = getSession().createQuery("from JobEntity where jobNo = :jobNo");
		query.setParameter("jobNo", jobNo);
		return (JobEntity) query.uniqueResult();
	}

	public List<JobEntity> searchByAttributes(JobSearchRequest searchRequest) {
		
		StringBuilder queryString = new StringBuilder("from JobEntity where ");
		
		for(int i = 0;i<searchRequest.getInfo().size();i++) {
						
			if(searchRequest.getInfo().get(i).getKey().equals("date")) {
				String value = searchRequest.getInfo().get(i).getValue();
				String date[] = value.split("::");
				queryString.append(searchRequest.getInfo().get(i).getKey() + " >= '"+ date[0] + "' and " + searchRequest.getInfo().get(i).getKey() + " <= '"+date[1] + "'");
			}
			else {
				queryString.append(searchRequest.getInfo().get(i).getKey() + " like '%" + searchRequest.getInfo().get(i).getValue() + "%'");
			}	
			
			if(i != searchRequest.getInfo().size()-1)
				queryString.append(" and ");
		}
		Query query = getSession().createQuery(queryString.toString());
		
		return query.list();
	}

}
