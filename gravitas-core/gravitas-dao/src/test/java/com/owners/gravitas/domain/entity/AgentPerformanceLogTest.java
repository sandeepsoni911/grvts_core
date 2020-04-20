package com.owners.gravitas.domain.entity;

import java.util.Arrays;
import java.util.List;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class AgentPerformanceLogTest.
 *
 * @author shivamm
 */
public class AgentPerformanceLogTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The test values. */
    private PropertiesAndValues testValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "agentFbId", null );
        defaultValues.put( "buyerOpportunitiesPercentage", null );
        defaultValues.put( "sellerOpportunitiesPercentage", null );
        defaultValues.put( "pendingTransactions", null );
        defaultValues.put( "closedTransactions", null );
        defaultValues.put( "responseTime", null );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "agentPerformanceLogId", null );
    }

    /**
     * Creates the test values.
     */
    private void createTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "agentFbId", "test" );
        testValues.put( "buyerOpportunitiesPercentage", 1.1d );
        testValues.put( "sellerOpportunitiesPercentage", 1.1d );
        testValues.put( "pendingTransactions", 0 );
        testValues.put( "closedTransactions", 0 );
        testValues.put( "responseTime", 1l );
        testValues.put( "createdBy", "test" );
        testValues.put( "agentPerformanceLogId", new AgentPerformanceLogId() );
    }

    /**
     * Test prospect attribute.
     */
    @Test
    public final void testProspectAttribute() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        List< Class< ? > > signature2 = Arrays.< Class< ? > > asList( String.class, AgentPerformanceLogId.class );
        mapping.put( signature1, Arrays.asList() );
        mapping.put( signature2, Arrays.asList( "agentFbId", "agentPerformanceLogId" ) );

        final BeanLikeTester blt = new BeanLikeTester( AgentPerformanceLog.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
