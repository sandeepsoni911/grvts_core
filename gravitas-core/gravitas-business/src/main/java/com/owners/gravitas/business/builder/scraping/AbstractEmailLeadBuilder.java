package com.owners.gravitas.business.builder.scraping;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.exception.AffiliateEmailParsingException;
import com.owners.gravitas.exception.ApplicationException;

/**
 * The Class MesageServiceActivator.
 *
 * @author vishwanathm
 */
public abstract class AbstractEmailLeadBuilder implements EmailLeadBuilder {

    /** The Constant REGEX_NBSP. */
    private static final String REGEX_NBSP = "\u00A0";

    /** The message string builder. */
    @Autowired
    private MessageStringBuilder messageStringBuilder;

    /**
     * This method converts the <code>A</code> domain object to <code>B</code>
     * dto object.
     *
     * @param source
     *            is the domain object.
     * @return dto object.
     */
    @Override
    public abstract GenericLeadRequest convertTo( final String source );

    /**
     * This method converts domain object to dto object.
     *
     * @param sourceObject
     *            is the domain object to be converted to dto.
     * @return B - destination object
     */
    @Override
    public GenericLeadRequest convertTo( final Message message ) {
        final String messageTextPart = messageStringBuilder.convertTo( message );
        try {
            return convertTo( messageTextPart );
        } catch ( Exception exp ) {
            throw new AffiliateEmailParsingException( message,
                    "Affiliate email parsing error : " + exp.getLocalizedMessage(), exp );
        }
    }

    /**
     * Gets the email address, if email string contains character '<', otherwise
     * return whole email address.
     *
     * @param message
     *            the message
     * @return the domain
     */
    protected String getEmailAddress( final Message message ) {
        String from;
        try {
            from = message.getFrom()[0].toString();
        } catch ( MessagingException e ) {
            throw new ApplicationException( e.getLocalizedMessage(), e );
        }
        return from.contains( "<" ) ? from.substring( from.indexOf( "<" ) + 1, from.indexOf( ">" ) ) : from;
    }

    /**
     * Gets the message string html.
     *
     * @param source
     *            the source
     * @return the message string html
     */
    protected String getMessageStringHTML( final String source ) {
        return source.replaceAll( Constants.REG_EXP_BLANK_SPACES, Constants.BLANK_SPACE )
                .replaceAll( REGEX_NBSP, StringUtils.EMPTY ).trim();
    }

    /**
     * Gets the message string plain.
     *
     * @param source
     *            the source
     * @return the message string plain
     */
    protected String getMessageStringPlain( final String source ) {
        return getMessageStringHTML( StringEscapeUtils
                .unescapeHtml4( source.replaceAll( Constants.REG_EXP_HTML_TAGS, StringUtils.EMPTY ) ) );
    }

    /**
     * Gets the lead parameter value.
     *
     * @param messageText
     *            the message text
     * @param param
     *            the param
     * @param nextParam
     *            the next param
     * @return the lead parameter value
     */
    protected String getLeadParameterValue( final String messageText, final String param, final String nextParam ) {
        int paramIndex = messageText.indexOf( param );
        int nextParamIndex = messageText.indexOf( nextParam );
        if (paramIndex == -1 || nextParamIndex == -1) {
            throw new AffiliateEmailParsingException( null,
                    ( paramIndex == -1 ? param : nextParam ) + " value not found in template",
                    new Exception( "Invalid template" ) );
        }
        return messageText.substring( paramIndex + param.length(), nextParamIndex ).trim();
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     * @param leadRequest
     *            the lead request
     */
    protected void setName( final String name, final GenericLeadRequest leadRequest ) {
        final String[] names = name.split( Constants.BLANK_SPACE );
        if (names.length > 1) {
            leadRequest.setFirstName( names[0] );
            leadRequest.setLastName( names[1] );
        } else {
            leadRequest.setLastName( name );
        }
    }

    /**
     * Append if not null.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     * @return the string
     */
    protected String appendParam( final String key, final String value ) {
        String paramValue = "NA";
        if (StringUtils.isNotBlank( value )) {
            paramValue = value;
        }
        return key + " - " + paramValue;
    }
    
    /**
	 * Gets the email body from mail.
	 *
	 * @param message
	 *            the message
	 * @return the email body
	 */
    protected String getEmailBody(final Message message) {
    	return messageStringBuilder.convertTo( message );
    }
}
