/**
 *
 */
package com.owners.gravitas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class Image.
 *
 * @author harshads
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class Image extends BaseDTO {

    /** x. */
    private static final long serialVersionUID = -2482431863260557755L;

    private String imageURL;
    
    /** The thumbnail image url. */
    private String thumbnailImageURL;

    /**
     * @return the imageURL
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * @param imageURL
     *            the imageURL to set
     */
    public void setImageURL( String imageURL ) {
        this.imageURL = imageURL;
    }

    /**
     * Gets the thumbnail image url.
     *
     * @return the thumbnailImageURL
     */
    public String getThumbnailImageURL() {
        return thumbnailImageURL;
    }

    /**
     * @param thumbnailImageURL
     *            the thumbnailImageURL to set
     */
    public void setThumbnailImageURL( final String thumbnailImageURL ) {
        this.thumbnailImageURL = thumbnailImageURL;
    }
}
