package com.owners.gravitas.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.GenericDao;

/**
 * The Class GenericDbServiceImplTest.
 * 
 * @author ankusht
 */
public class GenericDbServiceImplTest extends AbstractBaseMockitoTest {

    /** The generic dao. */
    @Mock
    private GenericDao genericDao;

    /** The generic db service impl. */
    @InjectMocks
    private GenericDbServiceImpl genericDbServiceImpl;

    /**
     * Test find cbsa by zip.
     */
    @Test
    public void testFindCbsaByZip() {
        final String zip = "zip";
        final String expected = "cbsa";
        when( genericDao.findCbsaByZip( zip ) ).thenReturn( expected );
        final String actual = genericDbServiceImpl.findCbsaByZip( zip );
        assertEquals( expected, actual );
        verify( genericDao ).findCbsaByZip( zip );
    }
}
