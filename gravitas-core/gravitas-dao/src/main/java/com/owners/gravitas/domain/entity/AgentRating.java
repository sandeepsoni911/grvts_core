package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * The Class AgentRating.
 * 
 * @author ankusht
 */
@Entity( name = "GR_AGENT_RATING" )
public class AgentRating extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3338353661344290123L;

    /** The client email. */
    @Column( name = "CLIENT_EMAIL", nullable = false )
    private String clientEmail;

    /** The client first name. */
    @Column( name = "CLIENT_FIRST_NAME", nullable = false )
    private String clientFirstName;

    /** The client last name. */
    @Column( name = "CLIENT_LAST_NAME", nullable = false )
    private String clientLastName;

    /** The agent details. */
    @ManyToOne
    @JoinColumn( name = "AGENT_DETAIL_ID", nullable = false )
    private AgentDetails agentDetails;

    /** The crm id. */
    @Column( name = "CRM_ID", nullable = false )
    private String crmId;

    /** The crm object type. */
    @Column( name = "CRM_OBJECT_TYPE", nullable = false )
    private String crmObjectType;

    /** The stage. */
    @Column( name = "STAGE", nullable = false )
    private String stage;

    /** The feedback received count. */
    @Column( name = "FEEDBACK_RECEIVED_COUNT", nullable = true )
    private int feedbackReceivedCount;

    /** The rating. */
    @Column( name = "RATING", nullable = true )
    private Integer rating;

    /** The feedback. */
    @Column( name = "FEEDBACK", nullable = true )
    private String feedback;

    /** The comments. */
    @Column( name = "COMMENTS", nullable = true )
    private String comments;

    /**
     * Gets the client email.
     *
     * @return the client email
     */
    public String getClientEmail() {
        return clientEmail;
    }

    /**
     * Sets the client email.
     *
     * @param clientEmail
     *            the new client email
     */
    public void setClientEmail( final String clientEmail ) {
        this.clientEmail = clientEmail;
    }

    /**
     * Gets the client first name.
     *
     * @return the client first name
     */
    public String getClientFirstName() {
        return clientFirstName;
    }

    /**
     * Sets the client first name.
     *
     * @param clientFirstName
     *            the new client first name
     */
    public void setClientFirstName( final String clientFirstName ) {
        this.clientFirstName = clientFirstName;
    }

    /**
     * Gets the client last name.
     *
     * @return the client last name
     */
    public String getClientLastName() {
        return clientLastName;
    }

    /**
     * Sets the client last name.
     *
     * @param clientLastName
     *            the new client last name
     */
    public void setClientLastName( final String clientLastName ) {
        this.clientLastName = clientLastName;
    }

    /**
     * Gets the agent details.
     *
     * @return the agent details
     */
    public AgentDetails getAgentDetails() {
        return agentDetails;
    }

    /**
     * Sets the agent details.
     *
     * @param agentDetails
     *            the new agent details
     */
    public void setAgentDetails( final AgentDetails agentDetails ) {
        this.agentDetails = agentDetails;
    }

    /**
     * Gets the crm id.
     *
     * @return the crm id
     */
    public String getCrmId() {
        return crmId;
    }

    /**
     * Sets the crm id.
     *
     * @param crmId
     *            the new crm id
     */
    public void setCrmId( final String crmId ) {
        this.crmId = crmId;
    }

    /**
     * Gets the crm object type.
     *
     * @return the crm object type
     */
    public String getCrmObjectType() {
        return crmObjectType;
    }

    /**
     * Sets the crm object type.
     *
     * @param crmObjectType
     *            the new crm object type
     */
    public void setCrmObjectType( final String crmObjectType ) {
        this.crmObjectType = crmObjectType;
    }

    /**
     * Gets the stage.
     *
     * @return the stage
     */
    public String getStage() {
        return stage;
    }

    /**
     * Sets the stage.
     *
     * @param stage
     *            the new stage
     */
    public void setStage( final String stage ) {
        this.stage = stage;
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
}
