package com.owners.gravitas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AgentAnalyticsDto extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The agent id. */
    private String agentId;

    /** The key. */
    private String key;

    /** The value. */
    private String value;

    /** The created date. */
    private String createdDate;

    /**
     * Gets the agent id.
     *
     * @return the agentId
     */
    public String getAgentId() {
        return agentId;
    }

    /**
     * Sets the agent id.
     *
     * @param agentId
     *            the agentId to set
     */
    @JsonProperty("agentId")
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    /**
     * Gets the key.
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key.
     *
     * @param key
     *            the key to set
     */
    @JsonProperty("key")
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value.
     *
     * @param value
     *            the value to set
     */
    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the created date.
     *
     * @return the createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the created date.
     *
     * @param createdDate
     *            the createdDate to set
     */
    @JsonProperty("createdDate")
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
