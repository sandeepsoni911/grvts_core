package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The Class RequestTypeTest.
 *
 * @author amits
 */
public class RequestTypeTest {
    /**
     * Tests all the enums.
     */
    @Test
    public final void test() {
        for ( RequestType requestType : RequestType.values() ) {
            Assert.assertNotNull( requestType.getType() );
        }
    }
}
