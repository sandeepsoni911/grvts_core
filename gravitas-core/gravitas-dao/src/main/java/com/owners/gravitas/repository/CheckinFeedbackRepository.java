package com.owners.gravitas.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.AgentTask;
import com.owners.gravitas.domain.entity.CheckinFeedback;

/**
 * The Interface CheckinFeedbackRepository.
 *
 * @author amits
 */
public interface CheckinFeedbackRepository extends JpaRepository< CheckinFeedback, String > {

    /**
     * Find by task.
     *
     * @param task
     *            the task
     * @return the collection
     */
    Collection< CheckinFeedback > findByTask( AgentTask task );
}
