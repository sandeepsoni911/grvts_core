package com.owners.gravitas.business.builder;

import static com.owners.gravitas.constants.Constants.SPACE;

import java.util.Date;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.constants.UserRole;
import com.owners.gravitas.dto.UserAddress;
import com.owners.gravitas.dto.request.AgentOnboardRequest;
import com.owners.gravitas.util.DateUtil;
import com.owners.gravitas.util.PropertiesUtil;

/**
 * The Class AgentOnboardRequestBuilder.
 *
 * @author raviz
 */
@Component( "agentOnboardRequestBuilder" )
public class AgentOnboardRequestBuilder extends AbstractBuilder< AgentSource, AgentOnboardRequest > {

    /** The Constant LANGUAGE. */
    private final static String LANGUAGE = "english";

    /** The Constant PROPERTY_STATE. */
    private final static String PROPERTY_STATE = "managing.broker.email.state.";

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public AgentOnboardRequest convertTo( final AgentSource source, final AgentOnboardRequest destination ) {
        AgentOnboardRequest onboardRequest = destination;
        if (source != null) {
            if (onboardRequest == null) {
                onboardRequest = new AgentOnboardRequest();
            }
            onboardRequest.setFirstName( source.getName().split( SPACE )[0] );
            onboardRequest.setLastName( getLastName( source.getName() ) );
            onboardRequest.setEmail( source.getEmail() );
            onboardRequest.setPhone( source.getPhone() );
            onboardRequest.setLanguage( LANGUAGE );
            onboardRequest.setPersonalEmail( source.getEmail() );
            onboardRequest.setType( source.getAgentType() );
            onboardRequest.setMobileCarrier( source.getMobileCarrier() );
            onboardRequest.setAgentStartingDate( getStartingDate( source.getStartingDate() ) );
            onboardRequest.setNotes( source.getNotes() );
            onboardRequest.setDrivingRadius( source.getDrivingRadius() );
            onboardRequest.setStatus( source.getStatus() );
            onboardRequest.setLicense( source.getLicense() );
            onboardRequest.setAvailable( source.isAvailable() );
            onboardRequest.setManagingBrokerId( getManagingBrokerEmail( source.getState() ) );
            onboardRequest.setRoleId( UserRole.FIREBASE_AGENT );

            final UserAddress address = new UserAddress();
            address.setAddress1( source.getAddress1() );
            address.setAddress2( source.getAddress2() );
            address.setCity( source.getCity() );
            address.setState( source.getState() );
            address.setZip( source.getHomeZip() );
            onboardRequest.setAddress( address );
        }
        return onboardRequest;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public AgentSource convertFrom( final AgentOnboardRequest source, final AgentSource destination ) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the last name.
     *
     * @param fullName
     *            the full name
     * @return the last name
     */
    private String getLastName( final String fullName ) {
        String lastName = null;
        final String[] name = fullName.split( SPACE, 2 );
        if (name.length > 1) {
            lastName = name[1];
        }
        return lastName;
    }

    /**
     * Gets the managing broker id.
     *
     * @param state
     *            the state
     * @return the managing broker id
     */
    private String getManagingBrokerEmail( final String state ) {
        return PropertiesUtil.getProperty( PROPERTY_STATE + state );
    }

    /**
     * Gets the starting date.
     *
     * @param date
     *            the date
     * @return the starting date
     */
    private String getStartingDate( final Date date ) {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern( DateUtil.DEFAULT_OWNERS_DATE_PATTERN );
        return dateTimeFormatter.print( date.getTime() );
    }
}
