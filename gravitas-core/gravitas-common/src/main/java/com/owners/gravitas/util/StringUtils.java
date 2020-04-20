package com.owners.gravitas.util;

import static com.owners.gravitas.constants.Constants.PERIOD;
import static com.owners.gravitas.constants.Constants.REGEX_DOUBLE_QUOTE_START_END;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class StringUtils.
 *
 * @author harshads
 */
public class StringUtils {

    public static String REGEX_ALPA_NUMERIC_UNDERSCORE_HYPEN = "^[a-zA-Z0-9_-]+$";

    /** The id max length. */
    private static int ID_MAX_LENGTH = 50;

    /** email validation emailPattern. */
    private static final String EMAIL_REGEXP = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-\\+]+)*@";

    /** The Constant DOMAIN_NAME_PATTERN. */
    private static final String DOMAIN_NAME_PATTERN = "((?!-)[A-Za-z0-9-]{1,}(?<!-)\\.)+[A-Za-z]{2,}$";

    /** The Constant MAX_EMAIL_LENGHT. */
    private static final int MAX_EMAIL_LENGHT = 255;

    /** email emailPattern. */
    public static final Pattern EMAIL_PATTERN = Pattern.compile( EMAIL_REGEXP + DOMAIN_NAME_PATTERN );

    private StringUtils() {
    }

    /**
     * Convert object to string.
     *
     * @param value
     *            the value
     * @return the string
     */
    public static String convertObjectToString( final Object value ) {
        return value != null ? String.valueOf( value ) : null;
    }

    /**
     * Trim double quotes.
     *
     * @param value
     *            the value
     * @return the string
     */
    public static String removeDoubleQuotes( final String value ) {
        String string = EMPTY;
        if (isNotBlank( value )) {
            string = value;
            string = value.replaceAll( REGEX_DOUBLE_QUOTE_START_END, EMPTY );
        }
        return string;
    }

    /**
     * Checks if the userId is valid.
     *
     * @param id
     *            the id
     * @return boolean
     */
    public static boolean isValidId( final String id ) {
        if (doNotHaveWhiteSpace( id ) && matchesCharPattern( id ) && id.length() <= ID_MAX_LENGTH) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the string has white space.
     *
     * @param str
     *            the str
     * @return boolean
     */
    public static boolean doNotHaveWhiteSpace( final String str ) {
        if (org.apache.commons.lang3.StringUtils.isEmpty( str )
                || org.apache.commons.lang3.StringUtils.containsWhitespace( str )) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the string matches the regex pattern.
     *
     * @param str
     *            the str
     * @return boolean
     */
    public static boolean matchesCharPattern( final String str ) {
        if (str.matches( REGEX_ALPA_NUMERIC_UNDERSCORE_HYPEN )) {
            return true;
        }
        return false;
    }

    /**
     * Validate email.
     *
     * @param inputEmail
     *            the input email
     * @return true, if successful
     */

    public static boolean isEmailIdValid( final String value ) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank( value )) {
            if (value.length() < MAX_EMAIL_LENGHT) {
                final Matcher matcher = EMAIL_PATTERN.matcher( value );
                if (matcher.matches()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Sub string for length.
     *
     * @param value
     *            the value
     * @param length
     *            the length
     * @return the string
     */
    public static String subStringForLength( final String value, final int length ) {
        return ( value.length() > length ) ? value.substring( 0, length ) : value;
    }

    /**
     * String value not emtpy.
     *
     * @param obj
     *            the obj
     * @return the string
     */
    public static String stringValueNotEmtpy( final Object obj ) {
        if (obj != null) {
            final String str = obj.toString().trim();
            return ( str.length() > 0 ) ? str : null;
        }
        return null;
    }

    /**
     * Remove all non-numeric characters such as hyphen or space from phone
     * number.
     * 
     * @param phoneNumber
     *            the phone number
     * @return phone number without any non-numeric characters
     */
    public static String formatPhoneNumber( final String phoneNumber ) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank( phoneNumber )) {
            return phoneNumber.replaceAll( "[^\\d]", "" );
        }
        return phoneNumber;
    }

    /**
     * Format phone number dot separated.
     * 
     * @param phoneNumber
     * @return
     */
    public static String formatPhoneNumberWithPeriods( final String phoneNumber ) {
        String phone = formatPhoneNumber( phoneNumber );
        StringBuilder formattedPhone = null;
        if (org.apache.commons.lang3.StringUtils.isNotBlank( phone ) && phone.length() <= 10 && phone.length() >= 7) {
            formattedPhone = new StringBuilder();
            formattedPhone.append( phone.substring( 0, 3 ) ).append( PERIOD ).append( phone.substring( 3, 6 ) )
                    .append( PERIOD ).append( phone.substring( 6 ) );
            phone = formattedPhone.toString();
        }
        return phone;
    }
}
