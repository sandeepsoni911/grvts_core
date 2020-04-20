package com.owners.gravitas.listener.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.owners.gravitas.amqp.BuyerWebActivitySource;
import com.owners.gravitas.annotation.PerformanceLog;
import com.owners.gravitas.business.BuyerFarmingBusinessService;
import com.owners.gravitas.config.BuyerFarmingConfig;
import com.owners.gravitas.util.JsonUtil;

/**
 * The listener interface for receiving leadActivity events.
 * The class that is interested in processing a leadActivity
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addLeadActivityListener<code> method. When
 * the leadActivity event occurs, that object's appropriate
 * method is invoked.
 *
 * @see LeadActivityEvent
 */
public class BuyerActivityListener {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( BuyerActivityListener.class );

    /** The buyer farming config. */
    @Autowired
    private BuyerFarmingConfig buyerFarmingConfig;

    /** The buyer farming business service. */
    @Autowired
    private BuyerFarmingBusinessService buyerFarmingBusinessService;

    /**
     * Handle buyer activity.
     *
     * @param buyerWebActivitySource
     *            the message
     */
    @PerformanceLog
    public void handleBuyerActivity( final BuyerWebActivitySource buyerWebActivitySource ) {
        LOGGER.info( "Recieved activity for user id " + buyerWebActivitySource.getUserId() );
        if (buyerFarmingConfig.isWebActivityFollowUpEnabled()) {
            try {
                buyerFarmingBusinessService.processWebActivity( buyerWebActivitySource );
            } catch ( Exception e ) {
                LOGGER.error( "Exception while handling buyer activity {} with exception",
                        JsonUtil.toJson( buyerWebActivitySource ), e );
            }
        }
    }
}
