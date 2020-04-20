package com.owners.gravitas.domain.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.joda.time.DateTime;

import com.owners.gravitas.util.StringUtils;

/**
 * The Class Contact.
 *
 * @author shivamm
 *         The Class Contact.
 */
@Entity( name = "GR_CONTACT" )
public class Contact extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1633548100973832562L;

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
    @JoinColumn( name = "CONTACT_ID", nullable = false )
    private Set< ContactAttribute > contactAttributes;

    /** The contact json attribute. */
    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    @JoinColumn( name = "CONTACT_ID", nullable = false )
    private Set< ContactJsonAttribute > contactJsonAttributes;

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
    @Column( name = "STATE", nullable = true )
    private String state;

    /** The buyer lead score. */
    @Column( name = "BUYER_LEAD_SCORE", nullable = true )
    private String buyerLeadScore;

    /** The opportunity V 1. */
    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    @JoinColumn( name = "CONTACT_ID", nullable = false )
    private Set< Opportunity > opportunities = new HashSet<>();
    
    /** The assignInsideSales. */
    @Column( name = "ASSIGN_INSIDE_SALES", nullable = true )
    private String assignInsideSales;
    
    /** The version. */
    //@Version
    @Column( name = "VERSION" )
    private long version;
    
    public Contact() {
		super();
	}

	public Contact(DateTime createdDate,String crmId, String email, String phone, String company,
			String source, String ownersComId, String stage, String type,
			String state, String buyerLeadScore, String assignInsideSales, String name) {
		super();
		super.setCreatedDate(createdDate);
		this.crmId = crmId;
		this.firstName = name;
		this.email = email;
		this.phone = phone;
		this.company = company;
		this.source = source;
		this.ownersComId = ownersComId;
		this.stage = stage;
		this.type = type;
		this.state = state;
		this.buyerLeadScore = buyerLeadScore;
		this.assignInsideSales = assignInsideSales;
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
    public void setFirstName( final String firstName ) {
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
    public void setLastName( final String lastName ) {
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
    public void setEmail( final String email ) {
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
    public void setPhone( final String phone ) {
        this.phone = StringUtils.formatPhoneNumber(phone);
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
    public void setCompany( final String company ) {
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
    public void setSource( final String source ) {
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
    public void setObjectType( final ObjectType objectType ) {
        this.objectType = objectType;
    }

    /**
     * Gets the contact attribute.
     *
     * @return the contact attribute
     */
    public Set< ContactAttribute > getContactAttributes() {
        return contactAttributes;
    }

    /**
     * Sets the contact attribute.
     *
     * @param contactAttributes
     *            the new contact attribute
     */
    public void setContactAttributes( final Set< ContactAttribute > contactAttributes ) {
        if (this.contactAttributes == null) {
            this.contactAttributes = contactAttributes;
        } else {
            this.contactAttributes.addAll( contactAttributes );
        }
    }

    /**
     * Gets the contact json attribute.
     *
     * @return the contact json attribute
     */
    public Set< ContactJsonAttribute > getContactJsonAttributes() {
        return contactJsonAttributes;
    }

    /**
     * Sets the contact json attribute.
     *
     * @param contactJsonAttributes
     *            the new contact json attribute
     */
    public void setContactJsonAttributes( final Set< ContactJsonAttribute > contactJsonAttributes ) {
        if (this.contactJsonAttributes == null) {
            this.contactJsonAttributes = contactJsonAttributes;
        } else {
            this.contactJsonAttributes.addAll( contactJsonAttributes );
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
    public void setStage( final String stage ) {
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
    public void setType( final String type ) {
        this.type = type;
    }

    /**
     * Gets the opportunity V 1.
     *
     * @return the opportunity V 1
     */
    public Set< Opportunity > getOpportunities() {
        return opportunities;
    }

    /**
     * Sets the opportunity V 1.
     *
     * @param opportunities
     *            the new opportunity V 1
     */
    public void setOpportunities( final Set< Opportunity > opportunities ) {
        this.opportunities = opportunities;
    }

    /**
     * Adds the opportunity.
     *
     * @param opportunity
     *            the opportunity
     */
    public void addOpportunity( final Opportunity opportunity ) {
        this.opportunities.add( opportunity );
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
    public void setOwnersComId( final String ownersComId ) {
        this.ownersComId = ownersComId;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state.
     *
     * @param state
     *            the new state
     */
    public void setState( final String state ) {
        this.state = state;
    }

    /**
     * Gets the buyer lead score.
     *
     * @return the buyer lead score
     */
    public String getBuyerLeadScore() {
        return buyerLeadScore;
    }

    /**
     * Sets the buyer lead score.
     *
     * @param buyerLeadScore
     *            the new buyer lead score
     */
    public void setBuyerLeadScore( final String buyerLeadScore ) {
        this.buyerLeadScore = buyerLeadScore;
    }
    
	/**
	 * @return the assignInsideSales
	 */
	public String getAssignInsideSales() {
		return assignInsideSales;
	}

	/**
	 * @param assignInsideSales the assignInsideSales to set
	 */
	public void setAssignInsideSales(String assignInsideSales) {
		this.assignInsideSales = assignInsideSales;
	}

	/**
	 * @return the version
	 */
	public long getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(long version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Contact [crmId=" + crmId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", phone=" + phone + ", company=" + company + ", source=" + source + ", objectType=" + objectType
				+ ", contactAttributes=" + contactAttributes + ", contactJsonAttributes=" + contactJsonAttributes
				+ ", ownersComId=" + ownersComId + ", stage=" + stage + ", type=" + type + ", state=" + state
				+ ", buyerLeadScore=" + buyerLeadScore + ", opportunities=" + opportunities + ", assignInsideSales="
				+ assignInsideSales + ", version=" + version + "]";
	}
}
