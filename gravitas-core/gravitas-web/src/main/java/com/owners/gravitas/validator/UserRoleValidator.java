package com.owners.gravitas.validator;

import static com.owners.gravitas.constants.UserRole.FIREBASE_AGENT;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.dto.ApiUser;
import com.owners.gravitas.enums.ErrorCode;
import com.owners.gravitas.exception.AgentInvalidException;
import com.owners.gravitas.exception.InvalidArgumentException;
import com.owners.gravitas.exception.InvalidUserIdException;
import com.owners.gravitas.util.GravitasWebUtil;
import com.owners.gravitas.util.StringUtils;

/**
 * The Class UserRoleValidator.
 */
@Component
public class UserRoleValidator {

    /** The gravitas web util. */
    @Autowired
    private GravitasWebUtil gravitasWebUtil;

    /**
     * Validate by agent id.
     *
     * @param agentId
     *            the agent id
     * @return the api user
     */
    public ApiUser validateByAgentId( final String agentId ) {
        return validateByUserId( agentId, FIREBASE_AGENT );
    }

    /**
     * Validate by user id.
     *
     * @param uid
     *            the uid
     * @param userRole
     *            the user role
     * @return the api user
     */
    public ApiUser validateByUserId( final String uid, final String userRole ) {
        final ApiUser user = gravitasWebUtil.getAppUser();
        if (isSingleRoleUser( user.getRoles(), userRole )) {
            validateUserId( user, uid );
        }
        return user;
    }

    /**
     * Validate by agent email.
     *
     * @param email
     *            the email
     * @return the api user
     */
    public ApiUser validateByAgentEmail( final String email ) {
        return validateByUserEmail( email, FIREBASE_AGENT );
    }

    /**
     * Validate by user email.
     *
     * @param email
     *            the email
     * @param userRole
     *            the user role
     * @return the api user
     */
    public ApiUser validateByUserEmail( final String email, final String userRole ) {
        final ApiUser user = gravitasWebUtil.getAppUser();
        if (isSingleRoleUser( user.getRoles(), userRole )) {
            validateUserEmail( user, email );
        }
        return user;
    }

    /**
     * Validate by user email.
     *
     * @param email
     *            the email
     */
    public void validateByUserEmail( final String email ) {
        final ApiUser user = gravitasWebUtil.getAppUser();
        validateUserEmail( user, email );
    }

    /**
     * Validate user id.
     *
     * @param user
     *            the user
     * @param agentId
     *            the agent id
     */
    private void validateUserId( final ApiUser user, final String agentId ) {
        // If user is a agent only then UID and againtId should match
        if (!user.getUid().equals( agentId )) {
            throw new AgentInvalidException( agentId + " is not valid agentId" );
        }
    }

    /**
     * Validate user email.
     *
     * @param user
     *            the user
     * @param email
     *            the email
     */
    private void validateUserEmail( final ApiUser user, final String email ) {
        // If user is a agent only then UID and againtId should match
        if (!user.getEmail().equals( email )) {
            throw new AgentInvalidException( email + " is not valid email" );
        }
    }

    /**
     * Checks if is single role user.
     *
     * @param roles
     *            the roles
     * @param userRole
     *            the user role
     * @return true, if is single role user
     */
    private boolean isSingleRoleUser( final Set< String > roles, final String userRole ) {
        boolean singleOnly = roles.contains( userRole ) && roles.size() == 1;
        return singleOnly;
    }

    /**
     * Checks if the userId is valid.
     *
     * @param userId
     *            the userId
     */

    public void validateUserId( String userId ) {
        if (!StringUtils.isValidId( userId )) {
            throw new InvalidUserIdException( userId + "is not valid userId" );
        }
    }
    
    /**
     * Checks if the email is valid.
     *
     * @param email
     *            the email
     */
    public void validateUserActivityInput( String input ) {
        if (!StringUtils.isEmailIdValid( input ) && !StringUtils.isValidId( input )) {
            List< String > errors = new ArrayList< String >();
            errors.add( ErrorCode.INVALID_USER_ACTIVITY_INPUT_PARAMETER.getCode() );
            throw new InvalidArgumentException( input + "is not valid ", errors );
        }
    }
}
