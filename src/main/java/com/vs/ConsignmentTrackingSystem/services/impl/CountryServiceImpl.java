package com.vs.ConsignmentTrackingSystem.services.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.vs.ConsignmentTrackingSystem.db.entities.CountryEntity;
import com.vs.ConsignmentTrackingSystem.db.entities.CountryEntityDAO;
import com.vs.ConsignmentTrackingSystem.models.Request.CountryRequest;
import com.vs.ConsignmentTrackingSystem.models.Response.GetResponse;
import com.vs.ConsignmentTrackingSystem.services.CountryService;
import com.vs.ConsignmentTrackingSystem.utilities.Constants;
@Component
public class CountryServiceImpl implements CountryService 
{
	@Autowired
	private CountryEntityDAO countryEntityDAO;
	
	public GetResponse createCountry(CountryRequest countryRequest){
		GetResponse getResponse=new GetResponse();
		try{
			CountryEntity countryEntity=new CountryEntity();
			countryEntity.setCountryName(countryRequest.getCountryName());
			countryEntityDAO.save(countryEntity);
			getResponse.setMessage(Constants.SUCCESS_RESPONSE);
		}
		catch(Exception e){
			getResponse.setMessage(Constants.FAILURE_RESONSE);
		}
		return getResponse;
	}
	
	public GetResponse updateCountry(long id, CountryRequest countryReq)
	{
		CountryEntity countryEntity = countryEntityDAO.findById(id);
		GetResponse getResponse = new GetResponse();
		if (countryEntity == null)
			getResponse.setMessage(Constants.FAILURE_RESONSE);
		else {
			if (countryReq.getCountryName() != null)
				countryEntity.setCountryName(countryReq.getCountryName());
			countryEntityDAO.update(countryEntity);
			getResponse.setMessage(Constants.SUCCESS_RESPONSE);
		}
	return getResponse;
    }
	
	public GetResponse deleteCountry(long id) {
		boolean isDeleted = countryEntityDAO.delete(id);
		GetResponse getResponse = new GetResponse();
		if (isDeleted)
			getResponse.setMessage(Constants.SUCCESS_RESPONSE);
		else
			getResponse.setMessage(Constants.FAILURE_RESONSE);
		return getResponse;
	}

	public GetResponse getAllCountries() {
		List<CountryEntity> country = countryEntityDAO.getCountries();
		GetResponse getResponse = new GetResponse();
		getResponse.setData(country);
		getResponse.setMessage(Constants.SUCCESS_RESPONSE);
		return getResponse;
	}
}
	


