package com.owners.gravitas.business.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.MessageSource;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.Test;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.owners.gravitas.business.BeanValidationService;
import com.owners.gravitas.business.builder.GmailMessageBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.request.EmailRequest;
import com.owners.gravitas.dto.response.AgentEmailResult;
import com.owners.gravitas.service.GmailService;

public class AgentEmailBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent email business service impl. */
    @InjectMocks
    private AgentEmailBusinessServiceImpl agentEmailBusinessServiceImpl;

    /** The gmail message builder. */
    @Mock
    private GmailMessageBuilder gmailMessageBuilder;

    /** The gmail service. */
    @Mock
    private GmailService gmailService;

    /** The message source. */
    @Mock
    private MessageSource messageSource;

    /** The gmail. */
    @Mock
    private Gmail gmail;

    /** The bean validation service. */
    @Mock
    private BeanValidationService beanValidationService;

    /**
     * Test send agent email when email request validated.
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testSendAgentEmailWhenEmailRequestValidated() throws InterruptedException, ExecutionException {
        EmailRequest emailRequest = new EmailRequest();
        String agentEmail = "agent@owners.com";
        List< String > testList = new ArrayList<>();
        testList.add( "test" );
        final Map< String, List< String > > failedContraints = new HashMap<>();
        failedContraints.put( "test", testList );

        when( beanValidationService.validate( emailRequest ) ).thenReturn( failedContraints );

        Future< AgentEmailResult > result = agentEmailBusinessServiceImpl.sendAgentEmail( emailRequest, gmail,
                agentEmail );

        assertEquals( result.get().getStatus(),
                ReflectionTestUtils.getField( AgentEmailBusinessServiceImpl.class, "ERROR" ) );
        assertNotNull( result );
        verify( beanValidationService ).validate( emailRequest );
        verifyZeroInteractions( gmailMessageBuilder );
    }

    /**
     * Test send agent email when email request not validated.
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testSendAgentEmailWhenEmailRequestNotValidated() throws InterruptedException, ExecutionException {
        EmailRequest emailRequest = new EmailRequest();
        String agentEmail = "agent@owners.com";
        final Map< String, List< String > > failedContraints = new HashMap<>();
        final Message message = new Message();
        List< String > sendTo = new ArrayList<>();
        sendTo.add( "test@test.com" );
        emailRequest.setTo( sendTo );

        when( beanValidationService.validate( emailRequest ) ).thenReturn( failedContraints );
        when( gmailMessageBuilder.convertTo( emailRequest ) ).thenReturn( message );
        Future< AgentEmailResult > result = agentEmailBusinessServiceImpl.sendAgentEmail( emailRequest, gmail,
                agentEmail );

        assertNotNull( result );
        assertEquals( result.get().getStatus(),
                ReflectionTestUtils.getField( AgentEmailBusinessServiceImpl.class, "SUCCESS" ) );
        verify( beanValidationService ).validate( emailRequest );

    }

    /**
     * Test send agent email should throw exception.
     * 
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testSendAgentEmailShouldThrowException() throws InterruptedException, ExecutionException {
        EmailRequest emailRequest = new EmailRequest();
        String agentEmail = "agent@owners.com";
        final Message message = new Message();
        List< String > sendTo = new ArrayList<>();
        sendTo.add( "test@test.com" );
        emailRequest.setTo( sendTo );
        when( gmailMessageBuilder.convertTo( emailRequest ) ).thenReturn( message );
        when( gmailService.sendEmail( gmail, message ) ).thenThrow( new RuntimeException() );
        Future< AgentEmailResult > result = agentEmailBusinessServiceImpl.sendAgentEmail( emailRequest, gmail,
                agentEmail );
        assertEquals( result.get().getStatus(),
                ReflectionTestUtils.getField( AgentEmailBusinessServiceImpl.class, "ERROR" ) );
    }

}
