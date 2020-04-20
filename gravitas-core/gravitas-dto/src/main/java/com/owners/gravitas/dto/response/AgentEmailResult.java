package com.owners.gravitas.dto.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.owners.gravitas.dto.ErrorDetail;

/**
 * The Class AgentEmailResult.
 * 
 * @author ankusht
 */
@JsonInclude(Include.NON_NULL)
public class AgentEmailResult implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8900973799237875476L;

    /** The status. */
    private String status;

    /** The errors. */
    private List< ErrorDetail > errors;

    /**
     * Instantiates a new agent email result.
     *
     * @param status
     *            the status
     * @param errors
     *            the errors
     */
    public AgentEmailResult( final String status, final List< ErrorDetail > errors ) {
        super();
        this.status = status;
        this.errors = errors;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status
     *            the new status
     */
    public void setStatus( final String status ) {
        this.status = status;
    }

    /**
     * Gets the errors.
     *
     * @return the errors
     */
    public List< ErrorDetail > getErrors() {
        return errors;
    }

    /**
     * Sets the errors.
     *
     * @param errors
     *            the new errors
     */
    public void setErrors( final List< ErrorDetail > errors ) {
        this.errors = errors;
    }
}
