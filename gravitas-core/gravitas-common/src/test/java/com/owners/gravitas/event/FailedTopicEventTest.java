package com.owners.gravitas.event;

import static com.owners.gravitas.enums.TopicName.LEAD;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * The Class FailedTopicEventTest.
 *
 * @author raviz
 */
public class FailedTopicEventTest extends AbstractBaseMockitoTest {

    /**
     * Test get failed topic name.
     */
    @Test
    public void testGetFailedTopicName() {
        final FailedTopicEvent failedTopicEvent = new FailedTopicEvent( new Object(), LEAD );
        assertEquals( failedTopicEvent.getFailedTopicName(), LEAD );
    }
}
