package com.owners.gravitas.dto;

import javax.validation.constraints.Size;

import com.owners.gravitas.enums.PhoneNumberType;

/**
 * The Class PhoneNumber.
 */
public class PhoneNumber extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5584765301929430079L;

    /** The type. */
    private PhoneNumberType type;

    /** The number. */
    @Size( min = 0, max = 40, message = "error.lead.phone.size" )
    private String number;

    /**
     * Instantiates a new phone number.
     */
    public PhoneNumber() {
        // Do nothing
    }

    /**
     * Instantiates a new phone number.
     *
     * @param type
     *            the type
     * @param number
     *            the number
     */
    public PhoneNumber( PhoneNumberType type, String number ) {
        this.type = type;
        this.number = number;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public PhoneNumberType getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type
     *            the new type
     */
    public void setType( PhoneNumberType type ) {
        this.type = type;
    }

    /**
     * Gets the number.
     *
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the number.
     *
     * @param number
     *            the new number
     */
    public void setNumber( String number ) {
        this.number = number;
    }
}
