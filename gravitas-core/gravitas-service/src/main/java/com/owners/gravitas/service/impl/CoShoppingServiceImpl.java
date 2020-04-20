package com.owners.gravitas.service.impl;

import static com.owners.gravitas.util.DateUtil.DEFAULT_COSHOPPING_TOUR_DTM_PATTERN;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.owners.gravitas.config.CoShoppingConfig;
import com.owners.gravitas.dto.request.AgentTaskRequest;
import com.owners.gravitas.dto.request.CoShoppingLeadRequest;
import com.owners.gravitas.dto.request.CoShoppingLeadUpdateRequest;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.enums.TaskType;
import com.owners.gravitas.service.CoShoppingService;
import com.owners.gravitas.service.util.RestServiceUtil;
import com.owners.gravitas.util.DateUtil;
import com.owners.gravitas.util.JsonUtil;
import com.zuner.coshopping.model.api.Response;
import com.zuner.coshopping.model.common.Resource;
import com.zuner.coshopping.model.enums.LeadRequestType;
import com.zuner.coshopping.model.lead.LeadModel;
import com.zuner.coshopping.model.lead.LeadResponse;
import com.zuner.coshopping.model.lead.LeadUpdateRequest;

/**
 * 
 * @author gururasm
 *
 */
@Service
public class CoShoppingServiceImpl implements CoShoppingService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( CoShoppingServiceImpl.class );

    @Autowired
    private RestServiceUtil restServiceUtil; 
    
    /** The co shopping config. */
    @Autowired
    private CoShoppingConfig coShoppingConfig;
        
    /** The contact url. */
    @Value( value = "${coshopping.lead.save.endpoint}" )
    private String coshoppingLeadSaveUrl;
    
    @Value( value = "${coshopping.lead.get.endpoint}" )
    private String coshoppingLeadGetUrl;
    
    @Value(value="${coshopping.lead.update.endpoint}")
    private String coShoppingLeadUpdateUrl;

    @Override
    public Resource postLeadDetails( final CoShoppingLeadRequest leadRequest ) {
        final Response< Resource > leadPostResponse = restServiceUtil.postEntityWithGenericResponse( coshoppingLeadSaveUrl,
                leadRequest, new ParameterizedTypeReference< Response< Resource > >() {
                } );
        LOGGER.debug( "Hitting coshopping lead post url : {} with json : {}", coshoppingLeadSaveUrl, leadRequest );
        return leadPostResponse.getResult();
    }

    @Override
    public boolean isLeadEligibleForCoShoppingPush( final LeadRequest request ) {
        boolean eligibility = false;
        if (( LeadRequestType.SCHEDULE_TOUR.name().equalsIgnoreCase( request.getRequestType() )
                || LeadRequestType.REQUEST_INFORMATION.name().equalsIgnoreCase( request.getRequestType() )
                || LeadRequestType.MAKE_OFFER.name().equalsIgnoreCase( request.getRequestType() ) )
                && StringUtils.isNotBlank( request.getOwnersComIdentifier() )
                && StringUtils.isNotBlank( request.getMlsId() ) && StringUtils.isNotBlank( request.getListingId() )
                && StringUtils.isNotBlank( request.getEmail() )) {
            eligibility = true;
        }
        LOGGER.info( "Lead eligibility for coshopping save is {}", eligibility );
        return eligibility;
    }

    @Override
    public Resource updateLeadDetails( final CoShoppingLeadUpdateRequest leadUpdateRequest ) {
        final Response< Resource > leadPostResponse = restServiceUtil.patchEntityWithGenericResponse(
                coShoppingLeadUpdateUrl, leadUpdateRequest, new ParameterizedTypeReference< Response< Resource > >() {
                } );
        LOGGER.info( "Hitting coshopping patch url : {} with json : {}", coShoppingLeadUpdateUrl, leadUpdateRequest );
        return leadPostResponse.getResult();
    }

	@Override
	public Response<LeadResponse> getLeadDetails(final String emailId) {
		final String url = coshoppingLeadGetUrl + "/" + emailId + "/";
		LOGGER.info( "Hitting coshopping lead get url : {} with path parameter email id : {}", url, emailId );
        final Response<LeadResponse> leadGetResponse = restServiceUtil.getEntityWithGenericResponse( url, 
				new ParameterizedTypeReference< Response<LeadResponse> >() { } );
        return leadGetResponse;
	}

    @Override
    public LeadRequestType getRequestType( final String requestType ) {
        LeadRequestType leadRequestType = null;
        switch(requestType) {
            case "MAKE_OFFER" : 
                leadRequestType = LeadRequestType.MAKE_OFFER;
                break;
            case "REQUEST_INFORMATION" :
                leadRequestType = LeadRequestType.REQUEST_INFORMATION;
                break;
            case "SCHEDULE_TOUR" :
                leadRequestType = LeadRequestType.SCHEDULE_TOUR;
                break;
        }
        return leadRequestType;
    }
    
    @Override
    public String getTourTime( final Date tourTime, final String timeZone ) {
        // eg. "propertyTourInformation": "05/25/2018:[10:00 AM]|"
        LOGGER.info( "tour time is : {}", tourTime );
        final String formattedTourDtm = DateUtil.toString( tourTime, DEFAULT_COSHOPPING_TOUR_DTM_PATTERN, timeZone );
        LOGGER.info( "formatted tour time is : {}", formattedTourDtm );
        return formattedTourDtm;
    }

    @Override
    public Resource updateLeadDetails( final LeadUpdateRequest leadUpdateRequest ) {
        final Response< Resource > leadPostResponse = restServiceUtil.patchEntityWithGenericResponse(
                coShoppingLeadUpdateUrl, leadUpdateRequest, new ParameterizedTypeReference< Response< Resource > >() {
                } );
        LOGGER.info( "Hitting coshopping patch url : {} with json : {}", coShoppingLeadUpdateUrl, JsonUtil.toJson( leadUpdateRequest ) );
        return leadPostResponse.getResult();
    }
    
    @Override
    public Resource editLeadDetails( final LeadModel editLeadRequest, String coShoppingId ) {
        final Response< Resource > leadPostResponse = restServiceUtil.putEntityWithGenericResponse(
                coshoppingLeadSaveUrl + "/" + coShoppingId, editLeadRequest, new ParameterizedTypeReference< Response< Resource > >() {
                } );
        LOGGER.info( "Hitting coshopping put url : {} with json : {}", coShoppingLeadUpdateUrl, JsonUtil.toJson( leadPostResponse ) );
        return leadPostResponse.getResult();
    }

    @Override
    public boolean isEligibleForCoshoppingTourCreation( AgentTaskRequest taskRequest ) {
        return coShoppingConfig.isScheduledTourByFieldAgentEnabled()
                && TaskType.SCHEDULE_TOUR.getType().equalsIgnoreCase( taskRequest.getType() )
                && StringUtils.isNotBlank( taskRequest.getListingId() ) && null != taskRequest.getDueDtm()
                && StringUtils.isBlank( taskRequest.getCoShoppingId() );
    }
    
}
