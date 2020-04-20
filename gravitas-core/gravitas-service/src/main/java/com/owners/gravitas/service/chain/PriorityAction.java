package com.owners.gravitas.service.chain;

import java.util.List;

import com.owners.gravitas.enums.BuyerAction;

/**
 * Used for creating web activity action item
 * 
 * @author gururasm
 *
 */
public interface PriorityAction {
    
    /**
     * set next priority
     * 
     * @param priorityAction
     */
    void setNextAction( PriorityAction priorityAction );

    /**
     * Gets highest priority action item
     * 
     * @param recievedActions
     * @return
     */
    BuyerAction getHighestPriorityAction( List< String > recievedActions, String objectType );
}
