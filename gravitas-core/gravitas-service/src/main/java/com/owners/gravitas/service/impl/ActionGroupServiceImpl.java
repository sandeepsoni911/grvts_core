package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.Constants.ACTION_OBJ;
import static com.owners.gravitas.constants.Constants.AGENT_EMAIL;
import static com.owners.gravitas.constants.Constants.AGENT_ID;
import static com.owners.gravitas.constants.Constants.ENTITY_ID;
import static com.owners.gravitas.constants.Constants.OPPORTUNITY_ID;
import static com.owners.gravitas.enums.ActionEntity.ACTION_FLOW;
import static com.owners.gravitas.enums.ActionType.CREATE;
import static com.owners.gravitas.enums.ActionType.UPDATE;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.annotation.Audit;
import com.owners.gravitas.dao.ActionGroupDao;
import com.owners.gravitas.domain.Action;
import com.owners.gravitas.domain.ActionGroup;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.entity.OpportunityAction;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.repository.OpportunityActionRepository;
import com.owners.gravitas.service.ActionGroupService;
import com.owners.gravitas.service.builder.OpportunityActionBuilder;

/**
 * The Class ActionGroupServiceImpl.
 *
 * @author shivamm
 */
@Service
public class ActionGroupServiceImpl implements ActionGroupService {

    /** The action group dao. */
    @Autowired
    private ActionGroupDao actionGroupDao;

    /** The opportunity action builder. */
    @Autowired
    private OpportunityActionBuilder opportunityActionBuilder;

    /** The opportunity action repository. */
    @Autowired
    private OpportunityActionRepository opportunityActionRepository;

    /*
     * (non-Javadoc)
     * @see
     */
    @Audit( type = CREATE, entity = ACTION_FLOW, args = { AGENT_ID, ACTION_OBJ, OPPORTUNITY_ID, "opportunity" } )
    @Override
    public PostResponse createActionGroup( final String agentId, final ActionGroup actionGroup,
            final String opportunityId, final Opportunity opportunity ) {
        final PostResponse postResponse = actionGroupDao.createActionGroup( agentId, actionGroup );
        saveActionGroup( postResponse.getName(), actionGroup );
        return postResponse;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ActionGroupService#getActionInfo(java.lang.
     * String, java.lang.String, java.lang.String)
     */
    @Override
    public Action getActionInfo( final String agentId, final String actionGroupId, final String actionId ) {
        return actionGroupDao.getAction( agentId, actionGroupId, actionId );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ActionGroupService#getActionGroup(java.lang.
     * String, java.lang.String)
     */
    @Override
    public ActionGroup getActionGroup( final String agentId, final String actionGroupId ) {
        return actionGroupDao.getActionGroup( agentId, actionGroupId );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentInfoService#patchAgentActionInfo(java.
     * lang.String, java.lang.String, java.lang.String,
     * com.owners.gravitas.domain.Action)
     */
    @Override
    public void patchAgentActionInfo( final String agentId, final String actionFlowId, final String actionId,
            final Map< String, Object > actionMap ) {
        actionGroupDao.patchAction( agentId, actionFlowId, actionId, actionMap );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ActionGroupService#getStartTime(java.lang.
     * String, java.lang.String)
     */
    @Override
    public List< OpportunityAction > getStartTime( final String actionGroupId ) {
        return opportunityActionRepository.findByActionFlowId( actionGroupId );
    }

    /**
     * Save action group.
     *
     * @param actionFlow
     *            the action flow
     * @return the list
     */
    @Override
    public List< OpportunityAction > saveActionGroup( final List< OpportunityAction > actionFlow ) {
        return opportunityActionRepository.save( actionFlow );
    }

    /**
     * Save action.
     *
     * @param action
     *            the action
     * @return the opportunity action
     */
    @Override
    public OpportunityAction saveAction( final OpportunityAction action ) {
        return opportunityActionRepository.save( action );
    }

    /**
     * Patch action group.
     *
     * @param agentId
     *            the agent id
     * @param actionGroupId
     *            the action group id
     * @param agentEmail
     *            the agent email
     * @param actionFlowData
     *            the action flow data
     */
    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ActionGroupService#patchActionGroup(java.lang
     * .String, java.lang.String, java.util.Map)
     */
    @Audit( type = UPDATE, entity = ACTION_FLOW, args = { AGENT_ID, ENTITY_ID, AGENT_EMAIL, ACTION_OBJ } )
    @Override
    public void patchActionGroup( final String agentId, final String actionGroupId, final String agentEmail,
            final Map< String, Object > actionFlowData ) {
        actionGroupDao.patchActionGroup( agentId, actionGroupId, actionFlowData );
    }

    /**
     * Save action group.
     *
     * @param actionFlowId
     *            the action id
     * @param actionGroup
     *            the action group
     */
    @Override
    public List< OpportunityAction > saveActionGroup( final String actionFlowId, final ActionGroup actionGroup ) {
        final List< OpportunityAction > actions = opportunityActionBuilder.convertTo( actionGroup );
        actions.forEach( action -> {
            action.setActionFlowId( actionFlowId );
        } );
        return opportunityActionRepository.save( actions );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ActionGroupService#getOpportunityAction(java.
     * lang.String, java.lang.String)
     */
    @Override
    public OpportunityAction getOpportunityAction( final String actionGroupId, final String actionId ) {
        return opportunityActionRepository.findByActionFlowIdAndActionId( actionGroupId, actionId );
    }
}
