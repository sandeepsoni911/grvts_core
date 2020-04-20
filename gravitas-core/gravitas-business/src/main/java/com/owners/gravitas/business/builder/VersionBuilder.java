/**
 *
 */
package com.owners.gravitas.business.builder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.Version;
import com.owners.gravitas.dto.request.VersionRequest;
import com.owners.gravitas.enums.GravitasClientType;

/**
 * The Class VersionBuilder which converts List< VersionRequest > to a
 * Version object.
 *
 * @author nishak
 *
 */
@Component
public class VersionBuilder extends AbstractBuilder< List< VersionRequest >, Version > {

    /** The URL constant. */
    private static final String URL = "url";

    /**
     * This method converts from List< VersionRequest > as source to Map<
     * String, Map< String, ClientVersion > > as destination.
     *
     * @param source
     *            - List< VersionRequest > objects.
     * @param destination
     *            - Version object.
     */
    @Override
    public Version convertTo( final List< VersionRequest > source, final Version destination ) {
        final Map< String, Object > ios = new HashMap<>();
        final Map< String, Object > android = new HashMap<>();
        Version version = destination;
        if (CollectionUtils.isNotEmpty( source )) {
            if (version == null) {
                version = new Version();
            }
            for ( final VersionRequest sourceRequest : source ) {
                version = new Version();
                version.setCurrentMessage( sourceRequest.getCurrentMessage() );
                version.setCurrentVersion( sourceRequest.getCurrentVersion() );
                version.setMinMessage( sourceRequest.getMinMessage() );
                version.setMinVersion( sourceRequest.getMinVersion() );
                if (sourceRequest.getClientType().equals( GravitasClientType.AgentAppIOS.getValue() )) {
                    ios.put( URL, sourceRequest.getUrl() );
                } else if (sourceRequest.getClientType().equals( GravitasClientType.AgentAppAndroid.getValue() )) {
                    android.put( URL, sourceRequest.getUrl() );
                }
            }
            version.setIos( ios );
            version.setAndroid( android );
        }
        return version;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public List< VersionRequest > convertFrom( final Version source, final List< VersionRequest > destination ) {
        throw new UnsupportedOperationException();
    }

}
