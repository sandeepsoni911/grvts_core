package com.owners.gravitas.dto;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The Class BaseDTOTest.
 *
 * @author vishwanathm
 */
public class BaseDTOTest {

    /**
     * Test tostring.
     */
    @Test
    public void testTostring() {
        BaseDTO dto = new BaseDTO();
        Assert.assertNotNull( dto );
        Assert.assertNotNull( dto.toString() );
    }
}
