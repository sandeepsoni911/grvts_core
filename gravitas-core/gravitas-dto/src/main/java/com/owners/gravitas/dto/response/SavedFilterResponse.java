package com.owners.gravitas.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This class response for Saved Filter
 * related APIs
 * @author chinmaytotekar
 *
 */

@JsonIgnoreProperties( ignoreUnknown = true )
public class SavedFilterResponse extends BaseResponse {
	
    /**
	 * Serial version id
	 */
	private static final long serialVersionUID = 8721637105382195911L;
	
	
	private List<SavedFilter> data;

	/**
	 * @return the data
	 */
	public List<SavedFilter> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(List<SavedFilter> data) {
		this.data = data;
	}
	
    
    
    public SavedFilterResponse(){
    }
    /**
     * Instantiates a new lead response.
     *
     * @param status
     *            the status
     * @param message
     *            the message
     */
    public SavedFilterResponse( final Status status, final String message) {
        super( status, message );
    }
}
