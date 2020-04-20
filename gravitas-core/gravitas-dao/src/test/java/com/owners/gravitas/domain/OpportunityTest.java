package com.owners.gravitas.domain;

import static java.lang.Boolean.TRUE;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class OpportunityTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues opportunityTestValues = null;

    private Opportunity opportunity = new Opportunity();

    /**
     * This method is calls the methods to create default and test values for
     * Opportunity.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createOpportunityTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "contacts", new HashSet<>() );
        defaultValues.put( "stage", null );
        defaultValues.put( "stageHistory", new ArrayList<>() );
        defaultValues.put( "listingIds", new ArrayList<>() );
        defaultValues.put( "crmId", null );
        defaultValues.put( "accepted", null );
        defaultValues.put( "acceptedDtm", null );
        defaultValues.put( "initialContactDtm", null );
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
        defaultValues.put( "financing", null );
        defaultValues.put( "medianPrice", null );
        defaultValues.put( "createdDtm", null );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "requestSummary", null );
        defaultValues.put( "lastModifiedDtm", null );
        defaultValues.put( "preferredLanguage", null );
        defaultValues.put( "deleted", null );
        defaultValues.put( "lastViewedDtm", null );
        defaultValues.put( "titleSelectionReason", null );
        defaultValues.put( "areaOfInterest", null );
        defaultValues.put( "assignedDtm", null );
        defaultValues.put( "priceRange", null );
        defaultValues.put( "propertyAddress", null );
        defaultValues.put( "listingSideCommission", null );
        defaultValues.put( "listPrice", null );
        defaultValues.put( "listDate", null );
        defaultValues.put( "expirationDate", null );
        defaultValues.put( "offerAmount", null );
        defaultValues.put( "firstContactDtm", null );
        defaultValues.put( "actionFlowIds", new ArrayList<>() );
        defaultValues.put( "bestTimeToContact", null );
        defaultValues.put( "isClientExpectingCall", null );
        defaultValues.put( "firstTimeHomeBuyer", null );
        defaultValues.put( "sellingHomeAsPartOfPurchase", null );
        defaultValues.put( "agentOclNotes", null );
        defaultValues.put( "oclReferralStatus", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createOpportunityTestValues() {
        opportunityTestValues = new PropertiesAndValues();
        opportunityTestValues.put( "contacts", new HashSet< String >() );
        opportunityTestValues.put( "stage", "test" );
        opportunityTestValues.put( "stageHistory", new ArrayList<>() );
        opportunityTestValues.put( "listingIds", new ArrayList<>() );
        opportunityTestValues.put( "crmId", "test" );
        opportunityTestValues.put( "accepted", true );
        opportunityTestValues.put( "acceptedDtm", 0L );
        opportunityTestValues.put( "initialContactDtm", 0L );
        opportunityTestValues.put( "leadSource", "test" );
        opportunityTestValues.put( "workingWithExternalAgent", "test" );
        opportunityTestValues.put( "buyerReadinessTimeline", "test" );
        opportunityTestValues.put( "budget", "test" );
        opportunityTestValues.put( "reasonLost", "test" );
        opportunityTestValues.put( "buySideCommission", "test" );
        opportunityTestValues.put( "salesPrice", new BigDecimal( 0 ) );
        opportunityTestValues.put( "expectedContractDate", new Date() );
        opportunityTestValues.put( "preApprovedAmount", new BigDecimal( 0 ) );
        opportunityTestValues.put( "opportunityType", "test" );
        opportunityTestValues.put( "commissionBasePrice", new BigDecimal( 0 ) );
        opportunityTestValues.put( "titleCompany", "test" );
        opportunityTestValues.put( "pendingDate", new Date() );
        opportunityTestValues.put( "actualClosingDate", new Date() );
        opportunityTestValues.put( "expectedAgentRevenue", new BigDecimal( 0 ) );
        opportunityTestValues.put( "expectedOwnersComRevenue", new BigDecimal( 0 ) );
        opportunityTestValues.put( "notes", "test" );
        opportunityTestValues.put( "preApprovedForMortgage", "test" );
        opportunityTestValues.put( "financing", "test" );
        opportunityTestValues.put( "medianPrice", new BigDecimal( 0 ) );
        opportunityTestValues.put( "createdDtm", 0L );
        opportunityTestValues.put( "createdBy", "test" );
        opportunityTestValues.put( "requestSummary", "test" );
        opportunityTestValues.put( "lastModifiedDtm", 0L );
        opportunityTestValues.put( "preferredLanguage", "test" );
        opportunityTestValues.put( "deleted", TRUE );
        opportunityTestValues.put( "lastViewedDtm", 0L );
        opportunityTestValues.put( "titleSelectionReason", "test" );
        opportunityTestValues.put( "areaOfInterest", "test" );
        opportunityTestValues.put( "assignedDtm", 0L );
        opportunityTestValues.put( "priceRange", "test" );
        opportunityTestValues.put( "propertyAddress", "test" );
        opportunityTestValues.put( "listingSideCommission", "test" );
        opportunityTestValues.put( "listPrice", new BigDecimal( 0 ) );
        opportunityTestValues.put( "listDate", new Date() );
        opportunityTestValues.put( "expirationDate", new Date() );
        opportunityTestValues.put( "offerAmount", new BigDecimal( 0 ) );
        opportunityTestValues.put( "firstContactDtm", 2l );
        opportunityTestValues.put( "actionFlowIds", new ArrayList<>() );
        opportunityTestValues.put( "bestTimeToContact", "anyTime" );
        opportunityTestValues.put( "isClientExpectingCall", "No" );
        opportunityTestValues.put( "firstTimeHomeBuyer", "No" );
        opportunityTestValues.put( "sellingHomeAsPartOfPurchase", "No" );
        opportunityTestValues.put( "agentOclNotes", "My notes" );
        opportunityTestValues.put( "oclReferralStatus", "Not Eligible" );
    }

    /**
     * Tests the {@link PatchNote} with default values. Tests the getters
     * and setters of PatchNote.
     */
    @Test
    public final void testOpportunity() {
        final BeanLikeTester blt = new BeanLikeTester( Opportunity.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, opportunityTestValues );
    }

    /**
     * Test add stage
     */
    @Test
    public final void testAddStage() {
        final Stage stage = new Stage();
        opportunity.addStage( stage );
        assertEquals( opportunity.getStageHistory().iterator().next(), stage );

    }

    /**
     * Test add contact
     */
    @Test
    public final void testAddContact() {
        final String test = "test";
        opportunity.addContact( test );
        assertEquals( opportunity.getContacts().iterator().next(), test );
    }

    /**
     * Test add listing id
     */
    @Test
    public final void testAddListingId() {
        final String test = "test";
        opportunity.addListingId( "test" );
        assertEquals( opportunity.getListingIds().iterator().next(), test );
    }

    /**
     * Test pop stage with not empty list.
     */
    @Test
    public final void testPopStage_WithNotEmptyList() {
        final List< Stage > stageHistory = new ArrayList<>();
        final Stage stage = new Stage();
        stageHistory.add( stage );
        final Opportunity opportunity = new Opportunity();
        opportunity.setStageHistory( stageHistory );
        assertEquals( opportunity.popStage(), stage );
    }

    /**
     * Test pop stage with empty list.
     */
    @Test( )
    public final void testPopStage_WithEmptyList() {
        final Opportunity opportunity = new Opportunity();
        opportunity.popStage();
    }
}
