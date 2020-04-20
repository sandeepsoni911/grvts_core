package com.owners.gravitas.dto.request;

import static com.owners.gravitas.constants.Constants.REGEX_CURRENCY;
import static com.owners.gravitas.constants.Constants.REGEX_STATE;
import static com.owners.gravitas.constants.Constants.REG_EXP_EMAIL;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.util.DateUtil;
import com.owners.gravitas.validators.Date;

/**
 * The Class RequestTypeLeadRequest.
 *
 * @author vishwanathm
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class RequestTypeLeadRequest {

    /** first name. */
    @Size( min = 0, max = 40, message = "error.lead.firstName.size" )
    private String firstName;

    /** last name. */
    @NotBlank( message = "error.lead.lastName.required" )
    @Size( min = 1, max = 80, message = "error.lead.lastName.size" )
    private String lastName;
    /** email. */
    @NotBlank( message = "error.lead.email.required" )
    @Email( message = "error.lead.email.format", regexp = REG_EXP_EMAIL )
    @Size( min = 1, max = 80, message = "error.lead.email.size" )
    private String email;

    /** phone number. */
    @Size( min = 0, max = 40, message = "error.lead.phone.size" )
    private String phone;

    /** The comments. */
    @Size( min = 0, max = 2048, message = "error.lead.comments.size" )
    private String comments;

    /** The source. */
    @NotBlank( message = "error.lead.source.required" )
    @Size( min = 1, max = 40, message = "error.lead.source.size" )
    private String source;

    /** The lead source url. */
    @NotBlank( message = "error.lead.leadSourceUrl.required" )
    @URL( message = "error.lead.leadSourceUrl.format" )
    @Size( min = 3, max = 255, message = "error.lead.leadSourceUrl.size" )
    private String leadSourceUrl;

    /** The state. */
    @Pattern( regexp = REGEX_STATE, message = "error.lead.state.format" )
    private String state;

    /** The property address. */
    @Size( min = 0, max = 255, message = "error.lead.propertyAddress.size" )
    private String propertyAddress;

    /** The additional property data. */
    @Size( min = 0, max = 2048, message = "error.lead.additionalPropertyData.size" )
    private String additionalPropertyData;

    /** The owners visitor id. */
    @Size( min = 0, max = 50, message = "error.lead.ownersVisitorId.size" )
    private String ownersVisitorId;

    /** The website session data. */
    @Size( min = 0, max = 2048, message = "error.lead.websiteSessionData.size" )
    private String websiteSessionData;

    /** The last visit date time. */
    @Date( format = DateUtil.DATE_TIME_PATTERN, message = "error.lead.lastVisitDateTime.format" )
    private String lastVisitDateTime;

    /** The median price. */
    @Size( min = 0, max = 19, message = "error.lead.medianPrice.size" )
    @Pattern( regexp = REGEX_CURRENCY, message = "error.lead.medianPrice.format" )
    private String medianPrice;

    /** The google analytics campaign. */
    @Size( min = 0, max = 255, message = "error.lead.googleAnalyticsCampaign.size" )
    private String googleAnalyticsCampaign;

    /** The google analytics content. */
    @Size( min = 0, max = 255, message = "error.lead.googleAnalyticsContent.size" )
    private String googleAnalyticsContent;

    /** The google analytics medium. */
    @Size( min = 0, max = 255, message = "error.lead.googleAnalyticsMedium.size" )
    private String googleAnalyticsMedium;

    /** The google analytics source. */
    @Size( min = 0, max = 255, message = "error.lead.googleAnalyticsSource.size" )
    private String googleAnalyticsSource;

    /** The google analytics term. */
    @Size( min = 0, max = 255, message = "error.lead.googleAnalyticsTerm.size" )
    private String googleAnalyticsTerm;

    /** The owns home. */
    private boolean ownsHome;

    /** The mls id. */
    @Size( min = 0, max = 30, message = "error.lead.mlsId.size" )
    private String mlsId;

    /** The listing id. */
    @Size( min = 0, max = 60, message = "error.lead.listingId.size" )
    private String listingId;

    /** The al id. */
    @Size( min = 0, max = 30, message = "error.lead.alId.size" )
    private String alId;

    /**
     * Instantiates a new lead request basic.
     */
    public RequestTypeLeadRequest() {
        super();
    }

    /**
     * Gets the lead type.
     *
     * @return the lead type
     */
    public String getLeadType() {
        return RecordType.BUYER.toString();
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
     * Gets the source.
     *
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the source.
     *
     * @param source
     *            the new source
     */
    public void setSource( final String source ) {
        this.source = source;
    }

    /**
     * Gets the lead source url.
     *
     * @return the lead source url
     */
    public String getLeadSourceUrl() {
        return leadSourceUrl;
    }

    /**
     * Sets the lead source url.
     *
     * @param leadSourceUrl
     *            the new lead source url
     */
    public void setLeadSourceUrl( final String leadSourceUrl ) {
        this.leadSourceUrl = leadSourceUrl;
    }

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
     *            the new phone
     */
    public void setPhone( final String phone ) {
        this.phone = phone;
    }

    /**
     * Gets the comments.
     *
     * @return the comments
     */
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

    /**
     * Gets the state.
     *
     * @return the state
     */
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
     * Gets the property address.
     *
     * @return the property address
     */
    public String getPropertyAddress() {
        return propertyAddress;
    }

    /**
     * Sets the property address.
     *
     * @param propertyAddress
     *            the new property address
     */
    public void setPropertyAddress( final String propertyAddress ) {
        this.propertyAddress = propertyAddress;
    }

    /**
     * Gets the additional property data.
     *
     * @return the additional property data
     */
    public String getAdditionalPropertyData() {
        return additionalPropertyData;
    }

    /**
     * Sets the additional property data.
     *
     * @param additionalPropertyData
     *            the new additional property data
     */
    public void setAdditionalPropertyData( final String additionalPropertyData ) {
        this.additionalPropertyData = additionalPropertyData;
    }

    /**
     * Gets the owners visitor id.
     *
     * @return the owners visitor id
     */
    public String getOwnersVisitorId() {
        return ownersVisitorId;
    }

    /**
     * Sets the owners visitor id.
     *
     * @param ownersVisitorId
     *            the new owners visitor id
     */
    public void setOwnersVisitorId( final String ownersVisitorId ) {
        this.ownersVisitorId = ownersVisitorId;
    }

    /**
     * Gets the website session data.
     *
     * @return the website session data
     */
    public String getWebsiteSessionData() {
        return websiteSessionData;
    }

    /**
     * Sets the website session data.
     *
     * @param websiteSessionData
     *            the new website session data
     */
    public void setWebsiteSessionData( final String websiteSessionData ) {
        this.websiteSessionData = websiteSessionData;
    }

    /**
     * Gets the last visit date time.
     *
     * @return the last visit date time
     */
    public String getLastVisitDateTime() {
        return lastVisitDateTime;
    }

    /**
     * Sets the last visit date time.
     *
     * @param lastVisitDateTime
     *            the new last visit date time
     */
    public void setLastVisitDateTime( final String lastVisitDateTime ) {
        this.lastVisitDateTime = lastVisitDateTime;
    }

    /**
     * Gets the median price.
     *
     * @return the median price
     */
    public String getMedianPrice() {
        return medianPrice;
    }

    /**
     * Sets the median price.
     *
     * @param medianPrice
     *            the new median price
     */
    public void setMedianPrice( final String medianPrice ) {
        this.medianPrice = medianPrice;
    }

    /**
     * Gets the google analytics campaign.
     *
     * @return the google analytics campaign
     */
    public String getGoogleAnalyticsCampaign() {
        return googleAnalyticsCampaign;
    }

    /**
     * Sets the google analytics campaign.
     *
     * @param googleAnalyticsCampaign
     *            the new google analytics campaign
     */
    public void setGoogleAnalyticsCampaign( final String googleAnalyticsCampaign ) {
        this.googleAnalyticsCampaign = googleAnalyticsCampaign;
    }

    /**
     * Gets the google analytics content.
     *
     * @return the google analytics content
     */
    public String getGoogleAnalyticsContent() {
        return googleAnalyticsContent;
    }

    /**
     * Sets the google analytics content.
     *
     * @param googleAnalyticsContent
     *            the new google analytics content
     */
    public void setGoogleAnalyticsContent( final String googleAnalyticsContent ) {
        this.googleAnalyticsContent = googleAnalyticsContent;
    }

    /**
     * Gets the google analytics medium.
     *
     * @return the google analytics medium
     */
    public String getGoogleAnalyticsMedium() {
        return googleAnalyticsMedium;
    }

    /**
     * Sets the google analytics medium.
     *
     * @param googleAnalyticsMedium
     *            the new google analytics medium
     */
    public void setGoogleAnalyticsMedium( final String googleAnalyticsMedium ) {
        this.googleAnalyticsMedium = googleAnalyticsMedium;
    }

    /**
     * Gets the google analytics source.
     *
     * @return the google analytics source
     */
    public String getGoogleAnalyticsSource() {
        return googleAnalyticsSource;
    }

    /**
     * Sets the google analytics source.
     *
     * @param googleAnalyticsSource
     *            the new google analytics source
     */
    public void setGoogleAnalyticsSource( final String googleAnalyticsSource ) {
        this.googleAnalyticsSource = googleAnalyticsSource;
    }

    /**
     * Gets the google analytics term.
     *
     * @return the google analytics term
     */
    public String getGoogleAnalyticsTerm() {
        return googleAnalyticsTerm;
    }

    /**
     * Sets the google analytics term.
     *
     * @param googleAnalyticsTerm
     *            the new google analytics term
     */
    public void setGoogleAnalyticsTerm( final String googleAnalyticsTerm ) {
        this.googleAnalyticsTerm = googleAnalyticsTerm;
    }

    /**
     * Checks if is owns home.
     *
     * @return true, if is owns home
     */
    public boolean isOwnsHome() {
        return ownsHome;
    }

    /**
     * Sets the owns home.
     *
     * @param ownsHome
     *            the new owns home
     */
    public void setOwnsHome( final boolean ownsHome ) {
        this.ownsHome = ownsHome;
    }

    /**
     * Gets the mls id.
     *
     * @return the mls id
     */
    public String getMlsId() {
        return mlsId;
    }

    /**
     * Sets the mls id.
     *
     * @param mlsId
     *            the new mls id
     */
    public void setMlsId( final String mlsId ) {
        this.mlsId = mlsId;
    }

    /**
     * Gets the listing id.
     *
     * @return the listing id
     */
    public String getListingId() {
        return listingId;
    }

    /**
     * Sets the listing id.
     *
     * @param listingId
     *            the new listing id
     */
    public void setListingId( final String listingId ) {
        this.listingId = listingId;
    }

    /**
     * Gets the al id.
     *
     * @return the al id
     */
    public String getAlId() {
        return alId;
    }

    /**
     * Sets the al id.
     *
     * @param alId
     *            the new al id
     */
    public void setAlId( final String alId ) {
        this.alId = alId;
    }
}
