package com.owners.gravitas.builder;

import static com.owners.gravitas.constants.Constants.NOTIFICATION_CLIENT_ID;
import static org.junit.Assert.assertNull;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.email.Email;
import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.builder.SavedSearchFollowupEmailNotificationBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.constants.NotificationParameters;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.service.ContactEntityService;

/**
 * The Class SavedSearchFollowupEmailNotificationBuilderTest.
 *
 * @author raviz
 */
public class SavedSearchFollowupEmailNotificationBuilderTest extends AbstractBaseMockitoTest {

    /** The Saved search followup email notification builder. */
    @InjectMocks
    private SavedSearchFollowupEmailNotificationBuilder savedSearchFollowupEmailNotificationBuilder;
    
    @Mock
    private ContactEntityService contactServiceV1;

    /** The buyer first name. */
    private String buyerFirstName;

    /** The agent name. */
    private String agentFullName = "testAgentFullName";

    /** The from email. */
    private String fromEmail = "test@fromEmail.com";

    /** The reply to email. */
    private String replyToEmail = "test@replyToEmail.com";

    /**
     * Inits the data.
     */
    @BeforeMethod
    public void initData() {
        buyerFirstName = ( String ) getField( NotificationParameters.class, "BUYER_FIRST_NAME" );
        setField( savedSearchFollowupEmailNotificationBuilder, "agentFullName", agentFullName );
        setField( savedSearchFollowupEmailNotificationBuilder, "fromEmail", fromEmail );
        setField( savedSearchFollowupEmailNotificationBuilder, "replyToEmail", replyToEmail );
    }

    /**
     * Test convert to should return response when source is not null.
     */
    @Test
    public void testConvertToShouldReturnResponseWhenSourceIsNotNull() {
        final LeadSource source = new LeadSource();
        final String firstName = "testBuyer";
        final String testEmail = "test@test.com";
        source.setFirstName( firstName );
        source.setEmail( testEmail );
        Contact contact = new Contact();
        contact.setOwnersComId("124842d6-a495-4e53-928c-ea3163c7d3e0");
        when(contactServiceV1.findByCrmId(anyString())).thenReturn(contact);
        final EmailNotification response = savedSearchFollowupEmailNotificationBuilder.convertTo( source );

        final Email email = response.getEmail();
        assertNotNull( response );
        assertNotNull( email );
        assertEquals( email.getFromDisplayName(), agentFullName );
        assertEquals( email.getFromEmail(), fromEmail );
        assertEquals( email.getReplyToEmail(), replyToEmail );
        assertEquals( response.getClientId(), NOTIFICATION_CLIENT_ID );
        assertEquals( email.getParameterMap().get( buyerFirstName ), firstName );
        assertEquals( email.getRecipients().getToList().get( 0 ), testEmail );
        assertNull( response.getMessageTypeName() );
    }

    /**
     * Test convert to should return response when source has last name.
     */
    @Test
    public void testConvertToShouldReturnResponseWhenSourceHasLastName() {
        final LeadSource source = new LeadSource();
        final String lastName = "testBuyer";
        final String testEmail = "test@test.com";
        source.setLastName( lastName );
        source.setEmail( testEmail );
        Contact contact = new Contact();
        contact.setOwnersComId("124842d6-a495-4e53-928c-ea3163c7d3e0");
        when(contactServiceV1.findByCrmId(anyString())).thenReturn(contact);
        final EmailNotification response = savedSearchFollowupEmailNotificationBuilder.convertTo( source );

        final Email email = response.getEmail();
        assertNotNull( response );
        assertNotNull( email );
        assertEquals( email.getFromDisplayName(), agentFullName );
        assertEquals( email.getFromEmail(), fromEmail );
        assertEquals( email.getReplyToEmail(), replyToEmail );
        assertEquals( response.getClientId(), NOTIFICATION_CLIENT_ID );
        assertEquals( email.getParameterMap().get( buyerFirstName ), lastName );
        assertEquals( email.getRecipients().getToList().get( 0 ), testEmail );
        assertNull( response.getMessageTypeName() );
    }

    /**
     * Test convert to should return response when source is not null and
     * destination is passed.
     */
    @Test
    public void testConvertToShouldReturnResponseWhenSourceIsNotNullAndDestinationIsPassed() {
        final LeadSource source = new LeadSource();
        final String firstName = "testBuyer";
        final String testEmail = "test@test.com";
        source.setFirstName( firstName );
        source.setEmail( testEmail );
        final EmailNotification notification = new EmailNotification();
        notification.setMessageTypeName( "testMessageType" );
        Contact contact = new Contact();
        contact.setOwnersComId("124842d6-a495-4e53-928c-ea3163c7d3e0");
        when(contactServiceV1.findByCrmId(anyString())).thenReturn(contact);
        final EmailNotification response = savedSearchFollowupEmailNotificationBuilder.convertTo( source,
                notification );

        final Email email = response.getEmail();
        assertNotNull( response );
        assertNotNull( email );
        assertEquals( email.getFromDisplayName(), agentFullName );
        assertEquals( email.getFromEmail(), fromEmail );
        assertEquals( email.getReplyToEmail(), replyToEmail );
        assertEquals( response.getClientId(), NOTIFICATION_CLIENT_ID );
        assertEquals( email.getParameterMap().get( buyerFirstName ), firstName );
        assertEquals( email.getRecipients().getToList().get( 0 ), testEmail );
        assertEquals( response.getMessageTypeName(), "testMessageType" );
    }

    /**
     * Test convert to should return null when source is null.
     */
    @Test
    public void testConvertToShouldReturnNullWhenSourceIsNull() {
        final EmailNotification response = savedSearchFollowupEmailNotificationBuilder.convertTo( null );
        assertNull( response );
    }

    /**
     * Test convert from
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFrom() {
        savedSearchFollowupEmailNotificationBuilder.convertFrom( new EmailNotification() );
    }
}
