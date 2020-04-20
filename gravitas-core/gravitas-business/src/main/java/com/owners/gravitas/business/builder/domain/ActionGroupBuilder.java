package com.owners.gravitas.business.builder.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.dao.ActionGroupDao;
import com.owners.gravitas.domain.ActionGroup;
import com.owners.gravitas.domain.entity.Action;

/**
 * The Class ActionGroupBuilder.
 *
 * @author shivamm
 */
@Component( "actionGroupBuilder" )
public class ActionGroupBuilder extends AbstractBuilder< String, ActionGroup > {

    /** The agent action group dao. */
    @Autowired
    private ActionGroupDao agentActionGroupDao;

    @Override
    public ActionGroup convertTo( final String source, final ActionGroup destination ) {
        ActionGroup actionGroup = destination;
        if (actionGroup == null) {
            actionGroup = new ActionGroup();
        }
        com.owners.gravitas.domain.entity.ActionGroup actionGroupEntity = agentActionGroupDao.getActionGroup();
        if (actionGroupEntity != null) {
            List< Action > actionsEntity = actionGroupEntity.getActions();
            com.owners.gravitas.domain.Action action = null;
            List< com.owners.gravitas.domain.Action > actionsDomain = new ArrayList<>();
            for ( com.owners.gravitas.domain.entity.Action actionEntity : actionsEntity ) {
                action = new com.owners.gravitas.domain.Action();
                action.setName( actionEntity.getActionName() );
                action.setComplete( false );
                actionsDomain.add( action );
            }
            actionGroup.setActions( actionsDomain );
            actionGroup.setGravitasGroupId( actionGroupEntity.getId() );
            actionGroup.setOpportunityId( source );
        }
        return actionGroup;
    }

    @Override
    public String convertFrom( final ActionGroup source, final String destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }

}
