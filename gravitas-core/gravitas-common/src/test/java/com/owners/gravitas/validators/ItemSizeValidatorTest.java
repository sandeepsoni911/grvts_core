package com.owners.gravitas.validators;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Payload;

import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;

public class ItemSizeValidatorTest extends AbstractBaseMockitoTest {
    /** The constraint validator. */
    @InjectMocks
    private ItemSizeValidator itemSizeValidator;

    @BeforeClass
    public void setUpBase() throws Exception {
        ReflectionTestUtils.setField( itemSizeValidator, "min", 1 );
        ReflectionTestUtils.setField( itemSizeValidator, "max", 10 );
    }

    /**
     * Test is valid for true result.
     */
    @Test( priority = 1 )
    public void testIsValidForTrueResult() {
        boolean result = itemSizeValidator.isValid( new ArrayList<>(), null );
        Assert.assertTrue( result );

        List< String > list = new ArrayList<>();
        list.add( "" );
        list.add( "itemSizeValidatoritemSizeValidator" );
        result = itemSizeValidator.isValid( list, null );
        Assert.assertFalse( result );

        list = new ArrayList<>();
        list.add( "itemSizeValidatoritemSizeValidator" );
        result = itemSizeValidator.isValid( list, null );
        Assert.assertFalse( result );

        list = new ArrayList<>();
        list.add( "itemSize" );
        result = itemSizeValidator.isValid( list, null );
        Assert.assertTrue( result );

        result = itemSizeValidator.isValid( null, null );
        Assert.assertTrue( result );
    }

    /**
     * Test initialize.
     */
    @Test( priority = 2 )
    public void testInitialize() {
        itemSizeValidator.initialize( new ItemSize() {

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
            public int min() {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public String message() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int max() {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public Class< ? >[] groups() {
                // TODO Auto-generated method stub
                return null;
            }
        } );
    }
}
