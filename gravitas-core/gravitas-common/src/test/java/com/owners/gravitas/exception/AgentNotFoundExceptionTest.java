package com.owners.gravitas.exception;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.enums.ErrorCode;

public class AgentNotFoundExceptionTest {

    @Test
    public void testConstructor() {
        AgentNotFoundException exp = new AgentNotFoundException( "test" );
        Assert.assertNotNull( exp );
        Assert.assertEquals( exp.getMessage(), "test" );
        Assert.assertEquals( exp.getErrorCode(), ErrorCode.AGENT_NOT_FOUND );
    }
}
