package com.owners.gravitas.service.chain.impl;

import static com.owners.gravitas.constants.Constants.LEAD;
import static com.owners.gravitas.enums.BuyerAction.UNENGAGED_30;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.owners.gravitas.enums.BuyerAction;
import com.owners.gravitas.service.chain.PriorityAction;

/**
 * 
 * @author gururasm
 *
 */
@Service
public class Unengage30Action implements PriorityAction {

    private PriorityAction nextAction;
    
    @PostConstruct
    public void initialize() {
        this.setNextAction( null );
    }
    
    @Override
    public BuyerAction getHighestPriorityAction( List< String > recievedActions, String objectType ) {
        if (recievedActions.contains( UNENGAGED_30.name() ) && objectType.toLowerCase().equals( LEAD.toLowerCase() )) {
            return UNENGAGED_30;
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
