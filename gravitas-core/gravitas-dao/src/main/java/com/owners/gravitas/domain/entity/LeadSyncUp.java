package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.joda.time.DateTime;

/**
 * The Class LeadSyncUp.
 *
 * @author kushwaja
 *         The Class LeadSyncUp.
 */
@Entity( name = "GR_LEAD_SYNC_UP" )
public class LeadSyncUp extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1633548100973832562L;

    /** The crm id. */
    @Column( name = "CRM_ID", nullable = false )
    private String crmId;

    /** The value. */
    @Column( name = "LOG_VALUE", nullable = true )
    private String value;

    
    public LeadSyncUp() {
		super();
	}

	public LeadSyncUp(DateTime createdDate,String crmId, String value) {
		super();
		super.setCreatedDate(createdDate);
		this.crmId = crmId;
		this.value = value;
	}
    
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
    public void setCrmId( final String crmId ) {
        this.crmId = crmId;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value.
     *
     * @param value
     *            the new value
     */
    public void setValue( final String value ) {
        this.value = value;
    }

}
