package com.vs.ConsignmentTrackingSystem.models.Response;

import java.sql.Date;
import java.util.List;

import com.vs.ConsignmentTrackingSystem.db.entities.UserJobTaskEntity;

public class GenerateReportResponse {
	private long id;

	private int jobNo;

	private long exporterId;

	private String exporterName;

	private String consignee;

	private String invoiceNo;

	private Date date;

	private String quantity;

	private String destination;

	private String mode;

	private String forwarder;

	private String pmv;

	private String buyerName;
	
	private String jobStatus;
	
	private String remark;
	
	private String commodity;

	private List<UserJobTaskEntity> userJobTaskEntity;

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

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public List<UserJobTaskEntity> getUserJobTaskEntity() {
		return userJobTaskEntity;
	}

	public void setUserJobTaskEntity(List<UserJobTaskEntity> userJobTaskEntity) {
		this.userJobTaskEntity = userJobTaskEntity;
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
