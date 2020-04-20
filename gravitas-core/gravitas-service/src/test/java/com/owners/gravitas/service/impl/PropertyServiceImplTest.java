package com.owners.gravitas.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNotNull;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.response.PropertyDetailsResponse;
import com.owners.gravitas.exception.ApplicationException;

/**
 * The Class PropertyServiceImplTest.
 *
 * @author raviz
 */
public class PropertyServiceImplTest extends AbstractBaseMockitoTest {

    /** The property service impl. */
    @InjectMocks
    private PropertyServiceImpl propertyServiceImpl;

    /** The rest template. */
    @Mock
    private RestTemplate restTemplate;

    /**
     * Test get property details should return property details.
     */
    @Test
    public void testGetPropertyDetailsShouldReturnPropertyDetails() {
        final String listingId = "testListingId";
        final String propertyDetailsUrl = "http://www.owners.com/homes/xx/x/x/%s?ajax=true";
        final String data = "{\"data\":{\"propertyAddress\":{\"addressLine1\":\"add1\",\"city\":\"city\",\"state\":\"state\"},\"price\":\"123\"}}";
        ReflectionTestUtils.setField( propertyServiceImpl, "propertyDetailsUrl", propertyDetailsUrl );
        when( restTemplate.getForObject( anyString(), any() ) ).thenReturn( data );
        final PropertyDetailsResponse propertyDetailsResponse = propertyServiceImpl.getPropertyDetails( listingId );
        assertNotNull( propertyDetailsResponse );
    }

    /**
     * Test get property details should throw exception.
     */
    @Test(enabled=false, expectedExceptions = ApplicationException.class )
    public void testGetPropertyDetailsShouldThrowApplicationException() {
        final String listingId = "testListingId";
        final String propertyDetailsUrl = "http://www.owners.com/homes/xx/x/x/%s?ajax=true";
        final String data = "invalidData";
        ReflectionTestUtils.setField( propertyServiceImpl, "propertyDetailsUrl", propertyDetailsUrl );
        when( restTemplate.getForObject( anyString(), any() ) ).thenReturn( data );
        propertyServiceImpl.getPropertyDetails( listingId );
    }
}
