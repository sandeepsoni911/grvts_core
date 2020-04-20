package com.owners.gravitas.domain.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * The Class Group.
 *
 * @author raviz
 */
@Entity( name = "GR_GROUP" )
public class Group extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1653830963301097447L;

    /** The name. */
    @Column( name = "NAME", nullable = false )
    private String name;

    /** The deleted. */
    @Column( name = "DELETED", nullable = false )
    private boolean deleted;

    /** The group okr. */
    @OneToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "group" )
    public GroupOkr groupOkr;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "GROUP_TYPE_CODE_ID", nullable = false )
    private RefCode refCode;

    /**
     * Instantiates a new group.
     */
    public Group() {
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setName( final String name ) {
        this.name = name;
    }

    /**
     * Checks if is deleted.
     *
     * @return true, if is deleted
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the deleted.
     *
     * @param deleted
     *            the new deleted
     */
    public void setDeleted( final boolean deleted ) {
        this.deleted = deleted;
    }

    /**
     * Gets the group okr.
     *
     * @return the group okr
     */
    public GroupOkr getGroupOkr() {
        return groupOkr;
    }

    /**
     * Sets the group okr.
     *
     * @param groupOkr
     *            the new group okr
     */
    public void setGroupOkr( final GroupOkr groupOkr ) {
        this.groupOkr = groupOkr;
    }

    /**
     * Gets the ref code.
     *
     * @return the ref code
     */
    public RefCode getRefCode() {
        return refCode;
    }

    /**
     * Sets the ref code.
     *
     * @param refCode
     *            the new ref code
     */
    public void setRefCode( final RefCode refCode ) {
        this.refCode = refCode;
    }
}
