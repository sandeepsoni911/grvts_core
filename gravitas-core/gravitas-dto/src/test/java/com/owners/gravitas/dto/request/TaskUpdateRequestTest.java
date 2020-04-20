package com.owners.gravitas.dto.request;

import java.util.Date;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TaskUpdateRequestTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues testValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * TaskUpdateRequest.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "scheduleDtm", null );
        defaultValues.put( "title", null );
        defaultValues.put( "description", null );
        defaultValues.put( "dueDtm", null );
        defaultValues.put( "location", null );
        defaultValues.put( "titleSelectionReason", null );
        defaultValues.put( "type", null );
        defaultValues.put( "status", null );
        defaultValues.put( "cancellationReason", null );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "primary", false );
        defaultValues.put( "coShoppingId", null );

    }

    /**
     * This method is to create the actual values.
     */
    private void createTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "scheduleDtm", new Date() );
        testValues.put( "title", "test" );
        testValues.put( "description", "test" );
        testValues.put( "dueDtm", new Date() );
        testValues.put( "location", "test" );
        testValues.put( "titleSelectionReason", "test" );
        testValues.put( "type", "test" );
        testValues.put( "status", "Completed" );
        testValues.put( "cancellationReason", null );
        testValues.put( "createdBy", null );
        testValues.put( "primary", false );
        testValues.put( "coShoppingId", "tests" );
    }

    /**
     * Tests the {@link TaskUpdateRequest} with default values. Tests the
     * getters
     * and setters of TaskUpdateRequest.
     */
    @Test(enabled = false)
    public final void testLeadRequest() {
        final BeanLikeTester blt = new BeanLikeTester( TaskUpdateRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
