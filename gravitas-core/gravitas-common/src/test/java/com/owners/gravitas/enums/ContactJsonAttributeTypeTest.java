/**
 *
 */
package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The Class ContactJsonAttributeTypeTest.
 *
 * @author shivamm
 */
public class ContactJsonAttributeTypeTest {

    /**
     * Test.
     */
    @Test
    public final void test() {
        for ( ContactJsonAttributeType contactJsonAttributeType : ContactJsonAttributeType.values() ) {
            Assert.assertNotNull( contactJsonAttributeType );
            Assert.assertNotNull( contactJsonAttributeType.getKey() );
        }
        Assert.assertEquals( ContactJsonAttributeType.ORDER_ID.getKey(), "orderId" );
        Assert.assertEquals( ContactJsonAttributeType.ORDER_ID.getDataType(), "string" );
    }

}
