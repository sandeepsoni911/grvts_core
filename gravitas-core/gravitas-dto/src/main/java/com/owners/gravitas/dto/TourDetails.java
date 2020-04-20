/**
 *
 */
package com.owners.gravitas.dto;

/**
 * The Class TourDetails.
 *
 * @author harshads
 * @param <T> the generic type
 */
public class TourDetails<T> extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2134172317631209418L;

    /** The type. */
    private String type;

    /** The value. */
    private T value;

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
     * @param type the new type
     */
    public void setType( final String type ) {
        this.type = type;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue( final T value ) {
        this.value = value;
    }
}
