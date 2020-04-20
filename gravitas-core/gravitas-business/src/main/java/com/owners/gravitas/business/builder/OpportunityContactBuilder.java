package com.owners.gravitas.business.builder;

import static com.owners.gravitas.util.StringUtils.convertObjectToString;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.owners.gravitas.amqp.OpportunityContact;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.PhoneNumber;
import com.owners.gravitas.enums.PhoneNumberType;

/**
 * The Class OpportunityContactBuilder.
 *
 * @author harshads
 */
@Component
public class OpportunityContactBuilder extends AbstractBuilder< Map< String, Object >, OpportunityContact > {

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public OpportunityContact convertTo( final Map< String, Object > source, final OpportunityContact destination ) {
        OpportunityContact opportunityContact = destination;
        if (source != null) {
            if (opportunityContact == null) {
                opportunityContact = new OpportunityContact();
            }
            final Map< String, Object > contactDetails = ( Map< String, Object > ) source.get( "Contact" );
            final Contact contact = new Contact();
            contact.setCrmId( convertObjectToString( source.get( "ContactId" ) ) );
            contact.setFirstName( convertObjectToString( contactDetails.get( "FirstName" ) ) );
            contact.setLastName( convertObjectToString( contactDetails.get( "LastName" ) ) );
            contact.setPreferredContactMethod( convertObjectToString( contactDetails.get( "Preferred_Contact_Method__c" ) ) );
            contact.setPreferredContactTime( convertObjectToString( contactDetails.get( "Preferred_Contact_Time__c" ) ) );
            final Map< String, Object > lastModifiedByDetails = ( Map< String, Object > ) contactDetails
                    .get( "LastModifiedBy" );
            contact.setLastModifiedBy( getLastModifiedBy( lastModifiedByDetails ) );
            if (contactDetails.get( "Email" ) != null) {
                contact.addEmail( convertObjectToString( contactDetails.get( "Email" ) ) );
            }
            if (contactDetails.get( "Phone" ) != null) {
                contact.addPhone( new PhoneNumber( PhoneNumberType.PRIMARY,
                        removeNumberFormat( convertObjectToString( contactDetails.get( "Phone" ) ) ) ) );
            }
            final Map< String, Object > opportunityDetails = ( Map< String, Object > ) ( Object ) source
                    .get( "Opportunity" );
            opportunityContact.setAgentEmail( convertObjectToString( opportunityDetails.get( "Owners_Agent__c" ) ) );

            contact.setLastModifiedDtm( new Date().getTime() );
            opportunityContact.setPrimaryContact( contact );
        }
        return opportunityContact;
    }

    /**
     * Gets the last modified by.
     *
     * @param map
     *            the map
     * @return the last modified by
     */
    private String getLastModifiedBy( Object map ) {
        return ( map != null ) ? ( ( Map< String, String > ) map ).get( "FirstName" ) + " "
                + ( ( Map< String, String > ) map ).get( "LastName" ) : StringUtils.EMPTY;
    }

    /**
     * Removes the number format.
     *
     * @param number
     *            the number
     * @return the string
     */
    private String removeNumberFormat( final String number ) {
        return number.replaceAll( "[^\\d]", "" );
    }

    /**
     * Method not supported.
     */
    @Override
    public Map< String, Object > convertFrom( OpportunityContact source, Map< String, Object > destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }
}
