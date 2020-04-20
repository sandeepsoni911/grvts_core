package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.enums.OpportunityStage;

/**
 * This is test class for {@link OpportunityStage}.
 *
 * @author vishwanathm
 */
public class OpportunityStageTest {

    /**
     * Tests all the enums.
     */
    @Test
    public final void test() {
        for ( OpportunityStage crmStages : OpportunityStage.values() ) {
            Assert.assertNotNull( crmStages.getStage() );
        }
    }
}
