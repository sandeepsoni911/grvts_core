package com.owners.gravitas.dto.crm.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.owners.gravitas.dto.BaseDTO;

public class CRMOpportunityContactRoleRequest extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1634913322407977692L;

    /** The contact id. */
    private String contactId;

    /** The is primary. */
    private boolean isPrimary;

    /** The opportunity id. */
    private String opportunityId;

    /** The role. */
    private String role;

    /**
     * Gets the contact id.
     *
     * @return the contact id
     */
    public String getContactId() {
        return contactId;
    }

    /**
     * Sets the contact id.
     *
     * @param contactId
     *            the new contact id
     */
    @JsonProperty( "ContactId" )
    public void setContactId( String contactId ) {
        this.contactId = contactId;
    }

    /**
     * Checks if is primary.
     *
     * @return true, if is primary
     */
    public boolean isPrimary() {
        return isPrimary;
    }

    /**
     * Sets the primary.
     *
     * @param isPrimary
     *            the new primary
     */
    @JsonProperty( "IsPrimary" )
    public void setPrimary( boolean isPrimary ) {
        this.isPrimary = isPrimary;
    }

    /**
     * Gets the opportunity id.
     *
     * @return the opportunity id
     */
    public String getOpportunityId() {
        return opportunityId;
    }

    /**
     * Sets the opportunity id.
     *
     * @param opportunityId
     *            the new opportunity id
     */
    @JsonProperty( "OpportunityId" )
    public void setOpportunityId( String opportunityId ) {
        this.opportunityId = opportunityId;
    }

    /**
     * Gets the role.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role.
     *
     * @param role
     *            the new role
     */
    @JsonProperty( "Role" )
    public void setRole( String role ) {
        this.role = role;
    }

}
