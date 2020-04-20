package com.owners.gravitas.service;

import java.util.Map;

import com.owners.gravitas.amqp.BuyerWebActivitySource;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.enums.BuyerAction;

public interface EventPriorityService {

    /**
     * Gets highest priority event name
     * 
     * @param buyerWebActivitySource
     * @param contact
     * @return
     */
    BuyerAction getHighestPriority( BuyerWebActivitySource buyerWebActivitySource, Contact contact );

    /**
     * checks the jmx flag
     * 
     * @param webActivityAction
     * @return
     */
    boolean isEligibleForEmailNotification(BuyerAction webActivityAction);
    
    /**
     * Get Subject And Email Template
     * 
     * @param priorityAction
     * @return
     */
    Map<String, String> getSubjectAndEmailTemplate( BuyerAction priorityAction );
}
