package com.owners.gravitas.service.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.Gmail.Users;
import com.google.api.services.gmail.Gmail.Users.Messages;
import com.google.api.services.gmail.Gmail.Users.Messages.Send;
import com.google.api.services.gmail.model.Message;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.exception.ApplicationException;

/**
 * The Class GmailServiceImplTest.
 *
 * @author raviz
 */
public class GmailServiceImplTest extends AbstractBaseMockitoTest {

    /** The gmail service impl. */
    @InjectMocks
    private GmailServiceImpl gmailServiceImpl;

    /** The gmail service. */
    @Mock
    private Gmail gmailService;

    /**
     * Test send email should send email.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testSendEmailShouldSendEmail() throws IOException {
        final Message actualMessage = new Message();
        final Message expectedMessage = new Message();
        expectedMessage.setId( "test" );
        final Users users = mock( Users.class );
        final Messages messages = mock( Messages.class );
        final Send send = mock( Send.class );

        when( gmailService.users() ).thenReturn( users );
        when( users.messages() ).thenReturn( messages );
        when( messages.send( Constants.GMAIL_USER_ID, actualMessage ) ).thenReturn( send );
        when( send.execute() ).thenReturn( expectedMessage );

        final String messageId = gmailServiceImpl.sendEmail( gmailService, actualMessage );
        assertEquals( expectedMessage.getId(), messageId );
    }

    /**
     * Test send email should throw application exception.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testSendEmailShouldThrowApplicationException() throws IOException {
        Mockito.reset( gmailService );
        final Message actualMessage = new Message();
        final Message expectedMessage = new Message();
        expectedMessage.setId( "test" );
        final Users users = mock( Users.class );
        final Messages messages = mock( Messages.class );
        final Send send = mock( Send.class );

        when( gmailService.users() ).thenReturn( users );
        when( users.messages() ).thenReturn( messages );
        when( messages.send( Constants.GMAIL_USER_ID, actualMessage ) ).thenReturn( send );
        when( send.execute() ).thenThrow( IOException.class );

        gmailServiceImpl.sendEmail( gmailService, actualMessage );
    }
}
