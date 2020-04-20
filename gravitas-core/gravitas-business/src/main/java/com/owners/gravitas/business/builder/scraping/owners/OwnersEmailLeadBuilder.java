package com.owners.gravitas.business.builder.scraping.owners;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.scraping.AbstractEmailLeadBuilder;
import com.owners.gravitas.dto.request.GenericLeadRequest;

/**
 * The Class OwnersEmailLeadBuilder.
 */
@Component
public class OwnersEmailLeadBuilder extends AbstractEmailLeadBuilder {

    /** The Constant URL. */
    private static final String URL = "URL:";

    /** The Constant SOURCE. */
    private static final String SOURCE = "Source:";

    /** The Constant FIRSTNAME. */
    private static final String FIRSTNAME = "First Name:";

    /** The Constant LASTNAME. */
    private static final String LASTNAME = "Last Name:";

    /** The Constant EMAIL. */
    private static final String EMAIL = "Email:";

    /** The Constant PHONE. */
    private static final String PHONE = "Phone:";

    /** The Constant MESSAGE. */
    private static final String MESSAGE = "Message:";

    /** The Constant PROPERTY_ADDRESS. */
    private static final String PROPERTY_ADDRESS = "Property Address:";

    /** The Constant PROPERTY_STATE. */
    private static final String PROPERTY_STATE = "Property State:";

    /** The Constant PROPERTY_ZIP. */
    private static final String PROPERTY_ZIP = "Property Zip:";

    /** The Constant PROPERTY_MLS. */
    private static final String PROPERTY_MLS = "Property/MLS ID:";

    /** The Constant PRICE. */
    private static final String PRICE = "Price:";

    /** The Constant NOTES. */
    private static final String NOTES = "Notes:";

    /** The Constant OWNERS_LEAD_SOURCE_URL. */
    private static final String OWNERS_LEAD_SOURCE_URL = "http://www.owners.com";

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
        final String sourceName = getLeadParameterValue( messageText, SOURCE, FIRSTNAME );
        final String firstname = getLeadParameterValue( messageText, FIRSTNAME, LASTNAME );
        final String lastname = getLeadParameterValue( messageText, LASTNAME, EMAIL );
        final String email = getLeadParameterValue( messageText, EMAIL, PHONE );
        final String phone = getLeadParameterValue( messageText, PHONE, MESSAGE );
        final String message = getLeadParameterValue( messageText, MESSAGE, PROPERTY_ADDRESS );
        final String propertyAddress = getLeadParameterValue( messageText, PROPERTY_ADDRESS, URL );
        final String url = getLeadParameterValue( messageText, URL, PROPERTY_STATE );
        final String propertyState = getLeadParameterValue( messageText, PROPERTY_STATE, PROPERTY_ZIP );
        final String propertyZip = getLeadParameterValue( messageText, PROPERTY_ZIP, PROPERTY_MLS );
        final String propertyMls = getLeadParameterValue( messageText, PROPERTY_MLS, PRICE );
        final String price = getLeadParameterValue( messageText, PRICE, NOTES );
        final String notes = messageText.substring( messageText.indexOf( NOTES ) + NOTES.length() ).trim();
        leadRequest.setFirstName( firstname );
        leadRequest.setLastName( lastname );
        leadRequest.setSource( sourceName );
        leadRequest.setPhone( phone );
        leadRequest.setEmail( email );
        leadRequest.setMessage( message );
        leadRequest.setLeadSourceUrl( url );
        leadRequest.setPropertyAddress( propertyAddress );
        leadRequest.setState( propertyState );
        leadRequest.setInterestedZipcodes( propertyZip );
        leadRequest.setListingId( propertyMls );
        leadRequest.setPriceRange( price );
        leadRequest.setComments( notes );
        leadRequest.setWebsite( OWNERS_LEAD_SOURCE_URL );

        if (StringUtils.isBlank( leadRequest.getLeadSourceUrl() )) {
            leadRequest.setLeadSourceUrl( OWNERS_LEAD_SOURCE_URL );
        }
        return leadRequest;
    }
}
