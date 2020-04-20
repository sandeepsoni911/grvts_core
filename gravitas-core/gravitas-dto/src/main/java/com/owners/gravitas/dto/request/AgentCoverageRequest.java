package com.owners.gravitas.dto.request;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class AgentCoverageRequest {
	
	List<String> zipCodes;
	
	boolean servable;
	
	String email;

	public List<String> getZipCodes() {
		return zipCodes;
	}

	public void setZipCodes(List<String> zipCodes) {
		this.zipCodes = zipCodes;
	}

	public boolean isServable() {
		return servable;
	}

	public void setServable(boolean servable) {
		this.servable = servable;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
