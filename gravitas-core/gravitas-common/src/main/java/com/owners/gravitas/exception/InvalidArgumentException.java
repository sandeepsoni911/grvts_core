/** InvalidArgumentException.java **/
package com.owners.gravitas.exception;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Exception class for invalid argument exception.
 *
 * @author Harshad Shinde
 */
public class InvalidArgumentException extends IllegalArgumentException {

    /** serialVersionUID. */
    private static final long serialVersionUID = -6107699055857280534L;

    /** list of validation errors. */
    private List< String > errors;

    /**
     * Default constructor.
     *
     * @param errors
     *            are the list of errors.
     */
    public InvalidArgumentException( final String message, final List< String > errors ) {
        super( message + ":" + errors.stream()
        .collect(Collectors.joining(", ")));
        this.errors = errors;
    }

    /**
     * This method returns list of error codes.
     *
     * @return the error code list.
     */
    public List< String > getErrors() {
        return errors;
    }

    /**
     * This method sets the list of error codes.
     *
     * @param errors
     *            the errors to set.
     */
    public void setErrors( final List< String > errors ) {
        this.errors = errors;
    }
    
}
