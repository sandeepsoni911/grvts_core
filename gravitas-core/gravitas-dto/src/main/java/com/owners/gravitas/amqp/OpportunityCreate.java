package com.owners.gravitas.amqp;

import com.owners.gravitas.dto.BaseDTO;
import com.owners.gravitas.dto.Contact;

/**
 * The Class OpportunityHolder holds opportunity create details.
 *
 * @author shivamm
 */
public class OpportunityCreate extends BaseDTO {

    /** The crm id. */
    private String crmId;

    /** For Serialization. */
    private static final long serialVersionUID = 1495858877151261992L;

    /** The contacts. */
    private Contact primaryContact;

    /** The opportunity type. */
    private String opportunityType;

    /**
     * Gets the crm id.
     *
     * @return the crm id
     */
    public String getCrmId() {
        return crmId;
    }

    /**
     * Sets the crm id.
     *
     * @param crmId
     *            the new crm id
     */
    public void setCrmId( String crmId ) {
        this.crmId = crmId;
    }

    /**
     * Gets the primary contact.
     *
     * @return the primary contact
     */
    public Contact getPrimaryContact() {
        return primaryContact;
    }

    /**
     * Sets the primary contact.
     *
     * @param primaryContact
     *            the new primary contact
     */
    public void setPrimaryContact( Contact primaryContact ) {
        this.primaryContact = primaryContact;
    }

    /**
     * Gets the opportunity type.
     *
     * @return the opportunity type
     */
    public String getOpportunityType() {
        return opportunityType;
    }

    /**
     * Sets the opportunity type.
     *
     * @param opportunityType
     *            the new opportunity type
     */
    public void setOpportunityType( String opportunityType ) {
        this.opportunityType = opportunityType;
    }
}
