package com.owners.gravitas.builder;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.VersionBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.Version;
import com.owners.gravitas.dto.request.VersionRequest;

/**
 * The Class VersionBuilderTest.
 *
 * @author nishak
 */
public class VersionBuilderTest extends AbstractBaseMockitoTest {

    /** The version builder. */
    @InjectMocks
    private VersionBuilder versionBuilder;

    /**
     * Test convert to in if.
     */
    @Test(enabled=true)
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
        final Version version = versionBuilder.convertTo( sourceVersions );
        Assert.assertNotNull( version );
    }

    /**
     * Test convert to destination.
     */
    @Test(enabled=true)
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

        Version version = new Version();
        version = versionBuilder.convertTo( sourceVersions, version );
        Assert.assertNotNull( version.getMinVersion() );
    }

    /**
     * Test convert to in if.
     */
    @Test(enabled=true)
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
        final Version version = versionBuilder.convertTo( sourceVersions );
        Assert.assertNotNull( version );
    }

    /**
     * Test convert from to null.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFromToNull() {
        final Version sourceObject = new Version();
        final List< VersionRequest > response = versionBuilder.convertFrom( sourceObject );
        Assert.assertNull( response );
    }


}
