/**
 *
 */
package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.enums.RecordType;

/**
 * @author harshads
 *
 */
public class RecordTypeTest {

    /**
     * Test.
     */
    @Test
    public final void test() {
        Assert.assertEquals( RecordType.BUYER.getType(), "Buyer", "CRM Record type type not matching" );
    }
    /**
     * Tests get record type test.
     */
    @Test
    public void testRecordTypeTest() {
        Assert.assertNotNull( RecordType.BUYER.getRecordType( RecordType.BUYER.getType() ) );
    }
}
