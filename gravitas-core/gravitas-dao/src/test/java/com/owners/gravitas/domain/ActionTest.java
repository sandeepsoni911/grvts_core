package com.owners.gravitas.domain;

import static org.junit.Assert.assertEquals;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class ActionTest.
 */
public class ActionTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The test values. */
    private PropertiesAndValues testActionValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createActionTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "name", null );
        defaultValues.put( "complete", false );
        defaultValues.put( "statusReason", null );
        defaultValues.put( "order", null );
        defaultValues.put( "lastModifiedDtm", null );
        defaultValues.put( "lastVisitedDtm", null );

    }

    /**
     * Creates the action test values.
     */
    private void createActionTestValues() {
        testActionValues = new PropertiesAndValues();
        testActionValues.put( "name", "test" );
        testActionValues.put( "complete", true );
        testActionValues.put( "statusReason", "test" );
        testActionValues.put( "order", "test" );
        testActionValues.put( "lastModifiedDtm", 0L );
        testActionValues.put( "lastVisitedDtm", null );

    }

    /**
     * Test action.
     */
    @Test
    public final void testAction() {
        final BeanLikeTester blt = new BeanLikeTester( Action.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testActionValues );
    }

    /**
     * Test name.
     */
    @Test
    public final void testName() {
        String name = "test";
        Action action = new Action();
        action.setName( name );
        assertEquals( action.getName(), name );
    }
}
