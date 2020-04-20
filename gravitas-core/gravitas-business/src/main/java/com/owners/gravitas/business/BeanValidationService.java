package com.owners.gravitas.business;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

/**
 * The Interface BeanValidationService.
 * 
 * @author ankusht
 */
public interface BeanValidationService {

    /**
     * Validate.
     *
     * @param object
     *            the object
     * @return the map
     */
    Map< String, List< String > > validate( final Object object );

    /**
     * Gets the constraint voilations.
     *
     * @param object
     *            the object
     * @return the constraint voilations
     */
    Set< ConstraintViolation< Object > > getConstraintViolations( final Object object );

    /**
     * Validate data.
     *
     * @param object
     *            the object
     */
    void validateData( final Object object );
}
