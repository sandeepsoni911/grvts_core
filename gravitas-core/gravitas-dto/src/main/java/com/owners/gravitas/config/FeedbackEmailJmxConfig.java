package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class FeedbackEmailJmxConfig.
 * 
 * @author ankusht
 */
@Component
@ManagedResource( objectName = "com.owners.gravitas:name=FeedbackEmailJmxConfig" )
public class FeedbackEmailJmxConfig {

    /** The Constant RATING. */
    public static final String RATING = "rating";

    /** The Constant RATING_ID. */
    public static final String RATING_ID = "ratingId";

    /** The Constant RATING1. */
    public static final String RATING1 = "rating1";

    /** The Constant RATING2. */
    public static final String RATING2 = "rating2";

    /** The Constant RATING3. */
    public static final String RATING3 = "rating3";

    /** The Constant RATING4. */
    public static final String RATING4 = "rating4";

    /** The Constant RATING5. */
    public static final String RATING5 = "rating5";

    /** The claimed stage feedback enabled. */
    @Value( "${feedback.email.claimed.stage.enabled: false}" )
    private boolean claimedStgFeedbackEnabled;

    /** The in contact stage feedback enabled. */
    @Value( "${feedback.email.incontact.stage.enabled: false}" )
    private boolean inContactStgFeedbackEnabled;

    /** The securing financing stage feedback enabled. */
    @Value( "${feedback.email.securing.financing.stage.enabled: false}" )
    private boolean securingFinancingStgFeedbackEnabled;

    /** The face to face mtg stage feedback enabled. */
    @Value( "${feedback.email.face.to.face.mtg.stage.enabled: false}" )
    private boolean faceToFaceMtgStgFeedbackEnabled;

    /** The showing homes stage feedback enabled. */
    @Value( "${feedback.email.showing.homes.stage.enabled: false}" )
    private boolean showingHomesStgFeedbackEnabled;

    /** The writing offer stage feedback enabled. */
    @Value( "${feedback.email.writing.offer.stage.enabled: false}" )
    private boolean writingOfferStgFeedbackEnabled;

    /** The sold stage feedback enabled. */
    @Value( "${feedback.email.sold.stage.enabled: false}" )
    private boolean soldStgFeedbackEnabled;

    /** The feedback email expiry period. */
    @Value( "${feedback.email.link.expiry.minutes: 21600}" )
    private int feedbackLinkExpiryInMinutes;

    /** The max allowed feedback attempts. */
    @Value( "${feedback.email.max.allowed.attempts: 5}" )
    private int maxAllowedFeedbackAttempts;

    /** The feedback from email. */
    @Value( "${feedback.email.from}" )
    private String feedbackFromEmail;

    /** The feedback email reply to. */
    @Value( "${no.reply.owners.email.id}" )
    private String noReplyOwnersEmailAddress;

    /** The feedback host url. */
    @Value( "${feedback.email.host.url}" )
    private String feedbackHostUrl;

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /**
     * Gets the claimed stg feedback enabled.
     *
     * @return the claimed stg feedback enabled
     */
    @ManagedAttribute
    public boolean getClaimedStgFeedbackEnabled() {
        return claimedStgFeedbackEnabled;
    }

    /**
     * Sets the claimed stg feedback enabled.
     *
     * @param claimedStgFeedbackEnabled
     *            the new claimed stg feedback enabled
     */
    @ManagedAttribute
    public void setClaimedStgFeedbackEnabled( final boolean claimedStgFeedbackEnabled ) {
        this.claimedStgFeedbackEnabled = claimedStgFeedbackEnabled;
        propertyWriter.saveJmxProperty( "feedback.email.claimed.stage.enabled", claimedStgFeedbackEnabled );
    }

    /**
     * Gets the in contact stg feedback enabled.
     *
     * @return the in contact stg feedback enabled
     */
    @ManagedAttribute
    public boolean getInContactStgFeedbackEnabled() {
        return inContactStgFeedbackEnabled;
    }

    /**
     * Sets the in contact stg feedback enabled.
     *
     * @param inContactStgFeedbackEnabled
     *            the new in contact stg feedback enabled
     */
    @ManagedAttribute
    public void setInContactStgFeedbackEnabled( final boolean inContactStgFeedbackEnabled ) {
        this.inContactStgFeedbackEnabled = inContactStgFeedbackEnabled;
        propertyWriter.saveJmxProperty( "feedback.email.incontact.stage.enabled", inContactStgFeedbackEnabled );
    }

    /**
     * Gets the securing financing stg feedback enabled.
     *
     * @return the securing financing stg feedback enabled
     */
    @ManagedAttribute
    public boolean getSecuringFinancingStgFeedbackEnabled() {
        return securingFinancingStgFeedbackEnabled;
    }

    /**
     * Sets the securing financing stg feedback enabled.
     *
     * @param securingFinancingStgFeedbackEnabled
     *            the new securing financing stg feedback enabled
     */
    @ManagedAttribute
    public void setSecuringFinancingStgFeedbackEnabled( final boolean securingFinancingStgFeedbackEnabled ) {
        this.securingFinancingStgFeedbackEnabled = securingFinancingStgFeedbackEnabled;
        propertyWriter.saveJmxProperty( "feedback.email.securing.financing.stage.enabled",
                securingFinancingStgFeedbackEnabled );
    }

    /**
     * Gets the face to face mtg stg feedback enabled.
     *
     * @return the face to face mtg stg feedback enabled
     */
    @ManagedAttribute
    public boolean getFaceToFaceMtgStgFeedbackEnabled() {
        return faceToFaceMtgStgFeedbackEnabled;
    }

    /**
     * Sets the face to face mtg stg feedback enabled.
     *
     * @param faceToFaceMtgStgFeedbackEnabled
     *            the new face to face mtg stg feedback enabled
     */
    @ManagedAttribute
    public void setFaceToFaceMtgStgFeedbackEnabled( final boolean faceToFaceMtgStgFeedbackEnabled ) {
        this.faceToFaceMtgStgFeedbackEnabled = faceToFaceMtgStgFeedbackEnabled;
        propertyWriter.saveJmxProperty( "feedback.email.face.to.face.mtg.stage.enabled",
                faceToFaceMtgStgFeedbackEnabled );
    }

    /**
     * Gets the showing homes stg feedback enabled.
     *
     * @return the showing homes stg feedback enabled
     */
    @ManagedAttribute
    public boolean getShowingHomesStgFeedbackEnabled() {
        return showingHomesStgFeedbackEnabled;
    }

    /**
     * Sets the showing homes stg feedback enabled.
     *
     * @param showingHomesStgFeedbackEnabled
     *            the new showing homes stg feedback enabled
     */
    @ManagedAttribute
    public void setShowingHomesStgFeedbackEnabled( final boolean showingHomesStgFeedbackEnabled ) {
        this.showingHomesStgFeedbackEnabled = showingHomesStgFeedbackEnabled;
        propertyWriter.saveJmxProperty( "feedback.email.showing.homes.stage.enabled", showingHomesStgFeedbackEnabled );
    }

    /**
     * Gets the writing offer stg feedback enabled.
     *
     * @return the writing offer stg feedback enabled
     */
    @ManagedAttribute
    public boolean getWritingOfferStgFeedbackEnabled() {
        return writingOfferStgFeedbackEnabled;
    }

    /**
     * Sets the writing offer stg feedback enabled.
     *
     * @param writingOfferStgFeedbackEnabled
     *            the new writing offer stg feedback enabled
     */
    @ManagedAttribute
    public void setWritingOfferStgFeedbackEnabled( final boolean writingOfferStgFeedbackEnabled ) {
        this.writingOfferStgFeedbackEnabled = writingOfferStgFeedbackEnabled;
        propertyWriter.saveJmxProperty( "feedback.email.writing.offer.stage.enabled", writingOfferStgFeedbackEnabled );
    }

    /**
     * Gets the sold stg feedback enabled.
     *
     * @return the sold stg feedback enabled
     */
    @ManagedAttribute
    public boolean getSoldStgFeedbackEnabled() {
        return soldStgFeedbackEnabled;
    }

    /**
     * Sets the sold stg feedback enabled.
     *
     * @param soldStgFeedbackEnabled
     *            the new sold stg feedback enabled
     */
    @ManagedAttribute
    public void setSoldStgFeedbackEnabled( final boolean soldStgFeedbackEnabled ) {
        this.soldStgFeedbackEnabled = soldStgFeedbackEnabled;
        propertyWriter.saveJmxProperty( "feedback.email.sold.stage.enabled", soldStgFeedbackEnabled );
    }

    /**
     * Gets the feedback link expiry in minutes.
     *
     * @return the feedback link expiry period
     */
    @ManagedAttribute
    public int getFeedbackLinkExpiryInMinutes() {
        return feedbackLinkExpiryInMinutes;
    }

    /**
     * Sets the feedback link expiry in minutes.
     *
     * @param feedbackLinkExpiryInMinutes
     *            the new feedback link expiry period
     */
    @ManagedAttribute
    public void setFeedbackLinkExpiryInMinutes( final int feedbackLinkExpiryPeriod ) {
        this.feedbackLinkExpiryInMinutes = feedbackLinkExpiryPeriod;
        propertyWriter.saveJmxProperty( "feedback.email.link.expiry.minutes", feedbackLinkExpiryPeriod );
    }

    /**
     * Gets the max allowed feedback attempts.
     *
     * @return the max allowed feedback attempts
     */
    @ManagedAttribute
    public int getMaxAllowedFeedbackAttempts() {
        return maxAllowedFeedbackAttempts;
    }

    /**
     * Sets the max allowed feedback attempts.
     *
     * @param maxAllowedFeedbackAttempts
     *            the new max allowed feedback attempts
     */
    @ManagedAttribute
    public void setMaxAllowedFeedbackAttempts( final int maxAllowedFeedbackAttempts ) {
        this.maxAllowedFeedbackAttempts = maxAllowedFeedbackAttempts;
        propertyWriter.saveJmxProperty( "feedback.email.max.allowed.attempts", maxAllowedFeedbackAttempts );
    }

    /**
     * Gets the feedback from email.
     *
     * @return the feedback from email
     */
    public String getFeedbackFromEmail() {
        return feedbackFromEmail;
    }

    /**
     * Sets the feedback from email.
     *
     * @param feedbackFromEmail
     *            the new feedback from email
     */
    public void setFeedbackFromEmail( String feedbackFromEmail ) {
        this.feedbackFromEmail = feedbackFromEmail;
    }

    /**
     * Gets the no reply owners email address.
     *
     * @return the no reply owners email address
     */
    public String getNoReplyOwnersEmailAddress() {
        return noReplyOwnersEmailAddress;
    }

    /**
     * Sets the no reply owners email address.
     *
     * @param noReplyOwnersEmailAddress
     *            the new no reply owners email address
     */
    public void setNoReplyOwnersEmailAddress( String noReplyOwnersEmailAddress ) {
        this.noReplyOwnersEmailAddress = noReplyOwnersEmailAddress;
    }

    /**
     * Gets the feedback host url.
     *
     * @return the feedback host url
     */
    public String getFeedbackHostUrl() {
        return feedbackHostUrl;
    }

    /**
     * Sets the feedback host url.
     *
     * @param feedbackHostUrl
     *            the new feedback host url
     */
    public void setFeedbackHostUrl( String feedbackHostUrl ) {
        this.feedbackHostUrl = feedbackHostUrl;
    }
}
