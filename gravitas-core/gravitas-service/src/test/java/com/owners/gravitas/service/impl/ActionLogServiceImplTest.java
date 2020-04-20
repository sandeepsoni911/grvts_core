package com.owners.gravitas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.ActionLog;
import com.owners.gravitas.repository.ActionLogRepository;

/**
 * The Class ActionLogServiceImplTest.
 *
 * @author vishwanathm
 */
public class ActionLogServiceImplTest extends AbstractBaseMockitoTest {

    /** The action log service impl. */
    @InjectMocks
    private ActionLogServiceImpl actionLogServiceImpl;

    /** The action log repository. */
    @Mock
    private ActionLogRepository actionLogRepository;

    /**
     * Test save.
     */
    @Test
    public void testSave() {
        ActionLog log = new ActionLog();
        log.setId( "id" );
        Mockito.when( actionLogRepository.save( Mockito.any( ActionLog.class ) ) ).thenReturn( log );
        ActionLog savedLog = actionLogServiceImpl.save( log );
        Mockito.verify( actionLogRepository ).save( Mockito.any( ActionLog.class ) );
        Assert.assertNotNull( savedLog );
        Assert.assertNotNull( savedLog.getId() );
        Assert.assertEquals( savedLog, log );
    }

    /**
     * Test get CTA audit logs.
     */
    @Test
    public void testGetCTAAuditLogs() {
        List< ActionLog > logList = new ArrayList< ActionLog >();
        ActionLog log1 = new ActionLog();
        log1.setId( "id1" );
        ActionLog log2 = new ActionLog();
        log2.setId( "id2" );
        logList.add( log1 );
        logList.add( log2 );
        Mockito.when( actionLogRepository.getActionLogByActionByAndActionEntityIdAndActionTypeIn( Mockito.anyString(),
                Mockito.anyString(), Mockito.any( List.class ) ) ).thenReturn( logList );
        List< ActionLog > ctaAuditLogList = actionLogServiceImpl.getCTAAuditLogs( "value1", "value2",
                new ArrayList< String >() );
        Mockito.verify( actionLogRepository ).getActionLogByActionByAndActionEntityIdAndActionTypeIn(
                Mockito.anyString(), Mockito.anyString(), Mockito.any( List.class ) );
        Assert.assertNotNull( ctaAuditLogList );
        Assert.assertEquals( ctaAuditLogList, logList );
    }

    /**
     * Test saveAll.
     */
    @Test
    public void testSaveAll() {
        List< ActionLog > logList = new ArrayList< ActionLog >();
        ActionLog log1 = new ActionLog();
        log1.setId( "id1" );
        ActionLog log2 = new ActionLog();
        log2.setId( "id2" );
        logList.add( log1 );
        logList.add( log2 );
        Mockito.when( actionLogRepository.save( Mockito.any( List.class ) ) ).thenReturn( logList );
        actionLogServiceImpl.saveAll( logList );
        Mockito.verify( actionLogRepository ).save( Mockito.any( List.class ) );
    }
}
