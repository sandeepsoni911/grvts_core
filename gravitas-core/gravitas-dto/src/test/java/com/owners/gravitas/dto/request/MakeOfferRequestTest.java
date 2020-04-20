package com.owners.gravitas.dto.request;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class MakeOfferRequestTest.
 *
 * @author vishwanathm
 */
public class MakeOfferRequestTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues requestTypeLeadRequestTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * CRMAccess.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createMakeOfferRequestTestValues();
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
        defaultValues.put( "offerAmount", null );
        defaultValues.put( "earnestMoneyDeposit", null );
        defaultValues.put( "purchaseMethod", null );
        defaultValues.put( "downPayment", null );
        defaultValues.put( "preApprovedForMortgage", null );
        defaultValues.put( "ownersVisitorId", null );
        defaultValues.put( "medianPrice", null );
        defaultValues.put( "requestType", "MAKE_OFFER" );
        defaultValues.put( "leadType", "BUYER" );
    }

    /**
     * This method is to create the actual values.
     */
    private void createMakeOfferRequestTestValues() {
        requestTypeLeadRequestTestValues = new PropertiesAndValues();
        requestTypeLeadRequestTestValues.put( "firstName", "test" );
        requestTypeLeadRequestTestValues.put( "lastName", "test" );
        requestTypeLeadRequestTestValues.put( "email", "test@test.com" );
        requestTypeLeadRequestTestValues.put( "phone", "123" );
        requestTypeLeadRequestTestValues.put( "source", "test" );
        requestTypeLeadRequestTestValues.put( "comments", "test" );
        requestTypeLeadRequestTestValues.put( "listingId", "test" );
        requestTypeLeadRequestTestValues.put( "state", "AB" );
        requestTypeLeadRequestTestValues.put( "propertyAddress", "test" );
        requestTypeLeadRequestTestValues.put( "mlsId", "test" );
        requestTypeLeadRequestTestValues.put( "alId", "test" );
        requestTypeLeadRequestTestValues.put( "leadSourceUrl", "test" );
        requestTypeLeadRequestTestValues.put( "additionalPropertyData", "test" );
        requestTypeLeadRequestTestValues.put( "websiteSessionData", "test" );
        requestTypeLeadRequestTestValues.put( "lastVisitDateTime", "2016-02-02" );
        requestTypeLeadRequestTestValues.put( "googleAnalyticsCampaign", "test" );
        requestTypeLeadRequestTestValues.put( "googleAnalyticsContent", "test" );
        requestTypeLeadRequestTestValues.put( "googleAnalyticsMedium", "test" );
        requestTypeLeadRequestTestValues.put( "googleAnalyticsSource", "test" );
        requestTypeLeadRequestTestValues.put( "googleAnalyticsTerm", "test" );
        requestTypeLeadRequestTestValues.put( "ownsHome", true );
        requestTypeLeadRequestTestValues.put( "offerAmount", "123" );
        requestTypeLeadRequestTestValues.put( "earnestMoneyDeposit", "123" );
        requestTypeLeadRequestTestValues.put( "purchaseMethod", "test" );
        requestTypeLeadRequestTestValues.put( "downPayment", "123" );
        requestTypeLeadRequestTestValues.put( "preApprovedForMortgage", "test" );
        requestTypeLeadRequestTestValues.put( "ownersVisitorId", "test" );
        requestTypeLeadRequestTestValues.put( "medianPrice", "test" );
        requestTypeLeadRequestTestValues.put( "requestType", "MAKE_OFFER" );
        requestTypeLeadRequestTestValues.put( "leadType", "BUYER" );
    }

    /**
     * Tests the {@link LeadRequest} with default values. Tests the getters
     * and setters of LeadRequest.
     */
    @Test
    public final void testLeadRequest() {
        final BeanLikeTester blt = new BeanLikeTester( MakeOfferRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, requestTypeLeadRequestTestValues );
    }
}
