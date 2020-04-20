package com.owners.gravitas.domain.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 
 * @author gururasm
 *
 */
@Entity( name = "GR_CONTACT_LOG" )
public class ContactLog extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8098380219820449474L;

    /** The crm id. */
    @Column( name = "CONTACT_ID", nullable = false )
    private String contactId;
    
    /** The crm id. */
    @Column( name = "CRM_ID", nullable = true )
    private String crmId;

    /** The first name. */
    @Column( name = "FIRST_NAME", nullable = true )
    private String firstName;

    /** The last name. */
    @Column( name = "LAST_NAME", nullable = false )
    private String lastName;

    /** The email. */
    @Column( name = "EMAIL", nullable = false )
    private String email;

    /** The phone. */
    @Column( name = "PHONE", nullable = true )
    private String phone;

    /** The company. */
    @Column( name = "COMPANY", nullable = true )
    private String company;

    /** The source. */
    @Column( name = "SOURCE", nullable = true )
    private String source;

    /** The object type. */
    @ManyToOne
    @JoinColumn( nullable = false, name = "OBJECT_ID" )
    private ObjectType objectType;

    /** The contact attribute. */
    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    @JoinColumn( name = "CONTACT_LOG_ID", nullable = false )
    private Set< ContactAttributeLog > contactAttributeLogs;

    /** The contact json attribute. */
    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    @JoinColumn( name = "CONTACT_LOG_ID", nullable = false )
    private Set< ContactJsonAttributeLog > contactJsonAttributeLogs;

    /** The ownersComId. */
    @Column( name = "OWNERSCOM_id", nullable = true )
    private String ownersComId;

    /** The stage. */
    @Column( name = "STAGE", nullable = false )
    private String stage;

    /** The type. */
    @Column( name = "TYPE", nullable = false )
    private String type;
    
    /** The state. */
    @Column( name = "state", nullable = true )
    private String state;

    /** The buyer lead score. */
    @Column( name = "buyer_lead_score", nullable = true )
    private String buyerLeadScore;
    
    /**
     * @return the contactId
     */
    public String getContactId() {
        return contactId;
    }

    /**
     * @param contactId
     *            the contactId to set
     */
    public void setContactId( String contactId ) {
        this.contactId = contactId;
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
    public void setCrmId( String crmId ) {
        this.crmId = crmId;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName
     *            the new first name
     */
    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName
     *            the new last name
     */
    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email
     *            the new email
     */
    public void setEmail( String email ) {
        this.email = email;
    }

    /**
     * Gets the phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone.
     *
     * @param phone
     *            the new phone
     */
    public void setPhone( String phone ) {
        this.phone = phone;
    }

    /**
     * Gets the company.
     *
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * Sets the company.
     *
     * @param company
     *            the new company
     */
    public void setCompany( String company ) {
        this.company = company;
    }

    /**
     * Gets the source.
     *
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the source.
     *
     * @param source
     *            the new source
     */
    public void setSource( String source ) {
        this.source = source;
    }

    /**
     * Gets the object type.
     *
     * @return the object type
     */
    public ObjectType getObjectType() {
        return objectType;
    }

    /**
     * Sets the object type.
     *
     * @param objectType
     *            the new object type
     */
    public void setObjectType( ObjectType objectType ) {
        this.objectType = objectType;
    }

    /**
     * @return the contactAttributeLogs
     */
    public Set< ContactAttributeLog > getContactAttributeLogs() {
        return contactAttributeLogs;
    }

    /**
     * @param contactAttributeLogs the contactAttributeLogs to set
     */
    public void setContactAttributeLogs( Set< ContactAttributeLog > contactAttributeLogs ) {
        this.contactAttributeLogs = contactAttributeLogs;
        if (this.contactAttributeLogs == null) {
            this.contactAttributeLogs = contactAttributeLogs;
        } else {
            this.contactAttributeLogs.addAll( contactAttributeLogs );
        }
    }

    /**
     * @return the contactJsonAttributeLogs
     */
    public Set< ContactJsonAttributeLog > getContactJsonAttributeLogs() {
        return contactJsonAttributeLogs;
    }

    /**
     * @param contactJsonAttributeLogs the contactJsonAttributeLogs to set
     */
    public void setContactJsonAttributeLogs( Set< ContactJsonAttributeLog > contactJsonAttributeLogs ) {
        this.contactJsonAttributeLogs = contactJsonAttributeLogs;
        if (this.contactJsonAttributeLogs == null) {
            this.contactJsonAttributeLogs = contactJsonAttributeLogs;
        } else {
            this.contactJsonAttributeLogs.addAll( contactJsonAttributeLogs );
        }
    }

    /**
     * Gets the stage.
     *
     * @return the stage
     */
    public String getStage() {
        return stage;
    }

    /**
     * Sets the stage.
     *
     * @param stage
     *            the new stage
     */
    public void setStage( String stage ) {
        this.stage = stage;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type
     *            the new type
     */
    public void setType( String type ) {
        this.type = type;
    }

    /**
     * Gets the owners com id.
     *
     * @return the owners com id
     */
    public String getOwnersComId() {
        return ownersComId;
    }

    /**
     * Sets the owners com id.
     *
     * @param ownersComId
     *            the new owners com id
     */
    public void setOwnersComId( String ownersComId ) {
        this.ownersComId = ownersComId;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state
     *            the state to set
     */
    public void setState( String state ) {
        this.state = state;
    }

    /**
     * @return the buyerLeadScore
     */
    public String getBuyerLeadScore() {
        return buyerLeadScore;
    }

    /**
     * @param buyerLeadScore
     *            the buyerLeadScore to set
     */
    public void setBuyerLeadScore( String buyerLeadScore ) {
        this.buyerLeadScore = buyerLeadScore;
    }
}
