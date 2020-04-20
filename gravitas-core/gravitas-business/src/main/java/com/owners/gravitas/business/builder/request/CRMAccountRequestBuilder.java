package com.owners.gravitas.business.builder.request;

import static com.owners.gravitas.constants.Constants.BLANK_SPACE;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.dto.Seller;
import com.owners.gravitas.dto.SellerAddress;
import com.owners.gravitas.dto.crm.request.CRMAccountRequest;
import com.owners.gravitas.dto.request.OpportunityRequest;

/**
 * The Class CRMAccountRequestBuilder.
 *
 * @author harshads
 */
@Component( "crmAccountRequestBuilder" )
public class CRMAccountRequestBuilder extends AbstractBuilder< OpportunityRequest, CRMAccountRequest > {

    /**
     * This method converts OpportunityRequest object to CRMAccountRequest
     * object.
     *
     * @param source
     *            is the dto object to be converted to domain.
     * @return CRMAccountRequest object
     */
    @Override
    public CRMAccountRequest convertTo( final OpportunityRequest source, final CRMAccountRequest destination ) {
        CRMAccountRequest account = destination;
        if (source != null) {
            if (account == null) {
                account = new CRMAccountRequest();
            }
            final Seller seller = source.getSeller();
            final String accountName = StringUtils.isNotBlank( seller.getFirstName() )
                    ? seller.getFirstName() + BLANK_SPACE + seller.getLastName() + " - " + seller.getEmail()
                    : seller.getLastName() + " - " + seller.getEmail();
            account.setName( accountName );
            account.setPhone( seller.getPhoneNumber() );

            final SellerAddress address = seller.getAddress();

            if (null != address) {
                account.setStreet( address.getAddress1() + ( StringUtils.isBlank( address.getAddress2() )
                        ? StringUtils.EMPTY : ", " + address.getAddress2() ) );
                account.setCity( address.getCity() );
                account.setState( address.getState() );
                account.setZip( address.getZip() );
            }
        }
        return account;
    }

    /**
     * Method not supported.
     */
    @Override
    public OpportunityRequest convertFrom( CRMAccountRequest source, OpportunityRequest destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }

}
