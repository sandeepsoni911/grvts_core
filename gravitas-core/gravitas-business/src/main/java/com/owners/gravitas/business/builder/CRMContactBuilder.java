/**
 *
 */
package com.owners.gravitas.business.builder;

import static com.owners.gravitas.util.StringUtils.convertObjectToString;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.owners.gravitas.dto.crm.request.CRMContactRequest;

/**
 * The Class CRMContactBuilder.
 *
 * @author harshads
 */
@Component
public class CRMContactBuilder extends AbstractBuilder< Map< String, Object >, CRMContactRequest > {

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public CRMContactRequest convertTo( final Map< String, Object > source, final CRMContactRequest destination ) {
        CRMContactRequest crmContact = destination;
        if (source != null) {
            if (crmContact == null) {
                crmContact = new CRMContactRequest();
            }
            crmContact.setFirstName( convertObjectToString( source.get( "FirstName" ) ) );
            crmContact.setLastName( convertObjectToString( source.get( "LastName" ) ) );
            crmContact.setEmail( convertObjectToString( source.get( "Email" ) ) );
            crmContact.setPhone( convertObjectToString( source.get( "Phone" ) ) );
            crmContact.setPreferredContactMethod( convertObjectToString( source.get( "Preferred_Contact_Time__c" ) ) );
            crmContact.setPreferredContactTime( convertObjectToString( source.get( "Preferred_Contact_Method__c" ) ) );
            crmContact.setRecordType( convertObjectToString( source.get( "RecordTypeId" ) ) );
            crmContact.setAccountId( convertObjectToString( source.get( "AccountId" ) ) );
        }
        return crmContact;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public Map< String, Object > convertFrom( final CRMContactRequest source,
            final Map< String, Object > destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }
}
