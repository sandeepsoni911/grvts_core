package com.owners.gravitas.business.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.dto.request.OclLeadRequest;
import com.owners.gravitas.enums.LeadRequestType;
import com.owners.gravitas.enums.LeadSourceName;
import com.owners.gravitas.service.ContactEntityService;

@Component( "oclLeadDetailsBuilder" )
public class OclLeadDetailsBuilder extends AbstractBuilder< OclLeadRequest, LeadSource > {

    /** The contact service V 1. */
    @Autowired
    private ContactEntityService contactServiceV1;
    
    /** The lead status. */
    @Value( "${ocl.lead.source.url}" )
    private String oclLeadSourceUrl;
    
    @Override
    public LeadSource convertTo( final OclLeadRequest source, final LeadSource destination ) {
        LeadSource leadSource = destination;
        if (source != null) {
            if (leadSource == null) {
                leadSource = new LeadSource();
            }
        }
        final Contact contact = contactServiceV1.getOpportunityByCrmId( source.getCrmId() );
        
        if(contact.getFirstName() != null) {
            leadSource.setFirstName(contact.getFirstName());
        }
        leadSource.setEmail( contact.getEmail() );
        leadSource.setLastName( contact.getLastName() );
        leadSource.setLeadSourceUrl( oclLeadSourceUrl );
        //leadSource.setLeadType( RecordType.OWNERS_COM_LOANS.toString());
        leadSource.setState( contact.getState() );
        leadSource.setPhone( contact.getPhone() );
        leadSource.setRequestType( LeadRequestType.OTHER.toString() );
        leadSource.setSource( LeadSourceName.OWNERS_AGENT_APP.getSource() );
        return leadSource;
    }

    @Override
    public OclLeadRequest convertFrom( final LeadSource source, final OclLeadRequest destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }
}
