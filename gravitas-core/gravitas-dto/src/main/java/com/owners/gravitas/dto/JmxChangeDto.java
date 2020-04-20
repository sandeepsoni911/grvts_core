package com.owners.gravitas.dto;

/**
 * The Class JmxChangeDto.
 * 
 * @author ankusht
 */
public class JmxChangeDto {

    /** The key. */
    private final String property;

    /** The value. */
    private final Object value;

    /**
     * Instantiates a new jmx change event.
     *
     * @param source
     *            the source
     * @param property
     *            the key
     * @param value
     *            the value
     */
    public JmxChangeDto( final String property, final Object value ) {
        this.property = property;
        this.value = value;
    }

    /**
     * Gets the property.
     *
     * @return the property
     */
    public String getProperty() {
        return property;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public Object getValue() {
        return value;
    }
}
