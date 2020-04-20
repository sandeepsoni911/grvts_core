package com.owners.gravitas.validators;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.collections.CollectionUtils;

/**
 * The Class EmailListValidator.
 *
 * @author vishwanathm
 *
 */
public class ItemSizeValidator implements ConstraintValidator< ItemSize, List< String > > {

    private int min;
    private int max;

    /*
     * (non-Javadoc)
     * @see
     * javax.validation.ConstraintValidator#initialize(java.lang.annotation.
     * Annotation)
     */
    @Override
    public void initialize( final ItemSize emailSize ) {
        min = emailSize.min();
        max = emailSize.max();
    }

    /*
     * (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
     * javax.validation.ConstraintValidatorContext)
     */
    public boolean isValid( final List< String > emails, final ConstraintValidatorContext context ) {
        boolean valid = FALSE;
        if (CollectionUtils.isNotEmpty( emails )) {
            for ( String email : emails ) {
                if (email.length() < min || email.length() > max) {
                    valid = FALSE;
                    break;
                }
                valid = TRUE;
            }
        } else {
            // @NotNull is not applied for CC and BCC fields
            valid = TRUE;
        }
        return valid;
    }

}
