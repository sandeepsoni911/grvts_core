package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test for TaskType.
 *
 * @author pabhishek
 */
public class TaskTypeTest {
	/**
     * Tests all the enums.
     */
	@Test
	public void test(){
		for(TaskType taskType:TaskType.values()){
			Assert.assertNotNull(taskType);
			Assert.assertNotNull(taskType.getType());
		}
	}
}
