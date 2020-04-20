package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.Constants.QUERY_PARAM_ID;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.service.CRMQueryService;
import com.owners.gravitas.service.impl.RecordTypeServiceImpl;

/**
 * The test class RecordTypeServiceImplTest.
 *
 * @author ankusht
 */
public class RecordTypeServiceImplTest extends AbstractBaseMockitoTest {

    /** The RecordTypeServiceImpl. */
    @InjectMocks
    private RecordTypeServiceImpl recordTypeServiceImpl;

    /** The crm query service. */
    @Mock
    private CRMQueryService crmQueryService;

    /**
     * Test get record type id by name should return record type id if not
     * present in cache and add it in cache.
     */
    @Test
    public void testGetRecordTypeIdByName_ShouldReturnRecordTypeId_IfNotPresentInCache_AndAddItInCache() {
        Map< String, String > cache = ( Map< String, String > ) ReflectionTestUtils.getField( recordTypeServiceImpl,
                "cache" );
        cache.clear();
        final String recordTypeName = "dummy recordTypeName";
        final String objectType = "dummy objectType";
        final Map< String, Object > map = new HashMap<>();
        final String expected = "dummy record type id";
        map.put( QUERY_PARAM_ID, expected );

        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        final String actual = recordTypeServiceImpl.getRecordTypeIdByName( recordTypeName, objectType );

        verify( crmQueryService ).findOne( anyString(), any( QueryParams.class ) );
        assertEquals( actual, expected );
        assertEquals( cache.get( objectType + recordTypeName ), expected );
    }

    /**
     * Test get record type id by name should return record type id from cache
     * if present in cache.
     */
    @Test
    public void testGetRecordTypeIdByName_ShouldReturnRecordTypeIdFromCache_IfPresentInCache() {
        Map< String, String > cache = ( Map< String, String > ) ReflectionTestUtils.getField( recordTypeServiceImpl,
                "cache" );
        cache.clear();
        final String objectType = "dummy objectType";
        final String expected = "dummy Id";
        final String recordTypeName = "dummy recordTypeName";
        cache.put( objectType + recordTypeName, expected );
        final String actual = recordTypeServiceImpl.getRecordTypeIdByName( recordTypeName, objectType );
        assertEquals( actual, expected );
        assertEquals( cache.get( objectType + recordTypeName ), expected );
        verifyZeroInteractions( crmQueryService );
    }
}
