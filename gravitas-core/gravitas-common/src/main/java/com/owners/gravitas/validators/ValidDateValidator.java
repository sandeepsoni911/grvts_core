package com.owners.gravitas.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.owners.gravitas.util.DateUtil;

/**
 * The Class FutureDateValidator.
 *
 * @author vishwanathm
 *
 */
public class ValidDateValidator implements ConstraintValidator< ValidDate, String > {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( ValidDateValidator.class );

    /** The format. */
    private String format;

    /*
     * (non-Javadoc)
     * @see
     * javax.validation.ConstraintValidator#initialize(java.lang.annotation.
     * Annotation)
     */
    @Override
    public void initialize( ValidDate date ) {
        this.format = date.format();
    }

    /*
     * (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
     * javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid( String value, ConstraintValidatorContext context ) {
        boolean valid = Boolean.FALSE;
        try {
            if (value == null) {
                valid = Boolean.TRUE;
            } else {
                final DateTime dateTime = DateUtil.toDateTime( value, format );
                valid = dateTime != null ? Boolean.TRUE : Boolean.FALSE;
            }
        } catch ( Exception e ) {
            LOGGER.info( "Problem validating date", e );
            valid = Boolean.FALSE;
        }
        return valid;
    }

}
