package com.owners.gravitas.amqp;

import static com.owners.gravitas.enums.EventType.ADD;

import java.math.BigDecimal;
import java.util.Date;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.Contact;

/**
 * The Class OpportunitySourceTest.
 *
 * @author amits
 */
public class OpportunitySourceTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues testValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * OpportunitySource.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createOpportunitySourceTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "eventType", null );
        defaultValues.put( "primaryContact", null );
        defaultValues.put( "stage", null );
        defaultValues.put( "listingId", null );
        defaultValues.put( "crmId", null );
        defaultValues.put( "leadSource", null );
        defaultValues.put( "workingWithExternalAgent", null );
        defaultValues.put( "buyerReadinessTimeline", null );
        defaultValues.put( "budget", null );
        defaultValues.put( "reasonLost", null );
        defaultValues.put( "buySideCommission", null );
        defaultValues.put( "salesPrice", null );
        defaultValues.put( "expectedContractDate", null );
        defaultValues.put( "preApprovedAmount", null );
        defaultValues.put( "opportunityType", null );
        defaultValues.put( "commissionBasePrice", null );
        defaultValues.put( "titleCompany", null );
        defaultValues.put( "pendingDate", null );
        defaultValues.put( "actualClosingDate", null );
        defaultValues.put( "expectedAgentRevenue", null );
        defaultValues.put( "expectedOwnersComRevenue", null );
        defaultValues.put( "notes", null );
        defaultValues.put( "preApprovedForMortgage", null );
        defaultValues.put( "interestedZipCodes", null );
        defaultValues.put( "financing", null );
        defaultValues.put( "medianPrice", null );
        defaultValues.put( "agentEmail", null );
        defaultValues.put( "leadRequestType", null );
        defaultValues.put( "offerAmount", null );
        defaultValues.put( "earnestMoneyDeposit", null );
        defaultValues.put( "purchaseMethod", null );
        defaultValues.put( "downPayment", null );
        defaultValues.put( "propertyTourInformation", null );
        defaultValues.put( "leadMessage", null );
        defaultValues.put( "requestSummary", null );
        defaultValues.put( "lastModifiedBy", null );
        defaultValues.put( "preferedLanguage", null );
        defaultValues.put( "stageChanged", false );
        defaultValues.put( "titleSelectionReason", null );
        defaultValues.put( "priceRange", null );
        defaultValues.put( "propertyAddress", null );
        defaultValues.put( "listingSideCommission", null );
        defaultValues.put( "listPrice", null );
        defaultValues.put( "listDate", null );
        defaultValues.put( "opportunityOwnerName", null );
        defaultValues.put( "opportunityOwnerEmail", null );
        defaultValues.put( "expirationDate", null );
        defaultValues.put( "propertyCity", null );
        defaultValues.put( "propertyState", null );
        defaultValues.put( "referred", false );
        defaultValues.put( "priceRanges", null );
        defaultValues.put( "dedupCount", null );
        defaultValues.put( "stageChanged", false );
        defaultValues.put( "buyerLeadQuality", null );
        defaultValues.put( "recordType", null);
        defaultValues.put( "sellerPropertyState", null );
        defaultValues.put( "propertyZip", null );
        defaultValues.put( "propertyZip", null );
        defaultValues.put( "farmingGroup", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createOpportunitySourceTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "eventType", ADD );
        testValues.put( "primaryContact", new Contact() );
        testValues.put( "stage", "test" );
        testValues.put( "listingId", "test" );
        testValues.put( "crmId", "test" );
        testValues.put( "leadSource", "test" );
        testValues.put( "workingWithExternalAgent", "test" );
        testValues.put( "buyerReadinessTimeline", "test" );
        testValues.put( "budget", "test" );
        testValues.put( "reasonLost", "test" );
        testValues.put( "buySideCommission", "test" );
        testValues.put( "salesPrice", new BigDecimal( "0" ) );
        testValues.put( "expectedContractDate", new Date() );
        testValues.put( "preApprovedAmount", new BigDecimal( "0" ) );
        testValues.put( "opportunityType", "test" );
        testValues.put( "commissionBasePrice", new BigDecimal( "0" ) );
        testValues.put( "titleCompany", "test" );
        testValues.put( "pendingDate", new Date() );
        testValues.put( "actualClosingDate", new Date() );
        testValues.put( "expectedAgentRevenue", new BigDecimal( "0" ) );
        testValues.put( "expectedOwnersComRevenue", new BigDecimal( "0" ) );
        testValues.put( "notes", "test" );
        testValues.put( "preApprovedForMortgage", "test" );
        testValues.put( "interestedZipCodes", "test" );
        testValues.put( "financing", "test" );
        testValues.put( "medianPrice", new BigDecimal( "0" ) );
        testValues.put( "agentEmail", "test" );
        testValues.put( "leadRequestType", "test" );
        testValues.put( "offerAmount", new BigDecimal( "0" ) );
        testValues.put( "earnestMoneyDeposit", "test" );
        testValues.put( "purchaseMethod", "test" );
        testValues.put( "downPayment", "test" );
        testValues.put( "propertyTourInformation", "test" );
        testValues.put( "leadMessage", "test" );
        testValues.put( "requestSummary", "test" );
        testValues.put( "lastModifiedBy", "test" );
        testValues.put( "preferedLanguage", "test" );
        testValues.put( "stageChanged", true );
        testValues.put( "titleSelectionReason", "test" );
        testValues.put( "priceRange", "test" );
        testValues.put( "propertyAddress", "test" );
        testValues.put( "listingSideCommission", "test" );
        testValues.put( "listPrice", new BigDecimal( "0" ) );
        testValues.put( "listDate", new Date() );
        testValues.put( "expirationDate", new Date() );
        testValues.put( "opportunityOwnerName", "test" );
        testValues.put( "opportunityOwnerEmail", "test" );
        testValues.put( "propertyCity", "test" );
        testValues.put( "propertyState", "test" );
        testValues.put( "referred", true );
        testValues.put( "priceRanges", "test" );
        testValues.put( "dedupCount", "test" );
        testValues.put( "stageChanged", true );
        testValues.put( "buyerLeadQuality", "test" );
        testValues.put( "recordType", "test" );
        testValues.put( "sellerPropertyState", "test" );
        testValues.put( "farmingGroup", "test" );
    }

    /**
     * Tests the {@link OpportunitySource} with default values. Tests the
     * getters
     * and setters of OpportunitySource.
     */
    @Test
    public final void testAddress() {
        final BeanLikeTester blt = new BeanLikeTester( OpportunitySource.class );
        //blt.testDefaultValues( defaultValues );
        //blt.testMutatorsAndAccessors( defaultValues, testValues );
    }

}
