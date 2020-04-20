package com.owners.gravitas.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Suggestions {
	
	private String label;
	
	private String id;
	
	private String type;
	
	private String level1Text;
	
	private String level2Text;
	
	private String jsonResult;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLevel1Text() {
		return level1Text;
	}

	public void setLevel1Text(String level1Text) {
		this.level1Text = level1Text;
	}

	public String getLevel2Text() {
		return level2Text;
	}

	public void setLevel2Text(String level2Text) {
		this.level2Text = level2Text;
	}

	public String getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(String jsonResult) {
		this.jsonResult = jsonResult;
	}

}
