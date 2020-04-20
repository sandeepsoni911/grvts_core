package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * The Class AgentFeedback.
 *
 * @author javeedsy
 */
@Entity( name = "GR_TOUR_FEEDBACK_FILE" )
public class TourFeedbackFile extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5451795329503483147L;

    /** The file url. */
    @Column( name = "FILE_URL", nullable = false )
    private String fileUrl;

    /** The file type. */
    @Column( name = "FILE_TYPE", nullable = false )
    private String fileType;

    /** The is like. */
    @Column( name = "IS_LIKE", nullable = true )
    private Boolean isLike;

    /** The comment. */
    @Column( name = "COMMENT", nullable = true )
    private String comment;

    /** The index. */
    @Column( name = "SEQUENCE_NUMBER", nullable = false )
    private int sequenceNumber;

    /**
     * Gets the file url.
     *
     * @return the file url
     */
    public String getFileUrl() {
        return fileUrl;
    }

    /**
     * Sets the file url.
     *
     * @param fileUrl
     *            the new file url
     */
    public void setFileUrl( final String fileUrl ) {
        this.fileUrl = fileUrl;
    }

    /**
     * Gets the file type.
     *
     * @return the file type
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * Sets the file type.
     *
     * @param fileType
     *            the new file type
     */
    public void setFileType( final String fileType ) {
        this.fileType = fileType;
    }

    /**
     * Checks if is like.
     *
     * @return true, if is like
     */
    public Boolean isLike() {
        return isLike;
    }

    /**
     * Sets the like.
     *
     * @param isLike
     *            the new like
     */
    public void setLike( final Boolean isLike ) {
        this.isLike = isLike;
    }

    /**
     * Gets the comment.
     *
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment.
     *
     * @param comment
     *            the new comment
     */
    public void setComment( final String comment ) {
        this.comment = comment;
    }

    /**
     * Gets the index.
     *
     * @return the index
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the index.
     *
     * @param index
     *            the new index
     */
    public void setSequenceNumber( final int sequenceNumber ) {
        this.sequenceNumber = sequenceNumber;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "TourFeedbackFile [fileUrl=" + fileUrl + ", fileType=" + fileType + ", isLike=" + isLike + ", comment="
                + comment + ", sequenceNumber=" + sequenceNumber + ", getCreatedBy()=" + getCreatedBy()
                + ", getCreatedDate()=" + getCreatedDate() + ", getLastModifiedBy()=" + getLastModifiedBy()
                + ", getLastModifiedDate()=" + getLastModifiedDate() + ", getId()=" + getId() + "]";
    }

}
