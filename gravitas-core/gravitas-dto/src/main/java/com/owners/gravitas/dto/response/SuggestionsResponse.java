package com.owners.gravitas.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.owners.gravitas.dto.ManagingBrokerCumulativeStatistics;

@JsonInclude( Include.NON_NULL )
public class SuggestionsResponse {
	
    private List< Options > suggestions;

    public List< Options > getSuggestions() {
        return suggestions;
    }

    public void setSuggestions( List< Options > suggestions ) {
        this.suggestions = suggestions;
    }
}
