package com.owners.gravitas.validators;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * The Class AgentLeadTypeValidatorTest.
 *
 * @author shivamm
 */
public class AgentLeadTypeValidatorTest extends AbstractBaseMockitoTest {

    /** The constraint validator. */
    @InjectMocks
    private AgentLeadTypeValidator agentLeadTypeValidator;

    /**
     * Test is valid for true result.
     */
    @Test
    public void testIsValid() {
        boolean result = agentLeadTypeValidator.isValid( "BUYER", null );
        Assert.assertTrue( result );
    }

}
