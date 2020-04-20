package com.owners.gravitas.dto;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * This is test class for OclEmailDtoTest.
 *
 * @author vishwanathm
 *
 */
public class OclEmailDtoTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues errorDetailsTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * ErrorDetail.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createErrorDetailTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "email", null );
        defaultValues.put( "firstName", null );
        defaultValues.put( "lastName", null );
        defaultValues.put( "loanPhase", null );
        defaultValues.put( "triggerEvent", null );
        defaultValues.put( "assignedMLO", null );
        defaultValues.put( "statusDate", null );
        defaultValues.put( "note", null );
        defaultValues.put( "loanNumber", null );
    }

    /**
     * This method is to create the default values.
     */
    private void createErrorDetailTestValues() {
        errorDetailsTestValues = new PropertiesAndValues();
        errorDetailsTestValues.put( "email", "test" );
        errorDetailsTestValues.put( "firstName", "test" );
        errorDetailsTestValues.put( "lastName", "test" );
        errorDetailsTestValues.put( "loanPhase", "test" );
        errorDetailsTestValues.put( "triggerEvent", "test" );
        errorDetailsTestValues.put( "assignedMLO", "test" );
        errorDetailsTestValues.put( "statusDate", "test" );
        errorDetailsTestValues.put( "note", "test" );
        errorDetailsTestValues.put( "loanNumber", "test" );
    }

    /**
     * Tests the RoleDetail with default values. Tests the getters and setters
     * of OclEmailDto.
     */
    @Test
    public final void errorDetail() {
        final BeanLikeTester blt = new BeanLikeTester( OclEmailDto.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, errorDetailsTestValues );
    }
}
