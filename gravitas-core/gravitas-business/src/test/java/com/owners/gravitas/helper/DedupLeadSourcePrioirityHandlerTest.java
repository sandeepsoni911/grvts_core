package com.owners.gravitas.helper;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.owners.gravitas.business.helper.DedupLeadSourcePrioirityHandler;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.util.PropertiesUtil;

/**
 * The Class DedupLeadSourcePrioirityHandlerTest.
 *
 * @author vishwanathm
 */
public class DedupLeadSourcePrioirityHandlerTest extends AbstractBaseMockitoTest {

    /** The dedup lead source prioirity handler. */
    @InjectMocks
    private DedupLeadSourcePrioirityHandler dedupLeadSourcePrioirityHandler;

    /**
     * Setup.
     */
    @BeforeTest
    public void setup() {
        final Map< String, String > props = new HashMap<>();
        props.put( "owners.dedupe.sources.priority.1", "ANY_SOURCE_VALUE" );
        props.put( "owners.dedupe.sources.priority.2", "Owners.com Property" );
        props.put( "owners.dedupe.sources.priority.3", "Owners.com Saved Search" );
        props.put( "owners.dedupe.sources.priority.4", "ANY_AFFILIATED_EMAIL" );
        props.put( "owners.dedupe.sources.priority.5", "Unbounce Landing Page" );
        props.put( "owners.dedupe.sources.priority.6", "Owners.com Registration" );
        props.put( "owners.dedupe.sources.priority.7", "Owners.com How it works" );
        props.put( "owners.dedupe.sources.priority.8", "Owners.com Home Page" );
        if (PropertiesUtil.getPropertiesMap() == null) {
            PropertiesUtil.setPropertiesMap( props );
        } else {
            PropertiesUtil.getPropertiesMap().putAll( props );
        }
    }

    /**
     * Test is source update required.
     */
    @Test
    public void testIsSourceUpdateRequired() {
        ReflectionTestUtils.setField( dedupLeadSourcePrioirityHandler, "affiliateSourcesStr",
                "updates@trulia.com,noReply@hubzu.com" );
        dedupLeadSourcePrioirityHandler.init();

        Assert.assertTrue( dedupLeadSourcePrioirityHandler.isSourceUpdateRequired( "xx", "yy" ) );

        Assert.assertTrue( dedupLeadSourcePrioirityHandler.isSourceUpdateRequired( "noReply@hubzu.com", "fff" ) );
        Assert.assertTrue(
                dedupLeadSourcePrioirityHandler.isSourceUpdateRequired( "Owners.com How it works", "zdsgsdgsd" ) );
        Assert.assertTrue( dedupLeadSourcePrioirityHandler.isSourceUpdateRequired( "cccc", "noReply@hubzu.com" ) );
        Assert.assertTrue( dedupLeadSourcePrioirityHandler.isSourceUpdateRequired( "cccc", "xxxx" ) );
        Assert.assertTrue(
                dedupLeadSourcePrioirityHandler.isSourceUpdateRequired( "xxxx", "Owners.com How it works" ) );
        Assert.assertTrue( dedupLeadSourcePrioirityHandler.isSourceUpdateRequired( "xxxx", "Owners.com Property" ) );
        Assert.assertTrue( dedupLeadSourcePrioirityHandler.isSourceUpdateRequired( "Owners.com How it works",
                "Owners.com Home Page" ) );
        Assert.assertTrue( dedupLeadSourcePrioirityHandler.isSourceUpdateRequired( "Owners.com Property",
                "Owners.com Saved Search" ) );
        Assert.assertTrue( dedupLeadSourcePrioirityHandler.isSourceUpdateRequired( "Unbounce Landing Page",
                "Owners.com How it works" ) );
        Assert.assertFalse( dedupLeadSourcePrioirityHandler.isSourceUpdateRequired( "updates@trulia.com",
                "Unbounce Landing Page" ) );
        Assert.assertTrue(
                dedupLeadSourcePrioirityHandler.isSourceUpdateRequired( "noReply@hubzu.com", "updates@trulia.com" ) );
        Assert.assertTrue( dedupLeadSourcePrioirityHandler.isSourceUpdateRequired( "Owners.com Saved Search",
                "noReply@hubzu.com" ) );
        Assert.assertTrue( dedupLeadSourcePrioirityHandler.isSourceUpdateRequired( "Owners.com Property",
                "Owners.com Saved Search" ) );
        Assert.assertTrue( dedupLeadSourcePrioirityHandler.isSourceUpdateRequired( "Owners.com Home Page",
                "Owners.com Saved Search" ) );
        Assert.assertTrue( dedupLeadSourcePrioirityHandler.isSourceUpdateRequired( "noReply@hubzu.com",
                "Owners.com Saved Search" ) );
    }
}
