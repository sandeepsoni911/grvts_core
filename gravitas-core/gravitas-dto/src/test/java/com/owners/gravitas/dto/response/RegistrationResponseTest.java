package com.owners.gravitas.dto.response;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class RegistrationResponseTest.
 *
 * @author vishwanathm
 */
public class RegistrationResponseTest {
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
     * ActionLogResponseTest.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createRegistrationResponseTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "result", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createRegistrationResponseTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "result", new RegisteredUserResponse() );
    }

    /**
     * Tests the {@link RegistrationResponse} with default values. Tests the
     * getters
     * and setters of RegistrationResponse.
     */
    @Test
    public final void testAgentResponse() {

        final BeanLikeTester blt = new BeanLikeTester( RegistrationResponse.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
