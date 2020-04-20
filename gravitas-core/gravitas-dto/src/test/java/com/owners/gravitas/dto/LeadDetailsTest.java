package com.owners.gravitas.dto;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The class Lead Details test
 * 
 * @author imranmoh
 *
 */
public class LeadDetailsTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;

    /**
     * for testing values.
     */
    private PropertiesAndValues leadDetailsTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * LeadDetails.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createLeadDetailsTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "leadName", null );
        defaultValues.put( "score", null );
        defaultValues.put( "crmId", null );
        defaultValues.put( "state", null );
        defaultValues.put( "email", null );
        defaultValues.put( "phone", null );
        defaultValues.put( "status", null );
        defaultValues.put( "createdDate", null );
        defaultValues.put( "company", null );
        defaultValues.put( "source", null );
        defaultValues.put( "stage", null );
        
        defaultValues.put( "marketingOptIn", null );
        defaultValues.put( "leadSourceUrl", null );
        defaultValues.put( "ownersVisitorId", null );
        defaultValues.put( "ownHome", null );
        defaultValues.put( "interestedInFinancing", null );
        defaultValues.put( "loanNumber", null );
        defaultValues.put( "requestType", null );
        defaultValues.put( "interestedZipCodes", null );
        defaultValues.put( "buyerLeadQuality", null );
        defaultValues.put( "preferredContactmethod", null );
        defaultValues.put( "preferredContactTime", null );
        defaultValues.put( "preferredLanguage", null );
        defaultValues.put( "lastVisitDateTime", null );
        defaultValues.put( "workingWithRealtor", null );
        defaultValues.put( "buyerReadinessTimeline", null );
        defaultValues.put( "comments", null );
        defaultValues.put( "financing", null );
        defaultValues.put( "priceRange", null );
        defaultValues.put( "medianPrice", null );
        defaultValues.put( "additionalPropertyData", null );
        defaultValues.put( "savedSearchValues", null );
        defaultValues.put( "websiteSessionData", null );
        defaultValues.put( "preApprovedForMortgage", null );
        defaultValues.put( "offerAmount", null );
        defaultValues.put( "earnestMoneyDeposit", null );
        defaultValues.put( "purchaseMethod", null );
        defaultValues.put( "downPayment", null );
        defaultValues.put( "propertyTourInformation", null );
        defaultValues.put( "mlsId", null );
        defaultValues.put( "ownersComIdentifier", null );
        defaultValues.put( "propertyAddress", null );
        defaultValues.put( "alId", null );
        defaultValues.put( "mlsPackageType", null );
        defaultValues.put( "listingCreationDate", null );
        defaultValues.put( "unbouncePageVariant", null );
        defaultValues.put( "searchCity", null );
        defaultValues.put( "searchAttributes", null );
        defaultValues.put( "propertyType", null );
        defaultValues.put( "propertySquareFoot", null );
        defaultValues.put( "propertyCity", null );
        defaultValues.put( "propertyBedroom", null );
        defaultValues.put( "partnerIdentifier", null );
        defaultValues.put( "orderId", null );
        defaultValues.put( "inquiryDate", null );
        defaultValues.put( "gclId", null );
        defaultValues.put( "farmingSystemActions", null );
        defaultValues.put( "farmingStatus", null );
        defaultValues.put( "farmingGroup", null );
        defaultValues.put( "farmingfailureCode", null );
        defaultValues.put( "farmingBuyerActions", null );
        defaultValues.put( "buyerLeadLabel", null );
        defaultValues.put( "propertyBathroom", null );
        defaultValues.put( "notes", null );
        defaultValues.put( "website", null );
        defaultValues.put( "message", null );
    }

    /**
     * This method is to create the test values.
     */
    private void createLeadDetailsTestValues() {
        leadDetailsTestValues = new PropertiesAndValues();
        leadDetailsTestValues.put( "leadName", "testLead" );
        leadDetailsTestValues.put( "score", "98.9654" );
        leadDetailsTestValues.put( "crmId", "356fsdsfd4564" );
        leadDetailsTestValues.put( "state", "AF" );
        leadDetailsTestValues.put( "email", "testemail@test.com" );
        leadDetailsTestValues.put( "phone", "35546546344" );
        leadDetailsTestValues.put( "status", "claimed" );
        leadDetailsTestValues.put( "createdDate", "12/25/2018" );
        leadDetailsTestValues.put( "company", "LOPEZ CARLOS (clpiraquive@gmail.com)" );
        leadDetailsTestValues.put( "source", "Zillow" );
        leadDetailsTestValues.put( "stage", "New" );
        leadDetailsTestValues.put( "marketingOptIn", "" );
        leadDetailsTestValues.put( "leadSourceUrl", "" );
        leadDetailsTestValues.put( "ownersVisitorId", "" );
        leadDetailsTestValues.put( "ownHome", "" );
        leadDetailsTestValues.put( "interestedInFinancing", "" );
        leadDetailsTestValues.put( "loanNumber", "" );
        leadDetailsTestValues.put( "requestType", "" );
        leadDetailsTestValues.put( "interestedZipCodes", "" );
        leadDetailsTestValues.put( "buyerLeadQuality", "" );
        leadDetailsTestValues.put( "preferredContactmethod", "" );
        leadDetailsTestValues.put( "preferredContactTime", "" );
        leadDetailsTestValues.put( "preferredLanguage", "" );
        leadDetailsTestValues.put( "lastVisitDateTime", "" );
        leadDetailsTestValues.put( "workingWithRealtor", "" );
        leadDetailsTestValues.put( "buyerReadinessTimeline", "" );
        leadDetailsTestValues.put( "comments", "" );
        leadDetailsTestValues.put( "financing", "" );
        leadDetailsTestValues.put( "priceRange", "" );
        leadDetailsTestValues.put( "medianPrice", "" );
        leadDetailsTestValues.put( "additionalPropertyData", "" );
        leadDetailsTestValues.put( "savedSearchValues", "" );
        leadDetailsTestValues.put( "websiteSessionData", "" );
        leadDetailsTestValues.put( "preApprovedForMortgage", "" );
        leadDetailsTestValues.put( "offerAmount", "" );
        leadDetailsTestValues.put( "earnestMoneyDeposit", "" );
        leadDetailsTestValues.put( "purchaseMethod", "" );
        leadDetailsTestValues.put( "downPayment", "" );
        leadDetailsTestValues.put( "propertyTourInformation", "" );
        leadDetailsTestValues.put( "mlsId", "" );
        leadDetailsTestValues.put( "ownersComIdentifier", "" );
        leadDetailsTestValues.put( "propertyAddress", "" );
        leadDetailsTestValues.put( "alId", "" );
        leadDetailsTestValues.put( "mlsPackageType", "" );
        leadDetailsTestValues.put( "listingCreationDate", "" );
        leadDetailsTestValues.put( "unbouncePageVariant", "" );
        leadDetailsTestValues.put( "searchCity", "" );
        leadDetailsTestValues.put( "searchAttributes", "" );
        leadDetailsTestValues.put( "propertyType", "" );
        leadDetailsTestValues.put( "propertySquareFoot", "" );
        leadDetailsTestValues.put( "propertyCity", "" );
        leadDetailsTestValues.put( "propertyBedroom", "" );
        leadDetailsTestValues.put( "partnerIdentifier", "" );
        leadDetailsTestValues.put( "orderId", "" );
        leadDetailsTestValues.put( "inquiryDate", "" );
        leadDetailsTestValues.put( "gclId", "" );
        leadDetailsTestValues.put( "farmingSystemActions", "" );
        leadDetailsTestValues.put( "farmingStatus", "" );
        leadDetailsTestValues.put( "farmingGroup", "" );
        leadDetailsTestValues.put( "farmingfailureCode", "" );
        leadDetailsTestValues.put( "farmingBuyerActions", "" );
        leadDetailsTestValues.put( "buyerLeadLabel", "" );
        leadDetailsTestValues.put( "propertyBathroom", "" );
        leadDetailsTestValues.put( "notes", "" );
        leadDetailsTestValues.put( "website", "" );
        leadDetailsTestValues.put( "message", "" );
    }

    /**
     * Tests the {@link OpportunityDetails} with default values. Tests the
     * getters
     * and setters of OpportunityDetails.
     */
    @Test
    public final void testLeadDetails() {
        final BeanLikeTester blt = new BeanLikeTester( LeadDetails.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, leadDetailsTestValues );
    }
}
