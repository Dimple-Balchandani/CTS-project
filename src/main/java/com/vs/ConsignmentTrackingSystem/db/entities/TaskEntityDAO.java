package com.vs.ConsignmentTrackingSystem.db.entities;

import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.logging.Logger;

@Repository
@Transactional
public class TaskEntityDAO {

	private final static Logger LOGGER = Logger.getLogger(TaskEntityDAO.class.getName());

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(TaskEntity taskEntity) {
		getSession().save(taskEntity);
	}

	public List<TaskEntity> getTasks() {
		Query query = getSession().createQuery("from TaskEntity order by task_id");
		return query.list();
	}

	public TaskEntity findById(long id) {
		Query query = getSession().createQuery("from TaskEntity where id = :id");
		query.setParameter("id", id);
		return (TaskEntity) query.uniqueResult();
	}

	public boolean delete(long id) {
		TaskEntity taskEntity = findById(id);
		if (taskEntity == null)
			return false;
		else {
			getSession().delete(taskEntity);
			return true;
		}
	}

	public void update(TaskEntity taskEntity) {
		getSession().update(taskEntity);
	}

	public TaskEntity findByTaskId(long taskId) {
		Query query = getSession().createQuery("from TaskEntity where taskId = :taskId");
		query.setParameter("taskId", taskId);
		return (TaskEntity) query.uniqueResult();
	}

	public List<TaskEntity> fetchTask(String type) {
		Query query = getSession().createQuery("from TaskEntity where userType =:type");
		query.setParameter("type", type);
		return query.list();

	}
	
	public List<TaskEntity> fetchAllTask(String taskIdList) {
		String queryString = "from TaskEntity where task_id IN (";
		String[] ids = taskIdList.split(",");

		for (int i = 0; i < ids.length; i++) {
			if (i == ids.length - 1)
				queryString += Long.parseLong(ids[i]);
			else
				queryString += Long.parseLong(ids[i]) + ",";
		}
		queryString += ") order by task_id";
		Query query = getSession().createQuery(queryString);
		return query.list();
	}

}
