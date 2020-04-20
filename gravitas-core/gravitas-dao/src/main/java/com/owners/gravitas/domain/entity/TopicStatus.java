package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * The Class TopicStatusType.
 *
 * @author raviz
 */
@Entity( name = "GR_TOPIC_STATUS" )
public class TopicStatus extends AbstractPersistable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6461437712148426084L;

    /** The topic name. */
    @Column( name = "NAME", nullable = false )
    private String topicName;

    /** The status. */
    @Column( name = "STATUS", nullable = true )
    private String status;

    /**
     * Gets the topic name.
     *
     * @return the topic name
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     * Sets the topic name.
     *
     * @param topicName
     *            the new topic name
     */
    public void setTopicName( final String topicName ) {
        this.topicName = topicName;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status
     *            the new status
     */
    public void setStatus( final String status ) {
        this.status = status;
    }

    /**
     * Instantiates a new topic status.
     *
     * @param topicName
     *            the topic name
     * @param status
     *            the status
     */
    public TopicStatus( final String topicName, final String status ) {
        super();
        this.topicName = topicName;
        this.status = status;
    }

    /**
     * Instantiates a new topic status.
     */
    public TopicStatus() {
        super();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( topicName == null ) ? 0 : topicName.hashCode() );
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( Object obj ) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TopicStatus other = ( TopicStatus ) obj;
        if (topicName == null) {
            if (other.topicName != null)
                return false;
        } else if (!topicName.equals( other.topicName ))
            return false;
        return true;
    }
}
