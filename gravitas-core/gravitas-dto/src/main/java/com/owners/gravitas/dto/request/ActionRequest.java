package com.owners.gravitas.dto.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.owners.gravitas.validators.Contains;

/**
 * The Class ActionRequest.
 *
 * @author vishwanathm
 */
public class ActionRequest {

    /** The action type. */
    @NotBlank( message = "error.action.actionType.required" )
    @Contains( propertyKey = "action.property.actionTypes", message = "error.action.actionType.format" )
    private String actionType;

    /** The action on. */
    @NotBlank( message = "error.action.actionEntity.required" )
    @Contains( propertyKey = "action.property.actionEntities", message = "error.action.actionEntity.format" )
    private String actionEntity;

    /** The action object id. */
    @Size( min = 0, max = 60, message = "error.action.actionEntityId.size" )
    private String actionEntityId;

    /** The previous value. */
    @Size( min = 0, max = 255, message = "error.action.previousValue.size" )
    private String previousValue;

    /** The current value. */
    @Size( min = 0, max = 255, message = "error.action.currentValue.size" )
    private String currentValue;

    /** The platform. */
    @NotBlank( message = "error.action.platform.required" )
    @Contains( propertyKey = "action.property.platforms", message = "error.action.platform.format" )
    private String platform;

    /** The platform version. */
    @Size( min = 0, max = 20, message = "error.action.platformVersion.size" )
    private String platformVersion;

    /** The description. */
    @Size( min = 0, max = 255, message = "error.action.description.size" )
    private String description;

    /**
     * Gets the action type.
     *
     * @return the action type
     */
    public String getActionType() {
        return actionType;
    }

    /**
     * Sets the action type.
     *
     * @param actionType
     *            the new action type
     */
    public void setActionType( final String actionType ) {
        this.actionType = actionType;
    }

    /**
     * Gets the action on.
     *
     * @return the action on
     */
    public String getActionEntity() {
        return actionEntity;
    }

    /**
     * Sets the action on.
     *
     * @param actionEntity
     *            the new action on
     */
    public void setActionEntity( final String actionOn ) {
        this.actionEntity = actionOn;
    }

    /**
     * Gets the previous value.
     *
     * @return the previous value
     */
    public String getPreviousValue() {
        return previousValue;
    }

    /**
     * Sets the previous value.
     *
     * @param previousValue
     *            the new previous value
     */
    public void setPreviousValue( final String previousValue ) {
        this.previousValue = previousValue;
    }

    /**
     * Gets the current value.
     *
     * @return the current value
     */
    public String getCurrentValue() {
        return currentValue;
    }

    /**
     * Sets the current value.
     *
     * @param currentValue
     *            the new current value
     */
    public void setCurrentValue( final String currentValue ) {
        this.currentValue = currentValue;
    }

    /**
     * Gets the platform.
     *
     * @return the platform
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * Sets the platform.
     *
     * @param platform
     *            the new platform
     */
    public void setPlatform( final String platform ) {
        this.platform = platform;
    }

    /**
     * Gets the action object id.
     *
     * @return the action object id
     */
    public String getActionEntityId() {
        return actionEntityId;
    }

    /**
     * Sets the action object id.
     *
     * @param actionEntityId
     *            the new action object id
     */
    public void setActionEntityId( final String actionObjectId ) {
        this.actionEntityId = actionObjectId;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description
     *            the new description
     */
    public void setDescription( final String description ) {
        this.description = description;
    }

    /**
     * Gets the platform version.
     *
     * @return the platform version
     */
    public String getPlatformVersion() {
        return platformVersion;
    }

    /**
     * Sets the platform version.
     *
     * @param platformVersion
     *            the new platform version
     */
    public void setPlatformVersion( final String platformVersion ) {
        this.platformVersion = platformVersion;
    }

}
