/**
 *
 */
package com.owners.gravitas.dto;

import java.util.Arrays;
import java.util.List;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class CrmNoteTest.
 *
 * @author shivamm
 */
public class CrmNoteTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues addressTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * CrmNoteTest.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createAddressTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "parentId", null );
        defaultValues.put( "title", null );
        defaultValues.put( "body", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createAddressTestValues() {
        addressTestValues = new PropertiesAndValues();
        addressTestValues.put( "parentId", "test" );
        addressTestValues.put( "title", "test" );
        addressTestValues.put( "body", "test" );
    }

    /**
     * Tests the {@link CrmNote} with default values. Tests the getters
     * and setters of CrmNote.
     */
    @Test
    public final void testCrmNote() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        List< Class< ? > > signature2 = Arrays.< Class< ? > > asList( String.class, String.class, String.class );
        mapping.put( signature1, Arrays.asList() );
        mapping.put( signature2, Arrays.asList( "parentId", "title", "body" ) );

        final BeanLikeTester blt = new BeanLikeTester( CrmNote.class, mapping );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, addressTestValues );
    }

    @Test
    public void testToString(){
        CrmNote t = new CrmNote();
        t.toString();
    }

}
