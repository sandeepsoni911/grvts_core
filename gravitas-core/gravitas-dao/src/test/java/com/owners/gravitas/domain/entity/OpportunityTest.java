package com.owners.gravitas.domain.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.joda.time.DateTime;
import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class OpportunityV1Test.
 *
 * @author amits
 */
public class OpportunityTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The opportunity values. */
    private PropertiesAndValues opportunityValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createOpportunityTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "id", null );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "createdDate", null );
        defaultValues.put( "lastModifiedBy", null );
        defaultValues.put( "lastModifiedDate", null );
        defaultValues.put( "opportunityId", null );
        defaultValues.put( "deleted", false );
        defaultValues.put( "assignedAgentId", null );
        defaultValues.put( "responseTime", null );
        defaultValues.put( "firstContactDtm", null );
        defaultValues.put( "assignedDate", null );
        defaultValues.put( "listingIdDetails", null );
        defaultValues.put( "agentOclNotes", null );
        defaultValues.put( "oclReferralStatus", null );
        defaultValues.put( "contact", null );
    }

    /**
     * Creates the opportunity test values.
     */
    private void createOpportunityTestValues() {
        opportunityValues = new PropertiesAndValues();
        opportunityValues.put( "id", "test" );
        opportunityValues.put( "createdBy", "test1" );
        opportunityValues.put( "createdDate", new DateTime() );
        opportunityValues.put( "lastModifiedBy", "test1" );
        opportunityValues.put( "lastModifiedDate", new DateTime() );
        opportunityValues.put( "opportunityId", "testid" );
        opportunityValues.put( "assignedAgentId", "testassignedagentid" );
        opportunityValues.put( "assignedDate", new DateTime() );
        opportunityValues.put( "deleted", true );
        opportunityValues.put( "responseTime", 123L );
        opportunityValues.put( "firstContactDtm", new DateTime() );
        opportunityValues.put( "listingIdDetails", new HashSet< ListingIdDetails >() );
        opportunityValues.put( "agentOclNotes", "Sample Notes..." );
        opportunityValues.put( "oclReferralStatus", "Eligible" );
        opportunityValues.put( "contact", new Contact() );
    }

    /**
     * Test opportunity.
     */
    @Test
    public final void testOpportunityV1() {
        final ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        final List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        final BeanLikeTester blt = new BeanLikeTester( Opportunity.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, opportunityValues );
    }
}
