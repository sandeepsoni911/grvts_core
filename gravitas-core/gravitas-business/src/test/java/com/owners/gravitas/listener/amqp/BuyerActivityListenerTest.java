package com.owners.gravitas.listener.amqp;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.BuyerWebActivitySource;
import com.owners.gravitas.business.impl.BuyerFarmingBusinessServiceImpl;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.BuyerFarmingConfig;
import com.owners.gravitas.domain.entity.Process;

/**
 * The Class BuyerActivityListenerTest.
 * 
 * @author pabhishek
 */
public class BuyerActivityListenerTest extends AbstractBaseMockitoTest {

    /** The buyer activity listener. */
    @InjectMocks
    private BuyerActivityListener buyerActivityListener;

    /** The buyer farming business service impl. */
    @Mock
    private BuyerFarmingBusinessServiceImpl buyerFarmingBusinessServiceImpl;

    /** The buyer farming config. */
    @Mock
    private BuyerFarmingConfig buyerFarmingConfig;

    /**
     * Test handle buyer activity.
     */
    @Test
    public void testHandleBuyerActivity() {
        final BuyerWebActivitySource buyerWebActivitySource = new BuyerWebActivitySource();
        buyerWebActivitySource.setUserId( "12345" );
        final com.owners.gravitas.domain.entity.Process process = new Process();
        process.setExecutionId( "11" );
        final Map< String, Object > paramData = new HashMap<>();
        paramData.put( "contactWebActivityMessage", buyerWebActivitySource );

        when( buyerFarmingConfig.isWebActivityFollowUpEnabled() ).thenReturn( true );
        doNothing().when( buyerFarmingBusinessServiceImpl ).processWebActivity( buyerWebActivitySource );

        buyerActivityListener.handleBuyerActivity( buyerWebActivitySource );

        verify( buyerFarmingConfig ).isWebActivityFollowUpEnabled();
        verify( buyerFarmingBusinessServiceImpl ).processWebActivity( buyerWebActivitySource );
    }
}
