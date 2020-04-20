package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * The Class SystemProperty.
 *
 * @author amits
 */
@Entity( name = "GR_SYSTEM_PROPERTY" )
public class SystemProperty extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2776562491387057521L;

    /** The id. */
    @Column( name = "PROPERTY_NAME" )
    private String name;

    /** The value. */
    @Column( name = "PROPERTY_VALUE" )
    private String value;

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
    public void setName( String name ) {
        this.name = name;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value.
     *
     * @param value
     *            the value to set
     */
    public void setValue( String value ) {
        this.value = value;
    }

}
