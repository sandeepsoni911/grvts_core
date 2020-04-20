package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test for ActionEntity.
 *
 * @author pabhishek
 */
public class ActionEntityTest {
	
	/**
     * Tests all the enums.
     */
	@Test
	public void test(){
		for(ActionEntity actionEntity:ActionEntity.values()){
			Assert.assertNotNull(actionEntity);
		}
	}

}
