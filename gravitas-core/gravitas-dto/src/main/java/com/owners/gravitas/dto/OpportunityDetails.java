package com.owners.gravitas.dto;

/**
 * The Class OpportunityDetails.
 */
public class OpportunityDetails extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -501439740408069888L;

    /** The name. */
    private String name;

    /** The type. */
    private String type;

    /** The zipcode. */
    private String zipcode;

    /** The state. */
    private String state;
    
    /** The crm Id. */
    private String id;
    
    /** The f 2 f created on. */
    private String f2fCreatedOn;

    /** The opportunity created on. */
    private String opportunityCreatedOn;
    
    /** The Buyer Email **/
    private String email;
    
    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the id to set
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the name to set
     */
    public void setName( final String name ) {
        this.name = name;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type
     *            the type to set
     */
    public void setType( final String type ) {
        this.type = type;
    }

    /**
     * Gets the zipcode.
     *
     * @return the zipcode
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * Sets the zipcode.
     *
     * @param zipcode
     *            the zipcode to set
     */
    public void setZipcode( final String zipcode ) {
        this.zipcode = zipcode;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state.
     *
     * @param state
     *            the state to set
     */
    public void setState( final String state ) {
        this.state = state;
    }
    
    /**
     * Gets the f 2 f created on.
     *
     * @return the f 2 f created on
     */
    public String getF2fCreatedOn() {
        return f2fCreatedOn;
    }

    /**
     * Sets the f 2 f created on.
     *
     * @param f2fCreatedOn
     *            the new f 2 f created on
     */
    public void setF2fCreatedOn( final String f2fCreatedOn ) {
        this.f2fCreatedOn = f2fCreatedOn;
    }

    /**
     * Gets the opportunity created on.
     *
     * @return the opportunity created on
     */
    public String getOpportunityCreatedOn() {
        return opportunityCreatedOn;
    }

    /**
     * Sets the opportunity created on.
     *
     * @param opportunityCreatedOn
     *            the new opportunity created on
     */
    public void setOpportunityCreatedOn( final String opportunityCreatedOn ) {
        this.opportunityCreatedOn = opportunityCreatedOn;
    }

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
