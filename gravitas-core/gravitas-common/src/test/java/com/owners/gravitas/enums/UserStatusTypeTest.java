package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The Class UserStatusTypeTest.
 * 
 * @author pabhishek
 */
public class UserStatusTypeTest {

    /**
     * Test.
     */
    @Test
    public void test() {
        for ( UserStatusType userStatusType : UserStatusType.values() ) {
            Assert.assertNotNull( userStatusType );
        }
    }
}
