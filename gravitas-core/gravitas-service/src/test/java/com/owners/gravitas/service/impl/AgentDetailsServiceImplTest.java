package com.owners.gravitas.service.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.AgentCoverage;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.repository.AgentDetailsRepository;
import com.owners.gravitas.repository.OpportunityRepository;

/**
 * The Class AgentDetailsServiceImplTest.
 *
 * @author ankusht
 */
public class AgentDetailsServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent service impl. */
    @InjectMocks
    private AgentDetailsServiceImpl agentDetailsServiceImpl;

    /** The agent details repository. */
    @Mock
    private AgentDetailsRepository agentDetailsRepository;

    /** The opportunity repository. */
    @Mock
    private OpportunityRepository opportunityV1Repository;

    /**
     * Test save.
     */
    @Test
    public void testSave() {
        final AgentDetails agentDetails = new AgentDetails();
        final AgentDetails expected = new AgentDetails();
        when( agentDetailsRepository.save( agentDetails ) ).thenReturn( expected );
        final AgentDetails actual = agentDetailsServiceImpl.save( agentDetails );
        verify( agentDetailsRepository ).save( agentDetails );
        assertEquals( expected, actual );
    }

    /**
     * Test get agent coverage areas should return coverage areas if present in
     * agent details.
     */
    @Test
    public void testGetAgentCoverageAreasShouldReturnCoverageAreasIfPresentInAgentDetails() {
        final String email = "test@test.com";
        final AgentDetails agentDetails = new AgentDetails();
        final List< AgentCoverage > coverageAreas = new ArrayList< AgentCoverage >();
        agentDetails.setCoverageArea( coverageAreas );
        final AgentCoverage ac = new AgentCoverage();
        final String zip = "testZip";
        ac.setZip( zip );
        coverageAreas.add( ac );
        when( agentDetailsRepository.findAgentByEmail( email ) ).thenReturn( agentDetails );
        final List< String > agentCoverageAreas = agentDetailsServiceImpl.getAgentCoverageAreas( email );
        assertEquals( zip, agentCoverageAreas.iterator().next() );
    }

    /**
     * Test get agent coverage areas should return nothing for empty coverage
     * area.
     */
    @Test
    public void testGetAgentCoverageAreasShouldReturnNothingForEmptyCoverageArea() {
        final String email = "test@test.com";
        final AgentDetails agentDetails = new AgentDetails();
        agentDetails.setCoverageArea( new ArrayList<>() );
        when( agentDetailsRepository.findAgentByEmail( email ) ).thenReturn( agentDetails );
        final List< String > agentCoverageAreas = agentDetailsServiceImpl.getAgentCoverageAreas( email );
        assertTrue( agentCoverageAreas.isEmpty() );
    }

    /**
     * Test get agent coverage areas should return nothing if agent not found.
     */
    @Test
    public void testGetAgentCoverageAreasShouldReturnNothingIfAgentNotFound() {
        final String email = "test@test.com";
        when( agentDetailsRepository.findAgentByEmail( email ) ).thenReturn( null );
        final List< String > agentCoverageAreas = agentDetailsServiceImpl.getAgentCoverageAreas( email );
        assertTrue( agentCoverageAreas.isEmpty() );
    }

    /**
     * Test find by user.
     */
    @Test
    public void testFindByUser() {
        final User user = new User();
        final AgentDetails expected = new AgentDetails();
        when( agentDetailsRepository.findByUser( user ) ).thenReturn( expected );
        final AgentDetails actual = agentDetailsServiceImpl.findByUser( user );
        verify( agentDetailsRepository ).findByUser( user );
        assertEquals( expected, actual );
    }

    /**
     * Test find agent email by listing id.
     */
    @Test
    public void testFindAgentEmailByListingIdWithoutException() {
        Mockito.when( opportunityV1Repository.findAgentEmailByListingId( "test" ) ).thenReturn( "test" );
        agentDetailsServiceImpl.findAgentEmailByListingId( "test" );
        verify( opportunityV1Repository ).findAgentEmailByListingId( "test" );
    }

    /**
     * Test find agent email by listing id.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testFindAgentEmailByListingIdWithException() {
        Mockito.when( opportunityV1Repository.findAgentEmailByListingId( "test" ) ).thenReturn( null );
        agentDetailsServiceImpl.findAgentEmailByListingId( "test" );
    }

    /**
     * Test find agent by email.
     */
    @Test
    public void testFindAgentByEmail() {
        final AgentDetails ad = new AgentDetails();
        Mockito.when( agentDetailsRepository.findAgentByEmail( "test" ) ).thenReturn( ad );
        final AgentDetails ex = agentDetailsServiceImpl.findAgentByEmail( "test" );
        verify( agentDetailsRepository ).findAgentByEmail( "test" );
        assertEquals( ad, ex );
    }

    /**
     * Test get agents from users.
     */
    @Test
    public void testGetAgentsFromUsers() {
        final List< User > userIds = new ArrayList<>();
        final List< AgentDetails > agents = new ArrayList<>();
        when( agentDetailsRepository.getAgentByUsers( userIds ) ).thenReturn( agents );
        final List< AgentDetails > agentsList = agentDetailsServiceImpl.getAgents( userIds );
        assertEquals( agents, agentsList );
        verify( agentDetailsRepository ).getAgentByUsers( userIds );
    }

    /**
     * Test find all.
     */
    @Test
    public void testFindAll() {
        final List< AgentDetails > expectedAgents = new ArrayList<>();
        when( agentDetailsRepository.findAll() ).thenReturn( expectedAgents );
        final List< AgentDetails > actualAgents = agentDetailsServiceImpl.findAll();
        assertEquals( actualAgents, expectedAgents );
        verify( agentDetailsRepository ).findAll();
    }

    /**
     * Test find by emails in.
     */
    @Test
    public void testFindByEmailsIn() {
        final Collection< String > emails = new ArrayList<>();
        final List< AgentDetails > expectedAgents = new ArrayList<>();
        when( agentDetailsRepository.findByEmailsIn( emails ) ).thenReturn( expectedAgents );
        final List< AgentDetails > actualAgents = agentDetailsServiceImpl.findByEmailsIn( emails );
        assertEquals( actualAgents, expectedAgents );
        verify( agentDetailsRepository ).findByEmailsIn( emails );
    }
}
