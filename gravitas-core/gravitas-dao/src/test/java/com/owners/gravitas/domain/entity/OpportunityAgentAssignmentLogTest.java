package com.owners.gravitas.domain.entity;

import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class OpportunityAgentAssignmentLogTest.
 *
 * @author amits
 */
public class OpportunityAgentAssignmentLogTest {

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
        defaultValues.put( "opportunity", null );
        defaultValues.put( "priority", 0 );
        defaultValues.put( "agentEmail", null );
        defaultValues.put( "agentScore", null );
        defaultValues.put( "agentStatus", null );
        defaultValues.put( "status", null );
        defaultValues.put( "createdDate", null );
        defaultValues.put( "lastModifiedDate", null );
        defaultValues.put( "lastModifiedBy", null );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "id", null );
    }

    /**
     * Creates the opportunity test values.
     */
    private void createOpportunityTestValues() {
        opportunityValues = new PropertiesAndValues();
        opportunityValues.put( "opportunity", new PotentialOpportunityAssignmentLog() );
        opportunityValues.put( "priority", 0 );
        opportunityValues.put( "agentEmail", "test" );
        opportunityValues.put( "agentScore", "test" );
        opportunityValues.put( "agentStatus", "test" );
        opportunityValues.put( "status", "test" );
        opportunityValues.put( "createdDate", new DateTime() );
        opportunityValues.put( "lastModifiedDate", new DateTime() );
        opportunityValues.put( "lastModifiedBy", "test" );
        opportunityValues.put( "createdBy", "test" );
        opportunityValues.put( "id", "test" );
    }

    /**
     * Test opportunity.
     */
    @Test
    public final void testOpportunity() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        final BeanLikeTester blt = new BeanLikeTester( PotentialOpportunityAgentAssignmentLog.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, opportunityValues );
    }
}
