package com.owners.gravitas.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.BaseDTO;

/**
 * The Class SaveSearchResponse.
 *
 * @author pabhishek
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class SaveSearchResponse extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2011315747218124805L;

    /** The result. */
    private SaveSearchResultResponse result;

    /**
     * Gets the result.
     *
     * @return the result
     */
    public SaveSearchResultResponse getResult() {
        return result;
    }

    /**
     * Sets the result.
     *
     * @param result
     *            the new result
     */
    public void setResult( final SaveSearchResultResponse result ) {
        this.result = result;
    }

}
