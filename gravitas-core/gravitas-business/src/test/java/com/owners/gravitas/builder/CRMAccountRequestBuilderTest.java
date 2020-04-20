package com.owners.gravitas.builder;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.request.CRMAccountRequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.Seller;
import com.owners.gravitas.dto.SellerAddress;
import com.owners.gravitas.dto.crm.request.CRMAccountRequest;
import com.owners.gravitas.dto.request.OpportunityRequest;

/**
 * The Class CRMAccountRequestBuilderTest.
 *
 * @author vishwanathm
 */
public class CRMAccountRequestBuilderTest extends AbstractBaseMockitoTest {

    /** The crm account request builder. */
    @InjectMocks
    private CRMAccountRequestBuilder crmAccountRequestBuilder;

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
        CRMAccountRequest accountRequest = crmAccountRequestBuilder.convertTo( req );
        Assert.assertNotNull( accountRequest );

        accountRequest = crmAccountRequestBuilder.convertTo( req, new CRMAccountRequest() );
        Assert.assertNotNull( accountRequest );
    }

    /**
     * Test convert to.
     */
    @Test
    public void testConvertToNullAddress() {
        final OpportunityRequest req = new OpportunityRequest();
        final Seller seller = new Seller();
        req.setSeller( seller );
        CRMAccountRequest accountRequest = crmAccountRequestBuilder.convertTo( req );
        Assert.assertNotNull( accountRequest );

        accountRequest = crmAccountRequestBuilder.convertTo( req, new CRMAccountRequest() );
        Assert.assertNotNull( accountRequest );
    }

    /**
     * Test convert to source null.
     */
    @Test
    public void testConvertToSourceNull() {
        final CRMAccountRequest accountRequest = crmAccountRequestBuilder.convertTo( null );
        Assert.assertNull( accountRequest );
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
        req.setSeller( seller );
        CRMAccountRequest accountRequest = crmAccountRequestBuilder.convertTo( req, null );
        Assert.assertNotNull( accountRequest );

        accountRequest = crmAccountRequestBuilder.convertTo( null, null );
        Assert.assertNull( accountRequest );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        crmAccountRequestBuilder.convertFrom( new CRMAccountRequest() );
    }

}
