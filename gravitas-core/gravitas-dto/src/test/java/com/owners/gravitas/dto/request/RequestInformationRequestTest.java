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
public class RequestInformationRequestTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues requestInformationRequestTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * CRMAccess.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createRequestInformationRequestTestValues();
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
        defaultValues.put( "requestType", "REQUEST_INFORMATION" );
        defaultValues.put( "leadType", "BUYER" );
    }

    /**
     * This method is to create the actual values.
     */
    private void createRequestInformationRequestTestValues() {
        requestInformationRequestTestValues = new PropertiesAndValues();
        requestInformationRequestTestValues.put( "firstName", "test" );
        requestInformationRequestTestValues.put( "lastName", "test" );
        requestInformationRequestTestValues.put( "email", "test@test.com" );
        requestInformationRequestTestValues.put( "phone", "123" );
        requestInformationRequestTestValues.put( "source", "test" );
        requestInformationRequestTestValues.put( "comments", "test" );
        requestInformationRequestTestValues.put( "listingId", "test" );
        requestInformationRequestTestValues.put( "state", "AB" );
        requestInformationRequestTestValues.put( "propertyAddress", "test" );
        requestInformationRequestTestValues.put( "mlsId", "test" );
        requestInformationRequestTestValues.put( "alId", "test" );
        requestInformationRequestTestValues.put( "leadSourceUrl", "test" );
        requestInformationRequestTestValues.put( "additionalPropertyData", "test" );
        requestInformationRequestTestValues.put( "websiteSessionData", "test" );
        requestInformationRequestTestValues.put( "lastVisitDateTime", "2016-02-02" );
        requestInformationRequestTestValues.put( "googleAnalyticsCampaign", "test" );
        requestInformationRequestTestValues.put( "googleAnalyticsContent", "test" );
        requestInformationRequestTestValues.put( "googleAnalyticsMedium", "test" );
        requestInformationRequestTestValues.put( "googleAnalyticsSource", "test" );
        requestInformationRequestTestValues.put( "googleAnalyticsTerm", "test" );
        requestInformationRequestTestValues.put( "ownsHome", true );
        requestInformationRequestTestValues.put( "ownersVisitorId", "test" );
        requestInformationRequestTestValues.put( "medianPrice", "test" );
        requestInformationRequestTestValues.put( "requestType", "REQUEST_INFORMATION" );
        requestInformationRequestTestValues.put( "leadType", "BUYER" );
    }

    /**
     * Tests the {@link LeadRequest} with default values. Tests the getters
     * and setters of LeadRequest.
     */
    @Test
    public final void testLeadRequest() {
        final BeanLikeTester blt = new BeanLikeTester( RequestInformationRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, requestInformationRequestTestValues );
    }
}
