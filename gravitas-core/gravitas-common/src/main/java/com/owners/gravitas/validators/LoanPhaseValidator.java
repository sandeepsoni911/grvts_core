package com.owners.gravitas.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.util.PropertiesUtil;

/**
 * The Class LoanPhaseValidator - that validates loan phase to be one of the
 * supported value from properties file
 * 
 * @author ankusht
 */
public class LoanPhaseValidator implements ConstraintValidator< ValidateLoanPhase, Object > {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( LoanPhaseValidator.class );

    /*
     * (non-Javadoc)
     * @see
     * javax.validation.ConstraintValidator#initialize(java.lang.annotation.
     * Annotation)
     */
    @Override
    public void initialize( final ValidateLoanPhase vlp ) {
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
        if (value != null) {
            try {
                String loanPhaseVal = value.toString();
                final String loanPhase = PropertiesUtil.getLoanPhasePropertyPrefix( loanPhaseVal );
                result = StringUtils.isNotBlank( PropertiesUtil.getProperty( Constants.OCL_LOAN_PHASE_PRE_FIX
                        + loanPhase.toLowerCase() + Constants.OCL_LOAN_PHASE_STAGE_SUFFIX ) );
            } catch ( Exception e ) {
                // nothing to do in case of exception
                LOGGER.error( "Error occurred while validating loan number", e );
                result = Boolean.FALSE;
            }
        }
        return result;
    }

}
