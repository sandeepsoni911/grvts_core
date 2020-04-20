package com.owners.gravitas.service.chain.impl;

import static com.owners.gravitas.enums.BuyerAction.FAVORITED;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.enums.BuyerAction;
import com.owners.gravitas.service.chain.PriorityAction;

@Service
public class FavoritedAction implements PriorityAction {

    @Autowired
    private RepeatPdpViewAction repeatPdpViewAction;
    
    private PriorityAction nextAction;
    
    @PostConstruct
    public void initialize() {
        this.setNextAction( repeatPdpViewAction );
    }

    @Override
    public BuyerAction getHighestPriorityAction( List< String > recievedActions, String objectType ) {
        if (recievedActions.contains( FAVORITED.name() )) {
            return FAVORITED;
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
