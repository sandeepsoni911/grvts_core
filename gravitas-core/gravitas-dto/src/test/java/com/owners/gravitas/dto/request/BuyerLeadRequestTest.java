package com.owners.gravitas.dto.request;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;

import com.owners.gravitas.dto.UserTimeZone;
import com.owners.gravitas.enums.RecordType;

/**
 * Test class for {@link BuyerLeadRequest}.
 *
 * @author harshads
 *
 */
public class BuyerLeadRequestTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues leadRequestTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * CRMAccess.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createLeadRequestTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "lastName", null );
        defaultValues.put( "firstName", null );
        defaultValues.put( "lastName", null );
        defaultValues.put( "email", null );
        defaultValues.put( "phone", null );
        defaultValues.put( "company", null );
        defaultValues.put( "source", null );
        defaultValues.put( "comments", null );
        defaultValues.put( "leadType", RecordType.BUYER.toString() );
        defaultValues.put( "listingId", null );
        defaultValues.put( "requestType", null );
        defaultValues.put( "leadStatus", null );
        defaultValues.put( "preApprovedForMortgage", null );
        defaultValues.put( "workingWithRealtor", null );
        defaultValues.put( "buyerReadinessTimeline", null );
        defaultValues.put( "marketingOptIn", false );
        defaultValues.put( "preferredContactTime", null );
        defaultValues.put( "preferredContactMethod", null );
        defaultValues.put( "priceRange", null );
        defaultValues.put( "buyerLeadQuality", null );
        defaultValues.put( "preferredLanguage", null );
        defaultValues.put( "state", null );
        defaultValues.put( "propertyAddress", null );
        defaultValues.put( "mlsId", null );
        defaultValues.put( "alId", null );
        defaultValues.put( "interestedZipcodes", null );
        defaultValues.put( "ownersComIdentifier", null );
        defaultValues.put( "offerAmount", null );
        defaultValues.put( "listingCreationDate", null );
        defaultValues.put( "financing", null );
        defaultValues.put( "website", null );
        defaultValues.put( "leadSourceUrl", null );
        defaultValues.put( "savedSearchValues", null );
        defaultValues.put( "earnestMoneyDeposit", null );
        defaultValues.put( "purchaseMethod", null );
        defaultValues.put( "downPayment", null );
        defaultValues.put( "propertyTourInformation", null );
        defaultValues.put( "additionalPropertyData", null );
        defaultValues.put( "websiteSessionData", null );
        defaultValues.put( "ownersVisitorId", null );
        defaultValues.put( "message", null );
        defaultValues.put( "medianPrice", null );
        defaultValues.put( "lastVisitDateTime", null );
        defaultValues.put( "mlsPackageType", null );
        defaultValues.put( "googleAnalyticsCampaign", null );
        defaultValues.put( "googleAnalyticsContent", null );
        defaultValues.put( "googleAnalyticsMedium", null );
        defaultValues.put( "googleAnalyticsSource", null );
        defaultValues.put( "googleAnalyticsTerm", null );
        defaultValues.put( "unbouncePageVariant", null );
        defaultValues.put( "gclId", null );
        defaultValues.put( "ownsHome", false );
        defaultValues.put( "userTimeZone", null );
        defaultValues.put( "propertyState", null );
        defaultValues.put( "interestedInFinancing", false );
        defaultValues.put( "loanNumber", Integer.valueOf( 0 ) );
        defaultValues.put( "orderId", null );
        defaultValues.put( "ownersAgent", null );
        defaultValues.put( "buyerLeadScore", null );
        defaultValues.put( "buyerLeadLabel", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createLeadRequestTestValues() {
        leadRequestTestValues = new PropertiesAndValues();
        leadRequestTestValues.put( "lastName", "test" );
        leadRequestTestValues.put( "firstName", "test" );
        leadRequestTestValues.put( "lastName", "test" );
        leadRequestTestValues.put( "email", "test" );
        leadRequestTestValues.put( "phone", "test" );
        leadRequestTestValues.put( "company", "test" );
        leadRequestTestValues.put( "source", "test" );
        leadRequestTestValues.put( "comments", "test" );
        leadRequestTestValues.put( "leadType", RecordType.BUYER.toString() );
        leadRequestTestValues.put( "listingId", "test" );
        leadRequestTestValues.put( "requestType", "test" );
        leadRequestTestValues.put( "leadStatus", "test" );
        leadRequestTestValues.put( "preApprovedForMortgage", "test" );
        leadRequestTestValues.put( "workingWithRealtor", "test" );
        leadRequestTestValues.put( "buyerReadinessTimeline", "test" );
        leadRequestTestValues.put( "marketingOptIn", true );
        leadRequestTestValues.put( "preferredContactTime", "test" );
        leadRequestTestValues.put( "preferredContactMethod", "test" );
        leadRequestTestValues.put( "priceRange", "test" );
        leadRequestTestValues.put( "buyerLeadQuality", "test" );
        leadRequestTestValues.put( "preferredLanguage", "test" );
        leadRequestTestValues.put( "state", "test" );
        leadRequestTestValues.put( "propertyAddress", "test" );
        leadRequestTestValues.put( "mlsId", "test" );
        leadRequestTestValues.put( "alId", "test" );
        leadRequestTestValues.put( "propertyState", "test" );
        leadRequestTestValues.put( "interestedZipcodes", "test" );
        leadRequestTestValues.put( "ownersComIdentifier", "test" );
        leadRequestTestValues.put( "offerAmount", "test" );
        leadRequestTestValues.put( "listingCreationDate", "2016-02-02" );
        leadRequestTestValues.put( "financing", "test" );
        leadRequestTestValues.put( "website", "test" );
        leadRequestTestValues.put( "leadSourceUrl", "test" );
        leadRequestTestValues.put( "savedSearchValues", "test" );
        leadRequestTestValues.put( "earnestMoneyDeposit", "test" );
        leadRequestTestValues.put( "purchaseMethod", "test" );
        leadRequestTestValues.put( "downPayment", "test" );
        leadRequestTestValues.put( "propertyTourInformation", "test" );
        leadRequestTestValues.put( "additionalPropertyData", "test" );
        leadRequestTestValues.put( "websiteSessionData", "test" );
        leadRequestTestValues.put( "ownersVisitorId", "test" );
        leadRequestTestValues.put( "message", "test" );
        leadRequestTestValues.put( "medianPrice", "test" );
        leadRequestTestValues.put( "lastVisitDateTime", "2016-02-02" );
        leadRequestTestValues.put( "mlsPackageType", "test" );
        leadRequestTestValues.put( "googleAnalyticsCampaign", "test" );
        leadRequestTestValues.put( "googleAnalyticsContent", "test" );
        leadRequestTestValues.put( "googleAnalyticsMedium", "test" );
        leadRequestTestValues.put( "googleAnalyticsSource", "test" );
        leadRequestTestValues.put( "googleAnalyticsTerm", "test" );
        leadRequestTestValues.put( "unbouncePageVariant", "test" );
        leadRequestTestValues.put( "gclId", "test" );
        leadRequestTestValues.put( "ownsHome", true );
        leadRequestTestValues.put( "userTimeZone", new UserTimeZone());
        leadRequestTestValues.put( "interestedInFinancing", true );
        leadRequestTestValues.put( "loanNumber", Integer.valueOf( 0 ) );
        leadRequestTestValues.put( "orderId", "test" );
        leadRequestTestValues.put( "ownersAgent", "test" );
        leadRequestTestValues.put( "buyerLeadScore", "test" );
        leadRequestTestValues.put( "buyerLeadLabel", "test" );
    }

    /**
     * Tests the {@link BuyerLeadRequest} with default values. Tests the getters
     * and setters of BuyerLeadRequest.
     */
//    @Test
    public final void testBuyerLeadRequest() {
        final BeanLikeTester blt = new BeanLikeTester( BuyerLeadRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, leadRequestTestValues );
    }
}
