package com.owners.gravitas.builder.response;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.api.client.util.ArrayMap;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.UserName;
import com.owners.gravitas.business.builder.response.AgentsResponseBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.response.AgentsResponse;
import com.owners.gravitas.dto.response.RoleDetailsResponse;

public class AgentsResponseBuilderTest extends AbstractBaseMockitoTest {

    @InjectMocks
    private AgentsResponseBuilder agentsResponseBuilder;

    @Test
    public void testConvertToSoruceNull() {
        AgentsResponse response = agentsResponseBuilder.convertTo( null );
        Assert.assertNull( response );
    }

    @Test
    public void testConvertToSoruceNotNull() {
        List< User > users = new ArrayList<>();
        AgentsResponse response = agentsResponseBuilder.convertTo( users );
        Assert.assertNotNull( response );
    }

    @Test
    public void testConvertToSoruceAndDestinationNotNull() {
        List< User > users = new ArrayList<>();
        AgentsResponse response = agentsResponseBuilder.convertTo( users, new AgentsResponse() );
        Assert.assertNotNull( response );
    }

    @Test
    public void testConvertToSoruceNotNullWitUserObject() {
        List< User > users = new ArrayList<>();
        User user = new User();
        UserName username = new UserName();
        username.setGivenName( "test" );
        username.setFamilyName( "test" );
        user.setName( username );
        user.setPrimaryEmail( "test@test.com" );
        ArrayMap< String, Object > arrayMap = new ArrayMap<>();
        List< ArrayMap< String, Object > > objs = new ArrayList<>();
        arrayMap.add( "type", "work" );
        arrayMap.add( "value", "888888888" );
        objs.add( arrayMap );
        user.setPhones( objs );
        arrayMap = new ArrayMap<>();
        objs = new ArrayList<>();
        arrayMap.add( "type", "home" );
        arrayMap.add( "region", "test" );
        objs.add( arrayMap );
        user.setAddresses( objs );
        users.add( user );
        AgentsResponse response = agentsResponseBuilder.convertTo( users );
        Assert.assertNotNull( response );
        Assert.assertNotNull( response.getAgents() );
        Assert.assertTrue( response.getAgents().size() > 0 );
        Assert.assertNotNull( response.getAgents().get( 0 ) );
        Assert.assertNotNull( response.getAgents().get( 0 ).getPhone() );
        Assert.assertNotNull( response.getAgents().get( 0 ).getAddress() );
    }

    /**
     * Test convert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFrom() {
        agentsResponseBuilder.convertFrom( new AgentsResponse() );
    }
}
