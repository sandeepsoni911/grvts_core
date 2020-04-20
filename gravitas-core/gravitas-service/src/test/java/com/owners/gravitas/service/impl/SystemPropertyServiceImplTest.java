package com.owners.gravitas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.SystemProperty;
import com.owners.gravitas.repository.SystemPropertyRepository;

/**
 * The Class ActionLogServiceImplTest.
 *
 */
public class SystemPropertyServiceImplTest extends AbstractBaseMockitoTest {

    /** The action log service impl. */
    @InjectMocks
    private SystemPropertyServiceImpl systemPropertyServiceImpl;

    /** The system property repository. */
    @Mock
    private SystemPropertyRepository systemPropertyRepository;

    /**
     * Test get property.
     */
    @Test
    public void testGetProperty() {
        SystemProperty property = new SystemProperty();
        property.setName( "name" );
        property.setValue( "value" );
        Mockito.when( systemPropertyRepository.findByName( "test" ) ).thenReturn( property );
        SystemProperty actualProperty = systemPropertyServiceImpl.getProperty( "test" );
        Assert.assertNotNull( actualProperty );
        Assert.assertEquals( actualProperty.getName(), property.getName() );
    }

    /**
     * Test get properties.
     */
    @Test
    public void testGetProperties() {
        List< SystemProperty > properties = new ArrayList<>();
        SystemProperty property1 = new SystemProperty();
        property1.setName( "prop1" );
        property1.setValue( "value1" );
        properties.add( property1 );
        SystemProperty property2 = new SystemProperty();
        property2.setName( "prop2" );
        property2.setValue( "value2" );
        properties.add( property2 );
        SystemProperty property3 = new SystemProperty();
        property3.setName( "prop3" );
        property3.setValue( "value3" );
        properties.add( property3 );
        List< String > list = new ArrayList<>();
        list.add( "prop1" );
        list.add( "prop2" );
        list.add( "prop3" );
        Mockito.when( systemPropertyRepository.findAllByNameIn( list ) ).thenReturn( properties );

        List< SystemProperty > actualProperties = systemPropertyServiceImpl.getProperties( list );
        Assert.assertNotNull( actualProperties );
        Assert.assertEquals( actualProperties.get( 0 ).getName(), properties.get( 0 ).getName() );
    }
}
