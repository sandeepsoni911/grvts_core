package com.owners.gravitas.domain;

import java.util.ArrayList;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class ActionGroupTest.
 */
public class ActionGroupTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The test values. */
    private PropertiesAndValues testValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "actions", new ArrayList< Action >() );
        defaultValues.put( "opportunityId", null );
        defaultValues.put( "gravitasGroupId", null );

    }

    /**
     * Creates the test values.
     */
    private void createTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "actions", new ArrayList< String >() );
        testValues.put( "opportunityId", "test" );
        testValues.put( "gravitasGroupId", "test" );

    }

    /**
     * Test action group.
     */
    @Test
    public final void testActionGroup() {
        final BeanLikeTester blt = new BeanLikeTester( ActionGroup.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
