package com.owners.gravitas.builder.response;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.api.client.util.ArrayMap;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.UserName;
import com.owners.gravitas.business.builder.response.UserDetailsResponseBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.response.UserDetailsResponse;

// TODO: Auto-generated Javadoc
/**
 * The Class AgentDetailsResponseBuilderTest.
 */
public class UserDetailsResponseBuilderTest extends AbstractBaseMockitoTest {

    /** The user details response builder. */
    @InjectMocks
    private UserDetailsResponseBuilder userDetailsResponseBuilder;

    /**
     * Test convert to destination is case.
     */
    @Test
    public void testConvertTo() {
        List< User > source = new ArrayList<>();
        UserName googleUserName1 = new UserName();
        googleUserName1.setGivenName( "Tom" );
        googleUserName1.setFamilyName( "Hardy" );

        User user1 = new User();
        user1.setPrimaryEmail( "tom@hardy.com" );
        user1.setName( googleUserName1 );

        List< ArrayMap< String, Object > > phones = new ArrayList< ArrayMap< String, Object > >();
        ArrayMap< String, Object > phone = new ArrayMap<>();
        phone.put( "type", "work" );
        phone.put( "value", "793847588" );
        phones.add( phone );
        user1.setPhones( phones );

        List< ArrayMap< String, Object > > addresses = new ArrayList< ArrayMap< String, Object > >();
        ArrayMap< String, Object > address = new ArrayMap<>();
        address.put( "type", "home" );
        address.put( "region", "street" );
        addresses.add( address );
        user1.setAddresses( addresses );
        source.add( user1 );

        UserName googleUserName2 = new UserName();
        googleUserName2.setGivenName( "Peter" );
        googleUserName2.setFamilyName( "Parker" );

        User user2 = new User();
        user2.setPrimaryEmail( "tom@hardy.com" );
        user2.setName( googleUserName2 );

        user2.setPhones( phones );

        user2.setAddresses( addresses );
        source.add( user2 );

        UserDetailsResponse userDetailsResponse = userDetailsResponseBuilder.convertTo( source,
                new UserDetailsResponse() );

        Assert.assertNotNull( userDetailsResponse );
        Assert.assertEquals( source.get( 0 ).getName().getFamilyName(),
                userDetailsResponse.getUsers().get( 0 ).getLastName() );
        Assert.assertEquals( source.get( 0 ).getPrimaryEmail(), userDetailsResponse.getUsers().get( 0 ).getEmail() );
    }

    /**
     * Test convert to_ source as null.
     */
    @Test
    public void testConvertTo_SourceAsNull() {
        UserDetailsResponse destination = userDetailsResponseBuilder.convertTo( null, new UserDetailsResponse() );
        Assert.assertNotNull( destination );
    }

    /**
     * Test convert to_ destination as null.
     */
    @Test
    public void testConvertTo_DestinationAsNull() {
        UserDetailsResponse destination = userDetailsResponseBuilder.convertTo( new ArrayList< User >(), null );
        Assert.assertNotNull( destination );
    }

    /**
     * Test convert to_ source and dest as null.
     */
    @Test
    public void testConvertTo_SourceAndDestAsNull() {
        UserDetailsResponse destination = userDetailsResponseBuilder.convertTo( null, null );
        Assert.assertNull( destination );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        userDetailsResponseBuilder.convertFrom( new UserDetailsResponse() );
    }
}
