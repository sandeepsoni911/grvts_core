package com.owners.gravitas.exception;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.enums.ErrorCode;

public class InvalidUserIdExceptionTest {

    @Test
    public void testConstructor() {
        InvalidUserIdException exp = new InvalidUserIdException( "test" );
        Assert.assertNotNull( exp );
        Assert.assertEquals( exp.getMessage(), "test" );
        Assert.assertEquals( exp.getErrorCode(), ErrorCode.INVALID_USER_ID_PARAMETER );
    }
}
