package com.owners.gravitas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Object to hold the values for Notes in salesforce
 *
 * @author ankusht
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrmNote extends BaseDTO {

    private static final long serialVersionUID = 7654080723705094844L;

    /** The parent id */
    private String parentId;

    /** The title */
    private String title;

    /** The body */
    private String body;

    public CrmNote() {
        super();
    }

    public CrmNote( final String parentId, final String title, final String body ) {
        super();
        this.parentId = parentId;
        this.title = title;
        this.body = body;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append( "CrmNote [parentId=" ).append( parentId ).append( ", title=" ).append( title )
                .append( ", body=" ).append( body ).append( "]" );
        return builder.toString();
    }

    /**
     * Gets the parent id
     * @return
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * Sets the parent id
     * @param parentId
     */
    public void setParentId( final String parentId ) {
        this.parentId = parentId;
    }

    /**
     * Gets the title
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title
     * @param title
     */
    public void setTitle(final String title ) {
        this.title = title;
    }

    /**
     * Gets the body
     * @return
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the body
     * @param body
     */
    public void setBody( final String body ) {
        this.body = body;
    }

}
