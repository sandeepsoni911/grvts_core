package com.owners.gravitas.validator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.owners.gravitas.dto.request.ScheduleTourLeadRequest;
import com.owners.gravitas.dto.request.ScheduleTourMeetingRequest;
import com.owners.gravitas.exception.InvalidArgumentException;

/**
 * Class ScheduleTourLeadValidator to validate the input schedule tour request
 * 
 * @author imranmoh
 *
 */
@Component
public class ScheduleTourLeadValidator {

    /***
     * Method to validate the ScheduleTourMeetingRequest
     * 
     * @param meetingRequest
     */
    public void validateScheduleTourLeadRequest( final ScheduleTourMeetingRequest meetingRequest ) {
        final List< ScheduleTourLeadRequest > requestList = meetingRequest.getMeetingRequest();
        final List< String > errorList = new ArrayList<>();
        for ( final ScheduleTourLeadRequest scheduleTourLeadRequest : requestList ) {

            validateInputField( scheduleTourLeadRequest.getListingId(),
                    "error.scheduleTourMeetingRequest.meetingRequest.listingId", errorList );
            validateInputField( scheduleTourLeadRequest.getRequestType(),
                    "error.scheduleTourMeetingRequest.meetingRequest.requestType", errorList );
            validateInputField( scheduleTourLeadRequest.getDueDtm(),
                    "error.scheduleTourMeetingRequest.meetingRequest.dueDtm", errorList );
            validateInputField( scheduleTourLeadRequest.getCoShoppingId(),
                    "error.scheduleTourMeetingRequest.meetingRequest.coShoppingId", errorList );
            validateInputField( scheduleTourLeadRequest.getTitle(),
                    "error.scheduleTourMeetingRequest.meetingRequest.title", errorList );
            validateInputField( scheduleTourLeadRequest.getUserId(),
                    "error.scheduleTourMeetingRequest.meetingRequest.userId", errorList );
            validateInputField( scheduleTourLeadRequest.getMlsId(),
                    "error.scheduleTourMeetingRequest.meetingRequest.mlsId", errorList );
            validateInputField( scheduleTourLeadRequest.getTimezone(),
                    "error.scheduleTourMeetingRequest.meetingRequest.timeZone", errorList );
            if(null==scheduleTourLeadRequest.getIsWarmTransferCall()) {
                errorList.add( "error.meeting.warmTransfer.required" );
            }

        }
        if (!errorList.isEmpty()) {
            throw new InvalidArgumentException( "Invalid schedule tour request ", errorList );
        }
    }

    /**
     * Validate null and blank fields and populate with given error code
     * 
     * @param field
     * @param errorCode
     * @param errorList
     */
    private void validateInputField( final String field, final String errorCode, final List< String > errorList ) {
        if (StringUtils.isBlank( field )) {
            errorList.add( errorCode );
        }
    }
}
