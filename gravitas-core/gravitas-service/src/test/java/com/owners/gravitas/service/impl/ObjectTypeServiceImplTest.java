package com.owners.gravitas.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.repository.ObjectTypeRepository;

/**
 * The Class ObjectTypeServiceImplTest.
 *
 * @author shivamm
 */
public class ObjectTypeServiceImplTest extends AbstractBaseMockitoTest {

    /** The object type service impl. */
    @InjectMocks
    private ObjectTypeServiceImpl objectTypeServiceImpl;

    /** The object type repository. */
    @Mock
    private ObjectTypeRepository objectTypeRepository;

    /**
     * Test get object attribute config.
     */
    @Test
    public void testFindByName() {
        String name = "lead";
        ObjectType objectType = new ObjectType();
        objectType.setName( "lead" );
        when( objectTypeRepository.findByName( name ) ).thenReturn( objectType );
        ObjectType actual = objectTypeServiceImpl.findByName( name );
        assertEquals( actual.getName(), name );
        verify( objectTypeRepository ).findByName( Mockito.anyString() );
    }

}
