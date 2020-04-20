package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author shivamm
 *         The Class ObjectType.
 */
@Entity( name = "GR_OBJECT" )
public class ObjectType extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1633548100973832562L;

    /** The type. */
    @Column( name = "NAME", nullable = false )
    private String name;

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

}
