package com.owners.gravitas.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.owners.gravitas.domain.entity.TopicStatus;

/**
 * The Interface TopicStatusRepository.
 *
 * @author raviz
 */
public interface TopicStatusRepository extends JpaRepository< TopicStatus, String > {

    @Query("select ts.topicName from GR_TOPIC_STATUS ts")
    Set< String > findTopicNames();
    
    /**
     * Find by topic name.
     *
     * @param topicName
     *            the topic name
     * @return the topic status
     */
    TopicStatus findByTopicName( final String topicName);
}
