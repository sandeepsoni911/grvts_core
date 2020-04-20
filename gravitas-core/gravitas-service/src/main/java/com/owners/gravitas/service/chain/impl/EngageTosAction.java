package com.owners.gravitas.service.chain.impl;

import static com.owners.gravitas.constants.Constants.LEAD;
import static com.owners.gravitas.enums.BuyerAction.ENGAGED_TOS;

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
public class EngageTosAction implements PriorityAction {

    @Autowired
    private UndecidedPdpViewAction undecidedPdpViewAction;
    
    private PriorityAction nextAction;
    
    @PostConstruct
    public void initialize() {
        this.setNextAction( undecidedPdpViewAction );
    }
    
    @Override
    public BuyerAction getHighestPriorityAction( List< String > recievedActions, String objectType ) {
        if (recievedActions.contains( ENGAGED_TOS.name()) && objectType.toLowerCase().equals( LEAD.toLowerCase() )) {
            return ENGAGED_TOS;
        } else {
            return this.nextAction.getHighestPriorityAction( recievedActions, objectType );
        }
    }

    /**
     * @param nextAction
     *            the nextAction to set
     */
    public void setNextAction( PriorityAction nextAction ) {
        this.nextAction = nextAction;
    }
}
