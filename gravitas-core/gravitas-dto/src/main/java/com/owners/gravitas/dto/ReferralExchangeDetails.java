package com.owners.gravitas.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class ReferralExchangeDetails - contains details about the
 * lead/opportunity which is being forwarded to referral exchange.
 *
 * @author vishwanathm
 */
public class ReferralExchangeDetails extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7768625221766135659L;

    /** The first name. */
    private String firstName;

    /** The last name. */
    private String lastName;

    /** The email. */
    private String email;

    /** The phone. */
    private String phone;

    /** The price. */
    private BigDecimal price;

    /** The city. */
    private String city;

    /** The state. */
    private String state;

    /** The submission type. */
    private String submissionType;

    /** The beds. */
    private BigDecimal beds;

    /** The baths. */
    private BigDecimal baths;

    /** The time frame. */
    private String timeFrame;

    /** The mortgage status. */
    private String mortgageStatus;

    /** The comments. */
    private String comments;

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    @JsonProperty( "first_name" )
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
    @JsonProperty( "last_name" )
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
     * Gets the email.
     *
     * @return the email
     */
    @JsonProperty( "email" )
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
     * Gets the phone.
     *
     * @return the phone
     */
    @JsonProperty( "phone_number" )
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone.
     *
     * @param phone
     *            the new phone
     */
    public void setPhone( final String phone ) {
        this.phone = phone;
    }

    /**
     * Gets the price.
     *
     * @return the price
     */
    @JsonProperty( "price" )
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the price.
     *
     * @param price
     *            the new price
     */
    public void setPrice( final BigDecimal price ) {
        this.price = price;
    }

    /**
     * Gets the city.
     *
     * @return the city
     */
    @JsonProperty( "city" )
    public String getCity() {
        return city;
    }

    /**
     * Sets the city.
     *
     * @param city
     *            the new city
     */
    public void setCity( final String city ) {
        this.city = city;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    @JsonProperty( "state" )
    public String getState() {
        return state;
    }

    /**
     * Sets the state.
     *
     * @param state
     *            the new state
     */
    public void setState( final String state ) {
        this.state = state;
    }

    /**
     * Gets the submission type.
     *
     * @return the submission type
     */
    @JsonProperty( "submission_type" )
    public String getSubmissionType() {
        return submissionType;
    }

    /**
     * Sets the submission type.
     *
     * @param submissionType
     *            the new submission type
     */
    public void setSubmissionType( final String submissionType ) {
        this.submissionType = submissionType;
    }

    /**
     * Gets the beds.
     *
     * @return the beds
     */
    @JsonProperty( "beds" )
    public BigDecimal getBeds() {
        return beds;
    }

    /**
     * Sets the beds.
     *
     * @param beds
     *            the new beds
     */
    public void setBeds( final BigDecimal beds ) {
        this.beds = beds;
    }

    /**
     * Gets the baths.
     *
     * @return the baths
     */
    @JsonProperty( "baths" )
    public BigDecimal getBaths() {
        return baths;
    }

    /**
     * Sets the baths.
     *
     * @param baths
     *            the new baths
     */
    public void setBaths( final BigDecimal baths ) {
        this.baths = baths;
    }

    /**
     * Gets the time frame.
     *
     * @return the time frame
     */
    @JsonProperty( "time_frame" )
    public String getTimeFrame() {
        return timeFrame;
    }

    /**
     * Sets the time frame.
     *
     * @param timeFrame
     *            the new time frame
     */
    public void setTimeFrame( final String timeFrame ) {
        this.timeFrame = timeFrame;
    }

    /**
     * Gets the mortgage status.
     *
     * @return the mortgage status
     */
    @JsonProperty( "mortgage_status" )
    public String getMortgageStatus() {
        return mortgageStatus;
    }

    /**
     * Sets the mortgage status.
     *
     * @param mortgageStatus
     *            the new mortgage status
     */
    public void setMortgageStatus( final String mortgageStatus ) {
        this.mortgageStatus = mortgageStatus;
    }

    /**
     * Gets the comments.
     *
     * @return the comments
     */
    @JsonProperty( "comments" )
    public String getComments() {
        return comments;
    }

    /**
     * Sets the comments.
     *
     * @param comments
     *            the new comments
     */
    public void setComments( final String comments ) {
        this.comments = comments;
    }

}
