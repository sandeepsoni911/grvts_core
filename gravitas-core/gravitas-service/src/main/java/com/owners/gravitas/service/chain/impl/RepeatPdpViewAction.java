package com.owners.gravitas.service.chain.impl;

import static com.owners.gravitas.constants.Constants.LEAD;
import static com.owners.gravitas.enums.BuyerAction.REPEAT_PDP_VIEWED;

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
public class RepeatPdpViewAction implements PriorityAction {

    @Autowired
    private PdpViewAction pdpViewAction;
    
    private PriorityAction nextAction;
    
    @PostConstruct
    public void initialize() {
        this.setNextAction( pdpViewAction );
    }

    @Override
    public BuyerAction getHighestPriorityAction( List< String > recievedActions, String objectType ) {
        if (recievedActions.contains( REPEAT_PDP_VIEWED.name() ) && objectType.toLowerCase().equals( LEAD.toLowerCase() )) {
            return REPEAT_PDP_VIEWED;
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
