package com.owners.gravitas.business.impl;

import static com.owners.gravitas.config.HappyAgentsConfig.BEST_FIT_AGENTS;
import static com.owners.gravitas.config.HappyAgentsConfig.IS_HAPPY_AGENT_ENABLED;
import static com.owners.gravitas.config.HappyAgentsConfig.NO_AGENT_AVAILABLE_SUFFIX;
import static com.owners.gravitas.config.HappyAgentsConfig.REASON_AGENT_EXCEED_ALL_THRESHOLDS;
import static com.owners.gravitas.config.HappyAgentsConfig.REASON_AGENT_IS_UNAVAILABLE;
import static com.owners.gravitas.config.HappyAgentsConfig.REASON_AGENT_STATUS_IS_HOLD;
import static com.owners.gravitas.config.HappyAgentsConfig.REASON_AGENT_STATUS_IS_INACTIVE;
import static com.owners.gravitas.config.HappyAgentsConfig.REASON_AGENT_STATUS_IS_ONBOARDING;
import static com.owners.gravitas.config.HappyAgentsConfig.REASON_ASSIGNED_TO_OTHER_AGENT_HAVING_LESS_THAN_MIN_OPPS;
import static com.owners.gravitas.config.HappyAgentsConfig.REASON_ASSIGNED_TO_OTHER_AVERAGE_AGENT_HAVING_LESS_LEVEL1_MAX_OPPS;
import static com.owners.gravitas.config.HappyAgentsConfig.REASON_ASSIGNED_TO_OTHER_GOOD_AGENT_HAVING_LESS_LEVEL1_MAX_OPPS;
import static com.owners.gravitas.config.HappyAgentsConfig.REASON_ASSIGNED_TO_OTHER_GOOD_AGENT_HAVING_LESS_LEVEL2_MAX_OPPS;
import static com.owners.gravitas.config.HappyAgentsConfig.REASON_ASSIGNED_TO_OTHER_NEW_AGENT_HAVING_LESS_LEVEL1_MAX_OPPS;
import static com.owners.gravitas.config.HappyAgentsConfig.REASON_CURRENTLY_ASSIGNED_AGENT;
import static com.owners.gravitas.config.HappyAgentsConfig.REASON_DISPLAYED_FOR_REFERRAL;
import static com.owners.gravitas.config.HappyAgentsConfig.REASON_REFERRED_TO_REF_EX;
import static com.owners.gravitas.config.HappyAgentsConfig.REASON_SELECTED_STAGES;
import static com.owners.gravitas.config.HungryAgentsConfig.AGENT_HAS_MORE_THAN_THRESHOLD_NO_OF_OPPORTUNITIES;
import static com.owners.gravitas.config.HungryAgentsConfig.ASSIGNED_TO_THE_MOST_ELIGIBLE_HUNGRY_AGENT;
import static com.owners.gravitas.config.HungryAgentsConfig.IS_HUNGRY_AGENT_ENABLED;
import static com.owners.gravitas.constants.Constants.COMMA;
import static com.owners.gravitas.constants.Constants.DEFAULT_CBSA;
import static com.owners.gravitas.enums.AgentType.AVERAGE;
import static com.owners.gravitas.enums.AgentType.GOOD;
import static com.owners.gravitas.enums.AgentType.NEW;
import static com.owners.gravitas.enums.AssignmentStatus.auto_assigned;
import static com.owners.gravitas.enums.AssignmentStatus.considered;
import static com.owners.gravitas.enums.AssignmentStatus.displayed;
import static com.owners.gravitas.enums.AssignmentStatus.excluded;
import static com.owners.gravitas.enums.AssignmentStatus.referred;
import static com.owners.gravitas.enums.HungryAgentsStatus.HUNGRY_AUTO_ASSIGNED;
import static com.owners.gravitas.enums.HungryAgentsStatus.HUNGRY_CONSIDERED;
import static com.owners.gravitas.enums.HungryAgentsStatus.HUNGRY_DISPLAYED;
import static com.owners.gravitas.enums.HungryAgentsStatus.HUNGRY_EXCLUDED;
import static com.owners.gravitas.enums.RecordType.BUYER;
import static com.owners.gravitas.enums.SCurveStatus.S_CURVE_AUTO_ASSIGNED;
import static com.owners.gravitas.enums.SCurveStatus.S_CURVE_DISPLAYED;
import static com.owners.gravitas.enums.UserStatusType.HOLD;
import static com.owners.gravitas.enums.UserStatusType.INACTIVE;
import static com.owners.gravitas.enums.UserStatusType.ONBOARDING;
import static java.lang.Boolean.FALSE;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.AgentLookupBusinessService;
import com.owners.gravitas.business.GroupManagementBusinessService;
import com.owners.gravitas.business.OpportunityBusinessService;
import com.owners.gravitas.business.builder.AgentDtoBuilder;
import com.owners.gravitas.business.builder.EligibleAgentBuilder;
import com.owners.gravitas.business.task.AgentLookupTask;
import com.owners.gravitas.config.HappyAgentsConfig;
import com.owners.gravitas.config.HungryAgentsConfig;
import com.owners.gravitas.config.SCurveConfig;
import com.owners.gravitas.domain.entity.AgentAssignmentLog;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.CbsaMarketLevel;
import com.owners.gravitas.domain.entity.Group;
import com.owners.gravitas.domain.entity.OwnersMarketCbsaLabel;
import com.owners.gravitas.domain.entity.UserGroup;
import com.owners.gravitas.dto.Agent;
import com.owners.gravitas.dto.AgentAssignmentLogDto;
import com.owners.gravitas.dto.agentassgn.EligibleAgent;
import com.owners.gravitas.enums.AssignmentStatus;
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
 * The Class AgentLookupBusinessServiceImpl.
 *
 * @author ankusht, abhishek, amit
 */
@Service( "agentLookupBusinessService" )
public class AgentLookupBusinessServiceImpl implements AgentLookupBusinessService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentLookupBusinessServiceImpl.class );

    /** The Constant ARE_AGENTS_SERVING_TO_ZIP. */
    private static final String ARE_AGENTS_SERVING_TO_ZIP = "areAgentsServingToZip";

    /** The Constant MOST_ELIGIBLE_HAPPY_AGENT. */
    private static final String MOST_ELIGIBLE_HAPPY_AGENT = "mostEligibleHappyAgent";

    /** The ref ex agent email. */
    @Value( "${ref.ex.agent.email}" )
    private String refExAgentEmail;

    /** The ref ex agent email. */
    @Value( "${no.coverage.message}" )
    private String noCoverageMessage;

    /** The ref ex agent email. */
    @Value( "${all.agent.busy.message}" )
    private String allAgentsBusyMessage;

    /** The agent service. */
    @Autowired
    private AgentService agentService;

    /** The agent details service. */
    @Autowired
    private AgentDetailsService agentDetailsService;

    /** The agent assignment log service. */
    @Autowired
    private AgentAssignmentLogService agentAssignmentLogService;

    /** The agent dto builder. */
    @Autowired
    private AgentDtoBuilder agentDtoBuilder;

    /** The opportunity service. */
    @Autowired
    private OpportunityService opportunityService;

    /** The agent lookup task. */
    @Autowired
    private AgentLookupTask agentLookupTask;

    /** The cbsa market level service. */
    @Autowired
    private CbsaMarketLevelService cbsaMarketLevelService;

    /** The opportunity business service. */
    @Autowired
    private OpportunityBusinessService opportunityBusinessService;

    /** The happy agents config. */
    @Autowired
    private HappyAgentsConfig happyAgentsConfig;

    /** The hungry agents config. */
    @Autowired
    private HungryAgentsConfig hungryAgentsConfig;

    /** The owners market cbsa label service. */
    @Autowired
    private OwnersMarketCbsaLabelService ownersMarketCbsaLabelService;

    /** The group management business service. */
    @Autowired
    private GroupManagementBusinessService groupManagementBusinessService;

    /** The user group service. */
    @Autowired
    private UserGroupService userGroupService;

    /** The generic db service. */
    @Autowired
    private GenericDbService genericDbService;

    /** The agent opportunity service. */
    @Autowired
    private AgentOpportunityService agentOpportunityService;

    /** The agent lookup service. */
    @Autowired
    private AgentLookupService agentLookupService;

    /** The s curve config. */
    @Autowired
    private SCurveConfig sCurveConfig;

    /** The eligible agent builder. */
    @Autowired
    private EligibleAgentBuilder eligibleAgentBuilder;

    /**
     * Gets the s curve agents.
     *
     * @param zip
     *            the zip
     * @param priceRange
     *            the price range
     * @param opportunitySource
     *            the opportunity source
     * @param toAutoAssign
     *            the to auto assign
     * @param state
     *            the state
     * @return the s curve agents
     */
    private List< Agent > getSCurveAgents( final String zip, final String priceRange,
            final OpportunitySource opportunitySource, final boolean toAutoAssign, final String state ) {
        List< Agent > agents = new ArrayList<>();

        if (StringUtils.isNotBlank( priceRange )) {
            final int topPrice = agentOpportunityService.getTopPrice( priceRange );
            final String crmId = opportunitySource.getCrmId();
            LOGGER.info(
                    "priceRange=" + priceRange + ", top price=" + topPrice + ", CRM_ID=" + crmId + ", state=" + state );

            final String jmxConfiguredStates = sCurveConfig.getScurveAllocationStates();
            final boolean toConsiderForSCurveAllocation = StringUtils.isBlank( jmxConfiguredStates )
                    || ( StringUtils.isNotBlank( state ) && jmxConfiguredStates.contains( state ) );

            if (toConsiderForSCurveAllocation) {
                final List< EligibleAgent > eligibleAgents = agentLookupService.getBestAgentsFromSCurveServer( zip,
                        topPrice, crmId );
                final String cbsaByZip = genericDbService.findCbsaByZip( zip );
                agents = populateAgents( eligibleAgents, crmId, zip, cbsaByZip, toAutoAssign );
                if (toAutoAssign && !agents.isEmpty()) {
                    opportunityBusinessService.assignOpportunityToAgent( opportunitySource,
                            agents.get( 0 ).getEmail() );
                }
            } else {
                LOGGER.info( "The state=" + state + " corresponding to opportunity=" + crmId
                        + " is not part of JMX params. Exiting SCURVE loop." );
            }
        } else {
            LOGGER.info( "can't get best agent from SCURVE as price range is null/empty" );
        }
        return agents;
    }

    /**
     * Populate agents.
     *
     * @param eligibleAgents
     *            the eligible agents
     * @param toAutoAssign
     * @return the list
     */
    private List< Agent > populateAgents( final List< EligibleAgent > eligibleAgents, final String crmOpportunityId,
            final String zip, final String cbsaCode, final boolean toAutoAssign ) {
        final List< Agent > agents = new ArrayList<>();
        if (CollectionUtils.isNotEmpty( eligibleAgents )) {
            final AtomicInteger size = new AtomicInteger( eligibleAgents.size() );
            final List< AgentAssignmentLog > list = new ArrayList<>( size.get() );
            final List< String > allAgentEmails = eligibleAgents.stream().map( agent -> agent.getEmail() )
                    .collect( Collectors.toList() );
            final List< String > offDutyAgentList = agentDetailsService
                    .findUnAvailableAgentsByEmailsIn( allAgentEmails );
            LOGGER.info( "off duty agent list : " + offDutyAgentList );
            eligibleAgents.forEach( ea -> {
                final Agent agent = agentDtoBuilder.convertTo( ea );
                final AgentAssignmentLog log = buildAgentAssignmentLog( crmOpportunityId, zip, cbsaCode,
                        size.decrementAndGet(), ea );
                if (CollectionUtils.isNotEmpty( offDutyAgentList ) && offDutyAgentList.contains( agent.getEmail() )) {
                    updateStatusAndReason( log, SCurveStatus.S_CURVE_EXCLUDED.name().toLowerCase(), REASON_AGENT_IS_UNAVAILABLE );
                } else {
                    agents.add( agent );
                }
                list.add( log );
            } );

            final String firstAgentStatus = toAutoAssign ? S_CURVE_AUTO_ASSIGNED.name().toLowerCase() : S_CURVE_DISPLAYED.name().toLowerCase();
            final List< AgentAssignmentLog > consideredAgents = list
                    .stream().filter(
                            log -> !log.getAssignmentStatus().equalsIgnoreCase( SCurveStatus.S_CURVE_EXCLUDED.name() ) )
                    .collect( Collectors.toList() );
            if (CollectionUtils.isNotEmpty( consideredAgents )) {
                consideredAgents.get( 0 ).setAssignmentStatus( firstAgentStatus );
            } else {
                LOGGER.info( "All agents were found as off duty so no elible agents by Scurve" );
            }
            agentAssignmentLogService.saveAll( list );
        } else {
            LOGGER.info( "No eligible agent found by SCURVE" );
        }
        return agents;
    }

    /**
     * Builds the agent assignment log.
     *
     * @param crmOpportunityId
     *            the crm opportunity id
     * @param zip
     *            the zip
     * @param cbsaCode
     *            the cbsa code
     * @param size
     *            the size
     * @param ea
     *            the ea
     * @return the agent assignment log
     */
    private AgentAssignmentLog buildAgentAssignmentLog( final String crmOpportunityId, final String zip,
            final String cbsaCode, final int priority, final EligibleAgent ea ) {
        final AgentAssignmentLog log = eligibleAgentBuilder.convertFrom( ea );
        log.setCrmOpportunityId( crmOpportunityId );
        log.setZip( zip );
        log.setCbsaCode( cbsaCode );
        log.setPriority( priority );
        return log;
    }

    /**
     * Gets the best performing agents by zipcode V 1.
     *
     * @param zipcode
     *            the zipcode
     * @param crmOpportunityId
     *            the crm opportunity id
     * @param state
     *            the state
     * @return the best performing agents by zipcode V 1
     */
    @Override
    @Transactional( propagation = REQUIRES_NEW )
    public Map< String, Object > getBestPerformingAgentsByZipcodeV1( final String zipcode,
            final String crmOpportunityId, final String state ) {
        LOGGER.info( "called getBestPerformingAgentsByZipcodeV1 for opp: " + crmOpportunityId + " zip: " + zipcode
                + " state: " + state );
        try {
            final OpportunitySource opportunitySource = opportunityBusinessService.getOpportunity( crmOpportunityId );
            LOGGER.info( "Got OpportunitySource: " + opportunitySource.getCrmId() );
            return getMostEligibleAgent( opportunitySource, false, zipcode, state );
        } catch ( final Exception e ) {
            throw new OpportunityNotAssignedException( "System error. Please retry assignment." );
        }
    }

    /**
     * Gets the most eligible hungry agent.
     *
     * @param opportunitySource
     *            the opportunity source
     * @param toAutoAssign
     *            the to auto assign
     * @param zipCode
     *            the zip code
     * @return the most eligible hungry agent
     */
    private Agent getMostEligibleHungryAgent( final OpportunitySource opportunitySource, final boolean toAutoAssign,
            final String zipCode ) {
        Agent agent = null;
        final List< String > hungryAgentBucketEmails = getHungryAgentBucketEmails();

        if (CollectionUtils.isNotEmpty( hungryAgentBucketEmails )) {
            final String crmOpportunityId = opportunitySource.getCrmId();
            LOGGER.info( "HUNGRYAGENTS bucket exists. Looking up for a suitable agent for: " + crmOpportunityId );

            final String zip = StringUtils.isNotBlank( zipCode ) ? zipCode : opportunitySource.getPropertyZip();
            final Set< AgentAssignmentLogDto > agentAssignmentLogDtos = populateAgentAssignmentDtos( zip,
                    hungryAgentBucketEmails );
            final List< AgentAssignmentLog > agentAssignmentLogs = getAgentAssignmenLogs( crmOpportunityId,
                    agentAssignmentLogDtos, true );

            final List< String > allAgentEmails = agentAssignmentLogs.stream().map( log -> log.getAgentEmail() )
                    .collect( Collectors.toList() );
            LOGGER.info( "Hungry agents initially considered: " + allAgentEmails );

            if (CollectionUtils.isNotEmpty( agentAssignmentLogs )) {
                applyInitialFilters( agentAssignmentLogs, allAgentEmails, crmOpportunityId,
                        opportunitySource.getRecordType(), true );
                applyMinimumOppsFilter( agentAssignmentLogs );

                final AgentAssignmentLog mostEligibleHungryAgent = getMostEligibleHungryAgent( agentAssignmentLogs );
                if (mostEligibleHungryAgent != null) {
                    agentAssignmentLogs.stream()
                            .filter( log -> log.getAssignmentStatus().equals( HUNGRY_CONSIDERED.name().toLowerCase() )
                                    && !log.getAgentEmail().equals( mostEligibleHungryAgent.getAgentEmail() ) )
                            .forEach( log -> updateStatusAndReason( log, HUNGRY_EXCLUDED.name().toLowerCase(),
                                    ASSIGNED_TO_THE_MOST_ELIGIBLE_HUNGRY_AGENT ) );
                    agent = getEligibleAgent( opportunitySource, toAutoAssign, zip, agentAssignmentLogs,
                            mostEligibleHungryAgent, true );
                    LOGGER.info( "assigning/displaying HUNGRY agent: " + agent.getEmail() + " to " + crmOpportunityId );
                }
                agentAssignmentLogService.saveAll( agentAssignmentLogs );
            }
        }
        return agent;
    }

    /**
     * Gets the hungry agent bucket emails.
     *
     * @return the hungry agent bucket emails
     */
    private List< String > getHungryAgentBucketEmails() {
        List< String > bucketAgentEmails = null;
        final List< Group > hungryAgentGroups = groupManagementBusinessService
                .getGroupsList( hungryAgentsConfig.getHungryAgentsBucketName(), FALSE.toString() );
        if (CollectionUtils.isNotEmpty( hungryAgentGroups )) {
            final Group hungryAgentGroup = hungryAgentGroups.get( 0 );
            final Set< UserGroup > userGroups = userGroupService.findByGroup( hungryAgentGroup );
            bucketAgentEmails = userGroups.stream().map( s -> s.getUser().getEmail() ).collect( Collectors.toList() );
            LOGGER.info( "Agents in HUNGRYAGENTS bucket are: " + bucketAgentEmails );
        }
        return bucketAgentEmails;
    }

    /**
     * Gets the most eligible hungry agent.
     *
     * @param agentAssignmentLogs
     *            the agent assignment logs
     * @return the most eligible hungry agent
     */
    private AgentAssignmentLog getMostEligibleHungryAgent( final List< AgentAssignmentLog > agentAssignmentLogs ) {
        AgentAssignmentLog eligibleAgent = null;
        final List< AgentAssignmentLog > consideredAgents = agentAssignmentLogs.stream()
                .filter( log -> log.getAssignmentStatus().equals( HUNGRY_CONSIDERED.name().toLowerCase() ) )
                .collect( Collectors.toList() );
        if (CollectionUtils.isNotEmpty( consideredAgents )) {
            if (consideredAgents.size() == 1) {
                eligibleAgent = consideredAgents.get( 0 );
            } else {
                final Map< Integer, List< AgentAssignmentLog > > countAgentMap = mapLogsToOpportunityCount(
                        agentAssignmentLogs );
                final List< AgentAssignmentLog > agentsHavingLeastAssignedOpps = countAgentMap.entrySet().iterator()
                        .next().getValue();
                LOGGER.info( "agentsHavingLeastAssignedOpps.size() = " + agentsHavingLeastAssignedOpps.size() );
                eligibleAgent = getHighestScoringAgent( agentsHavingLeastAssignedOpps );
            }
        }
        return eligibleAgent;
    }

    /**
     * Map logs to ascending order of opportunity count.
     *
     * @param logList
     *            the log list
     * @return the map
     */
    private Map< Integer, List< AgentAssignmentLog > > mapLogsToOpportunityCount(
            final List< AgentAssignmentLog > logList ) {
        final Map< Integer, List< AgentAssignmentLog > > countAgentMap = new TreeMap<>(
                ( i1, i2 ) -> i1.compareTo( i2 ) );
        logList.forEach( log -> {
            final Integer key = log.getNumberOfOppsInThresholdPeriod();
            if (countAgentMap.get( key ) == null) {
                countAgentMap.put( key, new LinkedList<>() );
            }
            countAgentMap.get( key ).add( log );
        } );
        LOGGER.info( "Agents scores: " + countAgentMap.keySet() );
        LOGGER.info( "countAgentMap: " + countAgentMap.entrySet() );
        return countAgentMap;
    }

    /**
     * Apply minimum opps filter.
     *
     * @param agentAssignmentLogs
     *            the agent assignment logs
     */
    private void applyMinimumOppsFilter( final List< AgentAssignmentLog > agentAssignmentLogs ) {
        LOGGER.info( "applying minimum opportunities filter to hungry agents" );
        agentAssignmentLogs.stream()
                .filter( log -> log.getAssignmentStatus().equals( HUNGRY_CONSIDERED.name().toLowerCase() ) )
                .forEach( log -> {
                    if (log.getNumberOfOppsInThresholdPeriod() >= hungryAgentsConfig
                            .getHungryAgentsOppCountThreshold()) {
                        log.setAssignmentStatus( HUNGRY_EXCLUDED.name().toLowerCase() );
                        log.setReason( AGENT_HAS_MORE_THAN_THRESHOLD_NO_OF_OPPORTUNITIES );
                    }
                } );
    }

    /**
     * Gets the most eligible agent.
     *
     * @param opportunitySource
     *            the opportunity source
     * @param toAutoAssign
     *            the to auto assign
     * @param zipCode
     *            the zip code
     * @return the most eligible agent
     */
    @Override
    @Transactional( propagation = REQUIRES_NEW )
    public Map< String, Object > getMostEligibleAgent( final OpportunitySource opportunitySource,
            final boolean toAutoAssign, final String zipCode, final String state ) {
        final String crmOpportunityId = opportunitySource.getCrmId();
        LOGGER.info( "Finding best agent for: " + crmOpportunityId );
        final String zip = StringUtils.isNotBlank( zipCode ) ? zipCode : opportunitySource.getPropertyZip();
        final String stateToConsider = StringUtils.isNotBlank( state ) ? state : opportunitySource.getPropertyState();

        final List< Agent > bestFitAgents = new ArrayList<>();

        // 1. SCurve allocation logic
        if (isEligibleForSCurveAllocation( opportunitySource.getOpportunityType() )) {
            try {
                final List< Agent > sCurveAgents = getSCurveAgents( zip, opportunitySource.getPriceRange(),
                        opportunitySource, toAutoAssign, stateToConsider );
                if (CollectionUtils.isNotEmpty( sCurveAgents )) {
                    bestFitAgents.addAll( sCurveAgents );
                }
            } catch ( final Exception e ) {
                LOGGER.error( "#### Exception occurred while getting best agent from SCURVE #### ", e );
            }
        }

        // 2. Hungry agents logic
        Agent mostEligibleAgent = null;
        if (bestFitAgents.isEmpty() && hungryAgentsConfig.isHungryAgentsEnabled()) {
            mostEligibleAgent = getMostEligibleHungryAgent( opportunitySource, toAutoAssign, zip );
        }

        // 3. Happy agents logic
        boolean areAgentsServingToZip = true;
        if (bestFitAgents.isEmpty() && mostEligibleAgent == null) {
            final Map< String, Object > map = getMostEligibleHappyAgent( zip, crmOpportunityId, opportunitySource,
                    toAutoAssign );
            mostEligibleAgent = ( Agent ) map.get( MOST_ELIGIBLE_HAPPY_AGENT );
            areAgentsServingToZip = ( boolean ) map.get( ARE_AGENTS_SERVING_TO_ZIP );
        }

        final Map< String, Object > response = buildResponse( zip, bestFitAgents, mostEligibleAgent,
                areAgentsServingToZip );
        return response;
    }

    /**
     * Checks if is eligible for S curve allocation.
     *
     * @param opportunityType
     *            the opportunity type
     * @return true, if is eligible for S curve allocation
     */
    private boolean isEligibleForSCurveAllocation( final String opportunityType ) {
        return sCurveConfig.isScurveAllocationEnabled() && BUYER.getType().equalsIgnoreCase( opportunityType );
    }

    /**
     * Builds the response.
     *
     * @param zip
     *            the zip
     * @param bestFitAgents
     *            the best fit agents
     * @param mostEligibleAgent
     *            the most eligible agent
     * @param areAgentsServingToZip
     *            the are agents serving to zip
     * @return the map
     */
    private Map< String, Object > buildResponse( final String zip, final List< Agent > bestFitAgents,
            final Agent mostEligibleAgent, final boolean areAgentsServingToZip ) {
        final Map< String, Object > response = new HashMap< String, Object >();
        if (bestFitAgents.isEmpty()) {
            if (mostEligibleAgent != null) {
                LOGGER.info( "mostEligibleAgent is : " + mostEligibleAgent.getEmail() );
                bestFitAgents.add( mostEligibleAgent );
            } else if (!areAgentsServingToZip) {
                response.put( "reasonForRefEx", String.format( noCoverageMessage, zip ) );
            } else {
                response.put( "reasonForRefEx", String.format( allAgentsBusyMessage, zip ) );
            }
        }
        final boolean hasMoreThan1Agent = bestFitAgents.size() > 1;
        response.put( BEST_FIT_AGENTS, bestFitAgents );
        response.put( IS_HAPPY_AGENT_ENABLED, hasMoreThan1Agent );
        response.put( IS_HUNGRY_AGENT_ENABLED, hasMoreThan1Agent );
        return response;
    }

    /**
     * Gets the most eligible happy agent.
     *
     * @param zip
     *            the zip
     * @param crmOpportunityId
     *            the crm opportunity id
     * @param opportunitySource
     *            the opportunity source
     * @param toAutoAssign
     *            the to auto assign
     * @return the most eligible happy agent
     */
    private Map< String, Object > getMostEligibleHappyAgent( final String zip, final String crmOpportunityId,
            final OpportunitySource opportunitySource, final boolean toAutoAssign ) {
        final Map< String, Object > map = new HashMap<>();
        map.put( MOST_ELIGIBLE_HAPPY_AGENT, null );
        map.put( ARE_AGENTS_SERVING_TO_ZIP, true );

        final Set< AgentAssignmentLogDto > agentAssignmentLogDtos = populateAgentAssignmentDtos( zip, null );
        final List< AgentAssignmentLog > agentAssignmentLogs = getAgentAssignmenLogs( crmOpportunityId,
                agentAssignmentLogDtos, false );

        final List< String > allAgentEmails = agentAssignmentLogs.stream().map( log -> log.getAgentEmail() )
                .collect( Collectors.toList() );
        LOGGER.info( "Happy agents initially considered: " + allAgentEmails );

        if (CollectionUtils.isNotEmpty( agentAssignmentLogs )) {
            applyInitialFilters( agentAssignmentLogs, allAgentEmails, crmOpportunityId,
                    opportunitySource.getRecordType(), false );

            final Map< String, List< Integer > > cbsaThresholdsMap = getCbsaThresholdsMap( agentAssignmentLogs );
            AgentAssignmentLog eligibleAgent = filterByMinimumOpps( agentAssignmentLogs, cbsaThresholdsMap );
            if (eligibleAgent == null) {
                eligibleAgent = filterByLevels( agentAssignmentLogs, cbsaThresholdsMap );
            } else {
                updateExclusionReason( agentAssignmentLogs, eligibleAgent,
                        REASON_ASSIGNED_TO_OTHER_AGENT_HAVING_LESS_THAN_MIN_OPPS );
            }

            final Agent mostEligibleHappyAgent = getEligibleAgent( opportunitySource, toAutoAssign, zip,
                    agentAssignmentLogs, eligibleAgent, false );
            map.put( MOST_ELIGIBLE_HAPPY_AGENT, mostEligibleHappyAgent );
            agentAssignmentLogService.saveAll( agentAssignmentLogs );
        } else {
            map.put( ARE_AGENTS_SERVING_TO_ZIP, false );
            referToRefExchg( opportunitySource, zip, NO_AGENT_AVAILABLE_SUFFIX, toAutoAssign );
        }
        return map;
    }

    /**
     * get the eligible agent.
     *
     * @param opportunitySource
     *            the opportunity source
     * @param toAutoAssign
     *            the to auto assign
     * @param zip
     *            the zip
     * @param agentAssignmentLogs
     *            the agent assignment logs
     * @param agentAssignmentLog
     *            the eligible agent
     * @param isHungryAgentLookup
     *            the is hungry agent lookup
     * @return the agent
     */
    private Agent getEligibleAgent( final OpportunitySource opportunitySource, final boolean toAutoAssign,
            final String zip, final List< AgentAssignmentLog > agentAssignmentLogs,
            final AgentAssignmentLog agentAssignmentLog, final boolean isHungryAgentLookup ) {
        Agent agent = null;
        final String exclusionStatus = isHungryAgentLookup ? HUNGRY_EXCLUDED.name().toLowerCase() : excluded.name();
        final String autoAssignedStatus = isHungryAgentLookup ? HUNGRY_AUTO_ASSIGNED.name().toLowerCase()
                : auto_assigned.name();
        final String displayedStatus = isHungryAgentLookup ? HUNGRY_DISPLAYED.name().toLowerCase() : displayed.name();

        if (agentAssignmentLog == null) {
            agentAssignmentLogs.stream().filter( log -> log.getAssignmentStatus().equals( considered.name() ) ).forEach(
                    log -> updateStatusAndReason( log, exclusionStatus, REASON_AGENT_EXCEED_ALL_THRESHOLDS ) );
            referToRefExchg( opportunitySource, zip, EMPTY, toAutoAssign );
        } else {
            LOGGER.info( "The most eligible agent is: " + agentAssignmentLog.getAgentEmail() );
            if (toAutoAssign) {
                opportunityBusinessService.assignOpportunityToAgent( opportunitySource,
                        agentAssignmentLog.getAgentEmail() );
                updateStatusAndReason( agentAssignmentLog, autoAssignedStatus, null );
                if (isHungryAgentLookup) {
                    agent = agentDtoBuilder.convertTo( agentAssignmentLog.getAgentEmail() );
                }
            } else {
                updateStatusAndReason( agentAssignmentLog, displayedStatus, null );
                agent = agentDtoBuilder.convertTo( agentAssignmentLog.getAgentEmail() );
            }
        }
        return agent;
    }

    /**
     * Refer to ref exchg.
     *
     * @param opportunitySource
     *            the opportunity source
     * @param zip
     *            the zip
     * @param reason
     *            the reason
     * @param toAutoAssign
     *            the to auto assign
     */
    private void referToRefExchg( final OpportunitySource opportunitySource, final String zip, final String reason,
            final boolean toAutoAssign ) {
        LOGGER.info( "No eligible agents found. Forwarding to referral ex." );
        final String crmOpportunityId = opportunitySource.getCrmId();
        final OwnersMarketCbsaLabel ownersMarketCbsaLabel = ownersMarketCbsaLabelService.findByZip( zip );
        final AgentAssignmentLog log = new AgentAssignmentLog();
        log.setAgentEmail( refExAgentEmail );
        log.setCrmOpportunityId( crmOpportunityId );
        log.setZip( zip );
        if (ownersMarketCbsaLabel != null) {
            log.setCbsaCode( ownersMarketCbsaLabel.getId() );
            log.setOwnersMarketLabel( ownersMarketCbsaLabel.getOwnersMarketLabel() );
        } else {
            log.setCbsaCode( DEFAULT_CBSA );
            log.setOwnersMarketLabel( DEFAULT_CBSA );
        }
        log.setAgentScore( 0 );
        log.setAgentType( GOOD.name() );
        log.setNumberOfOppsInThresholdPeriod( 0 );
        updateStatusAndReason( log, AssignmentStatus.displayed_for_referral.name(),
                REASON_DISPLAYED_FOR_REFERRAL + reason );
        if (toAutoAssign) {
            updateStatusAndReason( log, referred.name(), REASON_REFERRED_TO_REF_EX + reason );
            opportunityBusinessService.forwardToReferralExchange( opportunitySource );
        }
        agentAssignmentLogService.save( log );
    }

    /**
     * Filter by levels.
     *
     * @param agentAssignmentLogs
     *            the agent assignment logs
     * @param cbsaThresholdsMap
     *            the cbsa thresholds map
     * @return the agent assignment log
     */
    private AgentAssignmentLog filterByLevels( final List< AgentAssignmentLog > agentAssignmentLogs,
            final Map< String, List< Integer > > cbsaThresholdsMap ) {
        LOGGER.info( "Filtering by levels" );

        // level 2
        final List< AgentAssignmentLog > goodAgentsHavingLessThanLevel2Max = getAgentsByLevelThresholdAndAgentType(
                agentAssignmentLogs, cbsaThresholdsMap, GOOD.name(), 2 );
        AgentAssignmentLog eligibleAgent = setAgentPriority( goodAgentsHavingLessThanLevel2Max );

        // level 1
        if (eligibleAgent == null) {
            final List< AgentAssignmentLog > newAgentsHavingLessThanLevel1Max = getAgentsByLevelThresholdAndAgentType(
                    agentAssignmentLogs, cbsaThresholdsMap, NEW.name(), 4 );
            eligibleAgent = setAgentPriority( newAgentsHavingLessThanLevel1Max );
            if (eligibleAgent == null) {
                final List< AgentAssignmentLog > goodAgentsHavingLessThanLevel1Max = getAgentsByLevelThresholdAndAgentType(
                        agentAssignmentLogs, cbsaThresholdsMap, GOOD.name(), 3 );
                eligibleAgent = setAgentPriority( goodAgentsHavingLessThanLevel1Max );
                if (eligibleAgent == null) {
                    final List< AgentAssignmentLog > averageAgentsHavingLessThanLevel1Max = getAgentsByLevelThresholdAndAgentType(
                            agentAssignmentLogs, cbsaThresholdsMap, AVERAGE.name(), 5 );
                    eligibleAgent = setAgentPriority( averageAgentsHavingLessThanLevel1Max );
                    if (eligibleAgent != null) {
                        updateExclusionReason( agentAssignmentLogs, eligibleAgent,
                                REASON_ASSIGNED_TO_OTHER_AVERAGE_AGENT_HAVING_LESS_LEVEL1_MAX_OPPS );
                    }
                } else {
                    updateExclusionReason( agentAssignmentLogs, eligibleAgent,
                            REASON_ASSIGNED_TO_OTHER_GOOD_AGENT_HAVING_LESS_LEVEL1_MAX_OPPS );
                }
            } else {
                updateExclusionReason( agentAssignmentLogs, eligibleAgent,
                        REASON_ASSIGNED_TO_OTHER_NEW_AGENT_HAVING_LESS_LEVEL1_MAX_OPPS );
            }
        } else {
            updateExclusionReason( agentAssignmentLogs, eligibleAgent,
                    REASON_ASSIGNED_TO_OTHER_GOOD_AGENT_HAVING_LESS_LEVEL2_MAX_OPPS );
        }

        return eligibleAgent;
    }

    /**
     * Apply initial filters.
     *
     * @param agentAssignmentLogs
     *            the agent assignment logs
     * @param allAgentEmails
     *            the all agent emails
     * @param crmOpportunityId
     *            the crm opportunity id
     * @param recordType
     *            the record type
     * @param isHungryAgentLookup
     *            the is hungry agent lookup
     */
    private void applyInitialFilters( final List< AgentAssignmentLog > agentAssignmentLogs,
            final List< String > allAgentEmails, final String crmOpportunityId, final String recordType,
            final boolean isHungryAgentLookup ) {
        final List< AgentDetails > agentDetailsList = agentDetailsService.findByEmailsIn( allAgentEmails );
        final String currentlyAssignedAgentEmail = agentService.findAgentByCrmOpportunityId( crmOpportunityId );
        final List< String > selectedStages = getSelectedStages( recordType );
        final Set< String > emailsBySelStages = new HashSet<>();
        if (!isHungryAgentLookup) {
            emailsBySelStages.addAll( getEmailsBySelectedStages( agentAssignmentLogs, selectedStages ) );
        }

        // sort both on emails so that they are at the same indexes
        agentAssignmentLogs.sort( ( log1, log2 ) -> log1.getAgentEmail().compareTo( log2.getAgentEmail() ) );
        agentDetailsList.sort( ( ad1, ad2 ) -> ad1.getUser().getEmail().compareTo( ad2.getUser().getEmail() ) );

        final String exclusionStatus = isHungryAgentLookup ? HUNGRY_EXCLUDED.name().toLowerCase() : excluded.name();
        for ( int i = 0; i < agentAssignmentLogs.size(); i++ ) {
            final AgentAssignmentLog log = agentAssignmentLogs.get( i );
            final AgentDetails ad = agentDetailsList.get( i );
            final String agentEmail = ad.getUser().getEmail();
            if (agentEmail.equals( currentlyAssignedAgentEmail )) {
                updateStatusAndReason( log, exclusionStatus, REASON_CURRENTLY_ASSIGNED_AGENT );
            } else if (emailsBySelStages.contains( agentEmail )) {
                updateStatusAndReason( log, exclusionStatus, REASON_SELECTED_STAGES );
            } else {
                final String currentStatus = ad.getUser().getStatus();
                if (ONBOARDING.getStatus().equals( currentStatus )) {
                    updateStatusAndReason( log, exclusionStatus, REASON_AGENT_STATUS_IS_ONBOARDING );
                } else if (HOLD.getStatus().equals( currentStatus )) {
                    updateStatusAndReason( log, exclusionStatus, REASON_AGENT_STATUS_IS_HOLD );
                } else if (INACTIVE.getStatus().equals( currentStatus )) {
                    updateStatusAndReason( log, exclusionStatus, REASON_AGENT_STATUS_IS_INACTIVE );
                } else if (!ad.isAvailability()) {
                    updateStatusAndReason( log, exclusionStatus, REASON_AGENT_IS_UNAVAILABLE );
                }
            }
        }
    }

    /**
     * Gets the emails by selected stages.
     *
     * @param agentAssignmentLogs
     *            the agent assignment logs
     * @param selectedStages
     *            the selected stages
     * @return the emails by selected stages
     */
    private Set< String > getEmailsBySelectedStages( final List< AgentAssignmentLog > agentAssignmentLogs,
            final List< String > selectedStages ) {
        final Set< String > emailsBySelStages = new HashSet<>();
        if (CollectionUtils.isNotEmpty( selectedStages )) {
            final List< String > nonDefaultAgentEmails = new LinkedList<>();
            final List< String > defaultAgentEmails = new LinkedList<>();
            agentAssignmentLogs.forEach( ag -> {
                if (DEFAULT_CBSA.equalsIgnoreCase( ag.getCbsaCode() )) {
                    defaultAgentEmails.add( ag.getAgentEmail() );
                } else {
                    nonDefaultAgentEmails.add( ag.getAgentEmail() );
                }
            } );
            if (CollectionUtils.isNotEmpty( nonDefaultAgentEmails )) {
                final List< String > temp = opportunityService
                        .findAssignedAgentEmailsBySelectedStagesCount( selectedStages, nonDefaultAgentEmails );
                emailsBySelStages.addAll( temp );
            }
            if (CollectionUtils.isNotEmpty( defaultAgentEmails )) {
                final List< String > temp2 = opportunityService
                        .findAssignedAgentEmailsBySelectedStagesCountForDefault( selectedStages, defaultAgentEmails );
                emailsBySelStages.addAll( temp2 );
            }
        }
        return emailsBySelStages;
    }

    /**
     * Gets the selected stages.
     *
     * @param recordType
     *            the record type
     * @return the selected stages
     */
    private List< String > getSelectedStages( final String recordType ) {
        final List< String > selStages = new LinkedList<>();
        final String stagesProp = BUYER.getType().equalsIgnoreCase( recordType )
                ? happyAgentsConfig.getSelectedBuyerStages() : happyAgentsConfig.getSelectedSellerStages();
        LOGGER.info( "Selected stages for: " + recordType + " record type are: " + stagesProp );
        final String[] stages = stagesProp.split( COMMA );
        if (stages != null) {
            Stream.of( stages ).forEach( s -> selStages.add( s.trim() ) );
        }
        return selStages;
    }

    /**
     * Update exclusion reason.
     *
     * @param agentAssignmentLogs
     *            the agent assignment logs
     * @param eligibleAgent
     *            the eligible agent
     * @param reason
     *            the reason
     */
    private void updateExclusionReason( final List< AgentAssignmentLog > agentAssignmentLogs,
            final AgentAssignmentLog eligibleAgent, final String reason ) {
        agentAssignmentLogs.stream()
                .filter( log -> log.getAssignmentStatus().equals( considered.name() )
                        && !log.getAgentEmail().equals( eligibleAgent.getAgentEmail() ) )
                .forEach( log -> updateStatusAndReason( log, excluded.name(), reason ) );
    }

    /**
     * Populate agent assignment dtos.
     *
     * @param zip
     *            the zip
     * @param hungryAgentBucketEmails
     *            the hungry agent bucket emails
     * @return the list
     */
    private Set< AgentAssignmentLogDto > populateAgentAssignmentDtos( final String zip,
            final List< String > hungryAgentBucketEmails ) {
        List< AgentAssignmentLogDto > agentAssignmentLogDtos = null;
        if (CollectionUtils.isNotEmpty( hungryAgentBucketEmails )) {
            agentAssignmentLogDtos = agentAssignmentLogService.findByPropertyZipAndEmailsIn( zip,
                    hungryAgentBucketEmails );
        } else {
            agentAssignmentLogDtos = agentAssignmentLogService.findByPropertyZip( zip );
        }
        final List< AgentAssignmentLogDto > defaultAgentsAssignmentLogDtos = generateDefaultAgentsAssignmentLogDtos(
                zip, agentAssignmentLogDtos, hungryAgentBucketEmails );
        agentAssignmentLogDtos.addAll( defaultAgentsAssignmentLogDtos );
        return new HashSet<>( agentAssignmentLogDtos );
    }

    /**
     * Generate default agents assignment log dtos.
     *
     * @param propertyZipCode
     *            the interested zip codes
     * @param agentAssignmentLogDtos
     *            the agent assignment log dtos
     * @param hungryAgentBucketEmails
     *            the hungry agent bucket emails
     * @return the list
     */
    private List< AgentAssignmentLogDto > generateDefaultAgentsAssignmentLogDtos( final String propertyZipCode,
            final List< AgentAssignmentLogDto > agentAssignmentLogDtos, final List< String > hungryAgentBucketEmails ) {
        final List< String > agentEmails = agentAssignmentLogDtos.stream().map( dto -> dto.getAgentEmail() )
                .collect( Collectors.toList() );
        List< Object[] > zipAgentsList = null;
        if (CollectionUtils.isNotEmpty( hungryAgentBucketEmails )) {
            zipAgentsList = agentService.findByZipAndAgentEmails( propertyZipCode, hungryAgentBucketEmails );
        } else {
            zipAgentsList = agentService.findZipAndAgentsByZip( propertyZipCode );
        }

        final List< AgentAssignmentLogDto > retVal = new LinkedList<>();
        zipAgentsList.stream().filter( za -> !agentEmails.contains( za[1].toString() ) ).forEach( za1 -> {
            final BigDecimal score = ( BigDecimal ) za1[2];
            retVal.add( new AgentAssignmentLogDto( za1[1].toString(), score.doubleValue() * 100, za1[0].toString(),
                    DEFAULT_CBSA, DEFAULT_CBSA ) );
        } );
        return retVal;
    }

    /**
     * Gets the agents by level threshold and agent type.
     *
     * @param agentAssignmentLogs
     *            the agent assignment logs
     * @param cbsaThresholdsMap
     *            the cbsa thresholds map
     * @param agentType
     *            the agent type
     * @param thresholdIndex
     *            the threshold index
     * @return the agents by level threshold and agent type
     */
    private List< AgentAssignmentLog > getAgentsByLevelThresholdAndAgentType(
            final List< AgentAssignmentLog > agentAssignmentLogs,
            final Map< String, List< Integer > > cbsaThresholdsMap, final String agentType, final int thresholdIndex ) {
        final List< AgentAssignmentLog > agentsHavingLessThanThresholdOpps = agentAssignmentLogs.stream()
                .filter( log -> ( log.getAgentType().equalsIgnoreCase( agentType )
                        && log.getAssignmentStatus().equals( considered.name() ) ) )
                .filter( log -> log.getNumberOfOppsInThresholdPeriod() < cbsaThresholdsMap.get( log.getCbsaCode() )
                        .get( thresholdIndex ) )
                .collect( Collectors.toList() );
        return agentsHavingLessThanThresholdOpps;
    }

    /**
     * Gets the cbsa thresholds map.
     *
     * @param agentAssignmentLogs
     *            the agent assignment logs
     * @return the cbsa thresholds map
     */
    private Map< String, List< Integer > > getCbsaThresholdsMap(
            final List< AgentAssignmentLog > agentAssignmentLogs ) {
        final List< String > cbsaCodes = agentAssignmentLogs.stream().map( log -> log.getCbsaCode() )
                .collect( Collectors.toList() );
        final List< CbsaMarketLevel > cbsaMarketLevels = cbsaMarketLevelService.findByCbsaCodes( cbsaCodes );
        final Map< String, List< Integer > > cbsaThresholdsMap = new HashMap<>();
        cbsaMarketLevels.forEach( level -> {
            final List< Integer > cbsaParams = Arrays.asList( level.getMinOpportunities(), level.getRrThreshold(),
                    level.getLevel2MaxGood(), level.getLevel1MaxGood(), level.getLevel1MaxNew(),
                    level.getLevel1MaxAverage() );
            cbsaThresholdsMap.put( level.getOwnersMarketCbsaLabel().getId(), cbsaParams );
        } );
        return cbsaThresholdsMap;
    }

    /**
     * Filter by minimum opps.
     *
     * @param agentAssignmentLogs
     *            the agent assignment logs
     * @param cbsaThresholdsMap
     *            the cbsa thresholds map
     * @return the agent assignment log
     */
    private AgentAssignmentLog filterByMinimumOpps( final List< AgentAssignmentLog > agentAssignmentLogs,
            final Map< String, List< Integer > > cbsaThresholdsMap ) {
        AgentAssignmentLog retVal = null;
        final List< AgentAssignmentLog > agentsHavingLessThanMinOpps = getAgentsByThreshold( agentAssignmentLogs,
                cbsaThresholdsMap, 0 );
        if (CollectionUtils.isNotEmpty( agentsHavingLessThanMinOpps )) {
            final List< AgentAssignmentLog > agentsHavingLessThanRR = getAgentsByThreshold( agentsHavingLessThanMinOpps,
                    cbsaThresholdsMap, 1 );
            if (CollectionUtils.isNotEmpty( agentsHavingLessThanRR )) {
                LOGGER.info( "Number of agents having less than RR threshold opps: " + agentsHavingLessThanRR.size() );
                retVal = getAgentHavingLessThanThresholdOppsByType( agentsHavingLessThanRR );
            } else {
                LOGGER.info( "Number of agents having less than minumum num of opps: "
                        + agentsHavingLessThanMinOpps.size() );
                retVal = getAgentHavingLessThanThresholdOppsByType( agentsHavingLessThanMinOpps );
            }
        }
        return retVal;
    }

    /**
     * Gets the agent having less than threshold opps by type.
     *
     * @param list
     *            the list
     * @return the agent having less than threshold opps by type
     */
    private AgentAssignmentLog getAgentHavingLessThanThresholdOppsByType( final List< AgentAssignmentLog > list ) {
        AgentAssignmentLog retVal = null;
        final Map< String, List< AgentAssignmentLog > > agentTypeLogMap = new HashMap<>();
        list.forEach( log -> {
            if (agentTypeLogMap.get( log.getAgentType() ) == null) {
                agentTypeLogMap.put( log.getAgentType(), new LinkedList<>() );
            }
            agentTypeLogMap.get( log.getAgentType() ).add( log );
        } );

        retVal = setAgentPriority( agentTypeLogMap.get( GOOD.name().toLowerCase() ) );
        if (retVal == null) {
            retVal = setAgentPriority( agentTypeLogMap.get( NEW.name().toLowerCase() ) );
            if (retVal == null) {
                retVal = setAgentPriority( agentTypeLogMap.get( AVERAGE.name().toLowerCase() ) );
            }
        }
        return retVal;
    }

    /**
     * Sets the agent priority.
     *
     * @param logList
     *            the log list
     * @return the agent assignment log
     */
    private AgentAssignmentLog setAgentPriority( final List< AgentAssignmentLog > logList ) {
        AgentAssignmentLog retVal = null;
        if (CollectionUtils.isNotEmpty( logList )) {
            retVal = getHighestScoringAgent( logList );
            if (retVal != null) {
                retVal.setPriority( logList.size() );
                logList.remove( retVal );
                sort( logList );
                for ( int i = 0; i < logList.size(); i++ ) {
                    logList.get( i ).setPriority( logList.size() - i );
                }
            }
        }
        return retVal;
    }

    /**
     * Sort.
     *
     * @param logs
     *            the logs
     */
    private void sort( final List< AgentAssignmentLog > logs ) {
        logs.sort( ( o1, o2 ) -> {
            int retVal = 0;
            if (o1.getAgentScore() < o2.getAgentScore()) {
                retVal = 1;
            } else if (o1.getAgentScore() > o2.getAgentScore()) {
                retVal = -1;
            } else {
                if (o1.getNumberOfOppsInThresholdPeriod() > o2.getNumberOfOppsInThresholdPeriod()) {
                    retVal = 1;
                } else if (o1.getNumberOfOppsInThresholdPeriod() < o2.getNumberOfOppsInThresholdPeriod()) {
                    retVal = -1;
                } else {
                    final List< AgentAssignmentLog > temp = Arrays.asList( new AgentAssignmentLog[] { o1, o2 } );
                    final AgentAssignmentLog agentWithOldestAssignedDate = getAgentWithOldestAssignedDate( temp );
                    retVal = agentWithOldestAssignedDate == o1 ? 1 : -1;
                }
            }
            return retVal;
        } );
    }

    /**
     * Gets the agents by threshold.
     *
     * @param agentAssignmentLogs
     *            the agent assignment logs
     * @param cbsaThresholdsMap
     *            the cbsa thresholds map
     * @param thresholdIndex
     *            the threshold index
     * @return the agents by threshold
     */
    private List< AgentAssignmentLog > getAgentsByThreshold( final List< AgentAssignmentLog > agentAssignmentLogs,
            final Map< String, List< Integer > > cbsaThresholdsMap, final int thresholdIndex ) {
        final List< AgentAssignmentLog > list = agentAssignmentLogs.stream().filter( log -> {
            final List< Integer > thresholds = cbsaThresholdsMap.get( log.getCbsaCode() );
            return log.getAssignmentStatus().equals( considered.name() )
                    && log.getNumberOfOppsInThresholdPeriod() < thresholds.get( thresholdIndex );
        } ).collect( Collectors.toList() );
        return list;
    }

    /**
     * Gets the highest scoring agent. If 2 or more agents have equal score,
     * check
     * for recently assigned opportunity time.
     *
     * @param logList
     *            the agents
     * @return the highest scoring agent
     */
    private AgentAssignmentLog getHighestScoringAgent( final List< AgentAssignmentLog > logList ) {
        LOGGER.info( "logList.size() = " + logList.size() );
        AgentAssignmentLog assignmentLog = null;
        if (logList.size() == 1) {
            assignmentLog = logList.iterator().next();
        } else if (logList.size() > 0) {
            final Map< Double, List< AgentAssignmentLog > > scoreAgentMap = mapLogsToScore( logList );
            final List< AgentAssignmentLog > highestScoringAgents = scoreAgentMap.entrySet().iterator().next()
                    .getValue();
            LOGGER.info( "highestScoringAgents.size() = " + highestScoringAgents.size() );
            if (highestScoringAgents.size() == 1) {
                assignmentLog = highestScoringAgents.iterator().next();
            } else if (highestScoringAgents.size() > 0) {
                LOGGER.info( "More than 1 agent have highest score: "
                        + scoreAgentMap.entrySet().iterator().next().getKey() );
                assignmentLog = getAgentWithOldestAssignedDate( highestScoringAgents );
            }
        }
        return assignmentLog;
    }

    /**
     * Map logs to descending order of score.
     *
     * @param logList
     *            the agents
     * @return the map
     */
    private Map< Double, List< AgentAssignmentLog > > mapLogsToScore( final List< AgentAssignmentLog > logList ) {
        final Map< Double, List< AgentAssignmentLog > > scoreAgentMap = new TreeMap<>(
                ( d1, d2 ) -> d2.compareTo( d1 ) );
        logList.forEach( log -> {
            final Double key = log.getAgentScore();
            if (scoreAgentMap.get( key ) == null) {
                scoreAgentMap.put( key, new LinkedList<>() );
            }
            scoreAgentMap.get( key ).add( log );
        } );
        LOGGER.info( "Agents scores: " + scoreAgentMap.keySet() );
        LOGGER.info( "scoreAgentMap: " + scoreAgentMap.entrySet() );
        return scoreAgentMap;
    }

    /**
     * Gets the agent with oldest assigned date. If all the agents have same
     * oldest
     * date or no opportunities assigned to them then agents are sorted in the
     * ascending order of their emails.
     *
     * @param logList
     *            the log list
     * @return the agent with oldest assigned date
     */
    private AgentAssignmentLog getAgentWithOldestAssignedDate( final List< AgentAssignmentLog > logList ) {
        AgentAssignmentLog retVal = null;
        final List< String > agentEmails = logList.stream().map( log -> log.getAgentEmail() )
                .collect( Collectors.toList() );
        final String agentEmail = agentAssignmentLogService.findAgentEmailByLeastOppAssignedDate( agentEmails );
        if (StringUtils.isNotBlank( agentEmail )) {
            final List< AgentAssignmentLog > eligibleAgentList = logList.stream()
                    .filter( log -> log.getAgentEmail().equals( agentEmail ) ).collect( Collectors.toList() );
            retVal = eligibleAgentList.size() > 0 ? eligibleAgentList.get( 0 ) : null;
        } else {
            logList.sort( ( o1, o2 ) -> o1.getAgentEmail().compareTo( o2.getAgentEmail() ) );
            retVal = logList.get( 0 );
        }
        return retVal;
    }

    /**
     * Update status and reason.
     *
     * @param log
     *            the log
     * @param status
     *            the status
     * @param reason
     *            the reason
     */
    private void updateStatusAndReason( final AgentAssignmentLog log, final String status, final String reason ) {
        log.setAssignmentStatus( status );
        log.setReason( reason );
    }

    /**
     * Gets the agent assignmen logs.
     *
     * @param crmOpportunityId
     *            the crm opportunity id
     * @param agentAssignmentLogDtos
     *            the agent assignment log dtos
     * @param isHungryAgentLookup
     *            the is hungry agent lookup
     * @return the agent assignmen logs
     */
    private List< AgentAssignmentLog > getAgentAssignmenLogs( final String crmOpportunityId,
            final Collection< AgentAssignmentLogDto > agentAssignmentLogDtos, final boolean isHungryAgentLookup ) {
        final List< Future< AgentAssignmentLog > > futureList = new LinkedList<>();
        agentAssignmentLogDtos.forEach( dto -> futureList
                .add( agentLookupTask.buildAssignmentLog( dto, crmOpportunityId, isHungryAgentLookup ) ) );
        final List< AgentAssignmentLog > list = new ArrayList<>();
        for ( final Future< AgentAssignmentLog > future : futureList ) {
            try {
                list.add( future.get() );
            } catch ( InterruptedException | ExecutionException e ) {
                throw new ApplicationException( e.getMessage(), e );
            }
        }
        return list;
    }
}
