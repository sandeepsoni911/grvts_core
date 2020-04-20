package com.owners.gravitas.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.business.builder.AgentSourceResponseBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * The Class AgentSourceResponseBuilderTest.
 *
 * @author madhav
 */
public class AgentSourceResponseBuilderTest extends AbstractBaseMockitoTest {

    /** The agent source response builder. */
    @InjectMocks
    private AgentSourceResponseBuilder agentSourceResponseBuilder;

    /**
     * Test convert to destination not null.
     */
    @Test
    public void testConvertToDestinationNotNull() {
        Map< String, Object > crmAgent = new HashMap< String, Object >();
        crmAgent.put( "Id", "test" );
        crmAgent.put( "Name", "test" );
        crmAgent.put( "Email__c", "test" );
        crmAgent.put( "Address1__c", "test" );
        crmAgent.put( "Address2__c", "test" );
        crmAgent.put( "City__c", "test" );
        crmAgent.put( "State__c", "test" );
        crmAgent.put( "Zip_Code__c", "test" );
        crmAgent.put( "License_Number__c", "test" );
        crmAgent.put( "Active__c", true );
        crmAgent.put( "Agent_Mobile_Carrier__c", "test" );
        crmAgent.put( "Field_Agent__c", true );
        crmAgent.put( "Status__c", "test" );
        crmAgent.put( "Phone__c", "test" );
        crmAgent.put( "Agent_App_Starting_Date__c", "2017-08-15" );

        AgentSource agentSource = agentSourceResponseBuilder.convertTo( crmAgent, new AgentSource() );

        assertNotNull( agentSource );
        assertEquals( agentSource.getId(), crmAgent.get( "Id" ) );
        assertEquals( agentSource.getName(), crmAgent.get( "Name" ) );
        assertEquals( agentSource.getEmail(), crmAgent.get( "Email__c" ) );

    }

    /**
     * Test convert to destination null.
     */
    @Test
    public void testConvertToDestinationNull() {
        Map< String, Object > crmAgent = new HashMap< String, Object >();
        crmAgent.put( "Id", "test" );
        crmAgent.put( "Name", "test" );
        crmAgent.put( "Email__c", "test" );
        crmAgent.put( "Address1__c", "test" );
        crmAgent.put( "Address2__c", "test" );
        crmAgent.put( "City__c", "test" );
        crmAgent.put( "State__c", "test" );
        crmAgent.put( "Zip_Code__c", "test" );
        crmAgent.put( "License_Number__c", "test" );
        crmAgent.put( "Active__c", true );
        crmAgent.put( "Agent_Mobile_Carrier__c", "test" );
        crmAgent.put( "Field_Agent__c", true );
        crmAgent.put( "Status__c", "test" );
        crmAgent.put( "Phone__c", "test" );
        crmAgent.put( "Agent_App_Starting_Date__c", "2017-08-15" );

        AgentSource agentSource = agentSourceResponseBuilder.convertTo( crmAgent, null );

        assertNotNull( agentSource );
        assertEquals( agentSource.getId(), crmAgent.get( "Id" ) );
        assertEquals( agentSource.getName(), crmAgent.get( "Name" ) );
        assertEquals( agentSource.getEmail(), crmAgent.get( "Email__c" ) );

    }

    /**
     * Test convert from expect exception.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFromExpectException() {
        agentSourceResponseBuilder.convertFrom( new AgentSource() );
    }
}
