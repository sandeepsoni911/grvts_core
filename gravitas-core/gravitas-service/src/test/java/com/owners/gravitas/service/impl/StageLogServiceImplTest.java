package com.owners.gravitas.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.LeadOpportunityBusinessConfig;
import com.owners.gravitas.domain.entity.Opportunity;
import com.owners.gravitas.repository.StageLogRepository;
import com.owners.gravitas.service.OpportunityService;

/**
 * The Class StageLogServiceImplTest.
 *
 * @author shivamm
 */
public class StageLogServiceImplTest extends AbstractBaseMockitoTest {

    /** The object type service impl. */
    @InjectMocks
    private StageLogServiceImpl stageLogServiceImpl;

    @Mock
    private OpportunityService opportunityService;

    /** The stage log repository. */
    @Mock
    private StageLogRepository stageLogRepository;

    /** The lead opportunity business config. */
    @Mock
    private LeadOpportunityBusinessConfig leadOpportunityBusinessConfig;

    /**
     * Test save opportunity stagelog.
     */
    @Test
    public void testSaveOpportunityStagelog() {
        final String opportunityId = "test";
        final String stageStr = "test";
        final Opportunity opportunity = new Opportunity();
        Mockito.when( leadOpportunityBusinessConfig.isContactDatabaseConfig() ).thenReturn( true );
        Mockito.when( opportunityService.getOpportunityByFbId( opportunityId, Boolean.FALSE ) )
                .thenReturn( opportunity );
        stageLogServiceImpl.saveOpportunityStagelog( opportunityId, stageStr );
        Mockito.verify( opportunityService ).getOpportunityByFbId( opportunityId, Boolean.FALSE );
    }

}
