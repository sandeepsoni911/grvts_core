package com.owners.gravitas.business.impl;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.ClientVersionBuilder;
import com.owners.gravitas.business.builder.VersionBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.ClientVersion;
import com.owners.gravitas.domain.Version;
import com.owners.gravitas.dto.request.VersionRequest;
import com.owners.gravitas.service.VersionService;
import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class VersionBusinessServiceImplTest.
 *
 * @author nishak
 */
public class VersionBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The version business service impl. */
    @InjectMocks
    private VersionBusinessServiceImpl versionBusinessServiceImpl;

    /** The version service. */
    @Mock
    private VersionService versionService;

    /** The client version builder. */
    @Mock
    private ClientVersionBuilder clientVersionBuilder;

    /** The version builder. */
    @Mock
    private VersionBuilder versionBuilder;

    /** The property writer. */
    @Mock
    private PropertyWriter propertyWriter;

    /**
     * Test update client version with-if i.e. to update current and
     * old version info node.
     */
    @Test
    public void testUpdateClientVersionWithIf() {
        final Map< String, Map< String, ClientVersion > > clientVersionMap = clientVersionBuilder
                .convertTo( Mockito.anyListOf( VersionRequest.class ) );
        final Version version = versionBuilder.convertTo( Mockito.anyListOf( VersionRequest.class ) );
        final Map< String, Version > oldVersionMap = new HashMap<>();
        oldVersionMap.put( "versionInfo", version );
        versionBusinessServiceImpl.setUpdateOldVersionInfo( true );
        Mockito.doNothing().when( versionService ).addUpdateVersion( clientVersionMap, oldVersionMap );
        versionBusinessServiceImpl.updateClientVersion( Mockito.anyListOf( VersionRequest.class ) );
    }

    /**
     * Test update client version without-if i.e. not to update old version
     * info.
     */
    @Test
    public void testUpdateClientVersionWithoutIf() {
        Map< String, Map< String, ClientVersion > > clientVersionMap = new HashMap<>();
        versionBusinessServiceImpl.setUpdateOldVersionInfo( false );
        Mockito.doNothing().when( versionService ).addUpdateVersion( clientVersionMap, null );
        versionBusinessServiceImpl.updateClientVersion( Mockito.anyListOf( VersionRequest.class ) );
        Mockito.verifyZeroInteractions( versionBuilder );
    }

}
