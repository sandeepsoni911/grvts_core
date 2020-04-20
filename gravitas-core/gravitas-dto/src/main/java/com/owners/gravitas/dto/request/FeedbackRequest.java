package com.owners.gravitas.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class FeedbackRequest.
 * 
 * @author ankusht
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class FeedbackRequest {

    /** The rating id. */
    @Size( min = 36, max = 36, message = "error.feedback.ratind.id.required" )
    private String ratingId;

    /** The rating. */
    @Min( value = 1, message = "error.feedback.rating.size" )
    @Max( value = 5, message = "error.feedback.rating.size" )
    private int rating;

    /** The feedback. */
    @Size( min = 0, max = 200, message = "error.feedback.rating.feedback.size" )
    private String feedback;

    /** The comments. */
    @Size( min = 0, max = 255, message = "error.feedback.rating.comments.size" )
    private String comments;

    /**
     * Gets the rating id.
     *
     * @return the rating id
     */
    public String getRatingId() {
        return ratingId;
    }

    /**
     * Sets the rating id.
     *
     * @param ratingId
     *            the new rating id
     */
    public void setRatingId( final String ratingId ) {
        this.ratingId = ratingId;
    }

    /**
     * Gets the rating.
     *
     * @return the rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the rating.
     *
     * @param rating
     *            the new rating
     */
    public void setRating( final int rating ) {
        this.rating = rating;
    }

    /**
     * Gets the feedback.
     *
     * @return the feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Sets the feedback.
     *
     * @param feedback
     *            the new feedback
     */
    public void setFeedback( final String feedback ) {
        this.feedback = feedback;
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
}
