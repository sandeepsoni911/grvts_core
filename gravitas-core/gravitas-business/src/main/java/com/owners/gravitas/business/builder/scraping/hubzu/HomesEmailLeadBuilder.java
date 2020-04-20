package com.owners.gravitas.business.builder.scraping.hubzu;

import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.scraping.AbstractEmailLeadBuilder;
import com.owners.gravitas.dto.request.GenericLeadRequest;

/**
 * The Class HomesEmailLeadBuilder.
 *
 * @author vishwanathm
 */
@Component
public class HomesEmailLeadBuilder extends AbstractEmailLeadBuilder {

    /** The name key. */
    private static final String FIRST_NAME_KEY = "First Name:";

    /** The name key. */
    private static final String LAST_NAME_KEY = "Last Name:";

    /** The contact key. */
    private static final String CONTACT_KEY = "Phone:";

    /** The email key. */
    private static final String EMAIL_KEY = "Email:";

    /** The dummy key. */
    private static final String DUMMY_KEY = "Question/Comments:";

    /** The dummy key. */
    private static final String PROPERTY_ADDRESS = "(Listed by REALHome Services and Solutions)";

    /** The address key. */
    private static final String DUMMY2_KEY = "$";

    /**
     * Convert to instance of {@LeadRequest}.
     *
     * @param messageText
     *            the message text
     * @return the map
     */
    @Override
    public GenericLeadRequest convertTo( final String source ) {
        final String messageText = getMessageStringPlain( source );
        final GenericLeadRequest leadRequest = new GenericLeadRequest();
        final String fisrtName = getLeadParameterValue( messageText, FIRST_NAME_KEY, LAST_NAME_KEY );
        final String lastName = getLeadParameterValue( messageText, LAST_NAME_KEY, CONTACT_KEY );
        final String phone = getLeadParameterValue( messageText, CONTACT_KEY, EMAIL_KEY );
        final String email = getLeadParameterValue( messageText, EMAIL_KEY, DUMMY_KEY );
        final String message = getLeadParameterValue( messageText, DUMMY_KEY, PROPERTY_ADDRESS );
        final String propertyAddress = getLeadParameterValue( messageText, PROPERTY_ADDRESS, DUMMY2_KEY );

        leadRequest.setFirstName( fisrtName );
        leadRequest.setLastName( lastName );
        leadRequest.setEmail( email );
        leadRequest.setPhone( phone );
        leadRequest.setPropertyAddress( propertyAddress );
        leadRequest.setComments( appendParam( "Message", message ) );
        return leadRequest;
    }
}
