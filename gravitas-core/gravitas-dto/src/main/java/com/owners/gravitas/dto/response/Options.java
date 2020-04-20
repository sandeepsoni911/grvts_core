package com.owners.gravitas.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude( Include.NON_NULL )
public class Options {
	
	private String polygonLabel;
	
	private String polygonUiId;
	
	private String key;
	
	public String getPolygonLabel() {
        return polygonLabel;
    }

    public void setPolygonLabel( String polygonLabel ) {
        this.polygonLabel = polygonLabel;
    }

    public String getPolygonUiId() {
        return polygonUiId;
    }

    public void setPolygonUiId( String polygonUiId ) {
        this.polygonUiId = polygonUiId;
    }

    public String getKey() {
        return key;
    }

    public void setKey( String key ) {
        this.key = key;
    }
    
}
