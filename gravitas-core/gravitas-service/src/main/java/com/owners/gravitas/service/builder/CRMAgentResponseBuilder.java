package com.owners.gravitas.service.builder;

import static com.owners.gravitas.util.StringUtils.convertObjectToString;

import java.util.Map;
import org.springframework.stereotype.Component;

import com.owners.gravitas.dto.crm.response.CRMAgentResponse;
import com.owners.gravitas.util.DateUtil;

/**
 * The Class CRMAgentResponseBuilder.
 *
 * @author madhav
 */
@Component( "CRMAgentResponseBuilder" )
public class CRMAgentResponseBuilder extends AbstractBuilder< Map< String, Object >, CRMAgentResponse > {

    /* (non-Javadoc)
     * @see com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.Object, java.lang.Object)
     */
    @Override
    public CRMAgentResponse convertTo( Map< String, Object > source, CRMAgentResponse destination ) {
        CRMAgentResponse response = destination;
        if (source != null) {
            if (response == null) {
                response = new CRMAgentResponse();
            }
            response.setId( convertObjectToString( source.get( "Id" ) ) );
            response.setName( convertObjectToString( source.get( "Name" ) ) );
            response.setEmail( convertObjectToString( source.get( "Email__c" ) ) );
            response.setAddress1( convertObjectToString( source.get( "Address1__c" ) ) );
            response.setAddress2( convertObjectToString( source.get( "Address2__c" ) ) );
            response.setCity( convertObjectToString( source.get( "City__c" ) ) );
            response.setState( convertObjectToString( source.get( "State__c" ) ) );
            response.setHomeZip( convertObjectToString( source.get( "Zip_Code__c" ) ) );
            response.setLicense( convertObjectToString( source.get( "License_Number__c" ) ) );
            response.setAvailable( Boolean.valueOf( convertObjectToString( source.get( "Active__c" ) ) ) );
            response.setMobileCarrier( convertObjectToString( source.get( "Agent_Mobile_Carrier__c" ) ) );
            response.setFieldAgent(  Boolean.valueOf( convertObjectToString( source.get( "Field_Agent__c" ) ) ) );
            response.setStatus( convertObjectToString( source.get( "Status__c" ) ) );
            response.setPhone( convertObjectToString( source.get( "Phone__c" ) ) );
            response.setStartingDate( DateUtil.toDate( convertObjectToString( source.get( "Agent_App_Starting_Date__c" ) ) ) );
      }

        return response;
    }

    /* (non-Javadoc)
     *  Method not support
     *
     */
    @Override
    public Map< String, Object > convertFrom( CRMAgentResponse source, Map< String, Object > destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }

}

