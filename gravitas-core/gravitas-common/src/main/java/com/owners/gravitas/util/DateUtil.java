package com.owners.gravitas.util;

import static com.owners.gravitas.constants.Constants.NA;
import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.joda.time.DateTimeZone.UTC;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a utility class for date formatting.
 */
public class DateUtil {
    
    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( DateUtil.class );

    /** The Constant CRM_DATE_FORMAT. */
    public static final String DEFAULT_CRM_DATE_PATTERN = "yyyy-MM-dd";

    /** The Constant CRM_DATE_TIME_PATTERN. */
    public static final String CRM_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** The Constant CRM_DATE_TIME_PATTERN_OFFSET. */
    public static final String CRM_DATE_TIME_PATTERN_OFFSET = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    /** The Constant ISO_DATE_FORMAT. */
    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ";

    /** The Constant CRM_DATE_TIME_PATTERN. */
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd hh:mm a";

    /** The Constant PUSH_NTFC_DATE_TIME_PATTERN. */
    public static final String PUSH_NTFC_DATE_TIME_PATTERN = "MM/dd/yyyy hh:mm a";

    /** The Constant OWNERS_DATE_PATTERN. */
    public static final String DEFAULT_OWNERS_DATE_PATTERN = "MM/dd/yyyy";

    /** The Constant DEFAULT_TOUR_DATE_OUTPUT_PATTERN. 05/25/2018:[10:00 AM] */
    public static final String DEFAULT_TOUR_DATE_OUTPUT_PATTERN = "MM/dd/yyyy:hh:mm a";
    
    /** The Constant GRAVITAS_AUDIT_DATE_PATTERN. */
    public static final String GRAVITAS_AUDIT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss  ";
    

    /** The Constant OWNERS_DATE_PATTERN. */
    public static final String DEFAULT_COSHOPPING_TOUR_DTM_PATTERN = "MM/dd/yyyy:[hh:mm a]";

    public static final String CO_SHOPPING_PROPERTY_TOUR_INFO_DATE_PATTERN = "MM/dd/yyyy:[hh:mm a]|";
    
    public static final String AGENT_DATE_TIME_FORMAT = "dd MMM yyyy hh:mm a";
    
    public static final String SCHEDULE_TOUR_DATE_TIME_FORMAT = "EEEE, MMMM dd 'at' hh:mm a";

    /** The Constant DAY. */
    private final static String DAY = " day ";

    /** The Constant HOUR. */
    private final static String HOUR = " hour ";

    /** The Constant MIN. */
    private final static String MIN = " min ";

    /** The Constant SEC. */
    private final static String SEC = " sec";

    /** The Constant DAY_S. */
    private final static String DAY_S = " days ";

    /** The Constant HOUR_S. */
    private final static String HOUR_S = " hrs ";

    /** The Constant MIN_S. */
    private final static String MIN_S = " mins ";

    /** The Constant SEC_S. */
    private final static String SEC_S = " secs";

    /** The Constant DAY_SHORT. */
    private static final String DAY_SHORT = "d ";

    /** The Constant HOUR_SHORT. */
    private static final String HOUR_SHORT = "h ";

    /** The Constant MINUTE_SHORT. */
    private static final String MINUTE_SHORT = "m ";

    /** The Constant SECOND_SHORT. */
    private static final String SECOND_SHORT = "s";

    /**
     * Instantiates a new date util.
     */
    private DateUtil() {
    }

    /**
     * This method converts joda {@link LocalDateTime} object to string in
     * provided pattern.
     *
     * @param date
     *            joda {@link LocalDateTime} object.
     * @param pattern
     *            Date pattern
     * @return formatted date string
     */
    public static String toString( final LocalDateTime date, final String pattern ) {
        String dateStr = "";
        if (date != null && StringUtils.isNotBlank( pattern )) {
            final DateTimeFormatter dtFormatter = DateTimeFormat.forPattern( pattern );
            dateStr = dtFormatter.print( date );
        }
        return dateStr;
    }

    /**
     * This method converts joda {@link DateTime} object to string in provided
     * pattern.
     *
     * @param date
     *            joda {@link DateTime} object.
     * @param pattern
     *            Date pattern
     * @return formatted date string
     */
    public static String toString( final DateTime date, final String pattern ) {
        String dateStr = "";
        if (date != null && StringUtils.isNotBlank( pattern )) {
            final DateTimeFormatter dtFormatter = DateTimeFormat.forPattern( pattern );
            dateStr = dtFormatter.print( date );
        }
        return dateStr;
    }

    /**
     * This method converts string date into joda <code>LocalDateTime</code>
     * object.
     *
     * @param dateStr
     *            is the date in string format.
     * @param pattern
     *            Date pattern
     * @return instance of {@link LocalDateTime}
     */
    public static DateTime toDateTime( final String dateStr, final String pattern ) {
        String datePattern = pattern;
        DateTime dt = null;
        if (StringUtils.isNotBlank( dateStr )) {
            if (StringUtils.isBlank( datePattern )) {
                datePattern = DEFAULT_CRM_DATE_PATTERN;
            }
            final DateTimeFormatter dtFormatter = DateTimeFormat.forPattern( datePattern );
            dt = dtFormatter.parseDateTime( dateStr.trim() );
        }
        return dt;
    }

    /**
     * Checks if is valid format.
     *
     * @param dateStr
     *            the date string
     * @param pattern
     *            the pattern
     * @return true, if is valid format
     */
    public static boolean isValidFormat( final String dateStr, final String pattern ) {
        Date date = null;
        try {
            if (StringUtils.isNotBlank( dateStr ) && StringUtils.isNotBlank( pattern )) {
                final SimpleDateFormat sdf = new SimpleDateFormat( pattern );
                date = sdf.parse( dateStr );
                if (!dateStr.equals( sdf.format( date ) )) {
                    date = null;
                }
            }
        } catch ( final ParseException ex ) {
            date = null;
        }
        return date != null;
    }

    /**
     * Gets the long date.
     *
     * @param date
     *            the date
     * @param datePattern
     *            the date pattern
     * @return the long date
     */
    public static Long getLongDate( final String date, final String datePattern ) {
        Long longValue = null;
        if (StringUtils.isNotBlank( date )) {
            String pattern = DEFAULT_CRM_DATE_PATTERN;
            if (StringUtils.isNotBlank( datePattern )) {
                pattern = datePattern;
            }
            final DateTime dateTime = toDateTime( date, pattern );
            if (null != dateTime) {
                longValue = dateTime.getMillis();
            }
        }
        return longValue;
    }

    /**
     * Gets the long date.
     *
     * @param date
     *            the date
     * @return the long date
     */
    public static Long getLongDate( final String date ) {
        return getLongDate( date, null );
    }

    /**
     * This method converts string date into <code>Date</code>
     * object.
     *
     * @param dateStr
     *            is the date in string format.
     * @param pattern
     *            Date pattern
     * @return instance of {@link Date}
     */
    public static Date toDate( final String dateStr ) {
        final DateTime date = toDateTime( dateStr, DEFAULT_CRM_DATE_PATTERN );
        if (date != null) {
            return new Date( date.getMillis() );
        }
        return null;
    }

    /**
     * To date.
     *
     * @param dateStr
     *            the date str
     * @param pattern
     *            the pattern
     * @return the date
     */
    public static Date toDate( final String dateStr, final String pattern ) {
        String datePattern = pattern;
        if (StringUtils.isBlank( datePattern )) {
            datePattern = DEFAULT_OWNERS_DATE_PATTERN;
        }
        final DateTime date = toDateTime( dateStr, datePattern );
        if (date != null) {
            return new Date( date.getMillis() );
        }
        return null;
    }

    /**
     * To sql date.
     *
     * @param dateStr
     *            the date str
     * @param pattern
     *            the pattern
     * @return the java.sql. date
     */
    public static java.sql.Date toSqlDate( final String dateStr, final String pattern ) {
        String datePattern = pattern;
        java.sql.Date sqlDate = null;
        Date date = null;
        if (isNotBlank( dateStr )) {
            if (isBlank( datePattern )) {
                datePattern = DEFAULT_OWNERS_DATE_PATTERN;
            }
            final DateFormat dtFormatter = new SimpleDateFormat( datePattern );
            try {
                date = dtFormatter.parse( dateStr );
            } catch ( final ParseException e ) {
                // do nothing
            }
            if (date != null) {
                sqlDate = new java.sql.Date( date.getTime() );
            }
        }
        return sqlDate;
    }

    /**
     * To date.
     *
     * @param millis
     *            the millis
     * @return the date time
     */
    public static DateTime toDate( final Long millis ) {
        DateTime dt = null;
        if (null != millis) {
            dt = new DateTime( millis, UTC );
        }
        return dt;
    }

    /**
     * Gets the string from date.
     *
     * @param date
     *            the date
     * @param pattern
     *            the pattern
     * @return the string from date
     */
    public static String toString( final Date date, final String pattern ) {
        String datePattern = pattern;
        if (StringUtils.isBlank( datePattern )) {
            datePattern = DEFAULT_OWNERS_DATE_PATTERN;
        }
        final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern( datePattern );
        return ( date != null ) ? dateTimeFormatter.print( date.getTime() ) : null;
    }

    /**
     * Gets the readable time.
     *
     * @param duration
     *            the duration
     * @param isSeconds
     *            the is seconds
     * @param isShortNotation
     *            the is short notation
     * @return the readable time
     */
    public static String getReadableTime( final long duration, final boolean isSeconds,
            final boolean isShortNotation ) {
        String readableTime = EMPTY;
        if (isShortNotation) {
            readableTime = getShortReadableTime( duration, isSeconds );
        } else {
            readableTime = getReadableTime( duration, isSeconds );
        }
        if (isEmpty( readableTime )) {
            readableTime = NA;
        }
        return readableTime;
    }

    /**
     * Gets the readable time.
     *
     * @param duration
     *            the duration
     * @param isSeconds
     *            the is seconds
     * @return the readable time
     */
    private static String getReadableTime( final long duration, final boolean isSeconds ) {
        final StringBuffer readableTime = new StringBuffer();
        final long days = MILLISECONDS.toDays( duration );
        final long hours = MILLISECONDS.toHours( duration ) - DAYS.toHours( MILLISECONDS.toDays( duration ) );
        final long minutes = MILLISECONDS.toMinutes( duration ) - HOURS.toMinutes( MILLISECONDS.toHours( duration ) );
        final long seconds = MILLISECONDS.toSeconds( duration )
                - TimeUnit.MINUTES.toSeconds( MILLISECONDS.toMinutes( duration ) );

        if (days == 1) {
            readableTime.append( days );
            readableTime.append( DAY );
        } else if (days > 1) {
            readableTime.append( days );
            readableTime.append( DAY_S );
        }
        if (hours == 1) {
            readableTime.append( hours );
            readableTime.append( HOUR );
        } else if (hours > 1) {
            readableTime.append( hours );
            readableTime.append( HOUR_S );
        }
        if (minutes == 1) {
            readableTime.append( minutes );
            readableTime.append( MIN );
        } else if (minutes > 1) {
            readableTime.append( minutes );
            readableTime.append( MIN_S );
        }
        if (isSeconds) {
            if (seconds == 1) {
                readableTime.append( seconds );
                readableTime.append( SEC );
            } else if (seconds > 1) {
                readableTime.append( seconds );
                readableTime.append( SEC_S );
            }
        }
        return readableTime.toString();
    }

    /**
     * Gets the short readable time.
     *
     * @param duration
     *            the duration
     * @param isSeconds
     *            the is seconds
     * @return the short readable time
     */
    private static String getShortReadableTime( final long duration, final boolean isSeconds ) {
        final StringBuffer readableTime = new StringBuffer();
        final long days = MILLISECONDS.toDays( duration );
        final long hours = MILLISECONDS.toHours( duration ) - DAYS.toHours( MILLISECONDS.toDays( duration ) );
        final long minutes = MILLISECONDS.toMinutes( duration ) - HOURS.toMinutes( MILLISECONDS.toHours( duration ) );
        final long seconds = MILLISECONDS.toSeconds( duration )
                - TimeUnit.MINUTES.toSeconds( MILLISECONDS.toMinutes( duration ) );

        if (days > 0) {
            readableTime.append( days );
            readableTime.append( DAY_SHORT );
        }
        if (hours > 0) {
            readableTime.append( hours );
            readableTime.append( HOUR_SHORT );
        }
        if (minutes > 0) {
            readableTime.append( minutes );
            readableTime.append( MINUTE_SHORT );
        }
        if (isSeconds) {
            if (seconds > 0) {
                readableTime.append( seconds );
                readableTime.append( SECOND_SHORT );
            }
        }
        return readableTime.toString();
    }
    
    public static String getAsapDateTimeDetails(DateTime dt, final int additionalHours, final DateTimeZone userTimeZone) {
        final DateTime currentDateTime = new DateTime(userTimeZone);
        dt = dt.withHourOfDay( currentDateTime.getHourOfDay() ).plusHours(additionalHours);
        dt = dt.minuteOfHour().setCopy(0);
        final String asapDateTimeDetails = toString( dt, DEFAULT_TOUR_DATE_OUTPUT_PATTERN );
        return asapDateTimeDetails;
    }

    /**
     *
     * @return
     */
    public static Long getStartOfDayLongTime() {
        final Calendar c = Calendar.getInstance();
        c.set( Calendar.HOUR_OF_DAY, 0 );
        c.set( Calendar.MINUTE, 0 );
        c.set( Calendar.SECOND, 0 );
        c.set( Calendar.MILLISECOND, 0 );
        return c.getTimeInMillis();
    }

    public static Long getLongTimeAfterInputDays( final int days ) {
        final Calendar c = Calendar.getInstance();
        c.set( Calendar.HOUR_OF_DAY, 0 );
        c.set( Calendar.MINUTE, 0 );
        c.set( Calendar.SECOND, 0 );
        c.set( Calendar.MILLISECOND, 0 );
        c.add( Calendar.DATE, days );
        return c.getTimeInMillis();
    }

    public static Long getLiveVoxStartOfDay() {
        final DateTimeZone timeZone = DateTimeZone.forID( "US/Eastern" );
        final DateTime now = new DateTime( timeZone );
        final DateTime todayStart = now.withTimeAtStartOfDay();
        return todayStart.getMillis();

    }

    public static Long getLiveVoxEndOfDay() {
        final DateTimeZone timeZone = DateTimeZone.forID( "US/Eastern" );
        final DateTime now = new DateTime( timeZone );
        final DateTime tomorrowStart = now.plusDays( 1 ).withTimeAtStartOfDay();
        return tomorrowStart.getMillis();

    }
    
    /**
     * 
     * @param dueDate
     * @param timeZone
     * @return
     */
    public static String getPropertyTourDate( final Date dueDate, final String timeZone) {
        final SimpleDateFormat sdf = new SimpleDateFormat( CO_SHOPPING_PROPERTY_TOUR_INFO_DATE_PATTERN );
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime( dueDate );
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        return sdf.format( calendar.getTime() );
    }
    
    /**
     * To get date object
     * from string date time and
     * Time zone
     * @param dueDtm
     * @return
     */
    public static Date getDueDateFromStringDt(final String dueDtm, final String timezone) {
        Date dueDate = null;
        LOGGER.info("Getting due date for agent dueDtm : {}, timezone : {}", dueDtm, timezone);
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm");
            if (timezone != null) {
                sdf.setTimeZone(TimeZone.getTimeZone(timezone));
            }
            dueDate = sdf.parse(dueDtm);
        } catch (final Exception e) {
            LOGGER.error("Exception while converstion of date : {}, timezone : {}", dueDtm, timezone, e);
        }
        return dueDate;
    }
    
    /**
     * Gets the string from date.
     * 
     * @param date
     *            the date
     * @param pattern
     *            the pattern
     * @param timeZone
     *            the timeZone
     * @return the string from date
     */
    public static String toString( final Date date, final String pattern,String timeZone ) {
        String datePattern = pattern;
        if (StringUtils.isBlank( datePattern )) {
            datePattern = DEFAULT_OWNERS_DATE_PATTERN;
        }
        if(StringUtils.isBlank( timeZone )) {
            timeZone = "EST";
        }
        final DateTimeZone dateTimeZone = DateTimeZone.forID( timeZone );
        return DateUtil.toString( new LocalDateTime( date, dateTimeZone ), datePattern );
    }
    
    public static String toString( final long date, final String pattern ) {
        String dateStr = "";
        DateTime dateTime = new DateTime(date);
        if (dateTime != null && StringUtils.isNotBlank( pattern )) {
            final DateTimeFormatter dtFormatter = DateTimeFormat.forPattern( pattern );
            dateStr = dtFormatter.print( dateTime );
        }
        return dateStr;
    }
    
   public static DateTime getDateTimeFromString(String dateTimeString)
   {
	   final DateTime dateTime = new DateTime(dateTimeString);
	   
	   return dateTime;
   }
}
