package com.owners.gravitas.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.RefType;
import com.owners.gravitas.repository.RefTypeRepository;

/**
 * The Class RefTypeServiceImplTest.
 *
 * @author raviz
 */
public class RefTypeServiceImplTest extends AbstractBaseMockitoTest {

    /** The ref type service impl. */
    @InjectMocks
    private RefTypeServiceImpl refTypeServiceImpl;

    /** The ref type repository. */
    @Mock
    private RefTypeRepository refTypeRepository;

    /**
     * Test get ref type by type.
     */
    @Test
    public void testGetRefTypeByType() {
        final String refTypeString = "test";
        final RefType expectedRefType = new RefType();
        when( refTypeRepository.findByType( refTypeString ) ).thenReturn( expectedRefType );
        final RefType actualRefType = refTypeServiceImpl.getRefTypeByType( refTypeString );
        assertEquals( actualRefType, expectedRefType );
        verify( refTypeRepository ).findByType( refTypeString );
    }

}
