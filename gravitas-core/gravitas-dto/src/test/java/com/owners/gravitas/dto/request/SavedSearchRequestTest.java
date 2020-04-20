package com.owners.gravitas.dto.request;

import java.util.Arrays;
import java.util.List;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class SavedSearchRequestTest.
 * 
 * @author pabhishek
 */
public class SavedSearchRequestTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The saved search request test values. */
    private PropertiesAndValues savedSearchRequestTestValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createSavedSearchRequestTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "uuid", null );
        defaultValues.put( "zip", null );
        defaultValues.put( "city", null );
        defaultValues.put( "state", null );
        defaultValues.put( "beds", 0 );
        defaultValues.put( "price", null );
        defaultValues.put( "propertyType", null );
        defaultValues.put( "mlsId", null );
        defaultValues.put( "address", null );
    }

    /**
     * Creates the saved search request test values.
     */
    private void createSavedSearchRequestTestValues() {
        savedSearchRequestTestValues = new PropertiesAndValues();
        savedSearchRequestTestValues.put( "uuid", "testuuid" );
        savedSearchRequestTestValues.put( "zip", "12345" );
        savedSearchRequestTestValues.put( "city", "ga" );
        savedSearchRequestTestValues.put( "state", "atlanta" );
        savedSearchRequestTestValues.put( "beds", 0 );
        savedSearchRequestTestValues.put( "price", "12345" );
        savedSearchRequestTestValues.put( "propertyType", "testpropertytype" );
        savedSearchRequestTestValues.put( "mlsId", "testmlsid" );
        savedSearchRequestTestValues.put( "address", "testaddress" );
    }

    /**
     * Test saved search request.
     */
    @Test
    public final void testSavedSearchRequest() {
        final ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        final List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        final List< Class< ? > > signature2 = Arrays.< Class< ? > > asList( String.class, String.class, String.class,
                String.class, int.class, String.class, String.class, String.class, String.class );
        mapping.put( signature2,
                Arrays.asList( "uuid", "zip", "city", "state", "beds", "price", "propertyType", "mlsId", "address" ) );
        final BeanLikeTester blt = new BeanLikeTester( SavedSearchRequest.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, savedSearchRequestTestValues );
    }
}
