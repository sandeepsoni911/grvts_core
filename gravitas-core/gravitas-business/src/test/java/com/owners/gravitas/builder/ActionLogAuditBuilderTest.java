package com.owners.gravitas.builder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.ActionLogAuditBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.ActionLog;
import com.owners.gravitas.dto.ActionLogDto;

/**
 * The Class ActionLogAuditBuilderTest.
 *
 * @author vishwanathm
 */
public class ActionLogAuditBuilderTest extends AbstractBaseMockitoTest {

    /** The action log audit builder. */
    @InjectMocks
    private ActionLogAuditBuilder actionLogAuditBuilder;

    /**
     * Test convert to.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertTo() {
        actionLogAuditBuilder.convertTo( new ActionLog() );
    }

    /**
     * Test convert from witout source.
     */
    @Test
    public void testConvertFromWitoutSource() {
        ActionLog log = actionLogAuditBuilder.convertFrom( null );
        Assert.assertNull( log );
    }

    /**
     * Test convert from witout source with destination.
     */
    @Test
    public void testConvertFromWitoutSourceWithDestination() {
        ActionLog log = actionLogAuditBuilder.convertFrom( null, new ActionLog() );
        Assert.assertNotNull( log );
        Assert.assertNull( log.getActionEntityId() );
    }

    /**
     * Test convert from witout destination.
     */
    @Test
    public void testConvertFromWitoutDestination() {
        ActionLogDto dto = new ActionLogDto();
        dto.setActionEntityId( "actionEntityId" );
        ActionLog log = actionLogAuditBuilder.convertFrom( dto );
        Assert.assertNotNull( log );
        Assert.assertNotNull( log.getActionEntityId() );
    }

    /**
     * Test convert from wit destination.
     */
    @Test
    public void testConvertFromWitDestination() {
        ActionLogDto dto = new ActionLogDto();
        dto.setActionEntityId( "actionEntityId" );
        ActionLog log = actionLogAuditBuilder.convertFrom( dto, new ActionLog() );
        Assert.assertNotNull( log );
        Assert.assertNotNull( log.getActionEntityId() );
    }

    /**
     * Test convert all.
     */
    @Test
    public void testConvertAll() {
        ActionLogDto dto = new ActionLogDto();
        dto.setActionEntityId( "actionEntityId" );
        Map< String, Object > map = new HashMap<>();
        map.put( "key", "value" );
        Map< String, Object > nestedmap = new HashMap<>();
        map.put( "key1", nestedmap );
        dto.setCurrentValuesMap( map );
        List< ActionLog > list = actionLogAuditBuilder.convertAll( dto );
        Assert.assertNotNull( list );
        Assert.assertEquals( list.size(), 1 );
    }

    /**
     * Test convert all with null map.
     */
    @Test
    public void testConvertAllWithNullMap() {
        ActionLogDto dto = new ActionLogDto();
        dto.setActionEntityId( "actionEntityId" );
        Map< String, Object > map = new HashMap<>();
        map.put( "key", null );
        dto.setCurrentValuesMap( map );
        List< ActionLog > list = actionLogAuditBuilder.convertAll( dto );
        Assert.assertNotNull( list );
        Assert.assertEquals( list.size(), 0 );
    }
}
