package com.owners.gravitas.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.BaseDTO;

/**
 * 
 * @author gururasm
 *
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class UserNotificationConfigResult extends BaseDTO {

    /** The Serial version id **/
    private static final long serialVersionUID = -3027382381358496421L;

    private String status;

    private String statusMessage;

    private String errorMessage;

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus( String status ) {
        this.status = status;
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
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage
     *            the errorMessage to set
     */
    public void setErrorMessage( String errorMessage ) {
        this.errorMessage = errorMessage;
    }
}
