package com.owners.gravitas.builder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.business.builder.AgentOnboardEmailNotificationBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class AgentOnboardEmailNotificationBuilderTest.
 *
 * @author amits
 */
public class AgentOnboardEmailNotificationBuilderTest extends AbstractBaseMockitoTest {

    /** The email notification builder. */
    @InjectMocks
    private AgentOnboardEmailNotificationBuilder agentOnboardEmailNotificationBuilder;

    /** The property writer. */
    @Mock
    private PropertyWriter propertyWriter;

    /** The Constant minutes. */
    private static final int agentOnboardMailDelayMinutes = 4320;

    /**
     * Sets the up.
     */
    @BeforeMethod
    public void setUp() {
        ReflectionTestUtils.setField( agentOnboardEmailNotificationBuilder, "ptsFromEmail", "pts@owners.com" );
        ReflectionTestUtils.setField( agentOnboardEmailNotificationBuilder, "agentOnboardMailDelayMinutes",
                agentOnboardMailDelayMinutes );
    }

    /**
     * Test get mail delay minutes.
     */
    @Test
    public void testGetEscrowMailDelayMinutes() {
        int agentOnboardMailDelayMins = agentOnboardEmailNotificationBuilder.getAgentOnboardMailDelayMinutes();
        assertEquals( agentOnboardMailDelayMins, agentOnboardMailDelayMinutes );
    }

    /**
     * Test set mail delay minutes.
     */
    @Test
    public void testSetEscrowMailDelayMinutes() {
        int val = 1;
        agentOnboardEmailNotificationBuilder.setAgentOnboardMailDelayMinutes( val );
        assertEquals( val,
                ReflectionTestUtils.getField( agentOnboardEmailNotificationBuilder, "agentOnboardMailDelayMinutes" ) );
        verify( propertyWriter ).saveJmxProperty( Mockito.anyString(), Mockito.anyInt() );
    }

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        final AgentSource agentSource = new AgentSource();
        agentSource.setName( "test" );
        agentSource.setEmail( "test@test.com" );
        agentSource.setState( "FL" );
        EmailNotification request = agentOnboardEmailNotificationBuilder.convertTo( agentSource, null );
        Assert.assertNotNull( request );

        agentSource.setState( "GA" );
        request = agentOnboardEmailNotificationBuilder.convertTo( agentSource, null );
        Assert.assertNotNull( request );

        agentSource.setState( "MA" );
        request = agentOnboardEmailNotificationBuilder.convertTo( agentSource, null );
        Assert.assertNotNull( request );

        agentSource.setState( "FL" );
        request = agentOnboardEmailNotificationBuilder.convertTo( agentSource, null );
        Assert.assertNotNull( request );

        agentSource.setState( "IL" );
        request = agentOnboardEmailNotificationBuilder.convertTo( agentSource, null );
        Assert.assertNotNull( request );

        agentSource.setState( "PQ" );
        request = agentOnboardEmailNotificationBuilder.convertTo( agentSource, null );
        Assert.assertNull( request );

        agentSource.setState( null );
        request = agentOnboardEmailNotificationBuilder.convertTo( agentSource, null );
        Assert.assertNull( request );
    }

    /**
     * Test convert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        agentOnboardEmailNotificationBuilder.convertFrom( new EmailNotification() );
    }
}
