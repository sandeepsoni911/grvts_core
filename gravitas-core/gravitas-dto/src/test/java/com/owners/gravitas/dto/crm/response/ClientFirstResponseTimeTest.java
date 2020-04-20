package com.owners.gravitas.dto.crm.response;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.ClientFirstResponseTime;

/**
 * The Class ClientFirstResponseTimeTest.
 */
public class ClientFirstResponseTimeTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues testValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * ClientFirstResponseTime.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "message", "Operation was successful" );
        defaultValues.put( "status", Status.SUCCESS );
        defaultValues.put( "allResponseTime", null );
        defaultValues.put( "buyerResponseTime", null );
        defaultValues.put( "sellerResponseTime", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "message", "Operation was successful" );
        testValues.put( "status", Status.SUCCESS );
        testValues.put( "allResponseTime", "test" );
        testValues.put( "buyerResponseTime", "test" );
        testValues.put( "sellerResponseTime", "test" );
    }

    /**
     * Test client first response time.
     */
    @Test
    public final void testClientFirstResponseTime() {
        final BeanLikeTester blt = new BeanLikeTester( ClientFirstResponseTime.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
