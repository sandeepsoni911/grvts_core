package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The Class OpportunityEventTest.
 *
 * @author amits
 */
public class OpportunityEventTest {
    /**
     * Tests all the enums.
     */
    @Test
    public final void test() {
        for ( EventType opportunityEvent : EventType.values() ) {
            Assert.assertNotNull( opportunityEvent );
        }
    }
}
