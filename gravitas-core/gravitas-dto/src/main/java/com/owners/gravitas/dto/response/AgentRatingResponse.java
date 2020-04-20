package com.owners.gravitas.dto.response;

import java.io.Serializable;

/**
 * The Class AgentRatingResponse.
 * 
 * @author ankusht
 */
public class AgentRatingResponse implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5784654703739991500L;

    /** The id. */
    private String id;

    /** The rating. */
    private Integer rating;

    /** The feedback. */
    private String feedback;

    /** The comments. */
    private String comments;

    /** The agent email. */
    private String agentEmail;

    /** The feedback received count. */
    private int feedbackReceivedCount;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId( final String id ) {
        this.id = id;
    }

    /**
     * Gets the rating.
     *
     * @return the rating
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * Sets the rating.
     *
     * @param rating
     *            the new rating
     */
    public void setRating( final Integer rating ) {
        this.rating = rating;
    }

    /**
     * Gets the feedback.
     *
     * @return the feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Sets the feedback.
     *
     * @param feedback
     *            the new feedback
     */
    public void setFeedback( final String feedback ) {
        this.feedback = feedback;
    }

    /**
     * Gets the comments.
     *
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the comments.
     *
     * @param comments
     *            the new comments
     */
    public void setComments( final String comments ) {
        this.comments = comments;
    }

    /**
     * Gets the agent email.
     *
     * @return the agent email
     */
    public String getAgentEmail() {
        return agentEmail;
    }

    /**
     * Sets the agent email.
     *
     * @param agentEmail
     *            the new agent email
     */
    public void setAgentEmail( final String agentEmail ) {
        this.agentEmail = agentEmail;
    }

    /**
     * Gets the feedback received count.
     *
     * @return the feedback received count
     */
    public int getFeedbackReceivedCount() {
        return feedbackReceivedCount;
    }

    /**
     * Sets the feedback received count.
     *
     * @param feedbackReceivedCount
     *            the new feedback received count
     */
    public void setFeedbackReceivedCount( final int feedbackReceivedCount ) {
        this.feedbackReceivedCount = feedbackReceivedCount;
    }

}
