package com.owners.gravitas.dto;

/**
 * The Class UserTimeZone.
 *
 * @author amits
 */
public class UserTimeZone extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7205284051404609105L;

    /** The hour offset. */

    private Integer hourOffset;

    /** The minute offset. */

    private Integer minuteOffset;

    /**
     * Gets the hour offset.
     *
     * @return the hourOffset
     */
    public Integer getHourOffset() {
        return hourOffset;
    }

    /**
     * Sets the hour offset.
     *
     * @param hourOffset
     *            the hourOffset to set
     */
    public void setHourOffset( Integer hourOffset ) {
        this.hourOffset = hourOffset;
    }

    /**
     * Gets the minute offset.
     *
     * @return the minuteOffset
     */
    public Integer getMinuteOffset() {
        return minuteOffset;
    }

    /**
     * Sets the minute offset.
     *
     * @param minuteOffset
     *            the minuteOffset to set
     */
    public void setMinuteOffset( Integer minuteOffset ) {
        this.minuteOffset = minuteOffset;
        if (this.minuteOffset == null && hourOffset != null) {
            this.minuteOffset = 0;
        }
    }

}
