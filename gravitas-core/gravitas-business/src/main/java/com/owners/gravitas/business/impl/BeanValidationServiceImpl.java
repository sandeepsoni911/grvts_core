package com.owners.gravitas.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import com.owners.gravitas.business.BeanValidationService;

/**
 * The Class BeanValidationServiceImpl.
 * 
 * @author ankusht
 */
@Service
public class BeanValidationServiceImpl implements BeanValidationService {

    /**
     * Validate.
     *
     * @param object
     *            the request
     * @return the map
     */
    @Override
    public Map< String, List< String > > validate( final Object object ) {
        final Map< String, List< String > > failedContraints = new LinkedMultiValueMap< String, String >();
        for ( final ConstraintViolation< Object > violation : getConstraintViolations( object ) ) {
            final String propertyPath = violation.getPropertyPath().toString();
            List< String > violationList = failedContraints.get( propertyPath );
            if (null == violationList) {
                violationList = new ArrayList<>();
            }
            violationList.add( violation.getMessageTemplate() );
            failedContraints.put( propertyPath, violationList );
        }
        return failedContraints;
    }

    /**
     * Gets the constraint voilations.
     *
     * @param object
     *            the object
     * @return the constraint voilations
     */
    @Override
    public Set< ConstraintViolation< Object > > getConstraintViolations( final Object object ) {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final Set< ConstraintViolation< Object > > constraintViolations = validator.validate( object );
        return constraintViolations;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.BeanValidationService#validateData(java.lang
     * .Object)
     */
    @Override
    public void validateData( final Object object ) {
        final Set< ConstraintViolation< Object > > constraintViolations = getConstraintViolations( object );
        if (CollectionUtils.isNotEmpty( constraintViolations )) {
            throw new ConstraintViolationException( constraintViolations );
        }
    }
}
