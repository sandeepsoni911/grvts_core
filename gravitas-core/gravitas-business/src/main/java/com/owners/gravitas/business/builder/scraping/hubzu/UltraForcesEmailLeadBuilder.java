package com.owners.gravitas.business.builder.scraping.hubzu;

import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.scraping.AbstractEmailLeadBuilder;
import com.owners.gravitas.dto.request.GenericLeadRequest;

/**
 * The Class UltraForcesEmailLeadBuilder.
 *
 * @author vishwanathm
 */
@Component
public class UltraForcesEmailLeadBuilder extends AbstractEmailLeadBuilder {

    /** The name key. */
    private static final String NAME_KEY = "Customer Name:";

    /** The email key. */
    private static final String EMAIL_KEY = "Customer Email Address:";

    /** The contact key. */
    private static final String CONTACT_KEY = "Customer Phone:";

    /** The dummy key. */
    private static final String MESSAGE_KEY = "Message:";

    /** The dummy key. */
    private static final String DUMMY_KEY = "This email";

    /**
     * Convert to instance of {@LeadRequest}.
     *
     * @param messageText
     *            the message text
     * @return the map
     */
    @Override
    public GenericLeadRequest convertTo( final String source ) {
        final GenericLeadRequest leadRequest = new GenericLeadRequest();
        String messageText = getMessageStringPlain( source );
        final String name = getLeadParameterValue( messageText, NAME_KEY, EMAIL_KEY );
        final String email = getLeadParameterValue( messageText, EMAIL_KEY, CONTACT_KEY );
        final String phone = getLeadParameterValue( messageText, CONTACT_KEY, MESSAGE_KEY );
        final String message = getLeadParameterValue( messageText, MESSAGE_KEY, DUMMY_KEY );

        setName( name, leadRequest );
        leadRequest.setEmail( email );
        leadRequest.setPhone( phone );
        leadRequest.setComments( appendParam( "Message", message ) );
        return leadRequest;
    }
}
