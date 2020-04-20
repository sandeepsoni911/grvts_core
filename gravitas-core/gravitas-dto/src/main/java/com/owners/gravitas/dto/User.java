package com.owners.gravitas.dto;

/**
 * The Class User.
 *
 * @author vishwanathm
 */
public class User extends BaseDTO implements Comparable< User > {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -703316848163339570L;

    /** The first name. */
    private String firstName;

    /** The last name. */
    private String lastName;

    /** The primary email. */
    private String email;

    /** The phone. */
    private String phone;

    /** The status. */
    private String status;

    /** The address. */
    private UserAddress address;

    /**
     * Gets the first name.
     *
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName( final String firstName ) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     *
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName
     *            the lastName to set
     */
    public void setLastName( final String lastName ) {
        this.lastName = lastName;
    }

    /**
     * Gets the primary email.
     *
     * @return the primaryEmail
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the primary email.
     *
     * @param email
     *            the primaryEmail to set
     */
    public void setEmail( final String email ) {
        this.email = email;
    }

    /**
     * Gets the phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone.
     *
     * @param phone
     *            the phone to set
     */
    public void setPhone( final String phone ) {
        this.phone = phone;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status
     *            the new status
     */
    public void setStatus( String status ) {
        this.status = status;
    }

    /**
     * Gets the address.
     *
     * @return the address
     */
    public UserAddress getAddress() {
        return address;
    }

    /**
     * Sets the address.
     *
     * @param address
     *            the address to set
     */
    public void setAddress( final UserAddress address ) {
        this.address = address;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo( final User o ) {
        return this.firstName.compareTo( o.firstName );
    }
}
