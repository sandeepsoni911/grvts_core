package com.owners.gravitas.builder;

import static com.owners.gravitas.enums.RecordType.BUYER;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.builder.ClientFollowUpEmailNotificationBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.ClientFollowupConfig;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.enums.RecordType;

/**
 * The Class ClientFollowUpEmailNotificationBuilderTest.
 *
 * @author raviz
 */
public class ClientFollowUpEmailNotificationBuilderTest extends AbstractBaseMockitoTest {

    /** The client follow up email notification builder. */
    @InjectMocks
    private ClientFollowUpEmailNotificationBuilder clientFollowUpEmailNotificationBuilder;

    /** The client followup config. */
    @Mock
    private ClientFollowupConfig clientFollowupConfig;

    /**
     * Test convert to for buyer.
     *
     * @param opportunitySource
     *            the opportunity source
     */
    @Test( dataProvider = "opportunitySourceDataProvider" )
    public void testConvertToForBuyer( final OpportunitySource opportunitySource ) {
        opportunitySource.setOpportunityType( BUYER.getType() );
        final EmailNotification notification = clientFollowUpEmailNotificationBuilder.convertTo( opportunitySource );
        assertNotNull( notification );
        assertEquals( notification.getMessageTypeName(),
                getField( clientFollowUpEmailNotificationBuilder, "BUYER_MESSAGE_TYPE_NAME" ).toString() );
        assertEquals( notification.getEmail().getRecipients().getToList(),
                opportunitySource.getPrimaryContact().getEmails() );
        final String receiverName = getField( clientFollowUpEmailNotificationBuilder, "RECEIVER_NAME" ).toString();
        assertEquals( notification.getEmail().getParameterMap().get( receiverName ),
                opportunitySource.getPrimaryContact().getFirstName() );
    }

    /**
     * Test convert to for seller.
     *
     * @param opportunitySource
     *            the opportunity source
     */
    @Test( dataProvider = "opportunitySourceDataProvider" )
    public void testConvertToForSeller( final OpportunitySource opportunitySource ) {
        opportunitySource.setOpportunityType( RecordType.SELLER.getType() );
        opportunitySource.getPrimaryContact().setFirstName( "" );
        opportunitySource.getPrimaryContact().setLastName( "Test" );
        final EmailNotification notification = clientFollowUpEmailNotificationBuilder.convertTo( opportunitySource );
        assertNotNull( notification );
        assertEquals( notification.getMessageTypeName(),
                getField( clientFollowUpEmailNotificationBuilder, "SELLER_MESSAGE_TYPE_NAME" ).toString() );
        assertEquals( notification.getEmail().getRecipients().getToList(),
                opportunitySource.getPrimaryContact().getEmails() );
        final String receiverName = getField( clientFollowUpEmailNotificationBuilder, "RECEIVER_NAME" ).toString();
        assertEquals( notification.getEmail().getParameterMap().get( receiverName ),
                opportunitySource.getPrimaryContact().getLastName() );
    }

    /**
     * Test convert to if source is null.
     */
    @Test
    public void testConvertToIfSourceIsNull() {
        final EmailNotification notification = clientFollowUpEmailNotificationBuilder.convertTo( null );
        assertNull( notification );
        verifyZeroInteractions( clientFollowupConfig );
    }

    /**
     * Test convert from
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFrom() {
        clientFollowUpEmailNotificationBuilder.convertFrom( new EmailNotification() );
    }

    /**
     * Gets the opportunity source.
     *
     * @return the opportunity source
     */
    @DataProvider( name = "opportunitySourceDataProvider" )
    private Object[][] getOpportunitySource() {
        final OpportunitySource opportunitySource = new OpportunitySource();
        final Contact contact = new Contact();
        contact.setFirstName( "Test" );
        opportunitySource.setPrimaryContact( contact );
        return new Object[][] { { opportunitySource } };
    }
}
