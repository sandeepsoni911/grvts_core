package com.owners.gravitas.dto.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author gururasm
 *
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class SimilarPropertyResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private List< SimilarProperty > similarPropertyList;

    /**
     * @return the similarPropertyList
     */
    public List< SimilarProperty > getSimilarPropertyList() {
        return similarPropertyList;
    }

    /**
     * @param similarPropertyList
     *            the similarPropertyList to set
     */
    public void setSimilarPropertyList( List< SimilarProperty > similarPropertyList ) {
        this.similarPropertyList = similarPropertyList;
    }
}
