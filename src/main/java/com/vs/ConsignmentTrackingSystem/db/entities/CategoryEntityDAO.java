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
public class CategoryEntityDAO {
	
private final static Logger LOGGER = Logger.getLogger(JobEntityDAO.class.getName()); 
	
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void save(CategoryEntity categoryEntity) {
		getSession().save(categoryEntity);
	}

	public List<CategoryEntity> getCategories() {
		Query query = getSession().createQuery("from CategoryEntity");
		return query.list();
	}

	public CategoryEntity findById(long id) {
		Query query = getSession().createQuery("from CategoryEntity where id = :id");
		query.setParameter("id", id);
		return (CategoryEntity) query.uniqueResult();
	}

	public void update(CategoryEntity categoryEntity) {
		getSession().update(categoryEntity);
	}
}
