package com.owners.gravitas.dto;

import java.util.List;

import com.owners.gravitas.enums.ErrorCode;

// TODO: Auto-generated Javadoc
/**
 * The Class SlackError.
 */
public class SlackError {

    /** The exception. */
    private Throwable exception;

    /** The error code. */
    private ErrorCode errorCode;

    /** The error id. */
    private String errorId;

    /** The error message. */
    private String errorMessage;

    /** The request uri. */
    private String requestUrl;

    /** The user. */
    private String user;

    /** The request params. */
    private List< Object > requestParams;

    /**
     * Gets the exception.
     *
     * @return the exception
     */
    public Throwable getException() {
        return exception;
    }

    /**
     * Sets the exception.
     *
     * @param exception
     *            the new exception
     */
    public void setException( Throwable exception ) {
        this.exception = exception;
    }

    /**
     * Gets the error code.
     *
     * @return the error code
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the error code.
     *
     * @param errorCode
     *            the new error code
     */
    public void setErrorCode( ErrorCode errorCode ) {
        this.errorCode = errorCode;
    }

    /**
     * Gets the error id.
     *
     * @return the error id
     */
    public String getErrorId() {
        return errorId;
    }

    /**
     * Sets the error id.
     *
     * @param errorId
     *            the new error id
     */
    public void setErrorId( String errorId ) {
        this.errorId = errorId;
    }

    /**
     * Gets the error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message.
     *
     * @param errorMessage
     *            the new error message
     */
    public void setErrorMessage( String errorMessage ) {
        this.errorMessage = errorMessage;
    }

    /**
     * Gets the request uri.
     *
     * @return the request uri
     */
    public String getRequestUrl() {
        return requestUrl;
    }

    /**
     * Sets the request uri.
     *
     * @param requestUri
     *            the new request url
     */
    public void setRequestUrl( String requestUri ) {
        this.requestUrl = requestUri;
    }

    /**
     * Gets the user.
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the user.
     *
     * @param user
     *            the user to set
     */
    public void setUser( String user ) {
        this.user = user;
    }

    /**
     * Gets the request params.
     *
     * @return the requestParams
     */
    public List< Object > getRequestParams() {
        return requestParams;
    }

    /**
     * Sets the request params.
     *
     * @param requestParams
     *            the requestParams to set
     */
    public void setRequestParams( List< Object > requestParams ) {
        this.requestParams = requestParams;
    }

}
