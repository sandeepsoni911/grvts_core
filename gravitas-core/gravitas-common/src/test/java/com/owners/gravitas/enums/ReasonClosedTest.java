package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The Class ReasonClosedTest.
 */
public class ReasonClosedTest {

    /**
     * Test.
     */
    @Test
    public void test() {
    	for(ReasonClosed reasonClosed:ReasonClosed.values()){
			Assert.assertNotNull(reasonClosed);
			Assert.assertNotNull(reasonClosed.getReason());
		}
    	
        Assert.assertEquals( ReasonClosed.OUT_OF_COVERAGE_AREA.getReason(), "Out Of Coverage Area",
                "Closed reason not matching" );
    }
}
