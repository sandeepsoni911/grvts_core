package com.owners.gravitas.business.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.ActionLogAuditBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.ActionLog;
import com.owners.gravitas.dto.ActionLogDto;
import com.owners.gravitas.service.ActionLogService;

/**
 * The Class ActionLogBusinessServiceImplTest.
 *
 * @author ankusht
 */
public class ActionLogBusinessServiceImplTest extends AbstractBaseMockitoTest {

    @Mock
    private ActionLogService actionLogService;

    @InjectMocks
    private ActionLogBusinessServiceImpl actionLogBusinessServiceImpl;

    /** The action log audit builder. */
    @Mock
    private ActionLogAuditBuilder actionLogAuditBuilder;

    /**
     * Should save action log.
     */
    @Test
    public void shouldSaveActionLog() {
        ActionLog log = new ActionLog();
        actionLogBusinessServiceImpl.saveActionLog( log );
        verify( actionLogService ).save( log );
    }

    /**
     * Testagent audit for action.
     */
    @Test
    public void testagentAuditForAction(){
        ActionLogDto dto = new ActionLogDto();
        List< ActionLog > expected = new ArrayList<>();
        ActionLog actionLog = new ActionLog();
        expected.add( actionLog );
        List<ActionLogDto> dtos = new ArrayList<>();
        dtos.add( dto );
        Mockito.when( actionLogAuditBuilder.convertAll( dto ) ).thenReturn( expected );
        Mockito.doNothing().when( actionLogService ).saveAll( expected );

        actionLogBusinessServiceImpl.agentAuditForAction( dtos );

        Mockito.verify( actionLogService ).saveAll( expected );
    }

    /**
     * Should save map.
     */
    @Test
    public void shouldSaveMap() {
        Map< String, Object > previousValuesMap = new HashMap<>();
        Map< String, Object > currentValuesMap = new HashMap<>();
        previousValuesMap.put( "key1", "value1" );
        currentValuesMap.put( "key1", "value2" );

        ActionLogDto dto = new ActionLogDto();
        dto.setActionBy( "test" );
        dto.setActionType( "test" );
        dto.setActionEntity( "test" );
        dto.setActionEntityId( "test" );
        dto.setCurrentValuesMap( currentValuesMap );
        dto.setPreviousValuesMap( previousValuesMap );
        dto.setDescription( "test" );
        dto.setPlatform( "test" );
        dto.setPlatformVersion( "test" );

        List< ActionLog > expected = new ArrayList<>();
        ActionLog actionLog = new ActionLog();
        expected.add( actionLog );
        Mockito.when( actionLogAuditBuilder.convertAll( dto ) ).thenReturn( expected );
        Mockito.doNothing().when( actionLogService ).saveAll( expected );

        actionLogBusinessServiceImpl.auditForAction( dto );

        Mockito.verify( actionLogService ).saveAll( expected );
    }

    /**
     * Should save map if previous map is null.
     */
    @Test
    public void shouldSaveMapIfPreviousMapIsEmpty() {
        Map< String, Object > previousValuesMap = new HashMap<>();
        Map< String, Object > currentValuesMap = new HashMap<>();
        previousValuesMap.put( "key1", "value1" );
        currentValuesMap.put( "key1", "value2" );

        ActionLogDto dto = new ActionLogDto();
        dto.setActionBy( "test" );
        dto.setActionType( "test" );
        dto.setActionEntity( "test" );
        dto.setActionEntityId( "test" );
        dto.setCurrentValuesMap( currentValuesMap );
        dto.setPreviousValuesMap( previousValuesMap );
        dto.setDescription( "test" );
        dto.setPlatform( "test" );
        dto.setPlatformVersion( "test" );

        List< ActionLog > expected = new ArrayList<>();
        ActionLog actionLog = new ActionLog();
        expected.add( actionLog );

        Mockito.when( actionLogAuditBuilder.convertAll( dto ) ).thenReturn( expected );
        Mockito.doNothing().when( actionLogService ).saveAll( expected );

        actionLogBusinessServiceImpl.auditForAction( dto );

        Mockito.verify( actionLogService ).saveAll( expected );
    }

    @Test
    public void shouldGetCTAAuditLogs() {
        String email = "test";
        String opportunityId = "test";
        List< String > ctaValues = Arrays.asList( new String[] { "cta1" } );
        List< ActionLog > expected = new ArrayList<>();
        ActionLog actionLog = new ActionLog();
        expected.add( actionLog );
        when( actionLogService.getCTAAuditLogs( email, opportunityId, ctaValues ) ).thenReturn( expected );
        List< ActionLog > actual = actionLogBusinessServiceImpl.getCTAAuditLogs( email, opportunityId, ctaValues );
        assertEquals( expected, actual );
    }

}
