package com.owners.gravitas.service.impl;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.OwnersMarketCbsaLabel;
import com.owners.gravitas.repository.OwnersMarketCbsaLabelRepository;

/**
 * The Class OwnersMarketCbsaLabelServiceImplTest.
 * 
 * @author pabhishek
 */
public class OwnersMarketCbsaLabelServiceImplTest extends AbstractBaseMockitoTest {

    /** The owners market cbsa label service impl. */
    @InjectMocks
    private OwnersMarketCbsaLabelServiceImpl ownersMarketCbsaLabelServiceImpl;

    /** The owners market cbsa label repository. */
    @Mock
    private OwnersMarketCbsaLabelRepository ownersMarketCbsaLabelRepository;

    /**
     * Test find by zip.
     */
    @Test
    public void testFindByZip() {
        final String zip = "12345";
        final OwnersMarketCbsaLabel ownersMarketCbsaLabel = new OwnersMarketCbsaLabel();

        when( ownersMarketCbsaLabelRepository.findByZip( zip ) ).thenReturn( ownersMarketCbsaLabel );
        final OwnersMarketCbsaLabel actualOwnersMarketCbsaLabel = ownersMarketCbsaLabelServiceImpl.findByZip( zip );

        assertEquals( actualOwnersMarketCbsaLabel, ownersMarketCbsaLabel );
        Mockito.verify( ownersMarketCbsaLabelRepository ).findByZip( zip );
    }
}
