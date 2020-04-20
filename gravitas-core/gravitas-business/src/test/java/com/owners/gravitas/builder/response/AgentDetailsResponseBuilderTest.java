package com.owners.gravitas.builder.response;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.api.client.util.ArrayMap;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.UserName;
import com.owners.gravitas.business.builder.response.AgentDetailsResponseBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.dto.UserDetails;
import com.owners.gravitas.dto.response.AgentDetailsResponse;
import com.owners.gravitas.service.AgentDetailsService;
import com.owners.gravitas.service.CRMQueryService;

/**
 * The Class AgentDetailsResponseBuilderTest.
 */
public class AgentDetailsResponseBuilderTest extends AbstractBaseMockitoTest {

    /** The agent details response builder. */
    @InjectMocks
    private AgentDetailsResponseBuilder agentDetailsResponseBuilder;

    /** The agent details service. */
    @Mock
    private AgentDetailsService agentDetailsService;

    /** The crm query service. */
    @Mock
    private CRMQueryService crmQueryService;

    /**
     * Test convert to destination is case.
     */
    @Test
    public void testConvertTo() {
        UserDetails source = new UserDetails();
        User user = new User();
        UserName name = new UserName();
        name.setGivenName( "FirstName" );
        name.setFamilyName( "FamilyName" );
        user.setName( name );
        user.setPrimaryEmail( "user1@test.com" );

        List< ArrayMap< String, Object > > phones = new ArrayList< ArrayMap< String, Object > >();
        ArrayMap< String, Object > phone = new ArrayMap<>();
        phone.put( "type", "work" );
        phone.put( "value", "793847588" );
        phones.add( phone );
        user.setPhones( phones );

        List< ArrayMap< String, Object > > emails = new ArrayList< ArrayMap< String, Object > >();
        ArrayMap< String, Object > email = new ArrayMap<>();
        email.put( "type", "other" );
        email.put( "address", "test@value.com" );
        emails.add( email );
        user.setEmails( emails );

        List< ArrayMap< String, Object > > addresses = new ArrayList< ArrayMap< String, Object > >();
        ArrayMap< String, Object > address = new ArrayMap<>();
        address.put( "type", "home" );
        address.put( "streetAddress", "123 street" );
        addresses.add( address );
        user.setAddresses( addresses );

        Map< String, Map< String, Object > > customSchemas = new ArrayMap< String, Map< String, Object > >();
        Map< String, Object > scheme = new ArrayMap<>();
        scheme.put( "bioData", "value" );
        customSchemas.put( "otherFields", scheme );
        user.setCustomSchemas( customSchemas );

        AgentDetails agent = new AgentDetails();
        agent.setStartingOn( new Date( 0 ) );
        agent.setMobileCarrier( "test" );
        agent.setDrivingRadius( 0 );
        com.owners.gravitas.domain.entity.User broker = new com.owners.gravitas.domain.entity.User();
        broker.setEmail( "broker@test.com" );
        broker.setStatus( "test" );
        agent.setManagingBroker( broker );
        agent.setUser( broker );
        Mockito.when( agentDetailsService.findAgentByEmail( user.getPrimaryEmail() ) ).thenReturn( agent );

        source.setUser( user );
        AgentDetailsResponse destination = agentDetailsResponseBuilder.convertTo( source, new AgentDetailsResponse() );

        Assert.assertNotNull( destination );
        Assert.assertEquals( user.getName().getFamilyName(), destination.getDetails().getLastName() );
        Assert.assertEquals( user.getPrimaryEmail(), destination.getDetails().getEmail() );
    }

    /**
     * Test convert to_ source as null.
     */
    @Test
    public void testConvertTo_SourceAsNull() {
        AgentDetailsResponse destination = agentDetailsResponseBuilder.convertTo( null, new AgentDetailsResponse() );
        Assert.assertNotNull( destination );
    }

    /**
     * Test convert to_ source and dest as null.
     */
    @Test
    public void testConvertTo_SourceAndDestAsNull() {
        AgentDetailsResponse destination = agentDetailsResponseBuilder.convertTo( null, null );
        Assert.assertNull( destination );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        agentDetailsResponseBuilder.convertFrom( new AgentDetailsResponse() );
    }
}
