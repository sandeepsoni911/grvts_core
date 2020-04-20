package com.owners.gravitas.domain.entity;

import java.util.ArrayList;
import java.util.List;

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
 * The Class ActionGroup.
 *
 * @author shivamm
 */
@Entity( name = "GR_ACTION_GROUP" )
public class ActionGroup extends AbstractPersistable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1633548100973832562L;

    /** The label. */
    @Column( name = "LABEL", nullable = false )
    private String label;

    /** The created by. */
    @CreatedBy
    @Column( name = "CREATED_BY", updatable = false )
    private String createdBy;

    /** The created date. */
    @CreatedDate
    @Column( name = "CREATED_ON", updatable = false )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime createdDate;

    /** The opportunities. */
    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    @JoinColumn( name = "ACTION_GROUP_ID", nullable = false )
    private List< Action > actions = new ArrayList<>();

    /**
     * Gets the label.
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label.
     *
     * @param label
     *            the new label
     */
    public void setLabel( final String label ) {
        this.label = label;
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

    /**
     * Gets the actions.
     *
     * @return the actions
     */
    public List< Action > getActions() {
        return actions;
    }

    /**
     * Sets the actions.
     *
     * @param actions the new actions
     */
    public void setActions( final List< Action > actions ) {
        this.actions = actions;
    }
}
