package com.owners.gravitas.business.impl;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import org.apache.commons.lang3.StringUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.PropertyAddress;
import com.owners.gravitas.dto.PropertyData;
import com.owners.gravitas.dto.response.PropertyDetailsResponse;
import com.owners.gravitas.service.PropertyService;
import com.owners.gravitas.util.AddressFormatter;

/**
 * The test class PropertyBusinessServiceImplTest
 *
 * @author raviz
 *
 */
public class PropertyBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The property business service impl. */
    @InjectMocks
    private PropertyBusinessServiceImpl propertyBusinessServiceImpl;

    /** The Property Details Services . */
    @Mock
    private PropertyService propertyService;
    
    /** The address formatter. */
    @Mock
    private AddressFormatter addressFormatter;

    /**
     * Test get property details.
     */
    @Test
    public void testGetPropertyDetails() {
        final String listingId = "listingID";
        final PropertyDetailsResponse expectedResponse = new PropertyDetailsResponse();
        when( propertyService.getPropertyDetails( listingId ) ).thenReturn( expectedResponse );
        final PropertyDetailsResponse actualResponse = propertyBusinessServiceImpl.getPropertyDetails( listingId );
        assertEquals( actualResponse, expectedResponse );
    }

    /**
     * Test get property location.
     */
//    @Test
    public void testGetPropertyLocation() {
        final String listingId = "listingID";
        final PropertyDetailsResponse propertyDetailsResponse = new PropertyDetailsResponse();
        final PropertyData propertyData = new PropertyData();
        final PropertyAddress propertyAddress = new PropertyAddress();
        propertyAddress.setAddressLine1( "dummyAddressLine1" );
        propertyAddress.setAddressLine2( "dummyAddressLine2" );
        propertyAddress.setCity( "dummyCityName" );
        propertyAddress.setState( "dummyStateName" );
        propertyAddress.setZip( "dummyZipCode" );
        propertyData.setPropertyAddress( propertyAddress );
        propertyDetailsResponse.setData( propertyData );

        when( propertyService.getPropertyDetails( listingId ) ).thenReturn( propertyDetailsResponse );

        final String result = propertyBusinessServiceImpl.getPropertyLocation( listingId );
        assertNotNull( result );
    }

    /**
     * Test get property location if property details are null.
     */
    @Test
    public void testGetPropertyLocationIfPropertyDetailsAreNull() {
        final String listingId = "listingID";
        when( propertyService.getPropertyDetails( listingId ) ).thenReturn( null );
        final String result = propertyBusinessServiceImpl.getPropertyLocation( listingId );
        Assert.assertEquals( result, StringUtils.EMPTY );
    }
}
