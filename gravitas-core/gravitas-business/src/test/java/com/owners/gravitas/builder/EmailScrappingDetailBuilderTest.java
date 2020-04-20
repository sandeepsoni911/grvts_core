package com.owners.gravitas.builder;

import java.lang.reflect.Field;
import java.util.Date;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.integration.mail.AbstractMailReceiver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.EmailScrappingDetailBuilder;
import com.owners.gravitas.business.builder.scraping.MessageStringBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.EmailScrappingDetails;
import com.owners.gravitas.util.EmailUtil;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

/**
 * Test class for EmailScrappingDetailBuilder
 * 
 * @author sandeepsoni
 *
 */
public class EmailScrappingDetailBuilderTest extends AbstractBaseMockitoTest {

	/** The EmailScrappingDetailBuilder */
	@InjectMocks
	EmailScrappingDetailBuilder emailScrappingDetailBuilder;

	/** The MessageStringBuilder */
	@Mock
	MessageStringBuilder messageStringBuilder;

	/** The Email Util */
	@Mock
	EmailUtil emailUtil;

	/**
	 * To test ConvertTo
	 * 
	 * @throws Exception
	 */
	@Test
	public void testConvertTo() throws Exception {
		final String messageTxt = initMessageText();
		MimeMessage message = initiMessageFolderNotExists(messageTxt);
		Mockito.when(messageStringBuilder.convertTo(message)).thenReturn(Mockito.anyString());
		Mockito.when(emailUtil.getMessageStringPlain("testMessage")).thenReturn(Mockito.anyString());
		Mockito.when(emailUtil.getSenderAddress(message)).thenReturn(Mockito.anyString());
		Mockito.when(emailUtil.getUserName(message)).thenReturn(Mockito.anyString());
		EmailScrappingDetails emailScrappingDetails = emailScrappingDetailBuilder.convertTo(message);
		Assert.assertNotNull(emailScrappingDetails);

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
		String subject = "some subject";
		final Address[] address = { fromAddress };
		Mockito.when(message.getFrom()).thenReturn(address);
		Mockito.when(message.getAllRecipients()).thenReturn(address);
		Mockito.when(message.getSubject()).thenReturn(subject);
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
