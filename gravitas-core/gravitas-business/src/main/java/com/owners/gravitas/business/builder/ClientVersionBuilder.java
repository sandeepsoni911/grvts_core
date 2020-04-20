package com.owners.gravitas.business.builder;

import static com.owners.gravitas.constants.Constants.VERSION_INFO;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.ClientVersion;
import com.owners.gravitas.dto.request.VersionRequest;

/**
 * The Class ClientVersionBuilder which converts List< VersionRequest > to a
 * Map< String, Map< String, ClientVersion >.
 *
 * @author nishak
 *
 */
@Component
public class ClientVersionBuilder
        extends AbstractBuilder< List< VersionRequest >, Map< String, Map< String, ClientVersion > > > {

    /**
     * This method converts to List< VersionRequest > as source to Map<
     * String, Map< String, ClientVersion > > as destination.
     *
     * @param source
     *            - List< VersionRequest > objects.
     * @param destination
     *            - Map< String, Map< String, ClientVersion > > objects.
     * @return the map
     */
    @Override
    public Map< String, Map< String, ClientVersion > > convertTo( final List< VersionRequest > source,
            final Map< String, Map< String, ClientVersion > > destination ) {
        Map< String, Map< String, ClientVersion > > clientVersions = destination;
        if (isNotEmpty( source )) {
            if (clientVersions == null) {
                clientVersions = new HashMap<>();
            }
            Map< String, ClientVersion > versionInfoMap;
            ClientVersion clientVersion;
            for ( final VersionRequest version : source ) {
                versionInfoMap = new HashMap<>();
                clientVersion = new ClientVersion();
                clientVersion.setCurrentMessage( version.getCurrentMessage() );
                clientVersion.setCurrentVersion( version.getCurrentVersion() );
                clientVersion.setMinMessage( version.getMinMessage() );
                clientVersion.setMinVersion( version.getMinVersion() );
                clientVersion.setUrl( version.getUrl() );
                versionInfoMap.put( VERSION_INFO, clientVersion );
                clientVersions.put( version.getClientType(), versionInfoMap );
            }
        }
        return clientVersions;
    }

    /**
     * Convert from.
     *
     * @param source
     *            - List< VersionRequest > objects.
     * @param destination
     *            - Map< String, Map< String, ClientVersion > > objects.
     * @return the map
     */
    @Override
    public List< VersionRequest > convertFrom( Map< String, Map< String, ClientVersion > > source,
            List< VersionRequest > destination ) {
        throw new UnsupportedOperationException( "convertFrom operation is not supported" );
    }

}
