package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test for NotificationType.
 *
 * @author pabhishek
 */
public class NotificationTypeTest {
	/**
     * Tests all the enums.
     */
	@Test
	public void test(){
		for(NotificationType notificationType:NotificationType.values()){
			Assert.assertNotNull(notificationType);
		}
	}
}
