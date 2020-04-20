package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test for JobTypeTest.
 *
 * @author shivamm
 */
public class JobTypeTest {

    /**
     * Tests all the enums.
     */
    @Test
    public void test() {
        for ( JobType jobType : JobType.values() ) {
            Assert.assertNotNull( jobType );
        }
    }

}
