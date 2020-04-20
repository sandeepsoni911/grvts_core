package com.owners.gravitas.dao;

import java.util.Map;

import com.owners.gravitas.domain.ClientVersion;
import com.owners.gravitas.domain.Version;

/**
 * The Interface VersionDao.
 *
 * @author nishak
 */
public interface VersionDao {

    /**
     * Saves the application version info.
     *
     * @param clientVersionInfo
     *            the client version info
     * @param oldVersionInfo
     *            the old version info
     */
    void saveVersion( Map< String, Map< String, ClientVersion > > clientVersionInfo,
            final Map< String, Version > oldVersionInfo );

}
