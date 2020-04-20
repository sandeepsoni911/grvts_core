package com.owners.gravitas.dto.request;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;

import com.owners.gravitas.dto.UserTimeZone;

/**
 * The Class AgentLeadRequestTest.
 *
 * @author shivamm
 */
public class AgentLeadRequestTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues agentLeadRequestTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * OpportunityRequest.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createAgentLeadRequestTestValues();
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
        defaultValues.put( "leadType", null );
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
        defaultValues.put( "interestedInFinancing", false );
        defaultValues.put( "loanNumber", 0 );
        defaultValues.put( "propertyState", null );
        defaultValues.put( "orderId", null );
        defaultValues.put( "ownersAgent", null );
        defaultValues.put( "buyerLeadScore", null );
        defaultValues.put( "buyerLeadLabel", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createAgentLeadRequestTestValues() {
        agentLeadRequestTestValues = new PropertiesAndValues();
        agentLeadRequestTestValues.put( "lastName", "test" );
        agentLeadRequestTestValues.put( "firstName", "test" );
        agentLeadRequestTestValues.put( "lastName", "test" );
        agentLeadRequestTestValues.put( "email", "test" );
        agentLeadRequestTestValues.put( "phone", "test" );
        agentLeadRequestTestValues.put( "company", "test" );
        agentLeadRequestTestValues.put( "source", "test" );
        agentLeadRequestTestValues.put( "comments", "test" );
        agentLeadRequestTestValues.put( "leadType", "test" );
        agentLeadRequestTestValues.put( "listingId", "test" );
        agentLeadRequestTestValues.put( "requestType", "test" );
        agentLeadRequestTestValues.put( "leadStatus", "test" );
        agentLeadRequestTestValues.put( "preApprovedForMortgage", "test" );
        agentLeadRequestTestValues.put( "workingWithRealtor", "test" );
        agentLeadRequestTestValues.put( "buyerReadinessTimeline", "test" );
        agentLeadRequestTestValues.put( "marketingOptIn", true );
        agentLeadRequestTestValues.put( "preferredContactTime", "test" );
        agentLeadRequestTestValues.put( "preferredContactMethod", "test" );
        agentLeadRequestTestValues.put( "priceRange", "test" );
        agentLeadRequestTestValues.put( "buyerLeadQuality", "test" );
        agentLeadRequestTestValues.put( "preferredLanguage", "test" );
        agentLeadRequestTestValues.put( "state", "test" );
        agentLeadRequestTestValues.put( "propertyAddress", "test" );
        agentLeadRequestTestValues.put( "mlsId", "test" );
        agentLeadRequestTestValues.put( "alId", "test" );
        agentLeadRequestTestValues.put( "interestedZipcodes", "test" );
        agentLeadRequestTestValues.put( "ownersComIdentifier", "test" );
        agentLeadRequestTestValues.put( "offerAmount", "test" );
        agentLeadRequestTestValues.put( "listingCreationDate", "2016-02-02" );
        agentLeadRequestTestValues.put( "financing", "test" );
        agentLeadRequestTestValues.put( "website", "test" );
        agentLeadRequestTestValues.put( "leadSourceUrl", "test" );
        agentLeadRequestTestValues.put( "savedSearchValues", "test" );
        agentLeadRequestTestValues.put( "earnestMoneyDeposit", "test" );
        agentLeadRequestTestValues.put( "purchaseMethod", "test" );
        agentLeadRequestTestValues.put( "downPayment", "test" );
        agentLeadRequestTestValues.put( "propertyTourInformation", "test" );
        agentLeadRequestTestValues.put( "additionalPropertyData", "test" );
        agentLeadRequestTestValues.put( "websiteSessionData", "test" );
        agentLeadRequestTestValues.put( "ownersVisitorId", "test" );
        agentLeadRequestTestValues.put( "message", "test" );
        agentLeadRequestTestValues.put( "medianPrice", "test" );
        agentLeadRequestTestValues.put( "lastVisitDateTime", "2016-02-02" );
        agentLeadRequestTestValues.put( "mlsPackageType", "test" );
        agentLeadRequestTestValues.put( "googleAnalyticsCampaign", "test" );
        agentLeadRequestTestValues.put( "googleAnalyticsContent", "test" );
        agentLeadRequestTestValues.put( "googleAnalyticsMedium", "test" );
        agentLeadRequestTestValues.put( "googleAnalyticsSource", "test" );
        agentLeadRequestTestValues.put( "googleAnalyticsTerm", "test" );
        agentLeadRequestTestValues.put( "unbouncePageVariant", "test" );
        agentLeadRequestTestValues.put( "gclId", "test" );
        agentLeadRequestTestValues.put( "ownsHome", true );
        agentLeadRequestTestValues.put( "userTimeZone", new UserTimeZone() );
        agentLeadRequestTestValues.put( "interestedInFinancing", true );
        agentLeadRequestTestValues.put( "loanNumber", 10 );
        agentLeadRequestTestValues.put( "propertyState", "test" );
        agentLeadRequestTestValues.put( "orderId", "test" );
        agentLeadRequestTestValues.put( "ownersAgent", "test" );
        agentLeadRequestTestValues.put( "buyerLeadScore", "test" );
        agentLeadRequestTestValues.put( "buyerLeadLabel", "test" );

    }

    /**
     * Tests the {@link AgentLeadRequest} with default values. Tests the getters
     * and setters of AgentLeadRequest.
     */
//    @Test
    public final void testOpportunityRequest() {
        final BeanLikeTester blt = new BeanLikeTester( AgentLeadRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, agentLeadRequestTestValues );
    }

}
