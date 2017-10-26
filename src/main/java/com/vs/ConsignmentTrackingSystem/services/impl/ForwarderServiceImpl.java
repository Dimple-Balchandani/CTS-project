package com.vs.ConsignmentTrackingSystem.services.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.vs.ConsignmentTrackingSystem.db.entities.ForwarderEntity;
import com.vs.ConsignmentTrackingSystem.db.entities.ForwarderEntityDAO;
import com.vs.ConsignmentTrackingSystem.models.Request.ForwarderRequest;
import com.vs.ConsignmentTrackingSystem.models.Response.GetResponse;
import com.vs.ConsignmentTrackingSystem.services.ForwarderService;
import com.vs.ConsignmentTrackingSystem.utilities.Constants;
@Component
public class ForwarderServiceImpl implements ForwarderService
{
	@Autowired
	private ForwarderEntityDAO forwarderEntityDAO;

	public GetResponse createForwarder(ForwarderRequest forwarderRequest) {
		
		GetResponse getResponse = new GetResponse();
		
		try{
			ForwarderEntity forwarderEntity = new ForwarderEntity();
			forwarderEntity.setForwarderName(forwarderRequest.getName());
			forwarderEntityDAO.save(forwarderEntity);
			getResponse.setMessage(Constants.SUCCESS_RESPONSE);
		}
		catch(Exception e){
			getResponse.setMessage(Constants.FAILURE_RESONSE);
		}
		return getResponse;
	}

	public GetResponse getAllForwarder() {
		List<ForwarderEntity> forwarderEntity = forwarderEntityDAO.getForwarder();
		GetResponse getResponse = new GetResponse();
		getResponse.setData(forwarderEntity);
		getResponse.setMessage(Constants.SUCCESS_RESPONSE);
		return getResponse;
	}

	public GetResponse updateForwarder(long id, ForwarderRequest forwarderRequest) {
		ForwarderEntity forwarderEntity = forwarderEntityDAO.findById(id);
		GetResponse getResponse = new GetResponse();
			if (forwarderRequest.getName() != null)
				forwarderEntity.setForwarderName(forwarderRequest.getName());
			forwarderEntityDAO.update(forwarderEntity);
			getResponse.setMessage(Constants.SUCCESS_RESPONSE);
		return getResponse;
	}

	public GetResponse deleteForwarder(long id) {
		boolean isDeleted = forwarderEntityDAO.delete(id);
		GetResponse getResponse = new GetResponse();
		if (isDeleted)
			getResponse.setMessage(Constants.SUCCESS_RESPONSE);
		else
			getResponse.setMessage(Constants.FAILURE_RESONSE);
		return getResponse;
	}
}
