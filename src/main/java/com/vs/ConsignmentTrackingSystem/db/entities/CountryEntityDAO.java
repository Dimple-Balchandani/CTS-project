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

public class CountryEntityDAO
{
	private final static Logger LOGGER = Logger.getLogger(JobEntityDAO.class.getName());
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession()
	
    {
		return sessionFactory.getCurrentSession();
	}
	public void save(CountryEntity countryEntity) {
		getSession().save(countryEntity);
	}

	public List<CountryEntity> getCountries() {
		Query query = getSession().createQuery("from CountryEntity");
		return query.list();
	}

	public CountryEntity findById(long id) {
		Query query = getSession().createQuery("from CountryEntity where id = :id");
		query.setParameter("id", id);
		return (CountryEntity) query.uniqueResult();
	}

	public void update(CountryEntity countryEntity) {
		getSession().update(countryEntity);
	}
	public boolean delete(long id)
	{
		CountryEntity countryEntity = findById(id);
		if (countryEntity == null)
			return false;
		else
		{
			getSession().delete(countryEntity);
			return true;
		}
	}




}
