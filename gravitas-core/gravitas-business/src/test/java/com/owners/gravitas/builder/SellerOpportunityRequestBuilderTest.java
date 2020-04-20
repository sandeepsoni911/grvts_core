package com.owners.gravitas.builder;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.request.SellerOpportunityRequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.Property;
import com.owners.gravitas.dto.PropertyOrder;
import com.owners.gravitas.dto.Seller;
import com.owners.gravitas.dto.SellerAddress;
import com.owners.gravitas.dto.crm.request.CRMOpportunityRequest;
import com.owners.gravitas.dto.request.OpportunityRequest;

/**
 * The Class SellerOpportunityRequestBuilderTest.
 *
 * @author vishwanathm
 */
public class SellerOpportunityRequestBuilderTest extends AbstractBaseMockitoTest {

    /** The seller opportunity request builder. */
    @InjectMocks
    private SellerOpportunityRequestBuilder sellerOpportunityRequestBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        OpportunityRequest or = new OpportunityRequest();
        PropertyOrder po = new PropertyOrder();
        Property property = new Property();
        SellerAddress address = new SellerAddress();
        property.setAddress( address );
        po.setProperty( property );
        or.setPropertyOrder( po );
        or.setSeller( new Seller() );
        CRMOpportunityRequest crmReq = sellerOpportunityRequestBuilder.convertTo( or );
        Assert.assertNotNull( crmReq );

        address = new SellerAddress();
        address.setAddress2( "address1 address1" );
        property.setAddress( address );
        crmReq = sellerOpportunityRequestBuilder.convertTo( or );
        Assert.assertNotNull( crmReq );

        address = new SellerAddress();
        address.setAddress2( "address1" );
        property.setAddress( address );
        crmReq = sellerOpportunityRequestBuilder.convertTo( or );
        Assert.assertNotNull( crmReq );
    }

    /**
     * Test convert to null spurce.
     */
    @Test
    public void testConvertToNullSpurce() {
        CRMOpportunityRequest crmReq = sellerOpportunityRequestBuilder.convertTo( null, null );
        Assert.assertNull( crmReq );

        crmReq = sellerOpportunityRequestBuilder.convertTo( null );
        Assert.assertNull( crmReq );
    }

    /**
     * Test convert to null destination.
     */
    @Test
    public void testConvertToNullDestination() {
        OpportunityRequest or = new OpportunityRequest();
        PropertyOrder po = new PropertyOrder();
        Property property = new Property();
        SellerAddress address = new SellerAddress();
        property.setAddress( address );
        po.setProperty( property );
        or.setPropertyOrder( po );
        or.setSeller( new Seller() );
        CRMOpportunityRequest crmReq = sellerOpportunityRequestBuilder.convertTo( or, null );
        Assert.assertNotNull( crmReq );
        crmReq = sellerOpportunityRequestBuilder.convertTo( or, new CRMOpportunityRequest() );
        Assert.assertNotNull( crmReq );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        sellerOpportunityRequestBuilder.convertFrom( new CRMOpportunityRequest() );
    }
}
