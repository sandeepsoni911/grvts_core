package com.owners.gravitas.validators;

import static com.owners.gravitas.constants.Constants.REG_EXP_EMAIL;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.List;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.collections.CollectionUtils;

/**
 * The Class EmailListValidator.
 *
 * @author vishwanathm
 *
 */
public class EmailListValidator implements ConstraintValidator< EmailList, List< String > > {

    /*
     * (non-Javadoc)
     * @see
     * javax.validation.ConstraintValidator#initialize(java.lang.annotation.
     * Annotation)
     */
    @Override
    public void initialize( final EmailList emailList ) {
    }

    /*
     * (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
     * javax.validation.ConstraintValidatorContext)
     */
    public boolean isValid( final List< String > emails, final ConstraintValidatorContext context ) {
        boolean valid = FALSE;
        if (CollectionUtils.isNotEmpty( emails )) {
            final Pattern pattern = Pattern.compile( REG_EXP_EMAIL );
            for ( String email : emails ) {
                if (!pattern.matcher( email ).matches()) {
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
