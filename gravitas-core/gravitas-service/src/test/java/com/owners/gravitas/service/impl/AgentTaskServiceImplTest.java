package com.owners.gravitas.service.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.AgentContactDao;
import com.owners.gravitas.dao.AgentTaskDao;
import com.owners.gravitas.dao.SearchDao;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.domain.entity.AgentTask;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.repository.AgentTaskRepository;
import com.owners.gravitas.service.ContactService;

// TODO: Auto-generated Javadoc
/**
 * The Class AgentTaskServiceImplTest.
 *
 * @author vishwanathm
 */
public class AgentTaskServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent task service impl. */
    @InjectMocks
    private AgentTaskServiceImpl agentTaskServiceImpl;

    /** The agent task dao. */
    @Mock
    private AgentTaskDao agentTaskDao;

    /** The search dao. */
    @Mock
    private SearchDao searchDao;

    /** The agent contact dao impl. */
    @Mock
    private AgentContactDao agentContactDao;

    /** The contact service impl. */
    @Mock
    private ContactService contactService;

    /** The agent task repository. */
    @Mock
    private AgentTaskRepository agentTaskRepository;

    /**
     * Test save agent task.
     */
    @Test
    public void testSaveAgentTask() {
        final PostResponse ps = new PostResponse();
        ps.setName( "name" );
        Mockito.when( agentTaskDao.saveAgentTask( Mockito.any( Task.class ), Mockito.anyString() ) ).thenReturn( ps );
        final PostResponse resp = agentTaskServiceImpl.saveAgentTask( new Task(), "agentId" );
        Mockito.verify( agentTaskDao ).saveAgentTask( Mockito.any( Task.class ), Mockito.anyString() );
        Assert.assertNotNull( resp );
        Assert.assertNotNull( resp.getName() );
    }

    /**
     * Test patch agent task.
     */
    @Test
    public void testPatchAgentTask() {
        Mockito.when( agentTaskDao.patchAgentTask( Mockito.anyString(), Mockito.anyString(), Mockito.anyMap() ) )
                .thenReturn( new Task() );
        final Task resp = agentTaskServiceImpl.patchAgentTask( "taskId", "agentId", new HashMap< String, Object >() );
        Mockito.verify( agentTaskDao ).patchAgentTask( Mockito.anyString(), Mockito.anyString(), Mockito.anyMap() );
        Assert.assertNotNull( resp );
    }

    /**
     * Test save agent tasks.
     */
    @Test
    public void testSaveAgentTasks() {
        Mockito.when( agentTaskDao.saveAgentTasks( Mockito.anyMap(), Mockito.anyString() ) )
                .thenReturn( new PostResponse() );
        agentTaskServiceImpl.saveAgentTasks( new HashMap<>(), "agentId" );
        Mockito.verify( agentTaskDao ).saveAgentTasks( Mockito.anyMap(), Mockito.anyString() );
    }

    /**
     * Test get tasks by opportunity id.
     */
    @Test
    public void testGetTasksByOpportunityId() {
        Mockito.when( agentTaskDao.getTasksByOpportunityId( Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( new HashMap<>() );
        final Map< String, Task > map = agentTaskServiceImpl.getTasksByOpportunityId( "agentId", "opportunityId" );
        Assert.assertNotNull( map );
        Mockito.verify( agentTaskDao ).getTasksByOpportunityId( Mockito.anyString(), Mockito.anyString() );

    }

    /**
     * Test get task by id.
     */
    @Test
    public void testGetTaskById() {
        final Task actualTask = new Task();
        Mockito.when( agentTaskDao.getTaskById( Mockito.anyString(), Mockito.anyString() ) ).thenReturn( actualTask );
        final Task expectedTask = agentTaskServiceImpl.getTaskById( "agentId", "taskId" );
        Assert.assertNotNull( actualTask );
        Mockito.verify( agentTaskDao ).getTaskById( Mockito.anyString(), Mockito.anyString() );
        Assert.assertEquals( actualTask, expectedTask );
    }

    /**
     * Test get task by id.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testGetTaskByIdWithTaskNull() {
        final Task actualTask = null;
        Mockito.when( agentTaskDao.getTaskById( Mockito.anyString(), Mockito.anyString() ) ).thenReturn( actualTask );
        agentTaskServiceImpl.getTaskById( "agentId", "taskId" );

    }

    /**
     * Test get task by id.
     */
    @Test
    public void testGetTaskByType() {
        Mockito.when( agentTaskDao.getTaskByType( Mockito.anyString(), Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( new HashMap< String, Task >() );
        final Map< String, Task > map = agentTaskServiceImpl.getTasksByType( "agentId", "taskId", "type" );
        Assert.assertNotNull( map );
        Mockito.verify( agentTaskDao ).getTaskByType( Mockito.anyString(), Mockito.anyString(), Mockito.anyString() );
    }

    /**
     * Test get open tasks by type.
     */
    @Test
    public void testGetOpenTasksByType() {
        final Map< String, Task > map = new HashMap< String, Task >();
        map.put( "test", new Task() );
        Mockito.when( agentTaskDao.getTaskByType( Mockito.anyString(), Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( map );
        final Map< String, Task > map1 = agentTaskServiceImpl.getOpenTasksByType( "agentId", "taskId", "type" );
        Assert.assertNotNull( map1 );
        Mockito.verify( agentTaskDao ).getTaskByType( Mockito.anyString(), Mockito.anyString(), Mockito.anyString() );
    }

    /**
     * Test get by task id.
     */
    @Test
    public void testGetByTaskId() {
        final String taskId = "test";
        final AgentTask expectedTask = new AgentTask();
        when( agentTaskRepository.findByTaskId( taskId ) ).thenReturn( expectedTask );
        final AgentTask actualTask = agentTaskServiceImpl.getByTaskId( taskId );
        assertEquals( actualTask, expectedTask );
        verify( agentTaskRepository ).findByTaskId( taskId );
    }

    /**
     * Test save agent task db.
     */
    @Test
    public void testSaveAgentTaskDb() {
        final AgentTask expectedTask = new AgentTask();
        when( agentTaskRepository.save( expectedTask ) ).thenReturn( expectedTask );
        final AgentTask actualTask = agentTaskServiceImpl.saveAgentTask( expectedTask );
        assertEquals( actualTask, expectedTask );
        verify( agentTaskRepository ).save( expectedTask );
    }
}
