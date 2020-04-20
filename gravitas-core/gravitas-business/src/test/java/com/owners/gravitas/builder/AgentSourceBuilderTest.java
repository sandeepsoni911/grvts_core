package com.owners.gravitas.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.business.builder.AgentSourceBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.crm.response.CRMAgentResponse;

/**
 * The Class AgentSourceBuilderTest.
 *
 * @author shivamm
 */
public class AgentSourceBuilderTest extends AbstractBaseMockitoTest {

    /** The agent source builder. */
    @InjectMocks
    private AgentSourceBuilder agentSourceBuilder;

    /**
     * Test convert to with source not null with agent source not null.
     */
    @Test
    public void testConvertToWithSourceNotNull() {
        /*Map< String, Object > source = new HashMap<>();
        source.put( "Id", "test" );
        source.put( "Name", "test" );
        source.put( "Email__c", "test" );
        source.put( "State__c", "test" );
        source.put( "Status__c", "test" );*/
        CRMAgentResponse source = new CRMAgentResponse();
        AgentSource log = agentSourceBuilder.convertTo( source );
        Assert.assertNotNull( log );
        //Assert.assertEquals( source.get( "Id" ), log.getId() );
    }

    /**
     * Test convert to with source not null with agent source not null.
     */
    @Test
    public void testConvertToWithSourceNull() {
        AgentSource agentSrc = new AgentSource();
        AgentSource agentSource = agentSourceBuilder.convertTo( null, agentSrc );
        assertEquals( agentSource, agentSrc );
    }

    /**
     * Test convert to with source not null destination not null.
     */
    @Test
    public void testConvertToWithSourceNotNullDestinationNotNull() {
        /*Map< String, Object > source = new HashMap<>();
        source.put( "Id", "test" );*/
        CRMAgentResponse source = new CRMAgentResponse();
        AgentSource log = agentSourceBuilder.convertTo( source, new AgentSource() );
        Assert.assertNotNull( log );
    }

    /**
     * Test convert from
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertToSourceAsNull() {
       // Map< String, Object > source = agentSourceBuilder.convertFrom( new AgentSource() );
        CRMAgentResponse source = agentSourceBuilder.convertFrom( new AgentSource() );
        Assert.assertNotNull( source );
    }
}
