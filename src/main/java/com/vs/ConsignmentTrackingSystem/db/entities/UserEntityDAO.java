package com.vs.ConsignmentTrackingSystem.db.entities;

import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.logging.Logger;
import com.vs.ConsignmentTrackingSystem.models.Request.LoginRequest;

@Repository
@Transactional
public class UserEntityDAO {

	private final static Logger LOGGER = Logger.getLogger(UserEntityDAO.class.getName());

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(UserEntity user) {
		getSession().save(user);
	}

	public UserEntity findByUsername(String name, long loginTime) {
		Query query = getSession().createQuery("from UserEntity where username = :name and loginTime = :loginTime");
		query.setParameter("name", name);
		query.setParameter("loginTime", loginTime);
		return (UserEntity) query.uniqueResult();

	}

	public UserEntity findIfUserExists(LoginRequest logindetails) {

		Query query = getSession().createQuery("from UserEntity where username = :name and password = :pass");
		query.setParameter("name", logindetails.getUsername());
		query.setParameter("pass", logindetails.getPassword());
		UserEntity userEntity = (UserEntity) query.uniqueResult();
		return userEntity;
	}

	public List<UserEntity> getUsers() {
		Query query = getSession().createQuery("from UserEntity where role = :role");
		query.setParameter("role", "User");
		return query.list();
	}

	public List<UserEntity> getAdmin() {
		Query query = getSession().createQuery("from UserEntity where role = :role");
		query.setParameter("role", "Admin");
		return query.list();
	}

	public List<UserEntity> getClients() {
		Query query = getSession().createQuery("from UserEntity where role = :role");
		query.setParameter("role", "Client");
		return query.list();
	}

	public UserEntity findById(long id) {
		Query query = getSession().createQuery("from UserEntity where id = :id");
		query.setParameter("id", id);
		return (UserEntity) query.uniqueResult();
	}
	
	public UserEntity findByType(String type) {
		Query query = getSession().createQuery("from UserEntity where userType = :type");
		query.setParameter("type", type);
		return (UserEntity) query.uniqueResult();
	}
	
	public boolean delete(long id) {
		UserEntity userEntity = findById(id);
		if (userEntity == null)
			return false;
		else {
			getSession().delete(userEntity);
			return true;
		}

	}

	public List<UserEntity> getAllData() {
		Query query = getSession().createQuery("from UserEntity where role = 'User' OR role = 'Admin' OR role = 'Supervisior' order by userType");
		return query.list();
	}

	public List<UserEntity> getUserByRole(String role) {

		Query query = getSession().createQuery("from UserEntity where role = :role order by companyName");
		query.setParameter("role", role);
		return query.list();
	}

	public void grantAccess(String idList) {
		String queryString = "update UserEntity set isReportAccess = :isReportAccess where role =:role and id IN (";
		String[] ids = idList.split(",");

		for (int i = 0; i < ids.length; i++) {
			if (i == ids.length - 1)
				queryString += Long.parseLong(ids[i]);
			else
				queryString += Long.parseLong(ids[i]) + ",";
		}
		queryString += ")";
		Query query = getSession().createQuery(queryString);
		query.setParameter("isReportAccess", true);
		query.setParameter("role", "User");
		query.executeUpdate();

		queryString = "update UserEntity set isReportAccess = :isReportAccess where role =:role and id NOT IN (";
		String[] ids1 = idList.split(",");

		for (int i = 0; i < ids1.length; i++) {
			if (i == ids1.length - 1)
				queryString += Long.parseLong(ids1[i]);
			else
				queryString += Long.parseLong(ids1[i]) + ",";
		}
		queryString += ")";
		query = getSession().createQuery(queryString);
		query.setParameter("isReportAccess", false);
		query.setParameter("role", "User");
		query.executeUpdate();
	}

	public void denyAccess() {
		String queryString = "update UserEntity set isReportAccess = :isReportAccess where role =:role";
		Query query = getSession().createQuery(queryString);
		query.setParameter("isReportAccess", false);
		query.setParameter("role", "User");
		query.executeUpdate();

	}

	public void customizeReport(String idList, String customvalue) {
		String queryString = "update UserEntity set reportFields =:customvalue where id =:id";
		long id = Long.parseLong(idList);
		Query query = getSession().createQuery(queryString);
		query.setParameter("customvalue", customvalue);
		query.setParameter("id", id);
		query.executeUpdate();
	}

	public void update(UserEntity userEntity) {
		getSession().update(userEntity);

	}
	
	public UserEntity findByDetails(String username, String password) {
		Query query = getSession().createQuery("from UserEntity where username = :username and password = :password");
		query.setParameter("username", username);
		query.setParameter("password", password);
		return (UserEntity) query.uniqueResult();
	}
	
	public int calculateUserCount() {
		Query query = getSession().createQuery("from UserEntity where role in ('User')");
		return query.list().size();
	}
}
