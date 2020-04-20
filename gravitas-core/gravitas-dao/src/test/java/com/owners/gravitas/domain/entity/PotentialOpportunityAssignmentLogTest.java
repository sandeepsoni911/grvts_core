package com.owners.gravitas.domain.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class PotentialOpportunityAssignmentLogTest.
 *
 * @author amits
 */
public class PotentialOpportunityAssignmentLogTest {

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
        defaultValues.put( "crmId", null );
        defaultValues.put( "opportunityLabel", null );
        defaultValues.put( "opportunityScore", null );
        defaultValues.put( "zip", null );
        defaultValues.put( "state", null );
        defaultValues.put( "opportunityThreshold", 0 );
        defaultValues.put( "dayThreshold", 0 );
        defaultValues.put( "rrThreshold", 0 );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "createdDate", null );
        defaultValues.put( "agents", null );
    }

    /**
     * Creates the opportunity test values.
     */
    private void createOpportunityTestValues() {
        opportunityValues = new PropertiesAndValues();
        opportunityValues.put( "id", "test" );
        opportunityValues.put( "crmId", "test" );
        opportunityValues.put( "opportunityLabel", "test" );
        opportunityValues.put( "opportunityScore", "test" );
        opportunityValues.put( "zip", "test" );
        opportunityValues.put( "state", "test" );
        opportunityValues.put( "opportunityThreshold", 0 );
        opportunityValues.put( "dayThreshold", 0 );
        opportunityValues.put( "rrThreshold", 0 );
        opportunityValues.put( "createdBy", "test" );
        opportunityValues.put( "createdDate", new DateTime() );
        opportunityValues.put( "agents", new ArrayList< PotentialOpportunityAgentAssignmentLog >() );
    }

    /**
     * Test opportunity.
     */
    @Test
    public final void testOpportunity() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        final BeanLikeTester blt = new BeanLikeTester( PotentialOpportunityAssignmentLog.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, opportunityValues );
    }
}
