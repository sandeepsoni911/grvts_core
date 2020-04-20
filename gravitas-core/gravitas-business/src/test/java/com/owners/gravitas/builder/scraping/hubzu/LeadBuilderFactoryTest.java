package com.owners.gravitas.builder.scraping.hubzu;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.integration.mail.AbstractMailReceiver;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.scraping.EmailLeadBuilder;
import com.owners.gravitas.business.builder.scraping.LeadBuilderFactory;
import com.owners.gravitas.business.builder.scraping.hubzu.HomeBidzEmailLeadBuilder;
import com.owners.gravitas.business.builder.scraping.hubzu.HomesEmailLeadBuilder;
import com.owners.gravitas.business.builder.scraping.hubzu.HubzuEmailLeadBuilder;
import com.owners.gravitas.business.builder.scraping.hubzu.RealtorEmailLeadBuilder;
import com.owners.gravitas.business.builder.scraping.hubzu.TruliaEmailLeadBuilder;
import com.owners.gravitas.business.builder.scraping.hubzu.UltraForcesEmailLeadBuilder;
import com.owners.gravitas.business.builder.scraping.hubzu.ZillowEmailLeadBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.LeadSubject;
import com.owners.gravitas.exception.ApplicationException;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

/**
 * The Class LeadBuilderFactoryTest.
 *
 * @author vishwanathm
 */
public class LeadBuilderFactoryTest extends AbstractBaseMockitoTest {

    /** The lead builder factory. */
    @InjectMocks
    private LeadBuilderFactory leadBuilderFactory;

    /** The lead subject. */
    @Mock
    private LeadSubject leadSubject;

    /** The hubzu email lead builder. */
    @Mock
    private HubzuEmailLeadBuilder hubzuEmailLeadBuilder;

    /** The trulia email builder. */
    @Mock
    private TruliaEmailLeadBuilder truliaEmailLeadBuilder;

    /** The homes email lead builder. */
    @Mock
    private HomesEmailLeadBuilder homesEmailLeadBuilder;

    /** The realtor email lead builder. */
    @Mock
    private RealtorEmailLeadBuilder realtorEmailLeadBuilder;

    /** The zillow email lead builder. */
    @Mock
    private ZillowEmailLeadBuilder zillowEmailLeadBuilder;

    /** The home bidz email lead builder. */
    @Mock
    private HomeBidzEmailLeadBuilder homeBidzEmailLeadBuilder;

    /** The ultra forces email lead builder. */
    @Mock
    private UltraForcesEmailLeadBuilder ultraForcesEmailLeadBuilder;

    /**
     * Test get email lead builder.
     *
     * @throws Exception
     */
    @Test
    public void testGetEmailLeadBuilder() throws Exception {
        Mockito.when( leadSubject.getHubzuSubject() ).thenReturn( "test" );
        Mockito.when( leadSubject.getHomesSubject() ).thenReturn( "test1" );
        Mockito.when( leadSubject.getTruliaSubject() ).thenReturn( "test2" );
        Mockito.when( leadSubject.getHomeBidzSubject() ).thenReturn( "test3" );
        Mockito.when( leadSubject.getZillowSubject() ).thenReturn( "test4" );
        Mockito.when( leadSubject.getRealtorSubject() ).thenReturn( "test5" );
        Mockito.when( leadSubject.getUltraforceSubject() ).thenReturn( "test6" );

        Mockito.when( leadSubject.getOwnersRealtorSubject() ).thenReturn( "test7" );
        Mockito.when( leadSubject.getOwnersSubject() ).thenReturn( "test8" );
        Mockito.when( leadSubject.getOwnersSellerLeadSubject() ).thenReturn( "test9" );

        ReflectionTestUtils.invokeMethod( leadBuilderFactory, "initialize" );
        final EmailLeadBuilder builder = leadBuilderFactory.getEmailLeadBuilder( "Hubzu",
                initiMessageFolderExists( "test" ) );
        Assert.assertNotNull( builder );
    }

    /**
     * Test get email lead builder.
     *
     * @throws Exception
     */
    @Test( dependsOnMethods = "testGetEmailLeadBuilder" )
    public void testGetEmailLeadBuilderForEmptySet() throws Exception {
        final Map< String, Map< String, EmailLeadBuilder > > value = new HashMap<>();
        value.put( "Hubzu", new HashMap<>() );
        ReflectionTestUtils.setField( leadBuilderFactory, "buildersSource", value );
        final EmailLeadBuilder builder = leadBuilderFactory.getEmailLeadBuilder( "Hubzu",
                initiMessageFolderExists( "test" ) );
        Assert.assertNull( builder );
    }

    /**
     * Test get subjects.
     */
    @Test
    public void testGetSubjects() {
        ReflectionTestUtils.invokeMethod( leadBuilderFactory, "initialize" );
        final Set< String > subjects = leadBuilderFactory.getSubjects( "Hubzu" );
        Assert.assertNotNull( subjects );
    }

    /**
     * Test for exception.
     *
     * @throws Exception
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testForException() throws Exception {
        Mockito.when( leadSubject.getHubzuSubject() ).thenReturn( "test" );
        Mockito.when( leadSubject.getHomesSubject() ).thenReturn( "test1" );
        Mockito.when( leadSubject.getTruliaSubject() ).thenReturn( "test2" );
        Mockito.when( leadSubject.getHomeBidzSubject() ).thenReturn( "test3" );
        Mockito.when( leadSubject.getZillowSubject() ).thenReturn( "test4" );
        Mockito.when( leadSubject.getRealtorSubject() ).thenReturn( "test5" );
        Mockito.when( leadSubject.getUltraforceSubject() ).thenReturn( "test6" );

        ReflectionTestUtils.invokeMethod( leadBuilderFactory, "initialize" );
        final MimeMessage message = Mockito.mock( MimeMessage.class );
        Mockito.when( message.getSubject() ).thenThrow( new MessagingException() );
        leadBuilderFactory.getEmailLeadBuilder( "Hubzu", message );
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

        final Field folderField = AbstractMailReceiver.class.getDeclaredField( "folder" );
        folderField.setAccessible( true );
        final Folder folder = Mockito.mock( IMAPFolder.class );
        Mockito.when( message.getFolder() ).thenReturn( folder );
        final IMAPStore store = Mockito.mock( IMAPStore.class );
        Mockito.when( folder.getStore() ).thenReturn( store );
        Mockito.when( store.getFolder( Mockito.anyString() ) ).thenReturn( folder );
        Mockito.when( folder.exists() ).thenReturn( true );
        return message;
    }
}
