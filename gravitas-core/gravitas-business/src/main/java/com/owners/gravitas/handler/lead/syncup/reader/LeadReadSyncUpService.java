package com.owners.gravitas.handler.lead.syncup.reader;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.dto.request.SyncUpRequest;
import com.owners.gravitas.enums.LeadSyncUpAttributes;
import com.owners.gravitas.service.ContactEntityService;

/**
 * The Interface LeadSyncUpService.
 *
 * @author kushwaja
 */
public abstract class LeadReadSyncUpService {

	/** The contact service V 1. */
	@Autowired
	protected ContactEntityService contactServiceV1;
	
	/**
	 * @param request
	 */
	protected List<Contact> syncUpAttribute(SyncUpRequest request) {
		List<Contact> response = null;
		if (request.getAttribute().equalsIgnoreCase(LeadSyncUpAttributes.UUID.getAttribute()) && request.getDates() != null) {
			response = contactServiceV1.findLeadsByOwnersComIdIsNull(request.getFromDate(), request.getToDate());
		} else if (request.getAttribute().equalsIgnoreCase(LeadSyncUpAttributes.UUID.getAttribute()) && request.getDates() == null){
			response = contactServiceV1.findAllLeadsByOwnersComIdIsNull();
		}
		return response;
	}
}
