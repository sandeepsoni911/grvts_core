package com.owners.gravitas.exception;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The Class InvalidArgumentExceptionTest.
 *
 * @author amits
 */
public class InvalidArgumentExceptionTest {

	/**
	 * Test constructor.
	 */
	@Test
	public void testConstructor() {
		InvalidArgumentException exp = new InvalidArgumentException("test", new ArrayList<>());
		Assert.assertNotNull(exp);
		Assert.assertEquals(exp.getMessage(), "test:");
	}

	/**
	 * Test setters.
	 */
	@Test
	public void testSetters() {
		InvalidArgumentException exp = new InvalidArgumentException("test", new ArrayList<>());
		List<String> errors = new ArrayList<>();
		exp.setErrors(errors);
		Assert.assertNotNull(exp);
		Assert.assertEquals(exp.getMessage(), "test:");
		Assert.assertEquals(exp.getErrors(), errors);
	}
}
