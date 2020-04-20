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
 * The Class ActionTest1.
 *
 * @author shivamm
 */
public class ActionTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The opportunity values. */
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
        defaultValues.put( "id", null );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "createdDate", null );
        defaultValues.put( "lastModifiedBy", null );
        defaultValues.put( "lastModifiedDate", null );
        defaultValues.put( "actionName", null );
        defaultValues.put( "order", null );
    }

    /**
     * Creates the opportunity test values.
     */
    private void createTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "id", "test" );
        testValues.put( "createdBy", "test1" );
        testValues.put( "createdDate", new DateTime() );
        testValues.put( "lastModifiedBy", "test1" );
        testValues.put( "lastModifiedDate", new DateTime() );
        testValues.put( "actionName", "testid" );
        testValues.put( "order", "testorder" );
    }

    /**
     * Test action.
     */
    @Test
    public final void testAction() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        final BeanLikeTester blt = new BeanLikeTester( Action.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
