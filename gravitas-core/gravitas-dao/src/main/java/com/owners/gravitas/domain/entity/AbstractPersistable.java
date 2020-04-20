package com.owners.gravitas.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

/**
 * This is base class for all entities. It allows parameterization of Id and
 * choose auto generation strategy.
 *
 * @author vishwanathm
 *
 */
@MappedSuperclass
public abstract class AbstractPersistable implements Serializable, Persistable< String > {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8027101912375706763L;

    /** The id. */
    @Id
    @GeneratedValue( generator = "system-uuid" )
    @GenericGenerator( name = "system-uuid", strategy = "uuid2" )
    @Column( name = "ID" )
    private String id;

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Persistable#getId()
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of the entity.
     *
     * @param id
     *            the id to set
     */
    public void setId( final String id ) {
        this.id = id;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Persistable#isNew()
     */
    public boolean isNew() {
        return null == getId();
    }

}
