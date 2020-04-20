
package com.owners.gravitas.service.task;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.Test;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.Directory.Users;
import com.google.api.services.admin.directory.Directory.Users.Get;
import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * @author madhavk
 *
 */

@PrepareForTest( { BatchRequest.class, HttpRequestFactory.class, Directory.class } )
@PowerMockIgnore( "javax.management.*" )
public class UserProfileTaskTest extends AbstractBaseMockitoTest {

    /** The user task. */
    @InjectMocks
    private UserProfileTask userTask;

    /** The users. */
    @Mock
    private Users users;

    /** The get. */
    @Mock
    private Get get;

    Directory directoryService = PowerMockito.mock( Directory.class );

    /**
     * Gets the google profile.
     *
     * @param emails
     *            the emails
     * @return the google profile
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    //@Test
    public void testGetGoogleProfile() throws IOException {
        ReflectionTestUtils.setField( userTask, "emailBatchSize", 100 );
        List< String > emails = new ArrayList<>();
        emails.add( "test@test.com" );
        BatchRequest batchRequest = PowerMockito.mock( BatchRequest.class );
        HttpRequestFactory httpRequestFactory = PowerMockito.mock( HttpRequestFactory.class );

        when( directoryService.getRequestFactory() ).thenReturn( httpRequestFactory );
        when( directoryService.batch() ).thenReturn( batchRequest );
        when( directoryService.users() ).thenReturn( users );
        when( users.get( anyString() ) ).thenReturn( get );
        when( get.setProjection( anyString() ) ).thenReturn( get );

        userTask.getUserProfiles( emails );

    }

}
