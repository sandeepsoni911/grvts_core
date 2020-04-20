package com.owners.gravitas.business.impl;

import static com.owners.gravitas.constants.Constants.ACTION_OBJ;
import static com.owners.gravitas.constants.Constants.AGENT_ID;
import static com.owners.gravitas.constants.Constants.BLANK_SPACE;
import static com.owners.gravitas.constants.Constants.ENTITY_ID;
import static com.owners.gravitas.enums.ActionEntity.AGENT;
import static com.owners.gravitas.enums.ActionEntity.AGENT_NOTE;
import static com.owners.gravitas.enums.ActionEntity.CONTACT;
import static com.owners.gravitas.enums.ActionEntity.OPPORTUNITY;
import static com.owners.gravitas.enums.ActionEntity.REQUEST;
import static com.owners.gravitas.enums.ActionEntity.TASK;
import static com.owners.gravitas.enums.ActionType.CREATE;
import static com.owners.gravitas.enums.Platform.AGENT_APP;
import static com.owners.gravitas.enums.Platform.GRAVITAS;
import static com.owners.gravitas.enums.Platform.GRAVITAS_WEB;
import static org.apache.commons.collections.MapUtils.isNotEmpty;
import static org.apache.commons.lang3.ArrayUtils.isSameLength;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.owners.gravitas.annotation.Audit;
import com.owners.gravitas.business.ActionLogBusinessService;
import com.owners.gravitas.business.AuditTrailBusinessService;
import com.owners.gravitas.config.AuditTrailConfig;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.domain.Action;
import com.owners.gravitas.domain.ActionGroup;
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.AgentHolder;
import com.owners.gravitas.domain.AgentInfo;
import com.owners.gravitas.domain.BaseDomain;
import com.owners.gravitas.domain.Contact;
import com.owners.gravitas.domain.Device;
import com.owners.gravitas.domain.Note;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.PhoneNumber;
import com.owners.gravitas.domain.Reminder;
import com.owners.gravitas.domain.Request;
import com.owners.gravitas.domain.Stage;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.dto.ActionLogDto;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.enums.ActionEntity;
import com.owners.gravitas.enums.ActionType;
import com.owners.gravitas.enums.Platform;
import com.owners.gravitas.service.AgentService;

/**
 * The Class AuditTrailBusinessServiceImpl.
 *
 * @author vishwanathm
 */
@Service( "auditTrailBusinessService" )
public class AuditTrailBusinessServiceImpl implements AuditTrailBusinessService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AuditTrailBusinessServiceImpl.class );

    /** The action log business service. */
    @Autowired
    private ActionLogBusinessService actionLogBusinessService;

    /** The agent service. */
    @Autowired
    private AgentService agentService;

    /** The audit trail config. */
    @Autowired
    private AuditTrailConfig auditTrailConfig;

    /**
     * Store request params.
     *
     * @param joinPoint
     *            the join point
     * @param audit
     *            the action
     * @param postResponse
     *            the post response
     * @return the object
     */
    @Override
    @Async
    public void createAuditLog( final Object[] joinPointArgs, final Audit audit, final Object returnObj ) {
        if (auditTrailConfig.isEnableAutoAuditTrail()) {
            LOGGER.debug( "Audit logging started" );
            try {
                final Map< String, Object > argsMap = getArgsMap( audit.args(), joinPointArgs );
                if (isNotEmpty( argsMap )) {
                    final Platform platform = getPlatform();
                    LOGGER.debug( "Platform: " + platform + " auditing for entity: " + audit.entity() + " and action: "
                            + audit.type() );
                    if (AGENT.equals( audit.entity() )) {
                        handleAgentActionLog( argsMap );
                    } else {
                        handleAuditAction( audit, argsMap, returnObj, platform );
                    }
                } else {
                    LOGGER.error( "Auditing failed, as method argument(s) mismatch for entity: " + audit.entity()
                            + " and action: " + audit.type() );
                }
            } catch ( final Exception exp ) {
                LOGGER.error( "Problem in auditing for entity: " + audit.entity() + " and action: " + audit.type()
                        + BLANK_SPACE + exp.getLocalizedMessage(), exp );
            }
        }
    }

    /**
     * Save audit for action.
     *
     * @param actionLogDto
     *            the action log dto
     */
    @Override
    @Async
    public void saveAuditForAction( final ActionLogDto actionLogDto ) {
        if (auditTrailConfig.isEnableAutoAuditTrail()) {
            actionLogBusinessService.auditForAction( actionLogDto );
        }
    }

    /**
     * Gets the action log dto.
     *
     * @param platform
     *            the platform
     * @param agentId
     *            the agent id
     * @param auditMap
     *            the audit map
     * @param entity
     *            the entity
     * @param actionEntityId
     *            the action entity id
     * @return the action log dto
     */
    @Override
    public ActionLogDto getActionLogDto( final ActionType type, final String actionBy,
            final Map< String, Object > auditMap, final ActionEntity entity, final String actionEntityId ) {
        final ActionLogDto actionLogDto = new ActionLogDto();
        actionLogDto.setActionType( type.name() );
        actionLogDto.setActionEntity( entity.name() );
        actionLogDto.setActionEntityId( actionEntityId );
        actionLogDto.setActionBy( actionBy );
        actionLogDto.setCurrentValuesMap( auditMap );
        actionLogDto.setPlatform( getPlatform().name() );
        return actionLogDto;
    }

    /**
     * Handle agent action log.
     *
     * @param agentHolder
     *            the agent holder
     */
    private void handleAgentActionLog( final Map< String, Object > argsMap ) {
        final List< ActionLogDto > actionLogDtos = new ArrayList<>();
        if (argsMap.containsKey( ACTION_OBJ )) {
            Map< String, Object > auditMap;
            final AgentHolder agentHolder = ( AgentHolder ) argsMap.get( ACTION_OBJ );
            final Agent agent = agentHolder.getAgent();
            final String agentEmail = agent.getInfo().getEmail();
            final Map< ActionEntity, Map< String, Object > > agentAuditMap = getAgentFieldsAuditMap( agent );
            for ( final ActionEntity actionEntity : agentAuditMap.keySet() ) {
                for ( final Entry< String, Object > entry : agentAuditMap.get( actionEntity ).entrySet() ) {
                    auditMap = getAuditMap( actionEntity, entry.getValue() );
                    final ActionLogDto actionLogDto = getActionLogDto( CREATE, agentEmail, auditMap, actionEntity,
                            entry.getKey() );
                    actionLogDtos.add( actionLogDto );
                }
            }
            // action log for agent info
            auditMap = getAuditMap( AGENT, agentHolder.getAgent().getInfo() );
            final ActionLogDto actionLogDto = getActionLogDto( CREATE, agentEmail, auditMap, AGENT,
                    agentHolder.getAgentId() );
            actionLogDtos.add( actionLogDto );
        }
        actionLogBusinessService.agentAuditForAction( actionLogDtos );
    }

    /**
     * Save action log.
     *
     * @param audit
     *            the audit
     * @param actionEntityId
     *            the action entity id
     * @param argsMap
     *            the args map
     * @param auditMap
     *            the audit map
     * @param platform
     *            the platform
     */
    private void saveActionLog( final Audit audit, final String actionEntityId, final Map< String, Object > argsMap,
            final Map< String, Object > auditMap, final Platform platform ) {
        String actionBy = Constants.GRAVITAS;
        final ActionLogDto actionLogDto = getActionLogDto( platform, auditMap, audit, actionEntityId );
        if (argsMap.containsKey( AGENT_ID ) && !GRAVITAS.equals( platform )) {
            actionBy = getAgentEmail( argsMap.get( AGENT_ID ).toString() );
        }
        actionLogDto.setActionBy( actionBy );
        LOGGER.debug( "Saving action log for actionEntityId " + actionEntityId + " for entity " + audit.entity()
                + " and type " + audit.type() );
        actionLogBusinessService.auditForAction( actionLogDto );
    }

    /**
     * Creates the action log.
     *
     * @param auditParams
     *            the audit params
     * @param argsMap
     *            the args map
     * @param auditMap
     *            the audit map
     * @param action
     *            the action
     * @param postResponse
     *            the post response
     * @return the action log dto
     */
    private ActionLogDto getActionLogDto( final Platform platform, final Map< String, Object > auditMap,
            final Audit action, final String actionEntityId ) {
        final ActionLogDto actionLogDto = new ActionLogDto();
        actionLogDto.setActionType( action.type().name() );
        actionLogDto.setActionEntity( action.entity().name() );
        actionLogDto.setActionEntityId( actionEntityId );
        actionLogDto.setCurrentValuesMap( auditMap );
        actionLogDto.setPlatform( platform.name() );
        return actionLogDto;
    }

    /**
     * Gets the param map.
     *
     * @param argNames
     *            the param names
     * @param argValues
     *            the param values
     * @return the param map
     */
    private Map< String, Object > getArgsMap( final String[] argNames, final Object[] argValues ) {
        final Map< String, Object > argsMap = new HashMap<>();
        if (isSameLength( argValues, argNames )) {
            for ( int i = 0; i < argValues.length; i++ ) {
                argsMap.put( argNames[i], argValues[i] );
            }
        }
        return argsMap;
    }

    /**
     * Gets the device info.
     *
     * @return the device info
     */
    private Platform getPlatform() {
        Platform platform = GRAVITAS;
        final ServletRequestAttributes requestAttributes = ( ( ServletRequestAttributes ) RequestContextHolder
                .getRequestAttributes() );
        if (requestAttributes != null) {
            final HttpServletRequest request = requestAttributes.getRequest();
            if (request != null) {
                final org.springframework.mobile.device.Device device = ( org.springframework.mobile.device.Device ) request
                        .getAttribute( "currentDevice" );
                if (device != null) {
                    if (device.isMobile() || device.isTablet()) {
                        platform = AGENT_APP;
                    } else if (device.isNormal()) {
                        platform = GRAVITAS_WEB;
                    }
                }
            }
        }
        return platform;
    }

    /**
     * Gets the entity id.
     *
     * @param returnObj
     *            the return obj
     * @param argsMap
     *            the args map
     * @return the entity id
     */
    private String getEntityId( final Object returnObj, final Map< String, Object > argsMap ) {
        String actionEntityId = EMPTY;
        if (argsMap.containsKey( ENTITY_ID )) {
            actionEntityId = ( ( argsMap.containsKey( ENTITY_ID ) ) ? argsMap.get( ENTITY_ID ).toString() : EMPTY );
        } else if (returnObj != null && PostResponse.class.isAssignableFrom( returnObj.getClass() )) {
            final PostResponse postResponse = ( PostResponse ) returnObj;
            actionEntityId = ( isNotEmpty( postResponse.getName() ) ) ? postResponse.getName() : EMPTY;
        } else {
            actionEntityId = ( argsMap.containsKey( AGENT_ID ) ) ? argsMap.get( AGENT_ID ).toString() : EMPTY;
        }
        return actionEntityId;
    }

    /**
     * Gets the agent email.
     *
     * @param argsMap
     *            the args map
     * @param actionBy
     *            the action by
     * @return the agent email
     */
    private String getAgentEmail( final String agentId ) {
        String email = EMPTY;
        final Agent agent = agentService.getAgentById( agentId );
        if (agent != null) {
            email = agent.getInfo().getEmail();
        }
        return email;
    }

    /**
     * Handle audit action.
     *
     * @param audit
     *            the audit
     * @param argsMap
     *            the args map
     * @param returnObj
     *            the return obj
     * @param platform
     *            the platform
     */
    private void handleAuditAction( final Audit audit, final Map< String, Object > argsMap, final Object returnObj,
            final Platform platform ) {
        String actionEntityId = getEntityId( returnObj, argsMap );
        final ActionEntity entity = audit.entity();
        final Object object = argsMap.get( ACTION_OBJ );
        LOGGER.debug( "Auditing Started for entity id " + actionEntityId + " for entity " + audit.entity()
                + " and type " + audit.type() );
        if (HashMap.class.isAssignableFrom( object.getClass() )) {
            final Map< String, Object > auditMap = ( Map< String, Object > ) object;
            for ( final Map.Entry< String, Object > entry : auditMap.entrySet() ) {
                final Object entryObj = entry.getValue();
                if (null != entryObj) {
                    if (Task.class.getName().equals( entryObj.getClass().getName() )) {
                        final Map< String, Object > taskAauditMap = getDomainAudit( TASK, entryObj );
                        actionEntityId = entry.getKey();
                        saveActionLog( audit, actionEntityId, argsMap, taskAauditMap, platform );
                    } else if (Request.class.getName().equals( entryObj.getClass() )) {
                        final Map< String, Object > requestAauditMap = getDomainAudit( REQUEST, entryObj );
                        actionEntityId = entry.getKey();
                        saveActionLog( audit, actionEntityId, argsMap, requestAauditMap, platform );
                    } else {
                        saveActionLog( audit, actionEntityId, argsMap, auditMap, platform );
                        break;
                    }
                }
            }
        } else {
            final Map< String, Object > domainAuditMap = getDomainAudit( entity, object );
            saveActionLog( audit, actionEntityId, argsMap, domainAuditMap, platform );
        }
        LOGGER.debug( "Auditing completed for entity id " + actionEntityId + " for entity " + audit.entity()
                + " and type " + audit.type() );
    }

    /**
     * Gets the audit map.
     *
     * @param entity
     *            the entity
     * @param object
     *            the object
     * @return the audit map
     */
    private Map< String, Object > getAuditMap( final ActionEntity entity, final Object object ) {
        Map< String, Object > domainAuditMap = new HashMap<>();
        if (HashMap.class.isAssignableFrom( object.getClass() )) {
            final Map< String, Object > auditMap = ( Map< String, Object > ) object;
            for ( final Map.Entry< String, Object > entry : auditMap.entrySet() ) {
                final Object entryObj = entry.getValue();
                if (Task.class.getName().equals( entryObj.getClass().getName() )) {
                    final Map< String, Object > taskAauditMap = getDomainAudit( TASK, entryObj );
                    domainAuditMap.putAll( taskAauditMap );
                } else if (Request.class.getName().equals( entryObj.getClass() )) {
                    final Map< String, Object > requestAauditMap = getDomainAudit( REQUEST, entryObj );
                    domainAuditMap.putAll( requestAauditMap );
                } else {
                    domainAuditMap.putAll( auditMap );
                }
            }
        } else {
            domainAuditMap = getDomainAudit( entity, object );
        }
        return domainAuditMap;
    }

    /**
     * Gets the audit map for domain.
     *
     * @param entity
     *            the entity
     * @param object
     *            the object
     * @return the audit map for domain
     */
    protected Map< String, Object > getDomainAudit( final ActionEntity entity, final Object object ) {
        BaseDomain domain = new BaseDomain();
        switch ( entity ) {
            case AGENT_NOTE:
                domain = ( Note ) object;
                break;
            case TASK:
                domain = ( Task ) object;
                break;
            case REQUEST:
                domain = ( Request ) object;
                break;
            case OPPORTUNITY:
                domain = ( Opportunity ) object;
                break;
            case CONTACT:
                domain = ( Contact ) object;
                break;
            case PHONE:
                domain = ( PhoneNumber ) object;
                break;
            case AGENT_INFO:
                domain = ( AgentInfo ) object;
                break;
            case AGENT:
                domain = ( AgentInfo ) object;
                break;
            case STAGE:
                domain = ( Stage ) object;
                break;
            case DEVICE:
                domain = ( Device ) object;
                break;
            case REMINDER:
                domain = ( Reminder ) object;
                break;
            case ACTION:
                domain = ( Action ) object;
                break;
            case ACTION_FLOW:
                domain = ( ActionGroup ) object;
                break;
            default:
                LOGGER.info( "Un-supported action entity object " + entity );
                break;
        }
        return domain.toAuditMap();
    }

    /**
     * Gets the agent audit map.
     *
     * @param agent
     *            the agent
     * @return the agent audit map
     */
    private Map< ActionEntity, Map< String, Object > > getAgentFieldsAuditMap( final Agent agent ) {
        final Map< ActionEntity, Map< String, Object > > agentAuditMap = new HashMap<>();
        agentAuditMap.put( OPPORTUNITY, ( Map ) agent.getOpportunities() );
        agentAuditMap.put( CONTACT, ( Map ) agent.getContacts() );
        agentAuditMap.put( REQUEST, ( Map ) agent.getRequests() );
        agentAuditMap.put( TASK, ( Map ) agent.getTasks() );
        agentAuditMap.put( AGENT_NOTE, ( Map ) agent.getAgentNotes() );
        return agentAuditMap;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AuditTrailBusinessService#getActionLogDto(
     * com.owners.gravitas.enums.ActionType, java.lang.String, java.util.Map,
     * java.lang.String, java.lang.String)
     */
    @Override
    public ActionLogDto getActionLogDto( final ActionType actionType, final String user, final Map< String, Object > auditMap,
            final String entity, final String entityId ) {
        final ActionLogDto actionLogDto = new ActionLogDto();
        actionLogDto.setActionType( actionType.name() );
        actionLogDto.setActionEntity( entity );
        actionLogDto.setActionEntityId( entityId );
        actionLogDto.setActionBy( user );
        actionLogDto.setCurrentValuesMap( auditMap );
        actionLogDto.setPlatform( getPlatform().name() );
        return actionLogDto;
    }
}
