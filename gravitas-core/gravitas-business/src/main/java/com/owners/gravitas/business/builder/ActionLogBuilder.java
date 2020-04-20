package com.owners.gravitas.business.builder;

import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.entity.ActionLog;
import com.owners.gravitas.dto.request.ActionRequest;

/**
 * The Class ActionLogBuilder.
 *
 * @author vishwanathm
 */
@Component( "actionLogBuilder" )
public class ActionLogBuilder extends AbstractBuilder< ActionRequest, ActionLog > {

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
    public ActionLog convertTo( final ActionRequest source, final ActionLog destination ) {
        ActionLog actionLog = destination;
        if (source != null) {
            if (actionLog == null) {
                actionLog = new ActionLog();
            }
            actionLog.setActionType( source.getActionType().toUpperCase() );
            actionLog.setActionEntity( source.getActionEntity().toUpperCase() );
            actionLog.setPlatform( source.getPlatform().toUpperCase() );
            actionLog.setPreviousValue( source.getPreviousValue() );
            actionLog.setCurrentValue( source.getCurrentValue() );
            actionLog.setActionEntityId( source.getActionEntityId() );
            actionLog.setDescription( source.getDescription() );
            actionLog.setPlatformVersion( source.getPlatformVersion() );
        }
        return actionLog;
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
    public ActionRequest convertFrom( ActionLog source, ActionRequest destination ) {
        throw new UnsupportedOperationException();
    }
}
