package com.owners.gravitas.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.PotentialOpportunityAssignmentLog;
import com.owners.gravitas.repository.OpportunityAssignmentLogRepository;

/**
 * The Class OpportunityAssignmentLogServiceImplTest.
 */
public class OpportunityAssignmentLogServiceImplTest extends AbstractBaseMockitoTest {

    /** The opportunity assignment log service. */
    @InjectMocks
    private OpportunityAssignmentLogServiceImpl opportunityAssignmentLogService;

    /** The agent lookup log repository. */
    @Mock
    private OpportunityAssignmentLogRepository opportunityAssignmentLogRepository;

    /**
     * Test save.
     */
    @Test
    public void testSave() {
        PotentialOpportunityAssignmentLog log = new PotentialOpportunityAssignmentLog();
        Mockito.when( opportunityAssignmentLogRepository.save( log ) ).thenReturn( log );
        PotentialOpportunityAssignmentLog actualLog = opportunityAssignmentLogService.save( log );
        Assert.assertEquals( actualLog, log );
    }

    /**
     * Test mark agent opportunity status.
     */
    @Test
    public void testMarkAgentOpportunityStatus() {
        String crmId = "crmId", agentEmail = "agentEmail", currentUser = "currentUser";
        opportunityAssignmentLogService.updateAgentAssignmentAudit( crmId, agentEmail, currentUser );
        Mockito.verify( opportunityAssignmentLogRepository ).updateAgentAssignmentAudit( crmId, agentEmail,
                currentUser );
    }
}
