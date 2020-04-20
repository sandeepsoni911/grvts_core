package com.owners.gravitas.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * creates data node under agent info tab in FB
 * 
 * @author gururasm
 *
 */
@JsonInclude( JsonInclude.Include.NON_NULL )
public class AgentPreference extends BaseDomain {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -21124128984108620L;

    private Object signature;

    /** The object. */
    private Object object;

    /** The last modified dtm. */
    private Long lastModifiedDtm;

    /**
     * @return the signature
     */
    public Object getSignature() {
        return signature;
    }

    /**
     * @param signature
     *            the signature to set
     */
    public void setSignature( final Object signature ) {
        this.signature = signature;
    }

    /**
     * @return the lastModifiedDtm
     */
    public Long getLastModifiedDtm() {
        return lastModifiedDtm;
    }

    /**
     * @param lastModifiedDtm
     *            the lastModifiedDtm to set
     */
    public void setLastModifiedDtm( final Long lastModifiedDtm ) {
        this.lastModifiedDtm = lastModifiedDtm;
    }

    /**
     * Gets the object.
     *
     * @return the object
     */
    public Object getObject() {
        return object;
    }

    /**
     * Sets the object.
     *
     * @param object
     *            the new object
     */
    public void setObject( final Object object ) {
        this.object = object;
    }
}
