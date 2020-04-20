package com.owners.gravitas.validators;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.validator.routines.UrlValidator;

/**
 * The Class EmailListValidator.
 *
 * @author vishwanathm
 *
 */
public class UrlListValidator implements ConstraintValidator< UrlList, List< String > > {

    /*
     * (non-Javadoc)
     * @see
     * javax.validation.ConstraintValidator#initialize(java.lang.annotation.
     * Annotation)
     */
    @Override
    public void initialize( UrlList urlList ) {
    }

    /*
     * (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
     * javax.validation.ConstraintValidatorContext)
     */
    public boolean isValid( List< String > urls, ConstraintValidatorContext context ) {
        boolean valid = Boolean.FALSE;
        if (CollectionUtils.isNotEmpty( urls )) {
            final String[] schemes = { "http", "https" };
            UrlValidator urlValidator = new UrlValidator( schemes );
            for ( String url : urls ) {
                if (!urlValidator.isValid( url )) {
                    valid = Boolean.FALSE;
                    break;
                }
                valid = Boolean.TRUE;
            }
        } else {
            valid = Boolean.TRUE;
        }
        return valid;
    }

    
}
