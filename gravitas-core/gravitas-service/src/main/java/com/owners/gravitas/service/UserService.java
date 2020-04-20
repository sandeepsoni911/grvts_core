package com.owners.gravitas.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.UserPhoto;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.UserLoginLog;
import com.owners.gravitas.dto.UserDetails;

/**
 * The Interface UserService.
 */
public interface UserService {

    /**
     * Gets the roles.
     *
     * @param uid
     *            the uid
     * @param email
     *            the email
     * @return the roles
     */
    Set< String > getRoles( String uid, String email );

    /**
     * Gets the user.
     *
     * @param email
     *            the email
     * @return the user.
     */
    User getUser( String email );

    /**
     * Creates the user.
     *
     * @param newUser
     *            the new user
     * @return the user
     */
    User createGoogleUser( User newUser );

    /**
     * Update photo.
     *
     * @param email
     *            the email
     * @param photo
     *            the photo
     * @return the user photo
     */
    UserPhoto updateGooglePhoto( String email, UserPhoto photo );

    /**
     * Gets the users by role.
     *
     * @param role
     *            the role
     * @return the users by role
     */
    List< com.owners.gravitas.domain.entity.User > getUsersByRole( String role );

    /**
     * Save.
     *
     * @param user
     *            the user
     * @return the com.owners.gravitas.domain.entity. user
     */
    com.owners.gravitas.domain.entity.User save( com.owners.gravitas.domain.entity.User user );

    /**
     * Find by email.
     *
     * @param email
     *            the email
     * @return the com.owners.gravitas.domain.entity. user
     */
    com.owners.gravitas.domain.entity.User findByEmail( final String email );

    /**
     * Find all.
     *
     * @return the list
     */
    List< com.owners.gravitas.domain.entity.User > findAll();

    /**
     * Gets the users by emails.
     *
     * @param emails
     *            the emails
     * @return the users by emails
     */
    List< User > getUsersByEmails( List< String > emails );

    /**
     * Gets the best performing agents for zipcode.
     *
     * @param zipcode
     *            the zipcode
     * @return the best performing agents for zipcode
     */
    List< com.owners.gravitas.domain.entity.User > findActiveAgentsByZipcode( String zipcode );

    /**
     * Reset password.
     *
     * @param email
     *            the email
     */
    void resetPassword( String email );

    /**
     * Checks if is google user exists.
     *
     * @param email
     *            the email
     * @return the boolean
     */
    Boolean isGoogleUserExists( String email );

    /**
     * Update google user.
     *
     * @param email
     *            the email
     * @param newUser
     *            the new user
     * @return the user
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    User updateGoogleUser( String email, User newUser ) throws IOException;

    /**
     * Delete google photo.
     *
     * @param email
     *            the email
     */
    void deleteGooglePhoto( String email );

    /**
     * Gets the users by managing broker.
     *
     * @param mbEmail
     *            the mb email
     * @return the users by managing broker
     */
    List< AgentDetails > getUsersByManagingBroker( String mbEmail );

    /**
     * Gets the user by email.
     *
     * @param email
     *            the email
     * @return the user by email
     */
    com.owners.gravitas.domain.entity.User getUserByEmail( String email );

    /**
     * Save user login log.
     *
     * @param userLoginLog
     *            the user login log
     * @return the user login log
     */
    UserLoginLog saveUserLoginLog( UserLoginLog userLoginLog );

    /**
     * Gets the users by role.
     *
     * @param role
     *            the role
     * @param statuses
     *            the statuses
     * @return the users by role
     */
    List< com.owners.gravitas.domain.entity.User > getUsersByRole( String role, List< String > statuses );
    /**
     * Gets the managing broker.
     *
     * @param email
     *            the email
     * @return the managing broker
     */
    com.owners.gravitas.domain.entity.User getManagingBroker( String email );

    /**
     * Gets the users by email.
     *
     * @param emails
     *            the emails
     * @return the users by email
     */
    List< com.owners.gravitas.domain.entity.User > getUsersByEmail( List< String > emails );
    
    /**
     * Gets the agent details.
     *
     * @param userId
     *            the userId
     * @param agentOpportunityType
     *            the agentOpportunityType
     * @return the agents details
     */
    List< UserDetails > getAgentsDetails( final String userId, final String agentOpportunityType );
    
    /**
     * Gets the Schedule Meetings details.
     *
     * @param emailId
     *            the emailId
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @param isAgent
     *            the isAgent
     * @return the Schedule Meetings details
     */
    List< Object[] > getScheduleMeetingDetails( String emailId, Date[] dates, Boolean isAgent );
    
    /**
     * Check user preference.
     *
     * @param contactEmail
     *            the contact email
     * @return true, if successful
     */
    boolean checkUserPreference( String contactEmail );
    
    /**
     * Based on type return SubscriptionType(e.g. instant, daily, unsubscribe)
     * 
     * @param type
     * @return
     */
    String getSubscriptionType(String type);
    
    /**
     * to get user details by uuid
     * @param userId
     */
    com.owners.gravitas.domain.entity.User getUserDetailsByUserId(String userId);
    
    /**
     * Gets the user details.
     *
     * @param email
     *            the email
     * @return the user details
     */
    UserDetails getUserDetails( final String email );
    
}
