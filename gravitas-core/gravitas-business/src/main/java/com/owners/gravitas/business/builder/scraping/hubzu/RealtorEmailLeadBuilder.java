package com.owners.gravitas.business.builder.scraping.hubzu;

import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.scraping.AbstractEmailLeadBuilder;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.dto.request.GenericLeadRequest;

/**
 * The Class RealtorEmailLeadBuilder.
 *
 * @author vishwanathm
 */
@Component
public class RealtorEmailLeadBuilder extends AbstractEmailLeadBuilder {

    /** The message key. */
    private static final String MESSAGE_KEY = "New lead from realtor.com";

    /** The name. */
    private static final String NAME = "Name";

    /** The dummy key. */
    private static final String DUMMY_KEY = "Verified phone";

    /** The dummy key. */
    private static final String DUMMY1_KEY = "possible";

    /** The Constant DUMMY2_KEY. */
    private static final String DUMMY2_KEY = "Property Details";

    /** The Constant PROPERTY_ADDRESS. */
    private static final String MLS_ID = "MLS ID #";

    /** The Constant DUMMY3_KEY. */
    private static final String DUMMY3_KEY = "$";

    /**
     * Convert to instance of {@LeadRequest}.
     *
     * @param source
     *            the message text
     * @return the map
     */
    @Override
    public GenericLeadRequest convertTo( final String source ) {
        final GenericLeadRequest leadRequest = new GenericLeadRequest();
        String messageText = getMessageStringPlain( source );
        final String messageStr = getLeadParameterValue( messageText, MESSAGE_KEY, NAME );
        final int msgIndex = ( messageStr.indexOf( "am" ) ) < 0 ? messageStr.indexOf( "pm" ) + 2
                : messageStr.indexOf( "am" ) + 2;
        String message = messageStr.substring( msgIndex, messageStr.length() ).trim();
        final String name = getLeadParameterValue( messageText, NAME, DUMMY_KEY );
        final String contact = getLeadParameterValue( messageText, DUMMY1_KEY, DUMMY2_KEY );
        final String propertyAddress = getLeadParameterValue( messageText, MLS_ID, DUMMY3_KEY );
        message = message + MLS_ID + propertyAddress.substring( 0, propertyAddress.indexOf( Constants.BLANK_SPACE ) );
        final String[] contacts = contact.split( Constants.BLANK_SPACE );
        setName( name, leadRequest );
        leadRequest.setPhone( contacts[0] );
        leadRequest.setEmail( contacts[1] );
        leadRequest.setPropertyAddress(
                propertyAddress.substring( propertyAddress.indexOf( Constants.BLANK_SPACE ) ).trim() );
        leadRequest.setComments( appendParam( "Message", message ) );
        return leadRequest;
    }
}
