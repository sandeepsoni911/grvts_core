package com.owners.gravitas.validator;

import static com.owners.gravitas.enums.ErrorCode.FEEDBACK_LINK_EXPIRED;
import static com.owners.gravitas.enums.ErrorCode.INVALID_FEEDBACK_RECEIVED;
import static com.owners.gravitas.enums.ErrorCode.INVALID_RATING_RECEIVED;
import static com.owners.gravitas.enums.ErrorCode.MAX_ALLOWED_FEEDBACK_ATTEMPTS_EXCEEDED;
import static java.lang.System.currentTimeMillis;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.BeanValidationService;
import com.owners.gravitas.config.FeedbackEmailJmxConfig;
import com.owners.gravitas.domain.entity.AgentRating;
import com.owners.gravitas.dto.request.FeedbackRequest;
import com.owners.gravitas.exception.ApplicationException;

/**
 * The Class FeedbackValidator.
 * 
 * @author ankusht
 */
@Component
public class FeedbackValidator {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( FeedbackValidator.class );

    /** The Constant MIN_RATING. */
    private static final int MIN_RATING = 1;

    /** The Constant MAX_RATING. */
    private static final int MAX_RATING = 5;

    /** The feedback email jmx config. */
    @Autowired
    private FeedbackEmailJmxConfig feedbackEmailJmxConfig;

    /** The bean validation service. */
    @Autowired
    private BeanValidationService beanValidationService;

    /**
     * Check email expiry.
     *
     * @param agentRating
     *            the agent rating
     */
    public void checkEmailExpiry( final AgentRating agentRating ) {
        LOGGER.info( "Validating feedback email expiry for: " + agentRating.getId() );
        final DateTime maxTime = agentRating.getCreatedDate()
                .plusMinutes( feedbackEmailJmxConfig.getFeedbackLinkExpiryInMinutes() );
        if (maxTime.isBefore( currentTimeMillis() )) {
            LOGGER.info( "Feedback email was expired for: " + agentRating.getId() + ", client email: "
                    + agentRating.getAgentDetails().getUser().getEmail() );
            throw new ApplicationException( FEEDBACK_LINK_EXPIRED.getErrorDetail(), FEEDBACK_LINK_EXPIRED );
        }
    }

    /**
     * Validate rating.
     *
     * @param rating
     *            the rating
     */
    public void validateRating( final int rating ) {
        if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new ApplicationException( INVALID_RATING_RECEIVED.getErrorDetail(), INVALID_RATING_RECEIVED );
        }
    }

    /**
     * Validate feedback request.
     *
     * @param feedbackRequest
     *            the feedback request
     */
    public void validateFeedbackRequest( final FeedbackRequest feedbackRequest ) {
        final Map< String, List< String > > failedConstraints = beanValidationService.validate( feedbackRequest );
        if (MapUtils.isNotEmpty( failedConstraints )) {
            throw new ApplicationException( INVALID_FEEDBACK_RECEIVED.getErrorDetail(), INVALID_FEEDBACK_RECEIVED );
        }
    }

    /**
     * Validate max attempts.
     *
     * @param agentRating
     *            the agent rating
     */
    public void validateMaxAttempts( final int feedbackReceivedCount ) {
        if (feedbackReceivedCount >= feedbackEmailJmxConfig.getMaxAllowedFeedbackAttempts()) {
            throw new ApplicationException( MAX_ALLOWED_FEEDBACK_ATTEMPTS_EXCEEDED.getErrorDetail(),
                    MAX_ALLOWED_FEEDBACK_ATTEMPTS_EXCEEDED );
        }
    }
}
