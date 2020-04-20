package com.owners.gravitas.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;

import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.business.builder.EscrowEmailNotificationBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class EscrowEmailNotificationBuilderTest.
 *
 * @author ankusht
 */
public class EscrowEmailNotificationBuilderTest extends AbstractBaseMockitoTest {

    /** The escrow email notification builder. */
    @InjectMocks
    private EscrowEmailNotificationBuilder escrowEmailNotificationBuilder;

    /** The property writer. */
    @Mock
    private PropertyWriter propertyWriter;

    /** The Constant minutes. */
    private static final int minutes = 4320;

    /**
     * Sets the up.
     */
    @BeforeMethod
    public void setUp() {
        ReflectionTestUtils.setField( escrowEmailNotificationBuilder, "ptsFromEmail", "pts@owners.com" );
        ReflectionTestUtils.setField( escrowEmailNotificationBuilder, "escrowMailDelayMinutes", minutes );
    }

    /**
     * Test convert to for FL state.
     */
    @Test
    public void testConvertToForFLState() {
        EmailNotification destination = new EmailNotification();
        AgentSource source = new AgentSource();
        source.setState( "FL" );
        source.setName( "test" );
        source.setEmail( "test@test.com" );
        EmailNotification emailNotification = escrowEmailNotificationBuilder.convertTo( source, destination );
        assertEquals( ReflectionTestUtils.getField( EscrowEmailNotificationBuilder.class, "ESCROW_EMAIL_FL_TX_OH_PA" ),
                emailNotification.getMessageTypeName() );
    }

    /**
     * Test convert to for FL state.
     */
    @Test
    public void testConvertToForNullDestination() {
        EmailNotification destination = null;
        AgentSource source = new AgentSource();
        source.setState( "FL" );
        source.setName( "test" );
        source.setEmail( "test@test.com" );
        EmailNotification emailNotification = escrowEmailNotificationBuilder.convertTo( source, destination );
        assertEquals( ReflectionTestUtils.getField( EscrowEmailNotificationBuilder.class, "ESCROW_EMAIL_FL_TX_OH_PA" ),
                emailNotification.getMessageTypeName() );
    }

    /**
     * Test convert to for TX state.
     */
    @Test
    public void testConvertToForTXState() {
        EmailNotification destination = new EmailNotification();
        AgentSource source = new AgentSource();
        source.setState( "TX" );
        source.setName( "test" );
        source.setEmail( "test@test.com" );
        EmailNotification emailNotification = escrowEmailNotificationBuilder.convertTo( source, destination );
        assertEquals( ReflectionTestUtils.getField( EscrowEmailNotificationBuilder.class, "ESCROW_EMAIL_FL_TX_OH_PA" ),
                emailNotification.getMessageTypeName() );
    }

    /**
     * Test convert to for OH state.
     */
    @Test
    public void testConvertToForOHState() {
        EmailNotification destination = new EmailNotification();
        AgentSource source = new AgentSource();
        source.setState( "OH" );
        source.setName( "test" );
        source.setEmail( "test@test.com" );
        EmailNotification emailNotification = escrowEmailNotificationBuilder.convertTo( source, destination );
        assertEquals( ReflectionTestUtils.getField( EscrowEmailNotificationBuilder.class, "ESCROW_EMAIL_FL_TX_OH_PA" ),
                emailNotification.getMessageTypeName() );
    }

    /**
     * Test convert to for PA state.
     */
    @Test
    public void testConvertToForPAState() {
        EmailNotification destination = new EmailNotification();
        AgentSource source = new AgentSource();
        source.setState( "PA" );
        source.setName( "test" );
        source.setEmail( "test@test.com" );
        EmailNotification emailNotification = escrowEmailNotificationBuilder.convertTo( source, destination );
        assertEquals( ReflectionTestUtils.getField( EscrowEmailNotificationBuilder.class, "ESCROW_EMAIL_FL_TX_OH_PA" ),
                emailNotification.getMessageTypeName() );
    }

    /**
     * Test convert to for CA state.
     */
    @Test
    public void testConvertToForCAState() {
        EmailNotification destination = new EmailNotification();
        AgentSource source = new AgentSource();
        source.setState( "CA" );
        source.setName( "test" );
        source.setEmail( "test@test.com" );
        EmailNotification emailNotification = escrowEmailNotificationBuilder.convertTo( source, destination );
        assertEquals( ReflectionTestUtils.getField( EscrowEmailNotificationBuilder.class, "ESCROW_EMAIL_CA" ),
                emailNotification.getMessageTypeName() );
    }

    /**
     * Test convert to should return null.
     */
    @Test
    public void testConvertToShouldReturnNull() {
        EmailNotification destination = new EmailNotification();
        AgentSource source = new AgentSource();
        source.setState( "CA1" );
        source.setName( "test" );
        source.setEmail( "test@test.com" );
        EmailNotification emailNotification = escrowEmailNotificationBuilder.convertTo( source, destination );
        assertNull( emailNotification );
    }

    /**
     * Test convert from should throw exception.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFromShouldThrowException() {
        EmailNotification source = new EmailNotification();
        Map< String, Object > destination = null;
        escrowEmailNotificationBuilder.convertFrom( source, destination );
    }

    /**
     * Test convert to for null source.
     */
    @Test
    public void testConvertToForNullSource() {
        EmailNotification destination = new EmailNotification();
        AgentSource source = null;
        EmailNotification emailNotification = escrowEmailNotificationBuilder.convertTo( source, destination );
        assertEquals( destination, emailNotification );
    }

    /**
     * Test convert to for null state.
     */
    @Test
    public void testConvertToForNullState() {
        EmailNotification destination = new EmailNotification();
        AgentSource source = new AgentSource();
        EmailNotification emailNotification = escrowEmailNotificationBuilder.convertTo( source, destination );
        assertEquals( destination, emailNotification );
    }

    /**
     * Test get escrow mail delay minutes.
     */
    @Test
    public void testGetEscrowMailDelayMinutes() {
        int escrowMailDelayMinutes = escrowEmailNotificationBuilder.getEscrowMailDelayMinutes();
        assertEquals( minutes, escrowMailDelayMinutes );
    }

    /**
     * Test set escrow mail delay minutes.
     */
    @Test
    public void testSetEscrowMailDelayMinutes() {
        int val = 1;
        escrowEmailNotificationBuilder.setEscrowMailDelayMinutes( val );
        assertEquals( val, ReflectionTestUtils.getField( escrowEmailNotificationBuilder, "escrowMailDelayMinutes" ) );
        verify( propertyWriter ).saveJmxProperty( Mockito.anyString(), Mockito.anyInt() );
    }
}
