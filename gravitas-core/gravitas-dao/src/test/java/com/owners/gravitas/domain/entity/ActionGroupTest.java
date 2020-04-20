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
 * The Class ActionGroupTest.
 */
public class ActionGroupTest {

    /** The default value. */
    private PropertiesAndValues defaultValues = null;

    /** The test group values. */
    private PropertiesAndValues testActionGroupValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createGroupTestValues();

    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "id", null );
        defaultValues.put( "label", null );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "createdDate", null );
        defaultValues.put( "actions", new ArrayList< Action >() );

    }

    /**
     * Creates the group test values.
     */
    private void createGroupTestValues() {
        testActionGroupValues = new PropertiesAndValues();
        testActionGroupValues.put( "id", "test" );
        testActionGroupValues.put( "label", "test" );
        testActionGroupValues.put( "createdBy", "test" );
        testActionGroupValues.put( "createdDate", new DateTime() );
        testActionGroupValues.put( "actions", new ArrayList< String >() );

    }

    /**
     * Test action.
     */
    @Test
    public final void testActionGroup() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        final BeanLikeTester blt = new BeanLikeTester( ActionGroup.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, testActionGroupValues );
    }

}
