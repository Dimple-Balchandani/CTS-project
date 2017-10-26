package com.vs.ConsignmentTrackingSystem.db.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.vs.ConsignmentTrackingSystem.utilities.Constants;
/**
 * 
 * @author bharti
 * Class that maps to user_job_task database table 
 */
@Entity
@Table(name = Constants.USERJOBTASK_TABLE_NAME)
public class UserJobTaskEntity implements Serializable {

	@Id
	@Column(name = Constants.USER_TASK_ID)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "job_no")
	private int jobNo;

	@Column(name = "exporter_id")
	private long exporterId;

	@Column(name = "exporter_name")
	private String exporterName;

	@Column(name = "task_id")
	private long taskId;

	@Column(name = "task_title")
	private String taskTitle;

	@Column(name = "user_id")
	private long userId;

	@Column(name = "user_type")
	private String userType;
	
	@Column(name = "task_date")
	private Date taskDate;

	@Column(name = "task_text")
	private String taskText;

	@Column(name = "status")
	private String status;

	@Column(name = "assigned_date")
	private Date assignedDate;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "duration")
	private int duration;
	
	@Column(name = "task_pending_from")
	private long taskPendingFrom;

	@Column(name = "expected_date")
	private Date expectedDate;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "task_type")
	private String taskType;
	
	public String getUserRemark() {
		return userRemark;
	}

	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}

	public String getAdminRemark() {
		return adminRemark;
	}

	public void setAdminRemark(String adminRemark) {
		this.adminRemark = adminRemark;
	}

	@Column(name = "category_id")
	private long categoryId;
	
	@Column(name = "category_name")
	private String categoryName;
	
	@Column(name = "user_remark")
	private String userRemark;
	
	@Column(name = "admin_remark")
	private String adminRemark;
	
	
	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getJobNo() {
		return jobNo;
	}

	public void setJobNo(int jobNo) {
		this.jobNo = jobNo;
	}

	public long getExporterId() {
		return exporterId;
	}

	public void setExporterId(long exporterId) {
		this.exporterId = exporterId;
	}

	public String getExporterName() {
		return exporterName;
	}

	public void setExporterName(String exporterName) {
		this.exporterName = exporterName;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}
	
	public String getTaskText() {
		return taskText;
	}

	public void setTaskText(String taskText) {
		this.taskText = taskText;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Date getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Date getExpectedDate() {
		return expectedDate;
	}

	public void setExpectedDate(Date expectedDate) {
		this.expectedDate = expectedDate;
	}

	public long getTaskPendingFrom() {
		return taskPendingFrom;
	}

	public void setTaskPendingFrom(long taskPendingFrom) {
		this.taskPendingFrom = taskPendingFrom;
	}

}
