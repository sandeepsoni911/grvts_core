package com.owners.gravitas.domain;

import static java.lang.Boolean.FALSE;
import static com.owners.gravitas.enums.AgentTaskStatus.INCOMPLETE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.domain.Request;
import com.owners.gravitas.dto.TourDetails;
import com.owners.gravitas.enums.RequestType;

public class RequestTest {
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
     * Request.
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
        defaultValues.put( "type", null );
        defaultValues.put( "opportunityId", null );
        defaultValues.put( "converted", false );
        defaultValues.put( "listingId", null );
        defaultValues.put( "opportunityNotes", null );
        defaultValues.put( "preApprovaedForMortgage", null );
        defaultValues.put( "offerAmount", null );
        defaultValues.put( "earnestMoneyDeposit", null );
        defaultValues.put( "purchaseMethod", null );
        defaultValues.put( "downPayment", null );
        defaultValues.put( "propertyTourInfo", null );
        defaultValues.put( "leadMessage", null );
        defaultValues.put( "createdDtm", null );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "lastModifiedDtm", null );
        defaultValues.put( "taskId", null );
        defaultValues.put( "deleted", FALSE );
        defaultValues.put( "lastViewedDtm", null );
        defaultValues.put( "dates", null );
        defaultValues.put( "status", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "type", RequestType.APPOINTMENT );
        testValues.put( "opportunityId", "test" );
        testValues.put( "converted", true );
        testValues.put( "listingId", "test" );
        testValues.put( "opportunityNotes", "test" );
        testValues.put( "preApprovaedForMortgage", "test" );
        testValues.put( "offerAmount", new BigDecimal( 0 ) );
        testValues.put( "earnestMoneyDeposit", "test" );
        testValues.put( "purchaseMethod", "test" );
        testValues.put( "downPayment", "test" );
        testValues.put( "propertyTourInfo", "test" );
        testValues.put( "leadMessage", "test" );
        testValues.put( "createdDtm", 0L );
        testValues.put( "createdBy", "test" );
        testValues.put( "lastModifiedDtm", 0L );
        testValues.put( "taskId", "test" );
        testValues.put( "deleted", FALSE );
        testValues.put( "lastViewedDtm", 0L );
        testValues.put( "status", INCOMPLETE );
        testValues.put( "dates", new ArrayList<List< TourDetails >>() );
    }

    /**
     * Tests the {@link Request} with default values. Tests the getters
     * and setters of Request.
     */
    @Test(enabled=false)
    public final void testRequest() {
        final BeanLikeTester blt = new BeanLikeTester( Request.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
