package com.owners.gravitas.business.builder.scraping.hubzu;

import org.apache.commons.lang3.text.StrBuilder;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.scraping.AbstractEmailLeadBuilder;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.dto.request.GenericLeadRequest;

/**
 * The Class TruliaEmailLeadBuilder.
 *
 * @author vishwanathm
 */
@Component
public class TruliaEmailLeadBuilder extends AbstractEmailLeadBuilder {

    /** The Constant HYPHON. */
    private static final String HYPHON = "-";

    /** The from key. */
    private static final String FROM_KEY = "you have a new lead! From:";

    /** The email key. */
    private static final String EMAIL_KEY = "Email:";

    /** The phone key. */
    private static final String PHONE_KEY = "Phone:";

    /** The message key. */
    private static final String MESSAGE_KEY = "Message:";

    /** The dummy key. */
    private static final String DUMMY_KEY = "View all your leads";

    /** The address key. */
    private static final String ADDRESS_KEY = "This lead was sent from:";

    /** The mlsid key. */
    private static final String MLSID_KEY = "MLS ID:";

    /** The price key. */
    private static final String PRICE_KEY = "$";

    /** The DUMM y1_ key. */
    private static final String DUMMY1_KEY = "Need";

    /**
     * Convert to instance of {@LeadRequest}.
     *
     * @param source
     *            the message text
     * @return the map
     */
    @Override
    public GenericLeadRequest convertTo( final String source ) {
        GenericLeadRequest leadRequest = new GenericLeadRequest();
        final String messageText = getMessageStringPlain( source );
        final String name = getLeadParameterValue( messageText, FROM_KEY, EMAIL_KEY );
        final String email = getLeadParameterValue( messageText, EMAIL_KEY, PHONE_KEY );
        final String phone = getLeadParameterValue( messageText, PHONE_KEY, MESSAGE_KEY );
        final String message = getLeadParameterValue( messageText, MESSAGE_KEY, DUMMY_KEY );
        final String addressStr = getLeadParameterValue( messageText, ADDRESS_KEY, MLSID_KEY );
        final String mlsId = getLeadParameterValue( messageText, MLSID_KEY, PRICE_KEY );
        final String price = getLeadParameterValue( messageText, PRICE_KEY, DUMMY1_KEY );

        setName( name, leadRequest );
        leadRequest.setEmail( email );
        leadRequest.setPhone( phone );
        leadRequest.setPropertyAddress( getPropertyAddress( addressStr, getMessageStringHTML( source ) ) );
        leadRequest.setComments( getComments( mlsId, message, price ) );
        return leadRequest;
    }

    /**
     * Gets the property address.
     *
     * @param addressStr
     *            the address string
     * @param htmlStr
     *            the html string
     * @return the property address
     */
    public static String getPropertyAddress( final String addressStr, final String htmlStr ) {
        String addressRaw = addressStr.substring( 0, addressStr.indexOf( ',' ) ).trim()
                .replaceAll( Constants.BLANK_SPACE, HYPHON );
        final int addressStartIndex = htmlStr.indexOf( addressRaw.trim() );
        final int addressEndIndex = htmlStr.indexOf( '"', addressStartIndex );
        return htmlStr.substring( addressStartIndex, addressEndIndex ).replaceAll( HYPHON, Constants.BLANK_SPACE );
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
        comments.appendln( appendParam( "MLS Id", parameters[0] ) );
        comments.appendln( appendParam( "Message", parameters[1] ) );
        comments.appendln( appendParam( "Price", parameters[2] ) );
        return comments.toString();
    }

}
