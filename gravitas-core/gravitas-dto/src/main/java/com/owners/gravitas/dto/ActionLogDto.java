/**
 *
 */
package com.owners.gravitas.dto;

import java.util.Map;

/**
 * The Class ActionLogDto.
 *
 * @author harshads
 */
public class ActionLogDto extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8803116751616899968L;

    /** The action type. */
    private String actionType;

    /** The action dtm. */
    private Long actionDtm;

    /** The action entity. */
    private String actionEntity;

    /** The action entity id. */
    private String actionEntityId;

    /** The action by. */
    private String actionBy;

    /** The previous values map. */
    private Map< String, Object > previousValuesMap;

    /** The current values map. */
    private Map< String, Object > currentValuesMap;

    /** The description. */
    private String description;

    /** The platform. */
    private String platform;

    /** The platform version. */
    private String platformVersion;

    /**
     * Gets the action type.
     *
     * @return the actionType
     */
    public String getActionType() {
        return actionType;
    }

    /**
     * Sets the action type.
     *
     * @param actionType
     *            the actionType to set
     */
    public void setActionType( final String actionType ) {
        this.actionType = actionType;
    }

    /**
     * Gets the action dtm.
     *
     * @return the actionDtm
     */
    public Long getActionDtm() {
        return actionDtm;
    }

    /**
     * Sets the action dtm.
     *
     * @param actionDtm
     *            the actionDtm to set
     */
    public void setActionDtm( final Long actionDtm ) {
        this.actionDtm = actionDtm;
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
     * Gets the action by.
     *
     * @return the action by
     */
    public String getActionBy() {
        return actionBy;
    }

    /**
     * Sets the action by.
     *
     * @param actionBy
     *            the new action by
     */
    public void setActionBy( final String actionBy ) {
        this.actionBy = actionBy;
    }

    /**
     * Gets the previous values map.
     *
     * @return the previous values map
     */
    public Map< String, Object > getPreviousValuesMap() {
        return previousValuesMap;
    }

    /**
     * Sets the previous values map.
     *
     * @param previousValuesMap
     *            the previous values map
     */
    public void setPreviousValuesMap( final Map< String, Object > previousValuesMap ) {
        this.previousValuesMap = previousValuesMap;
    }

    /**
     * Gets the current values map.
     *
     * @return the current values map
     */
    public Map< String, Object > getCurrentValuesMap() {
        return currentValuesMap;
    }

    /**
     * Sets the current values map.
     *
     * @param currentValuesMap
     *            the current values map
     */
    public void setCurrentValuesMap( final Map< String, Object > currentValuesMap ) {
        this.currentValuesMap = currentValuesMap;
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
