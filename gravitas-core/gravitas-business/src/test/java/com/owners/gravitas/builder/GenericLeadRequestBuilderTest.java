package com.owners.gravitas.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.request.GenericLeadRequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.request.GenericLeadRequest;

public class GenericLeadRequestBuilderTest extends AbstractBaseMockitoTest {

    @InjectMocks
    private GenericLeadRequestBuilder genericLeadRequestBuilder;

    @Test
    public void testConvertTo() {
        Map< String, List< String > > map = new HashMap<>();
        List< String > list = new ArrayList<>();
        List< String > list1 = new ArrayList<>();

        list1.add( "false" );
        list.add( "lastName" );
        map.put( "lastName", list );
        map.put( "firstName", null );
        map.put( "email", new ArrayList<>() );
        map.put( "ownsHome", list1 );
        map.put( "marketingOptIn", null );
        final GenericLeadRequest req = genericLeadRequestBuilder.convertTo( map );
        Assert.assertNotNull( req );
        Assert.assertEquals( req.getLastName(), "lastName" );

        List< String > list2 = new ArrayList<>();
        list2.add( "true" );
        map.put( "marketingOptIn", list2 );
        final GenericLeadRequest req1 = genericLeadRequestBuilder.convertTo( map );
        Assert.assertNotNull( req1 );
        Assert.assertEquals( req1.getLastName(), "lastName" );

        map.put( "marketingOptIn", new ArrayList<>() );
        final GenericLeadRequest req2 = genericLeadRequestBuilder.convertTo( map, new GenericLeadRequest() );
        Assert.assertNotNull( req2 );
    }

    @Test
    public void testConvertToNullSource() {
        final GenericLeadRequest req1 = genericLeadRequestBuilder.convertTo( null );
        Assert.assertNull( req1 );

        final GenericLeadRequest req2 = genericLeadRequestBuilder.convertTo( new HashMap<>() );
        Assert.assertNull( req2 );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        genericLeadRequestBuilder.convertFrom( new GenericLeadRequest() );
    }
}
