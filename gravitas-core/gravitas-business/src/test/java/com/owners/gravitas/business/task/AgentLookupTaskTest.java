package com.owners.gravitas.business.task;

import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.AgentDetailsV1;
import com.owners.gravitas.domain.entity.PotentialOpportunityAssignmentLog;
import com.owners.gravitas.dto.Agent;
import com.owners.gravitas.dto.crm.response.CRMOpportunityResponse;
import com.owners.gravitas.service.AgentOpportunityService;
import com.owners.gravitas.service.OpportunityAssignmentLogService;

/**
 * The Class AgentLookupTaskTest.
 */
public class AgentLookupTaskTest extends AbstractBaseMockitoTest {

    /** The agent lookup task. */
    @InjectMocks
    private AgentLookupTask agentLookupTask;

    /** The agent opportunity service. */
    @Mock
    private AgentOpportunityService agentOpportunityService;

    /** The opportunity repository. */
/*    @Mock
    private OpportunityRepository opportunityRepository;*/

    /** The log service. */
    @Mock
    private OpportunityAssignmentLogService logService;

    /**
     * Gets the generic lead request.
     *
     * @return the generic lead request
     */
    @DataProvider( name = "agentList" )
    private Object[][] getGenericLeadRequest() {
        List< AgentDetailsV1 > list = new ArrayList<>();
        AgentDetailsV1 agent1 = new AgentDetailsV1();

        agent1.setEmail( "user1@test.com" );
        list.add( agent1 );
        AgentDetailsV1 agent2 = new AgentDetailsV1();
        agent2.setEmail( "user2@test.com" );
        list.add( agent2 );
        AgentDetailsV1 agent3 = new AgentDetailsV1();
        agent3.setEmail( "user3@test.com" );
        list.add( agent3 );
        return new Object[][] { { list } };
    }

    /**
     * Test opportunity assignment audit.
     *
     * @param list
     *            the list
     */
    @Test( dataProvider = "agentList" )
    void testOpportunityAssignmentAudit( List< AgentDetailsV1 > list ) {
        CRMOpportunityResponse opportunity = new CRMOpportunityResponse();
        opportunity.setSavedId( "0065B000007YFqP" );
        opportunity.setLabel( "bad" );
        opportunity.setScore( "0.0" );

        Map< String, Integer > thresholds = new HashMap< String, Integer >();
        thresholds.put( "bestFitAgentOpportunityThreshold", 2 );
        thresholds.put( "bestFitAgentDayThreshold", 2 );
        thresholds.put( "opportunityRRThreshold", 2 );

        List< Agent > bestFitAgents = new ArrayList<>();
        Agent agent = new Agent();
        agent.setEmail( "user1@test.com" );
        Agent agent2 = new Agent();
        agent2.setEmail( "user2@test.com" );
        bestFitAgents.add( agent );
        bestFitAgents.add( agent2 );

        agentLookupTask.auditOpportunityAssignment( "00000", "GA", opportunity, thresholds, list, bestFitAgents,
                "user" );
        verify( logService ).save( Mockito.any( PotentialOpportunityAssignmentLog.class ) );
    }

    /**
     * Test agent assign audit.
     */
    @Test
    void testAgentAssignAudit() {
        agentLookupTask.updateAgentAssignmentAudit( "0065B000007YFqPQWE", "user1@test.com", "user" );
        verify( logService ).updateAgentAssignmentAudit( "0065B000007YFqP", "user1@test.com", "user" );
    }

}
