package com.owners.gravitas.service.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.CbsaMarketLevel;
import com.owners.gravitas.repository.CbsaMarketLevelRepository;

/**
 * The Class CbsaMarketLevelServiceImplTest.
 * 
 * @author pabhishek
 */
public class CbsaMarketLevelServiceImplTest extends AbstractBaseMockitoTest {

    /** The cbsa market level service impl. */
    @InjectMocks
    private CbsaMarketLevelServiceImpl cbsaMarketLevelServiceImpl;

    /** The cbsa market level repository. */
    @Mock
    private CbsaMarketLevelRepository cbsaMarketLevelRepository;

    /**
     * Test find by cbsa code.
     */
    @Test
    public void testFindByCbsaCode() {
        final String cbsaCode = "testcode";
        final CbsaMarketLevel cbsaMarketLevel = new CbsaMarketLevel();

        when( cbsaMarketLevelRepository.findByCbsaCode( cbsaCode ) ).thenReturn( cbsaMarketLevel );
        final CbsaMarketLevel actualCbsaMarketLevel = cbsaMarketLevelServiceImpl.findByCbsaCode( cbsaCode );

        verify( cbsaMarketLevelRepository ).findByCbsaCode( cbsaCode );
        assertEquals( actualCbsaMarketLevel, cbsaMarketLevel );
    }

    /**
     * Test find threshold by cbsa code.
     */
    @Test
    public void testFindThresholdByCbsaCode() {
        final String cbsaCode = "testcode";
        when( cbsaMarketLevelRepository.findThresholdByCbsaCode( cbsaCode ) ).thenReturn( 1 );
        final int actualValue = cbsaMarketLevelServiceImpl.findThresholdByCbsaCode( cbsaCode );

        verify( cbsaMarketLevelRepository ).findThresholdByCbsaCode( cbsaCode );
        assertEquals( actualValue, 1 );
    }

    /**
     * Test find by cbsa codes.
     */
    @Test
    public void testFindByCbsaCodes() {
        final Collection< String > cbsaCodes = new ArrayList< String >();
        cbsaCodes.add( "testcode" );
        final List< CbsaMarketLevel > cbsaMarketLevels = new ArrayList< CbsaMarketLevel >();

        when( cbsaMarketLevelRepository.findByCbsaCodes( cbsaCodes ) ).thenReturn( cbsaMarketLevels );
        final List< CbsaMarketLevel > actualCbsaMarketLevels = cbsaMarketLevelServiceImpl.findByCbsaCodes( cbsaCodes );

        verify( cbsaMarketLevelRepository ).findByCbsaCodes( cbsaCodes );
        assertEquals( actualCbsaMarketLevels, cbsaMarketLevels );
    }
}
