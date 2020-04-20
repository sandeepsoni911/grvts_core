package com.owners.gravitas.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.owners.gravitas.dto.request.LeadDetailsRequest;
import com.owners.gravitas.exception.InvalidArgumentException;

/**
 * The class LeadDetailsRequestValidator
 * 
 * @author imranmoh
 *
 */
@Component
public class LeadDetailsRequestValidator {

    /**
     * 
     * Method to validate the input request parameters for the all buyers lead/
     * my buyer leads
     * 
     * @param request
     */
    public void validateLeadDetailsRequest( final LeadDetailsRequest request ) {
        final List< String > errors = new ArrayList<>();
        final String field = request.getProperty();
        final String sortDirection = request.getDirection();
        final Integer page = request.getPage();
        final Integer size = request.getSize();

        if (null == page || page < 0) {
            errors.add( "Invalid page number to be displayed" );
        }

        if (null == size || size < 0) {
            errors.add( "Invalid page size" );
        }

        if (null == sortDirection
                || !( sortDirection.equalsIgnoreCase( "asc" ) || sortDirection.equalsIgnoreCase( "desc" ) )) {
            errors.add( "Invalid sort direction, provide either asc or desc" );
        }

        if (null == InputFieldsEnum.getInputRequestType( field )) {
            errors.add( "Invalid field to sort: "+field );
        }

        if (!errors.isEmpty()) {
            throw new InvalidArgumentException( "Error occured for lead input paramter", errors );
        }
    }

    /**
     * Enum for the set fields which can only sorted
     * 
     * @author imranmoh
     *
     */
    private enum InputFieldsEnum {
        SCORE( "score" ),
        LEADNAME( "leadName" ),
        STATE( "state" ),
        EMAIL( "email" ),
        PHONE( "phone" ),
        CREATEDATE( "createdDate" ),
        CRMID( "crmID" ),
        BUYERLEADSCORE("buyerLeadScore");

        private String inputField;

        private InputFieldsEnum( final String field ) {
            inputField = field;
        }

        public String getInputField() {
            return inputField;
        }

        /**
         * Gets the lead field to be sorted.
         *
         * @param inputField
         *            the inputField
         * @return the inputField
         */
        public static String getInputRequestType( final String inputField ) {
            for ( final InputFieldsEnum field : values() ) {
                if (field.getInputField().equalsIgnoreCase( inputField )) {
                    return inputField;
                }
            }
            return null;
        }
    }
}
