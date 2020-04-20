package com.owners.gravitas.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.repository.ObjectAttributeConfigRepository;

/**
 * The Class ObjectAttributeConfigServiceImplTest.
 *
 * @author shivamm
 */
public class ObjectAttributeConfigServiceImplTest extends AbstractBaseMockitoTest {

    /** The object attribute config service impl. */
    @InjectMocks
    private ObjectAttributeConfigServiceImpl objectAttributeConfigServiceImpl;

    /** The object attribute config repository. */
    @Mock
    private ObjectAttributeConfigRepository objectAttributeConfigRepository;

    /**
     * Test get object attribute config.
     */
    @Test
    public void testGetObjectAttributeConfig() {
        String attributeName = "test";
        ObjectType objectType = new ObjectType();
        objectAttributeConfigServiceImpl.getObjectAttributeConfig( attributeName, objectType );
        Mockito.verify( objectAttributeConfigRepository ).findByAttributeNameAndObjectType( Mockito.anyString(),
                Mockito.any( ObjectType.class ) );
    }

}
