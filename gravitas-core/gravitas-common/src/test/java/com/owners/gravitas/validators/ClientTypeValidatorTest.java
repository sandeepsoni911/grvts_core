package com.owners.gravitas.validators;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Payload;

import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.enums.GravitasClientType;

/**
 * The Class ClientTypeValidatorTest.
 *
 * @author nishak
 */
public class ClientTypeValidatorTest extends AbstractBaseMockitoTest {

    /** The constraint validator. */
    @InjectMocks
    private ClientTypeValidator clientTypeValidator;

    /** The enum values. */
    private Set< String > enumValues;

    /**
     * Sets the up base.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public void setUpBase() throws Exception {
        enumValues = new HashSet< String >();
        enumValues.add( GravitasClientType.AgentAppAndroid.getValue() );
        enumValues.add( GravitasClientType.AgentAppIOS.getValue() );
        ReflectionTestUtils.setField( clientTypeValidator, "enumValues", enumValues );
    }

    /**
     * Test is valid for true result.
     */
    @Test( priority = 1 )
    public void testIsValidForTrueResult() {
        boolean result = clientTypeValidator.isValid( "AgentApp-IOS", null );
        Assert.assertTrue( result );
    }

    /**
     * Test exception.
     */
    @Test( priority = 2 )
    public void testIsValidForFailure() {
        ReflectionTestUtils.setField( clientTypeValidator, "enumValues", enumValues );
        boolean result = clientTypeValidator.isValid( "test", null );
        Assert.assertFalse( result );

        result = clientTypeValidator.isValid( "", null );
        Assert.assertFalse( result );
    }

    /**
     * Test initialize.
     */
    @Test( priority = 3 )
    public void testInitialize() {
        clientTypeValidator.initialize( new ClientType() {

            @Override
            public Class< ? extends Annotation > annotationType() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String message() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Class< ? >[] groups() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Class< ? extends Payload >[] payload() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Class< ? extends Enum< ? > > enumClass() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public boolean ignoreCase() {
                // TODO Auto-generated method stub
                return false;
            }

        } );
    }
}
