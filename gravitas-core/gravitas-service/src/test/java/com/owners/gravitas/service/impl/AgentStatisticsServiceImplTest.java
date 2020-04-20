package com.owners.gravitas.service.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.AgentStatistics;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.repository.AgentStatisticsRepository;

/**
 * @author madhavk
 *
 */
public class AgentStatisticsServiceImplTest extends AbstractBaseMockitoTest {

	/** The agent analytics service impl. */
	@InjectMocks
	private AgentStatisticsServiceImpl agentAnalyticsServiceImpl;

	/** The agent analytics repository. */
	@Mock
    private AgentStatisticsRepository agentAnalyticsRepository;

	/**
	 * Test get agent score analytics.
	 */
	@Test
	public void testGetAgentScoreAnalytics() {

	    User user = new User ();
	    user.setId( "test" );
	    AgentDetails agentDetails = new AgentDetails();
        agentDetails.setUser( user );
		AgentStatistics agentAnalytics = new AgentStatistics();
		agentAnalytics.setId( "id" );
		agentAnalytics.setAgentDetails( agentDetails );
		agentAnalytics.setKey( "score" );
		agentAnalytics.setValue( "5" );
		agentAnalytics.setCreatedBy( "test" );
		agentAnalytics.setCreatedDate( new DateTime() );

		List< AgentStatistics > agentList = new ArrayList<AgentStatistics>();
		agentList.add(agentAnalytics);

		when( agentAnalyticsRepository.findAgentStatistics( "test@test.com" ) ).thenReturn( agentList );

		List <AgentStatistics> list = agentAnalyticsServiceImpl.getAgentScoreStatistics( "test@test.com" );

		assertNotNull( list );
		verify( agentAnalyticsRepository ).findAgentStatistics( "test@test.com" );

	}

	/**
	 * Test save agent analytics list.
	 */
	@Test
	public void testSaveAgentAnalyticsList() {
	    User user = new User ();
        user.setId( "test" );
        AgentDetails agentDetails = new AgentDetails();
        agentDetails.setUser( user );
        AgentStatistics agentAnalytics = new AgentStatistics();
        agentAnalytics.setId( "id" );
        agentAnalytics.setAgentDetails( agentDetails );
		agentAnalytics.setKey( "key" );
		agentAnalytics.setValue( "5" );
		agentAnalytics.setCreatedBy( "test" );
		agentAnalytics.setCreatedDate( new DateTime() );

		List< AgentStatistics > agentList = new ArrayList<AgentStatistics>();
		agentList.add(agentAnalytics);

		when( agentAnalyticsRepository.save( agentList )).thenReturn( agentList );
		agentAnalyticsServiceImpl.save(agentList);

		verify( agentAnalyticsRepository ).save( agentList );
	}

	/**
	 * Test save agent analytics entity.
	 */
	@Test
	public void testSaveAgentAnalyticsEntity() {
	    User user = new User ();
        user.setId( "test" );
        AgentDetails agentDetails = new AgentDetails();
        agentDetails.setUser( user );
        AgentStatistics agentAnalytics = new AgentStatistics();
        agentAnalytics.setId( "id" );
        agentAnalytics.setAgentDetails( agentDetails );
		agentAnalytics.setKey( "key" );
		agentAnalytics.setValue( "5" );
		agentAnalytics.setCreatedBy( "test" );
		agentAnalytics.setCreatedDate( new DateTime() );

		when( agentAnalyticsRepository.save( agentAnalytics )).thenReturn( agentAnalytics );
		agentAnalyticsServiceImpl.save( agentAnalytics );

		verify( agentAnalyticsRepository ).save( agentAnalytics );
	}

}
