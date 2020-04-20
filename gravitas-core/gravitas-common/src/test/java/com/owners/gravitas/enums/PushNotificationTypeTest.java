package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test for PushNotificationType.
 *
 * @author pabhishek
 */
public class PushNotificationTypeTest {
	/**
     * Tests all the enums.
     */
	@Test
	public void test(){
		for(PushNotificationType pushNotificationType:PushNotificationType.values()){
			Assert.assertNotNull(pushNotificationType);
			
		}
	}
}
