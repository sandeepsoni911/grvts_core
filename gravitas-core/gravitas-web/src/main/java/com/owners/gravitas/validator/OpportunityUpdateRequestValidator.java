package com.owners.gravitas.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.owners.gravitas.exception.InvalidArgumentException;
import com.owners.gravitas.validators.ValidatorUtil;

/**
 * The Class OpportunityUpdateRequestValidator.
 *
 * @author amits
 */
@Component
public class OpportunityUpdateRequestValidator {

    /**
     * Validate opportunity request.
     *
     * @param request
     *            the request
     */
    public void validateOpportunityRequest( final Map< String, Object > request ) {
        final List< String > errors = new ArrayList< String >();
        ValidatorUtil.checkForLength( toString( request.get( "leadSource" ) ), Integer.valueOf( 1 ),
                Integer.valueOf( 40 ), errors, "error.opportunity.leadSource.size" );
        ValidatorUtil.checkForLength( toString( request.get( "workingWithExternalAgent" ) ), Integer.valueOf( 0 ),
                Integer.valueOf( 60 ), errors, "error.opportunity.workingWithExternalAgent.size" );
        ValidatorUtil.checkForLength( toString( request.get( "buyerReadinessTimeline" ) ), Integer.valueOf( 0 ),
                Integer.valueOf( 50 ), errors, "error.opportunity.buyerReadinessTimeline.size" );
        ValidatorUtil.checkForLength( toString( request.get( "budget" ) ), Integer.valueOf( 0 ), Integer.valueOf( 200 ),
                errors, "error.opportunity.budget.size" );
        ValidatorUtil.checkForLength( toString( request.get( "interestedZipCodes" ) ), Integer.valueOf( 0 ),
                Integer.valueOf( 200 ), errors, "error.opportunity.interestedZipCodes.size" );
        ValidatorUtil.checkForLength( toString( request.get( "financing" ) ), Integer.valueOf( 0 ),
                Integer.valueOf( 30 ), errors, "error.opportunity.financing.size" );
        ValidatorUtil.validateDecimalLength( toString( request.get( "medianPrice" ) ), errors,
                "error.opportunity.medianPrice.size" );
        ValidatorUtil.validateDecimalNumber( toString( request.get( "medianPrice" ) ), errors,
                "error.opportunity.medianPrice.value" );
        ValidatorUtil.checkForLength( toString( request.get( "stage" ) ), Integer.valueOf( 1 ), Integer.valueOf( 60 ),
                errors, "error.opportunity.stage.size" );
        ValidatorUtil.checkForLength( toString( request.get( "reasonLost" ) ), Integer.valueOf( 0 ),
                Integer.valueOf( 60 ), errors, "error.opportunity.reasonLost.size" );
        ValidatorUtil.validateDecimalLength( toString( request.get( "buySideCommission" ) ), errors,
                "error.opportunity.buySideCommission.size" );
        ValidatorUtil.validateDecimalNumber( toString( request.get( "buySideCommission" ) ), errors,
                "error.opportunity.buySideCommission.value" );
        ValidatorUtil.validateDecimalLength( toString( request.get( "salesPrice" ) ), errors,
                "error.opportunity.salesPrice.size" );
        ValidatorUtil.validateDecimalNumber( toString( request.get( "salesPrice" ) ), errors,
                "error.opportunity.salesPrice.value" );
        ValidatorUtil.validateDecimalLength( toString( request.get( "preApprovedAmount" ) ), errors,
                "error.opportunity.preApprovedAmount.size" );
        ValidatorUtil.validateDecimalNumber( toString( request.get( "preApprovedAmount" ) ), errors,
                "error.opportunity.preApprovedAmount.value" );
        ValidatorUtil.checkForLength( toString( request.get( "opportunityType" ) ), Integer.valueOf( 1 ),
                Integer.valueOf( 60 ), errors, "error.opportunity.opportunityType.size" );
        ValidatorUtil.validateDecimalLength( toString( request.get( "commissionBasePrice" ) ), errors,
                "error.opportunity.commissionBasePrice.size" );
        ValidatorUtil.validateDecimalNumber( toString( request.get( "commissionBasePrice" ) ), errors,
                "error.opportunity.commissionBasePrice.value" );
        ValidatorUtil.checkForLength( toString( request.get( "titleCompany" ) ), Integer.valueOf( 0 ),
                Integer.valueOf( 255 ), errors, "error.opportunity.titleCompany.value" );
        ValidatorUtil.checkForLength( toString( request.get( "priceRange" ) ), Integer.valueOf( 0 ),
                Integer.valueOf( 60 ), errors, "error.opportunity.priceRange.size" );
        ValidatorUtil.checkForLength( toString( request.get( "propertyAddress" ) ), Integer.valueOf( 0 ),
                Integer.valueOf( 255 ), errors, "error.opportunity.propertyAddress.value" );
        ValidatorUtil.validateDecimalLength( toString( request.get( "listingSideCommission" ) ), errors,
                "error.opportunity.listingSideCommission.size" );
        ValidatorUtil.validateDecimalNumber( toString( request.get( "listingSideCommission" ) ), errors,
                "error.opportunity.listingSideCommission.value" );
        ValidatorUtil.validateDecimalLength( toString( request.get( "listPrice" ) ), errors,
                "error.opportunity.listPrice.size" );
        ValidatorUtil.validateDecimalNumber( toString( request.get( "listPrice" ) ), errors,
                "error.opportunity.listPrice.value" );
        ValidatorUtil.validateDecimalLength( toString( request.get( "offerAmount" ) ), errors,
                "error.opportunity.offerAmount.size" );
        ValidatorUtil.validateDecimalNumber( toString( request.get( "offerAmount" ) ), errors,
                "error.opportunity.offerAmount.value" );

        if (!errors.isEmpty()) {
            throw new InvalidArgumentException( "Invalid opportunity update request", errors );
        }

    }

    /**
     * To string.
     *
     * @param value
     *            the value
     * @return the string
     */
    private String toString( Object value ) {
        return value != null ? String.valueOf( value ) : null;
    }
}
