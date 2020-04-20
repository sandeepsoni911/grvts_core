/**
 *
 */
package com.owners.gravitas.business.builder.response;

import static com.owners.gravitas.constants.CRMQuery.GET_CRM_AGENT_DETAILS_BY_EMAIL;
import static com.owners.gravitas.constants.Constants.EMAIL;
import static com.owners.gravitas.util.ObjectUtil.isNull;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.api.client.util.ArrayMap;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.UserName;
import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.dto.Agent;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.UserAddress;
import com.owners.gravitas.dto.UserDetails;
import com.owners.gravitas.dto.response.AgentDetailsResponse;
import com.owners.gravitas.service.AgentDetailsService;
import com.owners.gravitas.service.CRMQueryService;

/**
 * The Class AgentDetailsBuilder.
 *
 * @author harshads
 */
@Component
public class AgentDetailsResponseBuilder extends AbstractBuilder< UserDetails, AgentDetailsResponse > {

    /** The agent details service. */
    @Autowired
    private AgentDetailsService agentDetailsService;

    /** The crm query service. */
    @Autowired
    private CRMQueryService crmQueryService;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public AgentDetailsResponse convertTo( final UserDetails source, final AgentDetailsResponse destination ) {
        AgentDetailsResponse agentDetailsResponse = destination;
        if (source != null && source.getUser() != null) {
            if (agentDetailsResponse == null) {
                agentDetailsResponse = new AgentDetailsResponse();
            }
            final Agent agent = ( null == agentDetailsResponse.getDetails() ) ? new Agent()
                    : agentDetailsResponse.getDetails();
            final User user = source.getUser();
            final UserName name = user.getName();
            agent.setFirstName( name.getGivenName() );
            agent.setLastName( name.getFamilyName() );
            agent.setEmail( user.getPrimaryEmail() );

            agent.setPersonalEmail( setAgentPersonalEmail( user ) );
            agent.setPhone( setAgentPhones( user ) );
            agent.setBiodata( setCustomSchemas( user ) );
            agent.setAddress( setAgentAddresses( user ) );

            agent.setPhotoUrl( isNull( user.getThumbnailPhotoUrl() ) );
            agent.setCoverageArea( agentDetailsService.getAgentCoverageAreas( user.getPrimaryEmail() ) );

            final Map< String, Object > crmAgentDetails = getAgentDetails( user.getPrimaryEmail() );
            agent.setNotes( isNull( crmAgentDetails.get( "Notes__c" ) ) );
            final AgentDetails dbAgentDetails = agentDetailsService.findAgentByEmail( user.getPrimaryEmail() );
            if (dbAgentDetails != null) {
                agent.setAgentStartingDate( dbAgentDetails.getStartingOn().toString() );
                agent.setMobileCarrier( dbAgentDetails.getMobileCarrier() );
                agent.setDrivingRadius( String.valueOf( dbAgentDetails.getDrivingRadius() ) );
                agent.setManagingBrokerId( dbAgentDetails.getManagingBroker().getEmail() );
                agent.setStatus( isNull( dbAgentDetails.getUser().getStatus() ) );
                agent.setLanguage( dbAgentDetails.getLanguage() );
                agent.setLicense( dbAgentDetails.getLicense() );
            }
            agent.setEncodedPhotoData( source.getUserPhoto() );
            agentDetailsResponse.setDetails( agent );
        }
        return agentDetailsResponse;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public UserDetails convertFrom( AgentDetailsResponse source, UserDetails destination ) {
        throw new UnsupportedOperationException( "convertFrom operation is not supported" );
    }

    /**
     * Gets the agent details.
     *
     * @param email
     *            the email
     * @return the agent details
     */
    private Map< String, Object > getAgentDetails( final String email ) {
        final QueryParams params = new QueryParams();
        params.add( EMAIL, email );
        return crmQueryService.findOne( GET_CRM_AGENT_DETAILS_BY_EMAIL, params );
    }

    /**
     * Sets the agent phones.
     *
     * @param source
     *            the source
     * @return the string
     */
    private String setAgentPhones( final User source ) {
        String phone = "";
        final List< ArrayMap< String, Object > > phones = ( List< ArrayMap< String, Object > > ) source.getPhones();
        if (phones != null) {
            for ( ArrayMap< String, Object > phoneMap : phones ) {
                if ("work".equals( phoneMap.get( "type" ) )) {
                    phone = isNull( ( phoneMap.get( "value" ) ) );
                    break;
                }
            }
        }
        return phone;
    }

    /**
     * Sets the custom schemas.
     *
     * @param source
     *            the source
     * @return the string
     */
    private String setCustomSchemas( final User source ) {
        String bioData = "";
        final Map< String, Map< String, Object > > customSchemas = source.getCustomSchemas();
        if (null != customSchemas) {
            final Map< String, Object > otherFieldsSchema = customSchemas.get( "otherFields" );
            if (null != otherFieldsSchema) {
                bioData = isNull( otherFieldsSchema.get( "bioData" ) );
            }
        }
        return bioData;
    }

    /**
     * Sets the agent addresses.
     *
     * @param source
     *            the source
     * @return the user address
     */
    private UserAddress setAgentAddresses( final User source ) {
        final List< ArrayMap< String, Object > > addresses = ( List< ArrayMap< String, Object > > ) source
                .getAddresses();
        UserAddress userAddress = null;
        if (addresses != null) {
            for ( ArrayMap< String, Object > addressMap : addresses ) {
                if ("home".equals( addressMap.get( "type" ) )) {
                    userAddress = new UserAddress();
                    userAddress.setAddress1( isNull( addressMap.get( "streetAddress" ) ) );
                    userAddress.setAddress2( isNull( addressMap.get( "country" ) ) );
                    userAddress.setCity( isNull( addressMap.get( "locality" ) ) );
                    userAddress.setState( isNull( addressMap.get( "region" ) ) );
                    userAddress.setZip( isNull( addressMap.get( "postalCode" ) ) );
                    break;
                }
            }
        }
        return userAddress;
    }

    /**
     * Sets the agent personal email.
     *
     * @param source
     *            the source
     * @return the string
     */
    private String setAgentPersonalEmail( final User source ) {
        String personalEmail = "";
        final List< ArrayMap< String, Object > > emails = ( List< ArrayMap< String, Object > > ) source.getEmails();
        if (emails != null) {
            for ( ArrayMap< String, Object > emailMap : emails ) {
                if ("other".equals( emailMap.get( "type" ) )) {
                    personalEmail = isNull( emailMap.get( "address" ) );
                    break;
                }
            }
        }
        return personalEmail;
    }
}
