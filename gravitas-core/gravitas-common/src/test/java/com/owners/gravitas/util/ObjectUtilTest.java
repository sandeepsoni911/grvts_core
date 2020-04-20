package com.owners.gravitas.util;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.exception.ApplicationException;

/**
 * The Class ObjectUtilTest.
 *
 * @author shivamm
 */
public class ObjectUtilTest {

    /**
     * Test is null with value not null.
     */
    @Test
    public void testIsNullWithValueNotNull() {
        String value = "test";
        assertEquals( ObjectUtil.isNull( value ), value );
    }

    /**
     * Test is null with value null.
     */
    @Test
    public void testIsNullWithValueNull() {
        String value = "";
        assertEquals( ObjectUtil.isNull( null), value );
    }
    /**
     * Test is null with value null.
     */
    @Test
    public void testIsNullWithValueNullString() {
        ObjectUtil util = new ObjectUtil();
        String value = "null";
        assertEquals( ObjectUtil.isNull(value ), "" );
    }

}
