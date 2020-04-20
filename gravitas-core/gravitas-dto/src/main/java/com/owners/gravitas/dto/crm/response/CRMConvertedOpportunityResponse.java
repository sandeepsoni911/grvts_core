package com.owners.gravitas.dto.crm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.owners.gravitas.dto.response.BaseResponse;

/**
 * @author ankusht
 * 
 *         The Class CRMConvertedOpportunityResponse.
 *
 * @author ankusht
 */
public class CRMConvertedOpportunityResponse extends BaseResponse {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5484046630926204279L;

	/** The result. */
	private String result;

	/** The opportunityid. */
	private String opportunityid;

	/**
	 * Gets the result.
	 *
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * Sets the result.
	 *
	 * @param result
	 *            the new result
	 */
	@JsonProperty( "result" )
	public void setResult( final String result ) {
		this.result = result;
	}

	/**
	 * Gets the opportunityid.
	 *
	 * @return the opportunityid
	 */
	public String getOpportunityid() {
		return opportunityid;
	}

	/**
	 * Sets the opportunityid.
	 *
	 * @param opportunityid
	 *            the new opportunityid
	 */
	@JsonProperty( "opportunityid" )
	public void setOpportunityid( final String opportunityid ) {
		this.opportunityid = opportunityid;
	}

}
