package com.vs.ConsignmentTrackingSystem.models.Request;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateJobRequest {

	@JsonProperty
	private int jobNo;

	@JsonProperty
	private long exporterId;

	@JsonProperty
	private String exporterName;

	@JsonProperty
	private String consignee;

	@JsonProperty
	private String invoiceNo;

	@JsonProperty
	private Date date;

	@JsonProperty
	private String quantity;

	@JsonProperty
	private String destination;

	@JsonProperty
	private String mode;

	@JsonProperty
	private String forwarder;

	@JsonProperty
	private String pmv;

	@JsonProperty
	private String buyerName;
	
	@JsonProperty
	private String remark;
	
	@JsonProperty
	private String commodity;
	
	@JsonProperty
	private long categoryId;
	
	@JsonProperty
	private String categoryName;
	
	@JsonProperty
	private String agent;

	@JsonProperty
	private String jobCreatedBy;
	
	@JsonProperty
	private String country;
	
	@JsonProperty
	private String scrollNumber;
	
	public String getScrollNumber() {
		return scrollNumber;
	}

	public void setScrollNumber(String scrollNumber) {
		this.scrollNumber = scrollNumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
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

}
