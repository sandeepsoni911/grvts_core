package com.owners.gravitas.business.builder.scraping.hubzu;

import org.apache.commons.lang3.text.StrBuilder;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.scraping.AbstractEmailLeadBuilder;
import com.owners.gravitas.dto.request.GenericLeadRequest;

/**
 * The Class HubzuEmailLeadBuilder.
 *
 * @author vishwanathm
 */
@Component
public class HubzuEmailLeadBuilder extends AbstractEmailLeadBuilder {

    /** The name key. */
    private static final String NAME_KEY = "Name: :";

    /** The contact key. */
    private static final String CONTACT_KEY = "Buyer Contact Number: :";

    /** The email key. */
    private static final String EMAIL_KEY = "Buyer Email: :";

    /** The property id key. */
    private static final String PROPERTY_ID_KEY = "Property Details Property ID :";

    /** The address key. */
    private static final String ADDRESS_KEY = "Property Address :";

    /** The dummy key. */
    private static final String DUMMY_KEY = "Please";

    /** The Constant MESSAGE_KEY. */
    private static final String MESSAGE_KEY = "Buyer's Message";

    /** The Constant MESSAGE_KEY1. */
    private static final String MESSAGE_KEY1 = "Buyer's Details";

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
        final String name = getLeadParameterValue( messageText, NAME_KEY, CONTACT_KEY );
        final String phone = getLeadParameterValue( messageText, CONTACT_KEY, EMAIL_KEY );
        final String email = getLeadParameterValue( messageText, EMAIL_KEY, PROPERTY_ID_KEY );
        final String propertyId = getLeadParameterValue( messageText, PROPERTY_ID_KEY, ADDRESS_KEY );
        final String propertyAddress = getLeadParameterValue( messageText, ADDRESS_KEY, DUMMY_KEY );
        final String message = getLeadParameterValue( messageText, MESSAGE_KEY, MESSAGE_KEY1 );

        setName( name, leadRequest );
        leadRequest.setEmail( email );
        leadRequest.setPhone( phone );
        leadRequest.setPropertyAddress( propertyAddress );
        leadRequest.setComments( getComments( propertyId, message ) );
        return leadRequest;
    }

    /**
     * Gets the comments.
     *
     * @param parameters
     *            the parameters
     * @return the comments
     */
    private String getComments( final String... parameters ) {
        final StrBuilder comments = new StrBuilder();
        comments.appendln( appendParam( "Property Id", parameters[0] ) );
        comments.appendln( appendParam( "Message", parameters[1] ) );
        return comments.toString();
    }
}
