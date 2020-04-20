package com.owners.gravitas.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.owners.gravitas.dto.Group;

/**
 * The Class GroupDetailResponse.
 *
 * @author raviz
 */
public class GroupDetailResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6529243385708390905L;

    /** The groups. */
    private List< Group > groups = new ArrayList<>();

    /**
     * Instantiates a new group detail response.
     */
    public GroupDetailResponse() {

    }

    /**
     * Instantiates a new group detail response.
     *
     * @param groups
     *            the groups
     */
    public GroupDetailResponse( final List< Group > groups ) {
        this.groups = groups;
    }

    /**
     * Gets the groups.
     *
     * @return the groups
     */
    public List< Group > getGroups() {
        return groups;
    }

    /**
     * Sets the groups.
     *
     * @param groups
     *            the new groups
     */
    public void setGroups( final List< Group > groups ) {
        this.groups = groups;
    }
}
