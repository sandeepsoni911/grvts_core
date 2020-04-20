package com.owners.gravitas.exception;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.enums.ErrorCode;

public class AgentInvalidExceptionTest {

    @Test
    public void testConstructor() {
        AgentInvalidException exp = new AgentInvalidException( "test" );
        Assert.assertNotNull( exp );
        Assert.assertEquals( exp.getMessage(), "test" );
        Assert.assertEquals( exp.getErrorCode(), ErrorCode.AGENT_NOT_VALID );
    }
}
