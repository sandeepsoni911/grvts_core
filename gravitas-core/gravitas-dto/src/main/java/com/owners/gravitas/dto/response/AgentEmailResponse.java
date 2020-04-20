package com.owners.gravitas.dto.response;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * The Class AgentEmailResponse.
 *
 * @author ankusht
 */
public class AgentEmailResponse implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2293361750204825692L;

    /** The results. */
    private List< AgentEmailResult > results;

    /**
     * Adds the result.
     *
     * @param result
     *            the result
     */
    public void addResult( final AgentEmailResult result ) {
        if (results == null) {
            results = new LinkedList<>();
        }
        results.add( result );
    }

    /**
     * Clear results.
     */
    public void clearResults() {
        if (results == null) {
            results = new LinkedList<>();
        } else {
            results.clear();
        }
    }

    /**
     * Gets the results.
     *
     * @return the results
     */
    public List< AgentEmailResult > getResults() {
        return results;
    }

    /**
     * Sets the results.
     *
     * @param results
     *            the new results
     */
    public void setResults( List< AgentEmailResult > results ) {
        this.results = results;
    }
}
