package com.vs.ConsignmentTrackingSystem.db.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.vs.ConsignmentTrackingSystem.utilities.Constants;
/**
 * 
 * @author bharti
 * Class that maps to job database table
 */
@Entity
@Table(name = Constants.JOB_TABLE_NAME)
public class JobEntity implements Serializable {
	@Id
	@Column(name = Constants.JOB_ID)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "job_no", unique = true)
	private int jobNo;

	@Column(name = "exporter_id")
	private long exporterId;

	@Column(name = "exporter_name")
	private String exporterName;

	@Column(name = "consignee")
	private String consignee;

	@Column(name = "invoice_no")
	private String invoiceNo;

	@Column(name = "date")
	private Date date;

	@Column(name = "quantity")
	private String quantity;

	@Column(name = "destination")
	private String destination;

	@Column(name = "mode")
	private String mode;

	@Column(name = "forwarder")
	private String forwarder;

	@Column(name = "pmv")
	private String pmv;

	@Column(name = "buyer_name")
	private String buyerName;
	
	@Column(name = "job_status")
	private String jobStatus;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "commodity")
	private String commodity;
	
	@Column(name = "category_id")
	private long categoryId;
	
	@Column(name = "category_name")
	private String categoryName;

	@Column(name = "agent")
	private String agent;
	
	@Column(name = "job_created_by")
	private String jobCreatedBy;
	
	@Column(name="country")
	private String country;
	
	@Column(name="scroll_number")
	private String scrollNumber;
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getScrollNumber() {
		return scrollNumber;
	}

	public void setScrollNumber(String i) {
		this.scrollNumber = i;
	}

	public String getJobCreatedBy() {
		return jobCreatedBy;
	}

	public void setJobCreatedBy(String jobCreatedBy) {
		this.jobCreatedBy = jobCreatedBy;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
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

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPmv() {
		return pmv;
	}

	public void setPmv(String pmv) {
		this.pmv = pmv;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getForwarder() {
		return forwarder;
	}

	public void setForwarder(String forwarder) {
		this.forwarder = forwarder;
	}

	
}
