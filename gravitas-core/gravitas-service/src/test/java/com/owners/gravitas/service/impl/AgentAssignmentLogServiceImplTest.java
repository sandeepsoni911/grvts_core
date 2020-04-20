package com.owners.gravitas.service.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.AgentAssignmentLog;
import com.owners.gravitas.dto.AgentAssignmentLogDto;
import com.owners.gravitas.repository.AgentAssignmentLogRepository;

/**
 * The Class AgentAssignmentLogServiceImplTest.
 *
 * @author raviz
 */
public class AgentAssignmentLogServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent assignment log service impl. */
    @InjectMocks
    private AgentAssignmentLogServiceImpl agentAssignmentLogServiceImpl;

    /** The agent assignment log repository. */
    @Mock
    private AgentAssignmentLogRepository agentAssignmentLogRepository;

    /**
     * Test save all.
     */
    @Test
    public void testSaveAll() {
        final List< AgentAssignmentLog > agentAssignmentLogs = new ArrayList<>();
        when( agentAssignmentLogRepository.save( agentAssignmentLogs ) ).thenReturn( agentAssignmentLogs );
        agentAssignmentLogServiceImpl.saveAll( agentAssignmentLogs );
        verify( agentAssignmentLogRepository ).save( agentAssignmentLogs );
    }

    /**
     * Test find by property zip.
     */
    @Test
    public void testFindByPropertyZip() {
        final String zip = "zip";
        final String propertyZip = zip;
        final List< Object[] > values = new ArrayList<>();
        final BigDecimal score = new BigDecimal( 12 );
        final String email = "a@a.com";
        final String cbsa = "cbsa";
        final String label = "mktLabel";
        final Object[] object = { email, score, zip, cbsa, label };
        values.add( object );
        when( agentAssignmentLogRepository.findByPropertyZip( propertyZip ) ).thenReturn( values );
        final List< AgentAssignmentLogDto > result = agentAssignmentLogServiceImpl.findByPropertyZip( propertyZip );
        assertNotNull( result );
        verify( agentAssignmentLogRepository ).findByPropertyZip( propertyZip );
        assertEquals( result.get( 0 ).getScore(), score.doubleValue() * 100 );
        assertEquals( result.get( 0 ).getAgentEmail(), email );
        assertEquals( result.get( 0 ).getZip(), zip );
        assertEquals( result.get( 0 ).getCbsa(), cbsa );
        assertEquals( result.get( 0 ).getMarketLabel(), label );
    }

    /**
     * Test find by property zip when result is empty.
     */
    @Test
    public void testFindByPropertyZipWhenResultIsEmpty() {
        final String propertyZip = "zip";
        final List< Object[] > values = new ArrayList<>();
        when( agentAssignmentLogRepository.findByPropertyZip( propertyZip ) ).thenReturn( values );
        final List< AgentAssignmentLogDto > result = agentAssignmentLogServiceImpl.findByPropertyZip( propertyZip );
        assertNotNull( result );
        verify( agentAssignmentLogRepository ).findByPropertyZip( propertyZip );
        assertTrue( result.isEmpty() );
    }

    /**
     * Test find agent email by least opp assigned date.
     */
    @Test
    public void testFindAgentEmailByLeastOppAssignedDate() {
        final Collection< String > agentEmails = new ArrayList<>();
        final String expectedresult = "wjfhsdlhfdslkj";
        when( agentAssignmentLogRepository.findAgentEmailByLeastOppAssignedDate( agentEmails ) )
                .thenReturn( expectedresult );
        final String actualResult = agentAssignmentLogServiceImpl.findAgentEmailByLeastOppAssignedDate( agentEmails );
        assertEquals( actualResult, expectedresult );
    }

    /**
     * Test save.
     */
    @Test
    public void testSave() {
        final AgentAssignmentLog log = new AgentAssignmentLog();
        when( agentAssignmentLogRepository.save( log ) ).thenReturn( log );
        agentAssignmentLogServiceImpl.save( log );
        verify( agentAssignmentLogRepository ).save( log );
    }

    /**
     * Test find by opportunity id.
     */
    @Test
    public void testFindByOpportunityId() {
        final String crmId = "crmId";
        final String status = "status";
        final Pageable pageable = new PageRequest( 0, 1 );
        final List< AgentAssignmentLog > logs = new ArrayList<>();
        final AgentAssignmentLog expected = new AgentAssignmentLog();
        logs.add( expected );
        final Page< AgentAssignmentLog > page = new PageImpl< AgentAssignmentLog >( logs );
        when( agentAssignmentLogRepository
                .findByCrmOpportunityIdAndAssignmentStatusContainingOrderByCreatedDateDesc( crmId, status, pageable ) )
                        .thenReturn( page );
        final AgentAssignmentLog actual = agentAssignmentLogServiceImpl.findByOpportunityIdAndStatusLike( crmId, status,
                pageable );
        assertEquals( actual, expected );
        verify( agentAssignmentLogRepository )
                .findByCrmOpportunityIdAndAssignmentStatusContainingOrderByCreatedDateDesc( crmId, status, pageable );
    }
}
