package com.owners.gravitas.config;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.testng.Assert.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.owners.gravitas.config.ClientFollowupConfig;
import com.owners.gravitas.test.config.AbstractBaseMockitoTest;
import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class ClientFollowupConfigTest.
 *
 * @author raviz
 */
public class ClientFollowupConfigTest extends AbstractBaseMockitoTest {

    /** The client followup config. */
    @InjectMocks
    private ClientFollowupConfig clientFollowupConfig;

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
     * Test is enable client follow up email for buyer.
     */
    @Test
    public void testIsEnableClientFollowUpEmailForBuyer() {
        final boolean expected = true;
        ReflectionTestUtils.setField( clientFollowupConfig, "enableClientFollowUpEmailForBuyer", expected );
        final boolean actual = clientFollowupConfig.isEnableClientFollowUpEmailForBuyer();
        assertEquals( actual, expected );
    }

    /**
     * Test set client follow up mail delay minutes for buyer.
     */
    @Test
    public void testSetEnableClientFollowUpEmailForBuyer() {
        final boolean expected = true;
        clientFollowupConfig.setEnableClientFollowUpEmailForBuyer( expected );
        final boolean actual = parseBoolean(
                getField( clientFollowupConfig, "enableClientFollowUpEmailForBuyer" ).toString() );
        assertEquals( actual, expected );
        verify( propertyWriter ).saveJmxProperty( anyString(), any() );
    }

    /**
     * Test is enable client follow up email for seller.
     */
    @Test
    public void testIsEnableClientFollowUpEmailForSeller() {
        final boolean expected = true;
        ReflectionTestUtils.setField( clientFollowupConfig, "enableClientFollowUpEmailForSeller", expected );
        final boolean actual = clientFollowupConfig.isEnableClientFollowUpEmailForSeller();
        assertEquals( actual, expected );
    }

    /**
     * Test set client follow up mail delay minutes for seller.
     */
    @Test
    public void testSetEnableClientFollowUpEmailForSeller() {
        final boolean expected = true;
        clientFollowupConfig.setEnableClientFollowUpEmailForSeller( expected );
        final boolean actual = parseBoolean(
                getField( clientFollowupConfig, "enableClientFollowUpEmailForSeller" ).toString() );
        assertEquals( actual, expected );
        verify( propertyWriter ).saveJmxProperty( anyString(), any() );
    }

    /**
     * Test get client follow up mail delay minutes.
     */
    @Test
    public void testGetClientFollowUpMailDelayMinutes() {
        final int expected = 20;
        setField( clientFollowupConfig, "clientFollowUpMailDelayMinutes", expected );
        final int actual = clientFollowupConfig.getClientFollowUpMailDelayMinutes();
        assertEquals( actual, expected );
    }

    /**
     * Test set client follow up mail delay minutes.
     */
    @Test
    public void testSetClientFollowUpMailDelayMinutes() {
        final int expected = 20;
        clientFollowupConfig.setClientFollowUpMailDelayMinutes( expected );
        final int actual = parseInt( getField( clientFollowupConfig, "clientFollowUpMailDelayMinutes" ).toString() );
        assertEquals( actual, expected );
        verify( propertyWriter ).saveJmxProperty( anyString(), any() );
    }
}
