package com.owners.gravitas.business.tour;

import com.owners.gravitas.domain.Task;
import com.owners.gravitas.dto.request.AgentTaskRequest;
import com.owners.gravitas.dto.request.TaskUpdateRequest;
import com.zuner.coshopping.model.common.Resource;

public interface TourConfirmationBusiness {

    /**
     * send confirmation email for SCHEDULE_TOUR
     * 
     * @param agentId
     * @param task
     * @return
     */
    String sendConfirmationEmailForScheduleTour( String agentId, Task task );
    
    /**
     * create co-shopping schedule tour
     * 
     * @param request
     * @param opportunity
     * @param agentEmail
     * @return
     */
    String createCoshoppingTour( AgentTaskRequest request, String opportunity, String agentEmail );
    
    /**
     * create co-shopping schedule tour
     * 
     * @param request
     * @param opportunity
     * @param agentEmail
     * @return
     */
    Resource editCoshoppingTour( TaskUpdateRequest request, Task task, String agentEmail );
}
