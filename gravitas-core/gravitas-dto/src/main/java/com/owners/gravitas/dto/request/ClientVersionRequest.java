package com.owners.gravitas.dto.request;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class ClientVersionRequest.
 *
 * @author nishak
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class ClientVersionRequest {

    /** List of versionList. */
    @Valid
    private List< VersionRequest > versionList;

    /**
     * Gets the versionList.
     *
     * @return the versionList
     */
    public List< VersionRequest > getVersionList() {
        return versionList;
    }

    /**
     * Sets the versionList.
     *
     * @param versionList
     *            the versionList to set
     */
    public void setVersionList( final List< VersionRequest > versionList ) {
        this.versionList = versionList;
    }
}
