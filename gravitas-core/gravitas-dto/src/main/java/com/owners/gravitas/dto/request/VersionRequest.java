package com.owners.gravitas.dto.request;

import static com.owners.gravitas.constants.Constants.REG_EXP_NUMERIC_VERSION;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.enums.GravitasClientType;
import com.owners.gravitas.validators.ClientType;

/**
 * The Class VersionRequest.
 *
 * @author nishak
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class VersionRequest {

    /** The clientType. */
    @NotBlank( message = "error.version.clientType.required" )
    @ClientType( enumClass = GravitasClientType.class, message = "error.version.clientType.format" )
    private String clientType;

    /** The minVersion. */
    @NotBlank( message = "error.version.minVersion.required" )
    @Size( min = 5, max = 8, message = "error.invalid.version.size" )
    @Pattern( regexp = REG_EXP_NUMERIC_VERSION, message = "error.version.minVersion.format" )
    private String minVersion;

    /** The minMessage. */
    @NotBlank( message = "error.version.minMessage.required" )
    private String minMessage;

    /** The currentVersion. */
    @NotBlank( message = "error.version.currentVersion.required" )
    @Size( min = 5, max = 8, message = "error.invalid.version.size" )
    @Pattern( regexp = REG_EXP_NUMERIC_VERSION, message = "error.version.currentVersion.format" )
    private String currentVersion;

    /** The currentMessage. */
    @NotBlank( message = "error.version.currentMessage.required" )
    private String currentMessage;

    /** The download url. */
    @NotBlank( message = "error.version.url.required" )
    @URL( message = "error.version.url.format" )
    private String url;

    /**
     * Gets the clientType.
     * 
     * @return the clientType.
     */
    public String getClientType() {
        return clientType;
    }

    /**
     * Sets the clientType.
     * 
     * @param clientType
     *            the clientType to set
     */
    public void setClientType( final String clientType ) {
        this.clientType = clientType;
    }

    /**
     * Gets the minVersion.
     * 
     * @return the minVersion
     */
    public String getMinVersion() {
        return minVersion;
    }

    /**
     * Sets the minVersion.
     * 
     * @param minVersion
     *            the minVersion to set
     */
    public void setMinVersion( final String minVersion ) {
        this.minVersion = minVersion;
    }

    /**
     * Gets the minMessage.
     * 
     * @return the minMessage
     */
    public String getMinMessage() {
        return minMessage;
    }

    /**
     * Sets the minMessage.
     * 
     * @param minMessage
     *            the minMessage to set
     */
    public void setMinMessage( final String minMessage ) {
        this.minMessage = minMessage;
    }

    /**
     * Gets the currentVersion.
     * 
     * @return the currentVersion
     */
    public String getCurrentVersion() {
        return currentVersion;
    }

    /**
     * Sets the currentVersion.
     * 
     * @param currentVersion
     *            the currentVersion to set
     */
    public void setCurrentVersion( final String currentVersion ) {
        this.currentVersion = currentVersion;
    }

    /**
     * Gets the currentMessage.
     * 
     * @return the currentMessage
     */
    public String getCurrentMessage() {
        return currentMessage;
    }

    /**
     * Sets the currentMessage.
     * 
     * @param currentMessage
     *            the currentMessage to set
     */
    public void setCurrentMessage( final String currentMessage ) {
        this.currentMessage = currentMessage;
    }

    /**
     * Gets the url.
     * 
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url.
     * 
     * @param url
     *            the url to set
     */
    public void setUrl( final String url ) {
        this.url = url;
    }

}
