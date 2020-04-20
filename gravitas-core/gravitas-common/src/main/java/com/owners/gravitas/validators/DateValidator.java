package com.owners.gravitas.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.owners.gravitas.util.DateUtil;

/**
 * The Class DateValidator.
 *
 * @author vishwanathm
 *
 */
public class DateValidator implements ConstraintValidator< Date, String > {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( DateValidator.class );

    /** The format. */
    private String format;

    /*
     * (non-Javadoc)
     * @see
     * javax.validation.ConstraintValidator#initialize(java.lang.annotation.
     * Annotation)
     */
    @Override
    public void initialize( Date date ) {
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
                valid = !DateUtil.toDateTime( value, format ).isAfterNow();
            }
        } catch ( Exception e ) {
            LOGGER.info( "Problem validating date", e );
            valid = Boolean.FALSE;
        }
        return valid;
    }

}
