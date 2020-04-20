package com.owners.gravitas.dto;

import java.util.Arrays;
import java.util.List;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.enums.PhoneNumberType;

public class PhoneNumberTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues slackRequestTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * PhoneNumber.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createSlackRequestTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "type", null );
        defaultValues.put( "number", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createSlackRequestTestValues() {
        slackRequestTestValues = new PropertiesAndValues();
        slackRequestTestValues.put( "type", PhoneNumberType.PRIMARY );
        slackRequestTestValues.put( "number", "34534534" );
    }

    /**
     * Tests the {@link PhoneNumber} with default values. Tests the
     * getters
     * and setters of PhoneNumber.
     */
    @Test
    public final void testAgentDeviceRequest() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();

        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        List< Class< ? > > signature2 = Arrays.< Class< ? > > asList( PhoneNumberType.class, String.class );
        mapping.put( signature1, Arrays.asList() );
        mapping.put( signature2, Arrays.asList( "type", "number" ) );
        final BeanLikeTester blt = new BeanLikeTester( PhoneNumber.class, mapping );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, slackRequestTestValues );
    }
}
