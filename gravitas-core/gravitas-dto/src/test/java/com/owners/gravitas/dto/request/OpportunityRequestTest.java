package com.owners.gravitas.dto.request;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.PropertyOrder;
import com.owners.gravitas.dto.Seller;

/**
 * The Class OpportunityRequestTest.
 */
public class OpportunityRequestTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues leadRequestTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * OpportunityRequest.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createOpportunityRequestTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "seller", null );
        defaultValues.put( "propertyOrder", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createOpportunityRequestTestValues() {
        leadRequestTestValues = new PropertiesAndValues();
        leadRequestTestValues.put( "seller", new Seller() );
        leadRequestTestValues.put( "propertyOrder", new PropertyOrder() );
    }

    /**
     * Tests the {@link OpportunityRequest} with default values. Tests the getters
     * and setters of OpportunityRequest.
     */
    @Test
    public final void testOpportunityRequest() {
        final BeanLikeTester blt = new BeanLikeTester( OpportunityRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, leadRequestTestValues );
    }

}
