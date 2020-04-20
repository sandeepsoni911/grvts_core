package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The Class OpportunityLabelTest.
 * 
 * @author pabhishek
 */
public class OpportunityLabelTest {

    /**
     * Test.
     */
    @Test
    public void test() {
        for ( CRMObjectLabel opportunityLabel : CRMObjectLabel.values() ) {
            Assert.assertNotNull( opportunityLabel );
        }
    }
}
