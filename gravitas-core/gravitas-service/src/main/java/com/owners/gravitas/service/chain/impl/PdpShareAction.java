package com.owners.gravitas.service.chain.impl;

import static com.owners.gravitas.enums.BuyerAction.PDP_SHARED;

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
public class PdpShareAction implements PriorityAction {

    @Autowired
    private TourRequestAbandonmentAction tourRequestAbandonmentAction;
    
    private PriorityAction nextAction;
    
    @PostConstruct
    public void initialize() {
        this.setNextAction( tourRequestAbandonmentAction );
    }
    
    @Override
    public BuyerAction getHighestPriorityAction( List< String > recievedActions, String objectType ) {
        if (recievedActions.contains( PDP_SHARED.name() )) {
            return PDP_SHARED;
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
