package com.owners.gravitas.handler.lead.syncup.processor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.dto.crm.response.CRMLeadResponse;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.handler.lead.syncup.processor.LeadProcessSyncUpService;
import com.owners.gravitas.service.LeadService;

/**
 * @author kushwaja
 */
@Service
public class UuidProcessSyncUpServiceImpl implements LeadProcessSyncUpService {

	/** The Constant logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UuidProcessSyncUpServiceImpl.class);

	@Autowired
	private LeadService leadService;

	@Override
	public BaseResponse processSyncUpAttribute(Contact contact) {
		String crmId = contact.getCrmId();
		try {
			LOGGER.info("Trying to get  lead with CRM id :{}", crmId);
			CRMLeadResponse crmLeadResponse = leadService.getLead(crmId);
			String ownersId = crmLeadResponse.getUuid();
			contact.setOwnersComId(ownersId);
			LOGGER.info("Lead with CRM id :{} retrived successfully and uuid is :{}", crmId, ownersId);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error while getting lead with CRM id :{},message :{}",crmId,e.getMessage());
			return new BaseResponse(Status.FAILURE, "Lead Get Operation failed");
		}
		return new BaseResponse();
	}
}
