package com.owners.gravitas.builder;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.builder.ConvertLeadToOpportunityBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.Contact;

/**
 * The Class ConvertLeadToOpportunityBuilderTest.
 *
 * @author shivamm
 */
public class ConvertLeadToOpportunityBuilderTest extends AbstractBaseMockitoTest {

    /** The convert lead to opportunity builder. */
    @InjectMocks
    private ConvertLeadToOpportunityBuilder convertLeadToOpportunityBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        OpportunitySource opportunitySource = new OpportunitySource();
        Contact contact = new Contact();
        contact.setFirstName( "test" );
        contact.setLastName( "test" );
        List<String> emails = new ArrayList<String>();
        emails.add( "test" );
        contact.setEmails( emails );
        opportunitySource.setPrimaryContact( contact );
        // opportunitySource.setLastName( "test" );
        EmailNotification request = convertLeadToOpportunityBuilder.convertTo( opportunitySource );
        Assert.assertNotNull( request );

        // request = convertLeadToOpportunityBuilder.convertTo(genReq, new
        // EmailNotification() );
        // Assert.assertNotNull( request );

        Assert.assertNotNull( request );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        convertLeadToOpportunityBuilder.convertFrom( new EmailNotification() );
    }
}
