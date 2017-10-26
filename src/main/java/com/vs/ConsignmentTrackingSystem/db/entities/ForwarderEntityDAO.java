package com.vs.ConsignmentTrackingSystem.db.entities;

import java.util.List;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class ForwarderEntityDAO {
	
	private final static Logger LOGGER = Logger.getLogger(JobEntityDAO.class.getName()); 
	
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void save(ForwarderEntity forwarderEntity) {
		getSession().save(forwarderEntity);
	}

	public List<ForwarderEntity> getForwarder() {
		Query query = getSession().createQuery("from ForwarderEntity");
		return query.list();
	}
	
	public ForwarderEntity findById(long id) {
		Query query = getSession().createQuery("from ForwarderEntity where id = :id");
		query.setParameter("id", id);
		return (ForwarderEntity) query.uniqueResult();
	}

	public void update(ForwarderEntity forwarderEntity){
		getSession().update(forwarderEntity);
	}
	
	public boolean delete(long id){
		ForwarderEntity forwarderEntity = findById(id);
		if (forwarderEntity == null)
			return false;
		else{
			getSession().delete(forwarderEntity);
			return true;
		}
	}
}



