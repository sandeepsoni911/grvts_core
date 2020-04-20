package com.owners.gravitas.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.AgentReminderDao;
import com.owners.gravitas.domain.Reminder;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Class AgentReminderServiceImplTest.
 *
 * @author nishak
 */
public class AgentReminderServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent reminder service impl. */
    @InjectMocks
    private AgentReminderServiceImpl agentRequestServiceImpl;

    /** The reminder dao. */
    @Mock
    private AgentReminderDao agentReminderDao;

    /**
     * Test set reminder.
     */
    @Test
    public void testSetReminder() {
        Mockito.when( agentReminderDao.setReminder( Mockito.anyString(), Mockito.anyString(),
                Mockito.any( Reminder.class ) ) ).thenReturn( new PostResponse() );
        final PostResponse response = agentRequestServiceImpl.setReminder( "agentId", "taskId", new Reminder() );
        Assert.assertNotNull( response );
        Mockito.verify( agentReminderDao ).setReminder( Mockito.anyString(), Mockito.anyString(),
                Mockito.any( Reminder.class ) );
    }

    /**
     * Test patch reminder.
     */
    @Test
    public void testPatchReminder() {
        Mockito.when( agentReminderDao.patchReminder( Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf( String.class, Object.class ) ) ).thenReturn( new PostResponse() );
        final PostResponse response = agentRequestServiceImpl.patchReminder( "agentId", "taskId", "reminderId",
                new HashMap< String, Object >() );
        Assert.assertNotNull( response );
        Mockito.verify( agentReminderDao ).patchReminder( Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf( String.class, Object.class ) );

    }

    /**
     * Test get reminder.
     */
    @Test
    public void testGetReminder() {
        Mockito.when( agentReminderDao.getReminder( Mockito.anyString(), Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( new Reminder() );
        final Reminder reminder = agentRequestServiceImpl.getReminder( "agentId", "taskId", "reminderId" );
        Assert.assertNotNull( reminder );
        Mockito.verify( agentReminderDao ).getReminder( Mockito.anyString(), Mockito.anyString(), Mockito.anyString() );

    }

    /**
     * Test get task reminders.
     */
    @Test
    public void testGetTaskReminders() {
        Mockito.when( agentReminderDao.getTaskReminders( Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( new HashMap< String, Reminder >() );
        final Map< String, Reminder > map = agentRequestServiceImpl.getTaskReminders( "agentId", "taskId" );
        Assert.assertNotNull( map );
        Mockito.verify( agentReminderDao ).getTaskReminders( Mockito.anyString(), Mockito.anyString() );
    }

    /**
     * Test delete reminder.
     */
    @Test
    public void testDeleteReminder() {
        Mockito.when( agentReminderDao.deleteReminder( Mockito.anyString(), Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( new Reminder() );
        final Reminder reminder = agentRequestServiceImpl.deleteReminder( "agent1", "task2", "reminder3" );
        Assert.assertNotNull( reminder );
        Mockito.verify( agentReminderDao ).deleteReminder( Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString() );
    }

}
