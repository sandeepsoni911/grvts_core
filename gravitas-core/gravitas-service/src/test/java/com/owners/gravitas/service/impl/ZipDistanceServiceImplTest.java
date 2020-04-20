package com.owners.gravitas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.ZipDistance;
import com.owners.gravitas.repository.ZipDistanceRepository;

/**
 * The Class ZipDistanceServiceImplTest.
 *
 * @author amits
 */
public class ZipDistanceServiceImplTest extends AbstractBaseMockitoTest {

    /** The zip distance service impl. */
    @InjectMocks
    private ZipDistanceServiceImpl zipDistanceServiceImpl;

    /** The zip distance repository. */
    @Mock
    private ZipDistanceRepository zipDistanceRepository;

    /**
     * Test get zips within coverage area.
     */
    @Test
    public void testGetZipsWithinCoverageArea() {
        Mockito.when( zipDistanceServiceImpl.getZipsWithinCoverageArea( Mockito.any(), Mockito.any() ) )
                .thenReturn( new ArrayList<>() );
        List< ZipDistance > list = zipDistanceServiceImpl.getZipsWithinCoverageArea( 0d, "test" );
        Assert.assertNotNull( list );
    }

    /**
     * Test update zips within coverage area.
     */
    @Test
    public void testUpdateZipsWithinCoverageArea() {
        zipDistanceServiceImpl.updateZipDistanceExcluded( "test", true );
        Mockito.verify( zipDistanceRepository ).updateZipDistanceExcluded( "test", true );
    }
}
