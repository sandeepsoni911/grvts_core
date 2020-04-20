package com.owners.gravitas.builder;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.util.Date;

import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.business.builder.AgentOnboardRequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.constants.UserRole;
import com.owners.gravitas.dto.UserAddress;
import com.owners.gravitas.dto.request.AgentOnboardRequest;
import com.owners.gravitas.util.PropertiesUtil;

/**
 * The Class AgentOnboardRequestBuilderTest.
 *
 * @author raviz
 */
@PrepareForTest( PropertiesUtil.class )
public class AgentOnboardRequestBuilderTest extends AbstractBaseMockitoTest {

    /** The agent onboard request builder. */
    @InjectMocks
    private AgentOnboardRequestBuilder agentOnboardRequestBuilder;

    /**
     * Test convert to should return onboard request when source is not null.
     *
     * @param sourceObject
     *            the source object
     */
    @Test( dataProvider = "agentSourceDataProvider" )
    public void testConvertToShouldReturnOnboardRequestWhenSourceIsNotNull( final Object sourceObject ) {
        final AgentSource source = ( AgentSource ) sourceObject;
        final String language = ( String ) ReflectionTestUtils.getField( agentOnboardRequestBuilder, "LANGUAGE" );
        final String propertyState = ( String ) ReflectionTestUtils.getField( agentOnboardRequestBuilder,
                "PROPERTY_STATE" );
        final String mbEmail = "fredrik.eriksson@owners.com";

        mockStatic( PropertiesUtil.class );
        when( PropertiesUtil.getProperty( propertyState + source.getState() ) ).thenReturn( mbEmail );

        final AgentOnboardRequest destination = agentOnboardRequestBuilder.convertTo( source );

        assertNotNull( destination );
        assertEquals( destination.getFirstName(), "testFirstName" );
        assertEquals( destination.getLastName(), "testLastName" );
        assertEquals( destination.getEmail(), source.getEmail() );
        assertEquals( destination.getPhone(), source.getPhone() );
        assertEquals( destination.getLanguage(), language );
        assertEquals( destination.getPersonalEmail(), source.getEmail() );
        assertEquals( destination.getType(), source.getAgentType() );
        assertEquals( destination.getMobileCarrier(), source.getMobileCarrier() );
        assertNotNull( destination.getAgentStartingDate() );
        assertEquals( destination.getNotes(), source.getNotes() );
        assertEquals( destination.getDrivingRadius(), source.getDrivingRadius() );
        assertEquals( destination.getStatus(), source.getStatus() );
        assertEquals( destination.getLicense(), source.getLicense() );
        assertEquals( destination.isAvailable(), source.isAvailable() );
        assertEquals( destination.getManagingBrokerId(), mbEmail );
        assertEquals( destination.getRoleId(), UserRole.FIREBASE_AGENT );

        final UserAddress address = destination.getAddress();
        assertEquals( address.getAddress1(), source.getAddress1() );
        assertEquals( address.getAddress2(), source.getAddress2() );
        assertEquals( address.getCity(), source.getCity() );
        assertEquals( address.getState(), source.getState() );
        assertEquals( address.getZip(), source.getHomeZip() );
    }

    /**
     * Test convert to should set last name to null when full name contains only
     * first name.
     *
     * @param sourceObject
     *            the source object
     */
    @Test( dataProvider = "agentSourceDataProvider" )
    public void testConvertToShouldSetLastNameToNullWhenFullNameContainsOnlyFirstName( final Object sourceObject ) {
        final AgentSource source = ( AgentSource ) sourceObject;
        final String firstName = "testFirstName";
        source.setName( firstName );
        final String propertyState = ( String ) ReflectionTestUtils.getField( agentOnboardRequestBuilder,
                "PROPERTY_STATE" );
        final String mbEmail = "fredrik.eriksson@owners.com";
        mockStatic( PropertiesUtil.class );
        when( PropertiesUtil.getProperty( propertyState + source.getState() ) ).thenReturn( mbEmail );

        final AgentOnboardRequest destination = agentOnboardRequestBuilder.convertTo( source );
        assertNotNull( destination );
        assertEquals( destination.getFirstName(), firstName );
        assertNull( destination.getLastName() );
    }

    /**
     * Test convert to should set last name to null when full name contains more
     * than three words.
     *
     * @param sourceObject
     *            the source object
     */
    @Test( dataProvider = "agentSourceDataProvider" )
    public void testConvertToShouldSetLastNameToNullWhenFullNameContainsMoreThanThreeWords(
            final Object sourceObject ) {
        final AgentSource source = ( AgentSource ) sourceObject;
        final String firstName = "testFirstName";
        final String lastName = "testLastName";
        final String extraName = "afterLastName";
        source.setName( firstName + " " + lastName + " " + extraName );
        final String propertyState = ( String ) ReflectionTestUtils.getField( agentOnboardRequestBuilder,
                "PROPERTY_STATE" );
        final String mbEmail = "fredrik.eriksson@owners.com";
        mockStatic( PropertiesUtil.class );
        when( PropertiesUtil.getProperty( propertyState + source.getState() ) ).thenReturn( mbEmail );

        final AgentOnboardRequest destination = agentOnboardRequestBuilder.convertTo( source,
                new AgentOnboardRequest() );
        assertNotNull( destination );
        assertEquals( destination.getFirstName(), "testFirstName" );
        assertEquals( destination.getLastName(), lastName + " " + extraName );
    }

    /**
     * Test convert to should return null when source is null.
     */
    @Test
    public void testConvertToShouldReturnNullWhenSourceIsNull() {
        final AgentOnboardRequest destination = agentOnboardRequestBuilder.convertTo( null );
        assertNull( destination );
    }

    /**
     * Test convert from should throw exception.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFromShouldThrowException() {
        agentOnboardRequestBuilder.convertFrom( new AgentOnboardRequest() );
    }

    /**
     * Gets the agent source.
     *
     * @return the agent source
     */
    @DataProvider( name = "agentSourceDataProvider" )
    private Object[][] getAgentSource() {
        final AgentSource agentSource = new AgentSource();
        agentSource.setName( "testFirstName" + " " + "testLastName" );
        agentSource.setEmail( "tet@email.com" );
        agentSource.setPhone( "testPhone" );
        agentSource.setAgentType( "Field Agent" );
        agentSource.setMobileCarrier( "test" );
        agentSource.setStartingDate( new Date() );
        agentSource.setNotes( "testNotes" );
        agentSource.setDrivingRadius( "1.0" );
        agentSource.setStatus( "active" );
        agentSource.setLicense( "license" );
        agentSource.setAvailable( true );
        agentSource.setState( "FL" );
        agentSource.setAddress1( "address1" );
        agentSource.setAddress2( "address2" );
        agentSource.setCity( "city" );
        agentSource.setState( "state" );
        agentSource.setHomeZip( "zip" );
        return new Object[][] { { agentSource } };
    }
}
