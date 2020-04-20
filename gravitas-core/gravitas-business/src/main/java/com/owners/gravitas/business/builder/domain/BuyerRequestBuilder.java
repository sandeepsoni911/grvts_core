package com.owners.gravitas.business.builder.domain;

import static com.owners.gravitas.constants.Constants.BLANK_SPACE;
import static com.owners.gravitas.constants.Constants.COLON;
import static com.owners.gravitas.constants.Constants.COMMA;
import static com.owners.gravitas.constants.Constants.GRAVITAS;
import static com.owners.gravitas.constants.Constants.HYPHEN;
import static com.owners.gravitas.constants.Constants.PIPE;
import static com.owners.gravitas.enums.ErrorCode.INVALID_PROPERTY_TOUR_INFORMATION;
import static com.owners.gravitas.enums.TourSlotTiming.SPECIFIC_TIME;
import static com.owners.gravitas.enums.TourSlotTiming.TIME_RANGE;
import static com.owners.gravitas.util.DateUtil.DEFAULT_OWNERS_DATE_PATTERN;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.config.LeadBusinessConfig;
import com.owners.gravitas.domain.Request;
import com.owners.gravitas.domain.entity.StateTimeZone;
import com.owners.gravitas.dto.TourDetails;
import com.owners.gravitas.dto.TourRange;
import com.owners.gravitas.dto.UserTimeZone;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.enums.RequestType;
import com.owners.gravitas.enums.TourSlotTiming;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.StateTimeZoneService;
import com.owners.gravitas.util.DateUtil;

/**
 * The Class BuyerRequestBuilder.
 *
 * @author harshads
 */
@Component( "buyerRequestBuilder" )
public class BuyerRequestBuilder extends AbstractBuilder< LeadRequest, Request > {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( BuyerRequestBuilder.class );

    /** The Constant EST_TIME_OFFSET. */
    private static final int EST_TIME_OFFSET = -5;
    /** The time zone service. */
    @Autowired
    private StateTimeZoneService stateTimeZoneService;
    
    @Autowired
    LeadBusinessConfig leadBusinessConfig;

    /**
     * convert {@link LeadRequest} to {@link Request}.
     *
     * @param source
     *            the source
     * @param destination
     *            the destination
     * @return the request
     */
    @Override
    public Request convertTo( final LeadRequest source, final Request destination ) {
        Request request = destination;
        if (source != null) {
            if (request == null) {
                request = new Request();
            }
            if (source.getRequestType() != null) {
                LOGGER.debug( "Buyer request of type : " + source.getRequestType() );
                switch ( source.getRequestType() ) {
                    case "MAKE_OFFER":
                        setMakeOfferParams( source, request );
                        break;
                    case "SCHEDULE_TOUR":
                        setScheduleTourParams( source, request );
                        break;
                    case "REQUEST_INFORMATION":
                        request.setType( RequestType.INQUIRY );
                        request.setLeadMessage( source.getMessage() );
                        break;
                    default:
                        request = null;
                }
                if (request != null) {
                    setCommonParams( source, request );
                }
            }
        }
        return request;
    }

    /** Method is not supported. */
    @Override
    public LeadRequest convertFrom( Request source, LeadRequest destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }

    /**
     * Sets the common params.
     *
     * @param source
     *            the source
     * @param request
     *            the request
     */
    private void setCommonParams( final LeadRequest source, final Request request ) {
        request.setConverted( Boolean.FALSE );
        request.setListingId( source.getListingId() );
        request.setOpportunityNotes( source.getComments() );
        request.setLastModifiedDtm( new Date().getTime() );
        request.setCreatedBy( GRAVITAS );
        request.setCreatedDtm( new Date().getTime() );
    }

    /**
     * Sets the make offer params.
     *
     * @param source
     *            the source
     * @param request
     *            the request
     */
    private void setMakeOfferParams( final LeadRequest source, final Request request ) {
        request.setType( RequestType.BUYER_OFFER );
        request.setPreApprovaedForMortgage( source.getPreApprovedForMortgage() );
        request.setOfferAmount( StringUtils.isBlank( source.getOfferAmount() ) ? null
                : BigDecimal.valueOf( Double.valueOf( source.getOfferAmount() ) ) );
        request.setEarnestMoneyDeposit( source.getEarnestMoneyDeposit() );
        request.setPurchaseMethod( source.getPurchaseMethod() );
        request.setDownPayment( source.getDownPayment() );
    }

    /**
     * Sets the schedule tour params.
     *
     * @param source
     *            the source
     * @param request
     *            the request
     */
    private void setScheduleTourParams( final LeadRequest source, final Request request ) {
        request.setType( RequestType.APPOINTMENT );
        request.setPropertyTourInfo( source.getPropertyTourInformation() );
        request.setDates( getTourDetails( source ) );
    }

    /**
     * Gets the tour details.
     *
     * @param source
     *            the source
     * @return the tour dates
     */
    private List< List< TourDetails > > getTourDetails( final LeadRequest source ) {
        List< List< TourDetails > > tourDates = null;
        try {
            final DateTimeZone userTimeZone = getUserTimeZone( source.getUserTimeZone(), source.getState() );
            tourDates = getTourDates( source.getPropertyTourInformation(), userTimeZone );
        } catch ( Exception e ) {
            LOGGER.error(
                    "Problem with parsing property tour information. Lead email was : {}, & propertyTourInformation came in : {}",
                    source.getEmail(), source.getPropertyTourInformation(), e);
            throw new ApplicationException(
                    "Problem with parsing property tour information. Lead email was : " + source.getEmail()
                            + " & propertyTourInformation came in was : " + source.getPropertyTourInformation(),
                    e, INVALID_PROPERTY_TOUR_INFORMATION );
        }
        return tourDates;
    }

    /**
     * Gets the user time zone.
     *
     * @param userTimeZone
     *            the user time zone
     * @param state
     *            the state
     * @return the user time zone
     */
    private DateTimeZone getUserTimeZone( final UserTimeZone userTimeZone, final String state ) {
        DateTimeZone timeZone = null;
        if (null != userTimeZone && null != userTimeZone.getHourOffset()) {
            timeZone = DateTimeZone.forOffsetHoursMinutes( userTimeZone.getHourOffset(),
                    userTimeZone.getMinuteOffset() );
            LOGGER.debug( "UserTimeZone " + userTimeZone + " based on offsets : " + timeZone );
        }
        if (null == timeZone) {
            timeZone = getUserTimeZoneByState( state );
        }
        return timeZone;
    }

    /**
     * Gets the tour dates.
     * eg of propertyTourInformation - 11/04/2016:[12:00 PM, 1:00 PM, 2:00 PM,
     * 3:00 PM, 4:00 PM, 5:00 PM]|11/03/2016:[7:00 PM, 8:00 PM]|10/18/2016:[ALL
     * AFTERNOON/EVENING]|10/18/2016:[ALL AFTERNOON]|05/25/2018:[10:00 AM]|
     * 05/25/2018:[ASAP]|05/25/2018:[ANY TIME]
     * @param propertyTourInformation
     *            the property tour information
     * @param userTimeZone
     * @return the tour dates
     */
    private List< List< TourDetails > > getTourDates( final String propertyTourInformation,
            final DateTimeZone userTimeZone ) {
        List< List< TourDetails > > tourDates = new ArrayList<>();
        if (StringUtils.isNotBlank( propertyTourInformation )) {
            final StringTokenizer dayTokenizer = new StringTokenizer( propertyTourInformation, PIPE );
            while ( dayTokenizer.hasMoreTokens() ) {
                List< TourDetails > tourDetailsList = new ArrayList<>();
                final String daySchedule = dayTokenizer.nextToken();
                DateTime tourDay = getSelectedDay( daySchedule, userTimeZone );
                final String timeOptions = getSelectedTimeOptions( daySchedule );
                final StringTokenizer timeTokenizer = new StringTokenizer( timeOptions, COMMA );
                while ( timeTokenizer.hasMoreTokens() ) {
                    String time = timeTokenizer.nextToken();
                    addTourTime( time, tourDay, tourDetailsList, userTimeZone );
                }
                tourDates.add( tourDetailsList );
            }

        }
        return tourDates;
    }

    /**
     * Adds the tour time.
     *
     * @param time
     *            the time
     * @param tourDay
     *            the tour day
     * @param tourDetailsList
     *            the tour times
     * @param userTimeZone 
     */
    private void addTourTime( String time, DateTime tourDay, List< TourDetails > tourDetailsList, DateTimeZone userTimeZone ) {
        if (StringUtils.isNotBlank( time )) {
            LOGGER.debug( "Tour time : " + time.toLowerCase().trim() );
            TourDetails tourDetails = new TourDetails();
            tourDetails.setValue( tourDay.toString() );
            switch ( time.toLowerCase().trim() ) {
                case "afternoon/evening":
                    tourDetails.setType( TourSlotTiming.ALL_AFTERNOON.toString() );
                    addTourTime( "evening", tourDay, tourDetailsList, userTimeZone );
                    break;
                case "evening":
                    tourDetails.setType( TourSlotTiming.ALL_EVENING.toString() );
                    break;
                case "anytime":
                    tourDetails.setType( TourSlotTiming.ANY_TIME.toString() );
                    break;
                case "morning":
                    tourDetails.setType( TourSlotTiming.ALL_MORNING.toString() );
                    break;
                case "asap":
                    if (leadBusinessConfig.isBuyerTourAsapEnabled()) {
                        String dateTime = DateUtil.getAsapDateTimeDetails(tourDay, leadBusinessConfig.getBuyerTourOffsetHour(),
                                userTimeZone);
                        time = dateTime.substring(dateTime.indexOf(COLON) + 1, dateTime.length());
                        DateTime newTourDay = getSelectedDay( dateTime, userTimeZone );
                        tourDetails = addSpecificTime(tourDetailsList, time, newTourDay);
                    } 
                    break;
                default:
                    tourDetails = addSpecificTime( tourDetailsList, time, tourDay );
                    break;
            }
            if (StringUtils.isNotBlank(tourDetails.getType())) {
                tourDetailsList.add(tourDetails);
            }
        }
    }

    /**
     * Gets the selected time options.
     *
     * @param daySchedule
     *            the day schedule
     * @return the selected time options
     */
    private String getSelectedTimeOptions( final String daySchedule ) {
        String timingString = null;
        if (StringUtils.isNotBlank( daySchedule )) {
            int colonIndex = daySchedule.indexOf( COLON );
            // required to skip staring square bracket
            final int timesStartIndex = colonIndex + 2;
            // required to cut ending square bracket`
            final int timesEndIndex = daySchedule.length() - 1;
            timingString = daySchedule.substring( timesStartIndex, timesEndIndex );
        }
        return timingString;
    }

    /**
     * Gets the selected day.
     *
     * @param daySchedule
     *            the day schedule
     * @return the selected day
     */
    private DateTime getSelectedDay( final String daySchedule, DateTimeZone userTimeZone ) {
        String day = null;
        if (StringUtils.isNotBlank( daySchedule )) {
            int colonIndex = daySchedule.indexOf( COLON );
            // getting day of schedule.
            day = daySchedule.substring( 0, colonIndex );
        }
        return DateUtil.toDateTime( day, DEFAULT_OWNERS_DATE_PATTERN ).withZoneRetainFields( userTimeZone );
    }

    /**
     * Adds the tour time.
     *
     * @param tourDetailsList
     *            the dates
     * @param time
     *            the time
     * @param tourTime
     *            the tour time
     * @return the tour details
     */
    private TourDetails addSpecificTime( List< TourDetails > tourDetailsList, String time, DateTime tourTime ) {
        TourDetails tourDetails = new TourDetails<>();
        String type = SPECIFIC_TIME.toString();
        if (time.contains( HYPHEN )) {
            type = TIME_RANGE.toString();
            tourDetails = getTimeRangedTourDetails( time, tourTime );
        } else {
            List< String > tourTimes = new ArrayList<>();
            final String tourTimeStr = setTourHourAndMinutes( tourTime, time );
            tourTimes.add( tourTimeStr );
            if (!tourDetailsList.isEmpty()) {
                for ( Iterator iterator = tourDetailsList.iterator(); iterator.hasNext(); ) {
                    TourDetails specifiedTimeTour = ( TourDetails ) iterator.next();
                    if (specifiedTimeTour.getType().equals( SPECIFIC_TIME.toString() )) {
                        List< String > existingTimings = ( List< String > ) specifiedTimeTour.getValue();
                        tourTimes.addAll( existingTimings );
                        iterator.remove();
                        break;
                    }
                }
            }
            tourDetails.setValue( tourTimes );
        }
        tourDetails.setType( type );
        return tourDetails;
    }

    /**
     * Gets the time ranged tour details.
     *
     * @param time
     *            the time
     * @param tourTime
     *            the tour time
     * @return the time ranged tour details
     */
    private TourDetails getTimeRangedTourDetails( String time, DateTime tourTime ) {
        TourDetails tourDetails = new TourDetails<>();
        final String suggestedTimes[] = time.split( HYPHEN );
        final List< String > tourTimes = new ArrayList<>();
        for ( final String option : suggestedTimes ) {
            tourTimes.add( setTourHourAndMinutes( tourTime, option ) );
        }
        if (tourTimes.size() >= 2) {
            final TourRange tourRange = new TourRange();
            tourRange.setStart( tourTimes.get( 0 ) );
            tourRange.setEnd( tourTimes.get( 1 ) );
            tourDetails.setValue( tourRange );
        }
        return tourDetails;
    }

    /**
     * Sets the tour hour and minutes.
     *
     * @param tourTime
     *            the tour time
     * @param time
     *            the time
     * @return the date time
     */
    private String setTourHourAndMinutes( final DateTime tourTime, final String time ) {
        String[] splitTime = time.trim().split( BLANK_SPACE );
        String[] hrsMin = splitTime[0].split( COLON );
        int hour = Integer.valueOf( hrsMin[0].trim() );
        if ("PM".equalsIgnoreCase( splitTime[1].trim() ) && hour != 12) {
            hour += 12;
        }
        return tourTime.withTime( hour, Integer.valueOf( hrsMin[1].trim() ), 0, 0 ).toString();
    }

    /**
     * Gets the start your search date time for state.
     *
     * @param stateCode
     *            the state code
     * @return the start your search date time for state
     */
    private DateTimeZone getUserTimeZoneByState( final String stateCode ) {
        StateTimeZone stateTimezone = null;
        if (StringUtils.isNotEmpty( stateCode )) {
            stateTimezone = stateTimeZoneService.getStateTimeZone( stateCode );
        }
        Integer offsetHour = null;
        if (stateTimezone != null && stateTimezone.getOffSetHour() != null) {
            offsetHour = stateTimezone.getOffSetHour();
            LOGGER.debug( "Offset hours for  " + stateCode + " state : " + offsetHour );
        } else {
            offsetHour = EST_TIME_OFFSET;
            LOGGER.debug( "Defaulst offset hours : " + offsetHour );
        }
        return DateTimeZone.forOffsetHours( offsetHour );
    }
}
