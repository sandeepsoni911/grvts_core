package com.owners.gravitas.domain.entity;

import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class AgentRatingTest.
 * 
 * @author pabhishek
 */
public class AgentRatingTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The agent rating values. */
    private PropertiesAndValues agentRatingValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createAgentRatingTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "id", null );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "createdDate", null );
        defaultValues.put( "lastModifiedBy", null );
        defaultValues.put( "lastModifiedDate", null );
        defaultValues.put( "clientEmail", null );
        defaultValues.put( "clientFirstName", null );
        defaultValues.put( "clientLastName", null );
        defaultValues.put( "agentDetails", null );
        defaultValues.put( "crmId", null );
        defaultValues.put( "crmObjectType", null );
        defaultValues.put( "stage", null );
        defaultValues.put( "feedbackReceivedCount", 0 );
        defaultValues.put( "rating", 0 );
        defaultValues.put( "feedback", null );
        defaultValues.put( "comments", null );
    }

    /**
     * Creates the agent rating test values.
     */
    private void createAgentRatingTestValues() {
        final AgentDetails agentDetails = new AgentDetails();
        agentRatingValues = new PropertiesAndValues();
        agentRatingValues.put( "id", "1" );
        agentRatingValues.put( "createdBy", "gravitas" );
        agentRatingValues.put( "createdDate", new DateTime() );
        agentRatingValues.put( "lastModifiedBy", "gravitas" );
        agentRatingValues.put( "lastModifiedDate", new DateTime() );
        agentRatingValues.put( "clientEmail", "a@test.com" );
        agentRatingValues.put( "clientFirstName", "test" );
        agentRatingValues.put( "clientLastName", "user" );
        agentRatingValues.put( "agentDetails", agentDetails );
        agentRatingValues.put( "crmId", "crmid" );
        agentRatingValues.put( "crmObjectType", "type" );
        agentRatingValues.put( "stage", "new" );
        agentRatingValues.put( "feedbackReceivedCount", 1 );
        agentRatingValues.put( "rating", 2 );
        agentRatingValues.put( "feedback", "ok" );
        agentRatingValues.put( "comments", "comments" );
    }

    /**
     * Test agent rating.
     */
    @Test
    public final void testAgentRating() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        final BeanLikeTester blt = new BeanLikeTester( AgentRating.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, agentRatingValues );
    }
}
