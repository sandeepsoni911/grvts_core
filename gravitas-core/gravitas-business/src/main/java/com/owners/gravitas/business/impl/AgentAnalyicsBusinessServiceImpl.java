package com.owners.gravitas.business.impl;

import static com.owners.gravitas.enums.JobType.OPPORTUNITY_MAPPING_JOB;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.api.services.bigquery.model.TableDataInsertAllRequest.Rows;
import com.owners.gravitas.business.AgentAnalyicsBusinessService;
import com.owners.gravitas.dao.AgentOpportunityDao;
import com.owners.gravitas.domain.entity.SchedulerLog;
import com.owners.gravitas.service.AgentService;
import com.owners.gravitas.service.SchedulerLogService;

/**
 * The Class AgentAnalyicsBusinessServiceImpl performs below operation in to
 * google bigquery.
 * Create the agent analytics for agent UID with respective agent email.
 * Create the agent analytics for all opportunities with respective CRM ID and
 * firebase Id.
 *
 * @author vishwanathm
 */
@Service
@Transactional( readOnly = true )
public class AgentAnalyicsBusinessServiceImpl implements AgentAnalyicsBusinessService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentAnalyicsBusinessServiceImpl.class );

    /** The Constant AGENT_FB_ID. */
    private static final String AGENT_FB_ID = "Agent_FB_Id";

    /** The Constant AGENT_EMAIL_ID. */
    private static final String AGENT_EMAIL_ID = "Agent_Email_Id";

    /** The Constant FB_ID. */
    private static final String FB_ID = "FB_Id";

    /** The Constant CRM_ID. */
    private static final String CRM_ID = "CRM_Id";

    /** The AgentHolder dao. */
    @Autowired
    private AgentService agentService;

    /** The scheduler log service. */
    @Autowired
    private SchedulerLogService schedulerLogService;

    /** The agent opportunity dao. */
    @Autowired
    private AgentOpportunityDao agentOpportunityDao;

    /**
     * Start agent uid analytics.
     * Retrieve all agents from firebase and compare with those in google
     * bigquery, then compute the records that does not in google bigquery and
     * insert those records.
     *
     * @throws IOException
     */
    @Override
    public void startAgentAnalytics() {
        LOGGER.debug( "Agent analytics started" );
        final Map< String, String > linkedAgents = agentService.getAllMappedAgentIds();
        final Set< String > allAgentsIds = agentService.getAllAgentIds();
        final List< Rows > rowsList = getAgentUidMapping( linkedAgents, allAgentsIds );
        if (CollectionUtils.isNotEmpty( rowsList )) {
            agentService.saveAgentUidMapping( rowsList );
        }
        LOGGER.debug( "Agent analytics completed for " + rowsList.size() + " agent(s)." );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentAnalyicsBusinessService#mapFbCrmOppIds
     * ()
     */
    @Override
    public void opportunityMappingAnalytics() {
        LOGGER.debug( "Opportunity mapping started" );
        final Set< String > allAgentsIds = agentService.getAllAgentIds();
        final Pageable top = new PageRequest( 0, 1 );
        final List< SchedulerLog > schedulerLogList = schedulerLogService
                .findBySchedulerName( OPPORTUNITY_MAPPING_JOB.toString(), top );

        if (isNotEmpty( schedulerLogList )) {
            final SchedulerLog schedulerLog = schedulerLogList.get( 0 );
            final List< Map< String, Map > > opportunitiesMapList = new LinkedList<>();
            final long endTime = schedulerLog.getEndTime().getMillis();
            allAgentsIds.forEach( id -> {
                opportunitiesMapList.add( agentOpportunityDao.getOpportunitiesFromTime( id, endTime ) );
            } );
            final List< Rows > rowsList = new ArrayList<>();
            opportunitiesMapList.forEach( map -> {
                map.entrySet().forEach( entry -> {
                    rowsList.add( getRow( entry ) );
                } );
            } );
            if (CollectionUtils.isNotEmpty( rowsList )) {
                agentService.saveOpportunityMapping( rowsList );
            }
            LOGGER.debug( "Opportunity mapping completed for " + rowsList.size() + " id(s)." );
        }
    }

    /**
     * Gets the row.
     *
     * @param entry
     *            the entry
     * @return the row
     */
    private Rows getRow( Map.Entry< String, Map > entry ) {
        final Map< String, Object > fbCrmIdLinkMap = new HashMap<>();
        fbCrmIdLinkMap.put( FB_ID, entry.getKey() );
        fbCrmIdLinkMap.put( CRM_ID, ( String ) entry.getValue().get( "crmId" ) );
        final Rows rows = new Rows();
        rows.setJson( fbCrmIdLinkMap );
        return rows;
    }

    /**
     * Gets the agent uid mapping, by iterating through the agents those are not
     * present in google bigquery table.
     *
     * @param linkedAgents
     *            the linked agents
     * @param allAgentsIds
     *            the agents
     * @return the insert rows
     */
    private List< Rows > getAgentUidMapping( final Map< String, String > linkedAgents,
            final Set< String > allAgentsIds ) {
        final List< Rows > rowsList = new ArrayList<>();
        Map< String, Object > newAgentUidLinkMap = null;
        Rows rows = null;
        for ( String agentId : allAgentsIds ) {
            if (!linkedAgents.containsKey( agentId )) {
                final String email = agentService.getAgentEmailById( agentId );
                newAgentUidLinkMap = new HashMap<>();
                newAgentUidLinkMap.put( AGENT_EMAIL_ID,
                        com.owners.gravitas.util.StringUtils.removeDoubleQuotes( email ) );
                newAgentUidLinkMap.put( AGENT_FB_ID, agentId );
                rows = new Rows();
                rows.setJson( newAgentUidLinkMap );
                rowsList.add( rows );
            }
        }
        return rowsList;
    }
}
