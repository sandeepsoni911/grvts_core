package com.owners.gravitas.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;

/**
 * The Class DueDateValidator.
 *
 * @author bhardrah
 *
 */
public class DueDateValidator implements ConstraintValidator< DueDate, Date > {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( DueDateValidator.class );

    /** The dueDate. */
    private DueDate dueDate;

    /*
     * (non-Javadoc)
     * @see
     * javax.validation.ConstraintValidator#initialize(java.lang.annotation.
     * Annotation)
     */
    @Override
    public void initialize( DueDate dueDate ) {
        this.dueDate = dueDate;
    }

    /*
     * (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
     * javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid( Date value, ConstraintValidatorContext context ) {
        boolean valid = Boolean.TRUE;
        try {
            if (value != null) {
                Date date = new Date();
                valid = value.after( date );
            }
        } catch ( Exception e ) {
            LOGGER.info( "Problem validating date", e );
            valid = Boolean.FALSE;
        }
        return valid;
    }

}
