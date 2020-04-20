package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.UserRole.FIREBASE_AGENT;
import static com.owners.gravitas.enums.GoogleFieldProjection.FULL;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.Test;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.Directory.Users;
import com.google.api.services.admin.directory.Directory.Users.Get;
import com.google.api.services.admin.directory.Directory.Users.Insert;
import com.google.api.services.admin.directory.Directory.Users.Patch;
import com.google.api.services.admin.directory.Directory.Users.Photos;
import com.google.api.services.admin.directory.Directory.Users.Photos.Delete;
import com.google.api.services.admin.directory.Directory.Users.Photos.Update;
import com.google.api.services.admin.directory.model.UserPhoto;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.AgentDao;
import com.owners.gravitas.dao.UserDao;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.domain.entity.UserLoginLog;
import com.owners.gravitas.dto.UserDetails;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.repository.AgentDetailsRepository;
import com.owners.gravitas.repository.ContactRepository;
import com.owners.gravitas.repository.UserLoginLogRepository;
import com.owners.gravitas.repository.UserRepository;
import com.owners.gravitas.service.SearchService;
import com.owners.gravitas.service.task.UserProfileTask;

/**
 * Test class UserServiceImplTest.
 *
 * @author raviz
 */
@PrepareForTest( { BatchRequest.class, HttpRequestFactory.class, Directory.class } )
public class UserServiceImplTest extends AbstractBaseMockitoTest {

    /** The user service impl. */
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    /** The user dao. */
    @Mock
    private UserDao userDao;

    /** The agent dao. */
    @Mock
    private AgentDao agentDao;

    /** The user repository. */
    @Mock
    private UserRepository userRepository;

    /** The directory service. */
    @Mock
    private Directory directoryService;

    /** The users. */
    @Mock
    private Users users;

    /** The get. */
    @Mock
    private Get get;

    /** The insert. */
    @Mock
    private Insert insert;

    /** The photos. */
    @Mock
    private Photos photos;

    /** The update. */
    @Mock
    private Update update;

    /** The patch. */
    @Mock
    private Patch patch;

    /** The delete. */
    @Mock
    private Delete delete;

    /** The search service. */
    @Mock
    private SearchService searchService;

    /** The agent details repository. */
    @Mock
    private AgentDetailsRepository agentDetailsRepository;

    /** The user login log repository. */
    @Mock
    private UserLoginLogRepository userLoginLogRepository;

    /** The user profile task. */
    @Mock
    private UserProfileTask userProfileTask;

    /** The contact repository. */
    @Mock
    private ContactRepository contactRepository;

    /**
     * Test get roles for FB agent role.
     */
    @Test
    public void testGetRolesForFBAgentRole() {
        final String uid = "dumay uid";
        final String email = "test@email.com";
        when( userDao.getRoles( email ) ).thenReturn( new HashSet<>() );
        when( agentDao.isAgentExist( uid ) ).thenReturn( true );
        final Set< String > userRoles = userServiceImpl.getRoles( uid, email );
        assertNotNull( userRoles );
        assertEquals( userRoles.size(), 1 );
        assertEquals( userRoles.iterator().next(), FIREBASE_AGENT );
        verifyZeroInteractions( searchService );
        verify( agentDao ).isAgentExist( uid );
    }

    /**
     * Test get roles for non FB agent role.
     */
    @Test
    public void testGetRolesForNonFBAgentRole() {
        final String uid = "dumay uid";
        final String email = "test@email.com";
        final Set< String > roles = new HashSet<>();
        roles.add( "role1" );
        when( userDao.getRoles( email ) ).thenReturn( roles );
        when( agentDao.isAgentExist( uid ) ).thenReturn( false );
        final Set< String > userRoles = userServiceImpl.getRoles( uid, email );
        assertNotNull( userRoles );
        assertEquals( userRoles.size(), 1 );
        assertEquals( userRoles.iterator().next(), "role1" );
        verifyZeroInteractions( searchService );
        verify( agentDao ).isAgentExist( uid );
    }

    /**
     * Test get roles when uid is empty.
     */
    @Test
    public void testGetRolesWhenUidIsEmpty() {
        final String uid = "";
        final String email = "test@email.com";
        final Search search = new Search();
        final String agentId = "agentId";
        search.setAgentId( agentId );
        when( userDao.getRoles( email ) ).thenReturn( new HashSet<>() );
        when( searchService.searchByAgentEmail( email ) ).thenReturn( search );
        when( agentDao.isAgentExist( agentId ) ).thenReturn( true );
        final Set< String > userRoles = userServiceImpl.getRoles( uid, email );
        assertNotNull( userRoles );
        assertEquals( userRoles.size(), 1 );
        assertEquals( userRoles.iterator().next(), FIREBASE_AGENT );
        verify( agentDao ).isAgentExist( agentId );
    }

    /**
     * Test get roles when uid is null.
     */
    @Test
    public void testGetRolesWhenUidIsNull() {
        final String uid = null;
        final String email = "test@email.com";
        final Search search = null;
        final String agentId = "agentId";
        when( userDao.getRoles( email ) ).thenReturn( new HashSet<>() );
        when( searchService.searchByAgentEmail( email ) ).thenReturn( search );
        when( agentDao.isAgentExist( agentId ) ).thenReturn( true );
        final Set< String > userRoles = userServiceImpl.getRoles( uid, email );
        assertNotNull( userRoles );
        assertEquals( userRoles.size(), 0 );
        verifyZeroInteractions( agentDao );
    }

    /**
     * Test get user details shuld return user.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testGetUserDetailsShuldReturnUser() throws IOException {
        final String email = "a@a.com";
        final com.google.api.services.admin.directory.model.User actual = new com.google.api.services.admin.directory.model.User();
        when( directoryService.users() ).thenReturn( users );
        when( users.get( email ) ).thenReturn( get );
        when( get.execute() ).thenReturn( actual );
        when( get.setProjection( FULL.getType() ) ).thenReturn( get );
        final com.google.api.services.admin.directory.model.User expected = userServiceImpl.getUser( email );
        assertEquals( actual, expected );
        verify( directoryService ).users();
        verify( users ).get( email );
        verify( get ).execute();
        verify( get ).setProjection( FULL.getType() );
    }

    /**
     * Test get user details shuld not return user if IO exception is
     * encountered.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testGetUserDetailsShuldNotReturnUserIfIOExceptionIsEncountered() throws IOException {
        final String email = "a@a.com";
        when( directoryService.users() ).thenReturn( users );
        when( users.get( email ) ).thenThrow( IOException.class );
        userServiceImpl.getUser( email );
    }

    /**
     * Test is google user exists.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testIsGoogleUserExists() throws IOException {
        final String email = "a@a.com";
        final com.google.api.services.admin.directory.model.User user = new com.google.api.services.admin.directory.model.User();
        when( directoryService.users() ).thenReturn( users );
        when( users.get( email ) ).thenReturn( get );
        when( get.execute() ).thenReturn( user );
        final Boolean actual = userServiceImpl.isGoogleUserExists( email );
        assertTrue( actual );
        verify( directoryService ).users();
        verify( users ).get( email );
        verify( get ).execute();
    }

    /**
     * Test is google user exists should throw exception if IO exception is
     * encountered.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testIsGoogleUserExistsShouldThrowExceptionIfIOExceptionIsEncountered() throws IOException {
        final String email = "a@a.com";
        when( directoryService.users() ).thenThrow( IOException.class );
        userServiceImpl.isGoogleUserExists( email );
    }

    /**
     * Test is google user exists should throw exception if google json response
     * exception is encountered.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testIsGoogleUserExistsShouldThrowExceptionIfGoogleJsonResponseExceptionIsEncountered()
            throws IOException {
        final String email = "a@a.com";
        when( directoryService.users() ).thenThrow( GoogleJsonResponseException.class );
        userServiceImpl.isGoogleUserExists( email );
    }

    /**
     * Test create google user should create user.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testCreateGoogleUserShouldCreateUser() throws IOException {
        final com.google.api.services.admin.directory.model.User user = new com.google.api.services.admin.directory.model.User();
        final com.google.api.services.admin.directory.model.User expected = new com.google.api.services.admin.directory.model.User();
        when( directoryService.users() ).thenReturn( users );
        when( users.insert( user ) ).thenReturn( insert );
        when( insert.execute() ).thenReturn( expected );
        final com.google.api.services.admin.directory.model.User actual = userServiceImpl.createGoogleUser( user );
        assertEquals( actual, expected );
        verify( directoryService ).users();
        verify( users ).insert( user );
        verify( insert ).execute();
    }

    /**
     * Test get users by role.
     */
    @Test
    public void testGetUsersByRole() {
        final String role = "test";
        when( userRepository.getUsersByRole( Mockito.anyString() ) ).thenReturn( new ArrayList< User >() );
        final List< User > users = userServiceImpl.getUsersByRole( role );
        assertNotNull( users );
    }

    /**
     * Test get users by role and statuses.
     */
    @Test
    public void testGetUsersByRoleAndStatusesWhenStatusesNotNull() {
        final String role = "test";
        final List< String > statuses = new ArrayList<>();
        statuses.add( "active" );
        when( userRepository.getUsersByRole( Mockito.anyString() ) ).thenReturn( new ArrayList< User >() );
        final List< User > users = userServiceImpl.getUsersByRole( role, statuses );
        assertNotNull( users );
    }

    /**
     * Test get users by role and statuses when statuses null.
     */
    @Test
    public void testGetUsersByRoleAndStatusesWhenStatusesNull() {
        final String role = "test";
        final List< String > statuses = new ArrayList<>();
        when( userRepository.getUsersByRole( Mockito.anyString() ) ).thenReturn( new ArrayList< User >() );
        final List< User > users = userServiceImpl.getUsersByRole( role, statuses );
        assertNotNull( users );
    }

    /**
     * Test save.
     */
    @Test
    public void testSave() {
        final User user = new User();
        when( userRepository.save( Mockito.any( User.class ) ) ).thenReturn( new User() );
        final User savedUser = userServiceImpl.save( user );
        assertNotNull( savedUser );
    }

    /**
     * Test find by email.
     */
    @Test
    public void testFindByEmail() {
        final String email = "test@email.com";
        when( userRepository.findByEmail( Mockito.anyString() ) ).thenReturn( new User() );
        final User user = userServiceImpl.findByEmail( email );
        assertNotNull( user );
    }

    /**
     * Test find all.
     */
    @Test
    public void testFindAll() {
        when( userRepository.findAll() ).thenReturn( new ArrayList< User >() );
        final List< User > users = userServiceImpl.findAll();
        assertNotNull( users );
    }

    /**
     * Test find active agents by zipcode.
     */
    @Test
    public void testFindActiveAgentsByZipcode() {
        final String zipcode = "12345";
        when( userRepository.findActiveAgentsForZipcode( Mockito.anyString() ) ).thenReturn( new ArrayList< User >() );
        final List< User > users = userServiceImpl.findActiveAgentsByZipcode( zipcode );
        assertNotNull( users );
    }

    /**
     * Test create google user should throw exception if IO exception is
     * encountered.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testCreateGoogleUserShouldThrowExceptionIfIOExceptionIsEncountered() throws IOException {
        final com.google.api.services.admin.directory.model.User user = new com.google.api.services.admin.directory.model.User();
        when( directoryService.users() ).thenReturn( users );
        when( users.insert( user ) ).thenReturn( insert );
        when( insert.execute() ).thenThrow( IOException.class );
        userServiceImpl.createGoogleUser( user );
    }

    /**
     * Test update google photo should update photo.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testUpdateGooglePhotoShouldUpdatePhoto() throws IOException {
        final UserPhoto userPhoto = new UserPhoto();
        final UserPhoto expectedUserPhoto = new UserPhoto();
        final String userEmail = "test.user@ownerstest.com";
        when( directoryService.users() ).thenReturn( users );
        when( users.photos() ).thenReturn( photos );
        when( photos.update( userEmail, userPhoto ) ).thenReturn( update );
        when( update.execute() ).thenReturn( expectedUserPhoto );
        final UserPhoto actualUserPhoto = userServiceImpl.updateGooglePhoto( userEmail, userPhoto );
        assertEquals( actualUserPhoto, expectedUserPhoto );
        verify( directoryService ).users();
        verify( users ).photos();
        verify( photos ).update( userEmail, actualUserPhoto );
        verify( update ).execute();
    }

    /**
     * Test update google photo should throw exception if IO exception is
     * encountered.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testUpdateGooglePhotoShouldThrowExceptionIfIOExceptionIsEncountered() throws IOException {
        final UserPhoto userPhoto = new UserPhoto();
        final String userEmail = "test.user@ownerstest.com";
        when( directoryService.users() ).thenReturn( users );
        when( users.photos() ).thenReturn( photos );
        when( photos.update( userEmail, userPhoto ) ).thenReturn( update );
        when( update.execute() ).thenThrow( IOException.class );
        userServiceImpl.updateGooglePhoto( userEmail, userPhoto );
    }

    /**
     * Test update google user should update user.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testUpdateGoogleUserShouldUpdateUser() throws IOException {
        final com.google.api.services.admin.directory.model.User user = new com.google.api.services.admin.directory.model.User();
        final com.google.api.services.admin.directory.model.User expected = new com.google.api.services.admin.directory.model.User();
        final String userEmail = "test.user@ownerstest.com";
        when( directoryService.users() ).thenReturn( users );
        when( users.patch( userEmail, user ) ).thenReturn( patch );
        when( patch.execute() ).thenReturn( expected );
        final com.google.api.services.admin.directory.model.User actual = userServiceImpl.updateGoogleUser( userEmail,
                user );
        assertEquals( actual, expected );
        verify( directoryService ).users();
        verify( users ).patch( userEmail, user );
        verify( patch ).execute();
    }

    /**
     * Test reset password should reset password.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testResetPasswordShouldResetPassword() throws IOException {
        final com.google.api.services.admin.directory.model.User user = new com.google.api.services.admin.directory.model.User();
        final com.google.api.services.admin.directory.model.User expected = new com.google.api.services.admin.directory.model.User();
        final String userEmail = "test.user@ownerstest.com";

        when( directoryService.users() ).thenReturn( users );
        when( users.get( userEmail ) ).thenReturn( get );
        when( get.execute() ).thenReturn( user );

        when( directoryService.users() ).thenReturn( users );
        when( users.patch( userEmail, user ) ).thenReturn( patch );
        when( patch.execute() ).thenReturn( expected );
        userServiceImpl.resetPassword( userEmail );
        verify( directoryService, Mockito.atLeast( 2 ) ).users();
        verify( users ).patch( userEmail, user );
        verify( patch ).execute();
    }

    /**
     * Test reset password should throw exception if IO exception is
     * encountered.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testResetPasswordShouldThrowExceptionIfIOExceptionIsEncountered() throws IOException {
        final com.google.api.services.admin.directory.model.User user = new com.google.api.services.admin.directory.model.User();
        final String userEmail = "test.user@ownerstest.com";

        when( directoryService.users() ).thenReturn( users );
        when( users.get( userEmail ) ).thenReturn( get );
        when( get.execute() ).thenReturn( user );

        when( directoryService.users() ).thenReturn( users );
        when( users.patch( userEmail, user ) ).thenReturn( patch );
        when( patch.execute() ).thenThrow( IOException.class );
        userServiceImpl.resetPassword( userEmail );
    }

    /**
     * Test get user by email.
     */
    @Test
    public void testGetUserByEmail() {
        final String email = "test.user@ownerstest.com";
        final User expected = new User();
        when( userRepository.findByEmail( email ) ).thenReturn( expected );
        final User actual = userServiceImpl.getUserByEmail( email );
        assertEquals( actual, expected );
        verify( userRepository ).findByEmail( email );
    }

    /**
     * Test get users by emails.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testGetUsersByEmails() throws IOException {
        final com.google.api.services.admin.directory.model.User user = new com.google.api.services.admin.directory.model.User();
        user.setAddresses( "testAddress" );
        final List< com.google.api.services.admin.directory.model.User > usersList = new ArrayList<>();
        usersList.add( user );

        final List< String > emails = new ArrayList<>();
        emails.add( "test@test.com" );
        when( userProfileTask.getUserProfiles( emails ) ).thenReturn( usersList );
        userServiceImpl.getUsersByEmails( emails );
        verify( userProfileTask ).getUserProfiles( emails );
    }

    /**
     * Test get users by managing broker should return users.
     */
    @Test
    public void testGetUsersByManagingBrokerShouldReturnUsers() {
        final List< AgentDetails > expected = new ArrayList<>();
        final String email = "tset.user@ownerstest.com";
        final com.owners.gravitas.domain.entity.User managingBroker = new com.owners.gravitas.domain.entity.User();

        when( userRepository.findByEmail( email ) ).thenReturn( managingBroker );
        when( agentDetailsRepository.getAgentsByManagingBroker( managingBroker ) ).thenReturn( expected );

        final List< AgentDetails > actual = userServiceImpl.getUsersByManagingBroker( email );

        assertEquals( actual, expected );
        verify( userRepository ).findByEmail( email );
        verify( agentDetailsRepository ).getAgentsByManagingBroker( managingBroker );
    }

    /**
     * Test save user login log should save user log.
     */
    @Test
    public void testSaveUserLoginLogShouldSaveUserLog() {
        final UserLoginLog userLoginLog = new UserLoginLog();
        final UserLoginLog expectedUserLoginLog = new UserLoginLog();

        when( userLoginLogRepository.save( userLoginLog ) ).thenReturn( expectedUserLoginLog );

        final UserLoginLog actualUserLoginLog = userServiceImpl.saveUserLoginLog( userLoginLog );

        assertEquals( actualUserLoginLog, expectedUserLoginLog );
        verify( userLoginLogRepository ).save( userLoginLog );
    }

    /**
     * Test get agents details.
     */
    @Test
    public void testGetAgentsDetails() {
        final String userId = "test";
        final String type = "BUYER";
        final List< com.google.api.services.admin.directory.model.User > expectedUsers = new ArrayList<>();
        when( contactRepository.findByOwnersComId( Mockito.anyString() ) ).thenReturn( new HashSet< Contact >() );
        when( contactRepository.findByOwnersComIdAndType( Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( new HashSet< Contact >() );
        List< UserDetails > actualUsers1 = userServiceImpl.getAgentsDetails( userId, type );
        List< UserDetails > actualUsers2 = userServiceImpl.getAgentsDetails( userId, null );
        assertEquals( actualUsers1, expectedUsers );
        assertEquals( actualUsers2, expectedUsers );
        verify( contactRepository ).findByOwnersComId( userId );
        verify( contactRepository ).findByOwnersComIdAndType( userId, type );

    }

    /**
     * Test get users by email.
     */
    @Test
    public void testGetUsersByEmail() {
        final List< String > emails = new ArrayList<>();
        final List< User > expectedUsers = new ArrayList<>();
        when( userRepository.findByEmailIn( emails ) ).thenReturn( expectedUsers );
        final List< User > actualUsers = userServiceImpl.getUsersByEmail( emails );
        assertEquals( actualUsers, expectedUsers );
        verify( userRepository ).findByEmailIn( emails );
    }

    /**
     * Test delete google photo.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testDeleteGooglePhoto() throws IOException {
        final String email = "test@test.com";
        when( directoryService.users() ).thenReturn( users );
        when( users.photos() ).thenReturn( photos );
        when( photos.delete( email ) ).thenReturn( delete );
        userServiceImpl.deleteGooglePhoto( email );
    }

    /**
     * Test delete google photo should throw exception.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testDeleteGooglePhotoShouldThrowException() throws IOException {
        final String email = "test@test.com";
        when( directoryService.users() ).thenReturn( users );
        when( users.photos() ).thenReturn( photos );
        when( photos.delete( email ) ).thenReturn( delete );
        doThrow( GoogleJsonResponseException.class ).when( delete ).execute();
        userServiceImpl.deleteGooglePhoto( email );
    }

    /**
     * Test delete google photo should throw exception1.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testDeleteGooglePhotoShouldThrowException1() throws IOException {
        final String email = "test@test.com";
        when( directoryService.users() ).thenReturn( users );
        when( users.photos() ).thenReturn( photos );
        when( photos.delete( email ) ).thenReturn( delete );
        doThrow( IOException.class ).when( delete ).execute();
        userServiceImpl.deleteGooglePhoto( email );
    }

    /**
     * Test get managing broker.
     */
    @Test
    public void testGetManagingBroker() {
        final String email = "test@test.com";
        final User expectedManagingBroker = new User();
        when( userRepository.getManagingBroker( email ) ).thenReturn( expectedManagingBroker );
        final User actualManagingBroker = userServiceImpl.getManagingBroker( email );
        assertEquals( actualManagingBroker, expectedManagingBroker );
        verify( userRepository ).getManagingBroker( email );
    }
}
