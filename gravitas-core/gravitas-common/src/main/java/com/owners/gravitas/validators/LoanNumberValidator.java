package com.owners.gravitas.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The Class LoanNumberValidator - that validates loan number to be 9 digits
 * long.
 * 
 * @author ankusht
 */
public class LoanNumberValidator implements ConstraintValidator< ValidateLoanNumber, Object > {

    /*
     * (non-Javadoc)
     * @see
     * javax.validation.ConstraintValidator#initialize(java.lang.annotation.
     * Annotation)
     */
    @Override
    public void initialize( final ValidateLoanNumber vln ) {
        // do nothing
    }

    /*
     * (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
     * javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid( final Object value, final ConstraintValidatorContext context ) {
        boolean result = Boolean.TRUE;
        String loanNumber = value.toString();
        try {
            int intValue = Integer.parseInt( loanNumber );
            result = intValue > 99999999 && intValue < 1000000000;
        } catch ( NumberFormatException e ) {
            result = Boolean.FALSE;
        }
        return result;
    }

}
