package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.Constants.COMMA;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.owners.gravitas.config.BuyerFarmingConfig;
import com.owners.gravitas.dto.request.SavedSearchChimeraRequest;
import com.owners.gravitas.dto.request.SavedSearchRequest;
import com.owners.gravitas.dto.request.UserNotificationConfigDetails;
import com.owners.gravitas.dto.response.BuyerDeviceResponse;
import com.owners.gravitas.dto.response.RegistrationResponse;
import com.owners.gravitas.dto.response.SaveSearchResponse;
import com.owners.gravitas.dto.response.UserNotificationConfigResponse;
import com.owners.gravitas.service.BuyerService;
import com.owners.gravitas.util.JsonUtil;
import com.owners.gravitas.util.RestUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class BuyerServiceImpl.
 *
 * @author vishwanathm
 */
@Service
public class BuyerServiceImpl implements BuyerService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( BuyerServiceImpl.class );

    /** The buyer auto registration url. */
    @Value( "${buyer.autoRegistration.url}" )
    private String buyerAutoRegistrationUrl;

    /** The save search url. */
    @Value( "${buyer.saveSearch.url}" )
    private String saveSearchUrl;

    /** The check save search exists. */
    @Value( "${buyer.checkSaveSearchExists.url}" )
    private String checkSaveSearchExists;

    /** The check save search exists. */
    @Value( "${buyer.delete.saveSearch.url}" )
    private String deleteExistSaveSearch;

    /** The check save search exists. */
    @Value( "${buyer.create.saveSearch.url}" )
    private String createSaveSearch;
    
    @Value( "${buyer.updateUserNtfcConfig.url}")
    private String updateUserNtfcConfigUrl;
    
    @Value( "${buyer.userDeviceDetails.url}")
    private String userDeviceDetailsUrl;

    /** The config. */
    @Autowired
    private BuyerFarmingConfig config;

    /** restTemplate for making rest call to owners. */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Register buyer.
     *
     * @param userDetails
     *            the user details
     * @return the registration response
     */
    @Override
    public RegistrationResponse registerBuyer( final Map< String, Object > userDetails ) {
        LOGGER.info( "Buyer registration details : {} ", JsonUtil.toJson( userDetails ) );
        RegistrationResponse registrationResponse = restTemplate.exchange( buyerAutoRegistrationUrl, HttpMethod.POST,
                RestUtil.buildRequest( null, userDetails ), RegistrationResponse.class ).getBody();
        LOGGER.info( "Response : Buyer registration : {} ", JsonUtil.toJson(registrationResponse) );
        return registrationResponse;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.BuyerService#saveSearch(com.owners.gravitas.
     * dto.request.SavedSearchRequest)
     */
    @Override
    public SaveSearchResponse saveSearch( final SavedSearchRequest savedSearchRequest ) {
        LOGGER.info( "Start : Save Search for request : {} ", JsonUtil.toJson(savedSearchRequest) );
        SaveSearchResponse saveSearchResponse = null;
        String response = restTemplate.exchange( saveSearchUrl, HttpMethod.POST, RestUtil.buildRequest( null, savedSearchRequest ),
                String.class ).getBody();
        LOGGER.info( "Response : Save Search : {} ", response );
        try {
            saveSearchResponse = JsonUtil.convertFromJson(response, SaveSearchResponse.class);
        } catch (IOException e) {
            LOGGER.error("Error while converting to save search response : {}", response, e);
        }
        return saveSearchResponse;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.BuyerService#saveSearch(com.owners.gravitas.
     * dto.request.SavedSearchRequest)
     */
    @Override
    public Object createsaveSearch( final SavedSearchChimeraRequest savedSearchChimeraRequest ) {
        LOGGER.info( "Start : create Save Search for request : {} ", JsonUtil.toJson(savedSearchChimeraRequest) );
        Object body = restTemplate.exchange( createSaveSearch, HttpMethod.POST, RestUtil.buildRequest( null, savedSearchChimeraRequest ),
                Object.class ).getBody();
        LOGGER.info( "Response : create Save Search : {} ", JsonUtil.toJson(body) );
        return body;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.BuyerService#deleteExistSaveSearch(java.lang.
     * String)
     */
    @Override
    public Object deleteSaveSearch( String uuid ) {
        SavedSearchRequest savedSearchRequest = new SavedSearchRequest();
        savedSearchRequest.setUuid( uuid );
        return restTemplate.exchange( deleteExistSaveSearch, HttpMethod.DELETE,
                RestUtil.buildRequest( null, savedSearchRequest ), Object.class ).getBody();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.BuyerService#checkSaveSearchExists(java.lang.
     * String)
     */
    @Override
    public boolean checkSaveSearchExists( final String uuid ) {
        LOGGER.info( "Checking if save search exists for uuid : {}" , uuid );
        final String apiUrl = checkSaveSearchExists + uuid;
        final SaveSearchResponse response = restTemplate.getForObject( apiUrl, SaveSearchResponse.class, uuid );
        LOGGER.info( "Response save search exists for uuid : {}, {}" , uuid, JsonUtil.toJson(response));
        return response.getResult().getSaveSearchCount() == 0 ? false : true;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.BuyerService#viewExistSaveSearch(java.lang.
     * String)
     */
    @Override
    public SaveSearchResponse viewExistSaveSearch( String uuid ) {
        LOGGER.info( "Get existed save search details for uuid : {}", uuid );
        final String apiUrl = checkSaveSearchExists + uuid;
        final SaveSearchResponse response = restTemplate.getForObject( apiUrl, SaveSearchResponse.class, uuid );
        return response;
    }

    /**
     * Checks if is buyer auto registration email.
     *
     * @param emailStr
     *            the email str
     * @return true, if is buyer auto registration email
     */
    @Override
    public boolean isBuyerAutoRegistrationEmail( final String emailStr ) {
        boolean autoRegistrationEmail = FALSE;
        final String autoRegistrationEmailStr = config.getBuyerAutoRegistrationEmailStr();
        if (StringUtils.isNotBlank( autoRegistrationEmailStr )) {
            final List< String > buyerAutoRegistrationsEmails = new ArrayList<>();
            Stream.of( autoRegistrationEmailStr.split( COMMA ) )
                    .forEach( email -> buyerAutoRegistrationsEmails.add( email.trim().toLowerCase() ) );
            for ( final String email : buyerAutoRegistrationsEmails ) {
                if (emailStr.startsWith( email )) {
                    autoRegistrationEmail = TRUE;
                    break;
                }
            }
        } else {
            autoRegistrationEmail = TRUE;
        }
        return autoRegistrationEmail;
    }

    /**
     * Checks if is farm long term state.
     *
     * @param state
     *            the state
     * @return true, if is farm long term state
     */
    @Override
    public boolean isFarmLongTermState( final String state ) {
        boolean farmLongTermState = FALSE;
        final String farmLongTermBuyerStatesStr = config.getFarmLongTermBuyerStates();
        if (StringUtils.isNotBlank( farmLongTermBuyerStatesStr ) && StringUtils.isNotBlank( state )) {
            final List< String > farmLongTermBuyerStates = new ArrayList<>();
            Stream.of( farmLongTermBuyerStatesStr.split( COMMA ) )
                    .forEach( farmLongTermBuyerState -> farmLongTermBuyerStates
                            .add( farmLongTermBuyerState.trim().toLowerCase() ) );
            if (farmLongTermBuyerStates.contains( state.toLowerCase() )) {
                farmLongTermState = TRUE;
            }

        } else {
            farmLongTermState = TRUE;
        }
        return farmLongTermState;
    }

    @Override
    public UserNotificationConfigResponse updateNotificationConfig(UserNotificationConfigDetails userNotificationConfigDetails ) {
        LOGGER.info( "Start : update notification config request : {} ", userNotificationConfigDetails );
        UserNotificationConfigResponse response = null;
        String body = restTemplate.exchange( updateUserNtfcConfigUrl, HttpMethod.POST,
                RestUtil.buildRequest( null, userNotificationConfigDetails ), String.class ).getBody();
        LOGGER.info( "Response : update notification config : {} ", body );
        try {
            response = JsonUtil.convertFromJson( body, UserNotificationConfigResponse.class );
        } catch ( IOException e ) {
            LOGGER.error( "Error while converting to update notification config response : {}", response, e );
        }
        return response;
    }
    
    @Override
	public BuyerDeviceResponse getBuyerDeviceDetails(String uuid) {
		LOGGER.info("Start : Get buyer device details : {} ", uuid);
		BuyerDeviceResponse response = null;
		final String body = restTemplate.getForObject(userDeviceDetailsUrl + uuid, String.class);
		LOGGER.info("Response : Get buyer device details : {} ", body);
		try {
			response = JsonUtil.convertFromJson(body, BuyerDeviceResponse.class);
		} catch (Exception e) {
			LOGGER.error("Error while converting buyer device response : {}", response, e);
		}
		return response;
	}
}
