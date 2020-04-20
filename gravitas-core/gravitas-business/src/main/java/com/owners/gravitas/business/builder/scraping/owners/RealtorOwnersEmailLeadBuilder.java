package com.owners.gravitas.business.builder.scraping.owners;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.scraping.AbstractEmailLeadBuilder;
import com.owners.gravitas.dto.request.GenericLeadRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class RealtorOwnersEmailLeadBuilder.
 */
@Component
public class RealtorOwnersEmailLeadBuilder extends AbstractEmailLeadBuilder {

    /** The Constant FIRSTNAME. */
    private static final String FIRSTNAME = "First Name:";

    /** The Constant LASTNAME. */
    private static final String LASTNAME = "Last Name:";

    /** The Constant EMAIL. */
    private static final String EMAIL = "Email Address:";

    /** The Constant PHONE. */
    private static final String PHONE = "Phone Number:";

    /** The Constant NEED. */
    private static final String NEED = "I need:";

    /** The Constant PROPERTY_ADDRESS. */
    private static final String PROPERTY_ADDRESS = "Property Address:";

    /** The Constant COMMENT. */
    private static final String COMMENT = "Comment:";

    /** The Constant MLSID. */
    private static final String MLSID = "MLSID";

    /** The Constant INQUIRY. */
    private static final String INQUIRY = "This consumer inquired about:";

    /** The Constant ADDITIONAL_PROPERTY_DATA. */
    private static final String ADDITIONAL_PROPERTY_DATA = "Basic Property Attributes:";

    /** The Constant OWNERS_LEAD_SOURCE_URL. */
    private static final String LEAD_SOURCE_URL = "View this listing on REALTOR.com®:";

    /** The Constant COURTESY. */
    private static final String COURTESY = "Courtesy of:";

    /** The Constant LEAD_SOURCE. */
    private static final String LEAD_SOURCE = "Realtor.com";

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
        final String firstname = getLeadParameterValue( messageText, FIRSTNAME, LASTNAME );
        final String lastname = getLeadParameterValue( messageText, LASTNAME, EMAIL );
        final String email = getLeadParameterValue( messageText, EMAIL, PHONE );
        final String phone = getLeadParameterValue( messageText, PHONE, NEED );
        final String comment = getLeadParameterValue( messageText, COMMENT, INQUIRY );
        final String propertyAddress = getLeadParameterValue( messageText, PROPERTY_ADDRESS, MLSID );
        final String state = StringUtils.isNotBlank( propertyAddress )
                ? propertyAddress.substring( propertyAddress.lastIndexOf( " ", propertyAddress.lastIndexOf( " " )-1 )+1, propertyAddress.lastIndexOf( " " ) )
                : null;
        final String additionalPropertyData = getLeadParameterValue( messageText, ADDITIONAL_PROPERTY_DATA,
                LEAD_SOURCE_URL );
        final String leadSourceURL = getLeadParameterValue( messageText, LEAD_SOURCE_URL, COURTESY );
        leadRequest.setFirstName( firstname );
        leadRequest.setLastName( lastname );
        leadRequest.setEmail( email );
        leadRequest.setPhone( phone );
        leadRequest.setComments( comment );
        leadRequest.setPropertyAddress( propertyAddress );
        leadRequest.setState( state );
        leadRequest.setAdditionalPropertyData( additionalPropertyData );
        leadRequest.setLeadSourceUrl( leadSourceURL );
        leadRequest.setSource( LEAD_SOURCE );
        return leadRequest;
    }

}
