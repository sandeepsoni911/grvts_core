package com.owners.gravitas.business.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.AgentNotificationBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.Reminder;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.request.NotificationRequest;
import com.owners.gravitas.dto.request.ReminderRequest;
import com.owners.gravitas.dto.response.AgentResponse;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.enums.PushNotificationType;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.AgentContactService;
import com.owners.gravitas.service.AgentReminderService;
import com.owners.gravitas.service.AgentService;
import com.owners.gravitas.service.AgentTaskService;
import com.owners.gravitas.service.SearchService;

/**
 * The Class AgentReminderBusinessServiceImplTest.
 *
 * @author ankusht
 */
public class AgentReminderBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent reminder service. */
    @Mock
    private AgentReminderService agentReminderService;

    /** The agent notification business service. */
    @Mock
    private AgentNotificationBusinessService agentNotificationBusinessService;

    /** The agent task service. */
    @Mock
    private AgentTaskService agentTaskService;

    /** The search service. */
    @Mock
    private SearchService searchService;

    /** The agent contact service. */
    @Mock
    private AgentContactService agentContactService;

    /** The AgentHolder dao. */
    @Mock
    private AgentService agentService;

    /** The agent reminder task. */
    @Mock
    private AgentReminderTaskImpl agentReminderTask;

    /** The agent reminder business service impl. */
    @InjectMocks
    private AgentReminderBusinessServiceImpl agentReminderBusinessServiceImpl;

    /**
     * Should send new opportunity reminder.
     */
    @Test
    public void shouldSendNewOppReminder() {
        final Set< String > agentIds = new HashSet<>( Arrays.asList( new String[] { "id1" } ) );
        when( agentService.getAllAgentIds() ).thenReturn( agentIds );
        doNothing().when( agentReminderTask ).sendNewOppNotification( anyString() );
        agentReminderBusinessServiceImpl.sendNewOppReminder();
        verify( agentService ).getAllAgentIds();
        verify( agentReminderTask ).sendNewOppNotification( anyString() );
    }

    /**
     * Should send claimed opp reminder.
     */
    @Test
    public void shouldSendClaimedOppReminder() {
        final Set< String > agentIds = new HashSet<>( Arrays.asList( new String[] { "id1" } ) );
        when( agentService.getAllAgentIds() ).thenReturn( agentIds );
        doNothing().when( agentReminderTask ).sendClaimedOppNotification( anyString() );
        agentReminderBusinessServiceImpl.sendClaimedOppReminder();
        verify( agentService ).getAllAgentIds();
        verify( agentReminderTask ).sendClaimedOppNotification( anyString() );
    }

    /**
     * Should set reminder for valid inputs.
     */
    @Test
    public void shouldSetReminderForValidInputs() {
        final String agentId = "dummy agent id";
        final String taskId = "dummy task id";
        final String contactId = "dummy contact id";
        final ReminderRequest request = new ReminderRequest();
        request.setTriggerDtm( new Date() );
        final Task task = new Task();
        final String opportunityId = "dummy opp id";
        task.setOpportunityId( opportunityId );
        final PostResponse reminderResponse = new PostResponse();
        final String name = "dummy name";
        reminderResponse.setName( name );
        final AgentResponse response = new AgentResponse( name );
        final Search search = new Search();
        search.setAgentId( agentId );
        search.setContactId( contactId );

        when( agentTaskService.getTaskById( agentId, taskId ) ).thenReturn( task );
        when( searchService.searchByOpportunityId( opportunityId ) ).thenReturn( search );
        when( agentNotificationBusinessService.sendPushNotification( anyString(), any( NotificationRequest.class ) ) )
                .thenReturn( new ArrayList<>() );
        when( agentReminderService.setReminder( anyString(), anyString(), any( Reminder.class ) ) )
                .thenReturn( reminderResponse );
        when( agentContactService.getContactById( agentId, contactId ) )
                .thenReturn( new com.owners.gravitas.domain.Contact() );
        final AgentResponse agentResponse = agentReminderBusinessServiceImpl.setReminder( agentId, taskId, request );
        assertEquals( response.getId(), agentResponse.getId() );
    }

    /**
     * Should set reminder even if opportunity not found.
     */
    @Test
    public void shouldSetReminderEvenIfOpportunityNotFound() {
        final String agentId = "dummy agent id";
        final String taskId = "dummy task id";
        final ReminderRequest request = new ReminderRequest();
        request.setTriggerDtm( new Date() );
        final Task task = new Task();
        final String opportunityId = "dummy opp id";
        task.setOpportunityId( opportunityId );
        final PostResponse reminderResponse = new PostResponse();
        final String name = "dummy name";
        reminderResponse.setName( name );
        final AgentResponse response = new AgentResponse( name );

        when( agentTaskService.getTaskById( agentId, taskId ) ).thenReturn( task );
        when( searchService.searchByOpportunityId( opportunityId ) ).thenReturn( null );
        when( agentNotificationBusinessService.sendPushNotification( anyString(), any( NotificationRequest.class ) ) )
                .thenReturn( new ArrayList<>() );
        when( agentReminderService.setReminder( anyString(), anyString(), any( Reminder.class ) ) )
                .thenReturn( reminderResponse );
        final AgentResponse agentResponse = agentReminderBusinessServiceImpl.setReminder( agentId, taskId, request );
        assertEquals( response.getId(), agentResponse.getId() );
        verifyNoMoreInteractions( agentContactService );
    }

    /**
     * Test set task reminder.
     */
    @Test
    public void testSetTaskReminder() {
        final String agentId = "dummy agent id";
        final String opportunityId = "dummy opp id";
        final String taskId = "dummy task id";
        final String name = "dummy name";
        final Task task = new Task();
        final PushNotificationType type = PushNotificationType.NEW_OPPORTUNITY;
        final PostResponse reminderResponse = new PostResponse();
        reminderResponse.setName( name );
        final AgentResponse expectedResponse = new AgentResponse( name );

        when( searchService.searchByOpportunityId( opportunityId ) ).thenReturn( null );
        when( agentNotificationBusinessService.sendPushNotification( anyString(), any( NotificationRequest.class ) ) )
                .thenReturn( new ArrayList<>() );
        when( agentReminderService.setReminder( anyString(), anyString(), any( Reminder.class ) ) )
                .thenReturn( reminderResponse );

        final AgentResponse actualAgentResponse = agentReminderBusinessServiceImpl.setTaskReminder( agentId,
                opportunityId, taskId, task, type );

        verify( searchService ).searchByOpportunityId( opportunityId );
        verify( agentNotificationBusinessService ).sendPushNotification( anyString(),
                any( NotificationRequest.class ) );
        verify( agentReminderService ).setReminder( anyString(), anyString(), any( Reminder.class ) );
        Assert.assertEquals( actualAgentResponse.getId(), expectedResponse.getId() );
        Assert.assertEquals( actualAgentResponse.getMessage(), expectedResponse.getMessage() );
        Assert.assertEquals( actualAgentResponse.getStatus(), expectedResponse.getStatus() );
    }

    /**
     * Test update reminder should return agent response when notification id is
     * not provided.
     */
    @Test
    public void testUpdateReminder_shouldReturnAgentResponse_whenNotificationIdNotProvided() {

        final String agentId = "dummy agent id";
        final String reminderId = "dummy reminder id";
        final String taskId = "dummy task id";
        final ReminderRequest updateReminder = new ReminderRequest();
        updateReminder.setTriggerDtm( new Date() );
        final Reminder reminder = new Reminder();
        final AgentResponse expectedResponse = new AgentResponse( reminderId );

        when( agentReminderService.getReminder( agentId, taskId, reminderId ) ).thenReturn( reminder );

        final AgentResponse actualAgentResponse = agentReminderBusinessServiceImpl.updateReminder( agentId, taskId,
                reminderId, updateReminder );

        verify( agentReminderService ).getReminder( agentId, taskId, reminderId );
        verifyZeroInteractions( agentNotificationBusinessService );
        Assert.assertEquals( actualAgentResponse.getId(), expectedResponse.getId() );
        Assert.assertEquals( actualAgentResponse.getMessage(), expectedResponse.getMessage() );
        Assert.assertEquals( actualAgentResponse.getStatus(), expectedResponse.getStatus() );
    }

    /**
     * Test update reminder should return agent response when notification id is
     * provided.
     */
    @Test
    public void testUpdateReminder_shouldReturnAgentResponse_whenNotificationIdProvided() {

        final String agentId = "dummy agent id";
        final String reminderId = "dummy reminder id";
        final String taskId = "dummy task id";
        final Date triggerDtm = new Date();
        final ReminderRequest updateReminder = new ReminderRequest();
        updateReminder.setTriggerDtm( triggerDtm );
        final List< String > notificationIds = new ArrayList<>();
        notificationIds.add( "dummy notification id" );
        final Reminder reminder = new Reminder();
        reminder.setNotificationIds( notificationIds );
        final AgentResponse expectedResponse = new AgentResponse( reminderId );

        when( agentReminderService.getReminder( agentId, taskId, reminderId ) ).thenReturn( reminder );

        final AgentResponse actualAgentResponse = agentReminderBusinessServiceImpl.updateReminder( agentId, taskId,
                reminderId, updateReminder );

        verify( agentNotificationBusinessService ).updatePushNotification( notificationIds.get( 0 ),
                triggerDtm.getTime() );
        verify( agentReminderService ).getReminder( agentId, taskId, reminderId );
        Assert.assertEquals( actualAgentResponse.getId(), expectedResponse.getId() );
        Assert.assertEquals( actualAgentResponse.getMessage(), expectedResponse.getMessage() );
        Assert.assertEquals( actualAgentResponse.getStatus(), expectedResponse.getStatus() );
    }

    /**
     * Test update reminder should throw ApplicationException.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testUpdateReminder_shouldThrowException() {

        final String agentId = "dummy agent id";
        final String reminderId = "dummy reminder id";
        final String taskId = "dummy task id";
        final ReminderRequest updateReminder = new ReminderRequest();
        updateReminder.setTriggerDtm( new Date() );

        when( agentReminderService.getReminder( agentId, taskId, reminderId ) ).thenReturn( null );

        agentReminderBusinessServiceImpl.updateReminder( agentId, taskId, reminderId, updateReminder );
    }

    /**
     * Test update and cancel reminder.
     */
    @Test
    public void testUpdateAndCancelReminder() {
        final String agentId = "dummy agent id";
        final String taskId = "dummy task id";
        final Date triggerDtm = new Date();
        final Task task = new Task();
        task.setDueDtm( triggerDtm.getTime() );
        final Map< String, Reminder > reminders = new HashMap<>();
        reminders.put( "key", new Reminder( triggerDtm.getTime(), null ) );

        when( agentTaskService.getTaskById( agentId, taskId ) ).thenReturn( task );
        when( agentReminderService.getTaskReminders( agentId, taskId ) ).thenReturn( reminders );

        agentReminderBusinessServiceImpl.updateAndCancelReminder( agentId, taskId, triggerDtm.getTime() );

        verify( agentTaskService ).getTaskById( agentId, taskId );
        verify( agentReminderService ).getTaskReminders( agentId, taskId );
    }

    /**
     * Test update and cancel reminder when reminders is empty.
     */
    @Test
    public void testUpdateAndCancelReminder_whenRemindersIsEmpty() {
        final String agentId = "dummy agent id";
        final String taskId = "dummy task id";
        final Date triggerDtm = new Date();

        when( agentTaskService.getTaskById( agentId, taskId ) ).thenReturn( new Task() );
        when( agentReminderService.getTaskReminders( agentId, taskId ) ).thenReturn( new HashMap<>() );

        agentReminderBusinessServiceImpl.updateAndCancelReminder( agentId, taskId, triggerDtm.getTime() );

        verify( agentTaskService ).getTaskById( agentId, taskId );
        verify( agentReminderService ).getTaskReminders( agentId, taskId );
    }

    /**
     * Test update and cancel reminder when time is different.
     */
    @Test
    public void testUpdateAndCancelReminder_whenTimeIsDifferent() {
        final String agentId = "dummy agent id";
        final String taskId = "dummy task id";
        final Date triggerDtm = new Date();
        final Task task = new Task();
        task.setDueDtm( 123456L );
        final Map< String, Reminder > reminders = new HashMap<>();
        reminders.put( "key", new Reminder( triggerDtm.getTime(), null ) );

        when( agentTaskService.getTaskById( agentId, taskId ) ).thenReturn( task );
        when( agentReminderService.getTaskReminders( agentId, taskId ) ).thenReturn( reminders );

        agentReminderBusinessServiceImpl.updateAndCancelReminder( agentId, taskId, triggerDtm.getTime() );

        verify( agentTaskService ).getTaskById( agentId, taskId );
        verify( agentReminderService ).getTaskReminders( agentId, taskId );
    }

    /**
     * Test cancel reminders.
     */
    @Test
    public void testCancelReminders() {
        final String agentId = "dummy agent id";
        final String taskId = "dummy task id";
        final List< String > notificationIds = new ArrayList<>();
        notificationIds.add( "dummy notification id" );
        final Reminder reminder = new Reminder();
        reminder.setNotificationIds( notificationIds );
        final Map< String, Reminder > reminders = new HashMap<>();
        reminders.put( "key", reminder );

        agentReminderBusinessServiceImpl.cancelReminders( agentId, taskId, reminders );
        verify( agentNotificationBusinessService ).cancelPushNotification( notificationIds.get( 0 ) );
    }

    /**
     * Test cancel agent task reminders.
     */
    @Test
    public void testCancelAgentTaskReminders() {
        final String agentId = "dummy agent id";
        final String taskId = "dummy task id";

        when( agentReminderService.getTaskReminders( agentId, taskId ) ).thenReturn( new HashMap<>() );

        agentReminderBusinessServiceImpl.cancelAgentTaskReminders( agentId, taskId );
        verify( agentReminderService ).getTaskReminders( agentId, taskId );
    }

    /**
     * Test push reminder notification.
     */
    @Test
    public void testPushReminderNotification() {
        final String agentId = "";
        final String taskId = "";
        final Contact contact = new Contact();
        final Long dueDtm = new Date().getTime();
        final Task task = new Task();
        final PushNotificationType type = PushNotificationType.NEW_REQUEST;
        final List< String > notificationIds = new ArrayList<>();
        notificationIds.add( "dummy id" );

        when( agentNotificationBusinessService.sendPushNotification( anyString(), any( NotificationRequest.class ) ) )
                .thenReturn( notificationIds );
        final List< String > pushReminderNotification = agentReminderBusinessServiceImpl
                .pushReminderNotification( agentId, taskId, contact, dueDtm, task, type );

        Assert.assertEquals( notificationIds, pushReminderNotification );
    }

    /**
     * Test push reminder notification when contact is null.
     */
    @Test
    public void testPushReminderNotification_whenContactIsNull() {
        final String agentId = "";
        final String taskId = "";
        final Contact contact = null;
        final Long dueDtm = new Date().getTime();
        final Task task = new Task();
        final PushNotificationType type = PushNotificationType.NEW_REQUEST;
        final List< String > notificationIds = new ArrayList<>();
        notificationIds.add( "dummy id" );

        when( agentNotificationBusinessService.sendPushNotification( anyString(), any( NotificationRequest.class ) ) )
                .thenReturn( notificationIds );
        final List< String > pushReminderNotification = agentReminderBusinessServiceImpl
                .pushReminderNotification( agentId, taskId, contact, dueDtm, task, type );

        Assert.assertEquals( notificationIds, pushReminderNotification );

    }

    /**
     * Test send action flow incomplete reminder when no agents available.
     */
    @Test
    public void testSendActionFlowIncompleteReminderWhenNoAgentsAvailable() {
        when( agentService.getAllAgentIds() ).thenReturn( new HashSet<>() );
        agentReminderBusinessServiceImpl.sendActionFlowIncompleteReminder();
        verify( agentService ).getAllAgentIds();
        verifyZeroInteractions( agentReminderTask );
    }

    /**
     * Test send action flow incomplete reminder when agents available.
     */
    @Test
    public void testSendActionFlowIncompleteReminderWhenAgentsAvailable() {
        final String agentId = "agentId";
        final Set< String > agents = new HashSet<>();
        agents.add( agentId );
        when( agentService.getAllAgentIds() ).thenReturn( agents );
        agentReminderBusinessServiceImpl.sendActionFlowIncompleteReminder();
        verify( agentService ).getAllAgentIds();
        verify( agentReminderTask ).sendActionFlowIncompleteReminder( agentId );
    }

    /**
     * Test delete reminder.
     */
    @Test
    public void testDeleteReminder() {

        final String agentId = "agent1";
        final String taskId = "task2";
        final String reminderId = "remind3";
        final Long currentTimeMillis = new DateTime().getMillis();
        final Reminder reminder = new Reminder( currentTimeMillis + 1000, null );

        when( agentReminderService.getReminder( Mockito.anyString(), Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( reminder );
        when( agentReminderService.deleteReminder( Mockito.anyString(), Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( null );
        final AgentResponse response = agentReminderBusinessServiceImpl.deleteReminder( agentId, taskId, reminderId );
        Assert.assertEquals( response.getStatus(), Status.SUCCESS );
    }

    /**
     * Test delete reminder with past reminder.
     */
    @Test
    public void testDeleteReminder_with_Past_Reminder() {

        final String agentId = "agent1";
        final String taskId = "task2";
        final String reminderId = "remind3";
        final Long currentTimeMillis = new DateTime().getMillis();
        final Reminder reminder = new Reminder( currentTimeMillis - 1000, null );

        when( agentReminderService.getReminder( Mockito.anyString(), Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( reminder );
        final AgentResponse response = agentReminderBusinessServiceImpl.deleteReminder( agentId, taskId, reminderId );
        Assert.assertEquals( response.getStatus(), Status.FAILURE );
    }

    /**
     * Test delete reminder with null reminder.
     */
    @Test
    public void testDeleteReminder_with_null_Reminder() {
        final String agentId = "agent1";
        final String taskId = "task2";
        final String reminderId = "remind3";
        final Reminder reminder = null;

        when( agentReminderService.getReminder( Mockito.anyString(), Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( null );
        final AgentResponse response = agentReminderBusinessServiceImpl.deleteReminder( agentId, taskId, reminderId );
        Assert.assertEquals( response.getStatus(), Status.FAILURE );
    }

}
