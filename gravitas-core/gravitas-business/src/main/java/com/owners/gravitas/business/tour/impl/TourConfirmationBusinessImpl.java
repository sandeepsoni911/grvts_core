package com.owners.gravitas.business.tour.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubzu.notification.dto.client.digest.DigestEmailNotification;
import com.owners.gravitas.business.builder.CoShoppingLeadBuilder;
import com.owners.gravitas.business.builder.ScheduleTourConfirmEmailBuilder;
import com.owners.gravitas.business.tour.TourConfirmationBusiness;
import com.owners.gravitas.constants.NotificationParameters;
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.AgentInfo;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.dto.request.AgentTaskRequest;
import com.owners.gravitas.dto.request.CoShoppingLeadRequest;
import com.owners.gravitas.dto.request.TaskUpdateRequest;
import com.owners.gravitas.dto.response.NotificationResponse;
import com.owners.gravitas.dto.response.PropertyDetailsResponse;
import com.owners.gravitas.service.AgentDetailsService;
import com.owners.gravitas.service.AgentService;
import com.owners.gravitas.service.AgentTaskService;
import com.owners.gravitas.service.CoShoppingService;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.service.PropertyService;
import com.owners.gravitas.util.DateUtil;
import com.zuner.coshopping.model.common.Resource;
import com.zuner.coshopping.model.lead.LeadModel;

@Service( "tourConfirmationBusiness" )
public class TourConfirmationBusinessImpl implements TourConfirmationBusiness {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( TourConfirmationBusinessImpl.class );

    @Autowired
    private AgentService agentService;

    /** The property service. */
    @Autowired
    private PropertyService propertyService;

    /** The contact service v1. */
    @Autowired
    private ContactEntityService contactServiceV1;

    @Autowired
    private MailService mailService;

    /** The agent task service. */
    @Autowired
    private AgentTaskService agentTaskService;

    @Autowired
    private AgentDetailsService agentDetailsService;
    
    @Autowired
    private CoShoppingService coShoppingService; 

    @Autowired
    private ScheduleTourConfirmEmailBuilder scheduleTourConfirmEmailBuilder;
    
    /** The co shopping lead request builder. */
    @Autowired
    private CoShoppingLeadBuilder coShoppingLeadBuilder;

    @Override
    public String sendConfirmationEmailForScheduleTour( String agentId, Task task ) {
        String status = null;
        PropertyDetailsResponse propertyDetailsResponse = propertyService.getPropertyDetails( task.getListingId() );
        if (null != propertyDetailsResponse) {
            Agent agent = agentService.getAgentById( agentId );
            AgentInfo agentInfo = null != agent ? agent.getInfo() : new AgentInfo();
            com.owners.gravitas.domain.entity.Contact contact = contactServiceV1
                    .getContactByFbOpportunityId( task.getOpportunityId() );
            Map< String, String > map = agentTaskService.prepareAgentAndBuyerInfoMap( agentInfo, contact );
            final String tourTime = getTourTime( agentInfo.getEmail(), task.getDueDtm() );
            map.put( NotificationParameters.TOUR_TIME, tourTime );
            map.put( NotificationParameters.TASK_ID, task.getId() );
            DigestEmailNotification digestEmailNotification = scheduleTourConfirmEmailBuilder
                    .prepareEmailNotification( propertyDetailsResponse.getData(), map );
            NotificationResponse response = mailService.sendDigest( digestEmailNotification );
            status = null != response ? response.getStatus() : null;
            LOGGER.info( "SCHEDULE TOUR confirmation email sent for the agentId {} with status {}", agentId, status );
        }
        return status;
    }
    
    @Override
    public String createCoshoppingTour( AgentTaskRequest request, String opportunityId, String agentEmail ) {
        String coShoppingId = null;
        Contact contact = null;
        if (coShoppingService.isEligibleForCoshoppingTourCreation( request )) {
            LOGGER.info( "all conditions met, bulding coshopping request" );
            if (StringUtils.isNotBlank( opportunityId )) {
                contact = contactServiceV1.findByCrmId( opportunityId );
            }
            final PropertyDetailsResponse propertyDetails = propertyService.getPropertyDetails( request.getListingId() );
            LOGGER.info( "property details for the listing id {} is {}", request.getListingId(), propertyDetails.getData() );
            // use agent email to get timeZone
            final String agentTimeZone = agentDetailsService.getAgentsTimeZone( agentEmail );
            final CoShoppingLeadRequest coShoppingLeadRequest = coShoppingLeadBuilder.build( request.getDueDtm(),
                    contact, propertyDetails.getData(), agentTimeZone );
            coShoppingLeadRequest.getLeadModel().setType( coShoppingService.getRequestType( request.getType() ) );
            coShoppingLeadRequest.getLeadModel().setListingId( request.getListingId() );
            coShoppingLeadRequest.getLeadModel().setStatus( request.getStatus() );
            Resource coshoppingTourResponse = null;
            try {
                coshoppingTourResponse = coShoppingService.postLeadDetails( coShoppingLeadRequest );
            } catch ( Exception ex ) {
                LOGGER.error(
                        "Exception occurred in create co-shopping tour while pushing to coshopping api for the opportunity id {} with exception ",
                        opportunityId, ex );
            }         
            if (null != coshoppingTourResponse) {
                coShoppingId = coshoppingTourResponse.getId();
            }
            LOGGER.info( "coshopping tour id is : {} ", coShoppingId );
        }
        return coShoppingId;
    }

    @Override
    public Resource editCoshoppingTour( TaskUpdateRequest request, Task task, String agentEmail ) {
        LOGGER.info( "edit coshopping tour for the agent {}", agentEmail );
        LeadModel editLeadRequest = new LeadModel();
        String agentTimeZone = null;
        Resource coshoppingTourResponse = null;
        
        if (request.getDueDtm() != null) {
            agentTimeZone = agentDetailsService.getAgentsTimeZone( agentEmail );
            editLeadRequest.setPropertyTourInformation( coShoppingService.getTourTime( request.getDueDtm(), agentTimeZone ) );
        }
        if (StringUtils.isNotBlank( request.getListingId() ) && !task.getListingId().equalsIgnoreCase( request.getListingId() )) {
            final PropertyDetailsResponse propertyDetails = propertyService.getPropertyDetails( request.getListingId() );
            LOGGER.info( "property details for the listing id {} is {}", request.getListingId(), propertyDetails.getData() );
            if (null != propertyDetails && null != propertyDetails.getData()) {
                CoShoppingLeadRequest coShoppingLeadRequest = coShoppingLeadBuilder.build( request.getDueDtm(), null,
                        propertyDetails.getData(), agentTimeZone );
                editLeadRequest = coShoppingLeadRequest.getLeadModel();
                editLeadRequest.setListingId( request.getListingId() ); 
            } else {
                LOGGER.warn( "No property details found for the listing id {}", request.getListingId() );
            }
        }
        editLeadRequest.setStatus( request.getStatus() );
        try {
            coshoppingTourResponse = coShoppingService.editLeadDetails( editLeadRequest, task.getCoShoppingId() );
        } catch ( Exception e ) {
            LOGGER.error( "Error while editing data to Coshopping for the co-shopping id: {}", task.getCoShoppingId(), e );
        }
        if (null != coshoppingTourResponse) {
            LOGGER.info( "coshopping tour editing is done for the id : {} ", coshoppingTourResponse.getId() );
        }
        return coshoppingTourResponse;
    }
    
    private String getTourTime( String agentEmail, Long dueDtm ) {
        DateTime tourTime = new DateTime( dueDtm );
        final String agentTimeZone = agentDetailsService.getAgentsTimeZone( agentEmail );
        final String formattedTourDtm = DateUtil.toString( tourTime.toDate(), DateUtil.SCHEDULE_TOUR_DATE_TIME_FORMAT,
                agentTimeZone );
        return formattedTourDtm;
    }
}
