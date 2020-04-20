package com.owners.gravitas.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.domain.Search;

public class SearchTest {
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
        defaultValues.put( "id", null );
        defaultValues.put( "agentId", null );
        defaultValues.put( "agentEmail", null );
        defaultValues.put( "contactEmail", null );
        defaultValues.put( "contactId", null );
        defaultValues.put( "opportunityId", null );
        defaultValues.put( "crmOpportunityId", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "id", "test" );
        testValues.put( "agentId", "test" );
        testValues.put( "agentEmail", "test" );
        testValues.put( "contactEmail", "test" );
        testValues.put( "contactId", "test" );
        testValues.put( "opportunityId", "test" );
        testValues.put( "crmOpportunityId", "test" );
    }

    /**
     * Tests the {@link Search} with default values. Tests the getters
     * and setters of Search.
     */
    @Test
    public final void testSearch() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();

        List< Class< ? > > signature2 = Arrays.< Class< ? > > asList( String.class, String.class, String.class, String.class,
                String.class, String.class, String.class );
        mapping.put( Arrays.< Class< ? > > asList(), new ArrayList<>() );
        mapping.put( signature2, Arrays.asList( "id", "agentId", "agentEmail", "contactId", "crmOpportunityId",
                "contactEmail", "opportunityId" ) );
        final BeanLikeTester blt = new BeanLikeTester( Search.class, mapping );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
