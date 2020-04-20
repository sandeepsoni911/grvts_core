package com.owners.gravitas.validators;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

/**
 * The Class StringEnumerationValidator.
 *
 * @author vishwanathm
 *
 */
public class StringEnumerationValidator implements ConstraintValidator< StringEnum, String > {

    /** The available enum names. */
    private Set< String > enums;

    /**
     * Gets the names set.
     *
     * @param e
     *            the e
     * @return the names set
     */
    private Set< String > getNamesSet( Class< ? extends Enum< ? > > e ) {
        final Enum< ? >[] enumerations = e.getEnumConstants();
        final String[] names = new String[enumerations.length];
        for ( int i = 0; i < enumerations.length; i++ ) {
            names[i] = enumerations[i].name();
        }
        return new HashSet< String >( Arrays.asList( names ) );
    }

    /*
     * (non-Javadoc)
     * @see
     * javax.validation.ConstraintValidator#initialize(java.lang.annotation.
     * Annotation)
     */
    @Override
    public void initialize( StringEnum stringEnumeration ) {
        Class< ? extends Enum< ? > > enumSelected = stringEnumeration.enumClass();
        enums = getNamesSet( enumSelected );
    }

    /*
     * (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
     * javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid( String value, ConstraintValidatorContext context ) {
        boolean valid = Boolean.FALSE;
        if (StringUtils.isBlank( value )) {
            valid = Boolean.TRUE;
        } else {
            valid = enums.contains( value );
        }
        return valid;
    }
}
