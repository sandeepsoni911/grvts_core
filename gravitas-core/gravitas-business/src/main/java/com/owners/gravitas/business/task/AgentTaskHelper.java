package com.owners.gravitas.business.task;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.Task;
import com.owners.gravitas.domain.entity.AgentTask;
import com.owners.gravitas.service.AgentTaskService;

/**
 * The Class DataMigrationTask.
 *
 * @author
 */
@Service
public class AgentTaskHelper {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentTaskHelper.class );

    @Autowired
    private AgentTaskService agentTaskService;

    /**
     * Update task delete flag.
     *
     * @param taskId
     *            the task id
     * @param task
     *            the task
     */
    @Async( value = "apiExecutor" )
    @Transactional( propagation = REQUIRES_NEW )
    public void updateTaskDeleteFlag( final String taskId, final Task task ) {
        LOGGER.info( " updateTaskDeleteFlag is started for {}", taskId );
        Set< String > tasks = new HashSet<>();
        tasks.add( taskId );
        List< AgentTask > dbTasks = agentTaskService.getAgentTaskByTaskIds( tasks );
        LOGGER.info( "# of db task(s) found {}", dbTasks.size() );
        if (null != dbTasks && !dbTasks.isEmpty()) {
            final AgentTask dbTask = dbTasks.get( 0 );
            if (!dbTask.isDeleted()) {
                dbTask.setDeleted( task.getDeleted() );
                agentTaskService.saveAgentTask( dbTask );
                LOGGER.info( " updated successfull for {}", dbTask.getId() );
            }
        }
        LOGGER.info( " updateTaskDeleteFlag is ended for {}", taskId );
    }
}
