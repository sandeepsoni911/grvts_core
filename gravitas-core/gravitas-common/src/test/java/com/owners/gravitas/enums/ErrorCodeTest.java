package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.enums.ErrorCode;

/**
 * This is test class for {@link ErrorCode}.
 *
 * @author manishd
 */
public class ErrorCodeTest {

    /**
     * Tests all the enums.
     */
    @Test
    public final void test() {
        for ( ErrorCode errorCode : ErrorCode.values() ) {
            Assert.assertNotNull( errorCode.getCode() );
            Assert.assertNotNull( errorCode.getErrorDetail() );
        }
    }
}
