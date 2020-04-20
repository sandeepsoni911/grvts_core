package com.owners.gravitas.business;

import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.request.FeedbackRequest;
import com.owners.gravitas.dto.response.AgentRatingResponse;

/**
 * The Interface AgentRatingBusinessService.
 * 
 * @author ankusht
 */
public interface AgentRatingBusinessService {

    /**
     * Save agent rating.
     *
     * @param a
     *            the a
     * @param b
     *            the b
     * @return the agent rating response
     */
    AgentRatingResponse saveAgentRating( String a, String b );

    /**
     * Send email.
     *
     * @param emailTemplate
     *            the email template
     * @param agentId
     *            the agent id
     * @param crmOpportunityId
     *            the crm opportunity id
     * @param contact
     *            the contact
     */
    void sendEmail( String emailTemplate, String agentId, String crmOpportunityId, Contact contact );

    /**
     * Save feedback.
     *
     * @param feedbackRequest
     *            the feedback request
     */
    void saveFeedback( FeedbackRequest feedbackRequest );
}
