package com.owners.gravitas.service;

import java.util.Date;

import com.owners.gravitas.dto.request.AgentTaskRequest;
import com.owners.gravitas.dto.request.CoShoppingLeadRequest;
import com.owners.gravitas.dto.request.CoShoppingLeadUpdateRequest;
import com.owners.gravitas.dto.request.LeadRequest;
import com.zuner.coshopping.model.api.Response;
import com.zuner.coshopping.model.common.Resource;
import com.zuner.coshopping.model.enums.LeadRequestType;
import com.zuner.coshopping.model.lead.LeadModel;
import com.zuner.coshopping.model.lead.LeadResponse;
import com.zuner.coshopping.model.lead.LeadUpdateRequest;

public interface CoShoppingService {
    
    /**
     * Save lead info to co-shopping firebase
     * 
     * @param leadRequest
     * @return
     */
    Resource postLeadDetails(CoShoppingLeadRequest leadRequest);
    
    /**
     * checks eligibility before saving lead info to coshopping
     * 
     * @param request
     * @return
     */
    boolean isLeadEligibleForCoShoppingPush( LeadRequest request );
    
    /**
     * Update lead info to co-shopping firebase.
     * 
     * @param leadRequest
     * @return
     */
    Resource updateLeadDetails(CoShoppingLeadUpdateRequest leadUpdateRequest);
    
    /**
     * get lead info from co-shopping firebase
     * 
     * @param Response
     * @return
     */
    Response<LeadResponse> getLeadDetails(String emailId);
    
    /**
     * Gets respective enum type
     * 
     * @param requestType
     * @return
     */
    LeadRequestType getRequestType( String requestType );

    /**
     * 
     * @param tourTime
     * @param timeZone
     * @return
     */
    String getTourTime( Date tourTime, String timeZone );
    
    /**
     * Update lead details in co-shopping FB
     * 
     * @param leadUpdateRequest
     * @return
     */
    Resource updateLeadDetails( final LeadUpdateRequest leadUpdateRequest );
    
    /**
     * Edit lead request
     * 
     * @param leadUpdateRequest
     * @param coShoppingId
     * @return
     */
    Resource editLeadDetails( final LeadModel editLeadRequest, String coShoppingId );
    
    /**
     * Checks if is eligible for coshopping tour creation.
     *
     * @param taskRequest
     *            the task request
     * @return true, if is eligible for coshopping tour creation
     */
    boolean isEligibleForCoshoppingTourCreation( final AgentTaskRequest taskRequest );
}