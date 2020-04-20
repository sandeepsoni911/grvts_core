package com.owners.gravitas.service.impl;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNotNull;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.response.PropertyDetailsResponse;
import com.owners.gravitas.exception.ApplicationException;

/**
 * The Class PropertyDetailsServiceImplTest.
 *
 * @author vishwanathm
 */
public class PropertyDetailsServiceImplTest extends AbstractBaseMockitoTest {

    /** The Property Details Service Impl. */
    @InjectMocks
    private PropertyServiceImpl propertyDetailsServiceImpl;

    /** The rest template. */
    @Mock
    RestTemplate restTemplate;

    /**
     * Test save agent task.
     */
    @Test
    public void testGetPropertyDetails() {
        ReflectionTestUtils.setField( propertyDetailsServiceImpl, "propertyDetailsUrl", "urtl" );
        final String pdpResponse = "{\"data\" : null}";
        when( restTemplate.getForObject( Mockito.anyString(), Mockito.any( Class.class ) ) ).thenReturn( pdpResponse );

        final PropertyDetailsResponse response = propertyDetailsServiceImpl.getPropertyDetails( "test" );
        assertNotNull( response );
    }
}
