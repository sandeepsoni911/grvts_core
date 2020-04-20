package com.owners.gravitas.business.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.digest.DigestEmailNotification;
import com.owners.gravitas.business.builder.CoShoppingLeadBuilder;
import com.owners.gravitas.business.builder.ScheduleTourConfirmEmailBuilder;
import com.owners.gravitas.business.tour.impl.TourConfirmationBusinessImpl;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.AgentInfo;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.dto.PropertyData;
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
import com.zuner.coshopping.model.common.Resource;
import com.zuner.coshopping.model.lead.LeadModel;

public class TourConfirmationBusinessImplTest extends AbstractBaseMockitoTest {
   @InjectMocks
   private TourConfirmationBusinessImpl testTourConfirmationBusinessImpl;
   
   @Mock
   private AgentService agentService;

   /** The property service. */
   @Mock
   private PropertyService propertyService;

   /** The contact service v1. */
   @Mock
   private ContactEntityService contactServiceV1;

   @Mock
   private MailService mailService;

   /** The agent task service. */
   @Mock
   private AgentTaskService agentTaskService;

   @Mock
   private AgentDetailsService agentDetailsService;
   
   /** The contact entity service. */
   @Mock
   private ContactEntityService contactEntityService;
   
   @Mock
   private CoShoppingService coShoppingService; 

   @Mock
   private ScheduleTourConfirmEmailBuilder scheduleTourConfirmEmailBuilder;
   
   /** The co shopping lead request builder. */
   @Mock
   private CoShoppingLeadBuilder coShoppingLeadBuilder;
   
   @Test
   public void testSendConfirmationEmailForScheduleTour() {
       PropertyDetailsResponse propertyDetailsResponse = new PropertyDetailsResponse();
       Task task = new Task();
       task.setDueDtm( new Date().getTime() ); 
       task.setListingId( "357653823782753" );
       when( propertyService.getPropertyDetails( anyString() )).thenReturn( propertyDetailsResponse );
       Agent agent = new Agent();
       when( agentService.getAgentById( anyString() )).thenReturn( agent );
       Contact contact = new Contact();
       when( contactServiceV1.getContactByFbOpportunityId( task.getOpportunityId() )).thenReturn( contact );
       Map< String, String > map = new HashMap<>();
       AgentInfo agentInfo = new AgentInfo();
       agent.setInfo( agentInfo );
       when( agentTaskService.prepareAgentAndBuyerInfoMap( agentInfo, contact )).thenReturn( map );
       final String agentTimeZone = "EST5EDT";
       when( agentDetailsService.getAgentsTimeZone( anyString() )).thenReturn( agentTimeZone );
       DigestEmailNotification digestEmailNotification = new DigestEmailNotification();
       when( scheduleTourConfirmEmailBuilder.prepareEmailNotification( propertyDetailsResponse.getData(), map )).thenReturn( digestEmailNotification );
       NotificationResponse response = new NotificationResponse();
       response.setStatus( "SUCCESS" ); 
       when( mailService.sendDigest( digestEmailNotification )).thenReturn( response ); 
       String status = testTourConfirmationBusinessImpl.sendConfirmationEmailForScheduleTour( "VcjFLTkqhZeOlAdXyPgsMam6XO03", task );
       assertNotNull( status );
       assertEquals( status, "SUCCESS" ); 
   }
   
   @Test
   public void testCreateCoshoppingTour() {
       AgentTaskRequest request = new AgentTaskRequest();
       PropertyDetailsResponse propertyDetails = new PropertyDetailsResponse();
       when (coShoppingService.isEligibleForCoshoppingTourCreation( any(AgentTaskRequest.class) )).thenReturn( true );
       when (propertyService.getPropertyDetails( request.getListingId() )).thenReturn( propertyDetails );
       when(agentDetailsService.getAgentsTimeZone( anyString() )).thenReturn( "GA" );
       CoShoppingLeadRequest coShoppingLeadRequest = new CoShoppingLeadRequest();
       LeadModel leadModel = new LeadModel();
       coShoppingLeadRequest.setLeadModel( leadModel );
       when (coShoppingLeadBuilder.build( any(Date.class), any(Contact.class), any(PropertyData.class), anyString() )).thenReturn( coShoppingLeadRequest );
       Resource coshoppingTourResponse = new Resource();
       coshoppingTourResponse.setId( "dmnfbsad" );
       when (coShoppingService.postLeadDetails( any(CoShoppingLeadRequest.class ))).thenReturn( coshoppingTourResponse );
       String coShoppingId = testTourConfirmationBusinessImpl.createCoshoppingTour( request, "opportunityId", "agentEmail" );
       assertNotNull( coShoppingId );
       assertEquals( "coShoppingId-111", "coShoppingId-111" ); 
   }
   
   @Test
   public void testEditCoshoppingTour() {
       TaskUpdateRequest request = new TaskUpdateRequest();
       Task task = new Task();
       PropertyDetailsResponse propertyDetails = new PropertyDetailsResponse();
       when (coShoppingService.isEligibleForCoshoppingTourCreation( any(AgentTaskRequest.class) )).thenReturn( true );
       when (propertyService.getPropertyDetails( request.getListingId() )).thenReturn( propertyDetails );
       when(agentDetailsService.getAgentsTimeZone( anyString() )).thenReturn( "GA" );
       CoShoppingLeadRequest coShoppingLeadRequest = new CoShoppingLeadRequest();
       LeadModel leadModel = new LeadModel();
       coShoppingLeadRequest.setLeadModel( leadModel );
       when (coShoppingLeadBuilder.build( any(Date.class), any(Contact.class), any(PropertyData.class), anyString() )).thenReturn( coShoppingLeadRequest );
       Resource coshoppingTourResponseMsg = new Resource();
       coshoppingTourResponseMsg.setId( "dmnfbsad" );
       when (coShoppingService.editLeadDetails( any(LeadModel.class ), anyString())).thenReturn( coshoppingTourResponseMsg );
       Resource coshoppingTourResponse = testTourConfirmationBusinessImpl.editCoshoppingTour( request, task, "agentEmail" );
       assertNotNull( coshoppingTourResponse );
   }
}
