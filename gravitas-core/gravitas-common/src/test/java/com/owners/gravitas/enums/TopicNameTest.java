package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test for TopicNameTest.
 *
 * @author shivamm
 */
public class TopicNameTest {

    /**
     * Tests all the enums.
     */
    @Test
    public void test() {
        for ( TopicName topicName : TopicName.values() ) {
            Assert.assertNotNull( topicName );
        }
    }
    /**
     * Tests get name.
     */
    @Test
    public void testGetName() {
        Assert.assertNotNull( TopicName.CONTACT.getName() );
    }
}
