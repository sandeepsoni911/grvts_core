package com.owners.gravitas.service.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.RefCode;
import com.owners.gravitas.repository.RefCodeRepository;

/**
 * The Class RefCodeServiceImplTest.
 *
 * @author raviz
 */
public class RefCodeServiceImplTest extends AbstractBaseMockitoTest {

    /** The ref code service impl. */
    @InjectMocks
    private RefCodeServiceImpl refCodeServiceImpl;

    /** The ref code repository. */
    @Mock
    private RefCodeRepository refCodeRepository;

    /**
     * Test find by code.
     */
    @Test
    public void testFindByCode() {
        final String refCode = "refCode";
        final RefCode expectedRefCode = new RefCode();
        when( refCodeRepository.findByCode( refCode ) ).thenReturn( expectedRefCode );
        final RefCode actualRefCode = refCodeServiceImpl.findByCode( refCode );
        assertEquals( actualRefCode, expectedRefCode );
        verify( refCodeRepository ).findByCode( refCode );
    }
}
