package com.owners.gravitas.exception;

import static com.owners.gravitas.enums.ErrorCode.BIGQUERY_DATA_ERROR;

/**
 * Exception to denote no agent found for query.
 *
 * @author vishwanathm
 */
public class BigQueryDataException extends ApplicationException {

    /** For serialization. */
    private static final long serialVersionUID = -5579941357405609160L;

    /**
     * Constructor to set error message.
     *
     * @param message
     *            - error message
     * @param exception
     *            the exception
     */
    public BigQueryDataException( final String message, final Throwable exception ) {
        super( message, exception, BIGQUERY_DATA_ERROR );
    }

}
