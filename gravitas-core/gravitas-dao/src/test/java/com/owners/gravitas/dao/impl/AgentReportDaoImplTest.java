package com.owners.gravitas.dao.impl;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.AgentOpportunityBusinessConfig;

/**
 * The Class AgentReportDaoImplTest.
 *
 * @author raviz
 */
public class AgentReportDaoImplTest extends AbstractBaseMockitoTest {

	/** The agent report dao impl. */
	@InjectMocks
	private AgentReportDaoImpl agentReportDaoImpl;

	/** The entity manager factory. */
	@Mock
	private EntityManagerFactory entityManagerFactory;
	
	@Mock
    private AgentOpportunityBusinessConfig agentOpportunityBusinessConfig;

	/**
	 * Test get agents stagewise cta.
	 */
	@Test
	public void testGetAgentsStagewiseCta() {
		final Date startDate = new Date(1L);
		final Date endDate = new Date(1L);
		final String agentEmail = "agentEmail";
		final Query query = mock(Query.class);
		final EntityManager entityManager = mock(EntityManager.class);
		@SuppressWarnings("rawtypes")
		final List expectedList = new ArrayList<>();
		when(query.getResultList()).thenReturn(expectedList);
		when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
		when(entityManagerFactory.createEntityManager().createNativeQuery(anyString())).thenReturn(query);
		final List<Object[]> agentsStagewiseCta = agentReportDaoImpl.getAgentsStagewiseCta(startDate, endDate,
				agentEmail);
		assertEquals(agentsStagewiseCta, expectedList);
	}

	/**
	 * Test get opportunities stage details by date range.
	 */
	@Test
	public void testGetOpportunitiesStageDetailsByDateRange() {
		final Date startDate = new Date(1L);
		final Date endDate = new Date(1L);
		final String agentEmail = "agentEmail";
		final Query query = mock(Query.class);
		final EntityManager entityManager = mock(EntityManager.class);
		@SuppressWarnings("rawtypes")
		final List expectedList = new ArrayList<>();
		when(query.getResultList()).thenReturn(expectedList);
		when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
		when(entityManagerFactory.createEntityManager().createNativeQuery(anyString())).thenReturn(query);
		final List<Object[]> agentsStagewiseCta = agentReportDaoImpl.getOpportunitiesStageDetailsByDateRange(agentEmail,
				startDate, endDate);
		assertEquals(agentsStagewiseCta, expectedList);
	}

	/**
	 * Test get opportunities first response time.
	 */
	@Test
	public void testGetOpportunitiesFirstResponseTime() {
		final Date startDate = new Date(1L);
		final Date endDate = new Date(1L);
		final String agentEmail = "agentEmail";
		final Query query = mock(Query.class);
		final EntityManager entityManager = mock(EntityManager.class);
		@SuppressWarnings("rawtypes")
		final List expectedList = new ArrayList<>();
		when(query.getResultList()).thenReturn(expectedList);
		when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
		when(entityManagerFactory.createEntityManager().createNativeQuery(anyString())).thenReturn(query);
		final List<Object[]> agentsStagewiseCta = agentReportDaoImpl.getOpportunitiesFirstResponseTime(agentEmail,
				startDate, endDate);
		assertEquals(agentsStagewiseCta, expectedList);
	}

	/**
	 * Testget closed lost opportunity count.
	 */
	@Test
	public void testgetClosedLostOpportunityCount() {
		final Date startDate = new Date(1L);
		final Date endDate = new Date(1L);
		final String agentEmail = "agentEmail";
		final Query query = mock(Query.class);
		final EntityManager entityManager = mock(EntityManager.class);
		when(query.getSingleResult()).thenReturn(new Integer(1));
		when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
		when(entityManagerFactory.createEntityManager().createNativeQuery(anyString())).thenReturn(query);
		final Object count = agentReportDaoImpl.getClosedLostOpportunityCount(startDate, endDate, agentEmail,
				new ArrayList<>());
		assertEquals(count, 1);
	}

	/**
	 * Testget assigned opportunity count.
	 */
	@Test
	public void testgetAssignedOpportunityCount() {
		final Date startDate = new Date(1L);
		final Date endDate = new Date(1L);
		final String agentEmail = "agentEmail";
		final Query query = mock(Query.class);
		final EntityManager entityManager = mock(EntityManager.class);
		when(query.getSingleResult()).thenReturn(new Integer(1));
		when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
		when(entityManagerFactory.createEntityManager().createNativeQuery(anyString())).thenReturn(query);
		final Object count = agentReportDaoImpl.getAssignedOpportunityCount(startDate, endDate, agentEmail,
				new ArrayList<>());
		assertEquals(count, 1);
	}

	/**
	 * Testget opportunity count by stage.
	 */
	@Test
	public void testgetOpportunityCountByStage() {
		final Date startDate = new Date(1L);
		final Date endDate = new Date(1L);
		final String agentEmail = "agentEmail";
		final Query query = mock(Query.class);
		final EntityManager entityManager = mock(EntityManager.class);
		when(query.getSingleResult()).thenReturn(new Integer(1));
		when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
		when(entityManagerFactory.createEntityManager().createNativeQuery(anyString())).thenReturn(query);
		final Object count = agentReportDaoImpl.getOpportunityCountByStage(startDate, endDate, agentEmail,
				new ArrayList<>(), new ArrayList<>());
		assertEquals(count, 1);
	}

	/**
	 * Test get Managing Broker statistics
	 */
	@Test
	public void testGetManagingBrokerStatistics() {

		final String email = "managingBroker@owners.com";
		final Query query = mock(Query.class);
		final EntityManager entityManager = mock(EntityManager.class);

		@SuppressWarnings("rawtypes")
		final List expectedList = new ArrayList<>();
		when(query.getResultList()).thenReturn(expectedList);
		when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
		when(entityManagerFactory.createEntityManager().createNativeQuery(anyString())).thenReturn(query);
		final List<Object[]> objList = agentReportDaoImpl.getManagingBrokerStatistics(email);
		assertEquals(objList, expectedList);
	}

	/**
	 * Test get Managing Broker statistics for Grid
	 */
	@Test
	public void testGetManagingBrokerGridStatistics() {

		final String email = "managingBroker@owners.com";
		final Query query = mock(Query.class);
		final EntityManager entityManager = mock(EntityManager.class);

		@SuppressWarnings("rawtypes")
		final List expectedList = new ArrayList<>();
		when(query.getResultList()).thenReturn(expectedList);
		when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
		when(agentOpportunityBusinessConfig.getFaceToFaceopportunityRange()).thenReturn(7);
		when(entityManagerFactory.createEntityManager().createNativeQuery(anyString())).thenReturn(query);
		final List<Object[]> objList = agentReportDaoImpl.getManagingBrokerGridStatistics(email);
		assertEquals(objList, expectedList);
	}

	/**
	 * Test get Agent statistics
	 */
	@Test
	public void testGetAgentStatistics() {

		final String email = "agent@owners.com";
		final Query query = mock(Query.class);
		final EntityManager entityManager = mock(EntityManager.class);

		@SuppressWarnings("rawtypes")
		final List expectedList = new ArrayList<>();
		when(query.getResultList()).thenReturn(expectedList);
		when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
		when(entityManagerFactory.createEntityManager().createNativeQuery(anyString())).thenReturn(query);
		final List<Object[]> objList = agentReportDaoImpl.getAgentStatistics(email);
		assertEquals(objList, expectedList);
	}

	/**
	 * Test get Agent statistics for Grid
	 */
	@Test
	public void testGetAgentGridStatistics() {

		final String email = "agent@owners.com";
		final Query query = mock(Query.class);
		final EntityManager entityManager = mock(EntityManager.class);

		@SuppressWarnings("rawtypes")
		final List expectedList = new ArrayList<>();
		when(query.getResultList()).thenReturn(expectedList);
		when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
		when(agentOpportunityBusinessConfig.getFaceToFaceopportunityRange()).thenReturn(7);
		when(entityManagerFactory.createEntityManager().createNativeQuery(anyString())).thenReturn(query);
		final List<Object[]> objList = agentReportDaoImpl.getAgentGridStatistics(email);
		assertEquals(objList, expectedList);
	}

	/**
	 * Test get Face To Face statistics for Agent
	 */
	@Test
	public void testGetFaceToFaceStatisticsForAgent() {

		final String email = "agent@owners.com";
		final Query query = mock(Query.class);
		final EntityManager entityManager = mock(EntityManager.class);

		@SuppressWarnings("rawtypes")
		final List expectedList = new ArrayList<>();
		when(query.getResultList()).thenReturn(expectedList);
		when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
		when(entityManagerFactory.createEntityManager().createNativeQuery(anyString())).thenReturn(query);
		final List<Object[]> objList = agentReportDaoImpl.getFaceToFaceStatistics(email, true);
		assertEquals(objList, expectedList);
	}

	/**
	 * Test get Face To Face statistics for Managing Broker
	 */
	@Test
	public void testGetFaceToFaceStatisticsForManagingBroker() {

		final String email = "agent@owners.com";
		final Query query = mock(Query.class);
		final EntityManager entityManager = mock(EntityManager.class);

		@SuppressWarnings("rawtypes")
		final List expectedList = new ArrayList<>();
		when(query.getResultList()).thenReturn(expectedList);
		when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
		when(entityManagerFactory.createEntityManager().createNativeQuery(anyString())).thenReturn(query);
		final List<Object[]> objList = agentReportDaoImpl.getFaceToFaceStatistics(email, false);
		assertEquals(objList, expectedList);
	}

	/**
	 * Test Download Face To Face Count for Managing Broker
	 */
	@Test
	public void testDownloadFaceToFaceCount() {

		final String email = "agent@owners.com";
		final Query query = mock(Query.class);
		final EntityManager entityManager = mock(EntityManager.class);

		@SuppressWarnings("rawtypes")
		final List expectedList = new ArrayList<>();
		when(query.getResultList()).thenReturn(expectedList);
		when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
		when(entityManagerFactory.createEntityManager().createNativeQuery(anyString())).thenReturn(query);
		final List<Object[]> objList = agentReportDaoImpl.downloadFaceToFaceCount(email);
		assertEquals(objList, expectedList);
	}

	/**
	 * Test get Face To Face statistics for Agent for Grid
	 */
	@Test
	public void testGetFaceToFaceStatisticsForAgentForGrid() {

		final String email = "agent@owners.com";
		final Query query = mock(Query.class);
		final EntityManager entityManager = mock(EntityManager.class);

		@SuppressWarnings("rawtypes")
		final List expectedList = new ArrayList<>();
		when(query.getResultList()).thenReturn(expectedList);
		when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
		when(agentOpportunityBusinessConfig.getFaceToFaceopportunityRange()).thenReturn(7);
		when(entityManagerFactory.createEntityManager().createNativeQuery(anyString())).thenReturn(query);
		final List<Object[]> objList = agentReportDaoImpl.getFaceToFaceGridStatistics(email, true);
		assertEquals(objList, expectedList);
	}

	/**
	 * Test get Face To Face statistics for Managing Broker For Grid
	 */
	@Test
	public void testGetFaceToFaceStatisticsForManagingBrokerForGrid() {

		final String email = "agent@owners.com";
		final Query query = mock(Query.class);
		final EntityManager entityManager = mock(EntityManager.class);

		@SuppressWarnings("rawtypes")
		final List expectedList = new ArrayList<>();
		when(query.getResultList()).thenReturn(expectedList);
		when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
		when(agentOpportunityBusinessConfig.getFaceToFaceopportunityRange()).thenReturn(7);
		when(entityManagerFactory.createEntityManager().createNativeQuery(anyString())).thenReturn(query);
		final List<Object[]> objList = agentReportDaoImpl.getFaceToFaceGridStatistics(email, false);
		assertEquals(objList, expectedList);
	}
}
