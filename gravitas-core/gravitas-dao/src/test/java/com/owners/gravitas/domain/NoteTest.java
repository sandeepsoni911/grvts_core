package com.owners.gravitas.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class NoteTest.
 *
 * @author amits
 */
public class NoteTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues noteTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * PatchNote.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createNoteTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "details", null );
        defaultValues.put( "lastModifiedDtm", null );
        defaultValues.put( "createdDtm", null );
        defaultValues.put( "opportunityId", null );
        defaultValues.put( "deleted", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createNoteTestValues() {
        noteTestValues = new PropertiesAndValues();
        noteTestValues.put( "details", "test" );
        noteTestValues.put( "lastModifiedDtm", 0L );
        noteTestValues.put( "createdDtm", 0L );
        noteTestValues.put( "opportunityId", "test" );
        noteTestValues.put( "deleted", true );
    }

    /**
     * Tests the {@link Note} with default values. Tests the getters
     * and setters of Note.
     */
    @Test
    public final void testNote() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();

        List< Class< ? > > signature2 = Arrays.< Class< ? > > asList( String.class, Boolean.class, String.class,
                Long.class );
        mapping.put( Arrays.< Class< ? > > asList(), new ArrayList<>() );
        mapping.put( signature2, Arrays.asList( "details", "deleted", "opportunityId", "createdDtm" ) );

        final BeanLikeTester blt = new BeanLikeTester( Note.class, mapping );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, noteTestValues );
    }
}
