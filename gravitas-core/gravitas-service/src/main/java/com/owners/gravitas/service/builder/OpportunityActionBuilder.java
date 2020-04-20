package com.owners.gravitas.service.builder;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.Action;
import com.owners.gravitas.domain.ActionGroup;
import com.owners.gravitas.domain.entity.OpportunityAction;
import com.owners.gravitas.repository.ActionRepository;

/**
 * The Class OpportunityActionBuilder.
 *
 * @author raviz
 */
@Component( "OpportunityActionBuilder" )
public class OpportunityActionBuilder extends AbstractBuilder< ActionGroup, List< OpportunityAction > > {

    /** The action repository. */
    @Autowired
    private ActionRepository actionRepository;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public List< OpportunityAction > convertTo( final ActionGroup source,
            final List< OpportunityAction > destination ) {
        List< OpportunityAction > opportunityActions = destination;
        if (source != null) {
            if (opportunityActions == null) {
                opportunityActions = new ArrayList<>();
            }

            final List< Action > actions = source.getActions();
            if (CollectionUtils.isNotEmpty( actions )) {
                int actionStep = 0;
                for ( final Action action : actions ) {
                    final OpportunityAction opportunityAction = new OpportunityAction();
                    opportunityAction.setAction( actionRepository.findByActionName( action.getName() ) );
                    opportunityAction.setActionId( Integer.toString( actionStep++ ) );
                    opportunityAction.setCompleted( action.isComplete() );
                    opportunityAction.setDeleted( false );
                    opportunityAction.setOpportunityId( source.getOpportunityId() );
                    opportunityActions.add( opportunityAction );
                }
            }
        }
        return opportunityActions;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public ActionGroup convertFrom( final List< OpportunityAction > source, final ActionGroup destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }

}
