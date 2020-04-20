package com.owners.gravitas.dto.response;

public class CheckScheduleMeetingValidationResponse extends BaseResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 3824396293989711326L;

    private boolean isValidationsPassed;

    private boolean isScheduleTourMeetingEnabled;

    /**
     * @return the isValidationsPassed
     */
    public boolean isValidationsPassed() {
        return isValidationsPassed;
    }

    /**
     * @param isValidationsPassed
     *            the isValidationsPassed to set
     */
    public void setValidationsPassed( final boolean isValidationsPassed ) {
        this.isValidationsPassed = isValidationsPassed;
    }

    /**
     * @return the isScheduleTourMeetingEnabled
     */
    public boolean isScheduleTourMeetingEnabled() {
        return isScheduleTourMeetingEnabled;
    }

    /**
     * @param isScheduleTourMeetingEnabled
     *            the isScheduleTourMeetingEnabled to set
     */
    public void setScheduleTourMeetingEnabled( final boolean isScheduleTourMeetingEnabled ) {
        this.isScheduleTourMeetingEnabled = isScheduleTourMeetingEnabled;
    }

}
