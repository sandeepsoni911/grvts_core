package com.owners.gravitas.validators;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.owners.gravitas.enums.GravitasClientType;

/**
 * The Class ClientTypeValidator use to validate enum values of
 * GravitasClientType class.
 *
 * @author nishak
 *
 */
public class ClientTypeValidator implements ConstraintValidator< ClientType, String > {

    /** The available enum names. */
    private Set< String > enumValues;

    /**
     * Gets the names set.
     *
     * @param e
     *            the e
     * @return the names set
     */
    private Set< String > getNamesSet( Class< ? extends Enum< ? > > e ) {
        final GravitasClientType[] values = GravitasClientType.values();
        enumValues = new HashSet<>();
        for ( final GravitasClientType type : values ) {
            enumValues.add( type.getValue() );
        }
        return enumValues;
    }

    /*
     * (non-Javadoc)
     * @see
     * javax.validation.ConstraintValidator#initialize(java.lang.annotation.
     * Annotation)
     */
    @Override
    public void initialize( ClientType typeEnum ) {
        Class< ? extends Enum< ? > > enumSelected = typeEnum.enumClass();
        enumValues = getNamesSet( enumSelected );
    }

    /*
     * (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
     * javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid( String value, ConstraintValidatorContext context ) {
        boolean isValid = false;
        if (enumValues.contains( value )) {
            isValid = true;
        }
        return isValid;
    }

}
