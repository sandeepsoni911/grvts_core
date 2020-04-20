package com.owners.gravitas.delegate;

import static com.owners.gravitas.constants.Constants.ACTIVITI_EXCEPTION;
import static com.owners.gravitas.enums.ErrorCode.SCRIPTED_FLOW_CALL_ACTION_REMINDER_ERROR;
import static com.owners.gravitas.enums.PushNotificationType.SCRIPTED_FLOW_CALL_REMINDER;
import static com.owners.gravitas.util.StringUtils.convertObjectToString;

import org.flowable.engine.delegate.BpmnError;
import org.flowable.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.ActionFlowBusinessService;
import com.owners.gravitas.domain.entity.OpportunityAction;

/**
 * Activiti service task sends push notification.
 *
 * @author amits
 *
 */
@Component( "scriptedFlowCallTask" )
public class ScriptedFlowCallTask extends BaseServiceTask {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( ScriptedFlowCallTask.class );

    /** The action flow business service. */
    @Autowired
    private ActionFlowBusinessService actionFlowBusinessService;

    /**
     * This method checks the is complete status of Call Client action and if it
     * is not completed then sends push notification to the agent.
     */
    @Override
    public void execute( final DelegateExecution execution ) {
        try {
            LOGGER.info( "Push notification reminder for Call action to : "
                    + runtimeService.getVariable( execution.getId(), "agentId" ) + " action "
                    + runtimeService.getVariable( execution.getId(), "actionGroupId" ) );
            final String agentId = convertObjectToString( runtimeService.getVariable( execution.getId(), "agentId" ) );
            final OpportunityAction action = actionFlowBusinessService.getOpportunityAction(
                    convertObjectToString( runtimeService.getVariable( execution.getId(), "actionGroupId" ) ), "1" );
            if (null != action && !action.isCompleted() && !action.isDeleted()) {
                actionFlowBusinessService.sendOpportunityPushNotifications( agentId, SCRIPTED_FLOW_CALL_REMINDER );
            }
        } catch ( final Exception e ) {
        	LOGGER.error("Push notification reminder for Call action failed" + e.getMessage(), e);
            runtimeService.setVariable( execution.getId(), ACTIVITI_EXCEPTION, e );
            throw new BpmnError( SCRIPTED_FLOW_CALL_ACTION_REMINDER_ERROR.getCode(), e.getMessage() );
        }
    }
}
