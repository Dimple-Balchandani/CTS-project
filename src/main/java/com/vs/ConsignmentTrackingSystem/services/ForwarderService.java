package com.vs.ConsignmentTrackingSystem.services;

import org.springframework.stereotype.Service;
import com.vs.ConsignmentTrackingSystem.models.Request.ForwarderRequest;
import com.vs.ConsignmentTrackingSystem.models.Response.GetResponse;
@Service
public interface ForwarderService {
	public GetResponse createForwarder(ForwarderRequest forwarderReq);

	public GetResponse getAllForwarder();

	public GetResponse updateForwarder(long id, ForwarderRequest forwarderReq);

	public GetResponse deleteForwarder(long id);
}
