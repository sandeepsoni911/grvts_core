package com.owners.gravitas.validator;

import static com.owners.gravitas.enums.ErrorCode.REQUEST_PARAM_MISSING_ERROR;
import static org.apache.commons.collections.MapUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.Message;
import javax.validation.ConstraintViolation;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.BeanValidationService;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.enums.ErrorCode;
import com.owners.gravitas.exception.AffiliateEmailValidationException;
import com.owners.gravitas.exception.InvalidArgumentException;

/**
 * The Class LeadValidator.
 *
 * @author shivamm
 */
@Component
public class LeadValidator {
    
    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( LeadValidator.class );

    /** The bean validation service. */
    @Autowired
    private BeanValidationService beanValidationService;
    
    /**
     * Validate lead.
     *
     * @param leadRequest
     *            the lead request
     */
    public void validateOCLLeadRequest( final GenericLeadRequest leadRequest ) {
        LOGGER.info( "Validate OCL lead request" + leadRequest.getEmail() + " Time is : " + LocalDateTime.now() );
        final List< String > violationList = validateRequest( leadRequest );
        if (!violationList.isEmpty()) {
            throw new InvalidArgumentException( ErrorCode.INVALID_OCL_LEAD_PARAM_ERROR.getCode(), violationList );
        }
    }

    /**
     * Validate lead request.
     *
     * @param leadRequest
     *            the lead request
     */
    public void validateUnbounceLeadRequest( final GenericLeadRequest leadRequest ) {
        LOGGER.info( "Validate unbounce lead request" + leadRequest + " Time is : " + LocalDateTime.now() );
        List< String > violationList = null;
        if (null != leadRequest) {
            violationList = validateRequest( leadRequest );
        } else {
            violationList = new ArrayList<>();
            violationList.add( REQUEST_PARAM_MISSING_ERROR.getCode() );
        }
        if (!violationList.isEmpty()) {
            throw new InvalidArgumentException( "Invalid unbounce lead request", violationList );
        }
    }

    /**
     * Validate lead request.
     *
     * @param leadRequest
     *            the lead request
     * @param message
     *            the message
     */
    public void validateLeadRequest( final GenericLeadRequest leadRequest, final Message message ) {
        LOGGER.info( "Validate lead request" + leadRequest.getEmail() + " Time is : " + LocalDateTime.now() );
        final Map< String, List< String > > failedContraints = beanValidationService.validate( leadRequest );
        if (isNotEmpty( failedContraints )) {
            throw new AffiliateEmailValidationException( message, "Affiliate email validator error", failedContraints );
        }
    }

    /**
     * Validate request.
     *
     * @param leadRequest
     *            the lead request
     * @return the list
     */
    private List< String > validateRequest( final GenericLeadRequest leadRequest ) {
        LOGGER.info( "Validate Request" + leadRequest.getEmail() + " Time is : " + LocalDateTime.now() );
        final List< String > violationList = new ArrayList<>();
        final Set< ConstraintViolation< Object > > leadConstraintViolations = beanValidationService
                .getConstraintViolations( leadRequest );
        leadConstraintViolations
                .forEach( constraintViolation -> violationList.add( constraintViolation.getMessageTemplate() ) );
        return violationList;
    }
}
