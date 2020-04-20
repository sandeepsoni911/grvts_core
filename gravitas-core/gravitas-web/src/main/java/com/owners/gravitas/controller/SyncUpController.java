package com.owners.gravitas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.owners.gravitas.annotation.PerformanceLog;
import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.dto.request.SyncUpRequest;
import com.owners.gravitas.dto.response.BaseResponse;

/**
 * The Class SyncUpController.
 */
@RestController
public class SyncUpController extends BaseController {

	/** The Constant logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SyncUpController.class);

	/** instance of {@link LeadBusinessService}. */
	@Autowired
	private LeadBusinessService buyerBusinessService;

	/**
	 * Sync up Salesforce & DB Data.
	 *
	 * @param request
	 *            the request
	 * @return the new sync-up response
	 */
	@PerformanceLog
	@RequestMapping(value = "/lead/sync-Up", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse createGenericLead(@RequestBody final SyncUpRequest request) {
		LOGGER.debug("Sync up activity has been started for column:{}", request.getAttribute());
		buyerBusinessService.syncUpLead(request, false);
		BaseResponse baseResponse = new BaseResponse();
		return baseResponse;
	}

}
