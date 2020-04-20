package com.owners.gravitas.business.builder.request;

import static com.owners.gravitas.enums.CRMObject.AGENT;
import static com.owners.gravitas.enums.RecordType.OWNERS_COM;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.dto.crm.request.CRMAgentRequest;
import com.owners.gravitas.dto.request.AgentOnboardRequest;
import com.owners.gravitas.service.RecordTypeService;
import com.owners.gravitas.util.DateUtil;

/**
 * The Class CRMAgentRequestBuilder.
 *
 * @author amits
 */
@Component( "crmAgentRequestBuilder" )
public class CRMAgentRequestBuilder extends AbstractBuilder< AgentOnboardRequest, CRMAgentRequest > {

    /** The record type service. */
    @Autowired
    private RecordTypeService recordTypeService;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public CRMAgentRequest convertTo( AgentOnboardRequest source, CRMAgentRequest destination ) {
        CRMAgentRequest crmAgent = destination;
        if (source != null) {
            if (crmAgent == null) {
                crmAgent = new CRMAgentRequest();
            }
            crmAgent.setFullName( getFullName( source.getFirstName(), source.getLastName() ) );
            crmAgent.setEmail( source.getEmail() );
            crmAgent.setPhone( source.getPhone() );
            crmAgent.setState( source.getAddress() != null ? source.getAddress().getState() : null );
            crmAgent.setAddress1( source.getAddress().getAddress1() );
            crmAgent.setAddress2( source.getAddress().getAddress2() );
            crmAgent.setCity( source.getAddress().getCity() );
            crmAgent.setHomeZip( source.getAddress().getZip() );
            crmAgent.setNotes( source.getNotes() );
            crmAgent.setStatus( source.getStatus().toUpperCase() );
            crmAgent.setDrivingRadius( source.getDrivingRadius() );
            crmAgent.setRecordTypeId(
                    recordTypeService.getRecordTypeIdByName( OWNERS_COM.getType(), AGENT.getName() ) );
            crmAgent.setAgentType( source.getType() );
            crmAgent.setMobileCarrier( source.getMobileCarrier() );
            crmAgent.setStartingDate( DateUtil.toDate( source.getAgentStartingDate(), DateUtil.DEFAULT_OWNERS_DATE_PATTERN ) );
            crmAgent.setLicense( source.getLicense() );
        }
        return crmAgent;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public AgentOnboardRequest convertFrom( CRMAgentRequest source, AgentOnboardRequest destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }

    /**
     * Gets the full name.
     *
     * @param firstName
     *            the first name
     * @param lastName
     *            the last name
     * @return the full name
     */
    private String getFullName( final String firstName, final String lastName ) {
        return StringUtils.isBlank( firstName ) ? lastName : firstName + " " + lastName;
    }

}
