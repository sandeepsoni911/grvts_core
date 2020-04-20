package com.owners.gravitas.exception;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.enums.ErrorCode;

public class ResultNotFoundExceptionTest {

    @Test
    public void testConstructor() {
        ResultNotFoundException exp = new ResultNotFoundException( "test" );
        Assert.assertNotNull( exp );
        Assert.assertEquals( exp.getMessage(), "test" );
        Assert.assertEquals( exp.getErrorCode(), ErrorCode.RESULT_NOT_FOUND );
    }
}
