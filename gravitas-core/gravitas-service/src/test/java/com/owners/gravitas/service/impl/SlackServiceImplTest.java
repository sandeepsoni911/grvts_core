/**
 *
 */
package com.owners.gravitas.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.request.SlackRequest;

/**
 * The Class SlackServiceImplTest.
 *
 * @author harshads
 */
public class SlackServiceImplTest extends AbstractBaseMockitoTest {

    /** The slack service impl. */
    @InjectMocks
    private SlackServiceImpl slackServiceImpl;

    /** The rest template. */
    @Mock
    private RestTemplate restTemplate;

    /**
     * Publish to slack test.
     */
    @Test
    public void publishToSlackTest() {
        Mockito.when( restTemplate.postForObject( Mockito.anyString(), Mockito.any( SlackRequest.class ),
                Mockito.any( String.class.getClass() ) ) ).thenReturn( "OK" );
        slackServiceImpl.publishToSlack( new SlackRequest(), "test" );
        Mockito.verify( restTemplate ).postForObject( Mockito.anyString(), Mockito.any( SlackRequest.class ),
                Mockito.any( String.class.getClass() ) );
    }

}
