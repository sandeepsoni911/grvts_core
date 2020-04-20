package com.owners.gravitas.business.builder.domain;

import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.UserEmail;
import com.google.api.services.admin.directory.model.UserName;
import com.google.api.services.admin.directory.model.UserPhone;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.dto.UserAddress;
import com.owners.gravitas.dto.request.AgentOnboardRequest;

/**
 * The Class GoogleUserBuilderTest.
 *
 * @author raviz
 */
public class GoogleUserBuilderTest extends AbstractBaseMockitoTest {

    /** The google user builder. */
    @InjectMocks
    private GoogleUserBuilder googleUserBuilder;

    /**
     * Test convert from should return user when status is active.
     *
     * @param agentOnboardRequest
     *            the agent onboard request
     */
    @Test( dataProvider = "agentOnboardRequestData" )
    public void testConvertFromShouldReturnUserWhenStatusIsActive( final AgentOnboardRequest agentOnboardRequest ) {
        agentOnboardRequest.setStatus( "active" );
        final String defaultPassword = "defaultPassword";
        ReflectionTestUtils.setField( googleUserBuilder, "defaultPassword", defaultPassword );

        final User user = googleUserBuilder.convertFrom( agentOnboardRequest );

        assertNotNull( user );
        assertEquals( user.getName().getGivenName(), agentOnboardRequest.getFirstName() );
        assertEquals( user.getName().getFamilyName(), agentOnboardRequest.getLastName() );
        assertEquals( user.getName().getFullName(),
                agentOnboardRequest.getFirstName() + " " + agentOnboardRequest.getLastName() );
        assertTrue( user.getChangePasswordAtNextLogin() );
        assertEquals( user.getPrimaryEmail(), agentOnboardRequest.getEmail() );
        assertEquals( user.getHashFunction(), Constants.HASH_FUNCTION );
        assertEquals( user.getPassword(), defaultPassword );
        final List< com.google.api.services.admin.directory.model.UserAddress > userAddress = ( List< com.google.api.services.admin.directory.model.UserAddress > ) user
                .getAddresses();
        assertEquals( userAddress.get( 0 ).getStreetAddress(), agentOnboardRequest.getAddress().getAddress1() );
        assertEquals( userAddress.get( 0 ).getCountry(), agentOnboardRequest.getAddress().getAddress2() );
        assertEquals( userAddress.get( 0 ).getLocality(), agentOnboardRequest.getAddress().getCity() );
        assertEquals( userAddress.get( 0 ).getRegion(), agentOnboardRequest.getAddress().getState() );
        assertEquals( userAddress.get( 0 ).getPostalCode(), agentOnboardRequest.getAddress().getZip() );
        assertEquals( userAddress.get( 0 ).getType(), "home" );

        final List< UserPhone > userPhones = ( List< UserPhone > ) user.getPhones();
        assertTrue( userPhones.get( 0 ).getPrimary() );
        assertEquals( userPhones.get( 0 ).getValue(), agentOnboardRequest.getPhone() );
        assertEquals( userPhones.get( 0 ).getType(), "work" );

        final List< UserEmail > userEmails = ( List< UserEmail > ) user.getEmails();
        assertEquals( userEmails.size(), 2 );

        final Map< String, Map< String, Object > > customSchemas = user.getCustomSchemas();
        final Map< String, Object > otherFieldsSchema = customSchemas.get( "otherFields" );
        assertEquals( otherFieldsSchema.get( "bioData" ), agentOnboardRequest.getBiodata() );
    }

    /**
     * Test convert from should return user when status is inactive.
     *
     * @param agentOnboardRequest
     *            the agent onboard request
     */
    @Test( dataProvider = "agentOnboardRequestData" )
    public void testConvertFromShouldReturnUserWhenStatusIsInactive( final AgentOnboardRequest agentOnboardRequest ) {
        agentOnboardRequest.setStatus( "inactive" );
        final String disabledPassword = "disabledPassword";
        ReflectionTestUtils.setField( googleUserBuilder, "disabledPassword", disabledPassword );

        final User user = googleUserBuilder.convertFrom( agentOnboardRequest );

        assertNotNull( user );
        assertEquals( user.getName().getGivenName(), agentOnboardRequest.getFirstName() );
        assertEquals( user.getName().getFamilyName(), agentOnboardRequest.getLastName() );
        assertEquals( user.getName().getFullName(),
                agentOnboardRequest.getFirstName() + " " + agentOnboardRequest.getLastName() );
        assertTrue( user.getChangePasswordAtNextLogin() );
        assertEquals( user.getPrimaryEmail(), "disabled_" + agentOnboardRequest.getEmail() );
        assertEquals( user.getPassword(), disabledPassword );
        assertEquals( user.getHashFunction(), Constants.HASH_FUNCTION );
        final List< com.google.api.services.admin.directory.model.UserAddress > userAddress = ( List< com.google.api.services.admin.directory.model.UserAddress > ) user
                .getAddresses();
        assertEquals( userAddress.get( 0 ).getStreetAddress(), agentOnboardRequest.getAddress().getAddress1() );
        assertEquals( userAddress.get( 0 ).getCountry(), agentOnboardRequest.getAddress().getAddress2() );
        assertEquals( userAddress.get( 0 ).getLocality(), agentOnboardRequest.getAddress().getCity() );
        assertEquals( userAddress.get( 0 ).getRegion(), agentOnboardRequest.getAddress().getState() );
        assertEquals( userAddress.get( 0 ).getPostalCode(), agentOnboardRequest.getAddress().getZip() );
        assertEquals( userAddress.get( 0 ).getType(), "home" );

        final List< UserPhone > userPhones = ( List< UserPhone > ) user.getPhones();
        assertTrue( userPhones.get( 0 ).getPrimary() );
        assertEquals( userPhones.get( 0 ).getValue(), agentOnboardRequest.getPhone() );
        assertEquals( userPhones.get( 0 ).getType(), "work" );

        final List< UserEmail > userEmails = ( List< UserEmail > ) user.getEmails();
        assertEquals( userEmails.size(), 2 );

        final Map< String, Map< String, Object > > customSchemas = user.getCustomSchemas();
        final Map< String, Object > otherFieldsSchema = customSchemas.get( "otherFields" );
        assertEquals( otherFieldsSchema.get( "bioData" ), agentOnboardRequest.getBiodata() );
    }

    /**
     * Test convert from should return user when destination is not null.
     *
     * @param agentOnboardRequest
     *            the agent onboard request
     */
    @Test( dataProvider = "agentOnboardRequestData" )
    public void testConvertFromShouldReturnUserWhenDestinationIsNotNull(
            final AgentOnboardRequest agentOnboardRequest ) {
        agentOnboardRequest.setStatus( "active" );
        final String defaultPassword = "defaultPassword";
        ReflectionTestUtils.setField( googleUserBuilder, "defaultPassword", defaultPassword );
        final User destination = new User();
        final UserName userName = new UserName();
        userName.setGivenName( "testOriginalGivenName" );
        userName.setFamilyName( "testOriginalFamilyName" );
        userName.setFullName( "testOriginalGivenName" + " " + "testOriginalFamilyName" );
        destination.setName( userName );

        final User user = googleUserBuilder.convertFrom( agentOnboardRequest, destination );

        assertNotNull( user );
        assertNotEquals( user.getName().getGivenName(), agentOnboardRequest.getFirstName() );
        assertNotEquals( user.getName().getFamilyName(), agentOnboardRequest.getLastName() );
        assertNotEquals( user.getName().getFullName(),
                agentOnboardRequest.getFirstName() + " " + agentOnboardRequest.getLastName() );
        assertNull( user.getChangePasswordAtNextLogin() );
        assertEquals( user.getPrimaryEmail(), agentOnboardRequest.getEmail() );
        final List< com.google.api.services.admin.directory.model.UserAddress > userAddress = ( List< com.google.api.services.admin.directory.model.UserAddress > ) user
                .getAddresses();
        assertEquals( userAddress.get( 0 ).getStreetAddress(), agentOnboardRequest.getAddress().getAddress1() );
        assertEquals( userAddress.get( 0 ).getCountry(), agentOnboardRequest.getAddress().getAddress2() );
        assertEquals( userAddress.get( 0 ).getLocality(), agentOnboardRequest.getAddress().getCity() );
        assertEquals( userAddress.get( 0 ).getRegion(), agentOnboardRequest.getAddress().getState() );
        assertEquals( userAddress.get( 0 ).getPostalCode(), agentOnboardRequest.getAddress().getZip() );
        assertEquals( userAddress.get( 0 ).getType(), "home" );

        final List< UserPhone > userPhones = ( List< UserPhone > ) user.getPhones();
        assertTrue( userPhones.get( 0 ).getPrimary() );
        assertEquals( userPhones.get( 0 ).getValue(), agentOnboardRequest.getPhone() );
        assertEquals( userPhones.get( 0 ).getType(), "work" );

        final List< UserEmail > userEmails = ( List< UserEmail > ) user.getEmails();
        assertEquals( userEmails.size(), 2 );

        final Map< String, Map< String, Object > > customSchemas = user.getCustomSchemas();
        final Map< String, Object > otherFieldsSchema = customSchemas.get( "otherFields" );
        assertEquals( otherFieldsSchema.get( "bioData" ), agentOnboardRequest.getBiodata() );
    }

    /**
     * Test convert from should return user when address is null.
     *
     * @param agentOnboardRequest
     *            the agent onboard request
     */
    @Test( dataProvider = "agentOnboardRequestData" )
    public void testConvertFromShouldReturnUserWhenAddressIsNull( final AgentOnboardRequest agentOnboardRequest ) {
        agentOnboardRequest.setStatus( "active" );
        final String defaultPassword = "defaultPassword";
        ReflectionTestUtils.setField( googleUserBuilder, "defaultPassword", defaultPassword );
        agentOnboardRequest.setAddress( null );

        final User user = googleUserBuilder.convertFrom( agentOnboardRequest );

        assertNotNull( user );
        assertEquals( user.getName().getGivenName(), agentOnboardRequest.getFirstName() );
        assertEquals( user.getName().getFamilyName(), agentOnboardRequest.getLastName() );
        assertEquals( user.getName().getFullName(),
                agentOnboardRequest.getFirstName() + " " + agentOnboardRequest.getLastName() );
        assertTrue( user.getChangePasswordAtNextLogin() );
        assertEquals( user.getPrimaryEmail(), agentOnboardRequest.getEmail() );
        assertEquals( user.getHashFunction(), Constants.HASH_FUNCTION );
        assertEquals( user.getPassword(), defaultPassword );
        final List< com.google.api.services.admin.directory.model.UserAddress > userAddress = ( List< com.google.api.services.admin.directory.model.UserAddress > ) user
                .getAddresses();
        assertTrue( isEmpty( userAddress ) );

        final List< UserPhone > userPhones = ( List< UserPhone > ) user.getPhones();
        assertTrue( userPhones.get( 0 ).getPrimary() );
        assertEquals( userPhones.get( 0 ).getValue(), agentOnboardRequest.getPhone() );
        assertEquals( userPhones.get( 0 ).getType(), "work" );

        final List< UserEmail > userEmails = ( List< UserEmail > ) user.getEmails();
        assertEquals( userEmails.size(), 2 );

        final Map< String, Map< String, Object > > customSchemas = user.getCustomSchemas();
        final Map< String, Object > otherFieldsSchema = customSchemas.get( "otherFields" );
        assertEquals( otherFieldsSchema.get( "bioData" ), agentOnboardRequest.getBiodata() );
    }

    /**
     * Test convert from should return null when source is null.
     */
    @Test
    public void testConvertFromShouldReturnNullWhenSourceIsNull() {
        final User user = googleUserBuilder.convertFrom( null );
        assertNull( user );
    }

    /**
     * Test convert to should throw exception.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertToShouldThrowException() {
        googleUserBuilder.convertTo( new User() );
    }

    /**
     * Gets the agent onboard request data.
     *
     * @return the agent onboard request data
     */
    @DataProvider( name = "agentOnboardRequestData" )
    private Object[][] getAgentOnboardRequestData() {
        final AgentOnboardRequest agentOnboardRequest = new AgentOnboardRequest();
        final UserAddress userAddress = new UserAddress();
        userAddress.setAddress1( "testAddress1" );
        userAddress.setAddress2( "testAddress1" );
        userAddress.setCity( "testCity" );
        userAddress.setState( "testState" );
        userAddress.setZip( "testZip" );

        agentOnboardRequest.setFirstName( "testFirstName" );
        agentOnboardRequest.setLastName( "testlastName" );
        agentOnboardRequest.setAddress( userAddress );
        agentOnboardRequest.setPhone( "testPhone" );
        agentOnboardRequest.setEmail( "test@test.com" );
        agentOnboardRequest.setPersonalEmail( "test@ptest.com" );
        agentOnboardRequest.setBiodata( "testBioData" );

        return new Object[][] { { agentOnboardRequest } };
    }

}
