package com.owners.gravitas.business.builder.request;

import org.springframework.stereotype.Component;
import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.domain.entity.ContactStatus;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.util.JsonUtil;

/**
 * The class ContactStatusBuilder
 * @author sandeepsoni
 *
 */

@Component( "contactStatusBuilder" )
public class ContactStatusBuilder extends AbstractBuilder< LeadRequest, ContactStatus > {

	@Override
	public ContactStatus convertTo(LeadRequest source, ContactStatus destination) {
		ContactStatus contactStatus = destination;
		if(source != null) {
			if(contactStatus == null) {
				contactStatus = new ContactStatus();
			}
			contactStatus.setEmail(source.getEmail()); 
			contactStatus.setLeadSource(source.getSource());
			contactStatus.setLeadType(source.getLeadType());
			contactStatus.setStatus(Constants.FAILED);
			contactStatus.setCreatedBy(Constants.GRAVITAS);
			contactStatus.setRetryCount(0);
			contactStatus.setRequestJson(JsonUtil.toJson(source) );
			
		}
		return contactStatus;
	}

	@Override
	public LeadRequest convertFrom(ContactStatus source, LeadRequest destination) {
		 throw new UnsupportedOperationException();
	}
	
	

}
