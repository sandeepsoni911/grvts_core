package com.owners.gravitas.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * The Class Seller.
 *
 * @author vishwanathm
 */
public class Seller extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7719548756383180738L;

    /** The id. */
    @Size( max = 255, message = "error.seller.id.size" )
    private String id;

    /** The first name. */
    private String firstName;

    /** The last name. */
    @NotBlank( message = "error.lastname.required" )
    @Size( min = 1, max = 80, message = "error.lastname.size" )
    private String lastName;

    /** The phone number. */
    @Size( max = 40, message = "error.phone.size" )
    private String phoneNumber;

    /** The main contact numer. */
    @Size( max = 40, message = "error.main.contact.number.size" )
    private String mainContactNumer;

    /** The email. */
    @NotBlank(message = "error.email.required")
    @Email( message = "error.email.format" )
    @Size( max = 80, message = "error.email.size" )
    private String email;

    /** The address. */
    @Valid
    @NotNull(message = "error.address.required")
    private SellerAddress address;

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName
     *            the new first name
     */
    public void setFirstName( final String firstName ) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName
     *            the new last name
     */
    public void setLastName( final String lastName ) {
        this.lastName = lastName;
    }

    /**
     * Gets the phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number.
     *
     * @param phoneNumber
     *            the new phone number
     */
    public void setPhoneNumber( final String phoneNumber ) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the main contact numer.
     *
     * @return the main contact numer
     */
    public String getMainContactNumer() {
        return mainContactNumer;
    }

    /**
     * Sets the main contact numer.
     *
     * @param mainContactNumer
     *            the new main contact numer
     */
    public void setMainContactNumer( final String mainContactNumer ) {
        this.mainContactNumer = mainContactNumer;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email
     *            the new email
     */
    public void setEmail( final String email ) {
        this.email = email;
    }

    /**
     * Gets the address.
     *
     * @return the address
     */
    public SellerAddress getAddress() {
        return address;
    }

    /**
     * Sets the address.
     *
     * @param address
     *            the new address
     */
    public void setAddress( final SellerAddress address ) {
        this.address = address;
    }

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
     *            the new id
     */
    public void setId( final String id ) {
        this.id = id;
    }
}
