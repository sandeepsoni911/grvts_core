/**
 * 
 */
package com.owners.gravitas.validator;

import static com.owners.gravitas.validators.ValidatorUtil.checkForLength;
import static com.owners.gravitas.validators.ValidatorUtil.checkForRequired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.owners.gravitas.exception.InvalidArgumentException;

/**
 * The Class ContactUpdateRequestValidator.
 *
 * @author harshads
 */
@Component
public class ContactUpdateRequestValidator {

    /**
     * Validate the contact update request.
     * 
     * @param updateRequest
     *            the update request
     */
    public void validate( final Map< String, String > updateRequest ) {
        final List< String > errors = new ArrayList< String >();
        checkForLength( updateRequest.get( "firstName" ), 0, 40, errors, "error.contact.firstName.size" );
        checkForRequired( updateRequest.get( "lastName" ), errors, "error.contact.lastName.required" );
        checkForLength( updateRequest.get( "lastName" ), 0, 80, errors, "error.contact.lastName.size" );
        checkForLength( updateRequest.get( "phone" ), 0, 40, errors, "error.contact.phone.size" );
        checkForLength( updateRequest.get( "preferredContactTime" ), 0, 200, errors,
                "error.contact.preferredContactTime.size" );
        checkForLength( updateRequest.get( "preferredContactMethod" ), 0, 10, errors,
                "error.contact.preferredContactMethod.size" );
        if (!errors.isEmpty()) {
            throw new InvalidArgumentException( "Invalid contact update request", errors );
        }
    }
}
