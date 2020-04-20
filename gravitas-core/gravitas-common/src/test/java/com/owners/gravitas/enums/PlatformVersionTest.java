package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test for PlatformVersion.
 *
 * @author pabhishek
 */
public class PlatformVersionTest {
	/**
     * Tests all the enums.
     */
	@Test
	public void test(){
		for(PlatformVersion platformVersion:PlatformVersion.values()){
			Assert.assertNotNull(platformVersion);
			Assert.assertNotNull(platformVersion.getVersion());
		}
		Assert.assertEquals(PlatformVersion.VERSION_1_0_3.getVersion(),"1.0.3","Object not matching");
	}
}
