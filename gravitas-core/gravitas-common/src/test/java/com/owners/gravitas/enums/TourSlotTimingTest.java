package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test for TourSlotTiming.
 *
 * @author pabhishek
 */
public class TourSlotTimingTest {
	/**
     * Tests all the enums.
     */
	@Test
	public void test(){
		for(TourSlotTiming tourSlotTiming:TourSlotTiming.values()){
			Assert.assertNotNull(tourSlotTiming);
		}
	}
}
