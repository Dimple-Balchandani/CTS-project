package com.vs.ConsignmentTrackingSystem.services;
import org.springframework.stereotype.Service;
import com.vs.ConsignmentTrackingSystem.models.Request.CountryRequest;
import com.vs.ConsignmentTrackingSystem.models.Response.GetResponse;

@Service
public interface CountryService {
	
	public GetResponse createCountry(CountryRequest countryRequest);
	
	public GetResponse getAllCountries();
	
	public GetResponse updateCountry(long id, CountryRequest countryRequest);
	
	public GetResponse deleteCountry(long id);
}
