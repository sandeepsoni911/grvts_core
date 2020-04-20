package com.owners.gravitas.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.BaseDTO;

/**
 * 
 * @author gururasm
 *
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class UserNotificationConfigResponse extends BaseDTO {

    /** The Serial version id **/
    private static final long serialVersionUID = -894746757422986752L;

    private UserNotificationConfigResult result;

    private String status;

    private String statusMessage;

    /**
     * @return the result
     */
    public UserNotificationConfigResult getResult() {
        return result;
    }

    /**
     * @param result
     *            the result to set
     */
    public void setResult( UserNotificationConfigResult result ) {
        this.result = result;
    }

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
}
