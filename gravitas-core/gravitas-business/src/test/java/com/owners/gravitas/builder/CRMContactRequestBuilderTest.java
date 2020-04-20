package com.owners.gravitas.builder;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.request.CRMContactRequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.Seller;
import com.owners.gravitas.dto.SellerAddress;
import com.owners.gravitas.dto.crm.request.CRMContactRequest;
import com.owners.gravitas.dto.request.OpportunityRequest;

/**
 * The Class CRMContactRequestBuilderTest.
 *
 * @author vishwanathm
 */
public class CRMContactRequestBuilderTest extends AbstractBaseMockitoTest {

    /** The crm lead request builder. */
    @InjectMocks
    private CRMContactRequestBuilder crmContactRequestBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        final OpportunityRequest req = new OpportunityRequest();
        final Seller seller = new Seller();
        SellerAddress address = new SellerAddress();
        address.setAddress1( "123" );
        address.setAddress2( "1231" );
        seller.setAddress( address );
        req.setSeller( seller );
        CRMContactRequest contactRequest = crmContactRequestBuilder.convertTo( req );
        Assert.assertNotNull( contactRequest );

        contactRequest = crmContactRequestBuilder.convertTo( req, new CRMContactRequest() );
        Assert.assertNotNull( contactRequest );
    }

    /**
     * Test convert to with address null.
     */
    @Test
    public void testConvertToWithAddressNull() {
        final OpportunityRequest req = new OpportunityRequest();
        final Seller seller = new Seller();
        SellerAddress address = null;
        seller.setAddress( address );
        req.setSeller( seller );
        CRMContactRequest contactRequest = crmContactRequestBuilder.convertTo( req );
        Assert.assertNotNull( contactRequest );

        contactRequest = crmContactRequestBuilder.convertTo( req, new CRMContactRequest() );
        Assert.assertNotNull( contactRequest );
    }

    /**
     * Test convert to source null.
     */
    @Test
    public void testConvertToSourceNull() {
        final CRMContactRequest contactRequest = crmContactRequestBuilder.convertTo( null );
        Assert.assertNull( contactRequest );
    }

    /**
     * Test convert to destination null.
     */
    @Test
    public void testConvertToDestinationNull() {
        final OpportunityRequest req = new OpportunityRequest();
        final Seller seller = new Seller();
        SellerAddress address = new SellerAddress();
        address.setAddress1( "123" );
        seller.setAddress( address );
        req.setSeller( seller );
        CRMContactRequest contactRequest = crmContactRequestBuilder.convertTo( req, null );
        Assert.assertNotNull( contactRequest );

        contactRequest = crmContactRequestBuilder.convertTo( null, null );
        Assert.assertNull( contactRequest );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        crmContactRequestBuilder.convertFrom( new CRMContactRequest() );
    }

}
