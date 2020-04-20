package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.domain.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Abstract base class for auditable entities. Stores the audit values in
 * persistent fields. "Borrowed" from spring-data-jpa.
 *
 * ILR - Added in onPersist and onCreate. These will put in dates and users.
 *
 * @author vishwanathm
 */
@MappedSuperclass
@EntityListeners( AuditingEntityListener.class )
public abstract class AbstractAuditable extends AbstractPersistable implements Auditable< String, String > {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6615842299925446677L;

    @Column( name = "CREATED_BY", updatable = false )
    private String createdBy;

    @Column( name = "CREATED_ON", updatable = false )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime createdDate;

    @Column( name = "LAST_UPDATED_BY" )
    private String lastModifiedBy;

    @Column( name = "LAST_UPDATED_ON" )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime lastModifiedDate;

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Auditable#getCreatedBy()
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.data.domain.Auditable#setCreatedBy(java.lang.Object)
     */
    public void setCreatedBy( final String createdBy ) {
        this.createdBy = createdBy;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Auditable#getCreatedDate()
     */
    public DateTime getCreatedDate() {
        return createdDate == null ? null : createdDate;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.data.domain.Auditable#setCreatedDate(org.joda.time
     * .DateTime)
     */
    public void setCreatedDate( final DateTime createdDate ) {
        this.createdDate = createdDate == null ? null : createdDate;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Auditable#getLastModifiedBy()
     */
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.data.domain.Auditable#setLastModifiedBy(java.lang
     * .Object)
     */
    public void setLastModifiedBy( final String lastModifiedBy ) {
        this.lastModifiedBy = lastModifiedBy;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Auditable#getLastModifiedDate()
     */
    public DateTime getLastModifiedDate() {
        return lastModifiedDate == null ? null : lastModifiedDate;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.data.domain.Auditable#setLastModifiedDate(org.joda
     * .time.DateTime)
     */
    public void setLastModifiedDate( final DateTime lastModifiedDate ) {
        this.lastModifiedDate = lastModifiedDate == null ? null : lastModifiedDate;
    }

}
