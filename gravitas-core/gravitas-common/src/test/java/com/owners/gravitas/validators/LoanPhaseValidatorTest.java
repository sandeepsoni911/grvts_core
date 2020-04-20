package com.owners.gravitas.validators;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.util.PropertiesUtil;

/**
 * The Class LoanPhaseValidatorTest.
 *
 * @author shivamm
 */
public class LoanPhaseValidatorTest extends AbstractBaseMockitoTest {

    /** The constraint validator. */
    @InjectMocks
    private LoanPhaseValidator loanPhaseValidator;

    /**
     * Before test
     *
     * @throws Exception
     */
    @BeforeTest
    public void beforeTest() throws Exception {
        final Map< String, String > props = new HashMap< String, String >();
        props.put( "ocl.loan.phase.prospect.stage", "New" );
        if (PropertiesUtil.getPropertiesMap() == null) {
            PropertiesUtil.setPropertiesMap( props );
        } else {
            PropertiesUtil.getPropertiesMap().putAll( props );
        }
    }


    /**
     * Test is valid for true result.
     */
    @Test( priority = 1 )
    public void testIsValidForTrueResult() {

        boolean result = loanPhaseValidator.isValid( "Prospect", null );
        Assert.assertTrue( result );
    }

    /**
     * Test is valid for false result.
     */
    @Test( priority = 2 )
    public void testIsValidForFalseResult() {
        boolean result = loanPhaseValidator.isValid( "test", null );
        Assert.assertFalse( result );
    }

}
