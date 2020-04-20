package com.owners.gravitas.domain.entity;

import org.joda.time.DateTime;
import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.domain.entity.MarketingEmailLog;

public class MarketingEmailLogTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues marketingEmailLogValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * AgentInfo.
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
        defaultValues.put( "id", null );
        defaultValues.put( "new", true );
        defaultValues.put( "leadId", null );
        defaultValues.put( "createdDate", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createAddressTestValues() {
        marketingEmailLogValues = new PropertiesAndValues();
        marketingEmailLogValues.put( "id", "test" );
        marketingEmailLogValues.put( "new", true );
        marketingEmailLogValues.put( "leadId", "test" );
        marketingEmailLogValues.put( "createdDate", new DateTime() );
    }

    @Test
    public final void testMarketingEmailLog() {
        final BeanLikeTester blt = new BeanLikeTester( MarketingEmailLog.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, marketingEmailLogValues );
    }
}
