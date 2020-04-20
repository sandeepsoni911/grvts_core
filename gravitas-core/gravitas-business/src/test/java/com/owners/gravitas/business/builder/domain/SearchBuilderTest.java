package com.owners.gravitas.business.builder.domain;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.domain.SearchBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.AgentHolder;
import com.owners.gravitas.domain.AgentInfo;
import com.owners.gravitas.domain.Contact;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.Search;

/**
 * The Class SearchBuilderTest.
 *
 * @author vishwanathm
 */
public class SearchBuilderTest extends AbstractBaseMockitoTest {

    /** The search builder. */
    @InjectMocks
    private SearchBuilder searchBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        AgentHolder agentHolder = new AgentHolder();
        Opportunity opp = new Opportunity();
        opp.addContact( "contactId" );
        agentHolder.setAgent( new Agent() );
        agentHolder.getAgent().addOpportunity( "opportunityId", opp );
        Contact contact = new Contact();
        contact.addEmail( "email@email.email" );
        AgentInfo info = new AgentInfo();
        info.setEmail( "test" );
        agentHolder.getAgent().addContact( "contactId", contact );
        agentHolder.getAgent().setInfo( info );
        Map< String, Search > searchList = searchBuilder.convertTo( agentHolder );
        Assert.assertNotNull( searchList );
        Assert.assertTrue( !searchList.isEmpty() );

        agentHolder = new AgentHolder();
        agentHolder.setAgent( new Agent() );
        info.setEmail( "test" );
        agentHolder.getAgent().setInfo( info );
        searchList = searchBuilder.convertTo( agentHolder );
        Assert.assertNotNull( searchList );
    }

    /**
     * Test convert to null source.
     */
    @Test
    public void testConvertToNullSource() {
        Map< String, Search > searchList = searchBuilder.convertTo( null );
        Assert.assertNull( searchList );
    }

    /**
     * Test convert to null destination.
     */
    @Test
    public void testConvertToNullDestination() {
        Map< String, Search > searchList = searchBuilder.convertTo( null, null );
        Assert.assertNull( searchList );
        AgentInfo info = new AgentInfo();
        info.setEmail( "test" );
        AgentHolder agentHolder = new AgentHolder();
        agentHolder.setAgent( new Agent() );
        agentHolder.getAgent().setInfo( info );
        searchList = searchBuilder.convertTo( agentHolder );
        Assert.assertNotNull( searchList );

        searchList = searchBuilder.convertTo( agentHolder, null );
        Assert.assertNotNull( searchList );

        searchList = searchBuilder.convertTo( agentHolder, new HashMap< String, Search >() );
        Assert.assertNotNull( searchList );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        searchBuilder.convertFrom( new HashMap< String, Search >() );
    }
}
