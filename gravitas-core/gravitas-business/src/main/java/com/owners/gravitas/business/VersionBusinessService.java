/**
 * 
 */
package com.owners.gravitas.business;

import java.util.List;

import com.owners.gravitas.dto.request.VersionRequest;

/**
 * The Interface VersionBusinessService.
 * 
 * @author nishak
 *
 */
public interface VersionBusinessService {

    /**
     * Builds the version info map from list of version request. Also updates
     * the old version request nodes based on flag.
     * 
     * @param versionInfo
     *            - List of <code>VersionRequest</code> instance.
     */
    void updateClientVersion( final List< VersionRequest > versionInfo );

}
