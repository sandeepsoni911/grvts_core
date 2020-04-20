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
 * The Class StageLogTest.
 *
 * @author amits
 */
public class StageLogTest {

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
        defaultValues.put( "stage", null );
        defaultValues.put( "opportunity", null );
    }

    /**
     * Creates the opportunity test values.
     */
    private void createOpportunityTestValues() {
        opportunityValues = new PropertiesAndValues();
        opportunityValues.put( "id", "test" );
        opportunityValues.put( "createdBy", "test" );
        opportunityValues.put( "createdDate", new DateTime() );
        opportunityValues.put( "stage", "test" );
        opportunityValues.put( "opportunity", new Opportunity() );
    }

    /**
     * Test opportunity.
     */
    @Test
    public final void testStageLog() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        final BeanLikeTester blt = new BeanLikeTester( StageLog.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, opportunityValues );
    }
}
