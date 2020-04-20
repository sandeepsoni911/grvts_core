package com.owners.gravitas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.owners.gravitas.business.ZipCoverageBusinessService;
import com.owners.gravitas.dto.request.AgentCoverageRequest;
import com.owners.gravitas.dto.request.ZipCodeDistanceRequest;
import com.owners.gravitas.dto.response.BaseResponse;

@RestController
public class ZipCoverageController extends BaseController {

	@Autowired
	private ZipCoverageBusinessService zipCoverageBusinessService;

	@RequestMapping(value = "/zipDistance", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse excludeZipCodes(@RequestBody ZipCodeDistanceRequest zipCodeDistanceRequest) {
		return zipCoverageBusinessService.excludeZipCodes(zipCodeDistanceRequest);
	}

	@RequestMapping(value = "/agentCoverage", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse updateAgentServlableZipCode(@RequestBody AgentCoverageRequest agentCoverageRequest) {
		return zipCoverageBusinessService.updateAgentServlableZipCode(agentCoverageRequest);
	}

}
