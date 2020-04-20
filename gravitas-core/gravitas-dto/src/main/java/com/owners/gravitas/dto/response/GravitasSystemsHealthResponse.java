package com.owners.gravitas.dto.response;

/**
 * The Class GravitasSystemsHealthResponse.
 *
 * @author shivamm
 */
public class GravitasSystemsHealthResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8173259203567317433L;

    /** The firbase status. */
    private String firebaseStatus;

    /** The salesforce status. */
    private String salesforceStatus;

    /** The topics status. */
    private String topicsStatus;

    /** The gravitas DB status. */
    private String gravitasDBStatus;

    /** The rabbit MQ status. */
    private String rabbitMQStatus;

    /**
     * Gets the firebase status.
     *
     * @return the firebase status
     */
    public String getFirebaseStatus() {
        return firebaseStatus;
    }

    /**
     * Sets the firebase status.
     *
     * @param firebaseStatus the new firebase status
     */
    public void setFirebaseStatus( String firebaseStatus ) {
        this.firebaseStatus = firebaseStatus;
    }

    /**
     * Gets the salesforce status.
     *
     * @return the salesforce status
     */
    public String getSalesforceStatus() {
        return salesforceStatus;
    }

    /**
     * Sets the salesforce status.
     *
     * @param salesforceStatus the new salesforce status
     */
    public void setSalesforceStatus( String salesforceStatus ) {
        this.salesforceStatus = salesforceStatus;
    }

    /**
     * Gets the topics status.
     *
     * @return the topics status
     */
    public String getTopicsStatus() {
        return topicsStatus;
    }

    /**
     * Sets the topics status.
     *
     * @param topicsStatus the new topics status
     */
    public void setTopicsStatus( String topicsStatus ) {
        this.topicsStatus = topicsStatus;
    }

    /**
     * Gets the gravitas DB status.
     *
     * @return the gravitas DB status
     */
    public String getGravitasDBStatus() {
        return gravitasDBStatus;
    }

    /**
     * Sets the gravitas DB status.
     *
     * @param gravitasDBStatus the new gravitas DB status
     */
    public void setGravitasDBStatus( String gravitasDBStatus ) {
        this.gravitasDBStatus = gravitasDBStatus;
    }

    /**
     * Gets the rabbit MQ status.
     *
     * @return the rabbit MQ status
     */
    public String getRabbitMQStatus() {
        return rabbitMQStatus;
    }

    /**
     * Sets the rabbit MQ status.
     *
     * @param rabbitMQStatus the new rabbit MQ status
     */
    public void setRabbitMQStatus( String rabbitMQStatus ) {
        this.rabbitMQStatus = rabbitMQStatus;
    }
}
