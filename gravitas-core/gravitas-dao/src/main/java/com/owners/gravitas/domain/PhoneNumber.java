package com.owners.gravitas.domain;

import javax.validation.constraints.Size;

import com.owners.gravitas.enums.PhoneNumberType;

/**
 * The Class PhoneNumber.
 */
public class PhoneNumber extends BaseDomain {

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

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.domain.BaseDomain#toString()
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append( "{type=" ).append( type ).append( ", number=" ).append( number ).append( "}" );
        return builder.toString();
    }
}
