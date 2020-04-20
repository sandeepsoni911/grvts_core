package com.owners.gravitas.dto.response;

/**
 * The Class AgentEmailPermissionResponse.
 *
 * @author lavjeetk
 */
public class AgentEmailPermissionResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1003015951698594498L;
	
    /** The isAllowed. */
    private boolean isAllowed;

    /**
     * Return true is email is allowed
     * 
     * @return isAllowed
     */
	public boolean isAllowed() {
		return isAllowed;
	}

	/**
	 * Sets the isAllowed
	 * 
	 * @param isAllowed
	 * 			the isAllowed to set
	 */
	public void setAllowed(boolean isAllowed) {
		this.isAllowed = isAllowed;
	}

    

}
