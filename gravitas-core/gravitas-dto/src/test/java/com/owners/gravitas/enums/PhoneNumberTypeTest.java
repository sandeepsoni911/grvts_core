package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PhoneNumberTypeTest {
    /**
     * Tests all the enums.
     */
    @Test
    public final void test() {
        for ( PhoneNumberType phoneNumberTypes : PhoneNumberType.values() ) {
            Assert.assertNotNull( phoneNumberTypes );
        }
    }
}
