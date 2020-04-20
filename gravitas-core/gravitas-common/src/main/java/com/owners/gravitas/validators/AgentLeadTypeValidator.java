package com.owners.gravitas.validators;

import static com.owners.gravitas.enums.RecordType.BUYER;
import static com.owners.gravitas.enums.RecordType.OWNERS_COM_LOANS;
import static com.owners.gravitas.enums.RecordType.SELLER;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

/**
 * The Class AgentLeadTypeValidator that validates leadType of a lead created
 * through Agent app.
 *
 * @author ankusht
 */
public class AgentLeadTypeValidator implements ConstraintValidator< ValidateAgentLeadType, String > {

    /*
     * (non-Javadoc)
     * @see
     * javax.validation.ConstraintValidator#initialize(java.lang.annotation.
     * Annotation)
     */
    @Override
    public void initialize( final ValidateAgentLeadType valt ) {
        // do nothing
    }

    /*
     * (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
     * javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid( final String leadType, final ConstraintValidatorContext context ) {
        return StringUtils.isBlank( leadType ) || BUYER.name().equals( leadType ) || SELLER.name().equals( leadType )
                || OWNERS_COM_LOANS.name().equals( leadType );
    }

}
