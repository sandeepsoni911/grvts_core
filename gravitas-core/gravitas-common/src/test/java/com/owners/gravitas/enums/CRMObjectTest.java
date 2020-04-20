/**
 * 
 */
package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.enums.CRMObject;

/**
 * The Class CRMObjectTest.
 *
 * @author harshads
 */
public class CRMObjectTest {

    /**
     * Test.
     */
    @Test
    public final void test() {
    	for(CRMObject crmObject:CRMObject.values()){
			Assert.assertNotNull(crmObject);
			Assert.assertNotNull(crmObject.getName());
		}
        Assert.assertEquals( CRMObject.ACCOUNT.getName(), "Account", "CRM Object name not matching" );
    }

}
