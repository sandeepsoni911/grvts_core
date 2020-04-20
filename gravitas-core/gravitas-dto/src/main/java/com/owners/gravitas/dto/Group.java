package com.owners.gravitas.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.owners.gravitas.serializer.CustomDateTimeSerializer;

/**
 * The Class Group.
 *
 * @author raviz
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class Group extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1860876545435254170L;

    /** The group id. */
    private String groupId;

    /** The group name. */
    @NotBlank( message = "error.group.name.required" )
    @Size( min = 1, max = 30, message = "error.group.name.size" )
    private String groupName;

    /** The test start date. */
    @NotNull( message = "error.group.testStartDate.required" )
    @JsonFormat( shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC" )
    private Date testStartDate;

    /** The test end date. */
    @NotNull( message = "error.group.testEndDate.required" )
    @JsonFormat( shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC" )
    private Date testEndDate;

    /** The related okr. */
    @NotBlank( message = "error.group.relatedOkr.required" )
    @Size( min = 1, max = 30, message = "error.group.name.size" )
    private String relatedOkr;

    /** The created by. */
    private String createdBy;

    /** The created on. */
    private DateTime createdOn;

    /** The last modified by. */
    private String lastModifiedBy;

    /** The last modified on. */
    private DateTime lastModifiedOn;

    /**
     * Gets the group id.
     *
     * @return the group id
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Sets the group id.
     *
     * @param groupId
     *            the new group id
     */
    public void setGroupId( final String groupId ) {
        this.groupId = groupId;
    }

    /**
     * Gets the group name.
     *
     * @return the group name
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Sets the group name.
     *
     * @param groupName
     *            the new group name
     */
    public void setGroupName( final String groupName ) {
        this.groupName = groupName;
    }

    /**
     * Gets the test start date.
     *
     * @return the test start date
     */
    public Date getTestStartDate() {
        return testStartDate;
    }

    /**
     * Sets the test start date.
     *
     * @param testStartDate
     *            the new test start date
     */
    public void setTestStartDate( final Date testStartDate ) {
        this.testStartDate = testStartDate;
    }

    /**
     * Gets the test end date.
     *
     * @return the test end date
     */
    public Date getTestEndDate() {
        return testEndDate;
    }

    /**
     * Sets the test end date.
     *
     * @param testEndDate
     *            the new test end date
     */
    public void setTestEndDate( final Date testEndDate ) {
        this.testEndDate = testEndDate;
    }

    /**
     * Gets the related okr.
     *
     * @return the related okr
     */
    public String getRelatedOkr() {
        return relatedOkr;
    }

    /**
     * Sets the related okr.
     *
     * @param relatedOkr
     *            the new related okr
     */
    public void setRelatedOkr( final String relatedOkr ) {
        this.relatedOkr = relatedOkr;
    }

    /**
     * Gets the created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the created by.
     *
     * @param createdBy
     *            the new created by
     */
    public void setCreatedBy( final String createdBy ) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the created on.
     *
     * @return the created on
     */
    @JsonSerialize( using = CustomDateTimeSerializer.class )
    public DateTime getCreatedOn() {
        return createdOn;
    }

    /**
     * Sets the created on.
     *
     * @param createdOn
     *            the new created on
     */
    public void setCreatedOn( final DateTime createdOn ) {
        this.createdOn = createdOn;
    }

    /**
     * Gets the last modified by.
     *
     * @return the last modified by
     */
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     * Sets the last modified by.
     *
     * @param lastModifiedBy
     *            the new last modified by
     */
    public void setLastModifiedBy( final String lastModifiedBy ) {
        this.lastModifiedBy = lastModifiedBy;
    }

    /**
     * Gets the last modified on.
     *
     * @return the last modified on
     */
    @JsonSerialize( using = CustomDateTimeSerializer.class )
    public DateTime getLastModifiedOn() {
        return lastModifiedOn;
    }

    /**
     * Sets the last modified on.
     *
     * @param lastModifiedOn
     *            the new last modified on
     */
    public void setLastModifiedOn( final DateTime lastModifiedOn ) {
        this.lastModifiedOn = lastModifiedOn;
    }

}
