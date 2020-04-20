package com.owners.gravitas.dto.response;

public class LeadLayoutResponse extends BaseResponse {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1160047916773112178L;
	
	/** The layout. */
	private Object layout;
    
    /**
     * Instantiates a new LeadLayoutResponse.
     */
    public LeadLayoutResponse() {
        
    }

    /**
     * Instantiates a new LeadLayoutResponse.
     *
     * @param status
     *            the status
     * @param message
     *            the message
     */
    public LeadLayoutResponse( final Status status, final String message ) {
        super( status, message );
    }

	
    /**
	 * @return the layout
	 */
	public Object getLayout() {
		return layout;
	}
	

	/**
	 * @param layout the layout to set
	 */
	public void setLayout(Object layout) {
		this.layout = layout;
	}

}
