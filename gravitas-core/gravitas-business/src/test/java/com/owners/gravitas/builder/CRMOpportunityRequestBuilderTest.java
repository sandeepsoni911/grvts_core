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
 * The Class CRMOpportunityRequestBuilderTest.
 *
 * @author vishwanathm
 */
public class CRMOpportunityRequestBuilderTest extends AbstractBaseMockitoTest {

    /** The crm lead request builder. */
    @InjectMocks
    private SellerOpportunityRequestBuilder sellerOpportunityRequestBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        final OpportunityRequest req = new OpportunityRequest();

        req.setSeller( new Seller() );

        final Property property = new Property();
        SellerAddress address = new SellerAddress();
        address.setAddress1( "123" );
        address.setAddress2( "1231" );
        property.setAddress( address );
        final PropertyOrder propertyOrder = new PropertyOrder();
        propertyOrder.setProperty( property );
        req.setPropertyOrder( propertyOrder );
        CRMOpportunityRequest crmOpportunityRequest = sellerOpportunityRequestBuilder.convertTo( req );
        Assert.assertNotNull( crmOpportunityRequest );

        crmOpportunityRequest = sellerOpportunityRequestBuilder.convertTo( req, new CRMOpportunityRequest() );
        Assert.assertNotNull( crmOpportunityRequest );
    }

    /**
     * Test convert to.
     */
    @Test
    public void testConvertToNullPropertyAddress() {
        final OpportunityRequest req = new OpportunityRequest();
        req.setSeller( new Seller() );
        final Property property = new Property();
        SellerAddress address = null;
        property.setAddress( address );
        final PropertyOrder propertyOrder = new PropertyOrder();
        propertyOrder.setProperty( property );
        req.setPropertyOrder( propertyOrder );
        CRMOpportunityRequest crmOpportunityRequest = sellerOpportunityRequestBuilder.convertTo( req );
        Assert.assertNotNull( crmOpportunityRequest );

        crmOpportunityRequest = sellerOpportunityRequestBuilder.convertTo( req, new CRMOpportunityRequest() );
        Assert.assertNotNull( crmOpportunityRequest );
    }

    /**
     * Test convert to source null.
     */
    @Test
    public void testConvertToSourceNull() {
        final CRMOpportunityRequest crmOpportunityRequest = sellerOpportunityRequestBuilder.convertTo( null );
        Assert.assertNull( crmOpportunityRequest );
    }

    /**
     * Test convert to destination null.
     */
    @Test
    public void testConvertToDestinationNull() {
        final OpportunityRequest req = new OpportunityRequest();
        Seller seller = new Seller();
        seller.setMainContactNumer( "123123" );
        req.setSeller(seller  );
        final Property property = new Property();
        SellerAddress address = new SellerAddress();
        address.setAddress1( "123" );
        property.setAddress( address );
        final PropertyOrder propertyOrder = new PropertyOrder();
        propertyOrder.setProperty( property );
        req.setPropertyOrder( propertyOrder );
        CRMOpportunityRequest crmOpportunityRequest = sellerOpportunityRequestBuilder.convertTo( req, null );
        Assert.assertNotNull( crmOpportunityRequest );

        crmOpportunityRequest = sellerOpportunityRequestBuilder.convertTo( null, null );
        Assert.assertNull( crmOpportunityRequest );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        sellerOpportunityRequestBuilder.convertFrom( new CRMOpportunityRequest() );
    }

}
