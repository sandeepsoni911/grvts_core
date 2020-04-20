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
 * The Class SchedulerLogTest.
 * 
 * @author pabhishek
 */
public class SchedulerLogTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The scheduler log values. */
    private PropertiesAndValues schedulerLogValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createSchedulerLogTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "id", null );
        defaultValues.put( "schedulerName", null );
        defaultValues.put( "startTime", null );
        defaultValues.put( "endTime", null );
        defaultValues.put( "hostName", null );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "createdDate", null );
    }

    /**
     * Creates the scheduler log test values.
     */
    private void createSchedulerLogTestValues() {
        schedulerLogValues = new PropertiesAndValues();
        schedulerLogValues.put( "id", "test" );
        schedulerLogValues.put( "schedulerName", "schedulertest" );
        schedulerLogValues.put( "startTime", new DateTime() );
        schedulerLogValues.put( "endTime", new DateTime() );
        schedulerLogValues.put( "hostName", "hostnametest" );
        schedulerLogValues.put( "createdBy", "test1" );
        schedulerLogValues.put( "createdDate", new DateTime() );
    }

    /**
     * Test scheduler log.
     */
    @Test
    public final void testSchedulerLog() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        final BeanLikeTester blt = new BeanLikeTester( SchedulerLog.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, schedulerLogValues );
    }
}
