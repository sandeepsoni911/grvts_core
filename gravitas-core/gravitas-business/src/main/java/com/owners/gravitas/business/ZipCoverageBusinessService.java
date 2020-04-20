package com.owners.gravitas.business;

import com.owners.gravitas.dto.request.AgentCoverageRequest;
import com.owners.gravitas.dto.request.ZipCodeDistanceRequest;
import com.owners.gravitas.dto.response.BaseResponse;

/**
 * The Interface AgentBusinessService.
 *
 * @author lavjeetk
 */
public interface ZipCoverageBusinessService {

	public BaseResponse excludeZipCodes(ZipCodeDistanceRequest zipCodeDistanceRequest);

	public BaseResponse updateAgentServlableZipCode(AgentCoverageRequest agentCoverageRequest);

}