package com.owners.gravitas.business.builder.scraping.hubzu;

import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.scraping.AbstractEmailLeadBuilder;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.dto.request.GenericLeadRequest;

/**
 * The Class ZillowEmailLeadBuilder.
 *
 * @author vishwanathm
 */
@Component
public class ZillowEmailLeadBuilder extends AbstractEmailLeadBuilder {

    /** The name key. */
    private static final String NAME = "New Contact from Zillow";

    /** The message key. */
    private static final String MESSAGE_KEY = "says:";

    /** The dummy key. */
    private static final String DUMMY_KEY = "Respond within 10 minutes for best results.";

    /** The contact key. */
    private static final String CONTACT_KEY = "Manage or forward this contact on Zillow.";
    /** The Constant PROPERTY_ADDRESS_START. */
    private static final String PROPERTY_ADDRESS_START = "<a href=\"https://click.mail.zillow.com";

    /** The Constant PROPERTY_ADDRESS_END. */
    private static final String PROPERTY_ADDRESS_END = "</a>";

    /**
     * Convert to instance of {@LeadRequest}.
     *
     * @param messageText
     *            the message text
     * @return the map
     */
    @Override
    public GenericLeadRequest convertTo( final String source ) {
        GenericLeadRequest leadRequest = new GenericLeadRequest();
        final String popertyAddress = parsePropertyAddress( getMessageStringHTML( source ) );
        String messageText = getMessageStringPlain( source );
        final String name = getLeadParameterValue( messageText, NAME, MESSAGE_KEY );
        final String message = getLeadParameterValue( messageText, MESSAGE_KEY, DUMMY_KEY );
        final String contact = getLeadParameterValue( messageText, DUMMY_KEY, CONTACT_KEY );

        setName( name, leadRequest );
        final String[] contacts = contact.split( Constants.BLANK_SPACE );
        leadRequest.setEmail( contacts[0] );
        leadRequest.setPhone( contacts[1] );
        leadRequest.setPropertyAddress( popertyAddress );
        leadRequest.setComments( appendParam( "Message", message ) );
        return leadRequest;
    }

    /**
     * Parses the property address.
     *
     * @param source
     *            the source
     * @return the string
     */
    private String parsePropertyAddress( final String source ) {
        final String anchorStr = source.substring( source.indexOf( PROPERTY_ADDRESS_START ),
                source.indexOf( PROPERTY_ADDRESS_END, source.indexOf( PROPERTY_ADDRESS_START ) ) ).trim();
        return getMessageStringPlain( anchorStr );
    }

}
