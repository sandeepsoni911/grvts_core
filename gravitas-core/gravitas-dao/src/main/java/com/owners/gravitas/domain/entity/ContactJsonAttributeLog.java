package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 
 * @author gururasm
 *
 */
@Entity( name = "GR_CONTACT_JSON_ATTR_LOG" )
public class ContactJsonAttributeLog extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The object attribute config. */
    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "OBJECT_ATTR_CONF_ID", nullable = false )
    private ObjectAttributeConfig objectAttributeConfig;

    /** The value. */
    @Column( name = "VALUE", nullable = false )
    private String value;

    /**
     * Gets the object attribute config.
     *
     * @return the object attribute config
     */
    public ObjectAttributeConfig getObjectAttributeConfig() {
        return objectAttributeConfig;
    }

    /**
     * Sets the object attribute config.
     *
     * @param objectAttributeConfig
     *            the new object attribute config
     */
    public void setObjectAttributeConfig( final ObjectAttributeConfig objectAttributeConfig ) {
        this.objectAttributeConfig = objectAttributeConfig;
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
     *            the new value
     */
    public void setValue( final String value ) {
        this.value = value;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( objectAttributeConfig == null ) ? 0 : objectAttributeConfig.hashCode() );
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( final Object obj ) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ContactJsonAttributeLog other = ( ContactJsonAttributeLog ) obj;
        if (objectAttributeConfig == null) {
            if (other.objectAttributeConfig != null) {
                return false;
            }
        } else if (!objectAttributeConfig.equals( other.objectAttributeConfig )) {
            return false;
        }
        return true;
    }
}
