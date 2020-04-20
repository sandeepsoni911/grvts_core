package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

/**
 * The Class ReferenceTypeCode.
 *
 * @author raviz
 */
@Entity( name = "GR_REF_CODE" )
public class RefCode extends AbstractPersistable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6657311717406963006L;

    /** The ref type. */
    @ManyToOne
    @JoinColumn( name = "TYPE_ID", insertable = false, updatable = false )
    private RefType refType;

    /** The code. */
    @Column( name = "CODE", nullable = false, unique = true )
    private String code;

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
     * Gets the ref type.
     *
     * @return the ref type
     */
    public RefType getRefType() {
        return refType;
    }

    /**
     * Sets the ref type.
     *
     * @param refType
     *            the new ref type
     */
    public void setRefType( RefType refType ) {
        this.refType = refType;
    }

    /**
     * Instantiates a new ref code.
     */
    public RefCode() {
    }

    /**
     * Gets the code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code.
     *
     * @param code
     *            the new code
     */
    public void setCode( final String code ) {
        this.code = code;
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
