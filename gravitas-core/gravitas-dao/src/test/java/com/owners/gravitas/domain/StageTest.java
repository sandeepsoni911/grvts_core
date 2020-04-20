package com.owners.gravitas.domain;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.domain.Stage;

public class StageTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues addressTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * Stage.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createAddressTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "stage", null );
        defaultValues.put( "timestamp", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createAddressTestValues() {
        addressTestValues = new PropertiesAndValues();
        addressTestValues.put( "stage", null );
        addressTestValues.put( "timestamp", null );
    }

    /**
     * Tests the {@link Stage} with default values. Tests the getters
     * and setters of Stage.
     */
    @Test
    public final void testStage() {
        final BeanLikeTester blt = new BeanLikeTester( Stage.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, addressTestValues );
    }
}
