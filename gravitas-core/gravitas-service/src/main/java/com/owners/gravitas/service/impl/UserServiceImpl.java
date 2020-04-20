package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.Constants.DAILY;
import static com.owners.gravitas.constants.Constants.HASH_FUNCTION;
import static com.owners.gravitas.constants.Constants.INSTANT;
import static com.owners.gravitas.constants.Constants.UNSUBSCRIBE;
import static com.owners.gravitas.constants.UserRole.FIREBASE_AGENT;
import static com.owners.gravitas.enums.GoogleFieldProjection.FULL;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.util.Base64;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.UserPhoto;
import com.owners.gravitas.dao.AgentDao;
import com.owners.gravitas.dao.UserDao;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.Opportunity;
import com.owners.gravitas.domain.entity.UserLoginLog;
import com.owners.gravitas.dto.Preference;
import com.owners.gravitas.dto.UserDetails;
import com.owners.gravitas.dto.response.NotificationPreferenceResponse;
import com.owners.gravitas.enums.NotificationType;
import com.owners.gravitas.enums.SubscriptionType;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.repository.AgentDetailsRepository;
import com.owners.gravitas.repository.ContactRepository;
import com.owners.gravitas.repository.UserLoginLogRepository;
import com.owners.gravitas.repository.UserRepository;
import com.owners.gravitas.service.CRMAuthenticatorService;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.service.SearchService;
import com.owners.gravitas.service.UserService;
import com.owners.gravitas.service.task.UserProfileTask;
import com.owners.gravitas.util.JsonUtil;

/**
 * The Class UserServiceImpl.
 */
@Service
@Transactional( readOnly = true )
public class UserServiceImpl implements UserService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( UserServiceImpl.class );

    /** restTemplate for making rest call to owners. */
    @Autowired
    @Qualifier( "crmRestAuthenticator" )
    private CRMAuthenticatorService crmAuthenticator;

    /** The agent url. */
    @Value( value = "${salesforce.agent.url}" )
    private String agentUrl;

    /** The agent url. */
    @Value( value = "${owners.com.reset.password}" )
    private String resetPassword;

    /** The user dao. */
    @Autowired
    private UserDao userDao;

    /** The agent dao. */
    @Autowired
    private AgentDao agentDao;

    /** The directory service. */
    @Autowired
    private Directory directoryService;

    /** The user repository. */
    @Autowired
    private UserRepository userRepository;

    /** The agent details repository. */
    @Autowired
    private AgentDetailsRepository agentDetailsRepository;

    /** The search service. */
    @Autowired
    private SearchService searchService;

    /** The user login log repository. */
    @Autowired
    private UserLoginLogRepository userLoginLogRepository;

    /** The user profile task. */
    @Autowired
    private UserProfileTask userProfileTask;
    
    /** The opportunity V1 repository. */
    @Autowired
    private ContactRepository contactRepository;
    
    /** The mail service. */
    @Autowired
    private MailService mailService;

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.UserService#getRoles(java.lang.String,
     * java.lang.String)
     */
    @Override
    public Set< String > getRoles( final String uid, final String email ) {
        LOGGER.info("getting roles for email : {}", email);
        final Set< String > roles = userDao.getRoles( email );
        String agentId = uid;
        if (StringUtils.isBlank( agentId )) {
            final Search agentSearch = searchService.searchByAgentEmail( email );
            if (null != agentSearch) {
                agentId = agentSearch.getAgentId();
            }
        }
        if (StringUtils.isNotBlank( agentId ) && agentDao.isAgentExist( agentId )) {
            roles.add( FIREBASE_AGENT );
        }
        return roles;
    }

    /**
     * Gets the user.
     *
     * @param email
     *            the email
     * @return the user
     */
    @Override
    public User getUser( final String email ) {
        try {
            return directoryService.users().get( email ).setProjection( FULL.getType() ).execute();
        } catch ( Exception e ) {
            LOGGER.error( "error getting details for {}", email, e );
            throw new ApplicationException( "error getting details for " + email, e );
        }
    }
    
    /**
     * Gets the user details.
     *
     * @param email
     *            the email
     * @return the user details
     */
    @Override
    public UserDetails getUserDetails(final String email) {
        final UserDetails userDetails = new UserDetails();
        userDetails.setUser(getUser(email));
        final UserPhoto userPhoto = getUserPhotos(email);
        if(userPhoto != null) {
            userDetails.setUserPhoto(Base64.encodeBase64String(getUserPhotos(email).decodePhotoData()));
        }
        return userDetails;
    }

    /**
     * Gets the UserPhoto.
     * @param email
     *            the email
     * @return the UserPhoto
     */
    private UserPhoto getUserPhotos(final String email) {
        UserPhoto userPhoto = null;
        try {
            userPhoto = directoryService.users().photos().get(email).execute();
        } catch (Exception e) {
            LOGGER.error("error getting photo for {}", email, e);
        }
        return userPhoto;
    }

    /**
     * Checks if is google user exists.
     *
     * @param email
     *            the email
     * @return the boolean
     */
    @Override
    public Boolean isGoogleUserExists( final String email ) {
        boolean isExists = FALSE;
        try {
            directoryService.users().get( email ).execute();
            isExists = TRUE;
        } catch ( GoogleJsonResponseException ge ) {
            if (ge.getStatusCode() != NOT_FOUND.value()) {
                throw new ApplicationException( "error getting details for " + email, ge );
            }
        } catch ( IOException e ) {
            LOGGER.debug( "error getting details for " + email );
            throw new ApplicationException( "error getting details for " + email, e );
        }
        return isExists;
    }


    /* (non-Javadoc)
     * @see com.owners.gravitas.service.UserService#getUsersByEmails(java.util.List)
     */
    @Override
    public List< User > getUsersByEmails( final List< String > emails ) {
        List< User > googleUsers = new ArrayList<>();
        try {
            googleUsers.addAll( userProfileTask.getUserProfiles( emails ) );
        } catch ( Exception e ) {
            LOGGER.error( "Exception while getting Google Users ", e );
        }
        return googleUsers;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.UserService#createGoogleUser(com.google.api.
     * services.admin.directory.model.User)
     */
    @Override
    public User createGoogleUser( final User newUser ) {
        try {
            return directoryService.users().insert( newUser ).execute();
        } catch ( Exception e ) {
            LOGGER.error( "problem creating google user : {}", newUser.getPrimaryEmail(), e );
            throw new ApplicationException( "problem creating google user " + newUser.getPrimaryEmail(), e );
        }
    }

    /**
     * Update photo.
     *
     * @param email
     *            the email
     * @param photo
     *            the photo
     * @return the user photo
     */
    @Override
    public UserPhoto updateGooglePhoto( final String email, final UserPhoto photo ) {
        try {
            return directoryService.users().photos().update( email, photo ).execute();
        } catch ( Exception e ) {
            LOGGER.error( "problem updating photo for : {}", email, e );
            throw new ApplicationException( "problem updatig photo for " + email, e );
        }
    }

    /**
     * Gets the users by role.
     *
     * @param role
     *            the role
     * @return the users by role
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public List< com.owners.gravitas.domain.entity.User > getUsersByRole( final String role ) {
        return userRepository.getUsersByRole( role );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.UserService#getUsersByRole(java.lang.String,
     * java.util.List)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public List< com.owners.gravitas.domain.entity.User > getUsersByRole( final String role,
            final List< String > statuses ) {
        List< com.owners.gravitas.domain.entity.User > users = null;
        if (statuses.size() == 0) {
            users = getUsersByRole( role );
        } else {
            users = userRepository.getUsersByRole( role, statuses );
        }
        return users;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.UserService#save(com.owners.gravitas.domain.
     * entity.User)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public com.owners.gravitas.domain.entity.User save( final com.owners.gravitas.domain.entity.User user ) {
        return userRepository.save( user );
    }

    /**
     * Find by email.
     *
     * @param email
     *            the email
     * @return the com.owners.gravitas.domain.entity. user
     */
    @Override
    public com.owners.gravitas.domain.entity.User findByEmail( final String email ) {
        return userRepository.findByEmail( email );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.UserService#findAll()
     */
    @Override
    public List< com.owners.gravitas.domain.entity.User > findAll() {
        return userRepository.findAll();
    }

    /**
     * Gets the best performing agents for zipcode.
     *
     * @param zipcode
     *            the zipcode
     * @return the best performing agents for zipcode
     */
    @Override
    public List< com.owners.gravitas.domain.entity.User > findActiveAgentsByZipcode( final String zipcode ) {
        return userRepository.findActiveAgentsForZipcode( zipcode );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.UserService#updateGoogleUser(java.lang.
     * String, com.google.api.services.admin.directory.model.User)
     */
    // ToDo
    @Override
    public User updateGoogleUser( String email, User newUser ) throws IOException {
        return directoryService.users().patch( email, newUser ).execute();
    }

    /**
     * Reset password.
     *
     * @param email
     *            the email
     */
    @Override
    public void resetPassword( final String email ) {
        try {
            final User user = directoryService.users().get( email ).execute();
            user.setPassword( resetPassword );
            user.setHashFunction( HASH_FUNCTION );
            user.setChangePasswordAtNextLogin( TRUE );
            directoryService.users().patch( email, user ).execute();
            LOGGER.info( "password reset successful, email was" + email );
        } catch ( Exception e ) {
            LOGGER.error( "error resetting password for : {}", email, e );
            throw new ApplicationException( "error getting details for " + email, e );
        }
    }

    /**
     * Delete google photo.
     *
     * @param email
     *            the email
     */
    @Override
    public void deleteGooglePhoto( String email ) {
        try {
            directoryService.users().photos().delete( email ).execute();
        } catch ( GoogleJsonResponseException ge ) {
            if (ge.getStatusCode() != NOT_FOUND.value()) {
                throw new ApplicationException( "problem updatig photo for " + email, ge );
            }
        } catch ( Exception e ) {
            LOGGER.error( "problem updatig photo for : {}", email, e );
            throw new ApplicationException( "problem updatig photo for " + email, e );
        }
    }

    /**
     * Gets the user by email.
     *
     * @param email
     *            the email
     * @return the user by email
     */
    @Override
    public com.owners.gravitas.domain.entity.User getUserByEmail( final String email ) {
        return userRepository.findByEmail( email );
    }

    /**
     * Gets the users by managing broker.
     *
     * @param mbEmail
     *            the mb email
     * @return the users by managing broker
     */
    @Override
    public List< AgentDetails > getUsersByManagingBroker( final String mbEmail ) {
        List< AgentDetails > agents = new ArrayList<>();
        final com.owners.gravitas.domain.entity.User managingBroker = getUserByEmail( mbEmail );
        if (null != managingBroker) {
            agents = agentDetailsRepository.getAgentsByManagingBroker( managingBroker );
        }
        return agents;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.UserService#saveUserLoginLog(com.owners.
     * gravitas.domain.entity.UserLoginLog)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public UserLoginLog saveUserLoginLog( final UserLoginLog userLoginLog ) {
        return userLoginLogRepository.save( userLoginLog );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.UserService#getManagingBroker(java.lang.
     * String)
     */
    @Override
    public com.owners.gravitas.domain.entity.User getManagingBroker( String email ) {
        return userRepository.getManagingBroker( email );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.UserService#getUsersByEmail(java.util.List)
     */
    @Override
    public List< com.owners.gravitas.domain.entity.User > getUsersByEmail( final List< String > emails ) {
        return userRepository.findByEmailIn( emails );
    }
    
    /**
     * Gets the users by client ownersId of the client.
     *
     * @param userId
     *            the user Id of the client
     * @param agentOpportunityType
     *            the agent Opportunity Type
     * @return the list of users from google business account
     */

    @Override
    public List< UserDetails > getAgentsDetails( final String userId, final String agentOpportunityType ) {
        LOGGER.info( "getAgentDetails userId : {}, agentOpportunityType : {}", userId, agentOpportunityType );
        List< UserDetails > users = new ArrayList< >();
        Set< Opportunity > opportunitiesV1 = new HashSet< >();
        Set< Contact > contacts = new HashSet< >();
        if (StringUtils.isEmpty( agentOpportunityType )) {
            contacts = contactRepository.findByOwnersComId( userId );
        } else {
            contacts = contactRepository.findByOwnersComIdAndType( userId, agentOpportunityType );
        }
        contacts.stream().filter( contact -> contact != null )
                .filter( contact -> !CollectionUtils.isEmpty( contact.getOpportunities() ) )
                .forEach( contact -> opportunitiesV1.addAll( contact.getOpportunities() ) );
        opportunitiesV1.stream().filter( opportunityV1 -> opportunityV1 != null )
                .filter( opportunityV1 -> ( StringUtils.isNotEmpty( opportunityV1.getAssignedAgentId() ) && !opportunityV1.isDeleted() ) )
                .forEach( opportunityV1 -> users.add( getUserDetails( opportunityV1.getAssignedAgentId() ) ) );
        
        LOGGER.info( "getAgentDetails userId : {}, agentOpportunityType : {}, userDetails : {}", userId,
                agentOpportunityType, JsonUtil.toJson( users ) );
        return users;
    }
    
    @Override
    public List< Object[] > getScheduleMeetingDetails( String emailId, Date[] dates, Boolean isAgent ) {
        return userDao.getScheduleMeetingDetails( emailId, dates, isAgent );
    }

    @Override
    public boolean checkUserPreference( String contactEmail ) {
        boolean isEmailOptOut = FALSE;
        LOGGER.info( "Checking for user preference with email {}", contactEmail );
        final NotificationPreferenceResponse notificationPreferenceResponse = mailService
                .getNotificationPreferenceForUser( contactEmail );
        final List< Preference > preferences = notificationPreferenceResponse.getPreferences();
        if (CollectionUtils.isNotEmpty( preferences )) {
            isEmailOptOut = preferences.stream().anyMatch(
                    p -> NotificationType.MARKETING.name().equalsIgnoreCase( p.getType() ) && !p.getValue() );
        }
        LOGGER.info( "email opt out is {} for the email {}", isEmailOptOut, contactEmail );
        return isEmailOptOut;
    }

    @Override
    public String getSubscriptionType( String type ) {
        String subscriptionType = null;
        switch ( type ) {
            case INSTANT : 
                subscriptionType = SubscriptionType.SUBSCRIBE_INSTANT.name();
                break;
            case DAILY :
                subscriptionType = SubscriptionType.SUBSCRIBE_DAILY.name();
                break;
            case UNSUBSCRIBE :
                subscriptionType = SubscriptionType.UNSUBSCRIBE_NOTIFICATION.name();
                break;
        }
        return subscriptionType;
    }

	@Override
	public com.owners.gravitas.domain.entity.User getUserDetailsByUserId(String userId) {
		return userRepository.findOne(userId);
	}
}
