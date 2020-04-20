/**
 *
 */
package com.owners.gravitas.builder;

import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.request.SlackRequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.Property;
import com.owners.gravitas.dto.PropertyOrder;
import com.owners.gravitas.dto.Seller;
import com.owners.gravitas.dto.SellerAddress;
import com.owners.gravitas.dto.request.OpportunityRequest;
import com.owners.gravitas.dto.request.SlackRequest;

/**
 * The Class SlackRequestBuilderTest.
 *
 * @author harshads
 */
public class SlackRequestBuilderTest extends AbstractBaseMockitoTest {

    /** The slack request builder. */
    @InjectMocks
    private SlackRequestBuilder slackRequestBuilder;

    /**
     * Convert to test.
     */
    @Test
    public void convertToTest() {

        ReflectionTestUtils.setField( slackRequestBuilder, "message", "test" );

        OpportunityRequest source = new OpportunityRequest();

        Seller seller = new Seller();
        seller.setFirstName( "first" );
        seller.setLastName( "last" );

        source.setSeller( seller );

        PropertyOrder order = new PropertyOrder();

        Property property = new Property();

        SellerAddress address = new SellerAddress();

        address.setCity( "City" );
        address.setState( "state" );

        property.setListingId( "listng" );

        property.setAddress( address );
        order.setProperty( property );

        order.setOrderType( "type" );

        source.setPropertyOrder( order );

        Assert.assertNotNull( slackRequestBuilder.convertTo( source ) );

    }

    /**
     * Convert to test with not null destination.
     */
    @Test
    public void convertToTestWithNotNullDestination() {

        ReflectionTestUtils.setField( slackRequestBuilder, "message", "test" );

        OpportunityRequest source = new OpportunityRequest();

        Seller seller = new Seller();
        seller.setFirstName( "first" );
        seller.setLastName( "last" );

        source.setSeller( seller );

        PropertyOrder order = new PropertyOrder();

        Property property = new Property();

        SellerAddress address = new SellerAddress();

        address.setCity( "City" );
        address.setState( "state" );

        property.setListingId( "listng" );

        property.setAddress( address );
        order.setProperty( property );

        order.setOrderType( "type" );

        source.setPropertyOrder( order );

        Assert.assertNotNull( slackRequestBuilder.convertTo( source, new SlackRequest() ) );

    }

    /**
     * Convert to test with null source.
     */
    @Test
    public void convertToTestWithNullSource() {

        Assert.assertNull( slackRequestBuilder.convertTo( null ) );

    }

    /**
     * Convert from test.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void convertFromTest() {
        slackRequestBuilder.convertFrom( new SlackRequest() );
    }

}
