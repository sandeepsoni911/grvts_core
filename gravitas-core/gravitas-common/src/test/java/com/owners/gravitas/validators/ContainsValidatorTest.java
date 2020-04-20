package com.owners.gravitas.validators;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * The Class ContainsValidatorTest.
 *
 * @author pabhishek
 */
public class ContainsValidatorTest extends AbstractBaseMockitoTest {

	/** The constraint validator. */
    @InjectMocks
    private ContainsValidator constraintValidator;
    
    

    /**
     * This method sets up mockMVC object use along with GlobalExceptionHandler
     * as ExceptionResolver.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public void setUpBase() throws Exception {
    	List<String> loadProperties=new ArrayList<String>();
    	loadProperties.add("PROPERTY1");
    	loadProperties.add("PROPERTY2");
    	ReflectionTestUtils.setField( constraintValidator, "properties",loadProperties);

    }
    
    /**
     * Test is valid for true result.
     */
    @Test
    public void testIsValidForTrueResult() {
        boolean result = constraintValidator.isValid( "PROPERTY1", null );
        Assert.assertTrue( result );

        result = constraintValidator.isValid("", null );
        Assert.assertTrue( result );
    }
    

}
