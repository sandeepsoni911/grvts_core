package com.owners.gravitas.builder;

import java.util.Arrays;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.api.services.gmail.model.Message;
import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.business.builder.GmailMessageBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.request.EmailRequest;

/**
 * The Class GmailMessageBuilderTest.
 *
 * @author vishwanathm
 */
public class GmailMessageBuilderTest extends AbstractBaseMockitoTest {

    /** The gmail message builder. */
    @InjectMocks
    private GmailMessageBuilder gmailMessageBuilder;

    /**
     * Test convert to.
     *
     * @param er
     *            the er
     * @param msg
     *            the msg
     */
    @Test( dataProvider = "loadEmailRequest" )
    public void testConvertTo( EmailRequest er, final Message msg ) {
        Message message = gmailMessageBuilder.convertTo( er, msg );
        Assert.assertNotNull( message );
    }

    @Test
    public void testConvertToNullSpurce() {
        Message message = gmailMessageBuilder.convertTo( null );
        Assert.assertNull( message );

        message = gmailMessageBuilder.convertTo( null, null );
        Assert.assertNull( message );
    }

    /**
     * Load email request.
     *
     * @return the object[][]
     */
    @DataProvider( name = "loadEmailRequest" )
    public Object[][] loadEmailRequest() {
        EmailRequest er = new EmailRequest();
        er.setTo( Arrays.asList( "test@test.com" ) );
        er.setCc( Arrays.asList( "test@test.com" ) );
        er.setBcc( Arrays.asList( "test@test.com" ) );
        er.setSubject( "subject" );
        er.setBodyText( "bodyText" );
        er.setAttachmentUrls( Arrays.asList( "http://test.com" ) );

        EmailRequest er1 = new EmailRequest();
        er1.setTo( Arrays.asList( "test@test.com" ) );
        er1.setSubject( "subject" );
        er1.setBodyText( "bodyText" );

        return new Object[][] { { er, null }, { er, new Message() }, { er1, null } };
    }

    /**
     * Test convert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFrom() {
        gmailMessageBuilder.convertFrom( new Message(), new EmailRequest() );
    }

}
