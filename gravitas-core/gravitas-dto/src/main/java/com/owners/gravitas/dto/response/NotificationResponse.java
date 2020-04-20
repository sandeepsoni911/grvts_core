package com.owners.gravitas.dto.response;

import org.joda.time.DateTime;

/**
 * The Class NotificationResponse.
 *
 * @author vishwanathm
 */
public class NotificationResponse {

    /** The status. */
    private String status;

    /** The status code. */
    private String statusCode;

    /** The status message. */
    private String statusMessage;

    /** The request start time. */
    private DateTime requestStartTime;

    /** The execution time. */
    private int executionTime;

    /** The result. */
    private String result;

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
     * Gets the status code.
     *
     * @return the status code
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the status code.
     *
     * @param statusCode
     *            the new status code
     */
    public void setStatusCode( final String statusCode ) {
        this.statusCode = statusCode;
    }

    /**
     * @return the statusMessage
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * @param statusMessage
     *            the statusMessage to set
     */
    public void setStatusMessage( String statusMessage ) {
        this.statusMessage = statusMessage;
    }

    /**
     * Gets the request start time.
     *
     * @return the request start time
     */
    public DateTime getRequestStartTime() {
        return requestStartTime;
    }

    /**
     * Sets the request start time.
     *
     * @param requestStartTime
     *            the new request start time
     */
    public void setRequestStartTime( final DateTime requestStartTime ) {
        this.requestStartTime = requestStartTime;
    }

    /**
     * Gets the execution time.
     *
     * @return the execution time
     */
    public int getExecutionTime() {
        return executionTime;
    }

    /**
     * Sets the execution time.
     *
     * @param executionTime
     *            the new execution time
     */
    public void setExecutionTime( final int executionTime ) {
        this.executionTime = executionTime;
    }

    /**
     * Gets the result.
     *
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * Sets the result.
     *
     * @param result
     *            the new result
     */
    public void setResult( final String result ) {
        this.result = result;
    }
}
