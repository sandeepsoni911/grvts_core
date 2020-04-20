package com.owners.gravitas.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class LiveVoxRecord implements Serializable {

    private static final long serialVersionUID = 8553762540177869364L;
    private String account;
    private String firstName;
    private String lastName;
    private String phone1;
    private String extra13;
    private String practicePhoneAlternate;

    /**
     * @return the account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account
     *            the account to set
     */
    public void setAccount( String account ) {
        this.account = account;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }

    /**
     * @return the phone1
     */
    public String getPhone1() {
        return phone1;
    }

    /**
     * @param phone1
     *            the phone1 to set
     */
    public void setPhone1( String phone1 ) {
        this.phone1 = phone1;
    }

    /**
     * @return the extra13
     */
    public String getExtra13() {
        return extra13;
    }

    /**
     * @param extra13
     *            the extra13 to set
     */
    public void setExtra13( String extra13 ) {
        this.extra13 = extra13;
    }

    /**
     * @return the practicePhoneAlternate
     */
    public String getPracticePhoneAlternate() {
        return practicePhoneAlternate;
    }

    /**
     * @param practicePhoneAlternate
     *            the practicePhoneAlternate to set
     */
    public void setPracticePhoneAlternate( String practicePhoneAlternate ) {
        this.practicePhoneAlternate = practicePhoneAlternate;
    }
}
