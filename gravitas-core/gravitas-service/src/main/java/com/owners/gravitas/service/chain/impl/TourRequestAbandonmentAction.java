package com.owners.gravitas.service.chain.impl;

import static com.owners.gravitas.enums.BuyerAction.TOUR_REQUEST_ABANDONED;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.enums.BuyerAction;
import com.owners.gravitas.service.chain.PriorityAction;

/**
 * 
 * @author gururasm
 *
 */
@Service
public class TourRequestAbandonmentAction implements PriorityAction {

    @Autowired
    private TourSaveAction tourSaveAction;
    
    private PriorityAction nextAction;
    
    @PostConstruct
    public void initialize() {
        this.setNextAction( tourSaveAction );
    }
    
    @Override
    public BuyerAction getHighestPriorityAction( List< String > recievedActions, String objectType ) {
        if (recievedActions.contains( TOUR_REQUEST_ABANDONED.name() )) {
            return TOUR_REQUEST_ABANDONED;
        } else {
            return this.nextAction.getHighestPriorityAction( recievedActions, objectType );
        }
    }

    /**
     * @param priorityAction
     *            the priorityAction to set
     */
    @Override
    public void setNextAction( PriorityAction nextAction ) {
        this.nextAction = nextAction;
    }
}
