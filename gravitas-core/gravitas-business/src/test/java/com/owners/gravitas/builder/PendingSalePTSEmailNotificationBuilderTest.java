package com.owners.gravitas.builder;

import static org.junit.Assert.assertEquals;
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
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.builder.PendingSalePTSEmailNotificationBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class PendingSalePTSEmailNotificationBuilderTest.
 *
 * @author shivamm
 */
public class PendingSalePTSEmailNotificationBuilderTest extends AbstractBaseMockitoTest {

    /** The pending sale PTS email notification builder. */
    @InjectMocks
    private PendingSalePTSEmailNotificationBuilder pendingSalePTSEmailNotificationBuilder;

    /** The property writer. */
    @Mock
    private PropertyWriter propertyWriter;

    /** The Constant minutes. */
    private static final int minutes1 = 4320;

    /**
     * Sets the up.
     */
    @BeforeMethod
    public void setUp() {
        ReflectionTestUtils.setField( pendingSalePTSEmailNotificationBuilder, "agentPendingSaleMailDelayMinutes",
                minutes1 );
        ReflectionTestUtils.setField( pendingSalePTSEmailNotificationBuilder, "ptsFromEmail", "pts@owners.com" );
    }

    /**
     * Test convert to for FL state.
     */
    @Test
    public void testConvertToForFLState() {
        EmailNotification destination = new EmailNotification();
        AgentSource source = new AgentSource();
        source.setName( "test" );
        source.setEmail( "test@test.com" );
        OpportunitySource opSource = new OpportunitySource();
        opSource.setPropertyState( "FL" );
        opSource.setOpportunityType( "Buyer" );
        EmailNotification emailNotification = pendingSalePTSEmailNotificationBuilder.convertTo( true, source,
                opSource );
        assertEquals( ReflectionTestUtils.getField( PendingSalePTSEmailNotificationBuilder.class,
                "PTS_PENDING_SALE_EMAIL_FL_TX_OH_PA" ), emailNotification.getMessageTypeName() );
    }

    /**
     * Test convert to for TX state.
     */
    @Test
    public void testConvertToForTXState() {
        EmailNotification destination = new EmailNotification();
        AgentSource source = new AgentSource();
        source.setName( "test" );
        source.setEmail( "test@test.com" );
        OpportunitySource opSource = new OpportunitySource();
        opSource.setSellerPropertyState( "TX" );
        opSource.setOpportunityType( "Seller" );
        EmailNotification emailNotification = pendingSalePTSEmailNotificationBuilder.convertTo( true, source,
                opSource );
        assertEquals( ReflectionTestUtils.getField( PendingSalePTSEmailNotificationBuilder.class,
                "PTS_PENDING_SALE_EMAIL_FL_TX_OH_PA" ), emailNotification.getMessageTypeName() );
    }

    /**
     * Test convert to for OH state.
     */
    @Test
    public void testConvertToForOHState() {
        EmailNotification destination = new EmailNotification();
        AgentSource source = new AgentSource();
        source.setName( "test" );
        source.setEmail( "test@test.com" );
        OpportunitySource opSource = new OpportunitySource();
        opSource.setPropertyState( "OH" );
        opSource.setOpportunityType( "Buyer" );
        EmailNotification emailNotification = pendingSalePTSEmailNotificationBuilder.convertTo( true, source,
                opSource );
        assertEquals( ReflectionTestUtils.getField( PendingSalePTSEmailNotificationBuilder.class,
                "PTS_PENDING_SALE_EMAIL_FL_TX_OH_PA" ), emailNotification.getMessageTypeName() );
    }

    /**
     * Test convert to for PA state.
     */
    @Test
    public void testConvertToForPAState() {
        EmailNotification destination = new EmailNotification();
        AgentSource source = new AgentSource();
        source.setName( "test" );
        source.setEmail( "test@test.com" );
        OpportunitySource opSource = new OpportunitySource();
        opSource.setPropertyState( "PA" );
        opSource.setOpportunityType( "Buyer" );
        EmailNotification emailNotification = pendingSalePTSEmailNotificationBuilder.convertTo( true, source,
                opSource );
        assertEquals( ReflectionTestUtils.getField( PendingSalePTSEmailNotificationBuilder.class,
                "PTS_PENDING_SALE_EMAIL_FL_TX_OH_PA" ), emailNotification.getMessageTypeName() );
    }

    /**
     * Test convert to for CA state.
     */
    @Test
    public void testConvertToForCAState() {
        EmailNotification destination = new EmailNotification();
        AgentSource source = new AgentSource();
        source.setName( "test" );
        source.setEmail( "test@test.com" );
        OpportunitySource opSource = new OpportunitySource();
        opSource.setPropertyState( "CA" );
        opSource.setOpportunityType( "Buyer" );
        EmailNotification emailNotification = pendingSalePTSEmailNotificationBuilder.convertTo( true, source,
                opSource );
        assertEquals( ReflectionTestUtils.getField( PendingSalePTSEmailNotificationBuilder.class,
                "PTS_PENDING_SALE_EMAIL_CA" ), emailNotification.getMessageTypeName() );
    }

    /**
     * Test convert to for GA state.
     */
    @Test
    public void testConvertToForGAState() {
        EmailNotification destination = new EmailNotification();
        AgentSource source = new AgentSource();
        source.setName( "test" );
        source.setEmail( "test@test.com" );
        OpportunitySource opSource = new OpportunitySource();
        opSource.setPropertyState( "GA" );
        opSource.setOpportunityType( "Buyer" );
        EmailNotification emailNotification = pendingSalePTSEmailNotificationBuilder.convertTo( true, source,
                opSource );
        assertEquals( ReflectionTestUtils.getField( PendingSalePTSEmailNotificationBuilder.class,
                "PTS_PENDING_SALE_EMAIL_GA" ), emailNotification.getMessageTypeName() );
    }

    /**
     * Test convert to for MA state.
     */
    @Test
    public void testConvertToForMAState() {
        EmailNotification destination = new EmailNotification();
        AgentSource source = new AgentSource();
        source.setName( "test" );
        source.setEmail( "test@test.com" );
        OpportunitySource opSource = new OpportunitySource();
        opSource.setPropertyState( "MA" );
        opSource.setOpportunityType( "Buyer" );
        EmailNotification emailNotification = pendingSalePTSEmailNotificationBuilder.convertTo( true, source,
                opSource );
        assertEquals( ReflectionTestUtils.getField( PendingSalePTSEmailNotificationBuilder.class,
                "PTS_PENDING_SALE_EMAIL_MA" ), emailNotification.getMessageTypeName() );
    }

    /**
     * Test convert to for IL state.
     */
    @Test
    public void testConvertToForILState() {
        EmailNotification destination = new EmailNotification();
        AgentSource source = new AgentSource();
        source.setName( "test" );
        source.setEmail( "test@test.com" );
        OpportunitySource opSource = new OpportunitySource();
        opSource.setPropertyState( "IL" );
        opSource.setOpportunityType( "Buyer" );
        EmailNotification emailNotification = pendingSalePTSEmailNotificationBuilder.convertTo( true, source,
                opSource );
        assertEquals( ReflectionTestUtils.getField( PendingSalePTSEmailNotificationBuilder.class,
                "PTS_PENDING_SALE_EMAIL_IL" ), emailNotification.getMessageTypeName() );
    }

    /**
     * Test convert to for different state.
     */
    @Test
    public void testConvertToForDifferentStateWithPTS() {
        EmailNotification destination = new EmailNotification();
        AgentSource source = new AgentSource();
        source.setName( "test" );
        source.setEmail( "test@test.com" );
        OpportunitySource opSource = new OpportunitySource();
        opSource.setPropertyState( "Test" );
        opSource.setOpportunityType( "Buyer" );
        EmailNotification emailNotification = pendingSalePTSEmailNotificationBuilder.convertTo( true, source,
                opSource );
        assertEquals( null, emailNotification );
    }

    /**
     * Test convert to for different state with PTS false.
     */
    @Test
    public void testConvertToForDifferentStateWithPTSFalse() {
        EmailNotification destination = new EmailNotification();
        AgentSource source = new AgentSource();
        source.setName( "test" );
        source.setEmail( "test@test.com" );
        OpportunitySource opSource = new OpportunitySource();
        opSource.setPropertyState( "Test" );
        opSource.setOpportunityType( "Buyer" );
        EmailNotification emailNotification = pendingSalePTSEmailNotificationBuilder.convertTo( false, source,
                opSource );
        assertEquals( ReflectionTestUtils.getField( PendingSalePTSEmailNotificationBuilder.class,
                "PTS_PENDING_SALE_WITHOUT_PTS" ), emailNotification.getMessageTypeName() );
    }

    /**
     * Test convert from should throw exception.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFromShouldThrowException() {
        EmailNotification source = null;
        Map< String, Object > destination = null;
        pendingSalePTSEmailNotificationBuilder.convertFrom( source, destination );
    }

    /**
     * Test convert to for null source.
     */
    @Test
    public void testConvertToForNullSource() {
        EmailNotification destination = null;
        AgentSource source = null;
        OpportunitySource opSource = new OpportunitySource();
        opSource.setPropertyState( "FL" );
        opSource.setOpportunityType( "Buyer" );
        EmailNotification emailNotification = pendingSalePTSEmailNotificationBuilder.convertTo( false, source,
                opSource );
        assertEquals( destination, emailNotification );
    }

    /**
     * Test get agent pending sale mail delay minutes.
     */
    @Test
    public void testGetAgentPendingSaleMailDelayMinutes() {
        int agentPendingSaleMailDelayMinutes = pendingSalePTSEmailNotificationBuilder
                .getAgentPendingSaleMailDelayMinutes();
        assertEquals( minutes1, agentPendingSaleMailDelayMinutes );
    }

    /**
     * Test set pending sale mail delay minutes.
     */
    @Test
    public void testSetPendingSaleMailDelayMinutes() {
        int val = 1;
        pendingSalePTSEmailNotificationBuilder.setAgentPendingSaleMailDelayMinutes( val );
        assertEquals( val, ReflectionTestUtils.getField( pendingSalePTSEmailNotificationBuilder,
                "agentPendingSaleMailDelayMinutes" ) );
        verify( propertyWriter ).saveJmxProperty( Mockito.anyString(), Mockito.anyInt() );
    }
}
