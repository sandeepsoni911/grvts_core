package com.owners.gravitas.business.impl;

import static com.owners.gravitas.constants.Constants.AFFILIATE_EMAIL_PARSING_NOTIFICATION_MESSAGE_TYPE_NAME;
import static com.owners.gravitas.constants.Constants.AFFILIATE_EMAIL_VALIDATION_NOTIFICATION_MESSAGE_TYPE_NAME;
import static com.owners.gravitas.constants.Constants.BLANK_SPACE;
import static com.owners.gravitas.constants.Constants.COLON;
import static com.owners.gravitas.constants.Constants.GRAVITAS;
import static com.owners.gravitas.constants.Constants.HYPHEN;
import static com.owners.gravitas.constants.Constants.PERIOD;
import static com.owners.gravitas.enums.RecordType.BUYER;
import static com.owners.gravitas.enums.RecordType.HUBZU;
import static com.owners.gravitas.enums.RecordType.SELLER;
import static java.lang.Boolean.FALSE;
import static javax.mail.Folder.READ_WRITE;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.mail.Address;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.annotation.EmailParsingLog;
import com.owners.gravitas.business.AffiliateEmailBusinessService;
import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.business.builder.AffiliateErrorNotificationBuilder;
import com.owners.gravitas.business.builder.scraping.EmailLeadBuilder;
import com.owners.gravitas.business.builder.scraping.LeadBuilderFactory;
import com.owners.gravitas.config.NotificationEmailConfig;
import com.owners.gravitas.domain.entity.LeadEmailParsingLog;
import com.owners.gravitas.dto.AffiliateEmailAttribute;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.enums.LeadRequestType;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.exception.AffiliateEmailException;
import com.owners.gravitas.exception.AffiliateEmailParsingException;
import com.owners.gravitas.exception.AffiliateEmailValidationException;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.validator.LeadValidator;

/**
 * The Class AffiliateEmailBusinessServiceImpl, is to scrape the email from
 * affiliate leads and create the lead in to CRM, if there is failure in parsing
 * or validating the email error message email is triggered, and also that email
 * is marked not for lead not created.
 *
 * @author vishwanathm
 */
@Service
public class AffiliateEmailBusinessServiceImpl implements AffiliateEmailBusinessService {

    /** The Constant SOURCE_HUBZU. */
    private static final String SOURCE_HUBZU = "Hubzu";

    /** The Constant SOURCE_OWNERS. */
    private static final String SOURCE_OWNERS = "Owners";

    /** The Constant HTML_CONTENT_INDEX. */
    private static final int HTML_CONTENT_INDEX = 1;

    /** The Constant HUBZU_LEAD_SOURCE_URL. */
    private static final String HUBZU_LEAD_SOURCE_URL = "http://hubzu.com";

    /** The Constant BR_TAG. */
    private static final String BR_TAG = "<br>";

    /** The Constant COULD_NOT_CAPTURE. */
    private static final String COULD_NOT_CAPTURE = "COULD_NOT_CAPTURE";

    /** The Constant FAILURE. */
    private static final String FAILURE = "FAILURE";

    /** The Constant SUCCESS. */
    private static final String SUCCESS = "SUCCESS";

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AffiliateEmailBusinessServiceImpl.class );

    /** The label applied to a message if the lead is generated successfully. */
    @Value( "${affiliate.mail.lead.success.label}" )
    private String leadGeneratedLabel;

    /** The lead builder factory. */
    @Autowired
    private LeadBuilderFactory leadBuilderFactory;

    /** The mail service. */
    @Autowired
    private MailService mailService;

    /** The affiliate error notification builder. */
    @Autowired
    private AffiliateErrorNotificationBuilder affiliateErrorNotificationBuilder;

    /** instance of {@link LeadBusinessService}. */
    @Autowired
    private LeadBusinessService leadBusinessService;

    /** The message source. */
    @Autowired
    private MessageSource messageSource;

    /** The lead validator. */
    @Autowired
    private LeadValidator leadValidator;

    /**
     * Scrape hubzu email message by reading the plain text content from email
     * message and create lead in to CRM.
     *
     * If scraping is success the message is attached with provided label, on
     * failure for parsing and validation the email notification is triggered.
     *
     * @param message
     *            the message
     * @return the lead response
     */
    @Override
    @EmailParsingLog
    public GenericLeadRequest scrapeHubzuEmailMessage( final Message message ) {
        return createLead( message, HUBZU, leadBuilderFactory.getEmailLeadBuilder( SOURCE_HUBZU, message ) );
    }

    /**
     * Scrape owners email message by reading the plain text content from email
     * message and create lead in to CRM.
     *
     * If scraping is success the message is attached with provided label, on
     * failure for parsing and validation the email notification is triggered.
     *
     * @param message
     *            the message
     * @return the lead response
     */
    @Override
    @EmailParsingLog
    public GenericLeadRequest scrapeOwnersEmailMessage( final Message message ) {
        return createLead( message, BUYER,
                leadBuilderFactory.getEmailLeadBuilder( SOURCE_OWNERS, message ) );
    }

    /**
     * Scrape owners seller lead email message by reading the plain text content
     * from email message and create seller lead in to CRM.
     *
     * If scraping is success the message is attached with provided label, on
     * failure for parsing and validation the email notification is triggered.
     *
     * @param message
     *            the message
     * @return the lead response
     */
    @Override
    @EmailParsingLog
    public GenericLeadRequest scrapeOwnersSellerLeadEmailMessage( final Message message ) {
        return createLead( message, SELLER,
                leadBuilderFactory.getEmailLeadBuilder( SOURCE_OWNERS, message ) );
    }

    /**
     * Scrape valuations email message by reading the plain text content from
     * email message and create lead in to CRM.
     *
     * If scraping is success the message is attached with provided label, on
     * failure for parsing and validation the email notification is triggered.
     *
     * @param message
     *            the message
     * @return the lead response
     */
    @Override
    @EmailParsingLog
    public GenericLeadRequest scrapeValuationsEmailMessage( final Message message ) {
        return createLead( message, RecordType.MLS, leadBuilderFactory.getEmailLeadBuilder( SOURCE_OWNERS, message ) );
    }

    /**
     * Creates the lead.
     *
     * @param message
     *            the message
     * @param recordType
     *            the record type
     * @param emailLeadBuilder
     *            the email lead builder
     * @return the generic lead request
     */
    private GenericLeadRequest createLead( final Message message, final RecordType recordType,
            final EmailLeadBuilder emailLeadBuilder ) {
        final String emailAddress = getEmailAddress( message );
        GenericLeadRequest leadRequest = null;
        if (emailLeadBuilder != null) {
            leadRequest = emailLeadBuilder.convertTo( message );
            setBusinessParams( emailAddress, leadRequest, recordType );
            leadValidator.validateLeadRequest( leadRequest, message );
            leadBusinessService.createLead( leadRequest, FALSE, null );
            addLabelToMessage( leadGeneratedLabel, message );
            LOGGER.info( "Lead " + recordType.getType() + " creation of affiliate message for <" + emailAddress
                    + "> completed." );
        }
        return leadRequest;
    }

    /**
     * Sets the other details.
     *
     * @param emailAddress
     *            the email address
     * @param leadRequest
     *            the lead request
     * @param recordType
     *            the record type
     */
    private void setBusinessParams( final String emailAddress, final GenericLeadRequest leadRequest,
            final RecordType recordType ) {
        String leadSourcetr = leadRequest.getSource();
        if (RecordType.HUBZU.equals( recordType )) {
            leadSourcetr = emailAddress;
            leadRequest.setLeadSourceUrl( HUBZU_LEAD_SOURCE_URL );
        }
        leadRequest.setSource( getLeadSource( leadSourcetr, recordType ) );
        leadRequest.setLeadType( recordType.toString() );
        leadRequest.setCompany( emailAddress );
        leadRequest.setRequestType( LeadRequestType.REQUEST_INFORMATION.toString() );
    }

    /**
     * Add label to message for given label name(create if label/folder not
     * exists).
     *
     * @param labelName
     *            the folder name
     * @param message
     *            the message
     */
    @Override
    public void addLabelToMessage( final String labelName, final Message message ) {
        try {
            final Folder inbox = message.getFolder();
            if (!inbox.isOpen()) {
                inbox.open( READ_WRITE );
            }
            // Mark msg for deletion from the source folder fo that it resides
            // only in destination folder
            final String messageId = message.getHeader( "Message-Id" )[0];
            final Message[] messages = inbox.getMessages();
            final FetchProfile contentsProfile = new FetchProfile();
            contentsProfile.add( FetchProfile.Item.ENVELOPE );
            contentsProfile.add( FetchProfile.Item.CONTENT_INFO );
            contentsProfile.add( FetchProfile.Item.FLAGS );
            inbox.fetch( messages, contentsProfile );
            // find this message and mark for deletion
            for ( int i = 0; i < messages.length; i++ ) {
                if (( ( MimeMessage ) messages[i] ).getMessageID().equals( messageId )) {
                    messages[i].setFlag( Flags.Flag.DELETED, true );
                    break;
                }
            }
            final Folder folder = inbox.getStore().getFolder( labelName );
            if (!folder.exists()) {
                folder.create( Folder.HOLDS_MESSAGES );
            }
            folder.open( READ_WRITE );
            folder.appendMessages( new Message[] { message } );

            inbox.expunge();
            inbox.close( Boolean.TRUE );
            folder.close( Boolean.FALSE );
            LOGGER.info( "Label '" + labelName + "' is assigned to message : " + message.getSubject() );
        } catch ( MessagingException e ) {
            throw new ApplicationException( e.getLocalizedMessage(), e );
        }
    }

    /**
     * Handle email parsing error.
     *
     * @param errorId
     *            the error id
     * @param affEmailParsingExp
     *            the aff email parsing exp
     * @param emailConfig
     *            the email config
     */
    @Override
    public void handleEmailParsingError( final String errorId, final AffiliateEmailParsingException affEmailParsingExp,
            final NotificationEmailConfig emailConfig ) {
        final String errorSrc = getEmailAddress( affEmailParsingExp.getMailMessage() );
        final String errorMsg = getErrorMessage( errorId, affEmailParsingExp );
        sendEmail( errorMsg, errorSrc, AFFILIATE_EMAIL_PARSING_NOTIFICATION_MESSAGE_TYPE_NAME, emailConfig );
    }

    /**
     * Handle email validation error.
     *
     * @param errorId
     *            the error id
     * @param validationException
     *            the validation exception
     * @param emailConfig
     *            the email config
     */
    @Override
    public void handleEmailValidationError( final String errorId,
            final AffiliateEmailValidationException validationException, final NotificationEmailConfig emailConfig ) {
        final StrBuilder errorMsg = new StrBuilder();
        final String errorSrc = getEmailAddress( validationException.getMailMessage() );
        errorMsg.append( getErrorMessage( errorId, validationException ) );
        sendEmail( errorMsg.toString(), errorSrc, AFFILIATE_EMAIL_VALIDATION_NOTIFICATION_MESSAGE_TYPE_NAME,
                emailConfig );
    }

    /**
     * Gets the Hubzu subjects.
     *
     * @return the Hubzu subjects
     */
    @Override
    public Set< String > getHubzuSubjects() {
        return leadBuilderFactory.getSubjects( SOURCE_HUBZU );
    }

    /**
     * Gets the Owners subjects.
     *
     * @return the Owners subjects
     */
    @Override
    public Set< String > getOwnersSubjects() {
        return leadBuilderFactory.getSubjects( SOURCE_OWNERS );
    }

    /**
     * Send email.
     *
     * @param errorMsg
     *            the error msg
     * @param errorSource
     *            the error source
     * @param templateCode
     *            the template code
     * @param emailConfig
     *            the email config
     */
    private void sendEmail( final String errorMsg, final String errorSource, final String templateCode,
            final NotificationEmailConfig emailConfig ) {
        final AffiliateEmailAttribute emailAttribute = buildAffiliateEmailAttributes( errorMsg.toString(), errorSource,
                templateCode, emailConfig );
        final EmailNotification emailNotification = affiliateErrorNotificationBuilder.convertTo( emailAttribute );
        mailService.send( emailNotification );
    }

    /**
     * Builds the affiliate email attributes.
     *
     * @param errorLog
     *            the error log
     * @param errorSource
     *            the error source
     * @param typeName
     *            the type name
     * @param emailConfig
     *            the email config
     * @return the affiliate email attribute
     */
    private AffiliateEmailAttribute buildAffiliateEmailAttributes( final String errorLog, final String errorSource,
            final String typeName, final NotificationEmailConfig emailConfig ) {
        final AffiliateEmailAttribute emailAttribute = new AffiliateEmailAttribute();
        emailAttribute.setErrorLog( errorLog );
        emailAttribute.setErrorSource( errorSource );
        emailAttribute.setMessageTypeName( typeName );
        emailAttribute.setNotificationFrom( emailConfig.getNotifyFrom() );
        emailAttribute.setNotificationTo( emailConfig.getNotifyTo() );
        return emailAttribute;
    }

    /**
     * Gets the error message.
     *
     * @param errorId
     *            the error id
     * @param affiliateEmailException
     *            the affiliate email exception
     * @return the error message
     */
    private String getErrorMessage( final String errorId, final AffiliateEmailException affiliateEmailException ) {
        final StrBuilder errorMsg = new StrBuilder();
        errorMsg.appendln( "Error Id : " + errorId + BR_TAG );
        errorMsg.appendln( "Error Code : " + affiliateEmailException.getErrorCode() + BR_TAG );
        errorMsg.appendln( "Cause : " + affiliateEmailException.getLocalizedMessage() + BR_TAG );
        errorMsg.appendln( getValidationErrors( affiliateEmailException ) );
        try {
            final String subject = affiliateEmailException.getMailMessage().getSubject();
            String content = null;
            try {
                final MimeMultipart contentPart = ( MimeMultipart ) affiliateEmailException.getMailMessage()
                        .getContent();
                content = contentPart.getBodyPart( HTML_CONTENT_INDEX ).getContent().toString();
            } catch ( ClassCastException cce ) {
                LOGGER.info( "Email content type is plain-text, ignoring html part conversion", cce );
                content = ( String ) affiliateEmailException.getMailMessage().getContent();
            }
            errorMsg.appendln( "Subject : " + subject + BR_TAG );
            errorMsg.appendln( "Content : " + content + BR_TAG );
        } catch ( IOException | MessagingException e ) {
            LOGGER.info( "Problem getting email subject/content while doing email error handling.", e );
        }
        return errorMsg.toString();
    }

    /**
     * Gets the validation errors.
     *
     * @param affiliateEmailException
     *            the affiliate email exception
     * @return the validation errors
     */
    protected String getValidationErrors( final AffiliateEmailException affiliateEmailException ) {
        String errorMsg = "";
        if (affiliateEmailException instanceof AffiliateEmailValidationException) {
            errorMsg = getFailedConstraintsErrorMsg(
                    ( ( AffiliateEmailValidationException ) affiliateEmailException ).getFailedContraints() );
        }
        return errorMsg;
    }

    /**
     * Gets the failed constraints error msg.
     *
     * @param constraints
     *            the constraints
     * @return the failed constraints error msg
     */
    private String getFailedConstraintsErrorMsg( final Map< String, List< String > > constraints ) {
        final StrBuilder errorMsg = new StrBuilder();
        for ( final Map.Entry< String, List< String > > failedConstraint : constraints.entrySet() ) {
            final List< String > failedConstraints = failedConstraint.getValue();
            for ( final String constraint : failedConstraints ) {
                errorMsg.appendln( failedConstraint.getKey() + " : "
                        + messageSource.getMessage( constraint, null, Locale.getDefault() ) + BR_TAG );
            }
        }
        return errorMsg.toString();
    }

    /**
     * Gets the email address, if email string contains character '<', otherwise
     * return whole email address.
     *
     * @param message
     *            the message
     * @return the domain
     */
    private String getEmailAddress( final Message message ) {
        String from;
        try {
            from = message.getFrom()[0].toString();
        } catch ( MessagingException e ) {
            throw new ApplicationException( e.getLocalizedMessage(), e );
        }
        return from.contains( "<" ) ? from.substring( from.indexOf( "<" ) + 1, from.indexOf( ">" ) ) : from;
    }

    /**
     * Gets the lead source.
     * Instruction for
     * <code>final String[] emailParts = source.toString().split( "@", 3 );</code>
     * part.
     * Pplit email at '@' and consider local and domain part separately;
     * note a split limit of 3 is used as it causes all characters
     * following to an (illegal) second @ character to be put into a
     * separate array element, avoiding the regex application in this
     * case since the resulting array has more than 2 elements
     *
     * @param source
     *            the source
     * @param type
     *            the type
     * @return the lead source
     */
    private String getLeadSource( final String source, final RecordType type ) {
        String computedSource = StringUtils.EMPTY;
        final String middlePart = BLANK_SPACE + HYPHEN + BLANK_SPACE;
        if (StringUtils.isNotBlank( source )) {
            computedSource = SOURCE_OWNERS + middlePart + source;
            if (HUBZU.equals( type )) {
                final String[] emailParts = source.toString().split( "@", 3 );
                computedSource = SOURCE_HUBZU + middlePart + emailParts[1];
            }
        }
        return computedSource;
    }

    /**
     * Gets the failure logger.
     *
     * @param message
     *            the message
     * @param exception
     *            the exception
     * @return the failure logger
     */
    @Override
    public LeadEmailParsingLog getFailureLogger( final Message message, final Exception exception ) {
        final LeadEmailParsingLog logger = getLogger( message );
        if (exception instanceof AffiliateEmailValidationException) {
            logger.setFailuerReason( "Affiliate Email Validation Error : " + getFailedConstraintsErrorMsg(
                    ( ( AffiliateEmailValidationException ) exception ).getFailedContraints() ).replaceAll( BR_TAG,
                            PERIOD + BLANK_SPACE ) );
        } else if (exception instanceof AffiliateEmailParsingException) {
            logger.setFailuerReason( getFailureReasonText( exception, "Affiliate Email Parsing Error " ) );
        } else {
            logger.setFailuerReason( getFailureReasonText( exception, "Affiliate Email Error " ) );
        }
        logger.setStatus( FAILURE );
        logger.setLeadEmail( COULD_NOT_CAPTURE );
        logger.setLeadSource( COULD_NOT_CAPTURE );
        return logger;
    }

    /**
     * Gets the success logger.
     *
     * @param message
     *            the message
     * @param leadRequest
     *            the lead request
     * @return the success logger
     */
    @Override
    public LeadEmailParsingLog getSuccessLogger( final Message message, final GenericLeadRequest leadRequest ) {
        final LeadEmailParsingLog logger = getLogger( message );
        logger.setStatus( SUCCESS );
        logger.setLeadSource( leadRequest.getSource() );
        logger.setLeadEmail( leadRequest.getEmail() );
        return logger;
    }

    /**
     * Gets the failure reason text.
     *
     * @param exception
     *            the exception
     * @param lable
     *            the lable
     * @return the failure reason text
     */
    private String getFailureReasonText( final Exception exception, final String lable ) {
        return lable + COLON + ( exception.getCause() != null ? exception.getCause().getLocalizedMessage()
                : exception.getLocalizedMessage() );
    }

    /**
     * Gets the logger.
     *
     * @param message
     *            the message
     * @return the logger
     */
    private LeadEmailParsingLog getLogger( final Message message ) {
        final LeadEmailParsingLog logger = new LeadEmailParsingLog();
        try {
            logger.setMailReceivedTime( new DateTime( message.getReceivedDate().getTime() ) );
            logger.setFromEmail( getEmailAddress( message ) );
            logger.setToEmail( getAllRecipients( message ) );
            logger.setSubject( message.getSubject() );
            logger.setCreatedBy( GRAVITAS );
            logger.setCreatedDate( new DateTime() );
        } catch ( MessagingException e ) {
            LOGGER.error( "LOGGING FAILED : lead email parsing " + e );
        }
        return logger;
    }

    /**
     * Gets the all recipients.
     *
     * @param message
     *            the message
     * @return the all recipients
     * @throws MessagingException
     *             the messaging exception
     */
    private String getAllRecipients( final Message message ) throws MessagingException {
        String allRecipients = "";
        for ( Address recipient : message.getAllRecipients() ) {
            allRecipients += ( recipient.toString().contains( "<" ) ? recipient.toString()
                    .substring( recipient.toString().indexOf( '<' ) + 1, recipient.toString().indexOf( '>' ) )
                    : recipient.toString() ) + "; ";
        }
        return allRecipients;
    }
}
