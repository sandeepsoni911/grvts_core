package com.owners.gravitas.dto.crm.response;

import java.util.Arrays;
import java.util.List;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.LeadResponse;

/**
 * The Class LeadResponseTest.
 *
 * @author vishwanathm
 */
public class LeadResponseTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues leadResponseTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * LeadResponse.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createLeadResponseTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "id", null );
        defaultValues.put( "message", "Operation was successful" );
        defaultValues.put( "status", Status.SUCCESS );
    }

    /**
     * This method is to create the actual values.
     */
    private void createLeadResponseTestValues() {
        leadResponseTestValues = new PropertiesAndValues();
        leadResponseTestValues.put( "id", "454454" );
        leadResponseTestValues.put( "message", "msg" );
        leadResponseTestValues.put( "status", Status.SUCCESS );
    }

    /**
     * Tests the {@link LeadResponse} with default values. Tests the getters
     * and setters of LeadResponse.
     */
    @Test
    public final void testLeadResponse() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();

        List< Class< ? > > signature = Arrays.< Class< ? > > asList( BaseResponse.Status.class, String.class,
                String.class );
        mapping.put( signature, Arrays.asList( "status", "message", "id" ) );
        final BeanLikeTester blt = new BeanLikeTester( LeadResponse.class, mapping );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, leadResponseTestValues );
    }
}
