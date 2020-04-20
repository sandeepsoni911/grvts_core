package com.owners.gravitas.service.impl;

import static com.owners.gravitas.enums.ErrorCode.INVALID_LISTING_ID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.dto.PropertyAddress;
import com.owners.gravitas.dto.PropertyData;
import com.owners.gravitas.dto.response.PropertyDetailsResponse;
import com.owners.gravitas.dto.response.SimilarProperty;
import com.owners.gravitas.dto.response.SimilarPropertyResponse;
import com.owners.gravitas.dto.response.SuggestionOutputResponse;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.PropertyService;
import com.owners.gravitas.util.JsonUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class PropertyDetailsServiceImpl.
 *
 * @author vishwanathm
 */
@Service
public class PropertyServiceImpl implements PropertyService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( PropertyServiceImpl.class );

    /** The rest template. */
    @Autowired
    private RestTemplate restTemplate;

    /** The pdp url. */
    @Value( "${property.details.api.url}" )
    private String propertyDetailsUrl;
    
    @Value( "${property.pdp.url}" )
    private String pdpUrl;

    /** The location url. */
    @Value( "${location.details.api.url}" )
    private String locationUrl;
    
    @Value( "${buyer.similar.listings.search.url}") 
    private String buyerSimilarListingsSearchUrl;
    
    /**
     * Gets the property details.
     *
     * @param listingId
     *            the listing id
     * @return the property details
     */
    @Override
    public PropertyDetailsResponse getPropertyDetails( final String listingId ) {
        PropertyDetailsResponse propertyDetails = null;
        try {
            final String pdpResponse = restTemplate.getForObject( String.format( propertyDetailsUrl, listingId ), String.class );
            propertyDetails = JsonUtil.toType( pdpResponse, PropertyDetailsResponse.class );
        } catch ( final Exception e ) {
            LOGGER.error( "problem getting property details from owners.com", e );
        }
        return propertyDetails;
    }
    
    /**
     * Gets the property pdp url.
     *
     * @param listingId
     *            the listing id
     * @return the property pdp url
     */
    @Override
    public String getPdpUrl(PropertyDetailsResponse propertyDetailsResponse, String listingId) {
        String propertyPdpUrl = pdpUrl;
        try {
            //Replace state/city/pdp/listingId
            propertyPdpUrl = String.format( propertyPdpUrl, listingId );
            String state = propertyDetailsResponse.getData().getPropertyAddress().getState();
            String city = propertyDetailsResponse.getData().getPropertyAddress().getCity();
            String pdpString = null;
            Map<String, List<String>> mapParameters = propertyDetailsResponse.getData().getMapParameters();
            if(!CollectionUtils.isEmpty(mapParameters)){
                 List<String> pdpStringList = mapParameters.get("pdp-address-url");
                 if(!CollectionUtils.isEmpty(pdpStringList)){
                     pdpString = pdpStringList.get(0);
                 }
            }
            if(StringUtils.isNotBlank(state)){
                propertyPdpUrl = propertyPdpUrl.replace("state", state.toLowerCase());
            }
            if(StringUtils.isNotBlank(city)){
                propertyPdpUrl = propertyPdpUrl.replace("city", city.toLowerCase());
            }
            if(StringUtils.isNotBlank(pdpString)){
                propertyPdpUrl = propertyPdpUrl.replace("pdpurl", pdpString.toLowerCase());
            }
        } catch (final Exception e) {
            LOGGER.error("problem getting pdp url for :{}", propertyPdpUrl, e);
        }
        return propertyPdpUrl;
    }


    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.PropertyService#getPropertyDetailsIfAvailable
     * (java.lang.String)
     */
    @Override
    public PropertyDetailsResponse getPropertyDetailsIfAvailable( final String listingId ) {
        PropertyDetailsResponse propertyDetails = null;
        try {
            final String pdpResponse = restTemplate.getForObject( String.format( propertyDetailsUrl, listingId ), String.class );
            propertyDetails = JsonUtil.toType( pdpResponse, PropertyDetailsResponse.class );
        } catch ( final Exception e ) {
            LOGGER.error( "problem getting property details from owners.com for listing id {}", listingId, e );
        }
        return propertyDetails;
    }

    /**
     * Gets the locations details in the given location.
     *
     * @param locationId
     *            the location id
     * @return the locations details
     */
    @Override
    public SuggestionOutputResponse getAllOptionsInLocation( final String locationId ) {
        SuggestionOutputResponse suggestionOutputResponse = null;
        try {
            final String response = restTemplate.getForObject( String.format( locationUrl, locationId ), String.class );
            suggestionOutputResponse = JsonUtil.toType( response, SuggestionOutputResponse.class );
        } catch ( final Exception e ) {
            LOGGER.error( "problem getting property details from owners.com", e );
            throw new ApplicationException( "Invalid response, please check your listing id " + locationId, e,
                    INVALID_LISTING_ID );
        }
        return suggestionOutputResponse;
    }
    
    @Override
    public SimilarPropertyResponse getBuyerSimilarListingsSearchDetails(PropertyData propertyData) {
        LOGGER.info("Start : Get buyer similar listings search details for the mls id : {} ", propertyData.getMlsID());
        SimilarPropertyResponse response = null;
        StringBuilder sb = new StringBuilder( buyerSimilarListingsSearchUrl );
        if (null != propertyData.getPropertyAddress()) {
            sb.append( "?latitude=" ).append( propertyData.getPropertyAddress().getLatitude() ).append( "&longitude=" )
                    .append( propertyData.getPropertyAddress().getLongitude() ).append( "&bed=" )
                    .append( propertyData.getBedRooms() ).append( "&price=" ).append( propertyData.getPrice() )
                    .append( "&proptype=" ).append( propertyData.getPropertyType() ).append( "&tagType=MLS" )
                    .append( "&propertyId=" ).append( getValidPropertyId( propertyData.getPropertyID() ) );
        }
        LOGGER.info( "Request : Get buyer similar listings details url : {} ", sb.toString() );
        final String body = restTemplate.getForObject( sb.toString(), String.class );
        LOGGER.info("Response : Get buyer similar listings details : {} ", body);
        try {
            response = JsonUtil.convertFromJson(body, SimilarPropertyResponse.class);
        } catch (Exception e) {
            LOGGER.error("Error while converting buyer similar listings response : {}", response, e);
        }
        return response;
    }
    
    @Override
    public String formatStreetNameAndZip( String streetName, String zip ) {
        if (StringUtils.isBlank( streetName )) {
            return null;
        }
        String[] components = streetName.split( Constants.REG_EXP_BLANK_SPACES );
        StringBuilder sb = new StringBuilder();
        for ( String component : components ) {
            sb.append( component ).append( Constants.HYPHEN );
        }
        sb.append( zip );
        return sb.toString();
    }

    @Override
    public String getValidPropertyId( String propertyId ) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank( propertyId )) {
            return propertyId.replaceAll( "[^a-zA-Z0-9]", "" );
        }
        return propertyId;
    }
    
    @Override
    public String getPdpUrl( SimilarProperty similarProperty ) {
        PropertyDetailsResponse propertyDetailsResponse = new PropertyDetailsResponse();
        PropertyData propertyData = new PropertyData();
        PropertyAddress propertyAddress = new PropertyAddress();
        propertyAddress.setCity( similarProperty.getCity() );
        propertyAddress.setState( similarProperty.getState() );
        propertyData.setPropertyAddress( propertyAddress );
        final String formatStreetNameAndZip = formatStreetNameAndZip( similarProperty.getStreetName(),
                similarProperty.getZip() );
        if (StringUtils.isNotBlank( formatStreetNameAndZip )) {
            Map< String, List< String > > mapParameters = new HashMap<>();
            List< String > list = new ArrayList<>();
            list.add( formatStreetNameAndZip );
            mapParameters.put( "pdp-address-url", list );
            propertyData.setMapParameters( mapParameters );
        }
        propertyDetailsResponse.setData( propertyData );
        return getPdpUrl( propertyDetailsResponse, getValidPropertyId( similarProperty.getPropertyId() ) );
    }
}
