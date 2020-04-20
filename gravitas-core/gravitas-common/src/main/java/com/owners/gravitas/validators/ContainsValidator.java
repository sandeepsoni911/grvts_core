package com.owners.gravitas.validators;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.util.PropertiesUtil;

/**
 * The Class ContainsValidator.
 *
 * @author vishwanathm
 */
public class ContainsValidator implements ConstraintValidator< Contains, String > {

    /** The property value. */
    private List< String > properties;

    /**
     * Initializes the defined action properties.
     *
     * @param contains
     *            the action property
     */
    @Override
    public void initialize( final Contains contains ) {
        properties = Arrays
                .asList( PropertiesUtil.getProperty( contains.propertyKey() ).split( Constants.COMMA_AND_SPACE ) );
    }

    /**
     * Validates the given value against the defines action properties.
     *
     * @param value
     *            the value
     * @param context
     *            the context
     * @return true, if is valid
     */
    @Override
    public boolean isValid( final String value, final ConstraintValidatorContext context ) {
        boolean valid = Boolean.FALSE;
        if (StringUtils.isBlank( value )) {
            valid = Boolean.TRUE;
        } else {
            valid = properties.contains( value.toUpperCase() );
        }
        return valid;
    }

}
