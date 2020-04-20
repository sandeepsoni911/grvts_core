package com.owners.gravitas.domain.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

/**
 * The Class ReferenceType.
 *
 * @author raviz
 */
@Entity( name = "GR_REF_TYPE" )
public class RefType extends AbstractPersistable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3791450400642258036L;

    /** The type. */
    @Column( name = "TYPE", nullable = false )
    private String type;

    /** The reference type codes. */
    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    @JoinColumn( name = "TYPE_ID", nullable = false )
    private Set< RefCode > refCodes;

    /** The created by. */
    @CreatedBy
    @Column( name = "CREATED_BY", updatable = false )
    private String createdBy;

    /** The created date. */
    @CreatedDate
    @Column( name = "CREATED_ON", updatable = false )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime createdDate;

    /**
     * Instantiates a new ref type.
     */
    public RefType() {
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
     * Gets the ref codes.
     *
     * @return the ref codes
     */
    public Set< RefCode > getRefCodes() {
        return refCodes;
    }

    /**
     * Sets the ref codes.
     *
     * @param refCodes
     *            the new ref codes
     */
    public void setRefCodes( final Set< RefCode > refCodes ) {
        this.refCodes = refCodes;
    }

    /**
     * Gets the created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the created by.
     *
     * @param createdBy
     *            the new created by
     */
    public void setCreatedBy( final String createdBy ) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the created date.
     *
     * @return the created date
     */
    public DateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the created date.
     *
     * @param createdDate
     *            the new created date
     */
    public void setCreatedDate( final DateTime createdDate ) {
        this.createdDate = createdDate;
    }

}
