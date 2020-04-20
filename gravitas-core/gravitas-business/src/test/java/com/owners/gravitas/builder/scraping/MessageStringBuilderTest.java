package com.owners.gravitas.builder.scraping;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.scraping.MessageStringBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.exception.ApplicationException;

/**
 * The Class MessageStringBuilderTest.
 *
 * @author vishwanathm
 */
public class MessageStringBuilderTest extends AbstractBaseMockitoTest {

    /** The message string builder. */
    @InjectMocks
    private MessageStringBuilder messageStringBuilder;

    /**
     * Testconvert to for text all.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testconvertToForTextAll() throws Exception {
        final MimeMessage message = Mockito.mock( MimeMessage.class );
        Mockito.when( message.getContent() ).thenReturn( "plain text" );
        Mockito.when( message.isMimeType( "text/*" ) ).thenReturn( true );
        final String text = messageStringBuilder.convertTo( message );
        Assert.assertEquals( text, "plain text" );
    }

    /**
     * Testconvert to for text plain.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testconvertToForTextPlain() throws Exception {
        final MimeMessage message = Mockito.mock( MimeMessage.class );
        final Multipart multipart = Mockito.mock( Multipart.class );
        final BodyPart bodyPart = Mockito.mock( BodyPart.class );
        Mockito.when( bodyPart.getContent() ).thenReturn( "message text" );
        Mockito.when( bodyPart.isMimeType( "text/plain" ) ).thenReturn( true );
        Mockito.when( bodyPart.isMimeType( "text/*" ) ).thenReturn( true );
        Mockito.when( multipart.getCount() ).thenReturn( 1 );
        Mockito.when( multipart.getBodyPart( Mockito.anyInt() ) ).thenReturn( bodyPart );
        Mockito.when( message.getContent() ).thenReturn( multipart );
        Mockito.when( message.isMimeType( "multipart/alternative" ) ).thenReturn( true );
        final String text = messageStringBuilder.convertTo( message );
        Assert.assertEquals( text, "message text" );
    }

    /**
     * Testconvert to for text html.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testconvertToForTextHtml() throws Exception {
        final MimeMessage message = Mockito.mock( MimeMessage.class );
        final Multipart multipart = Mockito.mock( Multipart.class );
        final BodyPart bodyPart = Mockito.mock( BodyPart.class );
        Mockito.when( bodyPart.getContent() ).thenReturn( "message text" );
        Mockito.when( bodyPart.isMimeType( "text/html" ) ).thenReturn( true );
        Mockito.when( bodyPart.isMimeType( "text/*" ) ).thenReturn( true );
        Mockito.when( multipart.getCount() ).thenReturn( 1 );
        Mockito.when( multipart.getBodyPart( Mockito.anyInt() ) ).thenReturn( bodyPart );
        Mockito.when( message.getContent() ).thenReturn( multipart );
        Mockito.when( message.isMimeType( "multipart/alternative" ) ).thenReturn( true );
        final String text = messageStringBuilder.convertTo( message );
        Assert.assertEquals( text, "message text" );
    }

    /**
     * Testconvert to for multi part all.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testconvertToForMultiPartAll() throws Exception {
        final MimeMessage message = Mockito.mock( MimeMessage.class );
        final Multipart multipart = Mockito.mock( Multipart.class );
        final BodyPart bodyPart = Mockito.mock( BodyPart.class );
        Mockito.when( bodyPart.getContent() ).thenReturn( "message text" );
        Mockito.when( bodyPart.isMimeType( "text/plain" ) ).thenReturn( true );
        Mockito.when( bodyPart.isMimeType( "text/*" ) ).thenReturn( true );
        Mockito.when( multipart.getCount() ).thenReturn( 1 );
        Mockito.when( multipart.getBodyPart( Mockito.anyInt() ) ).thenReturn( bodyPart );
        Mockito.when( message.getContent() ).thenReturn( multipart );
        Mockito.when( message.isMimeType( "multipart/*" ) ).thenReturn( true );
        final String text = messageStringBuilder.convertTo( message );
        Assert.assertEquals( text, "message text" );
    }

    /**
     * Testconvert to exception.
     *
     * @throws Exception
     *             the exception
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testconvertToException() throws Exception {
        final MimeMessage message = Mockito.mock( MimeMessage.class );
        Mockito.when( message.isMimeType( "text/*" ) ).thenReturn( true );
        Mockito.when( message.getContent() ).thenThrow( new MessagingException() );
        messageStringBuilder.convertTo( message );
    }

    /**
     * Test conver to for null source.
     */
    @Test
    public void testConverToForNullSource() {
        String text = messageStringBuilder.convertTo( null );
        Assert.assertNull( text );

        text = messageStringBuilder.convertTo( null, "" );
        Assert.assertEquals( text, StringUtils.EMPTY );

        final MimeMessage message = Mockito.mock( MimeMessage.class );
        text = messageStringBuilder.convertTo( message, null );
        Assert.assertEquals( text, StringUtils.EMPTY );

        text = messageStringBuilder.convertTo( message, "test" );
        Assert.assertEquals( text, StringUtils.EMPTY );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        messageStringBuilder.convertFrom( new String() );
    }

}
