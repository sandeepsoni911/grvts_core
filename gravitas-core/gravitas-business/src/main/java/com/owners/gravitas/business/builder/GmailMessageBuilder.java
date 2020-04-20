package com.owners.gravitas.business.builder;

import static javax.mail.Message.RecipientType.BCC;
import static javax.mail.Message.RecipientType.CC;
import static javax.mail.Message.RecipientType.TO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.URLDataSource;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.api.client.util.Base64;
import com.google.api.services.gmail.model.Message;
import com.owners.gravitas.dto.request.EmailRequest;
import com.owners.gravitas.exception.ApplicationException;

/**
 * The Class GmailMessageBuilderTest.
 *
 * @author vishwanathm
 */
@Component( "gmailMessageBuilder" )
public class GmailMessageBuilder extends AbstractBuilder< EmailRequest, Message > {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( GmailMessageBuilder.class );

    /** The Constant TEXT_HTML. */
    private static final String TEXT_HTML = "text/html";
        
    /** The Constant TEXT_UTF_ENCODING. */
    private static final String UTF_8 = "UTF-8";

    /** The Constant TEXT_UTF_ENCODING. */
    private static final String TEXT_UTF_ENCODING = TEXT_HTML+"; charset=" + UTF_8;
    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public Message convertTo( final EmailRequest source, final Message destination ) {
        Message message = destination;
        if (null != source) {
            if (null == message) {
                message = new Message();
            }
            try {
                final MimeMessage mimeMessage = createEmail( source );
                final String base64RawEmailContent = createMessageWithEmail( mimeMessage );
                message.setRaw( base64RawEmailContent );
            } catch ( MessagingException | IOException me ) {
                LOGGER.error( "Problem in converting email request to message " + me.getLocalizedMessage(), me );
                throw new ApplicationException(
                        "Problem in converting email request to message " + me.getLocalizedMessage(), me );
            }
        }
        return message;
    }

    /**
     * Creates the email.
     *
     * @param source
     *            the source
     * @return the mime message
     * @throws MessagingException
     *             the messaging exception
     * @throws URISyntaxException
     *             the URI syntax exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public MimeMessage createEmail( EmailRequest source ) throws MessagingException, IOException {
        final MimeMessage email = new MimeMessage( Session.getDefaultInstance( new Properties(), null ) );

        email.addRecipients( TO, getAddressForList( source.getTo() ) );
        if (CollectionUtils.isNotEmpty( source.getCc() )) {
            email.addRecipients( CC, getAddressForList( source.getCc() ) );
        }
        if (CollectionUtils.isNotEmpty( source.getBcc() )) {
            email.addRecipients( BCC, getAddressForList( source.getBcc() ) );
        }
        email.setSubject( source.getSubject() );

        final Multipart multipart = new MimeMultipart();
        addBodyPart( source.getBodyText(), multipart );
        addAttachments( source.getAttachmentUrls(), multipart );

        email.setContent( multipart );
        return email;
    }

    /**
     * Adds the body part.
     *
     * @param bodyText
     *            the body text
     * @param multipart
     *            the multipart
     * @throws MessagingException
     *             the messaging exception
     */
    protected void addBodyPart( final String bodyText, final Multipart multipart ) throws MessagingException {
        final MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent( bodyText, TEXT_UTF_ENCODING );
        multipart.addBodyPart( mimeBodyPart );
    }

    /**
     * Adds the attachments.
     *
     * @param attachmentUrls
     *            the attachment urls
     * @param multipart
     *            the multipart
     * @throws MessagingException
     *             the messaging exception
     * @throws MalformedURLException
     *             the malformed url exception
     */
    private void addAttachments( final List< String > attachmentUrls, final Multipart multipart )
            throws MessagingException, MalformedURLException {
        if (CollectionUtils.isNotEmpty( attachmentUrls )) {
            for ( String attachment : attachmentUrls ) {
                final MimeBodyPart attachementPart = new MimeBodyPart();
                final DataSource daraSource = new URLDataSource( new URL( attachment ) );
                attachementPart.setDataHandler( new DataHandler( daraSource ) );
                multipart.addBodyPart( attachementPart );
            }
        }
    }

    /**
     * Gets the address for list.
     *
     * @param emailList
     *            the email list
     * @return the address for list
     * @throws AddressException
     *             the address exception
     */
    private Address[] getAddressForList( final List< String > emailList ) throws AddressException {
        final List< Address > addressList = new ArrayList<>();
        final Address[] addresses = new Address[addressList.size()];
        for ( String email : emailList ) {
            addressList.add( new InternetAddress( email ) );
        }
        return addressList.toArray( addresses );
    }

    /**
     * Create a message from an email.
     *
     * @param emailContent
     *            Email to be set to raw of message
     * @return a message containing a base64url encoded email
     * @throws MessagingException
     *             the messaging exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public String createMessageWithEmail( final MimeMessage emailContent ) throws MessagingException, IOException {
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo( buffer );
        return Base64.encodeBase64URLSafeString( buffer.toByteArray() );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public EmailRequest convertFrom( final Message source, final EmailRequest destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }

}
