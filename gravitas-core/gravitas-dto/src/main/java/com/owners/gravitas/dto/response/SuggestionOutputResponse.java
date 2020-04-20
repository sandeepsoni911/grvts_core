package com.owners.gravitas.dto.response;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SuggestionOutputResponse implements Cloneable{
	
	private Map<String, Map<String,List<Suggestions>>> suggestions = new LinkedHashMap<String, Map<String,List<Suggestions>>>();
	
	private String errorMessage;

	public Map<String, Map<String,List<Suggestions>>> getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(Map<String, Map<String,List<Suggestions>>> suggestions) {
		this.suggestions = suggestions;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	@Override
	public SuggestionOutputResponse clone() throws CloneNotSupportedException{
		SuggestionOutputResponse suggestionOutputClone = (SuggestionOutputResponse) super.clone();
		Map<String, Map<String,List<Suggestions>>> suggestionClone = new LinkedHashMap<String, Map<String,List<Suggestions>>>();
		for(Map.Entry<String, Map<String,List<Suggestions>>> mapEntry:suggestions.entrySet()){
			Map<String,List<Suggestions>> suggestionmapClone = new LinkedHashMap<String, List<Suggestions>>();
			for(Map.Entry<String, List<Suggestions>> mapEntrySuggestion:mapEntry.getValue().entrySet()){
				suggestionmapClone.put(mapEntrySuggestion.getKey(), new ArrayList<Suggestions>());
			}
			suggestionClone.put(mapEntry.getKey(), suggestionmapClone);
		}
		suggestionOutputClone.setSuggestions(suggestionClone);
		return suggestionOutputClone;
	}

}
