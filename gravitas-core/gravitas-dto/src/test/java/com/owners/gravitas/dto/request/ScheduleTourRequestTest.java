package com.owners.gravitas.dto.request;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class RequestInformationRequest.
 *
 * @author vishwanathm
 */
public class ScheduleTourRequestTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues scheduleTourRequestTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * CRMAccess.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createScheduleTourRequestTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "firstName", null );
        defaultValues.put( "lastName", null );
        defaultValues.put( "email", null );
        defaultValues.put( "phone", null );
        defaultValues.put( "source", null );
        defaultValues.put( "comments", null );
        defaultValues.put( "listingId", null );
        defaultValues.put( "state", null );
        defaultValues.put( "propertyAddress", null );
        defaultValues.put( "mlsId", null );
        defaultValues.put( "alId", null );
        defaultValues.put( "leadSourceUrl", null );
        defaultValues.put( "additionalPropertyData", null );
        defaultValues.put( "websiteSessionData", null );
        defaultValues.put( "lastVisitDateTime", null );
        defaultValues.put( "googleAnalyticsCampaign", null );
        defaultValues.put( "googleAnalyticsContent", null );
        defaultValues.put( "googleAnalyticsMedium", null );
        defaultValues.put( "googleAnalyticsSource", null );
        defaultValues.put( "googleAnalyticsTerm", null );
        defaultValues.put( "ownsHome", false );
        defaultValues.put( "ownersVisitorId", null );
        defaultValues.put( "medianPrice", null );
        defaultValues.put( "requestType", "SCHEDULE_TOUR" );
        defaultValues.put( "leadType", "BUYER" );
        defaultValues.put( "propertyTourInformation", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createScheduleTourRequestTestValues() {
        scheduleTourRequestTestValues = new PropertiesAndValues();
        scheduleTourRequestTestValues.put( "firstName", "test" );
        scheduleTourRequestTestValues.put( "lastName", "test" );
        scheduleTourRequestTestValues.put( "email", "test@test.com" );
        scheduleTourRequestTestValues.put( "phone", "123" );
        scheduleTourRequestTestValues.put( "source", "test" );
        scheduleTourRequestTestValues.put( "comments", "test" );
        scheduleTourRequestTestValues.put( "listingId", "test" );
        scheduleTourRequestTestValues.put( "state", "AB" );
        scheduleTourRequestTestValues.put( "propertyAddress", "test" );
        scheduleTourRequestTestValues.put( "mlsId", "test" );
        scheduleTourRequestTestValues.put( "alId", "test" );
        scheduleTourRequestTestValues.put( "leadSourceUrl", "test" );
        scheduleTourRequestTestValues.put( "additionalPropertyData", "test" );
        scheduleTourRequestTestValues.put( "websiteSessionData", "test" );
        scheduleTourRequestTestValues.put( "lastVisitDateTime", "2016-02-02" );
        scheduleTourRequestTestValues.put( "googleAnalyticsCampaign", "test" );
        scheduleTourRequestTestValues.put( "googleAnalyticsContent", "test" );
        scheduleTourRequestTestValues.put( "googleAnalyticsMedium", "test" );
        scheduleTourRequestTestValues.put( "googleAnalyticsSource", "test" );
        scheduleTourRequestTestValues.put( "googleAnalyticsTerm", "test" );
        scheduleTourRequestTestValues.put( "ownsHome", true );
        scheduleTourRequestTestValues.put( "ownersVisitorId", "test" );
        scheduleTourRequestTestValues.put( "medianPrice", "test" );
        scheduleTourRequestTestValues.put( "requestType", "SCHEDULE_TOUR" );
        scheduleTourRequestTestValues.put( "leadType", "BUYER" );
        scheduleTourRequestTestValues.put( "propertyTourInformation", "test" );

    }

    /**
     * Tests the {@link LeadRequest} with default values. Tests the getters
     * and setters of LeadRequest.
     */
    @Test
    public final void testLeadRequest() {
        final BeanLikeTester blt = new BeanLikeTester( ScheduleTourRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, scheduleTourRequestTestValues );
    }
}
