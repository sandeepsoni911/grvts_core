package com.owners.gravitas.config;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.testng.Assert.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.owners.gravitas.test.config.AbstractBaseMockitoTest;
import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class ClientStatisticsConfigTest.
 *
 * @author raviz
 */
public class ClientStatisticsConfigTest extends AbstractBaseMockitoTest {

    /** The client statistics config. */
    @InjectMocks
    private ClientStatisticsConfig clientStatisticsConfig;

    /** The property writer. */
    @Mock
    private PropertyWriter propertyWriter;

    /**
     * Before method.
     */
    @BeforeMethod
    public void beforeMethod() {
        reset( propertyWriter );
    }

    /**
     * Test get from date.
     */
    @Test
    public void testGetFromDate() {
        final String expectedFromDate = "2000-06-06";
        ReflectionTestUtils.setField( clientStatisticsConfig, "fromDate", expectedFromDate );
        final String actualFromDate = clientStatisticsConfig.getFromDate();
        assertEquals( actualFromDate, expectedFromDate );
    }

    /**
     * Test set from date.
     */
    @Test
    public void testSetFromDate() {
        final String expectedFromDate = "2000-06-06";
        clientStatisticsConfig.setFromDate( expectedFromDate );
        final String actualFromDate = getField( clientStatisticsConfig, "fromDate" ).toString();
        assertEquals( actualFromDate, expectedFromDate );
        verify( propertyWriter ).saveJmxProperty( anyString(), any() );
    }

    /**
     * Test get to date.
     */
    @Test
    public void testGetToDate() {
        final String expectedToDate = "2020-06-06";
        ReflectionTestUtils.setField( clientStatisticsConfig, "toDate", expectedToDate );
        final String actualToDate = clientStatisticsConfig.getToDate();
        assertEquals( actualToDate, expectedToDate );
    }

    /**
     * Test set to date.
     */
    @Test
    public void testSetToDate() {
        final String expectedToDate = "2000-06-06";
        clientStatisticsConfig.setToDate( expectedToDate );
        final String actualToDate = getField( clientStatisticsConfig, "toDate" ).toString();
        assertEquals( actualToDate, expectedToDate );
        verify( propertyWriter ).saveJmxProperty( anyString(), any() );
    }
}
