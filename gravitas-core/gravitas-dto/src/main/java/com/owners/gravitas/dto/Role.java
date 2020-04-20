package com.owners.gravitas.dto;

/**
 * The Class Role.
 * 
 * @author pabhishek
 */
public class Role extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -57473475075401481L;

    /** The name. */
    private String name;

    /** The description. */
    private String description;

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
     *            the new name
     */
    public void setName( String name ) {
        this.name = name;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description
     *            the new description
     */
    public void setDescription( String description ) {
        this.description = description;
    }

}
