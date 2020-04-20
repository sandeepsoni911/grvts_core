/*
 * 
 */
package com.owners.gravitas.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.VersionDao;
import com.owners.gravitas.domain.ClientVersion;
import com.owners.gravitas.domain.Version;

/**
 * The Class VersionServiceImplTest
 *
 * @author pabhishek
 */
public class VersionServiceImplTest extends AbstractBaseMockitoTest {

    /** The version service impl. */
    @InjectMocks
    private VersionServiceImpl versionServiceImpl;

    /** The version dao. */
    @Mock
    private VersionDao versionDao;

    /**
     * Test set version
     */
    @Test
    public void testSetVersion() {
        final Map< String, Map< String, ClientVersion > > clientVersionMap = new HashMap<>();
        final Map< String, Version > oldVersionMap = new HashMap<>();
        Mockito.doNothing().when( versionDao ).saveVersion( clientVersionMap, oldVersionMap );
        versionServiceImpl.addUpdateVersion( clientVersionMap, oldVersionMap );
        Mockito.verify( versionDao ).saveVersion( clientVersionMap, oldVersionMap );
    }

}
