package com.owners.gravitas.dto;

import java.util.HashMap;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class QueryParamTest.
 *
 * @author amits
 */
public class QueryParamTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues queryParamTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * QueryParams.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createQueryParamTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "params", new HashMap< String, String >() );
    }

    /**
     * This method is to create the actual values.
     */
    private void createQueryParamTestValues() {
        queryParamTestValues = new PropertiesAndValues();
        queryParamTestValues.put( "params", new HashMap< String, String >() );
    }

    /**
     * Tests the {@link QueryParams} with default values. Tests the getters
     * and setters of QueryParams.
     */
    @Test
    public final void testQueryParams() {
        final BeanLikeTester blt = new BeanLikeTester( QueryParams.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, queryParamTestValues );
        QueryParams queryParams = new QueryParams();
        queryParams.add( "test", "test" );
        Assert.assertEquals( queryParams.getParams().get( "test" ), "test" );
    }
}
