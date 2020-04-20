package com.owners.gravitas.validator;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.joda.time.DateTime;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.business.BeanValidationService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.FeedbackEmailJmxConfig;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.AgentRating;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.dto.request.FeedbackRequest;
import com.owners.gravitas.exception.ApplicationException;

/**
 * The Class FeedbackValidatorTest.
 *
 * @author shivamm
 */
public class FeedbackValidatorTest extends AbstractBaseMockitoTest {

    /** The feedback validator. */
    @InjectMocks
    private FeedbackValidator feedbackValidator;

    /** The bean validation service. */
    @Mock
    private BeanValidationService beanValidationService;

    /** The constraint violation. */
    @Mock
    private ConstraintViolation< Object > constraintViolation;

    /** The feedback email jmx config. */
    @Mock
    private FeedbackEmailJmxConfig feedbackEmailJmxConfig;

    /**
     * Test check mail expiry.
     */
    @Test
    public void testCheckMailExpiry() {
        final AgentRating agentRating = new AgentRating();
        agentRating.setId( "test" );
        final DateTime date = new DateTime();
        agentRating.setCreatedDate( date );
        final AgentDetails agentDetails = new AgentDetails();
        final User user = new User();
        user.setEmail( "test" );
        agentDetails.setUser( user );
        agentRating.setAgentDetails( agentDetails );
        when( feedbackEmailJmxConfig.getFeedbackLinkExpiryInMinutes() ).thenReturn( 123 );
        feedbackValidator.checkEmailExpiry( agentRating );
    }

    /**
     * Test check mail expiry should throw exception for expired email.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testCheckMailExpiryShouldThrowExceptionForExpiredEmail() {
        final AgentRating agentRating = new AgentRating();
        agentRating.setCreatedDate( DateTime.now().minusDays( 1 ) );
        final AgentDetails agentDetails = new AgentDetails();
        agentDetails.setUser( new User() );
        agentRating.setAgentDetails( agentDetails );
        when( feedbackEmailJmxConfig.getFeedbackLinkExpiryInMinutes() ).thenReturn( 1 );
        feedbackValidator.checkEmailExpiry( agentRating );
    }

    /**
     * Test validate ratings.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testValidateRatings() {
        feedbackValidator.validateRating( 7 );
    }

    /**
     * Test validate ratings 1.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testValidateRatings1() {
        feedbackValidator.validateRating( 0 );
    }

    /**
     * Test validate ratings should not throw exception.
     */
    @Test
    public void testValidateRatingsShouldNotThrowException() {
        feedbackValidator.validateRating( 2 );
    }

    /**
     * Test validate max attempts with feedback count greater then max allowed.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testValidateMaxAttempts_WithFeedbackCountGreaterThenMaxAllowed() {
        final AgentRating agentRating = new AgentRating();
        agentRating.setFeedbackReceivedCount( 2 );
        feedbackEmailJmxConfig.setMaxAllowedFeedbackAttempts( 1 );
        feedbackValidator.validateMaxAttempts( agentRating.getFeedbackReceivedCount() );
    }

    /**
     * Test validate max attempts with feedback count lesser then max allowed.
     */
    @Test
    public void testValidateMaxAttempts_WithFeedbackCountLesserThenMaxAllowed() {
        final AgentRating agentRating = new AgentRating();
        agentRating.setFeedbackReceivedCount( 2 );
        when( feedbackEmailJmxConfig.getMaxAllowedFeedbackAttempts() ).thenReturn( 3 );
        feedbackValidator.validateMaxAttempts( agentRating.getFeedbackReceivedCount() );
    }

    /**
     * Test validate feedback request.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testValidateFeedbackRequest() {
        final FeedbackRequest feedbackRequest = getInvalidFeedbackRequest();
        final Set< ConstraintViolation< Object > > feedbackConstraintViolations = new HashSet<>();
        feedbackConstraintViolations.add( constraintViolation );

        final Map< String, List< String > > failedContraints = new HashMap<>();
        failedContraints.put( "testKey", new ArrayList<>() );

        when( beanValidationService.validate( feedbackRequest ) ).thenReturn( failedContraints );
        feedbackValidator.validateFeedbackRequest( feedbackRequest );
    }

    /**
     * Test validate feedback request should not throw exception.
     */
    @Test
    public void testValidateFeedbackRequestShouldNotThrowException() {
        final FeedbackRequest feedbackRequest = getInvalidFeedbackRequest();
        final Set< ConstraintViolation< Object > > feedbackConstraintViolations = new HashSet<>();
        feedbackConstraintViolations.add( constraintViolation );
        feedbackValidator.validateFeedbackRequest( feedbackRequest );
    }

    /**
     * Gets the invalid feedback request.
     *
     * @return the invalid feedback request
     */
    private FeedbackRequest getInvalidFeedbackRequest() {
        final FeedbackRequest feedbackRequest = new FeedbackRequest();
        feedbackRequest.setComments( "test" );
        feedbackRequest.setFeedback( null );
        return feedbackRequest;
    }
}
