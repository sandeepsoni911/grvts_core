package com.owners.gravitas.business.impl;

import static com.owners.gravitas.config.HappyAgentsConfig.BEST_FIT_AGENTS;
import static com.owners.gravitas.config.HappyAgentsConfig.IS_HAPPY_AGENT_ENABLED;
import static com.owners.gravitas.config.HappyAgentsConfig.NO_AGENT_AVAILABLE_SUFFIX;
import static com.owners.gravitas.config.HungryAgentsConfig.IS_HUNGRY_AGENT_ENABLED;
import static com.owners.gravitas.enums.AgentType.AVERAGE;
import static com.owners.gravitas.enums.AgentType.GOOD;
import static com.owners.gravitas.enums.AgentType.NEW;
import static com.owners.gravitas.enums.AssignmentStatus.considered;
import static com.owners.gravitas.enums.AssignmentStatus.referred;
import static com.owners.gravitas.enums.HungryAgentsStatus.HUNGRY_CONSIDERED;
import static com.owners.gravitas.enums.RecordType.BUYER;
import static java.lang.Boolean.FALSE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.commons.collections.CollectionUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.GroupManagementBusinessService;
import com.owners.gravitas.business.OpportunityBusinessService;
import com.owners.gravitas.business.builder.AgentDtoBuilder;
import com.owners.gravitas.business.builder.EligibleAgentBuilder;
import com.owners.gravitas.business.task.AgentLookupTask;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.HappyAgentsConfig;
import com.owners.gravitas.config.HungryAgentsConfig;
import com.owners.gravitas.config.SCurveConfig;
import com.owners.gravitas.domain.entity.AgentAssignmentLog;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.CbsaMarketLevel;
import com.owners.gravitas.domain.entity.Group;
import com.owners.gravitas.domain.entity.OwnersMarketCbsaLabel;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.domain.entity.UserGroup;
import com.owners.gravitas.dto.Agent;
import com.owners.gravitas.dto.AgentAssignmentLogDto;
import com.owners.gravitas.dto.agentassgn.EligibleAgent;
import com.owners.gravitas.dto.crm.response.CRMResponse;
import com.owners.gravitas.enums.AssignmentStatus;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.enums.SCurveStatus;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.exception.OpportunityNotAssignedException;
import com.owners.gravitas.service.AgentAssignmentLogService;
import com.owners.gravitas.service.AgentDetailsService;
import com.owners.gravitas.service.AgentLookupService;
import com.owners.gravitas.service.AgentOpportunityService;
import com.owners.gravitas.service.AgentService;
import com.owners.gravitas.service.CbsaMarketLevelService;
import com.owners.gravitas.service.GenericDbService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.OwnersMarketCbsaLabelService;
import com.owners.gravitas.service.UserGroupService;

/**
 * The Class AgentLookupBusinessServiceImplTest.
 *
 * @author ankusht
 */
public class AgentLookupBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent lookup business service impl. */
    @InjectMocks
    private AgentLookupBusinessServiceImpl agentLookupBusinessServiceImpl;

    /** The agent service. */
    @Mock
    private AgentService agentService;

    /** The opportunity business service. */
    @Mock
    private OpportunityService opportunityService;

    /** The agent lookup task. */
    @Mock
    private AgentLookupTask agentLookupTask;

    /** The happy agents config. */
    @Mock
    private HappyAgentsConfig happyAgentsConfig;

    /** The owners market cbsa label service. */
    @Mock
    private OwnersMarketCbsaLabelService ownersMarketCbsaLabelService;

    /** The cbsa market level service. */
    @Mock
    private CbsaMarketLevelService cbsaMarketLevelService;

    /** The opportunity business service. */
    @Mock
    private OpportunityBusinessService opportunityBusinessService;

    /** The opportunity source. */
    @Mock
    private OpportunitySource opportunitySource;

    /** The agent assignment log service. */
    @Mock
    private AgentAssignmentLogService agentAssignmentLogService;

    /** The agent details service. */
    @Mock
    private AgentDetailsService agentDetailsService;

    /** The agent dto builder. */
    @Mock
    private AgentDtoBuilder agentDtoBuilder;

    /** The group management business service. */
    @Mock
    private GroupManagementBusinessService groupManagementBusinessService;

    /** The user group service. */
    @Mock
    private UserGroupService userGroupService;

    /** The hungry agents config. */
    @Mock
    private HungryAgentsConfig hungryAgentsConfig;

    /** The s curve config. */
    @Mock
    private SCurveConfig sCurveConfig;

    /** The agent opportunity service. */
    @Mock
    private AgentOpportunityService agentOpportunityService;

    /** The generic db service. */
    @Mock
    private GenericDbService genericDbService;

    /** The agent lookup service. */
    @Mock
    private AgentLookupService agentLookupService;

    /** The eligible agent builder. */
    @Mock
    private EligibleAgentBuilder eligibleAgentBuilder;

    /**
     * Gets the future.
     *
     * @param log
     *            the log
     * @param minOpps
     *            the min opps
     * @return the future
     */
    private Future< AgentAssignmentLog > getFuture( final AgentAssignmentLog log, final int minOpps ) {
        log.setNumberOfOppsInThresholdPeriod( minOpps );
        final Future< AgentAssignmentLog > future = new AsyncResult<>( log );
        return future;
    }

    /**
     * Test get most eligible agent should return good agent having less than
     * min opps.
     *
     * @param response
     *            the response
     * @param cbsaMarketLevels
     *            the cbsa market levels
     */
    @Test( dataProvider = "getBuyerResponse" )
    public void testGetMostEligibleAgent_shouldReturnGoodAgent_havingLessThanMinOpps( final CRMResponse response,
            final List< CbsaMarketLevel > cbsaMarketLevels ) {
        final boolean toAutoAssign = Boolean.FALSE;
        final String zipCode = "10001";
        final String crmOpportunityId = "100";
        final String cbsa = cbsaMarketLevels.iterator().next().getOwnersMarketCbsaLabel().getId();
        final String state = "GL";

        final List< AgentAssignmentLogDto > dtoList = new ArrayList<>();
        final List< String > allAgentEmails = Arrays.asList( new String[] { "agent1@ownerstest.com",
                "agent2@ownerstest.com", "agent3@ownerstest.com", "agent4@ownerstest.com", "agent5@ownerstest.com" } );
        final AgentAssignmentLogDto dto1 = new AgentAssignmentLogDto( allAgentEmails.get( 0 ), 77, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto2 = new AgentAssignmentLogDto( allAgentEmails.get( 1 ), 61, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto3 = new AgentAssignmentLogDto( allAgentEmails.get( 2 ), 34, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto4 = new AgentAssignmentLogDto( allAgentEmails.get( 3 ), 29, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto5 = new AgentAssignmentLogDto( allAgentEmails.get( 4 ), 100, zipCode, cbsa,
                "texas" );
        dtoList.add( dto1 );
        dtoList.add( dto2 );
        dtoList.add( dto3 );
        dtoList.add( dto4 );
        dtoList.add( dto5 );

        final AgentAssignmentLog log1 = getAgentAssignmentLog( dto1 );
        final Future< AgentAssignmentLog > future1 = getFuture( log1, 0 );
        final AgentAssignmentLog log2 = getAgentAssignmentLog( dto2 );
        final Future< AgentAssignmentLog > future2 = getFuture( log2, 0 );
        final AgentAssignmentLog log3 = getAgentAssignmentLog( dto3 );
        final Future< AgentAssignmentLog > future3 = getFuture( log3, 0 );
        final AgentAssignmentLog log4 = getAgentAssignmentLog( dto4 );
        final Future< AgentAssignmentLog > future4 = getFuture( log4, 0 );
        final AgentAssignmentLog log5 = getAgentAssignmentLog( dto5 );
        final Future< AgentAssignmentLog > future5 = getFuture( log5, 0 );

        final List< AgentDetails > agentDetailList = new ArrayList<>();
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 0 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 1 ), "hold", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 2 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 3 ), "onboarding", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 4 ), "active", false ) );

        final Agent expectedAgent = new Agent();
        expectedAgent.setEmail( allAgentEmails.get( 0 ) );

        when( opportunitySource.getCrmId() ).thenReturn( crmOpportunityId );
        when( opportunitySource.getRecordType() ).thenReturn( BUYER.getType() );
        when( agentAssignmentLogService.findByPropertyZip( zipCode ) ).thenReturn( dtoList );
        when( agentLookupTask.buildAssignmentLog( dto1, crmOpportunityId, false ) ).thenReturn( future1 );
        when( agentLookupTask.buildAssignmentLog( dto2, crmOpportunityId, false ) ).thenReturn( future2 );
        when( agentLookupTask.buildAssignmentLog( dto3, crmOpportunityId, false ) ).thenReturn( future3 );
        when( agentLookupTask.buildAssignmentLog( dto4, crmOpportunityId, false ) ).thenReturn( future4 );
        when( agentLookupTask.buildAssignmentLog( dto5, crmOpportunityId, false ) ).thenReturn( future5 );
        when( agentDetailsService.findByEmailsIn( anyList() ) ).thenReturn( agentDetailList );
        when( agentService.findAgentByCrmOpportunityId( crmOpportunityId ) ).thenReturn( null );
        when( happyAgentsConfig.getSelectedBuyerStages() ).thenReturn( "Showing Homes,Face To Face Meeting" );
        when( opportunityService.findAssignedAgentEmailsBySelectedStagesCount( anyCollection(), anyCollection() ) )
                .thenReturn( new ArrayList<>() );
        when( cbsaMarketLevelService.findByCbsaCodes( anyList() ) ).thenReturn( cbsaMarketLevels );
        when( agentDtoBuilder.convertTo( log1.getAgentEmail() ) ).thenReturn( expectedAgent );

        final Map< String, Object > map = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, zipCode, state );
        final Agent mostEligibleAgent = getAgentFromResponseMap( map );
        assertEquals( expectedAgent.getEmail(), mostEligibleAgent.getEmail() );

        verify( agentAssignmentLogService ).saveAll( anyList() );
    }

    /**
     * Test get most eligible agent should return average agent having less than
     * min opps.
     *
     * @param response
     *            the response
     * @param cbsaMarketLevels
     *            the cbsa market levels
     */
    @Test( dataProvider = "getBuyerResponse" )
    public void testGetMostEligibleAgent_shouldReturnAverageAgent_HavingLessThanMinOpps( final CRMResponse response,
            final List< CbsaMarketLevel > cbsaMarketLevels ) {
        final boolean toAutoAssign = Boolean.FALSE;
        final String zipCode = "10001";
        final String crmOpportunityId = "100";
        final String cbsa = cbsaMarketLevels.iterator().next().getOwnersMarketCbsaLabel().getId();
        final String state = "GL";

        final List< AgentAssignmentLogDto > dtoList = new ArrayList<>();
        final List< String > allAgentEmails = Arrays.asList( new String[] { "agent1@ownerstest.com",
                "agent2@ownerstest.com", "agent3@ownerstest.com", "agent4@ownerstest.com", "agent5@ownerstest.com" } );
        final AgentAssignmentLogDto dto1 = new AgentAssignmentLogDto( allAgentEmails.get( 0 ), 77, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto2 = new AgentAssignmentLogDto( allAgentEmails.get( 1 ), 61, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto3 = new AgentAssignmentLogDto( allAgentEmails.get( 2 ), 34, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto4 = new AgentAssignmentLogDto( allAgentEmails.get( 3 ), 29, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto5 = new AgentAssignmentLogDto( allAgentEmails.get( 4 ), 100, zipCode, cbsa,
                "texas" );
        dtoList.add( dto1 );
        dtoList.add( dto2 );
        dtoList.add( dto3 );
        dtoList.add( dto4 );
        dtoList.add( dto5 );

        final AgentAssignmentLog log1 = getAgentAssignmentLog( dto1 );
        final Future< AgentAssignmentLog > future1 = getFuture( log1, 4 );
        final AgentAssignmentLog log2 = getAgentAssignmentLog( dto2 );
        final Future< AgentAssignmentLog > future2 = getFuture( log2, 0 );
        final AgentAssignmentLog log3 = getAgentAssignmentLog( dto3 );
        final Future< AgentAssignmentLog > future3 = getFuture( log3, 0 );
        final AgentAssignmentLog log4 = getAgentAssignmentLog( dto4 );
        final Future< AgentAssignmentLog > future4 = getFuture( log4, 0 );
        final AgentAssignmentLog log5 = getAgentAssignmentLog( dto5 );
        final Future< AgentAssignmentLog > future5 = getFuture( log5, 0 );

        final List< AgentDetails > agentDetailList = new ArrayList<>();
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 0 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 1 ), "hold", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 2 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 3 ), "onboarding", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 4 ), "active", false ) );

        final Agent expectedAgent = new Agent();
        expectedAgent.setEmail( allAgentEmails.get( 2 ) );

        when( opportunitySource.getCrmId() ).thenReturn( crmOpportunityId );
        when( opportunitySource.getRecordType() ).thenReturn( RecordType.BUYER.getType() );
        when( agentAssignmentLogService.findByPropertyZip( zipCode ) ).thenReturn( dtoList );
        when( agentLookupTask.buildAssignmentLog( dto1, crmOpportunityId, false ) ).thenReturn( future1 );
        when( agentLookupTask.buildAssignmentLog( dto2, crmOpportunityId, false ) ).thenReturn( future2 );
        when( agentLookupTask.buildAssignmentLog( dto3, crmOpportunityId, false ) ).thenReturn( future3 );
        when( agentLookupTask.buildAssignmentLog( dto4, crmOpportunityId, false ) ).thenReturn( future4 );
        when( agentLookupTask.buildAssignmentLog( dto5, crmOpportunityId, false ) ).thenReturn( future5 );
        when( agentDetailsService.findByEmailsIn( anyList() ) ).thenReturn( agentDetailList );
        when( agentService.findAgentByCrmOpportunityId( crmOpportunityId ) ).thenReturn( null );
        when( happyAgentsConfig.getSelectedBuyerStages() ).thenReturn( "Showing Homes,Face To Face Meeting" );
        when( opportunityService.findAssignedAgentEmailsBySelectedStagesCount( anyCollection(), anyCollection() ) )
                .thenReturn( new ArrayList<>() );
        when( cbsaMarketLevelService.findByCbsaCodes( anyList() ) ).thenReturn( cbsaMarketLevels );
        when( agentDtoBuilder.convertTo( log3.getAgentEmail() ) ).thenReturn( expectedAgent );

        final Map< String, Object > map = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, zipCode, state );
        final Agent mostEligibleAgent = getAgentFromResponseMap( map );
        assertEquals( expectedAgent.getEmail(), mostEligibleAgent.getEmail() );

        verify( agentAssignmentLogService ).saveAll( anyList() );
    }

    /**
     * Test get most eligible agent should return null if no eligible agent Z
     * found.
     *
     * @param response
     *            the response
     * @param cbsaMarketLevels
     *            the cbsa market levels
     */
    @Test( dataProvider = "getBuyerResponse" )
    public void testGetMostEligibleAgent_shouldReturnNull_ifNoEligibleAgentZFound( final CRMResponse response,
            final List< CbsaMarketLevel > cbsaMarketLevels ) {
        final boolean toAutoAssign = Boolean.FALSE;
        final String zipCode = "10001";
        final String crmOpportunityId = "100";
        final String cbsa = cbsaMarketLevels.iterator().next().getOwnersMarketCbsaLabel().getId();
        final String state = "GL";

        final List< AgentAssignmentLogDto > dtoList = new ArrayList<>();
        final List< String > allAgentEmails = Arrays.asList( new String[] { "agent1@ownerstest.com",
                "agent2@ownerstest.com", "agent3@ownerstest.com", "agent4@ownerstest.com", "agent5@ownerstest.com" } );
        final AgentAssignmentLogDto dto1 = new AgentAssignmentLogDto( allAgentEmails.get( 0 ), 77, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto2 = new AgentAssignmentLogDto( allAgentEmails.get( 1 ), 61, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto3 = new AgentAssignmentLogDto( allAgentEmails.get( 2 ), 34, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto4 = new AgentAssignmentLogDto( allAgentEmails.get( 3 ), 29, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto5 = new AgentAssignmentLogDto( allAgentEmails.get( 4 ), 100, zipCode, cbsa,
                "texas" );
        dtoList.add( dto1 );
        dtoList.add( dto2 );
        dtoList.add( dto3 );
        dtoList.add( dto4 );
        dtoList.add( dto5 );

        final AgentAssignmentLog log1 = getAgentAssignmentLog( dto1 );
        final Future< AgentAssignmentLog > future1 = getFuture( log1, 40 );
        final AgentAssignmentLog log2 = getAgentAssignmentLog( dto2 );
        final Future< AgentAssignmentLog > future2 = getFuture( log2, 0 );
        final AgentAssignmentLog log3 = getAgentAssignmentLog( dto3 );
        final Future< AgentAssignmentLog > future3 = getFuture( log3, 0 );
        final AgentAssignmentLog log4 = getAgentAssignmentLog( dto4 );
        final Future< AgentAssignmentLog > future4 = getFuture( log4, 0 );
        final AgentAssignmentLog log5 = getAgentAssignmentLog( dto5 );
        final Future< AgentAssignmentLog > future5 = getFuture( log5, 0 );

        final List< AgentDetails > agentDetailList = new ArrayList<>();
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 0 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 1 ), "hold", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 2 ), "active", false ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 3 ), "onboarding", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 4 ), "active", false ) );

        final Agent expectedAgent = new Agent();
        expectedAgent.setEmail( allAgentEmails.get( 2 ) );

        when( opportunitySource.getCrmId() ).thenReturn( crmOpportunityId );
        when( opportunitySource.getRecordType() ).thenReturn( RecordType.BUYER.getType() );
        when( agentAssignmentLogService.findByPropertyZip( zipCode ) ).thenReturn( dtoList );
        when( agentLookupTask.buildAssignmentLog( dto1, crmOpportunityId, false ) ).thenReturn( future1 );
        when( agentLookupTask.buildAssignmentLog( dto2, crmOpportunityId, false ) ).thenReturn( future2 );
        when( agentLookupTask.buildAssignmentLog( dto3, crmOpportunityId, false ) ).thenReturn( future3 );
        when( agentLookupTask.buildAssignmentLog( dto4, crmOpportunityId, false ) ).thenReturn( future4 );
        when( agentLookupTask.buildAssignmentLog( dto5, crmOpportunityId, false ) ).thenReturn( future5 );
        when( agentDetailsService.findByEmailsIn( anyList() ) ).thenReturn( agentDetailList );
        when( agentService.findAgentByCrmOpportunityId( crmOpportunityId ) ).thenReturn( null );
        when( happyAgentsConfig.getSelectedBuyerStages() ).thenReturn( "Showing Homes,Face To Face Meeting" );
        when( opportunityService.findAssignedAgentEmailsBySelectedStagesCount( anyCollection(), anyCollection() ) )
                .thenReturn( new ArrayList<>() );
        when( cbsaMarketLevelService.findByCbsaCodes( anyList() ) ).thenReturn( cbsaMarketLevels );

        ReflectionTestUtils.setField( agentLookupBusinessServiceImpl, "allAgentsBusyMessage", "allAgentsBusyMessage" );
        final Map< String, Object > map = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, zipCode, state );
        final Agent mostEligibleAgent = getAgentFromResponseMap( map );
        assertNull( mostEligibleAgent );

        verify( agentAssignmentLogService ).saveAll( anyList() );
    }

    /**
     * Test get most eligible agent should forward to referral exchg if no
     * eligible agent Z found and auto assign is enabled.
     *
     * @param response
     *            the response
     * @param cbsaMarketLevels
     *            the cbsa market levels
     */
    @Test( dataProvider = "getBuyerResponse" )
    public void testGetMostEligibleAgent_shouldForwardToReferralExchg_ifNoEligibleAgentZFoundAndAutoAssignIsEnabled(
            final CRMResponse response, final List< CbsaMarketLevel > cbsaMarketLevels ) {
        final boolean toAutoAssign = Boolean.TRUE;
        final String zipCode = "10001";
        final String crmOpportunityId = "100";
        final String cbsa = cbsaMarketLevels.iterator().next().getOwnersMarketCbsaLabel().getId();
        final String state = "GL";

        final List< AgentAssignmentLogDto > dtoList = new ArrayList<>();
        final List< String > allAgentEmails = Arrays.asList( new String[] { "agent1@ownerstest.com",
                "agent2@ownerstest.com", "agent3@ownerstest.com", "agent4@ownerstest.com", "agent5@ownerstest.com" } );
        final AgentAssignmentLogDto dto1 = new AgentAssignmentLogDto( allAgentEmails.get( 0 ), 77, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto2 = new AgentAssignmentLogDto( allAgentEmails.get( 1 ), 61, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto3 = new AgentAssignmentLogDto( allAgentEmails.get( 2 ), 34, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto4 = new AgentAssignmentLogDto( allAgentEmails.get( 3 ), 29, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto5 = new AgentAssignmentLogDto( allAgentEmails.get( 4 ), 100, zipCode, cbsa,
                "texas" );
        dtoList.add( dto1 );
        dtoList.add( dto2 );
        dtoList.add( dto3 );
        dtoList.add( dto4 );
        dtoList.add( dto5 );

        final AgentAssignmentLog log1 = getAgentAssignmentLog( dto1 );
        final Future< AgentAssignmentLog > future1 = getFuture( log1, 40 );
        final AgentAssignmentLog log2 = getAgentAssignmentLog( dto2 );
        final Future< AgentAssignmentLog > future2 = getFuture( log2, 0 );
        final AgentAssignmentLog log3 = getAgentAssignmentLog( dto3 );
        final Future< AgentAssignmentLog > future3 = getFuture( log3, 0 );
        final AgentAssignmentLog log4 = getAgentAssignmentLog( dto4 );
        final Future< AgentAssignmentLog > future4 = getFuture( log4, 0 );
        final AgentAssignmentLog log5 = getAgentAssignmentLog( dto5 );
        final Future< AgentAssignmentLog > future5 = getFuture( log5, 0 );

        final List< AgentDetails > agentDetailList = new ArrayList<>();
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 0 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 1 ), "hold", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 2 ), "active", false ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 3 ), "onboarding", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 4 ), "active", false ) );

        final Agent expectedAgent = new Agent();
        expectedAgent.setEmail( allAgentEmails.get( 2 ) );

        when( opportunitySource.getCrmId() ).thenReturn( crmOpportunityId );
        when( opportunitySource.getRecordType() ).thenReturn( RecordType.BUYER.getType() );
        when( agentAssignmentLogService.findByPropertyZip( zipCode ) ).thenReturn( dtoList );
        when( agentLookupTask.buildAssignmentLog( dto1, crmOpportunityId, false ) ).thenReturn( future1 );
        when( agentLookupTask.buildAssignmentLog( dto2, crmOpportunityId, false ) ).thenReturn( future2 );
        when( agentLookupTask.buildAssignmentLog( dto3, crmOpportunityId, false ) ).thenReturn( future3 );
        when( agentLookupTask.buildAssignmentLog( dto4, crmOpportunityId, false ) ).thenReturn( future4 );
        when( agentLookupTask.buildAssignmentLog( dto5, crmOpportunityId, false ) ).thenReturn( future5 );
        when( agentDetailsService.findByEmailsIn( anyList() ) ).thenReturn( agentDetailList );
        when( agentService.findAgentByCrmOpportunityId( crmOpportunityId ) ).thenReturn( null );
        when( happyAgentsConfig.getSelectedBuyerStages() ).thenReturn( "Showing Homes,Face To Face Meeting" );
        when( opportunityService.findAssignedAgentEmailsBySelectedStagesCount( anyCollection(), anyCollection() ) )
                .thenReturn( new ArrayList<>() );
        when( cbsaMarketLevelService.findByCbsaCodes( anyList() ) ).thenReturn( cbsaMarketLevels );

        ReflectionTestUtils.setField( agentLookupBusinessServiceImpl, "allAgentsBusyMessage", "allAgentsBusyMessage" );
        final Map< String, Object > map = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, zipCode, state );
        final Agent mostEligibleAgent = getAgentFromResponseMap( map );
        assertNull( mostEligibleAgent );

        verify( agentAssignmentLogService ).saveAll( anyList() );
        verify( agentAssignmentLogService ).save( any( AgentAssignmentLog.class ) );
        verify( opportunityBusinessService ).forwardToReferralExchange( opportunitySource );
    }

    /**
     * Test get most eligible agent should auto assign and return null.
     *
     * @param response
     *            the response
     * @param cbsaMarketLevels
     *            the cbsa market levels
     */
    @Test( dataProvider = "getBuyerResponse" )
    public void testGetMostEligibleAgent_shouldAutoAssign_andReturnNull( final CRMResponse response,
            final List< CbsaMarketLevel > cbsaMarketLevels ) {
        final boolean toAutoAssign = Boolean.TRUE;
        final String zipCode = "10001";
        final String crmOpportunityId = "100";
        final String cbsa = cbsaMarketLevels.iterator().next().getOwnersMarketCbsaLabel().getId();
        final String state = "GL";

        final List< AgentAssignmentLogDto > dtoList = new ArrayList<>();
        final List< String > allAgentEmails = Arrays.asList( new String[] { "agent1@ownerstest.com",
                "agent2@ownerstest.com", "agent3@ownerstest.com", "agent4@ownerstest.com", "agent5@ownerstest.com" } );
        final AgentAssignmentLogDto dto1 = new AgentAssignmentLogDto( allAgentEmails.get( 0 ), 77, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto2 = new AgentAssignmentLogDto( allAgentEmails.get( 1 ), 61, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto3 = new AgentAssignmentLogDto( allAgentEmails.get( 2 ), 34, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto4 = new AgentAssignmentLogDto( allAgentEmails.get( 3 ), 29, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto5 = new AgentAssignmentLogDto( allAgentEmails.get( 4 ), 100, zipCode, cbsa,
                "texas" );
        dtoList.add( dto1 );
        dtoList.add( dto2 );
        dtoList.add( dto3 );
        dtoList.add( dto4 );
        dtoList.add( dto5 );

        final AgentAssignmentLog log1 = getAgentAssignmentLog( dto1 );
        final Future< AgentAssignmentLog > future1 = getFuture( log1, 0 );
        final AgentAssignmentLog log2 = getAgentAssignmentLog( dto2 );
        final Future< AgentAssignmentLog > future2 = getFuture( log2, 0 );
        final AgentAssignmentLog log3 = getAgentAssignmentLog( dto3 );
        final Future< AgentAssignmentLog > future3 = getFuture( log3, 0 );
        final AgentAssignmentLog log4 = getAgentAssignmentLog( dto4 );
        final Future< AgentAssignmentLog > future4 = getFuture( log4, 0 );
        final AgentAssignmentLog log5 = getAgentAssignmentLog( dto5 );
        final Future< AgentAssignmentLog > future5 = getFuture( log5, 0 );

        final List< AgentDetails > agentDetailList = new ArrayList<>();
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 0 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 1 ), "hold", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 2 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 3 ), "onboarding", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 4 ), "active", false ) );

        when( opportunitySource.getCrmId() ).thenReturn( crmOpportunityId );
        when( opportunitySource.getRecordType() ).thenReturn( RecordType.BUYER.getType() );
        when( agentAssignmentLogService.findByPropertyZip( zipCode ) ).thenReturn( dtoList );
        when( agentLookupTask.buildAssignmentLog( dto1, crmOpportunityId, false ) ).thenReturn( future1 );
        when( agentLookupTask.buildAssignmentLog( dto2, crmOpportunityId, false ) ).thenReturn( future2 );
        when( agentLookupTask.buildAssignmentLog( dto3, crmOpportunityId, false ) ).thenReturn( future3 );
        when( agentLookupTask.buildAssignmentLog( dto4, crmOpportunityId, false ) ).thenReturn( future4 );
        when( agentLookupTask.buildAssignmentLog( dto5, crmOpportunityId, false ) ).thenReturn( future5 );
        when( agentDetailsService.findByEmailsIn( anyList() ) ).thenReturn( agentDetailList );
        when( agentService.findAgentByCrmOpportunityId( crmOpportunityId ) ).thenReturn( null );
        when( happyAgentsConfig.getSelectedBuyerStages() ).thenReturn( "Showing Homes,Face To Face Meeting" );
        when( opportunityService.findAssignedAgentEmailsBySelectedStagesCount( anyCollection(), anyCollection() ) )
                .thenReturn( new ArrayList<>() );
        when( cbsaMarketLevelService.findByCbsaCodes( anyList() ) ).thenReturn( cbsaMarketLevels );

        ReflectionTestUtils.setField( agentLookupBusinessServiceImpl, "allAgentsBusyMessage", "allAgentsBusyMessage" );
        final Map< String, Object > map = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, zipCode, state );
        final Agent mostEligibleAgent = getAgentFromResponseMap( map );
        assertNull( mostEligibleAgent );
        verify( agentAssignmentLogService ).saveAll( anyList() );
        verify( opportunityBusinessService ).assignOpportunityToAgent( any( OpportunitySource.class ), anyString() );
    }

    /**
     * Test get most eligible agent should return good agent having less than
     * min opps and greater than RR.
     *
     * @param response
     *            the response
     * @param cbsaMarketLevels
     *            the cbsa market levels
     */
    @Test( dataProvider = "getBuyerResponse" )
    public void testGetMostEligibleAgent_shouldReturnGoodAgent_havingLessThanMinOpps_andGreaterThanRR(
            final CRMResponse response, final List< CbsaMarketLevel > cbsaMarketLevels ) {
        final boolean toAutoAssign = Boolean.FALSE;
        final String zipCode = "10001";
        final String crmOpportunityId = "100";
        final String cbsa = cbsaMarketLevels.iterator().next().getOwnersMarketCbsaLabel().getId();
        final String state = "GL";

        final List< AgentAssignmentLogDto > dtoList = new ArrayList<>();
        final List< String > allAgentEmails = Arrays.asList( new String[] { "agent1@ownerstest.com",
                "agent2@ownerstest.com", "agent3@ownerstest.com", "agent4@ownerstest.com", "agent5@ownerstest.com" } );
        final AgentAssignmentLogDto dto1 = new AgentAssignmentLogDto( allAgentEmails.get( 0 ), 77, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto2 = new AgentAssignmentLogDto( allAgentEmails.get( 1 ), 61, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto3 = new AgentAssignmentLogDto( allAgentEmails.get( 2 ), 34, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto4 = new AgentAssignmentLogDto( allAgentEmails.get( 3 ), 29, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto5 = new AgentAssignmentLogDto( allAgentEmails.get( 4 ), 100, zipCode, cbsa,
                "texas" );
        dtoList.add( dto1 );
        dtoList.add( dto2 );
        dtoList.add( dto3 );
        dtoList.add( dto4 );
        dtoList.add( dto5 );

        final List< AgentDetails > agentDetailList = new ArrayList<>();
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 0 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 1 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 2 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 3 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 4 ), "active", false ) );

        final AgentAssignmentLog log1 = getAgentAssignmentLog( dto1 );
        final Future< AgentAssignmentLog > future1 = getFuture( log1, 1 );
        final AgentAssignmentLog log2 = getAgentAssignmentLog( dto2 );
        final Future< AgentAssignmentLog > future2 = getFuture( log2, 4 );
        final AgentAssignmentLog log3 = getAgentAssignmentLog( dto3 );
        final Future< AgentAssignmentLog > future3 = getFuture( log3, 2 );
        final AgentAssignmentLog log4 = getAgentAssignmentLog( dto4 );
        final Future< AgentAssignmentLog > future4 = getFuture( log4, 2 );
        final AgentAssignmentLog log5 = getAgentAssignmentLog( dto5 );
        final Future< AgentAssignmentLog > future5 = getFuture( log5, 2 );

        final Agent expectedAgent = new Agent();
        expectedAgent.setEmail( allAgentEmails.get( 0 ) );

        when( opportunitySource.getCrmId() ).thenReturn( crmOpportunityId );
        when( opportunitySource.getRecordType() ).thenReturn( RecordType.BUYER.getType() );
        when( agentAssignmentLogService.findByPropertyZip( zipCode ) ).thenReturn( dtoList );
        when( agentLookupTask.buildAssignmentLog( dto1, crmOpportunityId, false ) ).thenReturn( future1 );
        when( agentLookupTask.buildAssignmentLog( dto2, crmOpportunityId, false ) ).thenReturn( future2 );
        when( agentLookupTask.buildAssignmentLog( dto3, crmOpportunityId, false ) ).thenReturn( future3 );
        when( agentLookupTask.buildAssignmentLog( dto4, crmOpportunityId, false ) ).thenReturn( future4 );
        when( agentLookupTask.buildAssignmentLog( dto5, crmOpportunityId, false ) ).thenReturn( future5 );
        when( agentDetailsService.findByEmailsIn( anyList() ) ).thenReturn( agentDetailList );
        when( agentService.findAgentByCrmOpportunityId( crmOpportunityId ) ).thenReturn( null );
        when( happyAgentsConfig.getSelectedBuyerStages() ).thenReturn( "Showing Homes,Face To Face Meeting" );
        when( opportunityService.findAssignedAgentEmailsBySelectedStagesCount( anyCollection(), anyCollection() ) )
                .thenReturn( new ArrayList<>() );
        when( cbsaMarketLevelService.findByCbsaCodes( anyList() ) ).thenReturn( cbsaMarketLevels );
        when( agentDtoBuilder.convertTo( log1.getAgentEmail() ) ).thenReturn( expectedAgent );

        final Map< String, Object > map = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, zipCode, state );
        final Agent mostEligibleAgent = getAgentFromResponseMap( map );
        assertEquals( expectedAgent.getEmail(), mostEligibleAgent.getEmail() );

        verify( agentAssignmentLogService ).saveAll( anyList() );
    }

    /**
     * Test get most eligible agent should return good agent having less than
     * level 2 max.
     *
     * @param response
     *            the response
     * @param cbsaMarketLevels
     *            the cbsa market levels
     */
    @Test( dataProvider = "getBuyerResponse" )
    public void testGetMostEligibleAgent_shouldReturnGoodAgent_havingLessThanLevel2Max( final CRMResponse response,
            final List< CbsaMarketLevel > cbsaMarketLevels ) {
        final boolean toAutoAssign = Boolean.FALSE;
        final String zipCode = "10001";
        final String crmOpportunityId = "100";
        final String cbsa = cbsaMarketLevels.iterator().next().getOwnersMarketCbsaLabel().getId();
        final String state = "GL";

        final List< AgentAssignmentLogDto > dtoList = new ArrayList<>();
        final List< String > allAgentEmails = Arrays.asList( new String[] { "agent1@ownerstest.com",
                "agent2@ownerstest.com", "agent3@ownerstest.com", "agent4@ownerstest.com", "agent5@ownerstest.com",
                "agent6@ownerstest.com", "agent7@ownerstest.com", "agent8@ownerstest.com", "agent9@ownerstest.com" } );
        final AgentAssignmentLogDto dto1 = new AgentAssignmentLogDto( allAgentEmails.get( 0 ), 77, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto2 = new AgentAssignmentLogDto( allAgentEmails.get( 1 ), 61, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto3 = new AgentAssignmentLogDto( allAgentEmails.get( 2 ), 34, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto4 = new AgentAssignmentLogDto( allAgentEmails.get( 3 ), 29, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto5 = new AgentAssignmentLogDto( allAgentEmails.get( 4 ), 100, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto6 = new AgentAssignmentLogDto( allAgentEmails.get( 5 ), 82, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto7 = new AgentAssignmentLogDto( allAgentEmails.get( 6 ), 67, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto8 = new AgentAssignmentLogDto( allAgentEmails.get( 7 ), 21, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto9 = new AgentAssignmentLogDto( allAgentEmails.get( 8 ), 100, zipCode, cbsa,
                "texas" );

        dtoList.add( dto1 );
        dtoList.add( dto2 );
        dtoList.add( dto3 );
        dtoList.add( dto4 );
        dtoList.add( dto5 );
        dtoList.add( dto6 );
        dtoList.add( dto7 );
        dtoList.add( dto8 );
        dtoList.add( dto9 );

        final List< AgentDetails > agentDetailList = new ArrayList<>();
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 0 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 1 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 2 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 3 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 4 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 5 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 6 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 7 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 8 ), "active", true ) );

        final AgentAssignmentLog log1 = getAgentAssignmentLog( dto1 );
        final Future< AgentAssignmentLog > future1 = getFuture( log1, 5 );
        final AgentAssignmentLog log2 = getAgentAssignmentLog( dto2 );
        final Future< AgentAssignmentLog > future2 = getFuture( log2, 4 );
        final AgentAssignmentLog log3 = getAgentAssignmentLog( dto3 );
        final Future< AgentAssignmentLog > future3 = getFuture( log3, 2 );
        final AgentAssignmentLog log4 = getAgentAssignmentLog( dto4 );
        final Future< AgentAssignmentLog > future4 = getFuture( log4, 4 );
        final AgentAssignmentLog log5 = getAgentAssignmentLog( dto5 );
        final Future< AgentAssignmentLog > future5 = getFuture( log5, 2 );
        final AgentAssignmentLog log6 = getAgentAssignmentLog( dto6 );
        final Future< AgentAssignmentLog > future6 = getFuture( log6, 4 );
        final AgentAssignmentLog log7 = getAgentAssignmentLog( dto7 );
        final Future< AgentAssignmentLog > future7 = getFuture( log7, 3 );
        final AgentAssignmentLog log8 = getAgentAssignmentLog( dto8 );
        final Future< AgentAssignmentLog > future8 = getFuture( log8, 2 );
        final AgentAssignmentLog log9 = getAgentAssignmentLog( dto9 );
        final Future< AgentAssignmentLog > future9 = getFuture( log9, 2 );

        final Agent expectedAgent = new Agent();
        expectedAgent.setEmail( allAgentEmails.get( 6 ) );

        when( opportunitySource.getCrmId() ).thenReturn( crmOpportunityId );
        when( opportunitySource.getRecordType() ).thenReturn( RecordType.BUYER.getType() );
        when( agentAssignmentLogService.findByPropertyZip( zipCode ) ).thenReturn( dtoList );
        when( agentLookupTask.buildAssignmentLog( dto1, crmOpportunityId, false ) ).thenReturn( future1 );
        when( agentLookupTask.buildAssignmentLog( dto2, crmOpportunityId, false ) ).thenReturn( future2 );
        when( agentLookupTask.buildAssignmentLog( dto3, crmOpportunityId, false ) ).thenReturn( future3 );
        when( agentLookupTask.buildAssignmentLog( dto4, crmOpportunityId, false ) ).thenReturn( future4 );
        when( agentLookupTask.buildAssignmentLog( dto5, crmOpportunityId, false ) ).thenReturn( future5 );
        when( agentLookupTask.buildAssignmentLog( dto6, crmOpportunityId, false ) ).thenReturn( future6 );
        when( agentLookupTask.buildAssignmentLog( dto7, crmOpportunityId, false ) ).thenReturn( future7 );
        when( agentLookupTask.buildAssignmentLog( dto8, crmOpportunityId, false ) ).thenReturn( future8 );
        when( agentLookupTask.buildAssignmentLog( dto9, crmOpportunityId, false ) ).thenReturn( future9 );

        when( agentDetailsService.findByEmailsIn( anyList() ) ).thenReturn( agentDetailList );
        when( agentService.findAgentByCrmOpportunityId( crmOpportunityId ) ).thenReturn( null );
        when( happyAgentsConfig.getSelectedBuyerStages() ).thenReturn( "Showing Homes,Face To Face Meeting" );
        when( opportunityService.findAssignedAgentEmailsBySelectedStagesCount( anyCollection(), anyCollection() ) )
                .thenReturn( new ArrayList<>() );
        when( cbsaMarketLevelService.findByCbsaCodes( anyList() ) ).thenReturn( cbsaMarketLevels );
        when( agentDtoBuilder.convertTo( log7.getAgentEmail() ) ).thenReturn( expectedAgent );

        final Map< String, Object > map = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, zipCode, state );
        final Agent mostEligibleAgent = getAgentFromResponseMap( map );
        assertEquals( expectedAgent.getEmail(), mostEligibleAgent.getEmail() );

        verify( agentAssignmentLogService ).saveAll( anyList() );
    }

    /**
     * Test get most eligible agent should return new agent having less than
     * level 1 max.
     *
     * @param response
     *            the response
     * @param cbsaMarketLevels
     *            the cbsa market levels
     */
    @Test( dataProvider = "getBuyerResponse" )
    public void testGetMostEligibleAgent_shouldReturnNewAgent_havingLessThanLevel1Max( final CRMResponse response,
            final List< CbsaMarketLevel > cbsaMarketLevels ) {
        final boolean toAutoAssign = Boolean.FALSE;
        final String zipCode = "10001";
        final String crmOpportunityId = "100";
        final String cbsa = cbsaMarketLevels.iterator().next().getOwnersMarketCbsaLabel().getId();
        final String state = "GL";

        final List< AgentAssignmentLogDto > dtoList = new ArrayList<>();
        final List< String > allAgentEmails = Arrays.asList( new String[] { "agent1@ownerstest.com",
                "agent2@ownerstest.com", "agent3@ownerstest.com", "agent4@ownerstest.com", "agent5@ownerstest.com",
                "agent6@ownerstest.com", "agent7@ownerstest.com", "agent8@ownerstest.com", "agent9@ownerstest.com" } );
        final AgentAssignmentLogDto dto1 = new AgentAssignmentLogDto( allAgentEmails.get( 0 ), 77, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto2 = new AgentAssignmentLogDto( allAgentEmails.get( 1 ), 61, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto3 = new AgentAssignmentLogDto( allAgentEmails.get( 2 ), 34, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto4 = new AgentAssignmentLogDto( allAgentEmails.get( 3 ), 29, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto5 = new AgentAssignmentLogDto( allAgentEmails.get( 4 ), 100, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto6 = new AgentAssignmentLogDto( allAgentEmails.get( 5 ), 82, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto7 = new AgentAssignmentLogDto( allAgentEmails.get( 6 ), 67, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto8 = new AgentAssignmentLogDto( allAgentEmails.get( 7 ), 21, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto9 = new AgentAssignmentLogDto( allAgentEmails.get( 8 ), 100, zipCode, cbsa,
                "texas" );

        dtoList.add( dto1 );
        dtoList.add( dto2 );
        dtoList.add( dto3 );
        dtoList.add( dto4 );
        dtoList.add( dto5 );
        dtoList.add( dto6 );
        dtoList.add( dto7 );
        dtoList.add( dto8 );
        dtoList.add( dto9 );

        final List< AgentDetails > agentDetailList = new ArrayList<>();
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 0 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 1 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 2 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 3 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 4 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 5 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 6 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 7 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 8 ), "active", true ) );

        final AgentAssignmentLog log1 = getAgentAssignmentLog( dto1 );
        final Future< AgentAssignmentLog > future1 = getFuture( log1, 5 );
        final AgentAssignmentLog log2 = getAgentAssignmentLog( dto2 );
        final Future< AgentAssignmentLog > future2 = getFuture( log2, 4 );
        final AgentAssignmentLog log3 = getAgentAssignmentLog( dto3 );
        final Future< AgentAssignmentLog > future3 = getFuture( log3, 2 );
        final AgentAssignmentLog log4 = getAgentAssignmentLog( dto4 );
        final Future< AgentAssignmentLog > future4 = getFuture( log4, 4 );
        final AgentAssignmentLog log5 = getAgentAssignmentLog( dto5 );
        final Future< AgentAssignmentLog > future5 = getFuture( log5, 2 );
        final AgentAssignmentLog log6 = getAgentAssignmentLog( dto6 );
        final Future< AgentAssignmentLog > future6 = getFuture( log6, 4 );
        final AgentAssignmentLog log7 = getAgentAssignmentLog( dto7 );
        final Future< AgentAssignmentLog > future7 = getFuture( log7, 6 );
        final AgentAssignmentLog log8 = getAgentAssignmentLog( dto8 );
        final Future< AgentAssignmentLog > future8 = getFuture( log8, 2 );
        final AgentAssignmentLog log9 = getAgentAssignmentLog( dto9 );
        final Future< AgentAssignmentLog > future9 = getFuture( log9, 2 );

        final Agent expectedAgent = new Agent();
        expectedAgent.setEmail( allAgentEmails.get( 4 ) );

        when( opportunitySource.getCrmId() ).thenReturn( crmOpportunityId );
        when( opportunitySource.getRecordType() ).thenReturn( RecordType.BUYER.getType() );
        when( agentAssignmentLogService.findByPropertyZip( zipCode ) ).thenReturn( dtoList );
        when( agentLookupTask.buildAssignmentLog( dto1, crmOpportunityId, false ) ).thenReturn( future1 );
        when( agentLookupTask.buildAssignmentLog( dto2, crmOpportunityId, false ) ).thenReturn( future2 );
        when( agentLookupTask.buildAssignmentLog( dto3, crmOpportunityId, false ) ).thenReturn( future3 );
        when( agentLookupTask.buildAssignmentLog( dto4, crmOpportunityId, false ) ).thenReturn( future4 );
        when( agentLookupTask.buildAssignmentLog( dto5, crmOpportunityId, false ) ).thenReturn( future5 );
        when( agentLookupTask.buildAssignmentLog( dto6, crmOpportunityId, false ) ).thenReturn( future6 );
        when( agentLookupTask.buildAssignmentLog( dto7, crmOpportunityId, false ) ).thenReturn( future7 );
        when( agentLookupTask.buildAssignmentLog( dto8, crmOpportunityId, false ) ).thenReturn( future8 );
        when( agentLookupTask.buildAssignmentLog( dto9, crmOpportunityId, false ) ).thenReturn( future9 );

        when( agentDetailsService.findByEmailsIn( anyList() ) ).thenReturn( agentDetailList );
        when( agentService.findAgentByCrmOpportunityId( crmOpportunityId ) ).thenReturn( null );
        when( happyAgentsConfig.getSelectedBuyerStages() ).thenReturn( "Showing Homes,Face To Face Meeting" );
        when( opportunityService.findAssignedAgentEmailsBySelectedStagesCount( anyCollection(), anyCollection() ) )
                .thenReturn( new ArrayList<>() );
        when( cbsaMarketLevelService.findByCbsaCodes( anyList() ) ).thenReturn( cbsaMarketLevels );
        when( agentDtoBuilder.convertTo( log5.getAgentEmail() ) ).thenReturn( expectedAgent );

        final Map< String, Object > map = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, zipCode, state );
        final Agent mostEligibleAgent = getAgentFromResponseMap( map );
        assertEquals( expectedAgent.getEmail(), mostEligibleAgent.getEmail() );

        verify( agentAssignmentLogService ).saveAll( anyList() );
    }

    /**
     * Test get most eligible agent should return new agent having less than
     * level 1 max.
     *
     * @param response
     *            the response
     * @param cbsaMarketLevels
     *            the cbsa market levels
     */
    @Test( dataProvider = "getBuyerResponse" )
    public void testGetMostEligibleAgent_shouldReturnNewAgent_havingLessThanLevel1Max_whileCheckingRecency(
            final CRMResponse response, final List< CbsaMarketLevel > cbsaMarketLevels ) {
        final boolean toAutoAssign = Boolean.FALSE;
        final String zipCode = "10001";
        final String crmOpportunityId = "100";
        final String cbsa = cbsaMarketLevels.iterator().next().getOwnersMarketCbsaLabel().getId();
        final String state = "GL";

        final List< AgentAssignmentLogDto > dtoList = new ArrayList<>();
        final List< String > allAgentEmails = Arrays.asList( new String[] { "agent1@ownerstest.com",
                "agent2@ownerstest.com", "agent3@ownerstest.com", "agent4@ownerstest.com", "agent5@ownerstest.com",
                "agent6@ownerstest.com", "agent7@ownerstest.com", "agent8@ownerstest.com", "agent9@ownerstest.com" } );
        final AgentAssignmentLogDto dto1 = new AgentAssignmentLogDto( allAgentEmails.get( 0 ), 77, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto2 = new AgentAssignmentLogDto( allAgentEmails.get( 1 ), 61, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto3 = new AgentAssignmentLogDto( allAgentEmails.get( 2 ), 34, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto4 = new AgentAssignmentLogDto( allAgentEmails.get( 3 ), 29, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto5 = new AgentAssignmentLogDto( allAgentEmails.get( 4 ), 100, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto6 = new AgentAssignmentLogDto( allAgentEmails.get( 5 ), 82, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto7 = new AgentAssignmentLogDto( allAgentEmails.get( 6 ), 67, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto8 = new AgentAssignmentLogDto( allAgentEmails.get( 7 ), 21, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto9 = new AgentAssignmentLogDto( allAgentEmails.get( 8 ), 100, zipCode, cbsa,
                "texas" );

        dtoList.add( dto1 );
        dtoList.add( dto2 );
        dtoList.add( dto3 );
        dtoList.add( dto4 );
        dtoList.add( dto5 );
        dtoList.add( dto6 );
        dtoList.add( dto7 );
        dtoList.add( dto8 );
        dtoList.add( dto9 );

        final List< AgentDetails > agentDetailList = new ArrayList<>();
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 0 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 1 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 2 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 3 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 4 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 5 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 6 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 7 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 8 ), "active", true ) );

        final AgentAssignmentLog log1 = getAgentAssignmentLog( dto1 );
        final Future< AgentAssignmentLog > future1 = getFuture( log1, 5 );
        final AgentAssignmentLog log2 = getAgentAssignmentLog( dto2 );
        final Future< AgentAssignmentLog > future2 = getFuture( log2, 4 );
        final AgentAssignmentLog log3 = getAgentAssignmentLog( dto3 );
        final Future< AgentAssignmentLog > future3 = getFuture( log3, 2 );
        final AgentAssignmentLog log4 = getAgentAssignmentLog( dto4 );
        final Future< AgentAssignmentLog > future4 = getFuture( log4, 4 );
        final AgentAssignmentLog log5 = getAgentAssignmentLog( dto5 );
        final Future< AgentAssignmentLog > future5 = getFuture( log5, 2 );
        final AgentAssignmentLog log6 = getAgentAssignmentLog( dto6 );
        final Future< AgentAssignmentLog > future6 = getFuture( log6, 4 );
        final AgentAssignmentLog log7 = getAgentAssignmentLog( dto7 );
        final Future< AgentAssignmentLog > future7 = getFuture( log7, 6 );
        final AgentAssignmentLog log8 = getAgentAssignmentLog( dto8 );
        final Future< AgentAssignmentLog > future8 = getFuture( log8, 2 );
        final AgentAssignmentLog log9 = getAgentAssignmentLog( dto9 );
        final Future< AgentAssignmentLog > future9 = getFuture( log9, 2 );

        final Agent expectedAgent = new Agent();
        expectedAgent.setEmail( allAgentEmails.get( 4 ) );

        when( opportunitySource.getCrmId() ).thenReturn( crmOpportunityId );
        when( opportunitySource.getRecordType() ).thenReturn( RecordType.BUYER.getType() );
        when( agentAssignmentLogService.findByPropertyZip( zipCode ) ).thenReturn( dtoList );
        when( agentLookupTask.buildAssignmentLog( dto1, crmOpportunityId, false ) ).thenReturn( future1 );
        when( agentLookupTask.buildAssignmentLog( dto2, crmOpportunityId, false ) ).thenReturn( future2 );
        when( agentLookupTask.buildAssignmentLog( dto3, crmOpportunityId, false ) ).thenReturn( future3 );
        when( agentLookupTask.buildAssignmentLog( dto4, crmOpportunityId, false ) ).thenReturn( future4 );
        when( agentLookupTask.buildAssignmentLog( dto5, crmOpportunityId, false ) ).thenReturn( future5 );
        when( agentLookupTask.buildAssignmentLog( dto6, crmOpportunityId, false ) ).thenReturn( future6 );
        when( agentLookupTask.buildAssignmentLog( dto7, crmOpportunityId, false ) ).thenReturn( future7 );
        when( agentLookupTask.buildAssignmentLog( dto8, crmOpportunityId, false ) ).thenReturn( future8 );
        when( agentLookupTask.buildAssignmentLog( dto9, crmOpportunityId, false ) ).thenReturn( future9 );

        when( agentDetailsService.findByEmailsIn( anyList() ) ).thenReturn( agentDetailList );
        when( agentService.findAgentByCrmOpportunityId( crmOpportunityId ) ).thenReturn( null );
        when( happyAgentsConfig.getSelectedBuyerStages() ).thenReturn( "Showing Homes,Face To Face Meeting" );
        when( opportunityService.findAssignedAgentEmailsBySelectedStagesCount( anyCollection(), anyCollection() ) )
                .thenReturn( new ArrayList<>() );
        when( cbsaMarketLevelService.findByCbsaCodes( anyList() ) ).thenReturn( cbsaMarketLevels );
        when( agentDtoBuilder.convertTo( log5.getAgentEmail() ) ).thenReturn( expectedAgent );
        when( agentAssignmentLogService.findAgentEmailByLeastOppAssignedDate( anyList() ) )
                .thenReturn( expectedAgent.getEmail() );

        final Map< String, Object > map = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, zipCode, state );
        final Agent mostEligibleAgent = getAgentFromResponseMap( map );
        assertEquals( expectedAgent.getEmail(), mostEligibleAgent.getEmail() );

        verify( agentAssignmentLogService ).saveAll( anyList() );
    }

    /**
     * Test get most eligible agent should return good agent having less than
     * level 1 max.
     *
     * @param response
     *            the response
     * @param cbsaMarketLevels
     *            the cbsa market levels
     */
    @Test( dataProvider = "getBuyerResponse" )
    public void testGetMostEligibleAgent_shouldReturnGoodAgent_havingLessThanLevel1Max( final CRMResponse response,
            final List< CbsaMarketLevel > cbsaMarketLevels ) {
        final boolean toAutoAssign = Boolean.FALSE;
        final String zipCode = "10001";
        final String crmOpportunityId = "100";
        final String cbsa = cbsaMarketLevels.iterator().next().getOwnersMarketCbsaLabel().getId();
        final String state = "GL";

        final List< AgentAssignmentLogDto > dtoList = new ArrayList<>();
        final List< String > allAgentEmails = Arrays.asList( new String[] { "agent1@ownerstest.com",
                "agent2@ownerstest.com", "agent3@ownerstest.com", "agent4@ownerstest.com", "agent5@ownerstest.com",
                "agent6@ownerstest.com", "agent7@ownerstest.com", "agent8@ownerstest.com", "agent9@ownerstest.com" } );
        final AgentAssignmentLogDto dto1 = new AgentAssignmentLogDto( allAgentEmails.get( 0 ), 77, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto2 = new AgentAssignmentLogDto( allAgentEmails.get( 1 ), 61, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto3 = new AgentAssignmentLogDto( allAgentEmails.get( 2 ), 34, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto4 = new AgentAssignmentLogDto( allAgentEmails.get( 3 ), 29, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto5 = new AgentAssignmentLogDto( allAgentEmails.get( 4 ), 100, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto6 = new AgentAssignmentLogDto( allAgentEmails.get( 5 ), 82, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto7 = new AgentAssignmentLogDto( allAgentEmails.get( 6 ), 67, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto8 = new AgentAssignmentLogDto( allAgentEmails.get( 7 ), 21, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto9 = new AgentAssignmentLogDto( allAgentEmails.get( 8 ), 100, zipCode, cbsa,
                "texas" );

        dtoList.add( dto1 );
        dtoList.add( dto2 );
        dtoList.add( dto3 );
        dtoList.add( dto4 );
        dtoList.add( dto5 );
        dtoList.add( dto6 );
        dtoList.add( dto7 );
        dtoList.add( dto8 );
        dtoList.add( dto9 );

        final List< AgentDetails > agentDetailList = new ArrayList<>();
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 0 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 1 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 2 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 3 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 4 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 5 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 6 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 7 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 8 ), "active", true ) );

        final AgentAssignmentLog log1 = getAgentAssignmentLog( dto1 );
        final Future< AgentAssignmentLog > future1 = getFuture( log1, 5 );
        final AgentAssignmentLog log2 = getAgentAssignmentLog( dto2 );
        final Future< AgentAssignmentLog > future2 = getFuture( log2, 4 );
        final AgentAssignmentLog log3 = getAgentAssignmentLog( dto3 );
        final Future< AgentAssignmentLog > future3 = getFuture( log3, 2 );
        final AgentAssignmentLog log4 = getAgentAssignmentLog( dto4 );
        final Future< AgentAssignmentLog > future4 = getFuture( log4, 2 );
        final AgentAssignmentLog log5 = getAgentAssignmentLog( dto5 );
        final Future< AgentAssignmentLog > future5 = getFuture( log5, 4 );
        final AgentAssignmentLog log6 = getAgentAssignmentLog( dto6 );
        final Future< AgentAssignmentLog > future6 = getFuture( log6, 6 );
        final AgentAssignmentLog log7 = getAgentAssignmentLog( dto7 );
        final Future< AgentAssignmentLog > future7 = getFuture( log7, 6 );
        final AgentAssignmentLog log8 = getAgentAssignmentLog( dto8 );
        final Future< AgentAssignmentLog > future8 = getFuture( log8, 2 );
        final AgentAssignmentLog log9 = getAgentAssignmentLog( dto9 );
        final Future< AgentAssignmentLog > future9 = getFuture( log9, 5 );

        final Agent expectedAgent = new Agent();
        expectedAgent.setEmail( allAgentEmails.get( 1 ) );

        when( opportunitySource.getCrmId() ).thenReturn( crmOpportunityId );
        when( opportunitySource.getRecordType() ).thenReturn( RecordType.BUYER.getType() );
        when( agentAssignmentLogService.findByPropertyZip( zipCode ) ).thenReturn( dtoList );
        when( agentLookupTask.buildAssignmentLog( dto1, crmOpportunityId, false ) ).thenReturn( future1 );
        when( agentLookupTask.buildAssignmentLog( dto2, crmOpportunityId, false ) ).thenReturn( future2 );
        when( agentLookupTask.buildAssignmentLog( dto3, crmOpportunityId, false ) ).thenReturn( future3 );
        when( agentLookupTask.buildAssignmentLog( dto4, crmOpportunityId, false ) ).thenReturn( future4 );
        when( agentLookupTask.buildAssignmentLog( dto5, crmOpportunityId, false ) ).thenReturn( future5 );
        when( agentLookupTask.buildAssignmentLog( dto6, crmOpportunityId, false ) ).thenReturn( future6 );
        when( agentLookupTask.buildAssignmentLog( dto7, crmOpportunityId, false ) ).thenReturn( future7 );
        when( agentLookupTask.buildAssignmentLog( dto8, crmOpportunityId, false ) ).thenReturn( future8 );
        when( agentLookupTask.buildAssignmentLog( dto9, crmOpportunityId, false ) ).thenReturn( future9 );

        when( agentDetailsService.findByEmailsIn( anyList() ) ).thenReturn( agentDetailList );
        when( agentService.findAgentByCrmOpportunityId( crmOpportunityId ) ).thenReturn( null );
        when( happyAgentsConfig.getSelectedBuyerStages() ).thenReturn( "Showing Homes,Face To Face Meeting" );
        when( opportunityService.findAssignedAgentEmailsBySelectedStagesCount( anyCollection(), anyCollection() ) )
                .thenReturn( new ArrayList<>() );
        when( cbsaMarketLevelService.findByCbsaCodes( anyList() ) ).thenReturn( cbsaMarketLevels );
        when( agentDtoBuilder.convertTo( log2.getAgentEmail() ) ).thenReturn( expectedAgent );

        final Map< String, Object > map = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, zipCode, state );
        final Agent mostEligibleAgent = getAgentFromResponseMap( map );
        assertEquals( expectedAgent.getEmail(), mostEligibleAgent.getEmail() );

        verify( agentAssignmentLogService ).saveAll( anyList() );
    }

    /**
     * Test get most eligible agent should return average agent having less than
     * level 1 max.
     *
     * @param response
     *            the response
     * @param cbsaMarketLevels
     *            the cbsa market levels
     */
    @Test( dataProvider = "getBuyerResponse" )
    public void testGetMostEligibleAgent_shouldReturnAverageAgent_havingLessThanLevel1Max( final CRMResponse response,
            final List< CbsaMarketLevel > cbsaMarketLevels ) {
        final boolean toAutoAssign = Boolean.FALSE;
        final String zipCode = "10001";
        final String crmOpportunityId = "100";
        final String cbsa = cbsaMarketLevels.iterator().next().getOwnersMarketCbsaLabel().getId();
        final String state = "GL";

        final List< AgentAssignmentLogDto > dtoList = new ArrayList<>();
        final List< String > allAgentEmails = Arrays.asList( new String[] { "agent1@ownerstest.com",
                "agent2@ownerstest.com", "agent3@ownerstest.com", "agent4@ownerstest.com", "agent5@ownerstest.com",
                "agent6@ownerstest.com", "agent7@ownerstest.com", "agent8@ownerstest.com", "agent9@ownerstest.com" } );
        final AgentAssignmentLogDto dto1 = new AgentAssignmentLogDto( allAgentEmails.get( 0 ), 77, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto2 = new AgentAssignmentLogDto( allAgentEmails.get( 1 ), 61, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto3 = new AgentAssignmentLogDto( allAgentEmails.get( 2 ), 34, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto4 = new AgentAssignmentLogDto( allAgentEmails.get( 3 ), 29, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto5 = new AgentAssignmentLogDto( allAgentEmails.get( 4 ), 100, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto6 = new AgentAssignmentLogDto( allAgentEmails.get( 5 ), 82, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto7 = new AgentAssignmentLogDto( allAgentEmails.get( 6 ), 67, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto8 = new AgentAssignmentLogDto( allAgentEmails.get( 7 ), 21, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto9 = new AgentAssignmentLogDto( allAgentEmails.get( 8 ), 100, zipCode, cbsa,
                "texas" );

        dtoList.add( dto1 );
        dtoList.add( dto2 );
        dtoList.add( dto3 );
        dtoList.add( dto4 );
        dtoList.add( dto5 );
        dtoList.add( dto6 );
        dtoList.add( dto7 );
        dtoList.add( dto8 );
        dtoList.add( dto9 );

        final List< AgentDetails > agentDetailList = new ArrayList<>();
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 0 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 1 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 2 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 3 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 4 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 5 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 6 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 7 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 8 ), "active", true ) );

        final AgentAssignmentLog log1 = getAgentAssignmentLog( dto1 );
        final Future< AgentAssignmentLog > future1 = getFuture( log1, 5 );
        final AgentAssignmentLog log2 = getAgentAssignmentLog( dto2 );
        final Future< AgentAssignmentLog > future2 = getFuture( log2, 5 );
        final AgentAssignmentLog log3 = getAgentAssignmentLog( dto3 );
        final Future< AgentAssignmentLog > future3 = getFuture( log3, 2 );
        final AgentAssignmentLog log4 = getAgentAssignmentLog( dto4 );
        final Future< AgentAssignmentLog > future4 = getFuture( log4, 2 );
        final AgentAssignmentLog log5 = getAgentAssignmentLog( dto5 );
        final Future< AgentAssignmentLog > future5 = getFuture( log5, 4 );
        final AgentAssignmentLog log6 = getAgentAssignmentLog( dto6 );
        final Future< AgentAssignmentLog > future6 = getFuture( log6, 6 );
        final AgentAssignmentLog log7 = getAgentAssignmentLog( dto7 );
        final Future< AgentAssignmentLog > future7 = getFuture( log7, 6 );
        final AgentAssignmentLog log8 = getAgentAssignmentLog( dto8 );
        final Future< AgentAssignmentLog > future8 = getFuture( log8, 2 );
        final AgentAssignmentLog log9 = getAgentAssignmentLog( dto9 );
        final Future< AgentAssignmentLog > future9 = getFuture( log9, 5 );

        final Agent expectedAgent = new Agent();
        expectedAgent.setEmail( allAgentEmails.get( 2 ) );

        when( opportunitySource.getCrmId() ).thenReturn( crmOpportunityId );
        when( opportunitySource.getRecordType() ).thenReturn( RecordType.BUYER.getType() );
        when( agentAssignmentLogService.findByPropertyZip( zipCode ) ).thenReturn( dtoList );
        when( agentLookupTask.buildAssignmentLog( dto1, crmOpportunityId, false ) ).thenReturn( future1 );
        when( agentLookupTask.buildAssignmentLog( dto2, crmOpportunityId, false ) ).thenReturn( future2 );
        when( agentLookupTask.buildAssignmentLog( dto3, crmOpportunityId, false ) ).thenReturn( future3 );
        when( agentLookupTask.buildAssignmentLog( dto4, crmOpportunityId, false ) ).thenReturn( future4 );
        when( agentLookupTask.buildAssignmentLog( dto5, crmOpportunityId, false ) ).thenReturn( future5 );
        when( agentLookupTask.buildAssignmentLog( dto6, crmOpportunityId, false ) ).thenReturn( future6 );
        when( agentLookupTask.buildAssignmentLog( dto7, crmOpportunityId, false ) ).thenReturn( future7 );
        when( agentLookupTask.buildAssignmentLog( dto8, crmOpportunityId, false ) ).thenReturn( future8 );
        when( agentLookupTask.buildAssignmentLog( dto9, crmOpportunityId, false ) ).thenReturn( future9 );

        when( agentDetailsService.findByEmailsIn( anyList() ) ).thenReturn( agentDetailList );
        when( agentService.findAgentByCrmOpportunityId( crmOpportunityId ) ).thenReturn( null );
        when( happyAgentsConfig.getSelectedBuyerStages() ).thenReturn( "Showing Homes,Face To Face Meeting" );
        when( opportunityService.findAssignedAgentEmailsBySelectedStagesCount( anyCollection(), anyCollection() ) )
                .thenReturn( new ArrayList<>() );
        when( cbsaMarketLevelService.findByCbsaCodes( anyList() ) ).thenReturn( cbsaMarketLevels );
        when( agentDtoBuilder.convertTo( log3.getAgentEmail() ) ).thenReturn( expectedAgent );

        final Map< String, Object > map = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, zipCode, state );
        final Agent mostEligibleAgent = getAgentFromResponseMap( map );
        assertEquals( expectedAgent.getEmail(), mostEligibleAgent.getEmail() );

        verify( agentAssignmentLogService ).saveAll( anyList() );
    }

    /**
     * Test get most eligible agent should filter currently assigned agent and
     * return average agent having less than level 1 max.
     *
     * @param response
     *            the response
     * @param cbsaMarketLevels
     *            the cbsa market levels
     */
    @Test( dataProvider = "getBuyerResponse" )
    public void testGetMostEligibleAgent_shouldFilterCurrentlyAssignedAgent_andReturnAverageAgent_havingLessThanLevel1Max(
            final CRMResponse response, final List< CbsaMarketLevel > cbsaMarketLevels ) {
        final boolean toAutoAssign = Boolean.FALSE;
        final String zipCode = "10001";
        final String crmOpportunityId = "100";
        final String cbsa = cbsaMarketLevels.iterator().next().getOwnersMarketCbsaLabel().getId();
        final String state = "GL";

        final List< AgentAssignmentLogDto > dtoList = new ArrayList<>();
        final List< String > allAgentEmails = Arrays.asList( new String[] { "agent1@ownerstest.com",
                "agent2@ownerstest.com", "agent3@ownerstest.com", "agent4@ownerstest.com", "agent5@ownerstest.com",
                "agent6@ownerstest.com", "agent7@ownerstest.com", "agent8@ownerstest.com", "agent9@ownerstest.com" } );
        final AgentAssignmentLogDto dto1 = new AgentAssignmentLogDto( allAgentEmails.get( 0 ), 77, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto2 = new AgentAssignmentLogDto( allAgentEmails.get( 1 ), 61, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto3 = new AgentAssignmentLogDto( allAgentEmails.get( 2 ), 34, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto4 = new AgentAssignmentLogDto( allAgentEmails.get( 3 ), 29, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto5 = new AgentAssignmentLogDto( allAgentEmails.get( 4 ), 100, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto6 = new AgentAssignmentLogDto( allAgentEmails.get( 5 ), 82, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto7 = new AgentAssignmentLogDto( allAgentEmails.get( 6 ), 67, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto8 = new AgentAssignmentLogDto( allAgentEmails.get( 7 ), 21, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto9 = new AgentAssignmentLogDto( allAgentEmails.get( 8 ), 100, zipCode, cbsa,
                "texas" );

        dtoList.add( dto1 );
        dtoList.add( dto2 );
        dtoList.add( dto3 );
        dtoList.add( dto4 );
        dtoList.add( dto5 );
        dtoList.add( dto6 );
        dtoList.add( dto7 );
        dtoList.add( dto8 );
        dtoList.add( dto9 );

        final List< AgentDetails > agentDetailList = new ArrayList<>();
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 0 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 1 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 2 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 3 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 4 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 5 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 6 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 7 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 8 ), "active", true ) );

        final AgentAssignmentLog log1 = getAgentAssignmentLog( dto1 );
        final Future< AgentAssignmentLog > future1 = getFuture( log1, 5 );
        final AgentAssignmentLog log2 = getAgentAssignmentLog( dto2 );
        final Future< AgentAssignmentLog > future2 = getFuture( log2, 5 );
        final AgentAssignmentLog log3 = getAgentAssignmentLog( dto3 );
        final Future< AgentAssignmentLog > future3 = getFuture( log3, 2 );
        final AgentAssignmentLog log4 = getAgentAssignmentLog( dto4 );
        final Future< AgentAssignmentLog > future4 = getFuture( log4, 2 );
        final AgentAssignmentLog log5 = getAgentAssignmentLog( dto5 );
        final Future< AgentAssignmentLog > future5 = getFuture( log5, 4 );
        final AgentAssignmentLog log6 = getAgentAssignmentLog( dto6 );
        final Future< AgentAssignmentLog > future6 = getFuture( log6, 6 );
        final AgentAssignmentLog log7 = getAgentAssignmentLog( dto7 );
        final Future< AgentAssignmentLog > future7 = getFuture( log7, 6 );
        final AgentAssignmentLog log8 = getAgentAssignmentLog( dto8 );
        final Future< AgentAssignmentLog > future8 = getFuture( log8, 2 );
        final AgentAssignmentLog log9 = getAgentAssignmentLog( dto9 );
        final Future< AgentAssignmentLog > future9 = getFuture( log9, 5 );

        final Agent expectedAgent = new Agent();
        expectedAgent.setEmail( allAgentEmails.get( 3 ) );

        when( opportunitySource.getCrmId() ).thenReturn( crmOpportunityId );
        when( opportunitySource.getRecordType() ).thenReturn( RecordType.BUYER.getType() );
        when( agentAssignmentLogService.findByPropertyZip( zipCode ) ).thenReturn( dtoList );
        when( agentLookupTask.buildAssignmentLog( dto1, crmOpportunityId, false ) ).thenReturn( future1 );
        when( agentLookupTask.buildAssignmentLog( dto2, crmOpportunityId, false ) ).thenReturn( future2 );
        when( agentLookupTask.buildAssignmentLog( dto3, crmOpportunityId, false ) ).thenReturn( future3 );
        when( agentLookupTask.buildAssignmentLog( dto4, crmOpportunityId, false ) ).thenReturn( future4 );
        when( agentLookupTask.buildAssignmentLog( dto5, crmOpportunityId, false ) ).thenReturn( future5 );
        when( agentLookupTask.buildAssignmentLog( dto6, crmOpportunityId, false ) ).thenReturn( future6 );
        when( agentLookupTask.buildAssignmentLog( dto7, crmOpportunityId, false ) ).thenReturn( future7 );
        when( agentLookupTask.buildAssignmentLog( dto8, crmOpportunityId, false ) ).thenReturn( future8 );
        when( agentLookupTask.buildAssignmentLog( dto9, crmOpportunityId, false ) ).thenReturn( future9 );

        when( agentDetailsService.findByEmailsIn( anyList() ) ).thenReturn( agentDetailList );
        when( agentService.findAgentByCrmOpportunityId( crmOpportunityId ) ).thenReturn( allAgentEmails.get( 2 ) );
        when( happyAgentsConfig.getSelectedBuyerStages() ).thenReturn( "Showing Homes,Face To Face Meeting" );
        when( opportunityService.findAssignedAgentEmailsBySelectedStagesCount( anyCollection(), anyCollection() ) )
                .thenReturn( new ArrayList<>() );
        when( cbsaMarketLevelService.findByCbsaCodes( anyList() ) ).thenReturn( cbsaMarketLevels );
        when( agentDtoBuilder.convertTo( log4.getAgentEmail() ) ).thenReturn( expectedAgent );

        final Map< String, Object > map = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, zipCode, state );
        final Agent mostEligibleAgent = getAgentFromResponseMap( map );
        assertEquals( expectedAgent.getEmail(), mostEligibleAgent.getEmail() );

        verify( agentAssignmentLogService ).saveAll( anyList() );
    }

    /**
     * Test get most eligible agent should filter selected stages threshold
     * exceeding agents and return average agent having less than level 1 max.
     *
     * @param response
     *            the response
     * @param cbsaMarketLevels
     *            the cbsa market levels
     */
    @Test( dataProvider = "getBuyerResponse" )
    public void testGetMostEligibleAgent_shouldFilterSelectedStagesThresholdExceedingAgents_andReturnAverageAgent_havingLessThanLevel1Max(
            final CRMResponse response, final List< CbsaMarketLevel > cbsaMarketLevels ) {
        final boolean toAutoAssign = Boolean.FALSE;
        final String zipCode = "10001";
        final String crmOpportunityId = "100";
        final String cbsa = cbsaMarketLevels.iterator().next().getOwnersMarketCbsaLabel().getId();
        final String state = "GL";

        final List< AgentAssignmentLogDto > dtoList = new ArrayList<>();
        final List< String > allAgentEmails = Arrays.asList( new String[] { "agent1@ownerstest.com",
                "agent2@ownerstest.com", "agent3@ownerstest.com", "agent4@ownerstest.com", "agent5@ownerstest.com",
                "agent6@ownerstest.com", "agent7@ownerstest.com", "agent8@ownerstest.com", "agent9@ownerstest.com" } );
        final AgentAssignmentLogDto dto1 = new AgentAssignmentLogDto( allAgentEmails.get( 0 ), 77, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto2 = new AgentAssignmentLogDto( allAgentEmails.get( 1 ), 61, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto3 = new AgentAssignmentLogDto( allAgentEmails.get( 2 ), 34, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto4 = new AgentAssignmentLogDto( allAgentEmails.get( 3 ), 29, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto5 = new AgentAssignmentLogDto( allAgentEmails.get( 4 ), 100, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto6 = new AgentAssignmentLogDto( allAgentEmails.get( 5 ), 82, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto7 = new AgentAssignmentLogDto( allAgentEmails.get( 6 ), 67, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto8 = new AgentAssignmentLogDto( allAgentEmails.get( 7 ), 21, zipCode, cbsa,
                "texas" );
        final AgentAssignmentLogDto dto9 = new AgentAssignmentLogDto( allAgentEmails.get( 8 ), 100, zipCode, cbsa,
                "texas" );

        dtoList.add( dto1 );
        dtoList.add( dto2 );
        dtoList.add( dto3 );
        dtoList.add( dto4 );
        dtoList.add( dto5 );
        dtoList.add( dto6 );
        dtoList.add( dto7 );
        dtoList.add( dto8 );
        dtoList.add( dto9 );

        final List< AgentDetails > agentDetailList = new ArrayList<>();
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 0 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 1 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 2 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 3 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 4 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 5 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 6 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 7 ), "active", true ) );
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 8 ), "active", true ) );

        final AgentAssignmentLog log1 = getAgentAssignmentLog( dto1 );
        final Future< AgentAssignmentLog > future1 = getFuture( log1, 5 );
        final AgentAssignmentLog log2 = getAgentAssignmentLog( dto2 );
        final Future< AgentAssignmentLog > future2 = getFuture( log2, 5 );
        final AgentAssignmentLog log3 = getAgentAssignmentLog( dto3 );
        final Future< AgentAssignmentLog > future3 = getFuture( log3, 2 );
        final AgentAssignmentLog log4 = getAgentAssignmentLog( dto4 );
        final Future< AgentAssignmentLog > future4 = getFuture( log4, 2 );
        final AgentAssignmentLog log5 = getAgentAssignmentLog( dto5 );
        final Future< AgentAssignmentLog > future5 = getFuture( log5, 4 );
        final AgentAssignmentLog log6 = getAgentAssignmentLog( dto6 );
        final Future< AgentAssignmentLog > future6 = getFuture( log6, 6 );
        final AgentAssignmentLog log7 = getAgentAssignmentLog( dto7 );
        final Future< AgentAssignmentLog > future7 = getFuture( log7, 6 );
        final AgentAssignmentLog log8 = getAgentAssignmentLog( dto8 );
        final Future< AgentAssignmentLog > future8 = getFuture( log8, 2 );
        final AgentAssignmentLog log9 = getAgentAssignmentLog( dto9 );
        final Future< AgentAssignmentLog > future9 = getFuture( log9, 5 );

        final Agent expectedAgent = new Agent();
        expectedAgent.setEmail( allAgentEmails.get( 7 ) );

        when( opportunitySource.getCrmId() ).thenReturn( crmOpportunityId );
        when( opportunitySource.getRecordType() ).thenReturn( RecordType.BUYER.getType() );
        when( agentAssignmentLogService.findByPropertyZip( zipCode ) ).thenReturn( dtoList );
        when( agentLookupTask.buildAssignmentLog( dto1, crmOpportunityId, false ) ).thenReturn( future1 );
        when( agentLookupTask.buildAssignmentLog( dto2, crmOpportunityId, false ) ).thenReturn( future2 );
        when( agentLookupTask.buildAssignmentLog( dto3, crmOpportunityId, false ) ).thenReturn( future3 );
        when( agentLookupTask.buildAssignmentLog( dto4, crmOpportunityId, false ) ).thenReturn( future4 );
        when( agentLookupTask.buildAssignmentLog( dto5, crmOpportunityId, false ) ).thenReturn( future5 );
        when( agentLookupTask.buildAssignmentLog( dto6, crmOpportunityId, false ) ).thenReturn( future6 );
        when( agentLookupTask.buildAssignmentLog( dto7, crmOpportunityId, false ) ).thenReturn( future7 );
        when( agentLookupTask.buildAssignmentLog( dto8, crmOpportunityId, false ) ).thenReturn( future8 );
        when( agentLookupTask.buildAssignmentLog( dto9, crmOpportunityId, false ) ).thenReturn( future9 );

        when( agentDetailsService.findByEmailsIn( anyList() ) ).thenReturn( agentDetailList );
        when( agentService.findAgentByCrmOpportunityId( crmOpportunityId ) ).thenReturn( allAgentEmails.get( 2 ) );
        when( happyAgentsConfig.getSelectedBuyerStages() ).thenReturn( "Showing Homes,Face To Face Meeting" );
        when( opportunityService.findAssignedAgentEmailsBySelectedStagesCount( anyCollection(), anyCollection() ) )
                .thenReturn( Arrays.asList( new String[] { allAgentEmails.get( 3 ) } ) );
        when( cbsaMarketLevelService.findByCbsaCodes( anyList() ) ).thenReturn( cbsaMarketLevels );
        when( agentDtoBuilder.convertTo( log8.getAgentEmail() ) ).thenReturn( expectedAgent );

        final Map< String, Object > map = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, zipCode, state );
        final Agent mostEligibleAgent = getAgentFromResponseMap( map );
        assertEquals( expectedAgent.getEmail(), mostEligibleAgent.getEmail() );

        verify( agentAssignmentLogService ).saveAll( anyList() );
    }

    /**
     * Test get most eligible agent should filter inactive agent and forward to
     * ref ex.
     *
     * @param response
     *            the response
     * @param cbsaMarketLevels
     *            the cbsa market levels
     */
    @Test( dataProvider = "getBuyerResponse" )
    public void testGetMostEligibleAgent_shouldFilterInactiveAgent_andForwardToRefEx( final CRMResponse response,
            final List< CbsaMarketLevel > cbsaMarketLevels ) {
        final boolean toAutoAssign = Boolean.TRUE;
        final String zipCode = "10001";
        final String crmOpportunityId = "100";
        final String cbsa = cbsaMarketLevels.iterator().next().getOwnersMarketCbsaLabel().getId();
        final String state = "GL";

        final List< AgentAssignmentLogDto > dtoList = new ArrayList<>();
        final List< String > allAgentEmails = Arrays.asList( new String[] { "agent1@ownerstest.com" } );
        final AgentAssignmentLogDto dto1 = new AgentAssignmentLogDto( allAgentEmails.get( 0 ), 77, zipCode, cbsa,
                "texas" );
        dtoList.add( dto1 );

        final AgentAssignmentLog log1 = getAgentAssignmentLog( dto1 );
        final Future< AgentAssignmentLog > future1 = getFuture( log1, 0 );

        final List< AgentDetails > agentDetailList = new ArrayList<>();
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 0 ), "inactive", true ) );

        final Agent expectedAgent = new Agent();
        expectedAgent.setEmail( allAgentEmails.get( 0 ) );

        when( opportunitySource.getCrmId() ).thenReturn( crmOpportunityId );
        when( opportunitySource.getRecordType() ).thenReturn( RecordType.BUYER.getType() );
        when( agentAssignmentLogService.findByPropertyZip( zipCode ) ).thenReturn( dtoList );
        when( agentLookupTask.buildAssignmentLog( dto1, crmOpportunityId, false ) ).thenReturn( future1 );
        when( agentDetailsService.findByEmailsIn( anyList() ) ).thenReturn( agentDetailList );
        when( agentService.findAgentByCrmOpportunityId( crmOpportunityId ) ).thenReturn( null );
        when( happyAgentsConfig.getSelectedBuyerStages() ).thenReturn( "Showing Homes,Face To Face Meeting" );
        when( opportunityService.findAssignedAgentEmailsBySelectedStagesCount( anyCollection(), anyCollection() ) )
                .thenReturn( new ArrayList<>() );
        when( cbsaMarketLevelService.findByCbsaCodes( anyList() ) ).thenReturn( cbsaMarketLevels );
        when( agentDtoBuilder.convertTo( log1.getAgentEmail() ) ).thenReturn( expectedAgent );

        ReflectionTestUtils.setField( agentLookupBusinessServiceImpl, "allAgentsBusyMessage", "allAgentsBusyMessage" );
        final Map< String, Object > map = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, zipCode, state );
        final Agent mostEligibleAgent = getAgentFromResponseMap( map );
        assertNull( mostEligibleAgent );

        verify( agentAssignmentLogService ).saveAll( anyList() );
        verify( opportunityBusinessService ).forwardToReferralExchange( opportunitySource );
        verify( agentAssignmentLogService ).save( any( AgentAssignmentLog.class ) );
    }

    /**
     * Test get most eligible agent should forward to ref ex if no agent is
     * serving at provided zip.
     *
     * @param response
     *            the response
     * @param cbsaMarketLevels
     *            the cbsa market levels
     */
    @Test( dataProvider = "getBuyerResponse" )
    public void testGetMostEligibleAgent_shouldForwardToRefEx_ifNoAgentIsServingAtProvidedZip(
            final CRMResponse response, final List< CbsaMarketLevel > cbsaMarketLevels ) {
        final boolean toAutoAssign = Boolean.TRUE;
        final String zipCode = "10002";
        final String crmOpportunityId = "100";
        final String state = "GL";

        when( opportunitySource.getCrmId() ).thenReturn( crmOpportunityId );
        when( opportunitySource.getRecordType() ).thenReturn( RecordType.BUYER.getType() );
        when( agentAssignmentLogService.findByPropertyZip( zipCode ) ).thenReturn( new ArrayList<>() );
        when( ownersMarketCbsaLabelService.findByZip( zipCode ) ).thenReturn( new OwnersMarketCbsaLabel() );

        ReflectionTestUtils.setField( agentLookupBusinessServiceImpl, "noCoverageMessage", "noCoverageMessage" );
        final Map< String, Object > map = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, zipCode, state );
        final Agent mostEligibleAgent = getAgentFromResponseMap( map );
        assertNull( mostEligibleAgent );

        verify( agentAssignmentLogService, times( 0 ) ).saveAll( anyList() );
        verify( opportunityBusinessService ).forwardToReferralExchange( opportunitySource );
        verify( agentAssignmentLogService ).save( any( AgentAssignmentLog.class ) );
    }

    /**
     * Test get most eligible agent should not be forwarded to ref ex if no
     * agent is serving at provided zip and auto assignment is false.
     *
     * @param response
     *            the response
     * @param cbsaMarketLevels
     *            the cbsa market levels
     */
    @Test( dataProvider = "getBuyerResponse" )
    public void testGetMostEligibleAgent_shouldNotBeForwardedToRefEx_ifNoAgentIsServingAtProvidedZip_andAutoAssignmentIsFalse(
            final CRMResponse response, final List< CbsaMarketLevel > cbsaMarketLevels ) {
        final boolean toAutoAssign = Boolean.FALSE;
        final String zipCode = "10002";
        final String crmOpportunityId = "100";
        final String state = "GL";

        when( opportunitySource.getCrmId() ).thenReturn( crmOpportunityId );
        when( agentAssignmentLogService.findByPropertyZip( zipCode ) ).thenReturn( new ArrayList<>() );
        when( ownersMarketCbsaLabelService.findByZip( zipCode ) ).thenReturn( new OwnersMarketCbsaLabel() );

        doAnswer( new Answer< Void >() {
            @Override
            public Void answer( final InvocationOnMock invocation ) throws Throwable {
                final AgentAssignmentLog log = invocation.getArgumentAt( 0, AgentAssignmentLog.class );
                Assert.assertEquals( log.getAssignmentStatus(), AssignmentStatus.displayed_for_referral.name() );
                Assert.assertEquals( log.getReason(),
                        HappyAgentsConfig.REASON_DISPLAYED_FOR_REFERRAL + NO_AGENT_AVAILABLE_SUFFIX );
                return null;
            }
        } ).when( agentAssignmentLogService ).save( Mockito.any( AgentAssignmentLog.class ) );

        ReflectionTestUtils.setField( agentLookupBusinessServiceImpl, "noCoverageMessage", "noCoverageMessage" );
        final Map< String, Object > map = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, zipCode, state );
        final Agent mostEligibleAgent = getAgentFromResponseMap( map );
        assertNull( mostEligibleAgent );
        verify( agentAssignmentLogService, times( 0 ) ).saveAll( anyList() );
        verify( opportunityBusinessService, times( 0 ) ).forwardToReferralExchange( opportunitySource );
        verify( agentAssignmentLogService ).save( Mockito.any( AgentAssignmentLog.class ) );
    }

    /**
     * Test get most eligible agent_should be forwarded to ref ex_if no agent is
     * serving at provided zip_and auto assignment istrue.
     *
     * @param response
     *            the response
     * @param cbsaMarketLevels
     *            the cbsa market levels
     */
    @Test( dataProvider = "getBuyerResponse" )
    public void testGetMostEligibleAgent_shouldBeForwardedToRefEx_ifNoAgentIsServingAtProvidedZip_andAutoAssignmentIstrue(
            final CRMResponse response, final List< CbsaMarketLevel > cbsaMarketLevels ) {
        final boolean toAutoAssign = Boolean.TRUE;
        final String zipCode = "10002";
        final String crmOpportunityId = "100";
        final String state = "GL";

        when( opportunitySource.getCrmId() ).thenReturn( crmOpportunityId );
        when( agentAssignmentLogService.findByPropertyZip( zipCode ) ).thenReturn( new ArrayList<>() );
        when( ownersMarketCbsaLabelService.findByZip( zipCode ) ).thenReturn( new OwnersMarketCbsaLabel() );

        doAnswer( new Answer< Void >() {
            @Override
            public Void answer( final InvocationOnMock invocation ) throws Throwable {
                final AgentAssignmentLog log = invocation.getArgumentAt( 0, AgentAssignmentLog.class );
                Assert.assertEquals( log.getAssignmentStatus(), referred.name() );
                Assert.assertEquals( log.getReason(),
                        HappyAgentsConfig.REASON_REFERRED_TO_REF_EX + NO_AGENT_AVAILABLE_SUFFIX );
                return null;
            }
        } ).when( agentAssignmentLogService ).save( Mockito.any( AgentAssignmentLog.class ) );

        ReflectionTestUtils.setField( agentLookupBusinessServiceImpl, "noCoverageMessage", "noCoverageMessage" );
        final Map< String, Object > map = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, zipCode, state );
        final Agent mostEligibleAgent = getAgentFromResponseMap( map );
        assertNull( mostEligibleAgent );
        verify( agentAssignmentLogService, times( 0 ) ).saveAll( anyList() );
        verify( opportunityBusinessService, times( 1 ) ).forwardToReferralExchange( opportunitySource );
        verify( agentAssignmentLogService ).save( Mockito.any( AgentAssignmentLog.class ) );
    }

    /**
     * Test get most eligible agent should throw exception if execution
     * exception occurs.
     *
     * @param response
     *            the response
     * @param cbsaMarketLevels
     *            the cbsa market levels
     * @throws InterruptedException
     *             the interrupted exception
     * @throws ExecutionException
     *             the execution exception
     */
    @Test( dataProvider = "getBuyerResponse", expectedExceptions = ApplicationException.class )
    public void testGetMostEligibleAgent_shouldThrowExceptionIfExecutionExceptionOccurs( final CRMResponse response,
            final List< CbsaMarketLevel > cbsaMarketLevels ) throws InterruptedException, ExecutionException {
        final boolean toAutoAssign = Boolean.TRUE;
        final String zipCode = "10001";
        final String crmOpportunityId = "100";
        final String cbsa = cbsaMarketLevels.iterator().next().getOwnersMarketCbsaLabel().getId();
        final String state = "GL";

        final List< AgentAssignmentLogDto > dtoList = new ArrayList<>();
        final List< String > allAgentEmails = Arrays.asList( new String[] { "agent1@ownerstest.com" } );
        final AgentAssignmentLogDto dto1 = new AgentAssignmentLogDto( allAgentEmails.get( 0 ), 77, zipCode, cbsa,
                "texas" );
        dtoList.add( dto1 );

        final AgentAssignmentLog log1 = getAgentAssignmentLog( dto1 );
        final Future< AgentAssignmentLog > future1 = Mockito.mock( Future.class );

        final List< AgentDetails > agentDetailList = new ArrayList<>();
        agentDetailList.add( getAgentDetail( allAgentEmails.get( 0 ), "active", true ) );

        final Agent expectedAgent = new Agent();
        expectedAgent.setEmail( allAgentEmails.get( 0 ) );

        when( opportunitySource.getCrmId() ).thenReturn( crmOpportunityId );
        when( opportunitySource.getRecordType() ).thenReturn( RecordType.BUYER.getType() );
        when( agentAssignmentLogService.findByPropertyZip( zipCode ) ).thenReturn( dtoList );
        when( agentLookupTask.buildAssignmentLog( dto1, crmOpportunityId, false ) ).thenReturn( future1 );
        when( agentDetailsService.findByEmailsIn( anyList() ) ).thenReturn( agentDetailList );
        when( agentService.findAgentByCrmOpportunityId( crmOpportunityId ) ).thenReturn( null );
        when( happyAgentsConfig.getSelectedBuyerStages() ).thenReturn( "Showing Homes,Face To Face Meeting" );
        when( opportunityService.findAssignedAgentEmailsBySelectedStagesCount( anyCollection(), anyCollection() ) )
                .thenReturn( new ArrayList<>() );
        when( cbsaMarketLevelService.findByCbsaCodes( anyList() ) ).thenReturn( cbsaMarketLevels );
        when( agentDtoBuilder.convertTo( log1.getAgentEmail() ) ).thenReturn( expectedAgent );
        when( future1.get() ).thenThrow( new ExecutionException( null ) );

        agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource, toAutoAssign, zipCode, state );
    }

    /**
     * Gets the agent detail.
     *
     * @param email
     *            the email
     * @param status
     *            the status
     * @param availability
     *            the availability
     * @return the agent detail
     */
    private AgentDetails getAgentDetail( final String email, final String status, final boolean availability ) {
        final AgentDetails agentDetails = new AgentDetails();
        agentDetails.setAvailability( availability );
        final User user = new User();
        user.setEmail( email );
        user.setStatus( status );
        agentDetails.setUser( user );
        return agentDetails;
    }

    /**
     * Gets the buyer response.
     *
     * @return the buyer response
     */
    @DataProvider( name = "getBuyerResponse" )
    private Object[][] getBuyerResponse() {
        final String cbsa = "cbsa1";
        final CRMResponse response = new CRMResponse();
        final Map< String, String > nameMap = new HashMap<>();
        nameMap.put( "Name", "Buyer" );

        final Map< String, Object > record = new HashMap<>();
        record.put( "RecordType", nameMap );
        record.put( "Property_Zip_del__c", "10002" );
        response.getRecords().add( record );

        final List< CbsaMarketLevel > cbsaMarketLevels = new ArrayList<>();
        final CbsaMarketLevel level = new CbsaMarketLevel();
        final OwnersMarketCbsaLabel ownersMarketCbsaLabel = new OwnersMarketCbsaLabel();
        ownersMarketCbsaLabel.setId( cbsa );
        level.setOwnersMarketCbsaLabel( ownersMarketCbsaLabel );
        level.setMinOpportunities( 2 );
        level.setRrThreshold( 1 );
        level.setLevel2MaxGood( 4 );
        level.setLevel1MaxGood( 5 );
        level.setLevel1MaxNew( 3 );
        level.setLevel1MaxAverage( 3 );
        cbsaMarketLevels.add( level );

        return new Object[][] { { response, cbsaMarketLevels } };
    }

    /**
     * Builds the agent assignment log.
     *
     * @param dto
     *            the dto
     * @return the agent assignment log
     */
    private AgentAssignmentLog getAgentAssignmentLog( final AgentAssignmentLogDto dto ) {
        final AgentAssignmentLog log = new AgentAssignmentLog();
        log.setAgentEmail( dto.getAgentEmail() );
        log.setAgentScore( dto.getScore() );
        log.setAgentType( getAgentType( dto.getScore() ) );
        log.setAssignmentStatus( considered.name() );
        log.setCbsaCode( dto.getCbsa() );
        log.setOwnersMarketLabel( dto.getMarketLabel() );
        log.setZip( dto.getZip() );
        log.setPriority( -1 );
        return log;
    }

    /**
     * Gets the agent type.
     *
     * @param score
     *            the score
     * @return the agent type
     */
    private String getAgentType( final double score ) {
        String agentType = AVERAGE.name().toLowerCase();
        if (score >= 20 && score < 40) {
            agentType = AVERAGE.name().toLowerCase();
        } else if (score == 100) {
            agentType = NEW.name().toLowerCase();
        } else if (score >= 40 && score < 100) {
            agentType = GOOD.name().toLowerCase();
        }
        return agentType;
    }

    /**
     * Gets the getAgentFromResponseMap .
     *
     * @param map
     *            the map
     * @return the Agent
     */
    private Agent getAgentFromResponseMap( final Map< String, Object > map ) {
        Agent agent = null;
        if (map.get( BEST_FIT_AGENTS ) != null) {
            final List< Agent > agentList = ( List< Agent > ) map.get( BEST_FIT_AGENTS );
            if (CollectionUtils.isNotEmpty( agentList )) {
                agent = agentList.get( 0 );
            }
        }
        return agent;
    }

    /**
     * Test get most eligible agent should return best S curve agents when S
     * curve is enabled.
     */
    @Test
    public void testGetMostEligibleAgent_ShouldReturnBestSCurveAgents_WhenSCurveIsEnabled() {
        final boolean toAutoAssign = false;
        final String zip = "30303";
        final String priceRange = "100K - 500K";
        final Integer topPrice = 500000;
        final String cbsa = "11620";
        final List< EligibleAgent > list = new ArrayList<>();
        final EligibleAgent ea = new EligibleAgent();
        list.add( ea );
        final Agent agent = new Agent();
        final String agentEmail = "a@a.com";
        agent.setEmail( agentEmail );
        final List< Agent > bestFitAgents = new ArrayList<>();
        bestFitAgents.add( agent );
        final String crmId = "crmId";
        final AgentAssignmentLog log = new AgentAssignmentLog();
        log.setAssignmentStatus(SCurveStatus.S_CURVE_CONSIDERED.name());
        final String state = "GL";

        when( opportunitySource.getPriceRange() ).thenReturn( priceRange );
        when( opportunitySource.getCrmId() ).thenReturn( crmId );
        when( opportunitySource.getPropertyZip() ).thenReturn( zip );
        when( opportunitySource.getOpportunityType() ).thenReturn( "Buyer" );
        when( sCurveConfig.isScurveAllocationEnabled() ).thenReturn( true );
        when( agentOpportunityService.getTopPrice( priceRange ) ).thenReturn( topPrice );
        when( genericDbService.findCbsaByZip( zip ) ).thenReturn( cbsa );
        when( sCurveConfig.getScurveAllocationStates() ).thenReturn( state );
        when( agentLookupService.getBestAgentsFromSCurveServer( zip, topPrice, crmId ) ).thenReturn( list );
        when( agentDtoBuilder.convertTo( ea ) ).thenReturn( agent );
        when( eligibleAgentBuilder.convertFrom( ea ) ).thenReturn( log );

        final Map< String, Object > response = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, null, state );
        assertEquals( response.get( BEST_FIT_AGENTS ), bestFitAgents );
        assertFalse( ( Boolean ) response.get( IS_HAPPY_AGENT_ENABLED ) );
        assertFalse( ( Boolean ) response.get( IS_HUNGRY_AGENT_ENABLED ) );
        verify( agentOpportunityService ).getTopPrice( priceRange );
        verify( genericDbService ).findCbsaByZip( zip );
        verify( agentLookupService ).getBestAgentsFromSCurveServer( zip, topPrice, crmId );
        verify( agentAssignmentLogService ).saveAll( anyList() );
    }

    /**
     * Test get most eligible agent should return best S curve agents and auto
     * assign when S curve is enabled.
     */
    
    @Test
    public void testGetMostEligibleAgent_ShouldReturnBestSCurveAgents_AndAutoAssign_WhenSCurveIsEnabled() {
        final boolean toAutoAssign = true;
        final String zip = "30303";
        final String priceRange = "100K - 500K";
        final Integer topPrice = 500000;
        final String cbsa = "11620";
        final List< EligibleAgent > list = new ArrayList<>();
        final EligibleAgent ea = new EligibleAgent();
        list.add( ea );
        final Agent agent = new Agent();
        final String agentEmail = "a@a.com";
        agent.setEmail( agentEmail );
        final List< Agent > bestFitAgents = new ArrayList<>();
        bestFitAgents.add( agent );
        final String crmId = "crmId";
        final AgentAssignmentLog log = new AgentAssignmentLog();
        log.setAssignmentStatus(SCurveStatus.S_CURVE_CONSIDERED.name());
        final String state = "GL";

        when( opportunitySource.getPriceRange() ).thenReturn( priceRange );
        when( opportunitySource.getCrmId() ).thenReturn( crmId );
        when( opportunitySource.getPropertyZip() ).thenReturn( zip );
        when( opportunitySource.getOpportunityType() ).thenReturn( "Buyer" );
        when( sCurveConfig.isScurveAllocationEnabled() ).thenReturn( true );
        when( agentOpportunityService.getTopPrice( priceRange ) ).thenReturn( topPrice );
        when( genericDbService.findCbsaByZip( zip ) ).thenReturn( cbsa );
        when( sCurveConfig.getScurveAllocationStates() ).thenReturn( state );
        when( agentLookupService.getBestAgentsFromSCurveServer( zip, topPrice, crmId ) ).thenReturn( list );
        when( agentDtoBuilder.convertTo( ea ) ).thenReturn( agent );
        when( eligibleAgentBuilder.convertFrom( ea ) ).thenReturn( log );

        final Map< String, Object > response = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, null, state );
        assertEquals( response.get( BEST_FIT_AGENTS ), bestFitAgents );
        assertFalse( ( Boolean ) response.get( IS_HAPPY_AGENT_ENABLED ) );
        assertFalse( ( Boolean ) response.get( IS_HUNGRY_AGENT_ENABLED ) );
        verify( agentOpportunityService ).getTopPrice( priceRange );
        verify( genericDbService ).findCbsaByZip( zip );
        verify( agentLookupService ).getBestAgentsFromSCurveServer( zip, topPrice, crmId );
        verify( opportunityBusinessService ).assignOpportunityToAgent( opportunitySource,
                bestFitAgents.get( 0 ).getEmail() );
        verify( agentAssignmentLogService ).saveAll( anyList() );
    }

    /**
     * Test get most eligible agent should return best hungry agent when S curve
     * is enabled but price range IS null and hungry enabled.
     */
    @Test
    public void testGetMostEligibleAgent_ShouldReturnBestHungryAgent_WhenSCurveIsEnabledButPriceRangeISNull_AndHungryEnabled() {
        final boolean toAutoAssign = false;
        final String zip = "30303";
        final String priceRange = null;
        final String cbsa = "11620";
        final List< EligibleAgent > list = new ArrayList<>();
        final EligibleAgent ea = new EligibleAgent();
        list.add( ea );
        final Agent agent = new Agent();
        final String agentEmail = "a@a.com";
        agent.setEmail( agentEmail );
        final List< Agent > bestFitAgents = new ArrayList<>();
        bestFitAgents.add( agent );
        final List< Group > hungryAgentGroups = new ArrayList<>();
        final Group hungryAgentGroup = new Group();
        hungryAgentGroups.add( hungryAgentGroup );
        final Set< UserGroup > userGroups = new HashSet<>();
        final UserGroup ug = new UserGroup();
        final User user = new User();
        final String hungryAgent = "u@u.com";
        user.setEmail( hungryAgent );
        ug.setUser( user );
        userGroups.add( ug );
        final String crmId = "crmId";
        final Collection< String > hungryAgentBucketEmails = new ArrayList<>();
        hungryAgentBucketEmails.add( hungryAgent );
        final List< AgentAssignmentLogDto > agentAssignmentLogDtos = new ArrayList<>();
        final AgentAssignmentLogDto dto = new AgentAssignmentLogDto( agentEmail, 76.50D, zip, cbsa, "label" );
        agentAssignmentLogDtos.add( dto );
        final AgentAssignmentLog log = getAgentAssignmentLog( dto );
        log.setAssignmentStatus( HUNGRY_CONSIDERED.name().toLowerCase() );
        final Future< AgentAssignmentLog > future = getFuture( log, 0 );
        final List< AgentDetails > agentDetailsList = new ArrayList<>();
        final AgentDetails ad = new AgentDetails();
        final User user1 = new User();
        user1.setEmail( agentEmail );
        ad.setUser( user1 );
        ad.setAvailability( true );
        agentDetailsList.add( ad );
        final String state = "GL";

        when( opportunitySource.getCrmId() ).thenReturn( crmId );
        when( opportunitySource.getPriceRange() ).thenReturn( priceRange );
        when( opportunitySource.getPropertyZip() ).thenReturn( zip );
        when( opportunitySource.getOpportunityType() ).thenReturn( "Buyer" );
        when( opportunitySource.getRecordType() ).thenReturn( "Buyer" );
        when( sCurveConfig.isScurveAllocationEnabled() ).thenReturn( true );
        when( genericDbService.findCbsaByZip( zip ) ).thenReturn( cbsa );
        when( hungryAgentsConfig.isHungryAgentsEnabled() ).thenReturn( true );
        when( hungryAgentsConfig.getHungryAgentsBucketName() ).thenReturn( "HUNGRYAGENTS" );
        when( groupManagementBusinessService.getGroupsList( hungryAgentsConfig.getHungryAgentsBucketName(),
                FALSE.toString() ) ).thenReturn( hungryAgentGroups );
        when( userGroupService.findByGroup( hungryAgentGroup ) ).thenReturn( userGroups );
        when( agentAssignmentLogService.findByPropertyZipAndEmailsIn( zip, hungryAgentBucketEmails ) )
                .thenReturn( agentAssignmentLogDtos );
        when( agentLookupTask.buildAssignmentLog( any( AgentAssignmentLogDto.class ), anyString(), anyBoolean() ) )
                .thenReturn( future );
        when( agentDetailsService.findByEmailsIn( anyList() ) ).thenReturn( agentDetailsList );
        when( hungryAgentsConfig.getHungryAgentsOppCountThreshold() ).thenReturn( 7 );
        when( agentDtoBuilder.convertTo( agentEmail ) ).thenReturn( agent );
        when( happyAgentsConfig.getSelectedBuyerStages() ).thenReturn( "Showing Homes,Face To Face Meeting" );

        final Map< String, Object > response = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, null, state );

        assertEquals( response.get( BEST_FIT_AGENTS ), bestFitAgents );
        assertFalse( ( Boolean ) response.get( IS_HAPPY_AGENT_ENABLED ) );
        assertFalse( ( Boolean ) response.get( IS_HUNGRY_AGENT_ENABLED ) );
        verifyZeroInteractions( opportunityBusinessService );
        verify( agentAssignmentLogService ).saveAll( anyList() );
    }

    /**
     * Test get most eligible agent should return best hungry agent when S curve
     * is enabled but no CBSA for zip and hungry enabled.
     */
    @Test
    public void testGetMostEligibleAgent_ShouldReturnBestHungryAgent_WhenSCurveIsEnabledButNoCBSAForZip_AndHungryEnabled() {
        final boolean toAutoAssign = false;
        final String zip = "30303";
        final String priceRange = "100K - 500K";
        final Integer topPrice = 500000;
        final String cbsa = null;
        final List< EligibleAgent > list = new ArrayList<>();
        final EligibleAgent ea = new EligibleAgent();
        list.add( ea );
        final Agent agent = new Agent();
        final String agentEmail = "a@a.com";
        agent.setEmail( agentEmail );
        final List< Agent > bestFitAgents = new ArrayList<>();
        bestFitAgents.add( agent );
        final List< Group > hungryAgentGroups = new ArrayList<>();
        final Group hungryAgentGroup = new Group();
        hungryAgentGroups.add( hungryAgentGroup );
        final Set< UserGroup > userGroups = new HashSet<>();
        final UserGroup ug = new UserGroup();
        final User user = new User();
        final String hungryAgent = "u@u.com";
        user.setEmail( hungryAgent );
        ug.setUser( user );
        userGroups.add( ug );
        final String crmId = "crmId";
        final Collection< String > hungryAgentBucketEmails = new ArrayList<>();
        hungryAgentBucketEmails.add( hungryAgent );
        final List< AgentAssignmentLogDto > agentAssignmentLogDtos = new ArrayList<>();
        final AgentAssignmentLogDto dto = new AgentAssignmentLogDto( agentEmail, 76.50D, zip, cbsa, "label" );
        agentAssignmentLogDtos.add( dto );
        final AgentAssignmentLog log = getAgentAssignmentLog( dto );
        log.setAssignmentStatus( HUNGRY_CONSIDERED.name().toLowerCase() );
        final Future< AgentAssignmentLog > future = getFuture( log, 0 );
        final List< AgentDetails > agentDetailsList = new ArrayList<>();
        final AgentDetails ad = new AgentDetails();
        final User user1 = new User();
        user1.setEmail( agentEmail );
        ad.setUser( user1 );
        ad.setAvailability( true );
        agentDetailsList.add( ad );
        final String state = "GL";

        when( agentOpportunityService.getTopPrice( priceRange ) ).thenReturn( topPrice );
        when( opportunitySource.getCrmId() ).thenReturn( crmId );
        when( opportunitySource.getPriceRange() ).thenReturn( priceRange );
        when( opportunitySource.getPropertyZip() ).thenReturn( zip );
        when( opportunitySource.getOpportunityType() ).thenReturn( "Buyer" );
        when( opportunitySource.getRecordType() ).thenReturn( "Buyer" );
        when( sCurveConfig.isScurveAllocationEnabled() ).thenReturn( true );
        when( sCurveConfig.getScurveAllocationStates() ).thenReturn( null );
        when( genericDbService.findCbsaByZip( zip ) ).thenReturn( cbsa );
        when( hungryAgentsConfig.isHungryAgentsEnabled() ).thenReturn( true );
        when( hungryAgentsConfig.getHungryAgentsBucketName() ).thenReturn( "HUNGRYAGENTS" );
        when( groupManagementBusinessService.getGroupsList( hungryAgentsConfig.getHungryAgentsBucketName(),
                FALSE.toString() ) ).thenReturn( hungryAgentGroups );
        when( userGroupService.findByGroup( hungryAgentGroup ) ).thenReturn( userGroups );
        when( agentAssignmentLogService.findByPropertyZipAndEmailsIn( zip, hungryAgentBucketEmails ) )
                .thenReturn( agentAssignmentLogDtos );
        when( agentLookupTask.buildAssignmentLog( any( AgentAssignmentLogDto.class ), anyString(), anyBoolean() ) )
                .thenReturn( future );
        when( agentDetailsService.findByEmailsIn( anyList() ) ).thenReturn( agentDetailsList );
        when( hungryAgentsConfig.getHungryAgentsOppCountThreshold() ).thenReturn( 7 );
        when( agentDtoBuilder.convertTo( agentEmail ) ).thenReturn( agent );
        when( happyAgentsConfig.getSelectedBuyerStages() ).thenReturn( "Showing Homes,Face To Face Meeting" );

        final Map< String, Object > response = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, null, state );

        assertEquals( response.get( BEST_FIT_AGENTS ), bestFitAgents );
        assertFalse( ( Boolean ) response.get( IS_HAPPY_AGENT_ENABLED ) );
        assertFalse( ( Boolean ) response.get( IS_HUNGRY_AGENT_ENABLED ) );
        verifyZeroInteractions( opportunityBusinessService );
        verify( agentAssignmentLogService ).saveAll( anyList() );
    }

    /**
     * Test get most eligible agent should return best hungry agent when S curve
     * is enabled but CBSA not in JM X and hungry enabled.
     */
    @Test
    public void testGetMostEligibleAgent_ShouldReturnBestHungryAgent_WhenSCurveIsEnabledButCBSANotInJMX_AndHungryEnabled() {
        final boolean toAutoAssign = false;
        final String zip = "30303";
        final String priceRange = "100K - 500K";
        final Integer topPrice = 500000;
        final String cbsa = "789as";
        final List< EligibleAgent > list = new ArrayList<>();
        final EligibleAgent ea = new EligibleAgent();
        list.add( ea );
        final Agent agent = new Agent();
        final String agentEmail = "a@a.com";
        agent.setEmail( agentEmail );
        final List< Agent > bestFitAgents = new ArrayList<>();
        bestFitAgents.add( agent );
        final List< Group > hungryAgentGroups = new ArrayList<>();
        final Group hungryAgentGroup = new Group();
        hungryAgentGroups.add( hungryAgentGroup );
        final Set< UserGroup > userGroups = new HashSet<>();
        final UserGroup ug = new UserGroup();
        final User user = new User();
        final String hungryAgent = "u@u.com";
        user.setEmail( hungryAgent );
        ug.setUser( user );
        userGroups.add( ug );
        final String crmId = "crmId";
        final Collection< String > hungryAgentBucketEmails = new ArrayList<>();
        hungryAgentBucketEmails.add( hungryAgent );
        final List< AgentAssignmentLogDto > agentAssignmentLogDtos = new ArrayList<>();
        final AgentAssignmentLogDto dto = new AgentAssignmentLogDto( agentEmail, 76.50D, zip, cbsa, "label" );
        agentAssignmentLogDtos.add( dto );
        final AgentAssignmentLogDto dto2 = new AgentAssignmentLogDto( agentEmail + "1", 76.50D, zip, cbsa, "label" );
        agentAssignmentLogDtos.add( dto2 );
        final AgentAssignmentLog log = getAgentAssignmentLog( dto );
        log.setAssignmentStatus( HUNGRY_CONSIDERED.name().toLowerCase() );
        final Future< AgentAssignmentLog > future = getFuture( log, 0 );
        final AgentAssignmentLog log2 = getAgentAssignmentLog( dto2 );
        log2.setAssignmentStatus( HUNGRY_CONSIDERED.name().toLowerCase() );
        final Future< AgentAssignmentLog > future2 = getFuture( log2, 9 );
        final List< AgentDetails > agentDetailsList = new ArrayList<>();
        final AgentDetails ad = new AgentDetails();
        final User user1 = new User();
        user1.setEmail( agentEmail );
        ad.setUser( user1 );
        ad.setAvailability( true );
        agentDetailsList.add( ad );
        agentDetailsList.add( ad );
        final String state = "GL";

        when( agentOpportunityService.getTopPrice( priceRange ) ).thenReturn( topPrice );
        when( sCurveConfig.getScurveAllocationStates() ).thenReturn( "abc" );
        when( opportunitySource.getCrmId() ).thenReturn( crmId );
        when( opportunitySource.getPriceRange() ).thenReturn( priceRange );
        when( opportunitySource.getPropertyZip() ).thenReturn( zip );
        when( opportunitySource.getOpportunityType() ).thenReturn( "Buyer" );
        when( opportunitySource.getRecordType() ).thenReturn( "Buyer" );
        when( sCurveConfig.isScurveAllocationEnabled() ).thenReturn( true );
        when( genericDbService.findCbsaByZip( zip ) ).thenReturn( cbsa );
        when( hungryAgentsConfig.isHungryAgentsEnabled() ).thenReturn( true );
        when( hungryAgentsConfig.getHungryAgentsBucketName() ).thenReturn( "HUNGRYAGENTS" );
        when( groupManagementBusinessService.getGroupsList( hungryAgentsConfig.getHungryAgentsBucketName(),
                FALSE.toString() ) ).thenReturn( hungryAgentGroups );
        when( userGroupService.findByGroup( hungryAgentGroup ) ).thenReturn( userGroups );
        when( agentAssignmentLogService.findByPropertyZipAndEmailsIn( zip, hungryAgentBucketEmails ) )
                .thenReturn( agentAssignmentLogDtos );
        when( agentLookupTask.buildAssignmentLog( any( AgentAssignmentLogDto.class ), anyString(), anyBoolean() ) )
                .thenReturn( future ).thenReturn( future2 );
        when( agentDetailsService.findByEmailsIn( anyList() ) ).thenReturn( agentDetailsList );
        when( hungryAgentsConfig.getHungryAgentsOppCountThreshold() ).thenReturn( 7 );
        when( agentDtoBuilder.convertTo( agentEmail ) ).thenReturn( agent );
        when( happyAgentsConfig.getSelectedBuyerStages() ).thenReturn( "Showing Homes,Face To Face Meeting" );

        final Map< String, Object > response = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, null, state );

        assertEquals( response.get( BEST_FIT_AGENTS ), bestFitAgents );
        assertFalse( ( Boolean ) response.get( IS_HAPPY_AGENT_ENABLED ) );
        assertFalse( ( Boolean ) response.get( IS_HUNGRY_AGENT_ENABLED ) );
        verifyZeroInteractions( opportunityBusinessService );
        verify( agentAssignmentLogService ).saveAll( anyList() );
    }

    /**
     * Test get most eligible agent should return best hungry agent when S curve
     * is enabled but CBSA not in JM X and hungry enabled and 2 agents in hungry
     * bucket.
     */
    @Test
    public void testGetMostEligibleAgent_ShouldReturnBestHungryAgent_WhenSCurveIsEnabledButCBSANotInJMX_AndHungryEnabled_And2AgentsInHungryBucket() {
        final boolean toAutoAssign = true;
        final String zip = "30303";
        final String priceRange = "100K - 500K";
        final Integer topPrice = 500000;
        final String cbsa = "789as";
        final List< EligibleAgent > list = new ArrayList<>();
        final EligibleAgent ea = new EligibleAgent();
        list.add( ea );
        final Agent agent = new Agent();
        final String agentEmail = "a@a.com";
        agent.setEmail( agentEmail );
        final List< Agent > bestFitAgents = new ArrayList<>();
        bestFitAgents.add( agent );
        final List< Group > hungryAgentGroups = new ArrayList<>();
        final Group hungryAgentGroup = new Group();
        hungryAgentGroups.add( hungryAgentGroup );
        final Set< UserGroup > userGroups = new HashSet<>();
        final UserGroup ug = new UserGroup();
        final User user = new User();
        final String hungryAgent = "u@u.com";
        user.setEmail( hungryAgent );
        ug.setUser( user );
        userGroups.add( ug );
        final String crmId = "crmId";
        final Collection< String > hungryAgentBucketEmails = new ArrayList<>();
        hungryAgentBucketEmails.add( hungryAgent );
        final List< AgentAssignmentLogDto > agentAssignmentLogDtos = new ArrayList<>();
        final AgentAssignmentLogDto dto = new AgentAssignmentLogDto( agentEmail, 76.50D, zip, cbsa, "label" );
        agentAssignmentLogDtos.add( dto );
        final AgentAssignmentLogDto dto2 = new AgentAssignmentLogDto( agentEmail + "1", 76.50D, zip, cbsa, "label" );
        agentAssignmentLogDtos.add( dto2 );
        final AgentAssignmentLog log = getAgentAssignmentLog( dto );
        log.setAssignmentStatus( HUNGRY_CONSIDERED.name().toLowerCase() );
        final Future< AgentAssignmentLog > future = getFuture( log, 0 );
        final AgentAssignmentLog log2 = getAgentAssignmentLog( dto2 );
        log2.setAssignmentStatus( HUNGRY_CONSIDERED.name().toLowerCase() );
        final Future< AgentAssignmentLog > future2 = getFuture( log2, 1 );
        final List< AgentDetails > agentDetailsList = new ArrayList<>();
        final AgentDetails ad = new AgentDetails();
        final User user1 = new User();
        user1.setEmail( agentEmail );
        ad.setUser( user1 );
        ad.setAvailability( true );
        agentDetailsList.add( ad );
        agentDetailsList.add( ad );
        final String state = "GL";

        when( agentOpportunityService.getTopPrice( priceRange ) ).thenReturn( topPrice );
        when( sCurveConfig.getScurveAllocationStates() ).thenReturn( "abc" );
        when( opportunitySource.getCrmId() ).thenReturn( crmId );
        when( opportunitySource.getPriceRange() ).thenReturn( priceRange );
        when( opportunitySource.getPropertyZip() ).thenReturn( zip );
        when( opportunitySource.getOpportunityType() ).thenReturn( "Buyer" );
        when( opportunitySource.getRecordType() ).thenReturn( "Buyer" );
        when( sCurveConfig.isScurveAllocationEnabled() ).thenReturn( true );
        when( genericDbService.findCbsaByZip( zip ) ).thenReturn( cbsa );
        when( hungryAgentsConfig.isHungryAgentsEnabled() ).thenReturn( true );
        when( hungryAgentsConfig.getHungryAgentsBucketName() ).thenReturn( "HUNGRYAGENTS" );
        when( groupManagementBusinessService.getGroupsList( hungryAgentsConfig.getHungryAgentsBucketName(),
                FALSE.toString() ) ).thenReturn( hungryAgentGroups );
        when( userGroupService.findByGroup( hungryAgentGroup ) ).thenReturn( userGroups );
        when( agentAssignmentLogService.findByPropertyZipAndEmailsIn( zip, hungryAgentBucketEmails ) )
                .thenReturn( agentAssignmentLogDtos );
        when( agentLookupTask.buildAssignmentLog( any( AgentAssignmentLogDto.class ), anyString(), anyBoolean() ) )
                .thenReturn( future ).thenReturn( future2 );
        when( agentDetailsService.findByEmailsIn( anyList() ) ).thenReturn( agentDetailsList );
        when( hungryAgentsConfig.getHungryAgentsOppCountThreshold() ).thenReturn( 7 );
        when( agentDtoBuilder.convertTo( agentEmail ) ).thenReturn( agent );
        when( happyAgentsConfig.getSelectedBuyerStages() ).thenReturn( "Showing Homes,Face To Face Meeting" );

        final Map< String, Object > response = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, null, state );

        assertEquals( response.get( BEST_FIT_AGENTS ), bestFitAgents );
        assertFalse( ( Boolean ) response.get( IS_HAPPY_AGENT_ENABLED ) );
        assertFalse( ( Boolean ) response.get( IS_HUNGRY_AGENT_ENABLED ) );
        verify( agentAssignmentLogService ).saveAll( anyList() );
    }

    /**
     * Test get best performing agents by zipcode V 1.
     */
    @Test
    public void testGetBestPerformingAgentsByZipcodeV1() {
        final String crmOpportunityId = "crmId";
        final String state = "MA";
        final String zip = "30303";
        final String priceRange = "100K - 500K";
        final Integer topPrice = 500000;
        final String cbsa = "11620";
        final List< EligibleAgent > list = new ArrayList<>();
        final EligibleAgent ea = new EligibleAgent();
        list.add( ea );
        final Agent agent = new Agent();
        final String agentEmail = "a@a.com";
        agent.setEmail( agentEmail );
        final List< Agent > bestFitAgents = new ArrayList<>();
        bestFitAgents.add( agent );
        final AgentAssignmentLog log = new AgentAssignmentLog();
        log.setAssignmentStatus(SCurveStatus.S_CURVE_CONSIDERED.name());

        when( opportunityBusinessService.getOpportunity( crmOpportunityId ) ).thenReturn( opportunitySource );
        when( opportunitySource.getPriceRange() ).thenReturn( priceRange );
        when( opportunitySource.getCrmId() ).thenReturn( crmOpportunityId );
        when( opportunitySource.getPropertyZip() ).thenReturn( zip );
        when( opportunitySource.getOpportunityType() ).thenReturn( "Buyer" );
        when( sCurveConfig.isScurveAllocationEnabled() ).thenReturn( true );
        when( agentOpportunityService.getTopPrice( priceRange ) ).thenReturn( topPrice );
        when( genericDbService.findCbsaByZip( zip ) ).thenReturn( cbsa );
        when( sCurveConfig.getScurveAllocationStates() ).thenReturn( state );
        when( agentLookupService.getBestAgentsFromSCurveServer( zip, topPrice, crmOpportunityId ) ).thenReturn( list );
        when( agentDtoBuilder.convertTo( ea ) ).thenReturn( agent );
        when( eligibleAgentBuilder.convertFrom( ea ) ).thenReturn( log );

        final Map< String, Object > response = agentLookupBusinessServiceImpl.getBestPerformingAgentsByZipcodeV1( zip,
                crmOpportunityId, state );

        assertEquals( response.get( BEST_FIT_AGENTS ), bestFitAgents );
        assertFalse( ( Boolean ) response.get( IS_HAPPY_AGENT_ENABLED ) );
        assertFalse( ( Boolean ) response.get( IS_HUNGRY_AGENT_ENABLED ) );
        verify( agentOpportunityService ).getTopPrice( priceRange );
        verify( genericDbService ).findCbsaByZip( zip );
        verify( agentLookupService ).getBestAgentsFromSCurveServer( zip, topPrice, crmOpportunityId );
        verify( agentAssignmentLogService ).saveAll( anyList() );
    }

    /**
     * Test get best performing agents by zipcode V 1 should throw excpetion if
     * no agent found.
     */
    @Test( expectedExceptions = OpportunityNotAssignedException.class )
    public void testGetBestPerformingAgentsByZipcodeV1_ShouldThrowExcpetionIfNoAgentFound() {
        final String crmOpportunityId = "crmId";
        final String state = "MA";
        final String zip = "30303";

        when( opportunityBusinessService.getOpportunity( crmOpportunityId ) ).thenThrow( NullPointerException.class );

        agentLookupBusinessServiceImpl.getBestPerformingAgentsByZipcodeV1( zip, crmOpportunityId, state );
    }
    
    /**
     * Test get most eligible agent should return best S curve agents when S
     * curve is enabled.
     */
    @Test
    public void testGetMostEligibleAgent_ShouldReturnBestSCurveAgents_afterFilteringOffDutyAgents() {
        final boolean toAutoAssign = false;
        final String zip = "30303";
        final String priceRange = "100K - 500K";
        final Integer topPrice = 500000;
        final String cbsa = "11620";
        final List< EligibleAgent > list = new ArrayList<>();
        EligibleAgent ea1 = new EligibleAgent();
        EligibleAgent ea2 = new EligibleAgent();
        
        ea1.setEmail("off1@offduty.com");
        list.add( ea1 );
        
        ea2 = new EligibleAgent();
        ea2.setEmail("onduty1@onduty.com");
        list.add( ea2 );
        
        
        
        final Agent ondutyAgent1 = new Agent();
        final String agentEmail = "onduty1@onduty.com";
        ondutyAgent1.setEmail( agentEmail );
        
        final Agent offDutyAgent = new Agent();
        final String agentEmail1 = "off1@offduty.com";
        offDutyAgent.setEmail( agentEmail1 );
        
        
        final List< Agent > bestFitAgents = new ArrayList<>();
        bestFitAgents.add( ondutyAgent1 );
        final String crmId = "crmId";
        final AgentAssignmentLog log = new AgentAssignmentLog();
        final String state = "GL";
        
        List<String> offDutyAgentEmailList = new ArrayList<String>();
        offDutyAgentEmailList.add("off1@offduty.com");

        
        when( opportunitySource.getPriceRange() ).thenReturn( priceRange );
        when( opportunitySource.getCrmId() ).thenReturn( crmId );
        when( opportunitySource.getPropertyZip() ).thenReturn( zip );
        when( opportunitySource.getOpportunityType() ).thenReturn( "Buyer" );
        when( sCurveConfig.isScurveAllocationEnabled() ).thenReturn( true );
        when( agentOpportunityService.getTopPrice( priceRange ) ).thenReturn( topPrice );
        when( genericDbService.findCbsaByZip( zip ) ).thenReturn( cbsa );
        when( sCurveConfig.getScurveAllocationStates() ).thenReturn( state );
        when( agentLookupService.getBestAgentsFromSCurveServer( zip, topPrice, crmId ) ).thenReturn( list );
        when( agentDtoBuilder.convertTo( ea1 ) ).thenReturn( offDutyAgent );
        when( agentDtoBuilder.convertTo( ea2 ) ).thenReturn( ondutyAgent1 );
        when( eligibleAgentBuilder.convertFrom( ea1 ) ).thenReturn( log );
        when( eligibleAgentBuilder.convertFrom( ea2 ) ).thenReturn( log );
        when(agentDetailsService.findUnAvailableAgentsByEmailsIn(anyList() )).thenReturn(offDutyAgentEmailList);

        final Map< String, Object > response = agentLookupBusinessServiceImpl.getMostEligibleAgent( opportunitySource,
                toAutoAssign, null, state );
        assertEquals( response.get( BEST_FIT_AGENTS ), bestFitAgents );
        assertFalse( ( Boolean ) response.get( IS_HAPPY_AGENT_ENABLED ) );
        assertFalse( ( Boolean ) response.get( IS_HUNGRY_AGENT_ENABLED ) );
        verify( agentOpportunityService ).getTopPrice( priceRange );
        verify( genericDbService ).findCbsaByZip( zip );
        verify( agentLookupService ).getBestAgentsFromSCurveServer( zip, topPrice, crmId );
        verify( agentAssignmentLogService ).saveAll( anyList() );
    }
}
