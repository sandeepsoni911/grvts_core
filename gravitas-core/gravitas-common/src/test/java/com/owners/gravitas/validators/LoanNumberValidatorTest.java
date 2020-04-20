package com.owners.gravitas.validators;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * The Class LoanNumberValidatorTest.
 *
 * @author shivamm
 */
public class LoanNumberValidatorTest extends AbstractBaseMockitoTest {

    /** The constraint validator. */
    @InjectMocks
    private LoanNumberValidator loanNumberValidator;

    /**
     * Test is valid for true result.
     */
    @Test( priority = 1 )
    public void testIsValidForTrueResult() {
        boolean result = loanNumberValidator.isValid( "100000000", null );
        Assert.assertTrue( result );
    }

    /**
     * Test is valid for false result.
     */
    @Test( priority = 2 )
    public void testIsValidForFalseResult() {
        boolean result = loanNumberValidator.isValid( "test", null );
        Assert.assertFalse( result );
    }
}
