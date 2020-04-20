package com.owners.gravitas.business.builder.scraping.hubzu;

import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.scraping.AbstractEmailLeadBuilder;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.dto.request.GenericLeadRequest;

/**
 * The Class HomeBidzEmailLeadBuilder.
 *
 * @author vishwanathm
 */
@Component
public class HomeBidzEmailLeadBuilder extends AbstractEmailLeadBuilder {

    /** The name key. */
    private static final String INFO_KEY = "Info:";

    /** The name key. */
    private static final String MESSAGE_KEY = "Message: ";

    /** The dummy key. */
    private static final String DUMMY_KEY = "- Property listings by homebidz.co";

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
        final String info = getLeadParameterValue( messageText, INFO_KEY, MESSAGE_KEY );
        final String message = getLeadParameterValue( messageText, MESSAGE_KEY, DUMMY_KEY );

        final String[] leadDetails = info.split( Constants.BLANK_SPACE );
        int countCorrector = 1;
        // assumption is, there wont be first name coming from string and only
        // last name.
        if (leadDetails.length >= 4) {
            countCorrector = 0;
            leadRequest.setFirstName( leadDetails[countCorrector] );
        }
        leadRequest.setLastName( leadDetails[1 - countCorrector] );
        leadRequest.setPhone( leadDetails[2 - countCorrector] );
        leadRequest.setEmail( leadDetails[3 - countCorrector] );
        leadRequest.setComments( appendParam( "Message", message ) );
        return leadRequest;
    }
}
