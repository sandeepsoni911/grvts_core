package com.owners.gravitas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The class OpportunityDataNode
 * @author sandeepsoni
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude( JsonInclude.Include.NON_NULL )
public class OpportunityDataNode extends BaseDomain {
	
	 /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5237888318653333351L;

    private String value;

    /** The created dtm. */
    private Long createdDtm;


    /** The created dtm. */
    private Long lastModifiedDtm;


	/**
	 * @return the createdDtm
	 */
	public Long getCreatedDtm() {
		return createdDtm;
	}

	/**
	 * @param createdDtm the createdDtm to set
	 */
	public void setCreatedDtm(Long createdDtm) {
		this.createdDtm = createdDtm;
	}

	/**
	 * @return the lastModifiedDtm
	 */
	public Long getLastModifiedDtm() {
		return lastModifiedDtm;
	}

	/**
	 * @param lastModifiedDtm the lastModifiedDtm to set
	 */
	public void setLastModifiedDtm(Long lastModifiedDtm) {
		this.lastModifiedDtm = lastModifiedDtm;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
