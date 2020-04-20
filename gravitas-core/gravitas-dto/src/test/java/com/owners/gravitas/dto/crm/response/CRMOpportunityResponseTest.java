package com.owners.gravitas.dto.crm.response;

import java.math.BigDecimal;

import org.joda.time.DateTime;
import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;

public class CRMOpportunityResponseTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues opportunityResponseTestValues = null;

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
        defaultValues.put( "type", null );
        defaultValues.put( "id", null );
        defaultValues.put( "savedId", null );
        defaultValues.put( "packageType", null );
        defaultValues.put( "loanNumber", 0 );
        defaultValues.put( "interestedInFinancing", false );
        defaultValues.put( "label", null );
        defaultValues.put( "recordTypeName", null );
        defaultValues.put( "orderId", null );
        defaultValues.put( "sellerState", null );
        defaultValues.put( "score", null );
        defaultValues.put( "createdDateTime", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createopportunityRequestTestValues() {
        opportunityResponseTestValues = new PropertiesAndValues();
        opportunityResponseTestValues.put( "firstName", "test" );
        opportunityResponseTestValues.put( "lastName", "test" );
        opportunityResponseTestValues.put( "email", "test" );
        opportunityResponseTestValues.put( "phone", "test" );
        opportunityResponseTestValues.put( "company", "test" );
        opportunityResponseTestValues.put( "source", "test" );
        opportunityResponseTestValues.put( "comments", "test" );
        opportunityResponseTestValues.put( "recordType", "test" );
        opportunityResponseTestValues.put( "listingId", "test" );
        opportunityResponseTestValues.put( "requestType", "test" );
        opportunityResponseTestValues.put( "leadStatus", "test" );
        opportunityResponseTestValues.put( "preApprovedForMortgage", "test" );
        opportunityResponseTestValues.put( "workingWithRealtor", "test" );
        opportunityResponseTestValues.put( "buyerReadinessTimeline", "test" );
        opportunityResponseTestValues.put( "marketingOptIn", true );
        opportunityResponseTestValues.put( "preferredContactTime", "test" );
        opportunityResponseTestValues.put( "preferredContactMethod", "test" );
        opportunityResponseTestValues.put( "priceRange", "test" );
        opportunityResponseTestValues.put( "buyerLeadQuality", "test" );
        opportunityResponseTestValues.put( "preferredLanguage", "test" );
        opportunityResponseTestValues.put( "state", "test" );
        opportunityResponseTestValues.put( "mlsId", "test" );
        opportunityResponseTestValues.put( "alId", "test" );
        opportunityResponseTestValues.put( "interestedZipcodes", "test" );
        opportunityResponseTestValues.put( "ownersComIdentifier", "test" );
        opportunityResponseTestValues.put( "offerAmount", "test" );
        opportunityResponseTestValues.put( "listingCreationDate", new DateTime() );
        opportunityResponseTestValues.put( "financing", "test" );
        opportunityResponseTestValues.put( "website", "test" );
        opportunityResponseTestValues.put( "leadSourceUrl", "test" );
        opportunityResponseTestValues.put( "savedSearchValues", "test" );
        opportunityResponseTestValues.put( "earnestMoneyDeposit", "test" );
        opportunityResponseTestValues.put( "purchaseMethod", "test" );
        opportunityResponseTestValues.put( "downPayment", "test" );
        opportunityResponseTestValues.put( "propertyTourInformation", "test" );
        opportunityResponseTestValues.put( "additionalPropertyData", "test" );
        opportunityResponseTestValues.put( "websiteSessionData", "test" );
        opportunityResponseTestValues.put( "ownersVisitorId", "test" );
        opportunityResponseTestValues.put( "message", "test" );
        opportunityResponseTestValues.put( "medianPrice", "test" );
        opportunityResponseTestValues.put( "lastVisitDateTime", new DateTime() );
        opportunityResponseTestValues.put( "auditRecord", "test" );
        opportunityResponseTestValues.put( "deDuped", true );
        opportunityResponseTestValues.put( "name", "123" );
        opportunityResponseTestValues.put( "accountId", "test.com" );
        opportunityResponseTestValues.put( "stageName", "878787" );
        opportunityResponseTestValues.put( "leadType", "878787" );
        opportunityResponseTestValues.put( "propertyAddress", "454548" );
        opportunityResponseTestValues.put( "propertyCity", "454548" );
        opportunityResponseTestValues.put( "propertyZip", "454548" );
        opportunityResponseTestValues.put( "probability", new Integer( 10 ) );
        opportunityResponseTestValues.put( "contactId", "contactId" );
        opportunityResponseTestValues.put( "preApprovedAmount", new BigDecimal( 10 ) );
        opportunityResponseTestValues.put( "lostReason", "lostReason" );
        opportunityResponseTestValues.put( "buySideCommisstion", "buySideCommisstion" );
        opportunityResponseTestValues.put( "expectedContractDate", new DateTime() );
        opportunityResponseTestValues.put( "salesPrice", new BigDecimal( 10 ) );
        opportunityResponseTestValues.put( "type", "type" );
        opportunityResponseTestValues.put( "id", "id" );
        opportunityResponseTestValues.put( "savedId", "test" );
        opportunityResponseTestValues.put( "packageType", "test" );
        opportunityResponseTestValues.put( "loanNumber", 10 );
        opportunityResponseTestValues.put( "interestedInFinancing", true );
        opportunityResponseTestValues.put( "label", "test" );
        opportunityResponseTestValues.put( "recordTypeName","test" );
        opportunityResponseTestValues.put( "orderId","test" );
        opportunityResponseTestValues.put( "sellerState","test" );
        opportunityResponseTestValues.put( "score", "test" );
        opportunityResponseTestValues.put( "createdDateTime", new DateTime());
    }

    /**
     * Test crm opportunity response.
     */
    public final void testCRMOpportunityResponse() {
        final BeanLikeTester blt = new BeanLikeTester( CRMOpportunityResponse.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, opportunityResponseTestValues );
        blt.testToString( defaultValues, opportunityResponseTestValues );
    }

}
