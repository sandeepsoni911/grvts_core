package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test for Platform.
 *
 * @author pabhishek
 */
public class PlatformTest {
	
	/**
     * Tests all the enums.
     */
	@Test
	public void test(){
		for(Platform platform:Platform.values()){
			Assert.assertNotNull(platform);
		}
	}
}
