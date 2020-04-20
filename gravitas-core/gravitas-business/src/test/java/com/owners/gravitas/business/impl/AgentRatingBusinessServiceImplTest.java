package com.owners.gravitas.business.impl;

import static com.owners.gravitas.config.FeedbackEmailJmxConfig.RATING;
import static com.owners.gravitas.config.FeedbackEmailJmxConfig.RATING_ID;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.business.builder.FeedbackEmailNotificationBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.AgentRating;
import com.owners.gravitas.domain.entity.Opportunity;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.service.AgentDetailsService;
import com.owners.gravitas.service.AgentRatingService;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.SearchService;
import com.owners.gravitas.util.EncryptDecryptUtil;
import com.owners.gravitas.util.PropertiesUtil;
import com.owners.gravitas.validator.FeedbackValidator;

/**
 * The Class AgentRatingBusinessServiceImplTest.
 *
 * @author ankusht
 */
public class AgentRatingBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent rating business service impl. */
    @InjectMocks
    private AgentRatingBusinessServiceImpl agentRatingBusinessServiceImpl;

    /** The agent rating service. */
    @Mock
    private AgentRatingService agentRatingService;

    /** The feedback validator. */
    @Mock
    private FeedbackValidator feedbackValidator;

    /** The opportunity service. */
    @Mock
    private OpportunityService opportunityService;

    /** The agent details service. */
    @Mock
    private AgentDetailsService agentDetailsService;

    /** The feedback email notification builder. */
    @Mock
    private FeedbackEmailNotificationBuilder feedbackEmailNotificationBuilder;

    /** The mail service. */
    @Mock
    private MailService mailService;

    /** The search service. */
    @Mock
    private SearchService searchService;

    /**
     * Before test.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeTest
    public void beforeTest() throws Exception {
        final Map< String, String > props = new HashMap< String, String >();
        props.put( "security.key", "sd>f#$@(*&)*;Alt!%$EHUB" );
        if (PropertiesUtil.getPropertiesMap() == null) {
            PropertiesUtil.setPropertiesMap( props );
        } else {
            PropertiesUtil.getPropertiesMap().putAll( props );
        }
    }

    /**
     * Test save agent rating.
     */
    // @Test
    public void testSaveAgentRating() {
        final Map< String, String > map = new HashMap<>();
        final String ratingString = "1";
        final Integer rating = 1;
        final String ratingId = "ratingId";
        map.put( RATING_ID, ratingId );
        map.put( RATING, ratingString );
        final String[] encrypted = EncryptDecryptUtil.encrypt( map );
        final AgentRating agentRating = new AgentRating();
        final AgentDetails agentDetails = new AgentDetails();
        final User user = new User();
        agentDetails.setUser( user );
        agentRating.setAgentDetails( agentDetails );
        when( agentRatingService.findOne( ratingId ) ).thenReturn( agentRating );
        doNothing().when( feedbackValidator ).validateMaxAttempts( agentRating.getFeedbackReceivedCount() );
        doNothing().when( feedbackValidator ).checkEmailExpiry( agentRating );
        doNothing().when( feedbackValidator ).validateRating( rating );
        agentRatingBusinessServiceImpl.saveAgentRating( encrypted[0], encrypted[1] );
        verify( agentRatingService ).save( agentRating );
        verify( agentRatingService ).findOne( ratingId );
    }

    /**
     * Test save agent rating should not save if agent rating not found.
     */
    // @Test
    public void testSaveAgentRatingShouldNotSaveIfAgentRatingNotFound() {
        final Map< String, String > map = new HashMap<>();
        final String ratingString = "1";
        final String ratingId = "ratingId";
        map.put( RATING_ID, ratingId );
        map.put( RATING, ratingString );
        final String[] encrypted = EncryptDecryptUtil.encrypt( map );
        when( agentRatingService.findOne( ratingId ) ).thenReturn( null );
        agentRatingBusinessServiceImpl.saveAgentRating( encrypted[0], encrypted[1] );
        verify( agentRatingService ).findOne( ratingId );
        verifyZeroInteractions( feedbackValidator );
        verifyZeroInteractions( agentRatingService );
    }

    /**
     * Test send email.
     */
    @Test
    public void testSendEmail() {
        final String emailTemplate = "template";
        final String agentId = "agentId";
        final String crmOpportunityId = "oppId";
        final Contact contact = new Contact();
        final List< String > emails = new ArrayList< String >();
        final String recipient = "a@a.com";
        emails.add( recipient );
        contact.setEmails( emails );
        final String agentEmail = "agent@owners.com";
        final Search searchVal = new Search( "is", agentId, agentEmail, "contactId", crmOpportunityId, recipient,
                "opportunityId" );
        final AgentDetails agentDetails = new AgentDetails();
        agentDetails.setUser( new User() );
        final Opportunity opportunity = new Opportunity();
        final AgentRating agentRating = new AgentRating();
        final EmailNotification notification = new EmailNotification();
        final com.owners.gravitas.domain.entity.Contact contactEntity = new com.owners.gravitas.domain.entity.Contact();
        opportunity.setContact( contactEntity );
        when( searchService.searchByAgentId( agentId ) ).thenReturn( searchVal );
        when( agentDetailsService.findAgentByEmail( agentEmail ) ).thenReturn( agentDetails );
        when( opportunityService.findOpportunityByCrmId( crmOpportunityId ) ).thenReturn( opportunity );
        when( agentRatingService.findByCrmIdAndStageAndClientEmailAndAgentDetails( crmOpportunityId, "stage", recipient,
                agentDetails ) ).thenReturn( null );
        when( agentRatingService.save( any( AgentRating.class ) ) ).thenReturn( agentRating );
        when( feedbackEmailNotificationBuilder.createEmailNotification( recipient, agentEmail, emailTemplate,
                agentRating.getId() ) ).thenReturn( notification );
        agentRatingBusinessServiceImpl.sendEmail( emailTemplate, agentId, crmOpportunityId, contact );
        verify( mailService ).send( notification );
    }

    /**
     * Test send email should not send if sent already.
     */
    @Test
    public void testSendEmailShouldNotSendIfSentAlready() {
        final String emailTemplate = "template";
        final String agentId = "agentId";
        final String crmOpportunityId = "oppId";
        final Contact contact = new Contact();
        final List< String > emails = new ArrayList< String >();
        final String recipient = "a@a.com";
        emails.add( recipient );
        contact.setEmails( emails );
        final String agentEmail = "agent@owners.com";
        final Search searchVal = new Search( "is", agentId, agentEmail, "contactId", crmOpportunityId, recipient,
                "opportunityId" );
        final AgentDetails agentDetails = new AgentDetails();
        agentDetails.setUser( new User() );
        final Opportunity opportunity = new Opportunity();
        final AgentRating agentRating = new AgentRating();
        final com.owners.gravitas.domain.entity.Contact contactEntity = new com.owners.gravitas.domain.entity.Contact();
        opportunity.setContact( contactEntity );
        contactEntity.setStage( "stage" );
        when( searchService.searchByAgentId( agentId ) ).thenReturn( searchVal );
        when( agentDetailsService.findAgentByEmail( agentEmail ) ).thenReturn( agentDetails );
        when( opportunityService.findOpportunityByCrmId( crmOpportunityId ) ).thenReturn( opportunity );
        when( agentRatingService.findByCrmIdAndStageAndClientEmailAndAgentDetails( crmOpportunityId, "stage", recipient,
                agentDetails ) ).thenReturn( agentRating );
        when( agentRatingService.save( any( AgentRating.class ) ) ).thenReturn( agentRating );
        agentRatingBusinessServiceImpl.sendEmail( emailTemplate, agentId, crmOpportunityId, contact );
        verifyZeroInteractions( mailService );
    }

    /**
     * Test send email should not send if opportunity and agent details missing
     * in db.
     */
    @Test
    public void testSendEmailShouldNotSendIfOpportunityAndAgentDetailsMissingInDb() {
        final String emailTemplate = "template";
        final String agentId = "agentId";
        final String crmOpportunityId = "oppId";
        final Contact contact = new Contact();
        final List< String > emails = new ArrayList< String >();
        final String recipient = "a@a.com";
        emails.add( recipient );
        contact.setEmails( emails );
        final String agentEmail = "agent@owners.com";
        final Search searchVal = new Search( "is", agentId, agentEmail, "contactId", crmOpportunityId, recipient,
                "opportunityId" );
        final AgentDetails agentDetails = null;
        final Opportunity opportunity = new Opportunity();
        final com.owners.gravitas.domain.entity.Contact contactEntity = new com.owners.gravitas.domain.entity.Contact();
        opportunity.setContact( contactEntity );
        when( searchService.searchByAgentId( agentId ) ).thenReturn( searchVal );
        when( agentDetailsService.findAgentByEmail( agentEmail ) ).thenReturn( agentDetails );
        when( opportunityService.findOpportunityByCrmId( crmOpportunityId ) ).thenReturn( opportunity );
        agentRatingBusinessServiceImpl.sendEmail( emailTemplate, agentId, crmOpportunityId, contact );
        verifyZeroInteractions( mailService, agentRatingService );
    }

    /**
     * Test send email should not send if opportunity missing in db.
     */
    @Test
    public void testSendEmailShouldNotSendIfOpportunityMissingInDb() {
        final String emailTemplate = "template";
        final String agentId = "agentId";
        final String crmOpportunityId = "oppId";
        final Contact contact = new Contact();
        final List< String > emails = new ArrayList< String >();
        final String recipient = "a@a.com";
        emails.add( recipient );
        contact.setEmails( emails );
        final String agentEmail = "agent@owners.com";
        final Search searchVal = new Search( "is", agentId, agentEmail, "contactId", crmOpportunityId, recipient,
                "opportunityId" );
        final User user = new User();
        user.setEmail( agentEmail );
        final AgentDetails agentDetails = new AgentDetails();
        agentDetails.setUser( user );
        final Opportunity opportunity = new Opportunity();
        final com.owners.gravitas.domain.entity.Contact contactEntity = new com.owners.gravitas.domain.entity.Contact();
        opportunity.setContact( contactEntity );
        final AgentRating agentRating = new AgentRating();
        when( searchService.searchByAgentId( agentId ) ).thenReturn( searchVal );
        when( agentDetailsService.findAgentByEmail( agentEmail ) ).thenReturn( null );
        when( opportunityService.findOpportunityByCrmId( crmOpportunityId ) ).thenReturn( opportunity );
        when( agentRatingService.save( any( AgentRating.class ) ) ).thenReturn( agentRating );
        when( agentRatingService.findByCrmIdAndStageAndClientEmailAndAgentDetails( crmOpportunityId, "stage", recipient,
                agentDetails ) ).thenReturn( agentRating );
        agentRatingBusinessServiceImpl.sendEmail( emailTemplate, agentId, crmOpportunityId, contact );
        verifyZeroInteractions( mailService, agentRatingService );
    }

    /**
     * Test send email should not send if agent details missing in db.
     */
    @Test
    public void testSendEmailShouldNotSendIfAgentDetailsMissingInDb() {
        final String emailTemplate = "template";
        final String agentId = "agentId";
        final String crmOpportunityId = "oppId";
        final Contact contact = new Contact();
        final List< String > emails = new ArrayList< String >();
        final String recipient = "a@a.com";
        emails.add( recipient );
        contact.setEmails( emails );
        final String agentEmail = "agent@owners.com";
        final Search searchVal = new Search( "is", agentId, agentEmail, "contactId", crmOpportunityId, recipient,
                "opportunityId" );
        final AgentDetails agentDetails = null;
        final Opportunity opportunity = new Opportunity();

        when( searchService.searchByAgentId( agentId ) ).thenReturn( searchVal );
        when( agentDetailsService.findAgentByEmail( agentEmail ) ).thenReturn( agentDetails );
        when( opportunityService.findOpportunityByCrmId( crmOpportunityId ) ).thenReturn( opportunity );
        agentRatingBusinessServiceImpl.sendEmail( emailTemplate, agentId, crmOpportunityId, contact );
        verifyZeroInteractions( mailService, agentRatingService );
    }
}
