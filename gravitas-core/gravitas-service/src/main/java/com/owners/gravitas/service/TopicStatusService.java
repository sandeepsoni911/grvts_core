package com.owners.gravitas.service;

import java.util.List;
import java.util.Set;

import com.owners.gravitas.domain.entity.TopicStatus;

/**
 * The Interface TopicStatusService.
 *
 * @author raviz
 */
public interface TopicStatusService {

    /**
     * Clear topic status data.
     */
    void clearTopicStatusData();

    /**
     * Find all.
     *
     * @return the list
     */
    Set< String > findTopicNames();

    /**
     * Save.
     *
     * @param topicStatus
     *            the topic status
     * @return the topic status
     */
    TopicStatus save( final TopicStatus topicStatus );

    /**
     * Find by name.
     *
     * @param topicName
     *            the topic name
     * @return the topic status
     */
    TopicStatus findByName( final String topicName );

    /**
     * Update topic status.
     *
     * @param name
     *            the name
     * @param status
     *            the status
     */
    void updateTopicStatus( final String name, final String status );

    /**
     * Find all.
     *
     * @return the list
     */
    List< TopicStatus > findAll();
}
