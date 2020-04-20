package com.owners.gravitas.business.impl;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.integration.mail.AbstractMailReceiver;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.business.builder.request.ZillowHotLeadRequestBuilder;
import com.owners.gravitas.business.builder.scraping.MessageAttachmentBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.request.LeadRequest;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

/**
 * Test class for  extends ZillowHotlineLeadBusinessServiceImpl
 * @author sandeepsoni
 *
 */
public class ZillowHotlineLeadBusinessServiceImplTest  extends AbstractBaseMockitoTest {
	
	
	/** The zillow hotline lead service. */
    @InjectMocks
    private ZillowHotlineLeadBusinessServiceImpl zillowHotlineLeadBusinessServiceImpl;
    
    @Mock
	ZillowHotLeadRequestBuilder zillowHotLeadRequestBuilder;

	/** instance of {@link LeadBusinessService}. */
    @Mock
	LeadBusinessService leadBusinessService;

    @Mock
	MessageAttachmentBuilder messageAttachmentBuilder;
    
    @Mock
    private ZillowHotlineLeadBusinessServiceImpl zillowHotlineLeadBusinessServiceImplMocked;
	
	 /**
     * Before method.
     */
    @BeforeMethod( )
    private void beforeMethod() {
        ReflectionTestUtils.setField( zillowHotlineLeadBusinessServiceImpl, "attachmentFileName",
                "non_pilot_crosstab231.csv" );
        ReflectionTestUtils.setField( zillowHotlineLeadBusinessServiceImpl, "attachmentFileDelemeter",
                "TAB" );
        
    }

    @Test(enabled=false)
	public void testScrapeZillowHotlineLeadAccount() throws Exception {
		MimeMessage message = initiMessageFolderNotExists("Some test mesage");
		message.setFileName("non_pilot_crosstab231.csv");
		List<Part> attachmentList = new ArrayList<Part>();
		attachmentList.add(message);
		Mockito.when(messageAttachmentBuilder.convertTo(message, null)).thenReturn(attachmentList);
		List<LeadRequest> leadRequestList = new ArrayList<LeadRequest>();
		Mockito.when(zillowHotlineLeadBusinessServiceImplMocked
				.getContactIdListWithEmailAsNull(message, "UTF-8")).thenReturn(leadRequestList);
		zillowHotlineLeadBusinessServiceImpl.scrapeZillowHotlineLeadAccount(message);
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
		final InternetAddress fromAddress = new InternetAddress("test <noReply@owners.com>");
		final Address[] address = { fromAddress };
		Mockito.when(message.getFrom()).thenReturn(address);
		Mockito.when(message.getAllRecipients()).thenReturn(address);
		Mockito.when(message.getSubject()).thenReturn("test");
		Mockito.when(message.getReceivedDate()).thenReturn(new Date());
		Mockito.when(message.getHeader("Message-Id")).thenReturn(new String[] { "headerValue1" });
		Mockito.when(message.getFileName()).thenReturn("non_pilot_crosstab231.csv");
		Mockito.when(message.getContentType()).thenReturn("text/csv");
		Mockito.when(message.getDisposition()).thenReturn(Part.ATTACHMENT);
		String text = "contactId\temail\tFull Name\t phoneNumber\t AgentName"
					+ "\n12345\t \t \t9611604761\t agName";
		InputStream inputFileStream = new ByteArrayInputStream(text.getBytes());
		Mockito.when(message.getInputStream()).thenReturn(inputFileStream);
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

}
