package com.owners.gravitas.business.impl;

import static com.owners.gravitas.enums.ErrorCode.GOOGLE_SERVICE_UNAVAILABLE;
import static java.util.Locale.getDefault;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.owners.gravitas.business.AgentEmailBusinessService;
import com.owners.gravitas.business.BeanValidationService;
import com.owners.gravitas.business.builder.GmailMessageBuilder;
import com.owners.gravitas.dto.ErrorDetail;
import com.owners.gravitas.dto.request.EmailRequest;
import com.owners.gravitas.dto.response.AgentEmailResult;
import com.owners.gravitas.service.GmailService;

/**
 * The Class AgentEmailBusinessServiceImpl.
 *
 * @author ankusht
 */
@Service
public class AgentEmailBusinessServiceImpl implements AgentEmailBusinessService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentEmailBusinessServiceImpl.class );

    /** The Constant SUCCESS. */
    private static final String SUCCESS = "success";

    /** The Constant ERROR. */
    private static final String ERROR = "error";

    /** The gmail message builder. */
    @Autowired
    private GmailMessageBuilder gmailMessageBuilder;

    /** The gmail service. */
    @Autowired
    private GmailService gmailService;

    /** The message source. */
    @Autowired
    private MessageSource messageSource;

    /** The bean validation service. */
    @Autowired
    private BeanValidationService beanValidationService;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentEmailBusinessService#sendAgentEmail(com
     * .owners.gravitas.dto.request.EmailRequest,
     * com.google.api.services.gmail.Gmail, java.lang.String)
     */
    @Override
    @Async( value = "apiExecutor" )
    public Future< AgentEmailResult > sendAgentEmail( final EmailRequest emailRequest, final Gmail gmail,
            final String agentEmail ) {
        final List< ErrorDetail > errorDetails = validateEmailRequest( emailRequest );
        AgentEmailResult agentEmailResult = null;
        if (isNotEmpty( errorDetails )) {
            agentEmailResult = new AgentEmailResult( ERROR, errorDetails );
        } else {
            final Message message = gmailMessageBuilder.convertTo( emailRequest );
            LOGGER.info( "sending email to " + emailRequest.getTo().get( 0 ) );
            try {
                gmailService.sendEmail( gmail, message );
                agentEmailResult = new AgentEmailResult( SUCCESS, null );
            } catch ( final Exception e ) {
                agentEmailResult = new AgentEmailResult( ERROR, buildErrorDetails() );
                LOGGER.error( "IGNORE: Sending agent email, has been taken care", e );
            }
        }
        return new AsyncResult<>( agentEmailResult );
    }

    /**
     * Builds the error details.
     *
     * @return the list
     */
    private List< ErrorDetail > buildErrorDetails() {
        final List< ErrorDetail > errorDetails = new ArrayList<>();
        errorDetails.add( populateErrorDetail( GOOGLE_SERVICE_UNAVAILABLE.getCode() ) );
        return errorDetails;
    }

    /**
     * Validate email request.
     *
     * @param emailRequest
     *            the email request
     * @return the list
     */
    private List< ErrorDetail > validateEmailRequest( final EmailRequest emailRequest ) {
        final Map< String, List< String > > failedContraints = beanValidationService.validate( emailRequest );
        final List< ErrorDetail > errorDetails = new ArrayList<>();
        for ( final List< String > errorCodesList : failedContraints.values() ) {
            for ( final String errorCode : errorCodesList ) {
                errorDetails.add( populateErrorDetail( errorCode ) );
            }
        }
        return errorDetails;
    }

    /**
     * Populate error detail.
     *
     * @param errorCode
     *            the error code
     * @return the error detail
     */
    private ErrorDetail populateErrorDetail( final String errorCode ) {
        return new ErrorDetail( errorCode, messageSource.getMessage( errorCode, null, getDefault() ) );
    }
}
