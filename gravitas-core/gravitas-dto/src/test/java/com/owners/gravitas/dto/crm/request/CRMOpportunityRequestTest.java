package com.owners.gravitas.dto.crm.request;

import java.math.BigDecimal;

import org.joda.time.DateTime;
import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;

public class CRMOpportunityRequestTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues opportunityRequestTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * CRMAccess.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createopportunityRequestTestValues();
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
        defaultValues.put( "name", null );
        defaultValues.put( "accountId", null );
        defaultValues.put( "stageName", null );
        defaultValues.put( "leadType", null );
        defaultValues.put( "propertyAddress", null );
        defaultValues.put( "propertyCity", null );
        defaultValues.put( "propertyZip", null );
        defaultValues.put( "probability", null );
        defaultValues.put( "contactId", null );
        defaultValues.put( "preApprovedAmount", null );
        defaultValues.put( "lostReason", null );
        defaultValues.put( "buySideCommisstion", null );
        defaultValues.put( "expectedContractDate", null );
        defaultValues.put( "salesPrice", null );
        defaultValues.put( "packageType", null );
        defaultValues.put( "type", null );
        defaultValues.put( "loanNumber", 0 );
        defaultValues.put( "interestedInFinancing", false );
        defaultValues.put( "label", null );
        defaultValues.put( "recordTypeName", null );
        defaultValues.put( "orderId", null );
        defaultValues.put( "sellerState", null );
        defaultValues.put( "score", null );
        defaultValues.put( "createdDateTime", null );
        defaultValues.put( "lastModifiedDate", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createopportunityRequestTestValues() {
        opportunityRequestTestValues = new PropertiesAndValues();
        opportunityRequestTestValues.put( "firstName", "test" );
        opportunityRequestTestValues.put( "lastName", "test" );
        opportunityRequestTestValues.put( "email", "test" );
        opportunityRequestTestValues.put( "phone", "test" );
        opportunityRequestTestValues.put( "company", "test" );
        opportunityRequestTestValues.put( "source", "test" );
        opportunityRequestTestValues.put( "comments", "test" );
        opportunityRequestTestValues.put( "recordType", "test" );
        opportunityRequestTestValues.put( "listingId", "test" );
        opportunityRequestTestValues.put( "requestType", "test" );
        opportunityRequestTestValues.put( "leadStatus", "test" );
        opportunityRequestTestValues.put( "preApprovedForMortgage", "test" );
        opportunityRequestTestValues.put( "workingWithRealtor", "test" );
        opportunityRequestTestValues.put( "buyerReadinessTimeline", "test" );
        opportunityRequestTestValues.put( "marketingOptIn", true );
        opportunityRequestTestValues.put( "preferredContactTime", "test" );
        opportunityRequestTestValues.put( "preferredContactMethod", "test" );
        opportunityRequestTestValues.put( "priceRange", "test" );
        opportunityRequestTestValues.put( "buyerLeadQuality", "test" );
        opportunityRequestTestValues.put( "preferredLanguage", "test" );
        opportunityRequestTestValues.put( "state", "test" );
        opportunityRequestTestValues.put( "mlsId", "test" );
        opportunityRequestTestValues.put( "alId", "test" );
        opportunityRequestTestValues.put( "interestedZipcodes", "test" );
        opportunityRequestTestValues.put( "ownersComIdentifier", "test" );
        opportunityRequestTestValues.put( "offerAmount", "test" );
        opportunityRequestTestValues.put( "listingCreationDate", new DateTime() );
        opportunityRequestTestValues.put( "financing", "test" );
        opportunityRequestTestValues.put( "website", "test" );
        opportunityRequestTestValues.put( "leadSourceUrl", "test" );
        opportunityRequestTestValues.put( "savedSearchValues", "test" );
        opportunityRequestTestValues.put( "earnestMoneyDeposit", "test" );
        opportunityRequestTestValues.put( "purchaseMethod", "test" );
        opportunityRequestTestValues.put( "downPayment", "test" );
        opportunityRequestTestValues.put( "propertyTourInformation", "test" );
        opportunityRequestTestValues.put( "additionalPropertyData", "test" );
        opportunityRequestTestValues.put( "websiteSessionData", "test" );
        opportunityRequestTestValues.put( "ownersVisitorId", "test" );
        opportunityRequestTestValues.put( "message", "test" );
        opportunityRequestTestValues.put( "medianPrice", "test" );
        opportunityRequestTestValues.put( "lastVisitDateTime", new DateTime() );
        opportunityRequestTestValues.put( "auditRecord", "test" );
        opportunityRequestTestValues.put( "deDuped", true );
        opportunityRequestTestValues.put( "name", "123" );
        opportunityRequestTestValues.put( "accountId", "test.com" );
        opportunityRequestTestValues.put( "stageName", "878787" );
        opportunityRequestTestValues.put( "leadType", "878787" );
        opportunityRequestTestValues.put( "propertyAddress", "454548" );
        opportunityRequestTestValues.put( "propertyCity", "454548" );
        opportunityRequestTestValues.put( "propertyZip", "454548" );
        opportunityRequestTestValues.put( "probability", new Integer( 10 ) );
        opportunityRequestTestValues.put( "contactId", "contactId" );
        opportunityRequestTestValues.put( "preApprovedAmount", new BigDecimal( 10 ) );
        opportunityRequestTestValues.put( "lostReason", "lostReason" );
        opportunityRequestTestValues.put( "buySideCommisstion", "buySideCommisstion" );
        opportunityRequestTestValues.put( "expectedContractDate", new DateTime() );
        opportunityRequestTestValues.put( "salesPrice", new BigDecimal( 10 ) );
        opportunityRequestTestValues.put( "packageType", "TEST" );
        opportunityRequestTestValues.put( "type", "type" );
        opportunityRequestTestValues.put( "loanNumber", 10 );
        opportunityRequestTestValues.put( "label", "test" );
        opportunityRequestTestValues.put( "recordTypeName", "test" );
        opportunityRequestTestValues.put( "interestedInFinancing", true );
        opportunityRequestTestValues.put( "orderId", "test" );
        opportunityRequestTestValues.put( "sellerState", "test" );
        opportunityRequestTestValues.put( "score", "test" );
        opportunityRequestTestValues.put( "createdDateTime", new DateTime() );
        opportunityRequestTestValues.put( "lastModifiedDate", new DateTime() );
    }

    /**
     * Test crm Opportunity request.
     */
    public final void testCRMOpportunityRequest() {
        final BeanLikeTester blt = new BeanLikeTester( CRMOpportunityRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, opportunityRequestTestValues );
        blt.testToString( defaultValues, opportunityRequestTestValues );
    }

}
