/**
 * 
 */
package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author harshads
 *
 */
public class LeadStatusTest {

    /**
     * Test.
     */
    @Test
    public final void test() {
    	for(LeadStatus leadStatus:LeadStatus.values()){
			Assert.assertNotNull(leadStatus);
			Assert.assertNotNull(leadStatus.getStatus());
		}
    	
        Assert.assertEquals( LeadStatus.LOST.getStatus(), "Lost", "Lead Status not matching" );
    }

}
