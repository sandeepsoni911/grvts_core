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
 * The Class SystemErrorLogTest.
 * 
 * @author pabhishek
 */
public class SystemErrorLogTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The system error log values. */
    private PropertiesAndValues systemErrorLogValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createSystemErrorLogTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "id", null );
        defaultValues.put( "schedulerLog", null );
        defaultValues.put( "systemName", null );
        defaultValues.put( "cause", null );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "createdDate", null );
    }

    /**
     * Creates the system error log test values.
     */
    private void createSystemErrorLogTestValues() {
        final SchedulerLog schedulerLog = new SchedulerLog();

        systemErrorLogValues = new PropertiesAndValues();
        systemErrorLogValues.put( "id", "test" );
        systemErrorLogValues.put( "schedulerLog", schedulerLog );
        systemErrorLogValues.put( "systemName", "sytemtest" );
        systemErrorLogValues.put( "cause", "causetest" );
        systemErrorLogValues.put( "createdBy", "test1" );
        systemErrorLogValues.put( "createdDate", new DateTime() );
        ;
    }

    /**
     * Test system error log.
     */
    @Test
    public final void testSystemErrorLog() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        final BeanLikeTester blt = new BeanLikeTester( SystemErrorLog.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, systemErrorLogValues );
    }
}
