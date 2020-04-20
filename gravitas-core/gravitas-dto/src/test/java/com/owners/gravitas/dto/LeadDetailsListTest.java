package com.owners.gravitas.dto;

import java.util.ArrayList;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LeadDetailsListTest {
    
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;

    /**
     * for testing values.
     */
    private PropertiesAndValues leadDetailsTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * LeadDetails.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createLeadDetailsTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "leadDetailsList", null );
        defaultValues.put( "totalPages", 0L );
        defaultValues.put( "totalElements", 0L );
        defaultValues.put( "numberOfElements", 0L );
        defaultValues.put( "pageNumber", 0L );
       
    }

    /**
     * This method is to create the test values.
     */
    private void createLeadDetailsTestValues() {
        leadDetailsTestValues = new PropertiesAndValues();
        leadDetailsTestValues.put( "leadDetailsList", new ArrayList<>() );
        leadDetailsTestValues.put( "totalPages", 39L );
        leadDetailsTestValues.put( "totalElements", 123L );
        leadDetailsTestValues.put( "numberOfElements", 321L );
        leadDetailsTestValues.put( "pageNumber", 1L );
    }

    /**
     * Tests the {@link OpportunityDetails} with default values. Tests the
     * getters
     * and setters of OpportunityDetails.
     */
    @Test
    public final void testLeadDetailsList() {
        final BeanLikeTester blt = new BeanLikeTester( LeadDetailsList.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, leadDetailsTestValues );
    }
}
