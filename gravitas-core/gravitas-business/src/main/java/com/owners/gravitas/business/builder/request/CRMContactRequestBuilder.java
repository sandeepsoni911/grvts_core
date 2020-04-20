package com.owners.gravitas.business.builder.request;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.dto.Seller;
import com.owners.gravitas.dto.SellerAddress;
import com.owners.gravitas.dto.crm.request.CRMContactRequest;
import com.owners.gravitas.dto.request.OpportunityRequest;

/**
 * The Class ContactBuilder.
 *
 * @author harshads
 */
@Component( "crmContactRequestBuilder" )
public class CRMContactRequestBuilder extends AbstractBuilder< OpportunityRequest, CRMContactRequest > {

    /**
     * Converts OpportunityRequest to CRMContactRequest object.
     */
    @Override
    public CRMContactRequest convertTo( OpportunityRequest source, CRMContactRequest destination ) {
        CRMContactRequest crmContact = destination;
        if (source != null) {
            if (crmContact == null) {
                crmContact = new CRMContactRequest();
            }
            final Seller seller = source.getSeller();
            crmContact.setFirstName( seller.getFirstName() );
            crmContact.setLastName( seller.getLastName() );
            crmContact.setEmail( seller.getEmail() );
            crmContact.setPhone( seller.getPhoneNumber() );

            final SellerAddress address = seller.getAddress();
            if (null != address) {
                crmContact.setStreet( address.getAddress1()
                        + ( StringUtils.isBlank( address.getAddress2() ) ? "" : ", " + address.getAddress2() ) );
                crmContact.setState( address.getCity() );
                crmContact.setState( address.getState() );
                crmContact.setZip( address.getZip() );
            }
        }
        return crmContact;
    }

    /**
     * Method not supported.
     */
    @Override
    public OpportunityRequest convertFrom( CRMContactRequest source, OpportunityRequest destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }

}
