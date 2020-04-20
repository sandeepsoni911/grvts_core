/**
 * 
 */
package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.enums.LeadRequestType;

/**
 * The Class LeadRequestTypeTest.
 *
 * @author harshads
 */
public class LeadRequestTypeTest {

    /**
     * Test.
     */
    @Test
    public final void test() {
        for ( LeadRequestType leadRequestType : LeadRequestType.values() ) {
            Assert.assertNotNull( leadRequestType );
            Assert.assertNotNull( leadRequestType.getType() );
        }
        Assert.assertEquals( LeadRequestType.OTHER.getType(), "Other", "Lead request type not matching" );
    }

    /**
     * Test get request type.
     */
    @Test
    public final void testGetRequestType() {
        for ( LeadRequestType leadRequestType : LeadRequestType.values() ) {
            Assert.assertNotNull( LeadRequestType.getRequestType( leadRequestType.getType() ) );
        }
    }

}
