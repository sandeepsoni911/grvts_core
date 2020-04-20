package com.owners.gravitas.exception;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.enums.ErrorCode;

public class UserNotLoggedInExceptionTest {

    @Test
    public void testConstructor() {
        UserNotLoggedInException exp = new UserNotLoggedInException( "test" );
        Assert.assertNotNull( exp );
        Assert.assertEquals( exp.getMessage(), "test" );
        Assert.assertEquals( exp.getErrorCode(), ErrorCode.USER_LOGIN_ERROR );
    }
}
