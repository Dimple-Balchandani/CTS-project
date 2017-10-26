package com.vs.ConsignmentTrackingSystem.db.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.vs.ConsignmentTrackingSystem.utilities.Constants;
/**
 * 
 * @author bharti
 * Class that maps to task database table
 */
@Entity
@Table(name = Constants.TASK_TABLE_NAME)
public class TaskEntity implements Serializable {

	@Id
	@Column(name = Constants.TASK_AUTO_ID)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	@Column(name = "task_id")
	private long taskId;

	@NotNull
	@Column(name = "task_title")
	private String taskTitle;

	@NotNull
	@Column(name = "duration")
	private int duration;

	@Column(name = "user_type")
	private String userType;
	
	@Column(name = "task_type")
	private String taskType;
	
	@Column(name = "task_pending_from")
	private long taskPendingFrom;


	public TaskEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TaskEntity(long id, long taskId, String taskTitle, int duration, String userType, String taskType,
		long taskPendingFrom) {
	super();
	this.id = id;
	this.taskId = taskId;
	this.taskTitle = taskTitle;
	this.duration = duration;
	this.userType = userType;
	this.taskType = taskType;
	this.taskPendingFrom = taskPendingFrom;
}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public long getTaskPendingFrom() {
		return taskPendingFrom;
	}

	public void setTaskPendingFrom(long taskPendingFrom) {
		this.taskPendingFrom = taskPendingFrom;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "Id: " + this.id + "\nTaskTitle: " + this.taskTitle + "\nDuration: " + this.duration;
	}

}
