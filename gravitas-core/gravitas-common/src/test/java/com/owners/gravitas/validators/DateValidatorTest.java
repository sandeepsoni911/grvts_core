package com.owners.gravitas.validators;

import java.lang.annotation.Annotation;

import javax.validation.Payload;

import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.util.DateUtil;

/**
 * The Class DateValidatorTest.
 *
 * @author vishwanathm
 */
public class DateValidatorTest extends AbstractBaseMockitoTest {

    /** The constraint validator. */
    @InjectMocks
    private DateValidator constraintValidator;

    /**
     * This method sets up mockMVC object use along with GlobalExceptionHandler
     * as ExceptionResolver.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public void setUpBase() throws Exception {
        ReflectionTestUtils.setField( constraintValidator, "format", DateUtil.DEFAULT_CRM_DATE_PATTERN );
    }

    /**
     * Test is valid for true result.
     */
    @Test( priority = 1 )
    public void testIsValidForTrueResult() {
        boolean result = constraintValidator.isValid( "2016-06-24", null );
        Assert.assertTrue( result );

        result = constraintValidator.isValid( null, null );
        Assert.assertTrue( result );
    }

    /**
     * Test is valid for false result.
     */
    @Test( priority = 2 )
    public void testIsValidForFalseResult() {
        boolean result = constraintValidator.isValid( "test", null );
        Assert.assertFalse( result );

        result = constraintValidator.isValid( "2222-06-24", null );
        Assert.assertFalse( result );
    }

    /**
     * Test initialize.
     */
    @Test( priority = 3 )
    public void testInitialize() {
        constraintValidator.initialize( new Date() {

            @Override
            public Class< ? extends Annotation > annotationType() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Class< ? extends Payload >[] payload() {
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
            public String format() {
                // TODO Auto-generated method stub
                return DateUtil.DEFAULT_CRM_DATE_PATTERN;
            }
        } );
    }

    /**
     * Test exception.
     */
    @Test( priority = 4 )
    public void testException() {
        ReflectionTestUtils.setField( constraintValidator, "format", null );
        boolean result = constraintValidator.isValid( "test", null );
        Assert.assertFalse( result );
    }
}
