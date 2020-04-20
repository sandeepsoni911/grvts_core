package com.owners.gravitas.business.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.metadata.ConstraintDescriptor;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * The Class BeanValidationServiceImplTest.
 * 
 * @author ankusht
 */
@PrepareForTest( { Validation.class } )
public class BeanValidationServiceImplTest extends AbstractBaseMockitoTest {

    /** The bean validation service impl. */
    @InjectMocks
    private BeanValidationServiceImpl beanValidationServiceImpl;

    /** The validator. */
    @Mock
    private Validator validator;

    /** The validator factory. */
    @Mock
    private ValidatorFactory validatorFactory;

    /**
     * Test validate.
     */
    @Test
    public void testValidate() {
        Object object = new Object();
        Set< ConstraintViolation< Object > > constraintViolations = new HashSet<>();
        ConstraintViolation< Object > violation = getViolation();
        constraintViolations.add( violation );
        mockStatic( Validation.class );
        when( Validation.buildDefaultValidatorFactory() ).thenReturn( validatorFactory );
        when( validatorFactory.getValidator() ).thenReturn( validator );
        when( validator.validate( object ) ).thenReturn( constraintViolations );
        Map< String, List< String > > failedContraints = beanValidationServiceImpl.validate( object );
        assertNotNull( failedContraints );
    }

    /**
     * Test validate data.
     */
    @Test( expectedExceptions = ConstraintViolationException.class )
    public void testValidateData() {
        Object object = new Object();
        Set< ConstraintViolation< Object > > constraintViolations = new HashSet<>();
        ConstraintViolation< Object > violation = getViolation();
        constraintViolations.add( violation );
        mockStatic( Validation.class );
        when( Validation.buildDefaultValidatorFactory() ).thenReturn( validatorFactory );
        when( validatorFactory.getValidator() ).thenReturn( validator );
        when( validator.validate( object ) ).thenReturn( constraintViolations );
        beanValidationServiceImpl.validateData( object );
    }

    /**
     * Test get constraint violations.
     */
    @Test
    public void testGetConstraintViolations() {
        Object object = new Object();
        Set< ConstraintViolation< Object > > constraintViolations = new HashSet<>();
        ConstraintViolation< Object > violation = getViolation();
        constraintViolations.add( violation );
        mockStatic( Validation.class );
        when( Validation.buildDefaultValidatorFactory() ).thenReturn( validatorFactory );
        when( validatorFactory.getValidator() ).thenReturn( validator );
        when( validator.validate( object ) ).thenReturn( constraintViolations );
        Set< ConstraintViolation< Object > > failedContraints = beanValidationServiceImpl
                .getConstraintViolations( object );
        assertNotNull( failedContraints );
    }

    /**
     * Gets the violation.
     *
     * @return the violation
     */
    private ConstraintViolation< Object > getViolation() {
        ConstraintViolation< Object > voilation = new ConstraintViolation< Object >() {

            @Override
            public < U > U unwrap( Class< U > type ) {
                return null;
            }

            @Override
            public Class< Object > getRootBeanClass() {
                return null;
            }

            @Override
            public Object getRootBean() {
                return null;
            }

            @Override
            public Path getPropertyPath() {
                return new Path() {

                    @Override
                    public Iterator< Node > iterator() {
                        return iterator();
                    }
                };
            }

            @Override
            public String getMessageTemplate() {
                return null;
            }

            @Override
            public String getMessage() {
                return null;
            }

            @Override
            public Object getLeafBean() {
                return null;
            }

            @Override
            public Object getInvalidValue() {
                return null;
            }

            @Override
            public Object getExecutableReturnValue() {
                return null;
            }

            @Override
            public Object[] getExecutableParameters() {
                return null;
            }

            @Override
            public ConstraintDescriptor< ? > getConstraintDescriptor() {
                return null;
            }
        };
        return voilation;
    }

}
