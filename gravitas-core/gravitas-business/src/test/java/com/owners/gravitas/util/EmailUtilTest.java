package com.owners.gravitas.util;

import java.lang.reflect.Field;
import java.util.Date;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.integration.mail.AbstractMailReceiver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;


/**
 * Test class for Email Utility class
 * @author sandeepsoni
 *
 */
public class EmailUtilTest extends AbstractBaseMockitoTest {
	
	@InjectMocks
	EmailUtil emailUtil;
	
	
	/**
	 * To test getSenderAddress
	 * @throws Exception 
	 */
	@Test
	public void testGetSenderAddress() throws Exception {
		Message message = initiMessageFolderNotExists("Some email body");
		String fromEmail = emailUtil.getSenderAddress(message);
		Assert.assertNotNull(fromEmail);
		Assert.assertEquals(fromEmail, "from@email.com");
	}
	
	
	
	/**
	 * To test getMessageStringPlain
	 * @throws Exception 
	 */
	@Test
	public void testGetMessageStringPlain() throws Exception {
		String body = "<html><body>this is email body</body></html>";
		String plainTextBody = emailUtil.getMessageStringPlain(body);
		Assert.assertNotNull(plainTextBody);
		Assert.assertEquals(plainTextBody, "this is email body");
	}
	
	/**
	 * To test get user name of mailbox
	 * @throws Exception 
	 */
	@Test
	public void testGetUserName() throws Exception {
		Message message = initiMessageFolderNotExists("Some email body");
		String userName = emailUtil.getUserName(message);
		Assert.assertNotNull(userName);
		Assert.assertEquals(userName, "sandeepsoni@gmail.com");
		
	}
	
	/**
	 * To test get subject line
	 * @throws Exception 
	 */
	@Test
	public void testgetEmailSubject() throws Exception {
		Message message = initiMessageFolderNotExists("Some email body");
		String subject = emailUtil.getEmailSubject(message);
		Assert.assertNotNull(subject);
		
	}
	
	/**
	 * To trimming of 
	 * Message subject 
	 * when more than 200 chars
	 * @throws Exception
	 */
	@Test
	public void testConvertTo_MessageSubjectTrimmed() throws Exception {
		Message message = initiMessageFolderNotExists("Some email body");
		String subject = emailUtil.getEmailSubject(message);
		Assert.assertNotNull(subject);
		Assert.assertEquals(subject.length(), 200);
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
		final InternetAddress fromAddress = new InternetAddress("from@email.com");
		final Address[] address = { fromAddress };
		Mockito.when(message.getFrom()).thenReturn(address);
		Mockito.when(message.getAllRecipients()).thenReturn(address);
		String longString201Chars = "This string contains 200 characters asldjf asldfj asfjlkj lkajf klasjd falks falasdjfakslfjd aslsdjf"
				+ " asldfj asfjlkj lkajf klasjd falks falasdjfakslfjd asldjf asldfj asfjlkj lkajf klasjd falks alsjf 201";
		Mockito.when(message.getSubject()).thenReturn(longString201Chars);
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
		final URLName urlName = Mockito.mock(URLName.class);
		Mockito.when(store.getURLName()).thenReturn(urlName);
		Mockito.when(urlName.getUsername()).thenReturn("sandeepsoni@gmail.com");
		return message;
	}

	

}
