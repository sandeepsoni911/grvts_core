package com.owners.gravitas.business.task;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.service.AgentReportService;

/**
 * The Class AgentLookupTaskTest.
 *
 * @author amits
 */
public class AgentReportTaskTest extends AbstractBaseMockitoTest {

    /** The agent report task. */
    @InjectMocks
    private AgentReportTask agentReportTask;

    /** The agent report service. */
    @Mock
    private AgentReportService agentReportService;

    /**
     * Testget agents stagewise cta.
     *
     * @throws InterruptedException
     *             the interrupted exception
     * @throws ExecutionException
     *             the execution exception
     */
    @Test
    public void testgetAgentsStagewiseCta() throws InterruptedException, ExecutionException {
        final Date fromDtm = new Date( System.currentTimeMillis() );
        final Date toDtm = new Date( System.currentTimeMillis() );
        final String agentEmail = "test.we@com";
        List< Object[] > response = new ArrayList<>();
        Object[] arr = new Object[6];
        arr[0] = "Buyer";
        arr[1] = "testValue";
        arr[2] = "testValue";
        arr[3] = "testValue";
        arr[4] = "testValue";
        arr[5] = "testValue";
        response.add( arr );
        Mockito.when( agentReportService.getAgentsStagewiseCta( fromDtm, toDtm, agentEmail ) ).thenReturn( response );
        Future< Map< String, Map< String, Map< String, String > > > > actualResponse = agentReportTask
                .getAgentsStagewiseCta( fromDtm, toDtm, agentEmail );
        Assert.assertNotNull( actualResponse );
        Assert.assertEquals( actualResponse.get().containsKey( arr[0] ), true );
        Assert.assertEquals( actualResponse.get().get( arr[0] ).containsKey( arr[1] ), true );
    }

    /**
     * Testget agents stagewise median.
     *
     * @throws InterruptedException
     *             the interrupted exception
     * @throws ExecutionException
     *             the execution exception
     */
    @Test
    public void testgetAgentsStagewiseMedian() throws InterruptedException, ExecutionException {
        final Date fromDtm = new Date( System.currentTimeMillis() );
        final Date toDtm = new Date( System.currentTimeMillis() );
        final String agentEmail = "test.we@com";
        List< Object[] > response = new ArrayList<>();
        Object[] arr = new Object[6];
        arr[0] = "Buyer";
        arr[1] = new BigDecimal( 135.69 );
        arr[2] = "Buyer";
        response.add( arr );
        Mockito.when( agentReportService.getOpportunitiesStageDetailsByDateRange( agentEmail, toDtm, fromDtm ) )
                .thenReturn( response );

        Future< Map< String, Map< String, String > > > actualResponse = agentReportTask
                .getAgentsStagewiseMedian( agentEmail, fromDtm, toDtm );
        Assert.assertNotNull( actualResponse );
        Assert.assertEquals( actualResponse.get().containsKey( arr[0] ), true );
    }
}
