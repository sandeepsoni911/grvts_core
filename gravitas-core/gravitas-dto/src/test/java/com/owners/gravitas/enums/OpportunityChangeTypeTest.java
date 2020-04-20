package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The Class OpportunityChangeTypeTest.
 *
 * @author amits
 */
public class OpportunityChangeTypeTest {
    /**
     * Tests all the enums.
     */
    @Test
    public final void test() {
        for ( OpportunityChangeType opportunityEvent : OpportunityChangeType.values() ) {
            Assert.assertNotNull( opportunityEvent );
        }
    }
}
