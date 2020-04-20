package com.owners.gravitas.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.BaseDTO;

/**
 * The Class RegistrationResponse.
 *
 * @author vishwanathm
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class RegistrationResponse extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4243265117193636375L;

    /** The result. */
    private RegisteredUserResponse result;

    /**
     * Gets the result.
     *
     * @return the result
     */
    public RegisteredUserResponse getResult() {
        return result;
    }

    /**
     * Sets the result.
     *
     * @param result
     *            the new result
     */
    public void setResult( final RegisteredUserResponse result ) {
        this.result = result;
    }
}
