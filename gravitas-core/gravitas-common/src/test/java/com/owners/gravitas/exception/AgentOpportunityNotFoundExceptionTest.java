package com.owners.gravitas.exception;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.enums.ErrorCode;

public class AgentOpportunityNotFoundExceptionTest {

    @Test
    public void testConstructor() {
        AgentOpportunityNotFoundException exp = new AgentOpportunityNotFoundException( "test" );
        Assert.assertNotNull( exp );
        Assert.assertEquals( exp.getMessage(), "test" );
        Assert.assertEquals( exp.getErrorCode(), ErrorCode.AGENT_OPPORTUNITY_NOT_FOUND );
    }
}
