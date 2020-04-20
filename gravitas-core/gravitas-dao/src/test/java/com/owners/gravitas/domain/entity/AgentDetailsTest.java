package com.owners.gravitas.domain.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class AgentDetailsTest.
 *
 * @author pabhishek
 */
public class AgentDetailsTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The agent detail values. */
    private PropertiesAndValues agentDetailValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createAgentDetailTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        final AgentCoverage agentCoverage1 = new AgentCoverage();
        final AgentCoverage agentCoverage2 = new AgentCoverage();
        List< AgentCoverage > agentCoverages = new ArrayList< AgentCoverage >();
        agentCoverages.add( agentCoverage1 );
        agentCoverages.add( agentCoverage2 );

        final User user = new User();
        final User managingBroker = new User();

        defaultValues = new PropertiesAndValues();
        defaultValues.put( "id", null );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "createdDate", null );
        defaultValues.put( "lastModifiedBy", null );
        defaultValues.put( "lastModifiedDate", null );
        defaultValues.put( "coverageArea", agentCoverages );
        defaultValues.put( "user", user );
        defaultValues.put( "managingBroker", managingBroker );
        defaultValues.put( "mobileCarrier", null );
        defaultValues.put( "startingOn", null );
        defaultValues.put( "endingOn", null );
        defaultValues.put( "drivingRadius", 0 );
        defaultValues.put( "availability", false );
        defaultValues.put( "homeZip", null );
        defaultValues.put( "license", null );
        defaultValues.put( "language", null );
        defaultValues.put( "state", null );
        defaultValues.put( "score", null );
    }

    /**
     * Creates the agent detail test values.
     */
    private void createAgentDetailTestValues() {
        final AgentCoverage agentCoverage1 = new AgentCoverage();
        final AgentCoverage agentCoverage2 = new AgentCoverage();
        List< AgentCoverage > agentCoverages = new ArrayList< AgentCoverage >();
        agentCoverages.add( agentCoverage1 );
        agentCoverages.add( agentCoverage2 );

        final User user = new User();
        final User managingBroker = new User();

        agentDetailValues = new PropertiesAndValues();
        agentDetailValues.put( "id", "test" );
        agentDetailValues.put( "createdBy", "test" );
        agentDetailValues.put( "createdDate", new DateTime() );
        agentDetailValues.put( "lastModifiedBy", "test" );
        agentDetailValues.put( "lastModifiedDate", new DateTime() );
        agentDetailValues.put( "coverageArea", agentCoverages );
        agentDetailValues.put( "user", user );
        agentDetailValues.put( "managingBroker", managingBroker );
        agentDetailValues.put( "mobileCarrier", "atnt" );
        agentDetailValues.put( "startingOn", new Date( 123456 ) );
        agentDetailValues.put( "endingOn", new Date( 123456 ) );
        agentDetailValues.put( "drivingRadius", 10 );
        agentDetailValues.put( "availability", true );
        agentDetailValues.put( "homeZip", "12345" );
        agentDetailValues.put( "license", "lic1" );
        agentDetailValues.put( "language", "EN" );
        agentDetailValues.put( "state", "GA" );
        agentDetailValues.put( "score", 0.0 );
    }

    /**
     * Test agent details.
     */
    @Test
    public final void testAgentDetails() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        List< Class< ? > > signature2 = Arrays.< Class< ? > > asList( List.class, User.class, User.class, String.class,
                Date.class, Date.class, int.class, boolean.class, String.class, String.class, String.class,
                String.class );
        mapping.put( signature2, Arrays.asList( "coverageArea", "user", "managingBroker", "mobileCarrier", "startingOn",
                "endingOn", "drivingRadius", "availability", "homeZip", "state", "license", "language" ) );
        final BeanLikeTester blt = new BeanLikeTester( AgentDetails.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, agentDetailValues );
    }

}
