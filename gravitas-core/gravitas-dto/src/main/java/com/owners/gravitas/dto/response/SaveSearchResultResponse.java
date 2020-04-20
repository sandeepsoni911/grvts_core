package com.owners.gravitas.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.BaseDTO;

/**
 * The Class SaveSearchResultResponse.
 * 
 * @author pabhishek
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class SaveSearchResultResponse extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7059408458428050724L;

    /** The status. */
    private String status;

    /** The duplicate saved search ID. */
    private String duplicateSavedSearchID;

    /** The message. */
    private String message;

    /** The save search count. */
    private int saveSearchCount;
    
    private List<SaveSearch> saveSearchList;

    /**
     * Gets the status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status
     *            the new status
     */
    public void setStatus( final String status ) {
        this.status = status;
    }

    /**
     * Gets the duplicate saved search ID.
     *
     * @return the duplicate saved search ID
     */
    public String getDuplicateSavedSearchID() {
        return duplicateSavedSearchID;
    }

    /**
     * Sets the duplicate saved search ID.
     *
     * @param duplicateSavedSearchID
     *            the new duplicate saved search ID
     */
    public void setDuplicateSavedSearchID( final String duplicateSavedSearchID ) {
        this.duplicateSavedSearchID = duplicateSavedSearchID;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param message
     *            the new message
     */
    public void setMessage( final String message ) {
        this.message = message;
    }

    /**
     * Gets the save search count.
     *
     * @return the save search count
     */
    public int getSaveSearchCount() {
        return saveSearchCount;
    }

    /**
     * Sets the save search count.
     *
     * @param saveSearchCount
     *            the new save search count
     */
    public void setSaveSearchCount( final int saveSearchCount ) {
        this.saveSearchCount = saveSearchCount;
    }

	/**
	 * @return the saveSearchList
	 */
	public List<SaveSearch> getSaveSearchList() {
		return saveSearchList;
	}

	/**
	 * @param saveSearchList
	 *            the saveSearchList to set
	 */
	public void setSaveSearchList(List<SaveSearch> saveSearchList) {
		this.saveSearchList = saveSearchList;
	}
}
