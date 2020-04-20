package com.owners.gravitas.event;

import org.springframework.context.ApplicationEvent;
import com.owners.gravitas.enums.TopicName;

/**
 * The Class FailedTopicEvent.
 *
 * @author raviz
 */
public class FailedTopicEvent extends ApplicationEvent {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1058153134256060561L;

    /** The topic name. */
    private TopicName topicName;

    /**
     * Instantiates a new failed topic event.
     *
     * @param source
     *            the source
     * @param topicName
     *            the topic name
     */
    public FailedTopicEvent( final Object source, final TopicName topicName ) {
        super( source );
        this.topicName = topicName;
    }

    /**
     * Gets the failed topic name.
     *
     * @return the failed topic name
     */
    public TopicName getFailedTopicName() {
        return topicName;
    }
}
