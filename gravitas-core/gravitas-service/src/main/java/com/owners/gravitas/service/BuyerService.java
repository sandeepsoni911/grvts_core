package com.owners.gravitas.service;

import java.util.Map;

import com.owners.gravitas.dto.request.SavedSearchChimeraRequest;
import com.owners.gravitas.dto.request.SavedSearchRequest;
import com.owners.gravitas.dto.request.UserNotificationConfigDetails;
import com.owners.gravitas.dto.response.BuyerDeviceResponse;
import com.owners.gravitas.dto.response.RegistrationResponse;
import com.owners.gravitas.dto.response.SaveSearchResponse;
import com.owners.gravitas.dto.response.UserNotificationConfigResponse;

// TODO: Auto-generated Javadoc
/**
 * The Interface BuyerService.
 */
public interface BuyerService {

    /**
     * Register buyer.
     *
     * @param userDetails
     *            the user details
     * @return the registration response
     */
    RegistrationResponse registerBuyer( Map< String, Object > userDetails );

    /**
     * Save search.
     *
     * @param savedSearchRequest
     *            the saved search request
     * @return the save search response
     */
    SaveSearchResponse saveSearch( SavedSearchRequest savedSearchRequest );

    /**
     * Check save search exists.
     *
     * @param uuid
     *            the uuid
     * @return true, if successful
     */
    boolean checkSaveSearchExists( String uuid );

    /**
     * Returns existed save search response for the uuid.
     *
     * @param uuid
     *            the uuid
     * @return the save search response
     */
    SaveSearchResponse viewExistSaveSearch( String uuid );

    /**
     * delete saved seach for the user and Returns remaining saved search.
     *
     * @param uuid
     *            the uuid
     * @return delete the Existing Save Search for the user
     */
    Object deleteSaveSearch( String uuid );

    /**
     * create save search for the user using ws buyer API
     *
     * @param savedSearchRequest
     *            the savedSearchRequest
     * @return create save search for the user using ws buyer API
     */
    Object createsaveSearch( SavedSearchChimeraRequest savedSearchCrmRequest );

    /**
     * Checks if is buyer auto registration email.
     *
     * @param emailStr
     *            the email str
     * @return true, if is buyer auto registration email
     */
    boolean isBuyerAutoRegistrationEmail( String emailStr );

    /**
     * Checks if is farm long term state.
     *
     * @param state
     *            the state
     * @return true, if is farm long term state
     */
    boolean isFarmLongTermState( String state );
    
    /**
     * update user notification config details to wsbuyer
     * 
     * @param userNotificationConfigDetails
     * @return
     */
    UserNotificationConfigResponse updateNotificationConfig( UserNotificationConfigDetails userNotificationConfigDetails );
    
    /**
     * Get buyer device details
     * 
     * @param uuid
     * @return
     */
    BuyerDeviceResponse getBuyerDeviceDetails( String uuid );
}
