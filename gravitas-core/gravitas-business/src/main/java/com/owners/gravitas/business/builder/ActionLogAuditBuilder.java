package com.owners.gravitas.business.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.entity.ActionLog;
import com.owners.gravitas.dto.ActionLogDto;

/**
 * The Class ActionLogAuditBuilder.
 *
 * @author vishwanathm
 */
@Component( "actionLogAuditBuilder" )
public class ActionLogAuditBuilder extends AbstractBuilder< ActionLog, ActionLogDto > {

    /**
     * Convert to.
     *
     * @param source
     *            the source
     * @param destination
     *            the destination
     * @return the action log
     */
    @Override
    public ActionLogDto convertTo( final ActionLog source, final ActionLogDto destination ) {
        throw new UnsupportedOperationException();
    }

    /**
     * Convert from.
     *
     * @param source
     *            the source
     * @param destination
     *            the destination
     * @return the action request
     */
    @Override
    public ActionLog convertFrom( final ActionLogDto source, final ActionLog destination ) {
        ActionLog actionLog = destination;
        if (source != null) {
            if (actionLog == null) {
                actionLog = new ActionLog();
            }
            actionLog.setActionType( source.getActionType() );
            actionLog.setActionEntity( source.getActionEntity() );
            actionLog.setActionEntityId( source.getActionEntityId() );
            actionLog.setActionBy( source.getActionBy() );
            actionLog.setDescription( source.getDescription() );
            actionLog.setPlatform( source.getPlatform() );
            actionLog.setPlatformVersion( source.getPlatformVersion() );
        }
        return actionLog;
    }

    /**
     * Convert all.
     *
     * @param actionLogDto
     *            the action log dto
     * @return the list
     */
    public List< ActionLog > convertAll( final ActionLogDto actionLogDto ) {
        final List< ActionLog > actionLogList = new ArrayList<>();
        convertNestedObjects( actionLogDto, actionLogList, actionLogDto.getCurrentValuesMap() );
        return actionLogList;
    }

    /**
     * Convert nested objects.
     *
     * @param actionLogDto
     *            the action log dto
     * @param actionLogList
     *            the action log list
     * @param currentValuesMap
     *            the map
     * @return the list
     */
    private void convertNestedObjects( final ActionLogDto actionLogDto, final List< ActionLog > actionLogList,
            final Map< String, Object > currentValuesMap ) {
        for ( Entry< String, Object > entry : currentValuesMap.entrySet() ) {
            if (entry.getValue() != null) {
                if (Map.class.isAssignableFrom( entry.getValue().getClass() )) {
                    convertNestedObjects( actionLogDto, actionLogList, ( Map ) entry.getValue() );
                    continue;
                }
                final ActionLog actionLog = convertFrom( actionLogDto );
                actionLog.setCurrentValue( String.valueOf( entry.getValue() ) );
                actionLog.setFieldName( entry.getKey() );
                actionLogList.add( actionLog );
            }
        }
    }
}
