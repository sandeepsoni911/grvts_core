package com.owners.gravitas.business.builder.scraping.ocl;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.lang.reflect.Field;
import java.util.Date;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.integration.mail.AbstractMailReceiver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.scraping.MessageStringBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.OclEmailDto;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.exception.AffiliateEmailParsingException;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

/**
 * The test class OclEmailDtoBuilderTest
 *
 * @author raviz
 *
 */
public class OclEmailDtoBuilderTest extends AbstractBaseMockitoTest {

    /** The ocl email dto builder. */
    @InjectMocks
    private OclEmailDtoBuilder oclEmailDtoBuilder;

    /** The message string builder. */
    @Mock
    private MessageStringBuilder messageStringBuilder;

    /**
     * Test convert to from string.
     */
    @Test
    public void testConvertToFromString() {
        final GenericLeadRequest leadRequest = oclEmailDtoBuilder.convertTo( "test" );
        Assert.assertNull( leadRequest );
    }

    /**
     * Test convert to ocl email dto.
     *
     * @throws Exception
     */
    @Test
    public void testConvertToOclEmailDto() throws Exception {
        final Message message = initMessageFolderNotExists( "message Text" );
        final String emailBody = "Email: test@test.com " + "First Name: Test " + "Last Name: Test "
                + "Loan Phase: Pre-Qualification/Short Form " + "Trigger Event: TestEvent " + "Assigned MLO: Test "
                + "Status Date: 02-03-2017 " + "Note: Note field data " + "Loan Number: 545550058";

        when( messageStringBuilder.convertTo( message ) ).thenReturn( emailBody );

        final OclEmailDto oclEmailDto = oclEmailDtoBuilder.convertToOclEmailDto( message );

        verify( messageStringBuilder ).convertTo( message );
        assertNotNull( oclEmailDto );
        assertEquals( oclEmailDto.getFirstName(), "Test" );
        assertEquals( oclEmailDto.getLastName(), "Test" );
        assertEquals( oclEmailDto.getEmail(), "test@test.com" );
        assertEquals( oclEmailDto.getAssignedMLO(), "Test" );
        assertEquals( oclEmailDto.getLoanPhase(), "Pre-Qualification/Short Form" );
    }

    /**
     * Test convert to ocl email dto.
     *
     * @throws Exception
     */
    @Test( expectedExceptions = AffiliateEmailParsingException.class )
    public void testConvertToOclEmailDto_shouldThrowException() throws Exception {
        final Message message = initMessageFolderNotExists( "message Text" );

        when( messageStringBuilder.convertTo( message ) ).thenReturn( null );

        oclEmailDtoBuilder.convertToOclEmailDto( message );
    }

    /**
     * Test convert to ocl email dto.
     *
     * @throws Exception
     */
    @Test( expectedExceptions = AffiliateEmailParsingException.class )
    public void testConvertToOclEmailDto_shouldThrowParsingException() throws Exception {
        final Message message = initMessageFolderNotExists( "message Text" );
        final String emailBody = "Email: test@test.com " + "First Name: Test "
                + "Loan Phase: Pre-Qualification/Short Form " + "Trigger Event: TestEvent " + "Assigned MLO: Test "
                + "Status Date: 02-03-2017 " + "Note: Note field data " + "Loan Number: 545550058";
        when( messageStringBuilder.convertTo( message ) ).thenReturn( emailBody );

        oclEmailDtoBuilder.convertToOclEmailDto( message );
    }

    /**
     * Test convert to from ocl email dto.
     */
    @Test
    public void testConvertToFromOclEmailDto() {
        final OclEmailDto oclEmailDto = new OclEmailDto();
        oclEmailDto.setEmail( "xyz@owners.com" );
        oclEmailDto.setLoanNumber( "100000001" );
        oclEmailDto.setFirstName( "firstName" );
        oclEmailDto.setLastName( "lastName" );
        oclEmailDto.setLoanPhase( "Prospect" );

        final GenericLeadRequest leadRequest = oclEmailDtoBuilder.convertTo( oclEmailDto );

        assertNotNull( leadRequest );
        assertEquals( leadRequest.getFirstName(), oclEmailDto.getFirstName() );
        assertEquals( leadRequest.getLastName(), oclEmailDto.getLastName() );
        assertEquals( leadRequest.getEmail(), oclEmailDto.getEmail() );
        assertEquals( leadRequest.getLoanNumber(), Integer.parseInt( oclEmailDto.getLoanNumber() ) );
    }

    /**
     * Init message.
     *
     * @param messageText
     *            the message text
     * @return the mime message
     * @throws Exception
     *             the exception
     */
    private MimeMessage initMessageFolderNotExists( final String messageText ) throws Exception {
        final MimeMessage message = mock( MimeMessage.class );
        final InternetAddress fromAddress = new InternetAddress( "test <noReply@hubzu.com>" );
        final Address[] address = { fromAddress };
        when( message.getFrom() ).thenReturn( address );
        when( message.getAllRecipients() ).thenReturn( address );
        when( message.getSubject() ).thenReturn( "test" );
        when( message.getReceivedDate() ).thenReturn( new Date() );
        message.setText( messageText );
        message.setContent( messageText, "text/html" );
        final Field folderField = AbstractMailReceiver.class.getDeclaredField( "folder" );
        folderField.setAccessible( true );
        final Folder folder = mock( IMAPFolder.class );
        when( message.getFolder() ).thenReturn( folder );
        final IMAPStore store = mock( IMAPStore.class );
        when( folder.getStore() ).thenReturn( store );
        when( store.getFolder( anyString() ) ).thenReturn( folder );
        when( folder.exists() ).thenReturn( false );
        return message;
    }

}
