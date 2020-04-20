package com.owners.gravitas.business.builder.scraping;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.exception.ApplicationException;

/**
 * The Class MessageStringBuilder, scrap the message by removing the HTML
 * elements and blank spaces from text message and return only text from email.
 *
 * @author vishwanathm
 */
@Component
public class MessageStringBuilder extends AbstractBuilder< Message, String > {

    /** The Constant MIME_TEXT_PLAIN. */
    private static final String MIME_TEXT_PLAIN = "text/plain";

    /** The Constant MIME_TEXT_HTML. */
    private static final String MIME_TEXT_HTML = "text/html";

    /** The Constant MIME_MULTIPART_ALTERNATIVE. */
    private static final String MIME_MULTIPART_ALTERNATIVE = "multipart/alternative";

    /** The Constant MULTIPART_MIME_TYPE. */
    private static final String MIME_MULTIPART_ALL = "multipart/*";

    /** The Constant TEXT_MIME_TYPE. */
    private static final String MIME_TEXT_ALL = "text/*";

    /**
     * Convert the {@link Message} to plain text message string by removing the
     * HTML tags and blank spaces from the text.
     *
     * @param source
     *            the source
     * @param destination
     *            the destination
     * @return the string
     */
    @Override
    public String convertTo( Message source, String destination ) {
        String messageText = destination;
        if (source != null) {
            try {
                messageText = getMessageText( source );
            } catch ( Exception e ) {
                throw new ApplicationException( "Problem in parsing the email ", e );
            }
        }
        return messageText;
    }

    /**
     * Gets the message text by iterating to all mime types and return the text
     * message.
     *
     * @param part
     *            the part object
     * @return the message text
     * @throws MessagingException
     *             the messaging exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private String getMessageText( Part part ) throws MessagingException, IOException {
        String messageText = StringUtils.EMPTY;
        Multipart mp = null;
        if (part.isMimeType( MIME_TEXT_ALL )) {
            return ( String ) part.getContent();
        }
        if (part.isMimeType( MIME_MULTIPART_ALTERNATIVE )) {
            mp = ( Multipart ) part.getContent();
            for ( int i = 0; i < mp.getCount(); i++ ) {
                Part bp = mp.getBodyPart( i );
                if (bp.isMimeType( MIME_TEXT_PLAIN )) {
                    if (StringUtils.isBlank( messageText ))
                        messageText = getMessageText( bp );
                    continue;
                } else if (bp.isMimeType( MIME_TEXT_HTML )) {
                    messageText = getMessageText( bp );
                    if (messageText != null)
                        return messageText;
                } else {
                    return getMessageText( bp );
                }
            }
            return messageText;
        } else if (part.isMimeType( MIME_MULTIPART_ALL )) {
            mp = ( Multipart ) part.getContent();
            for ( int i = 0; i < mp.getCount(); i++ ) {
                messageText = getMessageText( mp.getBodyPart( i ) );
                if (messageText != null)
                    return messageText;
            }
        }
        return messageText;
    }

    /**
     * Unsupported method operation.
     *
     * @param source
     *            the source
     * @param destination
     *            the destination
     * @return the message
     */
    @Override
    public Message convertFrom( String source, Message destination ) {
        throw new UnsupportedOperationException();
    }
}
