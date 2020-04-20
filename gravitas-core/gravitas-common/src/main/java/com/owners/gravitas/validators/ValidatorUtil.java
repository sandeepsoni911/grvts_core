/** ValidatorUtil.java. */
package com.owners.gravitas.validators;

import static com.owners.gravitas.constants.Constants.REGEX_DECIMAL_LENGTH_VALIDATION_PATTERN;
import static com.owners.gravitas.constants.Constants.REGEX_NUMBER_VALIDATION_PATTERN;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.owners.gravitas.util.DateUtil;

/**
 * Utility class for validation methods.
 *
 * @author Harshad Shinde
 */
public final class ValidatorUtil {

    /**
     * Instantiates a new validator util.
     */
    private ValidatorUtil() {
    }

    /**
     * Validate number.
     *
     * @param value
     *            the value
     * @param errors
     *            the errors
     * @param errorCode
     *            the error code
     */
    public static void validateDecimalNumber( final String value, final List< String > errors, String errorCode ) {
        if (null != value && !String.valueOf( value ).matches( REGEX_NUMBER_VALIDATION_PATTERN )) {
            errors.add( errorCode );
        }
    }

    /**
     * Validate decimal length.
     *
     * @param value
     *            the value
     * @param errors
     *            the errors
     * @param errorCode
     *            the error code
     */
    public static void validateDecimalLength( final String value, final List< String > errors, String errorCode ) {
        if (null != value && !String.valueOf( value ).matches( REGEX_DECIMAL_LENGTH_VALIDATION_PATTERN )) {
            errors.add( errorCode );
        }
    }

    /**
     * Validate date.
     *
     * @param value
     *            the value
     * @param errors
     *            the errors
     * @param errorCode
     *            the error code
     */
    public static void validateDate( final String value, final List< String > errors, String errorCode ) {
        if (null != value && !DateUtil.isValidFormat( value, DateUtil.DATE_TIME_PATTERN )) {
            errors.add( errorCode );
        }
    }

    /**
     * Check for length.
     *
     * @param value
     *            the value
     * @param minLength
     *            the min lenght
     * @param maxLength
     *            the max lenght
     * @param errors
     *            the errors
     * @param errorCode
     *            the error code
     */
    public static void checkForLength( final String value, final int minLength, final int maxLength,
            final List< String > errors, String errorCode ) {
        if (null != value && ( value.length() < minLength || value.length() > maxLength )) {
            errors.add( errorCode );
        }
    }

    /**
     * Check for required.
     *
     * @param value
     *            the value
     * @param errors
     *            the errors
     * @param errorCode
     *            the error code
     */
    public static void checkForRequired( final String value, final List< String > errors, final String errorCode ) {
        if (StringUtils.isEmpty( value )) {
            errors.add( errorCode );
        }
    }
}
