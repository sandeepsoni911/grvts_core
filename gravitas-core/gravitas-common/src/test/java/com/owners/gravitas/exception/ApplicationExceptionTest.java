package com.owners.gravitas.exception;

import static com.owners.gravitas.enums.ErrorCode.INTERNAL_SERVER_ERROR;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The test class for {@link ApplicationException}.
 *
 * @author manishd
 */
public class ApplicationExceptionTest {

    /** Test constructor 1. */
    @Test
    public void testConstuctor1() {
        try {
            throw new ApplicationException( "test exception message", INTERNAL_SERVER_ERROR );
        } catch ( ApplicationException ae ) {
            Assert.assertEquals( ae.getErrorCode(), INTERNAL_SERVER_ERROR );
            Assert.assertEquals( "test exception message", ae.getMessage() );
            Assert.assertNull( ae.getCause() );
        }
    }

    /** Test constructor 2. */
    @Test
    public void testConstuctor2() {
        Exception ex = new Exception( "test" );
        try {
            throw new ApplicationException( "test exception message", ex );
        } catch ( ApplicationException ae ) {
            Assert.assertEquals( "test exception message", ae.getMessage() );
            Assert.assertEquals( ae.getCause(), ex );
        }
    }

    /** Test constructor 3. */
    @Test
    public void testConstuctor3() {
        Exception ex = new Exception( "test" );
        try {
            throw new ApplicationException( "test exception message", ex, INTERNAL_SERVER_ERROR );
        } catch ( ApplicationException ae ) {
            Assert.assertEquals( "test exception message", ae.getMessage() );
            Assert.assertEquals( ae.getErrorCode(), INTERNAL_SERVER_ERROR );
            Assert.assertEquals( ae.getCause(), ex );
        }
    }
}
