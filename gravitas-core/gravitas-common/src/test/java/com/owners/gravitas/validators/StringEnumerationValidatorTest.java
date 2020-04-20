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
import com.owners.gravitas.enums.RecordType;

/**
 * The Class StringEnumerationValidatorTest.
 *
 * @author vishwanathm
 */
public class StringEnumerationValidatorTest extends AbstractBaseMockitoTest {

    /** The constraint validator. */
    @InjectMocks
    private StringEnumerationValidator constraintValidator;

    /**
     * This method sets up mockMVC object use along with GlobalExceptionHandler
     * as ExceptionResolver.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public void setUpBase() throws Exception {
        Set< String > mySet = new HashSet();
        mySet.add( RecordType.BOTH.toString() );

        ReflectionTestUtils.setField( constraintValidator, "enums", mySet );
    }

    /**
     * Test is valid for true result.
     */
    @Test( priority = 1 )
    public void testIsValidForTrueResult() {
        boolean result = constraintValidator.isValid( RecordType.BOTH.toString(), null );
        Assert.assertTrue( result );

        result = constraintValidator.isValid( "", null );
        Assert.assertTrue( result );

    }

    /**
     * Test is valid for false result.
     */
    @Test( priority = 2 )
    public void testIsValidForFalseResult() {
        boolean result = constraintValidator.isValid( "test", null );
        Assert.assertFalse( result );
    }

    /**
     * Test initialize.
     */
    @Test( priority = 3 )
    public void testInitialize() {
        constraintValidator.initialize( new StringEnum() {

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
            public Class< ? extends Enum< ? > > enumClass() {
                // TODO Auto-generated method stub
                return RecordType.class;
            }
        } );
    }
}
