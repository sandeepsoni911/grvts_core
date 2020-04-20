package com.owners.gravitas.validators;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Payload;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.enums.RecordType;

/**
 * The Class UrlListValidatorTest.
 *
 * @author vishwanathm
 */
public class UrlListValidatorTest extends AbstractBaseMockitoTest {
    /** The constraint validator. */
    @InjectMocks
    private UrlListValidator urlListValidator;

    /**
     * Test is valid for true result.
     */
    @Test( priority = 1 )
    public void testIsValidForTrueResult() {
        boolean result = urlListValidator.isValid( new ArrayList<>(), null );
        Assert.assertTrue( result );

        List< String > list = new ArrayList<>();
        list.add( "" );
        list.add( "itemSizeValidatoritemSizeValidator" );
        result = urlListValidator.isValid( list, null );
        Assert.assertFalse( result );

        list = new ArrayList<>();
        list.add( "http://test.com" );
        result = urlListValidator.isValid( list, null );
        Assert.assertTrue( result );

        list = new ArrayList<>();
        list.add( "itemSize" );
        result = urlListValidator.isValid( list, null );
        Assert.assertFalse( result );

        result = urlListValidator.isValid( null, null );
        Assert.assertTrue( result );
    }

    /**
     * Test initialize.
     */
    @Test( priority = 2 )
    public void testInitialize() {
        urlListValidator.initialize( new UrlList() {

            @Override
            public Class< ? extends Annotation > annotationType() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Class< ? extends Payload >[] payload() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public String message() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Class< ? >[] groups() {
                // TODO Auto-generated method stub
                return null;
            }
        } );
    }
}
