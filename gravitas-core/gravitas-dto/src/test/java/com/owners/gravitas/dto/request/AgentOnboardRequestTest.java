package com.owners.gravitas.dto.request;

import java.util.ArrayList;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.UserAddress;

/**
 * The Class AgentOnboardRequestTest.
 *
 * @author pabhishek
 */
public class AgentOnboardRequestTest {

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
        createAgentOnboardRequestTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "photoData", null );
        defaultValues.put( "roleId", null );
        defaultValues.put( "managingBrokerId", null );
        defaultValues.put( "personalEmail", null );
        defaultValues.put( "mobileCarrier", null );
        defaultValues.put( "agentStartingDate", null );
        defaultValues.put( "notes", null );
        defaultValues.put( "drivingRadius", null );
        defaultValues.put( "deleteFile", false );

        defaultValues.put( "biodata", null );
        defaultValues.put( "photoUrl", null );
        defaultValues.put( "address", null );
        defaultValues.put( "coverageArea", new ArrayList< String >() );
        defaultValues.put( "agentStartingDate", null );
        defaultValues.put( "status", null );

        defaultValues.put( "firstName", null );
        defaultValues.put( "lastName", null );
        defaultValues.put( "email", null );
        defaultValues.put( "phone", null );
        defaultValues.put( "language", null );
        defaultValues.put( "license", null );
        defaultValues.put( "type", null );
        defaultValues.put( "score", null );
        defaultValues.put( "available", false );
        defaultValues.put( "revenue", null );
        defaultValues.put( "encodedPhotoData", null );
    }

    /**
     * Creates the agent onboard request test values.
     */
    private void createAgentOnboardRequestTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "photoData", "bytes".getBytes() );
        testValues.put( "roleId", "roleId" );
        testValues.put( "managingBrokerId", "managingBrokerId" );
        testValues.put( "personalEmail", "personalEmail" );
        testValues.put( "mobileCarrier", "mobileCarrier" );
        testValues.put( "agentStartingDate", "agentStartingDate" );
        testValues.put( "notes", "notes" );
        testValues.put( "drivingRadius", "drivingRadius" );
        testValues.put( "deleteFile", true );

        testValues.put( "biodata", "biodata" );
        testValues.put( "photoUrl", "photoUrl" );
        testValues.put( "address", new UserAddress() );
        testValues.put( "coverageArea", new ArrayList< String >() );
        testValues.put( "agentStartingDate", "agentStartingDate" );
        testValues.put( "status", "active" );

        testValues.put( "firstName", "firstName" );
        testValues.put( "lastName", "lastName" );
        testValues.put( "email", "email" );
        testValues.put( "phone", "phone" );
        testValues.put( "language", "english" );
        testValues.put( "license", "testlic01" );
        testValues.put( "type", "Field Agent" );
        testValues.put( "score", 0.0 );
        testValues.put( "available", true );
        testValues.put( "revenue", 0.0 );
        testValues.put( "encodedPhotoData", "test" );
    }

    /**
     * Test agent onboard request.
     */
    @Test
    public final void testAgentOnboardRequest() {
        final BeanLikeTester blt = new BeanLikeTester( AgentOnboardRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
