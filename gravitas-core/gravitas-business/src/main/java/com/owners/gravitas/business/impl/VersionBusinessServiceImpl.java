package com.owners.gravitas.business.impl;

import static com.owners.gravitas.constants.Constants.VERSION_INFO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import com.owners.gravitas.business.VersionBusinessService;
import com.owners.gravitas.business.builder.ClientVersionBuilder;
import com.owners.gravitas.business.builder.VersionBuilder;
import com.owners.gravitas.domain.ClientVersion;
import com.owners.gravitas.domain.Version;
import com.owners.gravitas.dto.request.VersionRequest;
import com.owners.gravitas.service.VersionService;
import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class VersionBusinessServiceImpl.
 *
 * @author nishak
 */
@ManagedResource( objectName = "com.owners.gravitas:name=VersionBusinessServiceImpl" )
@Service
public class VersionBusinessServiceImpl implements VersionBusinessService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( VersionBusinessServiceImpl.class );

    /** The version service. */
    @Autowired
    private VersionService versionService;

    /** Boolean to update old version Info. */
    @Value( value = "${old.version.info.update:true}" )
    private boolean updateOldVersionInfo;

    /** The client version builder. */
    @Autowired
    private ClientVersionBuilder clientVersionBuilder;

    /** The version builder. */
    @Autowired
    private VersionBuilder versionBuilder;

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /**
     * Builds the version info map from list of version request. Also updates
     * the old version request nodes based on flag.
     *
     * @param versions
     *            - List of <code>VersionRequest</code> instance.
     */
    @Override
    public void updateClientVersion( final List< VersionRequest > versions ) {
        LOGGER.info( "Build version info map. " );
        final Map< String, Map< String, ClientVersion > > clientVersionInfoMap = clientVersionBuilder
                .convertTo( versions );
        Map< String, Version > oldVersionInfoMap = null;
        if (isUpdateOldVersionInfo()) {
            oldVersionInfoMap = new HashMap<>();
            oldVersionInfoMap.put( VERSION_INFO, versionBuilder.convertTo( versions ) );
        }
        versionService.addUpdateVersion( clientVersionInfoMap, oldVersionInfoMap );
    }

    /**
     * Returns boolean to update old version info.
     *
     * @return the updateOldVersionInfo
     */
    @ManagedAttribute
    public final boolean isUpdateOldVersionInfo() {
        return updateOldVersionInfo;
    }

    /**
     * Sets boolean to update old version info.
     *
     * @param updateOldVersionInfo
     *            the updateOldVersionInfo to set
     */
    @ManagedAttribute
    public final void setUpdateOldVersionInfo( final boolean updateOldVersionInfo ) {
        this.updateOldVersionInfo = updateOldVersionInfo;
        propertyWriter.saveJmxProperty( "old.version.info.update", updateOldVersionInfo );
    }
}
