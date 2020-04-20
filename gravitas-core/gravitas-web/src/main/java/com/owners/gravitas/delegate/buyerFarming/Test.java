package com.owners.gravitas.delegate.buyerFarming;

import org.flowable.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.owners.gravitas.delegate.BaseServiceTask;

@Service( "test" )
public class Test extends BaseServiceTask {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( Test.class );
    
    @Override
    public void execute( final DelegateExecution execution ) {
        LOGGER.info( "****************For Opportunity******************" );
    }

}
