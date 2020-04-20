package com.owners.gravitas.business.builder;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.entity.AgentTask;
import com.owners.gravitas.dto.request.TaskUpdateRequest;

/**
 * The Class AgentTaskBuilder.
 *
 * @author raviz
 */
@Component
public class AgentTaskBuilder extends AbstractBuilder< TaskUpdateRequest, AgentTask > {

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public AgentTask convertTo( final TaskUpdateRequest source, final AgentTask destination ) {
        AgentTask agentTask = destination;
        if (source != null) {
            if (agentTask == null) {
                agentTask = new AgentTask();
            }

            if (source.getDueDtm() != null) {
                agentTask.setScheduledDtm( new DateTime( source.getDueDtm() ) );
            }
            if (isNotBlank( source.getDescription() )) {
                agentTask.setDescription( source.getDescription() );
            }
            if (isNotBlank( source.getLocation() )) {
                agentTask.setLocation( source.getLocation() );
            }
            if (isNotBlank( source.getTitle() )) {
                agentTask.setTitle( source.getTitle() );
            }
            if (isNotBlank( source.getStatus() )) {
                agentTask.setStatus( source.getStatus() );
            }
        }
        return agentTask;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public TaskUpdateRequest convertFrom( final AgentTask source, final TaskUpdateRequest destination ) {
        throw new UnsupportedOperationException();
    }

}
