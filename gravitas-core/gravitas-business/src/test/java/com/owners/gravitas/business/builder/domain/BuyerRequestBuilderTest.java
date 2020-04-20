package com.owners.gravitas.business.builder.domain;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.domain.BuyerRequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.LeadBusinessConfig;
import com.owners.gravitas.domain.Request;
import com.owners.gravitas.domain.entity.StateTimeZone;
import com.owners.gravitas.dto.UserTimeZone;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.enums.LeadRequestType;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.StateTimeZoneService;

/**
 * The Class BuyerRequestBuilderTest.
 *
 * @author vishwanathm
 */
public class BuyerRequestBuilderTest extends AbstractBaseMockitoTest {

    /** The buyer request builder. */
    @InjectMocks
    private BuyerRequestBuilder buyerRequestBuilder;

    /** The state time zone service. */
    @Mock
    private StateTimeZoneService stateTimeZoneService;
    
    @Mock
    private LeadBusinessConfig leadBusinessConfig;

    /**
     * Test conver to.
     */
    @Test
    public void testConverTo() {
        LeadRequest request = new LeadRequest();
        request.setOfferAmount( "121112" );
        request.setRequestType( LeadRequestType.MAKE_OFFER.toString() );
        Request req = buyerRequestBuilder.convertTo( request );
        Assert.assertNotNull( req );

        request = new LeadRequest();
        request.setRequestType( LeadRequestType.SCHEDULE_TOUR.toString() );
        request.setPropertyTourInformation(
                "11/4/2016:[12:00 PM, 1:00 PM, 4:00 PM - 5:00 PM]|11/03/2016:[7:00 PM, 8:00 PM]|10/18/2016:[AFTERNOON/EVENING]|10/18/2016:[ANYTIME]|10/18/2016:[MORNING]" );
        req = buyerRequestBuilder.convertTo( request );
        Assert.assertNotNull( req );
        Assert.assertEquals( 5, req.getDates().size() );

        request = new LeadRequest();
        request.setRequestType( LeadRequestType.REQUEST_INFORMATION.toString() );
        req = buyerRequestBuilder.convertTo( request );
        Assert.assertNotNull( req );

        request = new LeadRequest();
        request.setRequestType( "test" );
        req = buyerRequestBuilder.convertTo( request );
        Assert.assertNull( req );
    }

    /**
     * Test convert to empty property tour info.
     */
    @Test
    public void testConvertToEmptyPropertyTourInfo() {
        LeadRequest request = new LeadRequest();
        request.setRequestType( LeadRequestType.SCHEDULE_TOUR.toString() );
        request.setPropertyTourInformation( "" );
        Request req = buyerRequestBuilder.convertTo( request );
        Assert.assertNotNull( req );
        Assert.assertEquals( 0, req.getDates().size() );
    }

    /**
     * Test convert to empty property tour info with user time zone.
     */
    @Test
    public void testConvertToEmptyPropertyTourInfoWithUserTimeZone() {
        LeadRequest request = new LeadRequest();
        UserTimeZone timeZone = new UserTimeZone();
        timeZone.setHourOffset( 5 );
        timeZone.setMinuteOffset( 30 );
        request.setUserTimeZone( timeZone );
        request.setRequestType( LeadRequestType.SCHEDULE_TOUR.toString() );
        request.setPropertyTourInformation( "10/18/2016:[ANYTIME]|" );
        Request req = buyerRequestBuilder.convertTo( request );
        Assert.assertNotNull( req );
        Assert.assertEquals( 1, req.getDates().size() );
    }
    
    
    @Test
    public void testPropertyTourInfoDateTimeWithUserTimeZone() {
        LeadRequest request = new LeadRequest();
        UserTimeZone timeZone = new UserTimeZone();
        timeZone.setHourOffset( 5 );
        timeZone.setMinuteOffset( 30 );
        request.setUserTimeZone( timeZone );
        request.setRequestType( LeadRequestType.SCHEDULE_TOUR.toString() );
        request.setPropertyTourInformation( "10/18/2016:[10:00 AM]|" );
        Request req = buyerRequestBuilder.convertTo( request );
        Assert.assertNotNull( req );
        Assert.assertEquals( 1, req.getDates().size() );
    }

    /**
     * Test convert to empty property tour info with user time zone.
     */
    @Test
    public void testAsapPropertyTourInfoWithUserTimeZone() {
        LeadRequest request = new LeadRequest();
        UserTimeZone timeZone = new UserTimeZone();
        timeZone.setHourOffset( -5 );
        timeZone.setMinuteOffset( 0 );
        request.setUserTimeZone( timeZone );
        request.setRequestType( LeadRequestType.SCHEDULE_TOUR.toString() );
        request.setPropertyTourInformation( "05/26/2018:[ASAP]|" );
        Mockito.when( leadBusinessConfig.getBuyerTourOffsetHour() )
        .thenReturn( -10 );
        Request req = buyerRequestBuilder.convertTo( request );
        Assert.assertNotNull( req );
        Assert.assertEquals( 1, req.getDates().size() );
    }

    /**
     * Test convert to empty property tour info without user time zone with
     * state.
     */
    @Test
    public void testConvertToEmptyPropertyTourInfoWithoutUserTimeZoneWithState() {
        LeadRequest request = new LeadRequest();
        request.setState( "AL" );
        request.setRequestType( LeadRequestType.SCHEDULE_TOUR.toString() );
        request.setPropertyTourInformation( "10/18/2016:[ANYTIME]|" );
        Mockito.when( stateTimeZoneService.getStateTimeZone( Mockito.anyString() ) )
                .thenReturn( Mockito.any( StateTimeZone.class ) );
        Request req = buyerRequestBuilder.convertTo( request );
        Assert.assertNotNull( req );
        Assert.assertEquals( 1, req.getDates().size() );
    }

    /**
     * Test convert to empty property tour info without user time zone with
     * invalid state.
     */
    @Test
    public void testConvertToEmptyPropertyTourInfoWithoutUserTimeZoneWithInvalidState() {
        LeadRequest request = new LeadRequest();
        request.setState( "test" );
        request.setRequestType( LeadRequestType.SCHEDULE_TOUR.toString() );
        request.setPropertyTourInformation( "10/18/2016:[ANYTIME]|" );
        Mockito.when( stateTimeZoneService.getStateTimeZone( Mockito.anyString() ) ).thenReturn( Mockito.any( null ) );
        Request req = buyerRequestBuilder.convertTo( request );
        Assert.assertNotNull( req );
        Assert.assertEquals( 1, req.getDates().size() );
    }

    /**
     * Test convert to bad property tour info.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testConvertToBadPropertyTourInfo() {
        LeadRequest request = new LeadRequest();
        request.setOfferAmount( "121112" );
        request.setRequestType( LeadRequestType.MAKE_OFFER.toString() );
        Request req = buyerRequestBuilder.convertTo( request );
        Assert.assertNotNull( req );

        request = new LeadRequest();
        request.setRequestType( LeadRequestType.SCHEDULE_TOUR.toString() );
        request.setPropertyTourInformation( "testing" );
        req = buyerRequestBuilder.convertTo( request );
    }

    /**
     * Test null source.
     */
    @Test
    public void testNullSource() {
        Request req = buyerRequestBuilder.convertTo( null );
        Assert.assertNull( req );
    }

    /**
     * Test null destination.
     */
    @Test
    public void testNullDestination() {
        LeadRequest request = new LeadRequest();
        Request req = buyerRequestBuilder.convertTo( request, null );
        Assert.assertNotNull( req );

        req = buyerRequestBuilder.convertTo( request, new Request() );
        request.setRequestType( LeadRequestType.SCHEDULE_TOUR.toString() );
        Assert.assertNotNull( req );
        Assert.assertNull( req.getDates() );

        req = buyerRequestBuilder.convertTo( null, null );
        Assert.assertNull( req );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        buyerRequestBuilder.convertFrom( new Request() );
    }
}
