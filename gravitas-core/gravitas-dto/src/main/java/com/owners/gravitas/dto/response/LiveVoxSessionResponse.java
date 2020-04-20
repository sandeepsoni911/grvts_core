package com.owners.gravitas.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class LiveVoxSessionResponse extends BaseResponse {

	private static final long serialVersionUID = 2061681524491590639L;
	
	private String sessionId;
	private Integer userId;
	private Integer clientId;
	private Integer daysUntilPasswordExpires;
	
	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}
	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * @return the clientId
	 */
	public Integer getClientId() {
		return clientId;
	}
	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	/**
	 * @return the daysUntilPasswordExpires
	 */
	public Integer getDaysUntilPasswordExpires() {
		return daysUntilPasswordExpires;
	}
	/**
	 * @param daysUntilPasswordExpires the daysUntilPasswordExpires to set
	 */
	public void setDaysUntilPasswordExpires(Integer daysUntilPasswordExpires) {
		this.daysUntilPasswordExpires = daysUntilPasswordExpires;
	}
}