package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * The Class ObjectAttributeConfig.
 *
 * @author shivamm
 *         The Class ObjectAttributeConfig.
 */
@Entity( name = "GR_OBJECT_ATTR_CONF" )
public class ObjectAttributeConfig extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1633548100973832562L;

    /** The object dataType. */
    @ManyToOne
    @JoinColumn( nullable = false, name = "OBJECT_ID" )
    private ObjectType objectType;

    /** The attribute name. */
    @Column( name = "ATTR_NAME", nullable = false )
    private String attributeName;

    /** The dataType. */
    @Column( name = "DATA_TYPE", nullable = false )
    private String dataType;

    /** The is required. */
    @Column( name = "IS_REQUIRED", nullable = false )
    private boolean required;

    /**
     * Gets the object dataType.
     *
     * @return the object dataType
     */
    public ObjectType getObjectType() {
        return objectType;
    }

    /**
     * Sets the object dataType.
     *
     * @param objectType
     *            the new object dataType
     */
    public void setObjectType( final ObjectType objectType ) {
        this.objectType = objectType;
    }

    /**
     * Gets the attribute name.
     *
     * @return the attribute name
     */
    public String getAttributeName() {
        return attributeName;
    }

    /**
     * Sets the attribute name.
     *
     * @param attributeName
     *            the new attribute name
     */
    public void setAttributeName( final String attributeName ) {
        this.attributeName = attributeName;
    }

    /**
     * Gets the data type.
     *
     * @return the data type
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * Sets the data type.
     *
     * @param dataType
     *            the new data type
     */
    public void setDataType( final String dataType ) {
        this.dataType = dataType;
    }

    /**
     * Checks if is required.
     *
     * @return true, if is required
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Sets the required.
     *
     * @param required
     *            the new required
     */
    public void setRequired( final boolean required ) {
        this.required = required;
    }

    @Override
    public String toString() {
        return "ObjectAttributeConfig [objectType=" + objectType + ", attributeName=" + attributeName + ", dataType="
                + dataType + ", required=" + required + "]";
    }
}
