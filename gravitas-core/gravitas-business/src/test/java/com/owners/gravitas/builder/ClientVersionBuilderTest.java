package com.owners.gravitas.builder;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.ClientVersionBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.ClientVersion;
import com.owners.gravitas.dto.request.VersionRequest;

/**
 * The Class ClientVersionBuilderTest.
 *
 * @author nishak
 */
public class ClientVersionBuilderTest extends AbstractBaseMockitoTest {

    /** The version builder. */
    @InjectMocks
    private ClientVersionBuilder clientVersionBuilder;

    /**
     * Test convert to in if.
     */
    @Test
    public void testConvertToInIf() {
        final List< VersionRequest > sourceVersions = new ArrayList< VersionRequest >();
        final VersionRequest request = new VersionRequest();
        request.setClientType( "AgentApp-IOS" );
        request.setCurrentMessage( "CurrentMessage" );
        request.setCurrentVersion( "CurrentVersion" );
        request.setMinMessage( "MinMessage" );
        request.setMinVersion( "MinVersion" );
        request.setUrl( "http://www.google.com" );
        sourceVersions.add( request );
        final Map< String, Map< String, ClientVersion > > versionMap = clientVersionBuilder.convertTo( sourceVersions );
        assertNotNull( versionMap.isEmpty() );
    }

    /**
     * Test convert to destination.
     */
    @Test
    public void testConvertToDestination() {
        final List< VersionRequest > sourceVersions = new ArrayList< VersionRequest >();
        final VersionRequest request = new VersionRequest();
        request.setClientType( "AgentApp-IOS" );
        request.setCurrentMessage( "CurrentMessage" );
        request.setCurrentVersion( "CurrentVersion" );
        request.setMinMessage( "MinMessage" );
        request.setMinVersion( "MinVersion" );
        request.setUrl( "http://www.google.com" );
        sourceVersions.add( request );

        Map< String, Map< String, ClientVersion > > versionMap = new HashMap<>();
        versionMap = clientVersionBuilder.convertTo( sourceVersions, versionMap );
        assertFalse( versionMap.isEmpty() );
    }

    /**
     * Test convert to in if.
     */
    @Test
    public void testConvertToInElseIf() {
        final List< VersionRequest > sourceVersions = new ArrayList< VersionRequest >();
        final VersionRequest request = new VersionRequest();
        request.setClientType( "AgentApp-Android" );
        request.setCurrentMessage( "CurrentMessage" );
        request.setCurrentVersion( "CurrentVersion" );
        request.setMinMessage( "MinMessage" );
        request.setMinVersion( "MinVersion" );
        request.setUrl( "http://www.google.com" );
        sourceVersions.add( request );
        final Map< String, Map< String, ClientVersion > > versionMap = clientVersionBuilder.convertTo( sourceVersions );
        assertFalse( versionMap.isEmpty() );
    }

    /**
     * Test convert from to null.
     */
    @Test(expectedExceptions=UnsupportedOperationException.class)
    public void testConvertFromToNull() {
        clientVersionBuilder.convertFrom( new HashMap<>() );
    }
}
