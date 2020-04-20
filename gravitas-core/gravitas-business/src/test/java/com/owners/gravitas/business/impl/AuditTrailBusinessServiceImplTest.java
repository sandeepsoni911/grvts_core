package com.owners.gravitas.business.impl;

import static com.owners.gravitas.constants.Constants.ACTION_OBJ;
import static com.owners.gravitas.constants.Constants.AGENT_ID;
import static com.owners.gravitas.enums.ActionEntity.AGENT;
import static com.owners.gravitas.enums.ActionEntity.AGENT_NOTE;
import static com.owners.gravitas.enums.ActionEntity.TASK;

import java.lang.annotation.Annotation;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mobile.device.DevicePlatform;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.owners.gravitas.annotation.Audit;
import com.owners.gravitas.business.ActionLogBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.AuditTrailConfig;
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.AgentHolder;
import com.owners.gravitas.domain.AgentInfo;
import com.owners.gravitas.domain.Contact;
import com.owners.gravitas.domain.Device;
import com.owners.gravitas.domain.Note;
import com.owners.gravitas.domain.Opportunity;
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
 * The Class AuditTrailBusinessServiceImplTest.
 */
public class AuditTrailBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The audit trail business service impl. */
    @InjectMocks
    private AuditTrailBusinessServiceImpl auditTrailBusinessServiceImpl;

    /** The action log business service. */
    @Mock
    private ActionLogBusinessService actionLogBusinessService;

    /** The agent service. */
    @Mock
    private AgentService agentService;

    /** The audit trail config. */
    @Mock
    private AuditTrailConfig auditTrailConfig;

    /** The request. */
    @Mock
    private HttpServletRequest request;

    /**
     * Test create audit log.
     *
     * @param joinPointArgs
     *            the join point args
     * @param audit
     *            the audit
     * @param returnObj
     *            the return obj
     */
    @Test( dataProvider = "loadAuditLoggingParams" )
    public void testCreateAuditLog( final Object[] joinPointArgs, final Audit audit, final Object returnObj ) {
        Mockito.when( auditTrailConfig.isEnableAutoAuditTrail() ).thenReturn( true );
        Mockito.when( agentService.getAgentById( Mockito.anyString() ) ).thenReturn( getAgent() );
        Mockito.doNothing().when( actionLogBusinessService ).auditForAction( Mockito.any( ActionLogDto.class ) );
        RequestAttributes requestAttributes = new ServletRequestAttributes( request );
        Mockito.when( request.getAttribute( Mockito.anyString() ) ).thenReturn( getDevice() );
        RequestContextHolder.setRequestAttributes( requestAttributes );
        auditTrailBusinessServiceImpl.createAuditLog( joinPointArgs, audit, returnObj );

        if (ActionEntity.AGENT_NOTE.equals( audit.type() )) {
            Mockito.verify( actionLogBusinessService ).auditForAction( Mockito.any( ActionLogDto.class ) );
        }
        if (ActionEntity.AGENT.equals( audit.type() )) {
            Mockito.verify( actionLogBusinessService ).agentAuditForAction( Mockito.anyList() );
        }
    }

    /**
     * Test create audit log_ audit disabled.
     */
    @Test
    public void testCreateAuditLog_AuditDisabled() {
        Mockito.when( auditTrailConfig.isEnableAutoAuditTrail() ).thenReturn( false );
        auditTrailBusinessServiceImpl.createAuditLog( null, null, null );
        Mockito.verify( actionLogBusinessService, Mockito.times( 0 ) )
                .auditForAction( Mockito.any( ActionLogDto.class ) );
    }

    /**
     * Test create audit log_ no args.
     */
    @Test
    public void testCreateAuditLog_NoArgs() {
        Mockito.when( auditTrailConfig.isEnableAutoAuditTrail() ).thenReturn( true );
        auditTrailBusinessServiceImpl.createAuditLog( new Object[] {}, getAudit( null, new String[] {} ), null );
        Mockito.verify( actionLogBusinessService, Mockito.times( 0 ) )
                .auditForAction( Mockito.any( ActionLogDto.class ) );
    }

    /**
     * Test save audit for action.
     */
    @Test
    public void testSaveAuditForAction() {
        Mockito.when( auditTrailConfig.isEnableAutoAuditTrail() ).thenReturn( true );
        Mockito.doNothing().when( actionLogBusinessService ).auditForAction( Mockito.any( ActionLogDto.class ) );
        auditTrailBusinessServiceImpl.saveAuditForAction( new ActionLogDto() );

        Mockito.verify( actionLogBusinessService, Mockito.times( 1 ) )
                .auditForAction( Mockito.any( ActionLogDto.class ) );
    }

    /**
     * Test save audit for action_ audit disbaled.
     */
    @Test
    public void testSaveAuditForAction_AuditDisbaled() {
        Mockito.when( auditTrailConfig.isEnableAutoAuditTrail() ).thenReturn( false );
        Mockito.doNothing().when( actionLogBusinessService ).auditForAction( Mockito.any( ActionLogDto.class ) );
        auditTrailBusinessServiceImpl.saveAuditForAction( new ActionLogDto() );

        Mockito.verify( actionLogBusinessService, Mockito.times( 0 ) )
                .auditForAction( Mockito.any( ActionLogDto.class ) );
    }

    /**
     * Testget action log dto.
     */
    @Test
    public void testgetActionLogDto() {
        RequestAttributes requestAttributes = new ServletRequestAttributes( request );
        RequestContextHolder.setRequestAttributes( requestAttributes );
        Mockito.when( request.getAttribute( Mockito.anyString() ) ).thenReturn( getDevice1() );

        ActionLogDto dto = auditTrailBusinessServiceImpl.getActionLogDto( ActionType.CREATE, "actionBy",
                ( new Note() ).toAuditMap(), ActionEntity.AGENT_NOTE, "actionEntityId " );
        Assert.assertNotNull( dto );
        Assert.assertEquals( dto.getPlatform(), Platform.AGENT_APP.name() );
    }

    /**
     * Load audit logging params.
     *
     * @return the object[][]
     */
    @DataProvider( name = "loadAuditLoggingParams" )
    public Object[][] loadAuditLoggingParams() {
        final Object[] argsValues = new Object[] { new Note(), "agentId" };
        final Object[] reminderArgsValues = new Object[] { new Reminder(), "agentId" };
        final Object[] taskargsValues = new Object[] { new Task(), "agentId" };
        final Object[] agetntArgsValues = new Object[] { getAgentHolder() };
        final Object[] requestArgsValues = new Object[] { new Request(), "agentId" };
        final Object[] oppArgsValues = new Object[] { new Opportunity(), "agentId" };
        final Object[] contactArgsValues = new Object[] { new Contact(), "agentId" };
        final Object[] stageArgsValues = new Object[] { new Stage(), "agentId" };
        final Object[] deviceArgsValues = new Object[] { new Device(), "agentId" };
        final Object[] mapArgsValues = new Object[] { new HashMap<>(), "agentId" };

        final PostResponse response = new PostResponse();
        response.setName( "noteId" );

        return new Object[][] { { argsValues, getAudit( AGENT_NOTE, new String[] { ACTION_OBJ, AGENT_ID } ), response },
                { taskargsValues, getAudit( TASK, new String[] { ACTION_OBJ, AGENT_ID } ), response },
                { requestArgsValues, getAudit( ActionEntity.REQUEST, new String[] { ACTION_OBJ, AGENT_ID } ),
                        response },
                { oppArgsValues, getAudit( ActionEntity.OPPORTUNITY, new String[] { ACTION_OBJ, AGENT_ID } ),
                        response },
                { contactArgsValues, getAudit( ActionEntity.CONTACT, new String[] { ACTION_OBJ, AGENT_ID } ),
                        response },
                { stageArgsValues, getAudit( ActionEntity.STAGE, new String[] { ACTION_OBJ, AGENT_ID } ), response },
                { deviceArgsValues, getAudit( ActionEntity.DEVICE, new String[] { ACTION_OBJ, AGENT_ID } ), response },
                { reminderArgsValues, getAudit( ActionEntity.REMINDER, new String[] { ACTION_OBJ, AGENT_ID } ),
                        response },
                { reminderArgsValues, getAudit( null, new String[] { ACTION_OBJ, AGENT_ID } ), response },
                { mapArgsValues, getAudit( ActionEntity.REMINDER, new String[] { ACTION_OBJ, AGENT_ID } ), response },
                { agetntArgsValues, getAudit( AGENT, new String[] { ACTION_OBJ } ), getAgent() } };
    }

    /**
     * Gets the device.
     *
     * @return the device
     */
    private org.springframework.mobile.device.Device getDevice() {
        return new org.springframework.mobile.device.Device() {
            public boolean isNormal() {
                return true;
            }

            public boolean isMobile() {
                return false;
            }

            public boolean isTablet() {
                return false;
            }

            public DevicePlatform getDevicePlatform() {
                return DevicePlatform.UNKNOWN;
            }
        };
    }

    /**
     * Gets the device1.
     *
     * @return the device1
     */
    private org.springframework.mobile.device.Device getDevice1() {
        return new org.springframework.mobile.device.Device() {
            public boolean isNormal() {
                return false;
            }

            public boolean isMobile() {
                return true;
            }

            public boolean isTablet() {
                return true;
            }

            public DevicePlatform getDevicePlatform() {
                return DevicePlatform.ANDROID;
            }
        };
    }

    /**
     * Gets the agent holder.
     *
     * @return the agent holder
     */
    private AgentHolder getAgentHolder() {
        AgentHolder agentHolder = new AgentHolder();
        agentHolder.setAgentId( "agentId" );
        agentHolder.setAgent( getAgent() );
        return agentHolder;
    }

    /**
     * Gets the agent.
     *
     * @return the agent
     */
    private Agent getAgent() {
        Agent agent = new Agent();
        agent.addAgentNote( "agentId", new Note() );
        AgentInfo info = new AgentInfo();
        info.setEmail( "email@email.com" );
        agent.setInfo( info );
        return agent;
    }

    /**
     * Gets the audit.
     *
     * @param et
     *            the et
     * @param args
     *            the args
     * @return the audit
     */
    private Audit getAudit( ActionEntity et, String[] args ) {
        return new Audit() {

            @Override
            public Class< ? extends Annotation > annotationType() {
                return Audit.class;
            }

            @Override
            public ActionType type() {
                return ActionType.CREATE;
            }

            @Override
            public ActionEntity entity() {
                return et;
            }

            @Override
            public String[] args() {
                return args;
            }
        };
    }
}
