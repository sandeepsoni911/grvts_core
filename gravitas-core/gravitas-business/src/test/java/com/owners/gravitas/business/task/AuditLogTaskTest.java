package com.owners.gravitas.business.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.business.AuditTrailBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.Action;
import com.owners.gravitas.domain.ActionGroup;
import com.owners.gravitas.domain.AgentInfo;
import com.owners.gravitas.domain.LastViewed;
import com.owners.gravitas.dto.request.LastViewedRequest;
import com.owners.gravitas.service.ActionGroupService;
import com.owners.gravitas.service.AgentInfoService;

public class AuditLogTaskTest extends AbstractBaseMockitoTest {

    @InjectMocks
    private AuditLogTask auditLogTask;

    /** The action group service. */
    @Mock
    private ActionGroupService actionGroupService;

    /** The audit trail business service. */
    @Mock
    private AuditTrailBusinessService auditTrailBusinessService;

    /** The agent info service. */
    @Mock
    private AgentInfoService agentInfoService;

    @Test
    public void testAuditLastViewed() {
        final AgentInfo info = new AgentInfo();
        info.setEmail( "testEmail" );
        final LastViewedRequest req = new LastViewedRequest();
        req.setObjectType( "TASK" );
        Mockito.when( agentInfoService.getAgentInfo( Mockito.anyString() ) ).thenReturn( info );
        auditLogTask.auditLastViewed( "test", "test", req, new LastViewed() );
        Mockito.verify( auditTrailBusinessService ).saveAuditForAction( Mockito.any() );
    }

    @Test
    public void testauditActionFlow() {
        final ActionGroup actionGroup = new ActionGroup();
        actionGroup.setOpportunityId( "testOpp" );
        final Action action = new Action();
        action.setName( "Test Action" );
        final List< Action > list = new ArrayList<>();
        list.add( action );
        actionGroup.setActions( list );
        Mockito.when( actionGroupService.getActionGroup( Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( actionGroup );
        auditLogTask.auditActionFlow( "test", "test", "test", "0", new HashMap< String, Object >() );
    }
}
