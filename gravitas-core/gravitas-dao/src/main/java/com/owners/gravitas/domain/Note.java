package com.owners.gravitas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class Base PatchNote.
 *
 * @author amits
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude( JsonInclude.Include.NON_NULL )
public class Note extends BaseDomain {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5237888318653333351L;

    /** The details. */
    private String details;

    /** The created dtm. */
    private Long createdDtm;

    /** The opportunity id. */
    private String opportunityId;

    /** The created dtm. */
    private Long lastModifiedDtm;

    /** The deleted. */
    private Boolean deleted;

    /**
     * Instantiates a new note.
     */
    public Note() {
        // do nothing
    }

    /**
     * Instantiates a new note.
     *
     * @param details
     *            the details
     * @param deleted
     *            the deleted
     */
    public Note( final String details, final Boolean deleted, final String opportunityId, final Long createdDtm ) {
        super();
        this.details = details;
        this.deleted = deleted;
        this.opportunityId = opportunityId;
        this.createdDtm = createdDtm;
    }

    /**
     * Gets the details.
     *
     * @return the details
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets the details.
     *
     * @param details
     *            the new details
     */
    public void setDetails( final String details ) {
        this.details = details;
    }

    /**
     * Gets the created dtm.
     *
     * @return the created dtm
     */
    public Long getLastModifiedDtm() {
        return lastModifiedDtm;
    }

    /**
     * Sets the last modified dtm.
     *
     * @param lastModifiedDtm
     *            the new created dtm
     */
    public void setLastModifiedDtm( final Long lastModifiedDtm ) {
        this.lastModifiedDtm = lastModifiedDtm;
    }

    /**
     * Checks if is deleted.
     *
     * @return true, if is deleted
     */
    public Boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the deleted.
     *
     * @param deleted
     *            the new deleted
     */
    public void setDeleted( final Boolean deleted ) {
        this.deleted = deleted;
    }

    /**
     * Gets the opportunity id.
     *
     * @return the opportunity id
     */
    public String getOpportunityId() {
        return opportunityId;
    }

    /**
     * Sets the opportunity id.
     *
     * @param opportunityId
     *            the new opportunity id
     */
    public void setOpportunityId( final String opportunityId ) {
        this.opportunityId = opportunityId;
    }

    /**
     * Gets the created dtm.
     *
     * @return the created dtm
     */
    public Long getCreatedDtm() {
        return createdDtm;
    }

    /**
     * Sets the created dtm.
     *
     * @param createdDtm
     *            the new created dtm
     */
    public void setCreatedDtm( final Long createdDtm ) {
        this.createdDtm = createdDtm;
    }
}
