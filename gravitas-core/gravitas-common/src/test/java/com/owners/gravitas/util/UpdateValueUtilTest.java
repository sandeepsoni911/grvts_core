package com.owners.gravitas.util;

import static com.owners.gravitas.util.UpdateValueUtil.updateField;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * The Class UpdateValueUtilTest.
 *
 * @author raviz
 */
public class UpdateValueUtilTest extends AbstractBaseMockitoTest {

    /**
     * Test update field string should return updated value when new value is
     * not blank and not same as old value.
     */
    @Test
    public void testUpdateFieldStringShouldReturnUpdatedValueWhenNewValueIsNotBlankAndNotSameAsOldValue() {
        final String newValue = "testNewValue";
        final String oldValue = "testOldValue";
        final String actualValue = updateField( oldValue, newValue );
        assertEquals( actualValue, newValue );
    }

    /**
     * Test update field string should return null when new value is empty.
     */
    @Test
    public void testUpdateFieldStringShouldReturnNullWhenNewValueIsEmpty() {
        final String newValue = "";
        final String oldValue = "testOldValue";
        final String actualValue = updateField( oldValue, newValue );
        assertNull( actualValue );
    }

    /**
     * Test update field string should return null when new value is null.
     */
    @Test
    public void testUpdateFieldStringShouldReturnNullWhenNewValueIsNull() {
        final String newValue = null;
        final String oldValue = "testOldValue";
        final String actualValue = updateField( oldValue, newValue );
        assertNull( actualValue );
    }

    /**
     * Test update field string should return null when new value is not blank
     * and same as old value.
     */
    @Test
    public void testUpdateFieldStringShouldReturnNullWhenNewValueIsNotBlankAndSameAsOldValue() {
        final String newValue = "testOldValue";
        final String oldValue = "testOldValue";
        final String actualValue = updateField( oldValue, newValue );
        assertNull( actualValue );
    }

    /**
     * Test update field object should return updated value when new value is
     * not blank and not same as old value.
     */
    @Test
    public void testUpdateFieldObjectShouldReturnUpdatedValueWhenNewValueIsNotBlankAndNotSameAsOldValue() {
        final Object newValue = "testNewValue";
        final Object oldValue = "testOldValue";
        final Object actualValue = updateField( oldValue, newValue );
        assertEquals( actualValue, newValue );
    }

    /**
     * Test update field object should return null when new value is null.
     */
    @Test
    public void testUpdateFieldObjectShouldReturnNullWhenNewValueIsNull() {
        final Object newValue = null;
        final Object oldValue = "testOldValue";
        final Object actualValue = updateField( oldValue, newValue );
        assertNull( actualValue );
    }

    /**
     * Test update field object should return null when new value is not blank
     * and same as old value.
     */
    @Test
    public void testUpdateFieldObjectShouldReturnNullWhenNewValueIsNotBlankAndSameAsOldValue() {
        final Object newValue = "testOldValue";
        final Object oldValue = "testOldValue";
        final Object actualValue = updateField( oldValue, newValue );
        assertNull( actualValue );
    }
}
