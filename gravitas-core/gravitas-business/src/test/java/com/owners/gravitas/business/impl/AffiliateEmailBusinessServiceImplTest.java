package com.owners.gravitas.business.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.integration.mail.AbstractMailReceiver;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.business.builder.AffiliateErrorNotificationBuilder;
import com.owners.gravitas.business.builder.scraping.LeadBuilderFactory;
import com.owners.gravitas.business.builder.scraping.MessageStringBuilder;
import com.owners.gravitas.business.builder.scraping.hubzu.HubzuEmailLeadBuilder;
import com.owners.gravitas.business.builder.scraping.owners.OwnersEmailLeadBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.NotificationEmailConfig;
import com.owners.gravitas.domain.entity.LeadEmailParsingLog;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.LeadResponse;
import com.owners.gravitas.enums.LeadRequestType;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.exception.AffiliateEmailException;
import com.owners.gravitas.exception.AffiliateEmailParsingException;
import com.owners.gravitas.exception.AffiliateEmailValidationException;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.CRMQueryService;
import com.owners.gravitas.service.LeadService;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.validator.LeadValidator;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

// TODO: Auto-generated Javadoc
/**
 * The Class AffiliateEmailBusinessServiceImplTest.
 *
 * @author vishwanathm
 */
public class AffiliateEmailBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The affiliate email business service impl. */
    @InjectMocks
    private AffiliateEmailBusinessServiceImpl affiliateEmailBusinessServiceImpl;

    /** The lead service. */
    @Mock
    private LeadService leadService;

    /** The crm query service. */
    @Mock
    private CRMQueryService crmQueryService;

    /** The message string builder. */
    @Mock
    private MessageStringBuilder messageStringBuilder;

    /** The hubzu email lead builder. */
    @Mock
    private HubzuEmailLeadBuilder hubzuEmailLeadBuilder;

    /** The owners email lead builder. */
    @Mock
    private OwnersEmailLeadBuilder ownersEmailLeadBuilder;

    /** The lead builder factory. */
    @Mock
    private LeadBuilderFactory leadBuilderFactory;

    /** The buyer business service. */
    @Mock
    private LeadBusinessService buyerBusinessService;

    /** The affiliate lead parsing error notification builder. */
    @Mock
    private AffiliateErrorNotificationBuilder affiliateErrorNotificationBuilder;

    /** The mail service. */
    @Mock
    private MailService mailService;

    /** The mime multipart. */
    @Mock
    private MimeMultipart mimeMultipart;

    /** The body part. */
    @Mock
    private BodyPart bodyPart;

    /** The message. */
    @Mock
    private Message message;

    /** The message source. */
    @Mock
    private MessageSource messageSource;

    /**
     * Before test.
     */
    @Mock
    private AffiliateEmailException affiliateEmailException;

    /** The lead validator. */
    @Mock
    private LeadValidator leadValidator;

    /**
     * Before test.
     */
    @BeforeMethod
    public void beforeTest() {
        ReflectionTestUtils.setField( affiliateEmailBusinessServiceImpl, "leadGeneratedLabel", "test" );
        Mockito.reset( leadService );
        Mockito.reset( crmQueryService );
    }

    /**
     * Test scrap email message.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testScrapHubzuEmailMessage() throws Exception {
        final String messageTxt = initMessageText();
        MimeMessage message = initiMessageFolderNotExists( messageTxt );
        Mockito.when( leadBuilderFactory.getEmailLeadBuilder( Mockito.any(), Mockito.any() ) )
                .thenReturn( hubzuEmailLeadBuilder );
        Mockito.when( messageStringBuilder.convertTo( Mockito.any( Message.class ) ) ).thenReturn( messageTxt );
        final GenericLeadRequest leadRequest = new GenericLeadRequest();
        leadRequest.setLastName( "leadRequest" );
        leadRequest.setLeadType( RecordType.HUBZU.toString() );
        leadRequest.setSource( "source" );
        leadRequest.setEmail( "test@test.com" );
        leadRequest.setLeadSourceUrl( "http://test.com" );
        leadRequest.setRequestType( LeadRequestType.MAKE_OFFER.toString() );

        final Set< String > subjects = new HashSet<>();
        subjects.add( "xyz" );
        subjects.add( "test" );
        Mockito.when( leadBuilderFactory.getSubjects( Mockito.any() ) ).thenReturn( subjects );
        Mockito.when( hubzuEmailLeadBuilder.convertTo( Mockito.any( Message.class ) ) ).thenReturn( leadRequest );
        Mockito.when( buyerBusinessService.createLead( leadRequest, false, null ) )
                .thenReturn( new LeadResponse( Status.SUCCESS, "", "" ) );
        Mockito.when( message.getHeader( Mockito.anyString() ) ).thenReturn( new String[] { "test" } );
        Folder inbox = message.getFolder();
        Mockito.when( inbox.getMessages() ).thenReturn( new Message[0] );
        affiliateEmailBusinessServiceImpl.scrapeHubzuEmailMessage( message );

        Mockito.verify( buyerBusinessService ).createLead( leadRequest, false, null );
    }

    /**
     * Test scrap email message.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testScrapOwnersEmailMessage() throws Exception {
        final String messageTxt = initMessageText();
        MimeMessage message = initiMessageFolderNotExists( messageTxt );
        Mockito.when( leadBuilderFactory.getEmailLeadBuilder( Mockito.any(), Mockito.any() ) )
                .thenReturn( ownersEmailLeadBuilder );
        Mockito.when( messageStringBuilder.convertTo( Mockito.any( Message.class ) ) ).thenReturn( messageTxt );
        final GenericLeadRequest leadRequest = new GenericLeadRequest();
        leadRequest.setLastName( "leadRequest" );
        leadRequest.setLeadType( RecordType.HUBZU.toString() );
        leadRequest.setSource( "source" );
        leadRequest.setEmail( "test@test.com" );
        leadRequest.setLeadSourceUrl( "http://test.com" );
        leadRequest.setRequestType( LeadRequestType.MAKE_OFFER.toString() );

        final Set< String > subjects = new HashSet<>();
        subjects.add( "xyz" );
        subjects.add( "test" );
        Mockito.when( leadBuilderFactory.getSubjects( Mockito.any() ) ).thenReturn( subjects );
        Mockito.when( ownersEmailLeadBuilder.convertTo( Mockito.any( Message.class ) ) ).thenReturn( leadRequest );
        Mockito.when( buyerBusinessService.createLead( leadRequest, false, null ) )
                .thenReturn( new LeadResponse( Status.SUCCESS, "", "" ) );
        Mockito.when( message.getHeader( Mockito.anyString() ) ).thenReturn( new String[] { "test" } );
        Folder inbox = message.getFolder();
        Mockito.when( inbox.getMessages() ).thenReturn( new Message[0] );
        affiliateEmailBusinessServiceImpl.scrapeOwnersEmailMessage( message );

        Mockito.verify( buyerBusinessService ).createLead( leadRequest, false, null );
    }

    /**
     * Test scrap email message folder exists.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testScrapEmailMessageFolderExists() throws Exception {
        final String messageTxt = initMessageText();
        final MimeMessage message = initiMessageFolderExists( messageTxt );

        Mockito.when( leadBuilderFactory.getEmailLeadBuilder( Mockito.any(), Mockito.any( Message.class ) ) )
                .thenReturn( hubzuEmailLeadBuilder );
        Mockito.when( messageStringBuilder.convertTo( Mockito.any( Message.class ) ) ).thenReturn( messageTxt );
        final GenericLeadRequest leadRequest = new GenericLeadRequest();
        leadRequest.setLastName( "leadRequest" );
        leadRequest.setLeadType( RecordType.HUBZU.toString() );
        leadRequest.setSource( "source" );
        leadRequest.setEmail( "test@test.com" );
        leadRequest.setLeadSourceUrl( "http://test.com" );
        leadRequest.setRequestType( LeadRequestType.MAKE_OFFER.toString() );
        final Set< String > subjects = new HashSet<>();
        subjects.add( "test" );
        subjects.add( "xyz" );
        Mockito.when( leadBuilderFactory.getSubjects( Mockito.any() ) ).thenReturn( subjects );
        Mockito.when( hubzuEmailLeadBuilder.convertTo( Mockito.any( Message.class ) ) ).thenReturn( leadRequest );
        Mockito.when( buyerBusinessService.createLead( leadRequest, false, null ) )
                .thenReturn( new LeadResponse( Status.SUCCESS, "", "" ) );
        Mockito.when( message.getHeader( Mockito.anyString() ) ).thenReturn( new String[] { "test" } );
        Folder inbox = message.getFolder();
        Mockito.when( inbox.getMessages() ).thenReturn( new Message[0] );

        affiliateEmailBusinessServiceImpl.scrapeHubzuEmailMessage( message );
        Mockito.verify( buyerBusinessService ).createLead( leadRequest, false, null );
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

        affiliateEmailBusinessServiceImpl.scrapeHubzuEmailMessage( message );
    }

    /**
     * Test for owner id application exception.
     *
     * @throws Exception
     *             the exception
     */
    @Test( expectedExceptions = AffiliateEmailValidationException.class )
    public void testForAffiliateEmailValidationException() throws Exception {
        GenericLeadRequest leadRequest = new GenericLeadRequest();
        final String messageTxt = initMessageText();
        final MimeMessage message = initiMessageFolderNotExists( messageTxt );
        Mockito.when( leadBuilderFactory.getEmailLeadBuilder( Mockito.any(), Mockito.any( Message.class ) ) )
                .thenReturn( hubzuEmailLeadBuilder );
        Mockito.when( hubzuEmailLeadBuilder.convertTo( message ) ).thenReturn( leadRequest );
        Mockito.doThrow( AffiliateEmailValidationException.class ).when( leadValidator )
                .validateLeadRequest( leadRequest, message );
        affiliateEmailBusinessServiceImpl.scrapeHubzuEmailMessage( message );
    }

    /**
     * Test handle affiliate email parsing error.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testHandleHubzeAffiliateEmailParsingError() throws Exception {
        final String messageTxt = initMessageText();
        final MimeMessage mailMessage = initiMessageFolderNotExists( messageTxt );
        EmailNotification en = new EmailNotification();
        Mockito.when( affiliateErrorNotificationBuilder.convertTo( Mockito.any() ) ).thenReturn( en );
        Mockito.when( affiliateEmailException.getMailMessage() ).thenReturn( mailMessage );
        Mockito.when( mailMessage.getContent() ).thenReturn( mimeMultipart );
        Mockito.when( messageSource.getMessage( Mockito.anyString(), Mockito.any(), Mockito.any( Locale.class ) ) )
                .thenReturn( "value" );
        Mockito.when( mimeMultipart.getBodyPart( 1 ) ).thenReturn( bodyPart );
        Mockito.when( bodyPart.getContent() ).thenReturn( "test" );
        affiliateEmailBusinessServiceImpl.handleEmailParsingError( "errorId",
                new AffiliateEmailParsingException( mailMessage, "message", new Exception() ),
                new NotificationEmailConfig( "", "" ) );

        Mockito.verify( mailService ).send( en );
    }

    /**
     * Handle affiliate email validation error.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testHandleHubzuAffiliateEmailValidationError() throws Exception {
        final String messageTxt = initMessageText();
        final MimeMessage mailMessage = initiMessageFolderNotExists( messageTxt );
        EmailNotification en = new EmailNotification();
        Mockito.when( affiliateErrorNotificationBuilder.convertTo( Mockito.any() ) ).thenReturn( en );
        Map< String, List< String > > constraint = new HashMap< String, List< String > >();
        List< String > constraints = new ArrayList<>();
        constraints.add( "test" );
        constraint.put( "test", constraints );

        Mockito.when( affiliateEmailException.getMailMessage() ).thenReturn( mailMessage );
        Mockito.when( mailMessage.getContent() ).thenReturn( mimeMultipart );
        Mockito.when( messageSource.getMessage( Mockito.anyString(), Mockito.any(), Mockito.any( Locale.class ) ) )
                .thenReturn( "value" );
        Mockito.when( mimeMultipart.getBodyPart( 1 ) ).thenReturn( bodyPart );
        Mockito.when( bodyPart.getContent() ).thenReturn( "test" );
        affiliateEmailBusinessServiceImpl.handleEmailValidationError( "errorId",
                new AffiliateEmailValidationException( mailMessage, "message", constraint ),
                new NotificationEmailConfig( "", "" ) );

        Mockito.verify( mailService ).send( en );
    }

    /**
     * Test handle Owners affiliate email parsing error.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testHandleOwnersAffiliateEmailParsingError() throws Exception {
        final String messageTxt = initMessageText();
        final MimeMessage mailMessage = initiMessageFolderNotExists( messageTxt );
        EmailNotification en = new EmailNotification();
        Mockito.when( affiliateErrorNotificationBuilder.convertTo( Mockito.any() ) ).thenReturn( en );
        Mockito.when( affiliateEmailException.getMailMessage() ).thenReturn( mailMessage );
        Mockito.when( mailMessage.getContent() ).thenReturn( mimeMultipart );
        Mockito.when( messageSource.getMessage( Mockito.anyString(), Mockito.any(), Mockito.any( Locale.class ) ) )
                .thenReturn( "value" );
        Mockito.when( mimeMultipart.getBodyPart( 1 ) ).thenReturn( bodyPart );
        Mockito.when( bodyPart.getContent() ).thenReturn( "test" );
        affiliateEmailBusinessServiceImpl.handleEmailParsingError( "errorId",
                new AffiliateEmailParsingException( mailMessage, "message", new Exception() ),
                new NotificationEmailConfig( "", "" ) );

        Mockito.verify( mailService ).send( en );
    }

    /**
     * Handle Owners affiliate email validation error.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testHandleOwnersAffiliateEmailValidationError() throws Exception {
        final String messageTxt = initMessageText();
        final MimeMessage mailMessage = initiMessageFolderNotExists( messageTxt );
        EmailNotification en = new EmailNotification();
        Mockito.when( affiliateErrorNotificationBuilder.convertTo( Mockito.any() ) ).thenReturn( en );
        Map< String, List< String > > constraint = new HashMap< String, List< String > >();
        List< String > constraints = new ArrayList<>();
        constraints.add( "test" );
        constraint.put( "test", constraints );

        Mockito.when( affiliateEmailException.getMailMessage() ).thenReturn( mailMessage );
        Mockito.when( mailMessage.getContent() ).thenReturn( mimeMultipart );
        Mockito.when( messageSource.getMessage( Mockito.anyString(), Mockito.any(), Mockito.any( Locale.class ) ) )
                .thenReturn( "value" );
        Mockito.when( mimeMultipart.getBodyPart( 1 ) ).thenReturn( bodyPart );
        Mockito.when( bodyPart.getContent() ).thenReturn( "test" );
        affiliateEmailBusinessServiceImpl.handleEmailValidationError( "errorId",
                new AffiliateEmailValidationException( mailMessage, "message", constraint ),
                new NotificationEmailConfig( "", "" ) );

        Mockito.verify( mailService ).send( en );
    }

    /**
     * Test get hubzu subjects.
     */
    @Test
    public void testGetHubzuSubjects() {
        Set< String > hubzuSubjects = affiliateEmailBusinessServiceImpl.getHubzuSubjects();
        Assert.assertNotNull( hubzuSubjects );
    }

    /**
     * Test get owners subjects.
     */
    @Test
    public void testGetOwnersSubjects() {
        Set< String > ownersSubjects = affiliateEmailBusinessServiceImpl.getOwnersSubjects();
        Assert.assertNotNull( ownersSubjects );
    }

    /**
     * Test get failure logger.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetFailureLogger() throws Exception {
        final String messageTxt = initMessageText();
        final MimeMessage mailMessage = initiMessageFolderNotExists( messageTxt );
        Map< String, List< String > > constraint = new HashMap< String, List< String > >();
        List< String > constraints = new ArrayList<>();
        constraints.add( "test" );
        constraint.put( "test", constraints );
        final LeadEmailParsingLog log = affiliateEmailBusinessServiceImpl.getFailureLogger( mailMessage,
                new AffiliateEmailParsingException( mailMessage, messageTxt, new Exception() ) );
        Assert.assertNotNull( log );
        final LeadEmailParsingLog log2 = affiliateEmailBusinessServiceImpl.getFailureLogger( mailMessage,
                new AffiliateEmailValidationException( mailMessage, messageTxt, constraint ) );
        Assert.assertNotNull( log2 );
        final LeadEmailParsingLog log3 = affiliateEmailBusinessServiceImpl.getFailureLogger( mailMessage,
                new Exception( messageTxt ) );
        Assert.assertNotNull( log3 );
    }

    /**
     * Test get success logger.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetSuccessLogger() throws Exception {
        final String messageTxt = initMessageText();
        final MimeMessage mailMessage = initiMessageFolderNotExists( messageTxt );
        final GenericLeadRequest leadRequest = new GenericLeadRequest();
        leadRequest.setEmail( "test@test.com" );
        leadRequest.setSource( "test" );
        final LeadEmailParsingLog log = affiliateEmailBusinessServiceImpl.getSuccessLogger( mailMessage, leadRequest );
        Assert.assertNotNull( log );
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
    private MimeMessage initiMessageFolderNotExists( final String messageTxt ) throws Exception {
        final MimeMessage message = Mockito.mock( MimeMessage.class );
        final InternetAddress fromAddress = new InternetAddress( "test <noReply@hubzu.com>" );
        final Address[] address = { fromAddress };
        Mockito.when( message.getFrom() ).thenReturn( address );
        Mockito.when( message.getAllRecipients() ).thenReturn( address );
        Mockito.when( message.getSubject() ).thenReturn( "test" );
        Mockito.when( message.getReceivedDate() ).thenReturn( new Date() );
        Mockito.when( message.getHeader( "Message-Id" ) ).thenReturn( new String[] { "headerValue1" } );
        message.setText( messageTxt );
        message.setContent( messageTxt, "text/html" );
        message.setHeader( "Message-Id", "header" );
        final Field folderField = AbstractMailReceiver.class.getDeclaredField( "folder" );
        folderField.setAccessible( true );
        final Folder folder = Mockito.mock( IMAPFolder.class );
        Mockito.when( message.getFolder() ).thenReturn( folder );
        final IMAPStore store = Mockito.mock( IMAPStore.class );
        Mockito.when( folder.getStore() ).thenReturn( store );
        Mockito.when( store.getFolder( Mockito.anyString() ) ).thenReturn( folder );
        Mockito.when( folder.exists() ).thenReturn( false );
        return message;
    }

    /**
     * Test scrape owners seller lead email message.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testScrapeOwnersSellerLeadEmailMessage() throws Exception {
        final String messageTxt = initMessageText();
        final MimeMessage message = initiMessageFolderExists( messageTxt );
        final Set< String > subjects = new HashSet<>();
        subjects.add( "xyz" );
        subjects.add( "test" );
        final GenericLeadRequest leadRequest = new GenericLeadRequest();
        leadRequest.setLastName( "leadRequest" );
        leadRequest.setLeadType( RecordType.HUBZU.toString() );
        leadRequest.setSource( "source" );
        leadRequest.setEmail( "test@test.com" );
        leadRequest.setLeadSourceUrl( "http://test.com" );
        leadRequest.setRequestType( LeadRequestType.MAKE_OFFER.toString() );

        Mockito.when( leadBuilderFactory.getEmailLeadBuilder( Mockito.any(), Mockito.any() ) )
                .thenReturn( ownersEmailLeadBuilder );
        Mockito.when( leadBuilderFactory.getSubjects( Mockito.any() ) ).thenReturn( subjects );
        Mockito.when( ownersEmailLeadBuilder.convertTo( message ) ).thenReturn( leadRequest );
        Mockito.when( messageStringBuilder.convertTo( Mockito.any( Message.class ) ) ).thenReturn( messageTxt );
        Mockito.when( message.getHeader( Mockito.anyString() ) ).thenReturn( new String[] { "test" } );
        Mockito.when( message.getFolder().getMessages() ).thenReturn( new Message[0] );

        final GenericLeadRequest genericLeadRequest = affiliateEmailBusinessServiceImpl
                .scrapeOwnersSellerLeadEmailMessage( message );

        Mockito.verify( ownersEmailLeadBuilder ).convertTo( message );
        Assert.assertNotNull( genericLeadRequest );
    }

    /**
     * Test scrape valuations email message.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testScrapeValuationsEmailMessage() throws Exception {
        final String messageTxt = initMessageText();
        final MimeMessage message = initiMessageFolderExists( messageTxt );
        final Set< String > subjects = new HashSet<>();
        subjects.add( "xyz" );
        subjects.add( "test" );
        final GenericLeadRequest leadRequest = new GenericLeadRequest();
        leadRequest.setLastName( "leadRequest" );
        leadRequest.setLeadType( RecordType.HUBZU.toString() );
        leadRequest.setSource( "source" );
        leadRequest.setEmail( "test@test.com" );
        leadRequest.setLeadSourceUrl( "http://test.com" );
        leadRequest.setRequestType( LeadRequestType.MAKE_OFFER.toString() );

        Mockito.when( leadBuilderFactory.getEmailLeadBuilder( Mockito.any(), Mockito.any() ) )
                .thenReturn( ownersEmailLeadBuilder );
        Mockito.when( leadBuilderFactory.getSubjects( Mockito.any() ) ).thenReturn( subjects );
        Mockito.when( ownersEmailLeadBuilder.convertTo( message ) ).thenReturn( leadRequest );
        Mockito.when( messageStringBuilder.convertTo( Mockito.any( Message.class ) ) ).thenReturn( messageTxt );
        Mockito.when( message.getHeader( Mockito.anyString() ) ).thenReturn( new String[] { "test" } );
        Mockito.when( message.getFolder().getMessages() ).thenReturn( new Message[0] );

        final GenericLeadRequest genericLeadRequest = affiliateEmailBusinessServiceImpl
                .scrapeValuationsEmailMessage( message );

        Mockito.verify( ownersEmailLeadBuilder ).convertTo( message );
        Assert.assertNotNull( genericLeadRequest );
    }

    /**
     * Initi message folder exists.
     *
     * @param messageTxt
     *            the message txt
     * @return the mime message
     * @throws Exception
     *             the exception
     */
    private MimeMessage initiMessageFolderExists( final String messageTxt ) throws Exception {
        final MimeMessage message = Mockito.mock( MimeMessage.class );
        final InternetAddress fromAddress = new InternetAddress( "noReply@hubzu.com" );
        final Address[] address = { fromAddress };
        Mockito.when( message.getFrom() ).thenReturn( address );
        Mockito.when( message.getSubject() ).thenReturn( "test" );
        message.setText( messageTxt );
        message.setContent( messageTxt, "text/html" );
        final Field folderField = AbstractMailReceiver.class.getDeclaredField( "folder" );
        folderField.setAccessible( true );
        message.setHeader( "Message-Id", "header" );
        final Folder folder = Mockito.mock( IMAPFolder.class );
        Mockito.when( message.getFolder() ).thenReturn( folder );
        final IMAPStore store = Mockito.mock( IMAPStore.class );
        Mockito.when( folder.getStore() ).thenReturn( store );
        Mockito.when( store.getFolder( Mockito.anyString() ) ).thenReturn( folder );
        Mockito.when( folder.exists() ).thenReturn( true );
        return message;
    }

    /**
     * Inits the message text.
     *
     * @return the string
     */
    private String initMessageText() {
        final String messageTxt = "2016-04-13 Bruce is interested in your listing at xyz xyz and sent you the following message: "
                + "Buyer's Message I&#039;d like REALHome Services and Solutions, Inc. to refer me to a local agent to help me purchase the property "
                + "at xyz xyz. Thank you. Buyer's Details Name: : Bruce Robison Buyer Contact Number: : 45454545454 Buyer Email: :"
                + " test@test.com Property Details Property ID : 1212121 Property Address : xyz xyz Please  ";
        return messageTxt;
    }
}
