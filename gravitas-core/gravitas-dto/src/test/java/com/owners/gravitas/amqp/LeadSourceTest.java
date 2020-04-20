package com.owners.gravitas.amqp;

import org.joda.time.DateTime;
import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;

import com.owners.gravitas.dto.request.LeadRequest;

/**
 * @author harshads
 *
 */
public class LeadSourceTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues leadSourceTestValues = null;

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
        defaultValues.put( "priceRanges", null );
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
        defaultValues.put( "recordTypeName", null );
        defaultValues.put( "interestedInFinancing", false );
        defaultValues.put( "loanNumber", 0 );
        defaultValues.put( "propertyCity", null );
        defaultValues.put( "createdById", null );
        defaultValues.put( "lastModifiedById", null );
        defaultValues.put( "createdDateTime", null );

    }

    /**
     * This method is to create the actual values.
     */
    private void createleadResponseTestValues() {
        leadSourceTestValues = new PropertiesAndValues();
        leadSourceTestValues.put( "firstName", "test" );
        leadSourceTestValues.put( "lastName", "test" );
        leadSourceTestValues.put( "email", "test" );
        leadSourceTestValues.put( "phone", "test" );
        leadSourceTestValues.put( "company", "test" );
        leadSourceTestValues.put( "source", "test" );
        leadSourceTestValues.put( "comments", "test" );
        leadSourceTestValues.put( "propertyCity", "test" );
        leadSourceTestValues.put( "recordType", "test" );
        leadSourceTestValues.put( "listingId", "test" );
        leadSourceTestValues.put( "requestType", "test" );
        leadSourceTestValues.put( "leadStatus", "test" );
        leadSourceTestValues.put( "preApprovedForMortgage", "test" );
        leadSourceTestValues.put( "workingWithRealtor", "test" );
        leadSourceTestValues.put( "buyerReadinessTimeline", "test" );
        leadSourceTestValues.put( "marketingOptIn", true );
        leadSourceTestValues.put( "preferredContactTime", "test" );
        leadSourceTestValues.put( "preferredContactMethod", "test" );
        leadSourceTestValues.put( "priceRange", "test" );
        leadSourceTestValues.put( "priceRanges", "test" );
        leadSourceTestValues.put( "buyerLeadQuality", "test" );
        leadSourceTestValues.put( "preferredLanguage", "test" );
        leadSourceTestValues.put( "state", "test" );
        leadSourceTestValues.put( "propertyAddress", "test" );
        leadSourceTestValues.put( "mlsId", "test" );
        leadSourceTestValues.put( "alId", "test" );
        leadSourceTestValues.put( "interestedZipcodes", "test" );
        leadSourceTestValues.put( "ownersComIdentifier", "test" );
        leadSourceTestValues.put( "offerAmount", "test" );
        leadSourceTestValues.put( "listingCreationDate", new DateTime() );
        leadSourceTestValues.put( "financing", "test" );
        leadSourceTestValues.put( "website", "test" );
        leadSourceTestValues.put( "leadSourceUrl", "test" );
        leadSourceTestValues.put( "savedSearchValues", "test" );
        leadSourceTestValues.put( "earnestMoneyDeposit", "test" );
        leadSourceTestValues.put( "purchaseMethod", "test" );
        leadSourceTestValues.put( "downPayment", "test" );
        leadSourceTestValues.put( "propertyTourInformation", "test" );
        leadSourceTestValues.put( "additionalPropertyData", "test" );
        leadSourceTestValues.put( "websiteSessionData", "test" );
        leadSourceTestValues.put( "ownersVisitorId", "test" );
        leadSourceTestValues.put( "message", "test" );
        leadSourceTestValues.put( "medianPrice", "test" );
        leadSourceTestValues.put( "lastVisitDateTime", new DateTime() );
        leadSourceTestValues.put( "auditRecord", "test" );
        leadSourceTestValues.put( "deDuped", true );
        leadSourceTestValues.put( "id", "test" );
        leadSourceTestValues.put( "mlsPackageType", "test" );
        leadSourceTestValues.put( "googleAnalyticsCampaign", "test" );
        leadSourceTestValues.put( "googleAnalyticsContent", "test" );
        leadSourceTestValues.put( "googleAnalyticsMedium", "test" );
        leadSourceTestValues.put( "googleAnalyticsSource", "test" );
        leadSourceTestValues.put( "googleAnalyticsTerm", "test" );
        leadSourceTestValues.put( "unbouncePageVariant", "test" );
        leadSourceTestValues.put( "gclId", "test" );
        leadSourceTestValues.put( "engineId", "test" );
        leadSourceTestValues.put( "recordHistory", "test" );
        leadSourceTestValues.put( "deDupCounter", Integer.valueOf( 10 ) );
        leadSourceTestValues.put( "ownsHome", true );
        leadSourceTestValues.put( "doNotCall", true );
        leadSourceTestValues.put( "doNotEmail", true );
        leadSourceTestValues.put( "referred", true );
        leadSourceTestValues.put( "convertedDate", "test" );
        leadSourceTestValues.put( "ownerId", "test" );
        leadSourceTestValues.put( "closedReason", "test" );
        leadSourceTestValues.put( "recordTypeName", "test" );
        leadSourceTestValues.put( "interestedInFinancing", true );
        leadSourceTestValues.put( "loanNumber", 1 );
        leadSourceTestValues.put( "lastModifiedById", "test" );
        leadSourceTestValues.put( "createdById", "test" );
        leadSourceTestValues.put( "createdDateTime", new DateTime() );
    }

    /**
     * Tests the {@link LeadRequest} with default values. Tests the getters
     * and setters of LeadRequest.
     */
//    @Test
    public final void testCRMLeadResponse() {
        final BeanLikeTester blt = new BeanLikeTester( LeadSource.class );
        blt.testDefaultValues( defaultValues );
        blt.testToString( defaultValues, leadSourceTestValues );
    }
}
