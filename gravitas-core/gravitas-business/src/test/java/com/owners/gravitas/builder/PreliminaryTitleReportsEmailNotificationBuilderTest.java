package com.owners.gravitas.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.business.builder.PreliminaryTitleReportsEmailNotificationBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class PreliminaryTitleReportsEmailNotificationBuilderTest.
 *
 * @author ankusht
 */
public class PreliminaryTitleReportsEmailNotificationBuilderTest extends AbstractBaseMockitoTest {

    /** The preliminary title reports email notification builder. */
    @InjectMocks
    private PreliminaryTitleReportsEmailNotificationBuilder preliminaryTitleReportsEmailNotificationBuilder;

    /** The property writer. */
    @Mock
    private PropertyWriter propertyWriter;

    /** The Constant minutes. */
    private static final int minutes = 5760;

    /**
     * Sets the up.
     */
    @BeforeMethod
    public void setUp() {
        ReflectionTestUtils.setField( preliminaryTitleReportsEmailNotificationBuilder,
                "preliminaryTitleReportsMailDelayMinutes", minutes );
        ReflectionTestUtils.setField( preliminaryTitleReportsEmailNotificationBuilder, "ptsFromEmail",
                "pts@owners.com" );
    }

    /**
     * Test convert to for CA state.
     */
    @Test
    public void testConvertToForCAState() {
        EmailNotification destination = new EmailNotification();
        AgentSource source = new AgentSource();
        source.setState( "CA" );
        source.setEmail( "test@email.com" );
        source.setName( "Test Name" );
        EmailNotification emailNotification = preliminaryTitleReportsEmailNotificationBuilder.convertTo( source,
                destination );
        assertEquals( ReflectionTestUtils.getField( PreliminaryTitleReportsEmailNotificationBuilder.class,
                "PRELIMINARY_TITLE_REPORTS_EMAIL_CA" ), emailNotification.getMessageTypeName() );
    }

    /**
     * Test convert to for null source.
     */
    @Test
    public void testConvertToForNullSource() {
        EmailNotification destination = new EmailNotification();
        AgentSource source = null;
        EmailNotification emailNotification = preliminaryTitleReportsEmailNotificationBuilder.convertTo( source,
                destination );
        assertEquals( destination, emailNotification );
    }

    /**
     * Test convert to for null state.
     */
    @Test
    public void testConvertToForNullState() {
        EmailNotification destination = new EmailNotification();
        AgentSource source = new AgentSource();
        EmailNotification emailNotification = preliminaryTitleReportsEmailNotificationBuilder.convertTo( source,
                destination );
        assertEquals( destination, emailNotification );
    }

    /**
     * Test convert to for CA state and null destination.
     */
    @Test
    public void testConvertToForCAStateAndNullDestination() {
        EmailNotification destination = null;
        AgentSource source = new AgentSource();
        source.setState( "CA" );
        source.setEmail( "test@email.com" );
        source.setName( "Test Name" );
        EmailNotification emailNotification = preliminaryTitleReportsEmailNotificationBuilder.convertTo( source,
                destination );
        assertEquals( ReflectionTestUtils.getField( PreliminaryTitleReportsEmailNotificationBuilder.class,
                "PRELIMINARY_TITLE_REPORTS_EMAIL_CA" ), emailNotification.getMessageTypeName() );
    }

    /**
     * Test convert to should return null.
     */
    @Test
    public void testConvertToShouldReturnNull() {
        EmailNotification destination = null;
        AgentSource source = new AgentSource();
        source.setState( "CA1" );
        source.setEmail( "test@email.com" );
        source.setName( "Test Name" );
        EmailNotification emailNotification = preliminaryTitleReportsEmailNotificationBuilder.convertTo( source,
                destination );
        assertNull( emailNotification );
    }

    /**
     * Test convert from should throw exception.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFromShouldThrowException() {
        EmailNotification source = new EmailNotification();
        Map< String, Object > destination = null;
        preliminaryTitleReportsEmailNotificationBuilder.convertFrom( source, destination );
    }

    /**
     * Test get escrow mail delay minutes.
     */
    @Test
    public void testGetEscrowMailDelayMinutes() {
        int escrowMailDelayMinutes = preliminaryTitleReportsEmailNotificationBuilder
                .getPreliminaryTitleReportsMailDelayMinutes();
        assertEquals( minutes, escrowMailDelayMinutes );
    }

    /**
     * Test set escrow mail delay minutes.
     */
    @Test
    public void testSetEscrowMailDelayMinutes() {
        int val = 1;
        preliminaryTitleReportsEmailNotificationBuilder.setPreliminaryTitleReportsMailDelayMinutes( val );
        assertEquals( val, ReflectionTestUtils.getField( preliminaryTitleReportsEmailNotificationBuilder,
                "preliminaryTitleReportsMailDelayMinutes" ) );
    }
}
