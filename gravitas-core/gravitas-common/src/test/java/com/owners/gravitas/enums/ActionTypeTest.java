package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test for ActionType.
 *
 * @author pabhishek
 */
public class ActionTypeTest {
	/**
     * Tests all the enums.
     */
	@Test
	public void test(){
		for(ActionType actionType:ActionType.values()){
			Assert.assertNotNull(actionType);
		}
	}
}
