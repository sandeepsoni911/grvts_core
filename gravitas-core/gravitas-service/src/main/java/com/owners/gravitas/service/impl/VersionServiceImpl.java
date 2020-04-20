package com.owners.gravitas.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.dao.VersionDao;
import com.owners.gravitas.domain.ClientVersion;
import com.owners.gravitas.domain.Version;
import com.owners.gravitas.service.VersionService;

/**
 * The Class VersionServiceImpl.
 *
 * @author nishak
 */
@Service
public class VersionServiceImpl implements VersionService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( VersionServiceImpl.class );

    /** VersionDao instance. */
    @Autowired
    private VersionDao versionDao;

    /**
     * Adds/updates the client version info.
     *
     * @param clientVersionInfoMap
     *            - Map of ClientVersion (current version object).
     * @param oldVersionInfoMap
     *            - Map of Version (old version object).
     */
    @Override
    public void addUpdateVersion( final Map< String, Map< String, ClientVersion > > clientVersionInfoMap,
            final Map< String, Version > oldVersionInfoMap ) {
        LOGGER.info( "Add/update the client version info. " );
        versionDao.saveVersion( clientVersionInfoMap, oldVersionInfoMap );
    }

}
