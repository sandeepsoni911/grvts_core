/**
 *
 */
package com.owners.gravitas.business;

import java.util.List;

import com.google.api.client.util.ArrayMap;
import com.owners.gravitas.dto.crm.request.UserLoginLogRequest;
import com.owners.gravitas.dto.request.SavedSearchChimeraRequest;
import com.owners.gravitas.dto.request.UserNotificationConfigDetails;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.SaveSearchResponse;
import com.owners.gravitas.dto.response.ScheduleMeetingResponse;

/**
 * The Interface UserBusinessService.
 *
 * @author harshads
 */
public interface UserBusinessService {

    /**
     * Gets the user details.
     *
     * @param email
     *            the email
     * @return the user details
     */
    BaseResponse getUserDetails( String email );

    /**
     * Gets the user phones.
     *
     * @param email
     *            the email
     * @return the user phones
     */
    List< ArrayMap< String, Object > > getUserPhones( String email );

    /**
     * Gets the user roles by email.
     *
     * @param email
     *            the email
     * @return the user roles by email
     */
    BaseResponse getUserRolesByEmail( String email );

    /**
     * Reset password.
     *
     * @param email
     *            the email
     * @return the base response
     */
    BaseResponse resetPassword( String email );

    /**
     * Checks if is google user exists.
     *
     * @param email
     *            the email
     * @return the boolean
     */
    Boolean isGoogleUserExists( String email );

    /**
     * Gets the agent details.
     *
     * @param listingId
     *            the listing id
     * @return the agent details
     */
    BaseResponse getAgentDetails( String listingId );

    /**
     * Save user login log.
     *
     * @param userLoginLogRequest
     *            the user login log request
     * @return the base response
     */
    BaseResponse saveUserLoginLog( UserLoginLogRequest userLoginLogRequest );

    /**
     * Gets the agents by manager.
     *
     * @param email
     *            the email
     * @param filter
     *            the filter
     * @return the agents by manager
     */
    BaseResponse getAgentsByManager( String email, String filter );

    /**
     * Gets the users by filters 1.
     *
     * @param role
     *            the role
     * @param filter
     *            the filter
     * @return the users by filters 1
     */
    BaseResponse getUsersByFilters( String role, String filter );

    /**
     * Gets the agent details.
     *
     * @param userId
     *            the owners Id of the client
     * @param agentOpportunityType
     *            the opportunity Type
     * @return the agents details
     */
    BaseResponse getAgentsDetails( final String userId, final String agentOpportunityType );

    /**
     * Gets the user activity details.
     *
     * @param input
     *            the input
     * @return the user activity details
     */
    BaseResponse getUserActivityDetails( String input );

    /**
     * Returns existed save search response for the uuid
     * 
     * @param uuid
     * @return
     */
    SaveSearchResponse viewExistSaveSearch( String uuid );

    /**
     * 
     * @param emailId
     * @param timeMin
     * @param timeMax
     * @return
     */
    BaseResponse getGoogleCalendarEvents( String emailId, final Long timeMin, final Long timeMax );

    /**
     * update Existing save search for the user
     * 
     * @param request
     * @return SaveSearchResponse
     */
    Object updateSaveSearch( SavedSearchChimeraRequest savedSearchRequest );

    /**
     * create Existing save search for the user
     * 
     * @param request
     * @return SaveSearchResponse
     */
    Object createSaveSearch( SavedSearchChimeraRequest savedSearchRequest );

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
    ScheduleMeetingResponse getScheduleMeetingDetails( String emailId, String fromDate, String toDate,
            Boolean isAgent );

    /**
     * 
     * @param crmId
     * @param dueDtm
     * @return
     */
    BaseResponse isAgentEventExists( String agentEmail, Long dueDtm );
    
    /**
     * update user notification config details to wsbuyer
     * 
     * @param userNotificationConfigDetails
     * @return
     */
    BaseResponse updateNotificationConfig( UserNotificationConfigDetails userNotificationConfigDetails );
}
