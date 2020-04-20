package com.owners.gravitas.business.tour;

import com.owners.gravitas.dto.tour.TourFeedbackResponse;
import com.zuner.coshopping.model.tour_feedback.TourFeedback;

/**
 * The Interface TourFeedbackBusiness.
 *
 * @author rajputbh
 */
public interface TourFeedbackBusiness {

    /**
     * Creates the agent feedback.
     *
     * @param agentId
     *            the agent id
     * @param agentFeedbackRequest
     *            the agent feedback request
     * @return the agent response
     */
    TourFeedbackResponse createTourFeedback(String agentId, TourFeedback agentFeedbackRequest);

}
