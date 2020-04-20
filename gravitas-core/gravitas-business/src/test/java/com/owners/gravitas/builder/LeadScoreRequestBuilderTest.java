package com.owners.gravitas.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.request.LeadScoreRequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.enums.LeadRequestType;

/**
 * The Class OpportunityScoreRequestBuilderTest.
 *
 * @author shivamm
 */
public class LeadScoreRequestBuilderTest extends AbstractBaseMockitoTest {

    /** The opportunity score request builder. */
    @InjectMocks
    private LeadScoreRequestBuilder leadScoreRequestBuilder;

    /**
     * Sets the up.
     */
    @BeforeMethod
    public void setUp() {
        ReflectionTestUtils.setField( leadScoreRequestBuilder, "buyerReadinessTimeLineValues",
                "Now,1-3 Months,6+ Months,3-6 Months" );
        ReflectionTestUtils.setField( leadScoreRequestBuilder, "requestTypes",
                "Request Information,Schedule a Tour,Make An Offer" );
        ReflectionTestUtils.setField( leadScoreRequestBuilder, "workingWithRealtorValues",
                "I am represented,I am searching on my own" );
        ReflectionTestUtils.setField( leadScoreRequestBuilder, "preApprovedForMortgageValues",
                "I have been pre-approved,I have not been pre-approved,I'm a cash buyer" );
        ReflectionTestUtils.setField( leadScoreRequestBuilder, "invalidPhoneNumbers",
                "1111111111,2222222222,3333333333,4444444444" );
        ReflectionTestUtils.invokeMethod( leadScoreRequestBuilder, "initDataBuilder" );
    }

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        final LeadRequest source = new LeadRequest();
        source.setEmail( "a@owners.com" );
        final String sourceStr = "source";
        source.setSource( sourceStr );
        final String buyerReadinessTimeline = "brt";
        source.setBuyerReadinessTimeline( buyerReadinessTimeline );
        final String requestType = "rt";
        source.setRequestType( requestType );
        final String workingWithRealtor = "wwr";
        source.setWorkingWithRealtor( workingWithRealtor );
        final String preApprovedForMortgage = "pafm";
        source.setPreApprovedForMortgage( preApprovedForMortgage );
        final String phone = "p";
        source.setPhone( phone );
        final Map< String, String > destination = new HashMap<>();

        final Map< String, String > actual = leadScoreRequestBuilder.convertTo( source, destination );

        final String other = "Other";
        final String unknown = "Unknown";
        final String domain = "owners.com";

        assertEquals( other, actual.get( "buyerReadinessTimeline" ) );
        assertEquals( other, actual.get( "requestType" ) );
        assertEquals( unknown, actual.get( "workingWithRealtor" ) );
        assertEquals( unknown, actual.get( "preApprovedForMortgage" ) );
        assertEquals( "1", actual.get( "isInvalidPhone" ) );
        assertEquals( domain, actual.get( "domain" ) );
    }

    /**
     * Test convert to for null destination.
     */
    @Test
    public void testConvertToForNullDestination() {
        final LeadRequest source = new LeadRequest();
        source.setEmail( "aowners.com" );
        final String sourceStr = "source";
        source.setSource( sourceStr );
        final String buyerReadinessTimeline = "brt";
        source.setBuyerReadinessTimeline( buyerReadinessTimeline );
        final String requestType = "rt";
        source.setRequestType( requestType );
        final String workingWithRealtor = "wwr";
        source.setWorkingWithRealtor( workingWithRealtor );
        final String preApprovedForMortgage = "pafm";
        source.setPreApprovedForMortgage( preApprovedForMortgage );
        final Map< String, String > destination = null;

        final Map< String, String > actual = leadScoreRequestBuilder.convertTo( source, destination );

        final String other = "Other";
        final String unknown = "Unknown";
        final String domain = "aowners.com";

        assertEquals( other, actual.get( "buyerReadinessTimeline" ) );
        assertEquals( other, actual.get( "requestType" ) );
        assertEquals( unknown, actual.get( "workingWithRealtor" ) );
        assertEquals( unknown, actual.get( "preApprovedForMortgage" ) );
        assertEquals( "1", actual.get( "isInvalidPhone" ) );
        assertEquals( domain, actual.get( "domain" ) );
    }

    /**
     * Test convert to for null destination 2.
     */
    @Test
    public void testConvertToForNullDestination2() {
        final LeadRequest source = new LeadRequest();
        final String sourceStr = "self generated";
        source.setSource( sourceStr );
        source.setPhone("(453) 453-4564");
        final String buyerReadinessTimeline = "brt";
        source.setBuyerReadinessTimeline( buyerReadinessTimeline );
        final String workingWithRealtor = "wwr";
        source.setWorkingWithRealtor( workingWithRealtor );
        final String preApprovedForMortgage = "pafm";
        source.setPreApprovedForMortgage( preApprovedForMortgage );
        final Map< String, String > destination = null;

        final Map< String, String > actual = leadScoreRequestBuilder.convertTo( source, destination );

        final String other = "Other";
        final String unknown = "Unknown";
        final String nullStr ="NULL";

        assertEquals( other, actual.get( "buyerReadinessTimeline" ) );
        assertEquals( nullStr, actual.get( "requestType" ) );
        assertEquals( unknown, actual.get( "workingWithRealtor" ) );
        assertEquals( unknown, actual.get( "preApprovedForMortgage" ) );
        assertEquals( "0", actual.get( "isInvalidPhone" ) );
        assertNull( actual.get( "domain" ) );
    }

    /**
     * Test convert to for null destination and valid values.
     */
    @Test
    public void testConvertToForNullDestinationAndValidValues() {
        final LeadRequest source = new LeadRequest();
        source.setEmail( "a@owners.com" );
        final String sourceStr = "source";
        source.setSource( sourceStr );
        final String now = "Now";
        source.setBuyerReadinessTimeline( now );
        final String requestType = LeadRequestType.SCHEDULE_TOUR.name();
        source.setRequestType( requestType );
        final String workingWithRealtor = "I am represented";
        source.setWorkingWithRealtor( workingWithRealtor );
        final String preApprovedForMortgage = "I have been pre-approved";
        source.setPreApprovedForMortgage( preApprovedForMortgage );
        final String phone = "0123456789";
        source.setPhone( phone );
        final Map< String, String > destination = null;

        final Map< String, String > actual = leadScoreRequestBuilder.convertTo( source, destination );

        final String domain = "owners.com";

        assertEquals( now, actual.get( "buyerReadinessTimeline" ) );
        assertEquals( LeadRequestType.SCHEDULE_TOUR.getType(), actual.get( "requestType" ) );
        assertEquals( workingWithRealtor, actual.get( "workingWithRealtor" ) );
        assertEquals( preApprovedForMortgage, actual.get( "preApprovedForMortgage" ) );
        assertEquals( "0", actual.get( "isInvalidPhone" ) );
        assertEquals( domain, actual.get( "domain" ) );
    }

    /**
     * Test convert to should return destination when source is null.
     */
    @Test
    public void testConvertToShouldReturnDestinationWhenSourceIsNull() {
        final LeadRequest source = null;
        final Map< String, String > destination = new HashMap<>();
        final Map< String, String > actual = leadScoreRequestBuilder.convertTo( source, destination );
        assertEquals( destination, actual );
    }

    /**
     * Test convert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFrom() {
        leadScoreRequestBuilder.convertFrom( null, null );
    }

}
