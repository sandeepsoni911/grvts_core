package com.owners.gravitas.dto.request;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class CoShoppingLeadModel extends BaseRequest {

    /*** The serialVersionUID */
    private static final long serialVersionUID = 1166574902063804925L;

    @NotNull
    private String userId;
    @NotNull
    private String mlsId;
    @NotNull
    private String listingId;
    private String propertyAddress;
    private String firstName;
    private String lastName;
    @NotNull
    private String email;
    private String propertyBedroom;
    private String propertyBathroom;
    private String offerAmount;
    private String propertyPrice;
    private String question;
    private String propertyImageURL;
    private String propertyHalfBathroom;
    private String propertyTourInformation;
    @NotNull
    private String type;
    private String status;

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId( final String userId ) {
        this.userId = userId;
    }

    /**
     * @return the mlsId
     */
    public String getMlsId() {
        return mlsId;
    }

    /**
     * @param mlsId
     *            the mlsId to set
     */
    public void setMlsId( final String mlsId ) {
        this.mlsId = mlsId;
    }

    /**
     * @return the listingId
     */
    public String getListingId() {
        return listingId;
    }

    /**
     * @param listingId
     *            the listingId to set
     */
    public void setListingId( final String listingId ) {
        this.listingId = listingId;
    }

    /**
     * @return the propertyAddress
     */
    public String getPropertyAddress() {
        return propertyAddress;
    }

    /**
     * @param propertyAddress
     *            the propertyAddress to set
     */
    public void setPropertyAddress( final String propertyAddress ) {
        this.propertyAddress = propertyAddress;
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
    public void setFirstName( final String firstName ) {
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
    public void setLastName( final String lastName ) {
        this.lastName = lastName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail( final String email ) {
        this.email = email;
    }

    /**
     * @return the propertyBedroom
     */
    public String getPropertyBedroom() {
        return propertyBedroom;
    }

    /**
     * @param propertyBedroom
     *            the propertyBedroom to set
     */
    public void setPropertyBedroom( final String propertyBedroom ) {
        this.propertyBedroom = propertyBedroom;
    }

    /**
     * @return the propertyBathroom
     */
    public String getPropertyBathroom() {
        return propertyBathroom;
    }

    /**
     * @param propertyBathroom
     *            the propertyBathroom to set
     */
    public void setPropertyBathroom( final String propertyBathroom ) {
        this.propertyBathroom = propertyBathroom;
    }

    /**
     * @return the offerAmount
     */
    public String getOfferAmount() {
        return offerAmount;
    }

    /**
     * @param offerAmount
     *            the offerAmount to set
     */
    public void setOfferAmount( final String offerAmount ) {
        this.offerAmount = offerAmount;
    }

    /**
     * @return the propertyPrice
     */
    public String getPropertyPrice() {
        return propertyPrice;
    }

    /**
     * @param propertyPrice
     *            the propertyPrice to set
     */
    public void setPropertyPrice( final String propertyPrice ) {
        this.propertyPrice = propertyPrice;
    }

    /**
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question
     *            the question to set
     */
    public void setQuestion( final String question ) {
        this.question = question;
    }

    /**
     * @return the propertyImageURL
     */
    public String getPropertyImageURL() {
        return propertyImageURL;
    }

    /**
     * @param propertyImageURL
     *            the propertyImageURL to set
     */
    public void setPropertyImageURL( final String propertyImageURL ) {
        this.propertyImageURL = propertyImageURL;
    }

    /**
     * @return the propertyHalfBathroom
     */
    public String getPropertyHalfBathroom() {
        return propertyHalfBathroom;
    }

    /**
     * @param propertyHalfBathroom
     *            the propertyHalfBathroom to set
     */
    public void setPropertyHalfBathroom( final String propertyHalfBathroom ) {
        this.propertyHalfBathroom = propertyHalfBathroom;
    }

    /**
     * @return the propertyTourInformation
     */
    public String getPropertyTourInformation() {
        return propertyTourInformation;
    }

    /**
     * @param propertyTourInformation
     *            the propertyTourInformation to set
     */
    public void setPropertyTourInformation( final String propertyTourInformation ) {
        this.propertyTourInformation = propertyTourInformation;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType( final String type ) {
        this.type = type;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus( final String status ) {
        this.status = status;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "CoShoppingLeadModel [userId=" ).append( userId ).append( ", mlsId=" ).append( mlsId )
                .append( ", listingId=" ).append( listingId ).append( ", propertyAddress=" ).append( propertyAddress )
                .append( ", firstName=" ).append( firstName ).append( ", lastName=" ).append( lastName )
                .append( ", email=" ).append( email ).append( ", propertyBedroom=" ).append( propertyBedroom )
                .append( ", propertyBathroom=" ).append( propertyBathroom ).append( ", offerAmount=" )
                .append( offerAmount ).append( ", propertyPrice=" ).append( propertyPrice ).append( ", question=" )
                .append( question ).append( ", propertyImageURL=" ).append( propertyImageURL )
                .append( ", propertyHalfBathroom=" ).append( propertyHalfBathroom )
                .append( ", propertyTourInformation=" ).append( propertyTourInformation ).append( ", type=" )
                .append( type ).append( "]" );
        return sb.toString();
    }

}
