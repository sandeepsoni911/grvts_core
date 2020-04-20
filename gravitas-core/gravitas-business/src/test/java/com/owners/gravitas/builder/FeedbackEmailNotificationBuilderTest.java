/**
 *
 */
package com.owners.gravitas.builder;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.FeedbackEmailNotificationBuilder;
import com.owners.gravitas.business.builder.request.SlackRequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.FeedbackEmailJmxConfig;
import com.owners.gravitas.dto.Property;
import com.owners.gravitas.dto.PropertyOrder;
import com.owners.gravitas.dto.Seller;
import com.owners.gravitas.dto.SellerAddress;
import com.owners.gravitas.dto.request.OpportunityRequest;
import com.owners.gravitas.dto.request.SlackRequest;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.AgentService;
import com.owners.gravitas.util.PropertiesUtil;

/**
 * The Class FeedbackEmailNotificationBuilderTest.
 *
 * @author shivamm
 */
public class FeedbackEmailNotificationBuilderTest extends AbstractBaseMockitoTest {

    /** The slack request builder. */
    @InjectMocks
    private FeedbackEmailNotificationBuilder feedbackEmailNotificationBuilder;

    /** The feedback email jmx config. */
    @Mock
    private FeedbackEmailJmxConfig feedbackEmailJmxConfig;

    /** The agent service. */
    @Mock
    private AgentService agentService;

    /**
     * Before test.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeTest
    public void beforeTest() throws Exception {
        final Map< String, String > props = new HashMap< String, String >();
        props.put( "security.key", "sd>f#$@(*&)*;Alt!%$EHUB" );
        if (PropertiesUtil.getPropertiesMap() == null) {
            PropertiesUtil.setPropertiesMap( props );
        } else {
            PropertiesUtil.getPropertiesMap().putAll( props );
        }
    }

    /**
     * Convert create email notification.
     */
    @Test
    public void convertCreateEmailNotification() {
        String agentEmail = "test";
        String recipient = "test";
        String emailTemplate = "test";
        String agentRatingId = "test";
        String fromEmail = "test";
        String hostUrl = "test";
        feedbackEmailJmxConfig.setFeedbackFromEmail( "test" );
        Map< String, Object > response = new HashMap<>();
        response.put( "Name", "test" );
        feedbackEmailJmxConfig.setNoReplyOwnersEmailAddress( "test" );
        when( agentService.getAgentDetails( agentEmail ) ).thenReturn( response );
        when( feedbackEmailJmxConfig.getFeedbackFromEmail() ).thenReturn( fromEmail );
        when( feedbackEmailJmxConfig.getFeedbackHostUrl() ).thenReturn( hostUrl );
        feedbackEmailNotificationBuilder.createEmailNotification( recipient, agentEmail, emailTemplate, agentRatingId );
        verify( feedbackEmailJmxConfig ).getNoReplyOwnersEmailAddress();
        verify( agentService ).getAgentDetails( agentEmail );
        verify( feedbackEmailJmxConfig ).getFeedbackFromEmail();
        verify( feedbackEmailJmxConfig ,times( 5 )).getFeedbackHostUrl();
    }

}
