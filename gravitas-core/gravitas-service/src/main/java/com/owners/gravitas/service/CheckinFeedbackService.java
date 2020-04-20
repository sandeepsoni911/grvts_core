package com.owners.gravitas.service;

import java.util.Collection;

import com.owners.gravitas.domain.entity.AgentTask;
import com.owners.gravitas.domain.entity.CheckinFeedback;

// TODO: Auto-generated Javadoc
/**
 * The Interface CheckinFeedbackService.
 *
 * @author amits
 */
public interface CheckinFeedbackService {

    /**
     * Save all.
     *
     * @param feedback
     *            the feedback
     */
    void saveAll( Collection< CheckinFeedback > feedback );

    /**
     * Gets the checkin feedback.
     *
     * @param task
     *            the task
     * @param questionCode
     *            the question code
     * @return the checkin feedback
     */
    Collection< CheckinFeedback > getCheckinFeedback( AgentTask task );

    /**
     * Delete checkins.
     *
     * @param checkins
     *            the checkins
     */
    void deleteCheckins( Collection< CheckinFeedback > checkins );
}
