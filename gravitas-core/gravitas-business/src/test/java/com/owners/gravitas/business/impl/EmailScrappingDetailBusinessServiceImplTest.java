package com.owners.gravitas.business.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.integration.mail.AbstractMailReceiver;
import org.testng.annotations.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Date;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.owners.gravitas.business.builder.EmailScrappingDetailBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.EmailScrappingDetails;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.EmailScrappingDetailService;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

/**
 * The test class for EmailScrappingDetailBusinessServiceImpl
 * 
 * @author sandeepsoni
 *
 */
public class EmailScrappingDetailBusinessServiceImplTest extends AbstractBaseMockitoTest {

	/**
	 * The Email Parsing Log Business Service Impl
	 */
	@InjectMocks
	EmailScrappingDetailBusinessServiceImpl emailScrappingDetailBusinessServiceImpl;

	@Mock
	EmailScrappingDetailService emailScrappingDetailService;

	@Mock
	EmailScrappingDetailBuilder emailScrappingDetailBuilder;

	/**
	 * Test save lead email parsing log.
	 *
	 * @param parsingLog
	 *            the parsing log
	 * @throws Exception
	 */
	@Test
	public void testScrapeEmailDetails() throws Exception {

		final String messageTxt = initMessageText();
		MimeMessage message = initiMessageFolderNotExists(messageTxt);
		EmailScrappingDetails emailScrappingDetails = new EmailScrappingDetails();
		emailScrappingDetails.setFromEmail("fromEmail");
		emailScrappingDetails.setEmailSubject("emailSubject");
		when(emailScrappingDetailService.save(emailScrappingDetails)).thenReturn(emailScrappingDetails);
		when(emailScrappingDetailBuilder.convertTo(message)).thenReturn(emailScrappingDetails);
		emailScrappingDetailBusinessServiceImpl.scrapeEmailDetails(message);
		verify(emailScrappingDetailService).save(any(EmailScrappingDetails.class));

	}
	
	/**
     * Test for application exception.
     *
     * @throws Exception
     *             the exception
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testForApplicationException() throws Exception {
        final MimeMessage message = Mockito.mock( MimeMessage.class );
        Mockito.when( message.getFrom() ).thenThrow( new MessagingException() );
        when(emailScrappingDetailBuilder.convertTo(message)).thenThrow(new 
        		ApplicationException("error",new Exception()));
        emailScrappingDetailBusinessServiceImpl.scrapeEmailDetails(message);
    }


	/**
	 * Inti message.
	 *
	 * @param messageTxt
	 *            the message txt
	 * @return the mime message
	 * @throws Exception
	 *             the exception
	 */
	private MimeMessage initiMessageFolderNotExists(final String messageTxt) throws Exception {
		final MimeMessage message = Mockito.mock(MimeMessage.class);
		final InternetAddress fromAddress = new InternetAddress("test <noReply@hubzu.com>");
		final Address[] address = { fromAddress };
		Mockito.when(message.getFrom()).thenReturn(address);
		Mockito.when(message.getAllRecipients()).thenReturn(address);
		Mockito.when(message.getSubject()).thenReturn("test");
		Mockito.when(message.getReceivedDate()).thenReturn(new Date());
		Mockito.when(message.getHeader("Message-Id")).thenReturn(new String[] { "headerValue1" });
		message.setText(messageTxt);
		message.setContent(messageTxt, "text/html");
		message.setHeader("Message-Id", "header");
		final Field folderField = AbstractMailReceiver.class.getDeclaredField("folder");
		folderField.setAccessible(true);
		final Folder folder = Mockito.mock(IMAPFolder.class);
		Mockito.when(message.getFolder()).thenReturn(folder);
		final IMAPStore store = Mockito.mock(IMAPStore.class);
		Mockito.when(folder.getStore()).thenReturn(store);
		Mockito.when(store.getFolder(Mockito.anyString())).thenReturn(folder);
		Mockito.when(folder.exists()).thenReturn(false);
		return message;
	}

	/**
	 * Inits the message text.
	 *
	 * @return the string
	 */
	private String initMessageText() {
		final String messageTxt = "This is email body for test ";
		return messageTxt;
	}

}
