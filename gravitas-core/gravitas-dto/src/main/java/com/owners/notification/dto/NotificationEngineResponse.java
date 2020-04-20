package com.owners.notification.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.response.BaseResponse;

@JsonIgnoreProperties( ignoreUnknown = true )
public class NotificationEngineResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /** The status. */
    private Status status;

    /** The status code. */
    private String statusCode;

    /** The status message. */
    private String statusMessage;

    /** The request id. */
    private String requestId;

    /** The request start time. */
    private long requestStartTime;

    /** The execution time. */
    private long executionTime;

    private String result;

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus( Status status ) {
        this.status = status;
    }

    /**
     * @return the statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode
     *            the statusCode to set
     */
    public void setStatusCode( String statusCode ) {
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
     * @return the requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * @param requestId
     *            the requestId to set
     */
    public void setRequestId( String requestId ) {
        this.requestId = requestId;
    }

    /**
     * @return the requestStartTime
     */
    public long getRequestStartTime() {
        return requestStartTime;
    }

    /**
     * @param requestStartTime
     *            the requestStartTime to set
     */
    public void setRequestStartTime( long requestStartTime ) {
        this.requestStartTime = requestStartTime;
    }

    /**
     * @return the executionTime
     */
    public long getExecutionTime() {
        return executionTime;
    }

    /**
     * @param executionTime
     *            the executionTime to set
     */
    public void setExecutionTime( long executionTime ) {
        this.executionTime = executionTime;
    }

    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * @param result
     *            the result to set
     */
    public void setResult( String result ) {
        this.result = result;
    }

}
