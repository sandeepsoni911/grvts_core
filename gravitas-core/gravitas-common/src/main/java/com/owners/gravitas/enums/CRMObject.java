/**
 *
 */
package com.owners.gravitas.enums;

/**
 * The Enum CRMObject.
 *
 * @author harshads
 */
public enum CRMObject {

    /** The lead. */
    LEAD( "Lead" ),

    /** The queue. */
    QUEUE( "Queue" ),

    /** The account. */
    ACCOUNT( "Account" ),

    /** The opportunity. */
    OPPORTUNITY( "Opportunity" ),

    /** The contact. */
    CONTACT( "Contact" ),

    /** The agent. */
    AGENT( "Agent__c" );

    /** The crm object. */
    private String name;

    /**
     * Instantiates a new CRM objects.
     *
     * @param object
     *            the object
     */
    private CRMObject( String object ) {
        this.name = object;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
}
