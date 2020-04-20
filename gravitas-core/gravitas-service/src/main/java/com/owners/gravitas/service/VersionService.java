package com.owners.gravitas.service;

import java.util.Map;

import com.owners.gravitas.domain.ClientVersion;
import com.owners.gravitas.domain.Version;

/**
 * The Interface VersionService.
 *
 * @author nishak
 */
public interface VersionService {

    /**
     * Adds/updates the client version info.
     *
     * @param clientVersionInfoMap
     *            - Map of ClientVersion (current version object).
     * @param oldVersionInfoMap
     *            - Map of Version (old version object).
     */
    void addUpdateVersion( final Map< String, Map< String, ClientVersion > > clientVersionInfoMap,
            final Map< String, Version > oldVersionInfoMap );

}
