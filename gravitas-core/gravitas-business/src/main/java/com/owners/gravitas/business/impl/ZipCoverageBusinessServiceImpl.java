package com.owners.gravitas.business.impl;

import static com.owners.gravitas.dto.response.BaseResponse.Status.SUCCESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.business.ZipCoverageBusinessService;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.dto.request.AgentCoverageRequest;
import com.owners.gravitas.dto.request.ZipCodeDistanceRequest;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.service.AgentCoverageService;
import com.owners.gravitas.service.AgentDetailsService;
import com.owners.gravitas.service.ZipDistanceService;

@Service("zipCoverageBusinessService")
public class ZipCoverageBusinessServiceImpl implements ZipCoverageBusinessService {

	@Autowired
	private ZipDistanceService zipDistanceService;

	@Autowired
	private AgentCoverageService agentCoverageService;

	@Autowired
	private AgentDetailsService agentDetailsService;

	@Override
	public BaseResponse excludeZipCodes(final ZipCodeDistanceRequest zipCodeDistanceRequest) {
		zipCodeDistanceRequest.getZipCodes().forEach(
				zipCode -> zipDistanceService.updateZipDistanceExcluded(zipCode, zipCodeDistanceRequest.isExclude()));
		//update the servable column for all existing agents
		zipCodeDistanceRequest.getZipCodes().forEach(
				zipCode -> agentCoverageService.updateServableZipCodeForAllAgents( zipCode, !zipCodeDistanceRequest.isExclude() ));
		return new BaseResponse(SUCCESS, "Zip Code Distance updated sucessfully!!");

	}

	@Override
	public BaseResponse updateAgentServlableZipCode(final AgentCoverageRequest agentCoverageRequest) {
		final AgentDetails agentDetails = agentDetailsService.findAgentByEmail(agentCoverageRequest.getEmail());
		agentCoverageRequest.getZipCodes()
				.forEach(zipCode -> agentCoverageService.updateServableZipCode( agentDetails, zipCode, agentCoverageRequest.isServable() ));
		return new BaseResponse(SUCCESS, "Agent Coverage updated sucessfully!!");
	}

}