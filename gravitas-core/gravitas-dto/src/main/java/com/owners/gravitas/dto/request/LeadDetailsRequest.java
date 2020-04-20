package com.owners.gravitas.dto.request;

import org.apache.commons.lang3.StringUtils;

public class LeadDetailsRequest {

    private Integer page;

    private Integer size;

    private String direction;

    private String property;
    
    private String type;

    /**
     * @return the page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page
     *            the page to set
     */
    public void setPage( Integer page ) {
        if (page == null) {
            page = 0;
        }
        this.page = page;
    }

    /**
     * @return the size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * @param size
     *            the size to set
     */
    public void setSize( Integer size ) {
        if (size == null) {
            size = 100;
        }
        this.size = size;
    }

    /**
     * @return the direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * @param direction
     *            the direction to set
     */
    public void setDirection( String direction ) {
        if (StringUtils.isEmpty( direction ) || "null".equalsIgnoreCase( direction )) {
            direction = "desc";
        }
        this.direction = direction;
    }

    /**
     * @return the property
     */
    public String getProperty() {
        return property;
    }

    /**
     * @param property
     *            the property to set
     */
    public void setProperty( String property ) {
        if (StringUtils.isEmpty( property ) || "score".equalsIgnoreCase( property ) || "null".equalsIgnoreCase( property )) {
            property = "buyerLeadScore";
        }else{
            property =  getPropertyName( property );
        }
        this.property = property;
    }
    
    
    private String getPropertyName( final String property ) {
        String finalName = "";
        switch ( property ) {
            case "score":
                finalName = "buyerLeadScore";
                break;
            default:
                finalName = property;
        }
        return finalName;
    }

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		if (StringUtils.isEmpty( type ) || (!"lead".equalsIgnoreCase( type ) && !"opportunity".equalsIgnoreCase( type ))) {
			type = "lead";
        }
        this.type = type;
	}
    
}
