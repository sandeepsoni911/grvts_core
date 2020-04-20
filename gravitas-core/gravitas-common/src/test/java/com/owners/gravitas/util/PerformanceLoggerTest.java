package com.owners.gravitas.util;

import java.util.Properties;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;

// TODO: Auto-generated Javadoc
/**
 * The Class PerformanceLoggerTest.
 *
 * @author vishwanathm
 */
public class PerformanceLoggerTest extends AbstractBaseMockitoTest {

    /** The performance logger. */
    @InjectMocks
    private PerformanceLogger performanceLogger;

    /** The properties util mock. */
    @Mock
    private PropertiesUtil propertiesUtilMock;


    /**
     * Test process properties.
     */
    @Test
    public void testProcessProperties() {
        final Properties props = new Properties();
        props.put( "test", "test" );
        performanceLogger.millisToShortDHMS(1000);
        performanceLogger.createAndStart();
    }

    /**
     * Test get time.
     */
    @Test
    public void testGetTime() {
        performanceLogger.getTime();
    }
}
