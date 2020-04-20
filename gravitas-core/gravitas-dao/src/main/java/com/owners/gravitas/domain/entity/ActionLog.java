package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * The Class ActionLog.
 *
 * @author vishwanathm
 */
@Entity( name = "GR_ACTION_LOG" )
public class ActionLog extends AbstractAuditable {

    /** The Constant DATA_SIZE_LIMIT. */
    private static final int DATA_SIZE_LIMIT = 255;

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3134752266045846647L;

    /** The action type. */
    @Column( name = "ACTION_TYPE", nullable = false )
    private String actionType;

    /** The action on. */
    @Column( name = "ACTION_ENTITY", nullable = false )
    private String actionEntity;

    /** The action object id. */
    @Column( name = "ACTION_ENTITY_ID", nullable = true )
    private String actionEntityId;

    /** The modified by. */
    @Column( name = "ACTION_BY", nullable = false )
    private String actionBy;

    /** The previous value. */
    @Column( name = "PREVIOUS_VALUE", nullable = true )
    private String previousValue;

    /** The current value. */
    @Column( name = "CURRENT_VALUE", nullable = true )
    private String currentValue;

    /** The description. */
    @Column( name = "DESCRIPTION", nullable = true )
    private String description;

    /** The platform. */
    @Column( name = "PLATFORM", nullable = true )
    private String platform;

    /** The platform version. */
    @Column( name = "PLATFORM_VERSION", nullable = true )
    private String platformVersion;

    /** The previous value. */
    @Column( name = "FIELD_NAME", nullable = true )
    private String fieldName;

    /**
     * Gets the field name.
     *
     * @return the field name
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Sets the field name.
     *
     * @param fieldName
     *            the new field name
     */
    public void setFieldName( String fieldName ) {
        this.fieldName = fieldName;
    }

    /**
     * Instantiates a new action log.
     */
    public ActionLog() {
        // default constructor
    }

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
     * Gets the action entity.
     *
     * @return the action entity
     */
    public String getActionEntity() {
        return actionEntity;
    }

    /**
     * Sets the action entity.
     *
     * @param actionEntity
     *            the new action entity
     */
    public void setActionEntity( final String actionEntity ) {
        this.actionEntity = actionEntity;
    }

    /**
     * Gets the modified by.
     *
     * @return the modified by
     */
    public String getActionBy() {
        return actionBy;
    }

    /**
     * Sets the modified by.
     *
     * @param actionBy
     *            the new action by
     */
    public void setActionBy( final String actionBy ) {
        this.actionBy = actionBy;
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
        this.previousValue = trimToSize( previousValue, DATA_SIZE_LIMIT );
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
        this.currentValue = trimToSize( currentValue, DATA_SIZE_LIMIT );
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
     * Gets the action entity id.
     *
     * @return the action entity id
     */
    public String getActionEntityId() {
        return actionEntityId;
    }

    /**
     * Sets the action entity id.
     *
     * @param actionEntityId
     *            the new action entity id
     */
    public void setActionEntityId( final String actionEntityId ) {
        this.actionEntityId = actionEntityId;
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

    /**
     * Trim to size.
     *
     * @param value
     *            the value
     * @param size
     *            the size
     * @return the string
     */
    private String trimToSize( final String value, final int size ) {
        return null == value || value.length() < size ? value : value.substring( 0, size );
    }
}
