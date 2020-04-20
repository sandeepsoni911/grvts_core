package com.owners.gravitas.dto.response;

public class MenuConfigResponse extends BaseResponse {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1160047916773112178L;
	
	/** The config. */
	private Object config;
    
    /**
     * Instantiates a new MenuConfigResponse.
     */
    public MenuConfigResponse() {
        
    }

    /**
     * Instantiates a new MenuConfigResponse.
     *
     * @param status
     *            the status
     * @param message
     *            the message
     */
    public MenuConfigResponse( final Status status, final String message ) {
        super( status, message );
    }

	/**
	 * @return the config
	 */
	public Object getConfig() {
		return config;
	}

	/**
	 * @param config the config to set
	 */
	public void setConfig(Object config) {
		this.config = config;
	}

}
