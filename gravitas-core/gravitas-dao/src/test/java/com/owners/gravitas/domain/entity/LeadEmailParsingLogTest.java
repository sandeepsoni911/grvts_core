package com.owners.gravitas.domain.entity;

import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class LeadEmailParsingLogTest.
 * 
 * @author pabhishek
 */
public class LeadEmailParsingLogTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The lead email parsing log values. */
    private PropertiesAndValues leadEmailParsingLogValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createLeadEmailParsingLogTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "id", null );
        defaultValues.put( "leadSource", null );
        defaultValues.put( "mailReceivedTime", null );
        defaultValues.put( "fromEmail", null );
        defaultValues.put( "toEmail", null );
        defaultValues.put( "subject", null );
        defaultValues.put( "leadEmail", null );
        defaultValues.put( "status", null );
        defaultValues.put( "failuerReason", null );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "createdDate", null );
    }

    /**
     * Creates the lead email parsing log test values.
     */
    private void createLeadEmailParsingLogTestValues() {
        leadEmailParsingLogValues = new PropertiesAndValues();
        leadEmailParsingLogValues.put( "id", "test" );
        leadEmailParsingLogValues.put( "leadSource", "testsource" );
        leadEmailParsingLogValues.put( "mailReceivedTime", new DateTime() );
        leadEmailParsingLogValues.put( "fromEmail", "frommailtest" );
        leadEmailParsingLogValues.put( "toEmail", "tomailtest" );
        leadEmailParsingLogValues.put( "subject", "subjecttest" );
        leadEmailParsingLogValues.put( "leadEmail", "leademailtest" );
        leadEmailParsingLogValues.put( "status", "statustest" );
        leadEmailParsingLogValues.put( "failuerReason", "failurereasontest" );
        leadEmailParsingLogValues.put( "createdBy", "test1" );
        leadEmailParsingLogValues.put( "createdDate", new DateTime() );
    }

    /**
     * Test lead email parsing log.
     */
    @Test
    public final void testLeadEmailParsingLog() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        final BeanLikeTester blt = new BeanLikeTester( LeadEmailParsingLog.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, leadEmailParsingLogValues );
    }
}
