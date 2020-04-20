package com.owners.gravitas.handler.lead.syncup.processor;

import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.dto.response.BaseResponse;

/**
 * The Interface LeadSyncUpService.
 *
 * @author kushwaja
 */
public interface LeadProcessSyncUpService {

	/**
	 * @param request
	 */
	public BaseResponse processSyncUpAttribute(Contact contact);

}
