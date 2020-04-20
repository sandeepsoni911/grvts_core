package com.owners.gravitas.business.builder.response;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.api.client.util.ArrayMap;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.UserName;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.response.UserDetailsResponse;

/**
 * The Class UserDetailsResponseBuilderTest.
 *
 * @author raviz
 */
public class UserDetailsResponseBuilderTest extends AbstractBaseMockitoTest {

    /** The user details response builder. */
    @InjectMocks
    private UserDetailsResponseBuilder userDetailsResponseBuilder;

    /**
     * Test convert to.
     *
     * @param source
     *            the source
     */
    @Test( dataProvider = "sourceDataProvider" )
    public void testConvertTo( final List< User > source ) {
        final UserDetailsResponse userDetailsResponse = userDetailsResponseBuilder.convertTo( source );

        final com.owners.gravitas.dto.User expectedUser = userDetailsResponse.getUsers().get( 0 );
        final User actualUser = source.get( 0 );

        assertNotNull( userDetailsResponse );
        assertEquals( userDetailsResponse.getUsers().size(), source.size() );
        assertEquals( actualUser.getName().getGivenName(), expectedUser.getFirstName() );
        assertEquals( actualUser.getName().getFamilyName(), expectedUser.getLastName() );
        assertEquals( actualUser.getPrimaryEmail(), expectedUser.getEmail() );
        assertEquals( "testPhone", expectedUser.getPhone() );
    }

    /**
     * Test convert to should return null if source and destination are null.
     */
    @Test
    public void testConvertToShouldReturnNullIfSourceAndDestinationAreNull() {
        final UserDetailsResponse userDetailsResponse = userDetailsResponseBuilder.convertTo( null, null );
        assertNull( userDetailsResponse );
    }

    /**
     * Test convert to should return user details when source is empty.
     */
    @Test
    private void testConvertToShouldReturnUserDetailsWhenSourceIsEmpty() {
        final UserDetailsResponse userDetailsResponse = userDetailsResponseBuilder.convertTo( new ArrayList<>(), null );
        assertNotNull( userDetailsResponse );
    }

    /**
     * Test convert to should return null phone when source do not have phone
     * number.
     *
     * @param source
     *            the source
     */
    @Test( dataProvider = "sourceDataProvider" )
    private void testConvertToShouldReturnNullPhoneWhenSourceDoNotHavePhoneNumber( final List< User > source ) {
        source.get( 0 ).setPhones( null ); // sets phone list empty
        final UserDetailsResponse userDetailsResponse = userDetailsResponseBuilder.convertTo( source );

        final com.owners.gravitas.dto.User expectedUser = userDetailsResponse.getUsers().get( 0 );
        final User actualUser = source.get( 0 );

        assertNotNull( userDetailsResponse );
        assertNull( expectedUser.getPhone() );
        assertEquals( userDetailsResponse.getUsers().size(), source.size() );
        assertEquals( actualUser.getName().getGivenName(), expectedUser.getFirstName() );
        assertEquals( actualUser.getName().getFamilyName(), expectedUser.getLastName() );
        assertEquals( actualUser.getPrimaryEmail(), expectedUser.getEmail() );

    }

    /**
     * Test convet to should return empty address when source do not have
     * address.
     *
     * @param source
     *            the source
     */
    @Test( dataProvider = "sourceDataProvider" )
    private void testConvetToShouldReturnEmptyStateWhenSourceDoNotHaveAddress( final List< User > source ) {
        source.get( 0 ).setAddresses( null ); // set address empty
        final UserDetailsResponse userDetailsResponse = userDetailsResponseBuilder.convertTo( source );

        final com.owners.gravitas.dto.User expectedUser = userDetailsResponse.getUsers().get( 0 );
        final User actualUser = source.get( 0 );

        assertNotNull( userDetailsResponse );
        assertEquals( userDetailsResponse.getUsers().size(), source.size() );
        assertEquals( actualUser.getName().getGivenName(), expectedUser.getFirstName() );
        assertEquals( actualUser.getName().getFamilyName(), expectedUser.getLastName() );
        assertEquals( actualUser.getPrimaryEmail(), expectedUser.getEmail() );
        assertEquals( "testPhone", expectedUser.getPhone() );
    }

    /**
     * Gets the source.
     *
     * @return the source
     */
    @DataProvider( name = "sourceDataProvider" )
    private Object[][] getSource() {

        final ArrayMap< String, Object > phoneMap = new ArrayMap<>();
        phoneMap.put( "type", "work" );
        phoneMap.put( "value", "testPhone" );
        final List< ArrayMap< String, Object > > phones = new ArrayList<>();
        phones.add( phoneMap );

        final ArrayMap< String, Object > addressMap = new ArrayMap<>();
        addressMap.put( "type", "home" );
        addressMap.put( "region", "testRegion" );
        final List< ArrayMap< String, Object > > addresses = new ArrayList<>();
        addresses.add( addressMap );

        final UserName userName = new UserName();
        userName.setGivenName( "testGiven" );
        userName.setFamilyName( "familyName" );

        final User user = new User();
        user.setPhones( phones );
        user.setAddresses( addresses );
        user.setPrimaryEmail( "testPrimaryEmail" );
        user.setName( userName );

        final List< User > source = new ArrayList<>();
        source.add( user );

        return new Object[][] { { source } };
    }

}
