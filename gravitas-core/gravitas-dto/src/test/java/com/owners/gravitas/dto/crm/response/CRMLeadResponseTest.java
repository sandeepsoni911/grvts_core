package com.owners.gravitas.dto.crm.response;

import org.joda.time.DateTime;
import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;

import com.owners.gravitas.dto.request.LeadRequest;

public class CRMLeadResponseTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues leadResponseTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * CRMAccess.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createleadResponseTestValues();
    }

    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "firstName", null );
        defaultValues.put( "lastName", null );
        defaultValues.put( "email", null );
        defaultValues.put( "phone", null );
        defaultValues.put( "company", null );
        defaultValues.put( "source", null );
        defaultValues.put( "comments", null );
        defaultValues.put( "recordType", null );
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
        defaultValues.put( "auditRecord", null );
        defaultValues.put( "deDuped", false );
        defaultValues.put( "id", null );
        defaultValues.put( "savedId", null );
        defaultValues.put( "mlsPackageType", null );
        defaultValues.put( "googleAnalyticsCampaign", null );
        defaultValues.put( "googleAnalyticsContent", null );
        defaultValues.put( "googleAnalyticsMedium", null );
        defaultValues.put( "googleAnalyticsSource", null );
        defaultValues.put( "googleAnalyticsTerm", null );
        defaultValues.put( "unbouncePageVariant", null );
        defaultValues.put( "gclId", null );
        defaultValues.put( "engineId", null );
        defaultValues.put( "recordHistory", null );
        defaultValues.put( "deDupCounter", null );
        defaultValues.put( "ownsHome", false );
        defaultValues.put( "doNotCall", false );
        defaultValues.put( "doNotEmail", false );
        defaultValues.put( "referred", false );
        defaultValues.put( "convertedDate", null );
        defaultValues.put( "ownerId", null );
        defaultValues.put( "closedReason", null );
        defaultValues.put( "propertyCity", null );
        defaultValues.put( "priceRanges", null );
        defaultValues.put( "loanNumber", Integer.valueOf( 0 ) );
        defaultValues.put( "interestedInFinancing", false );
        defaultValues.put( "orderId", null );
        defaultValues.put( "ownersAgent", null );
        defaultValues.put( "recordTypeName", null );
        defaultValues.put( "createdById", null );
        defaultValues.put( "lastModifiedById", null );
        defaultValues.put( "createdDateTime", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createleadResponseTestValues() {
        leadResponseTestValues = new PropertiesAndValues();
        leadResponseTestValues.put( "firstName", "test" );
        leadResponseTestValues.put( "lastName", "test" );
        leadResponseTestValues.put( "email", "test" );
        leadResponseTestValues.put( "phone", "test" );
        leadResponseTestValues.put( "company", "test" );
        leadResponseTestValues.put( "source", "test" );
        leadResponseTestValues.put( "comments", "test" );
        leadResponseTestValues.put( "recordType", "test" );
        leadResponseTestValues.put( "listingId", "test" );
        leadResponseTestValues.put( "requestType", "test" );
        leadResponseTestValues.put( "leadStatus", "test" );
        leadResponseTestValues.put( "preApprovedForMortgage", "test" );
        leadResponseTestValues.put( "workingWithRealtor", "test" );
        leadResponseTestValues.put( "buyerReadinessTimeline", "test" );
        leadResponseTestValues.put( "marketingOptIn", true );
        leadResponseTestValues.put( "preferredContactTime", "test" );
        leadResponseTestValues.put( "preferredContactMethod", "test" );
        leadResponseTestValues.put( "priceRange", "test" );
        leadResponseTestValues.put( "buyerLeadQuality", "test" );
        leadResponseTestValues.put( "preferredLanguage", "test" );
        leadResponseTestValues.put( "state", "test" );
        leadResponseTestValues.put( "propertyAddress", "test" );
        leadResponseTestValues.put( "mlsId", "test" );
        leadResponseTestValues.put( "alId", "test" );
        leadResponseTestValues.put( "interestedZipcodes", "test" );
        leadResponseTestValues.put( "ownersComIdentifier", "test" );
        leadResponseTestValues.put( "offerAmount", "test" );
        leadResponseTestValues.put( "listingCreationDate", new DateTime() );
        leadResponseTestValues.put( "financing", "test" );
        leadResponseTestValues.put( "website", "test" );
        leadResponseTestValues.put( "leadSourceUrl", "test" );
        leadResponseTestValues.put( "savedSearchValues", "test" );
        leadResponseTestValues.put( "earnestMoneyDeposit", "test" );
        leadResponseTestValues.put( "purchaseMethod", "test" );
        leadResponseTestValues.put( "downPayment", "test" );
        leadResponseTestValues.put( "propertyTourInformation", "test" );
        leadResponseTestValues.put( "additionalPropertyData", "test" );
        leadResponseTestValues.put( "websiteSessionData", "test" );
        leadResponseTestValues.put( "ownersVisitorId", "test" );
        leadResponseTestValues.put( "message", "test" );
        leadResponseTestValues.put( "medianPrice", "test" );
        leadResponseTestValues.put( "lastVisitDateTime", new DateTime() );
        leadResponseTestValues.put( "auditRecord", "test" );
        leadResponseTestValues.put( "deDuped", true );
        leadResponseTestValues.put( "id", "test" );
        leadResponseTestValues.put( "savedId", "test" );
        leadResponseTestValues.put( "mlsPackageType", "test" );
        leadResponseTestValues.put( "googleAnalyticsCampaign", "test" );
        leadResponseTestValues.put( "googleAnalyticsContent", "test" );
        leadResponseTestValues.put( "googleAnalyticsMedium", "test" );
        leadResponseTestValues.put( "googleAnalyticsSource", "test" );
        leadResponseTestValues.put( "googleAnalyticsTerm", "test" );
        leadResponseTestValues.put( "unbouncePageVariant", "test" );
        leadResponseTestValues.put( "gclId", "test" );
        leadResponseTestValues.put( "engineId", "test" );
        leadResponseTestValues.put( "recordHistory", "test" );
        leadResponseTestValues.put( "deDupCounter", Integer.valueOf( 10 ) );
        leadResponseTestValues.put( "ownsHome", true );
        leadResponseTestValues.put( "doNotCall", true );
        leadResponseTestValues.put( "doNotEmail", true );
        leadResponseTestValues.put( "referred", true );
        leadResponseTestValues.put( "convertedDate", "test" );
        leadResponseTestValues.put( "ownerId", "test" );
        leadResponseTestValues.put( "closedReason", "test" );
        leadResponseTestValues.put( "propertyCity", "test" );
        leadResponseTestValues.put( "priceRanges", "test" );
        leadResponseTestValues.put( "loanNumber", Integer.valueOf( 10 ) );
        leadResponseTestValues.put( "interestedInFinancing", true );
        leadResponseTestValues.put( "orderId", "test" );
        leadResponseTestValues.put( "ownersAgent", "test" );
        leadResponseTestValues.put( "recordTypeName", "test" );
        leadResponseTestValues.put( "createdById", "test" );
        leadResponseTestValues.put( "lastModifiedById", "test" );
        leadResponseTestValues.put( "createdDateTime", new DateTime() );

    }

    /**
     * Tests the {@link LeadRequest} with default values. Tests the getters
     * and setters of LeadRequest.
     */
//    @Test
    public final void testCRMLeadResponse() {
        final BeanLikeTester blt = new BeanLikeTester( CRMLeadResponse.class );
        blt.testDefaultValues( defaultValues );
        blt.testToString( defaultValues, leadResponseTestValues );
    }
}
