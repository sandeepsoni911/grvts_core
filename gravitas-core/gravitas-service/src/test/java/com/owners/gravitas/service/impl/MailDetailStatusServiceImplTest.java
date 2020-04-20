package com.owners.gravitas.service.impl;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.email.data.EmailDataFilter;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.NotificationResponseObject;
import com.owners.gravitas.util.JsonUtil;

/**
 * The Class MailDetailStatusServiceImplTest.
 *
 * @author javeedsy
 */
@PrepareForTest( JsonUtil.class )
public class MailDetailStatusServiceImplTest extends AbstractBaseMockitoTest {

    /** The Mail Details Status Service impl. */
    @InjectMocks
    private MailDetailStatusServiceImpl mailDetailStatusServiceImpl;

    /** The rest template. */
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ResponseEntity< String > object = new ResponseEntity< String >( HttpStatus.OK );

    /**
     * Test save.
     */
    @Test
    public void testGetNotificationFeedback_withNullRequestIdList() {

        final EmailDataFilter emailDataFilter = new EmailDataFilter();

        NotificationResponseObject notificationResponseObject = mailDetailStatusServiceImpl
                .getNotificationFeedback( emailDataFilter );

        assertNull( notificationResponseObject );

    }

    /**
     * Test save.
     */
    @Test
    public void testGetNotificationFeedback_withRequestIdList() {
        final String notificationEngineURL = null;
        final EmailDataFilter emailDataFilter = new EmailDataFilter();
        final List< String > requestIdList = new ArrayList< String >();
        final String sampleNotificationResponse = "Sample Response Json";
        final NotificationResponseObject notificationResponseExpectedObject = new NotificationResponseObject();
        requestIdList.add( "RequestId1" );
        emailDataFilter.setRequestIdList( requestIdList );

        final HttpEntity< EmailDataFilter > request = new HttpEntity<>( emailDataFilter );

        when( restTemplate.exchange( notificationEngineURL, HttpMethod.POST, request, String.class ) )
                .thenReturn( object );
        when( object.getBody() ).thenReturn( sampleNotificationResponse );
        PowerMockito.mockStatic( JsonUtil.class );
        PowerMockito.when( JsonUtil.getFromJson( sampleNotificationResponse, NotificationResponseObject.class ) )
                .thenReturn( notificationResponseExpectedObject );

        final NotificationResponseObject notificationResponseActualObject = mailDetailStatusServiceImpl
                .getNotificationFeedback( emailDataFilter );

        verify( restTemplate ).exchange( notificationEngineURL, HttpMethod.POST, request, String.class );
        assertEquals( notificationResponseActualObject, notificationResponseExpectedObject );
    }

}
