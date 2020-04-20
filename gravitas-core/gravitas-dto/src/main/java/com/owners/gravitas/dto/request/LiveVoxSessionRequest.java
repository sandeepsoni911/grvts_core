package com.owners.gravitas.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class LiveVoxSessionRequest {
	
	private String clientName;
	private String userName;
	private String password;
	private Boolean agent;
	
	/**
	 * @return the clientName
	 */
	public String getClientName() {
		return clientName;
	}
	/**
	 * @param clientName the clientName to set
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
    /**
     * @return the agent
     */
    public Boolean getAgent() {
        return agent;
    }
    /**
     * @param agent the agent to set
     */
    public void setAgent( Boolean agent ) {
        this.agent = agent;
    }
	
}
