package com.owners.gravitas.config;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AgentTopicJmxConfig;
import com.owners.gravitas.test.config.AbstractBaseMockitoTest;
import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class AgentTopicJmxConfigTest.
 *
 * @author raviz
 */
public class AgentTopicJmxConfigTest extends AbstractBaseMockitoTest {

    @InjectMocks
    private AgentTopicJmxConfig agentTopicJmxConfig;

    /** The property writer. */
    @Mock
    private PropertyWriter propertyWriter;

    /**
     * Test is enable agent db sync.
     */
    @Test
    public void testIsEnableAgentDbSync() {
        final boolean expected = true;
        ReflectionTestUtils.setField( agentTopicJmxConfig, "enableAgentDbSync", expected );
        final boolean actual = agentTopicJmxConfig.isEnableAgentDbSync();
        assertEquals( actual, expected );
    }

    /**
     * Test set enable agent db sync.
     */
    @Test
    public void testSetEnableAgentDbSync() {
        final boolean expected = true;
        doNothing().when( propertyWriter ).saveJmxProperty( "agent.topic.dbSync.enabled", expected );
        agentTopicJmxConfig.setEnableAgentDbSync( true );
        final boolean actual = ( boolean ) ReflectionTestUtils.getField( agentTopicJmxConfig, "enableAgentDbSync" );
        assertEquals( actual, expected );
        verify( propertyWriter ).saveJmxProperty( "agent.topic.dbSync.enabled", expected );
    }

}
