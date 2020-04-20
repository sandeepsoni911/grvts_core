package com.owners.gravitas.business.task;

import static com.owners.gravitas.constants.Constants.CALL_KEY;
import static com.owners.gravitas.constants.Constants.EMAIL_KEY;
import static com.owners.gravitas.constants.Constants.SMS_KEY;
import static com.owners.gravitas.constants.Constants.TOTAL_KEY;
import static com.owners.gravitas.util.DateUtil.getReadableTime;
import static com.owners.gravitas.util.MathUtil.getMedian;
import static com.owners.gravitas.util.StringUtils.convertObjectToString;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.service.AgentReportService;
import com.owners.gravitas.util.StringUtils;

/**
 * The Class AgentReportTaskImpl.
 *
 * @author raviz
 */
@Service( "agentReportTask" )
public class AgentReportTask {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentReportTask.class );

    /** The opportunity service. */
    @Autowired
    private AgentReportService agentReportService;

    /**
     * Gets the agents stagewise cta.
     *
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @param agentEmail
     *            the agent email
     * @return the agents stagewise cta
     */
    @Async( value = "apiExecutor" )
    public Future< Map< String, Map< String, Map< String, String > > > > getAgentsStagewiseCta( final Date fromDate,
            final Date toDate, final String agentEmail ) {
        LOGGER.info( "Started Calculating stagewise cta details for agent " + agentEmail + " from " + fromDate + " to "
                + toDate );
        return new AsyncResult<>( getCtaDetails( agentEmail, fromDate, toDate ) );
    }

    /**
     * Gets the agents stagewise median.
     *
     * @param agentEmail
     *            the agent email
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @return the agents stagewise median
     */
    @Async( value = "apiExecutor" )
    public Future< Map< String, Map< String, String > > > getAgentsStagewiseMedian( final String agentEmail,
            final Date fromDate, final Date toDate ) {
        LOGGER.info( "Started calculating median stage duration for agent " + agentEmail + " from " + fromDate + " to "
                + toDate );
        final Map< String, Map< String, String > > stageWiseMedian = getStageMedian(
                getStageWiseDurations( agentEmail, fromDate, toDate ) );
        return new AsyncResult< Map< String, Map< String, String > > >( stageWiseMedian );
    }

    /**
     * Gets the stage median.
     *
     * @param stageWiseDuration
     *            the stage wise duration
     * @return the stage median
     */
    private Map< String, Map< String, String > > getStageMedian(
            final Map< String, Map< String, List< Long > > > stageWiseDuration ) {
        final Map< String, Map< String, String > > stageMedianMap = new HashMap<>();
        for ( final Map.Entry< String, Map< String, List< Long > > > entry : stageWiseDuration.entrySet() ) {
            final String opportunityType = entry.getKey();
            final Map< String, String > stageMedian = new HashMap<>();
            stageMedianMap.put( opportunityType, stageMedian );

            entry.getValue().forEach( ( key, value ) -> {
                stageMedian.put( key, getReadableTime( getMedian( value ), true, true ) );
            } );
        }
        return stageMedianMap;
    }

    /**
     * Gets the stage wise durations.
     *
     * @param agentEmail
     *            the agent email
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @return the stage wise durations
     */
    private Map< String, Map< String, List< Long > > > getStageWiseDurations( final String agentEmail,
            final Date fromDate, final Date toDate ) {
        final List< Object[] > stageDetails = agentReportService.getOpportunitiesStageDetailsByDateRange( agentEmail,
                fromDate, toDate );
        final Map< String, Map< String, List< Long > > > oppTypeMap = new HashMap<>();
        oppTypeMap.put( RecordType.BUYER.getType(), new HashMap<>() );
        oppTypeMap.put( RecordType.SELLER.getType(), new HashMap<>() );
        if (isNotEmpty( stageDetails )) {
            for ( final Object[] object : stageDetails ) {
                try {
                    final String stage = convertObjectToString( object[0] ).trim();
                    final long duration = ( ( BigDecimal ) ( object[1] ) ).longValue() * 1000;
                    final String oppType = convertObjectToString( object[2] ).trim();
                    final Map< String, List< Long > > stageMap = oppTypeMap.get( oppType );
                    List< Long > durationList = stageMap.get( stage );
                    if (CollectionUtils.isNotEmpty( durationList )) {
                        durationList.add( duration );
                    } else {
                        durationList = new ArrayList<>();
                        durationList.add( duration );
                        stageMap.put( stage, durationList );
                    }
                } catch ( final Exception e ) {
                    LOGGER.error( "Problem in getting stage wise durations " + e.getMessage(), e );
                }
            }
        }
        return oppTypeMap;
    }

    /**
     * Gets the cta details.
     *
     * @param agentEmail
     *            the agent email
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @return the cta details
     */
    private Map< String, Map< String, Map< String, String > > > getCtaDetails( final String agentEmail,
            final Date fromDate, final Date toDate ) {
        final List< Object[] > ctaForOpportunityStage = agentReportService.getAgentsStagewiseCta( fromDate, toDate,
                agentEmail );
        final Map< String, Map< String, Map< String, String > > > resultMap = new HashMap<>();
        resultMap.put( RecordType.BUYER.getType(), new HashMap<>() );
        resultMap.put( RecordType.SELLER.getType(), new HashMap<>() );
        LOGGER.info( "Total cta rows count " + ctaForOpportunityStage.size() );
        if (isNotEmpty( ctaForOpportunityStage )) {
            ctaForOpportunityStage.forEach( entry -> {
                final Map< String, Map< String, String > > stageMap = resultMap
                        .get( StringUtils.convertObjectToString( entry[0] ).trim() );
                final Map< String, String > ctaMap = new HashMap<>();
                ctaMap.put( CALL_KEY, StringUtils.convertObjectToString( entry[2] ) );
                ctaMap.put( EMAIL_KEY, StringUtils.convertObjectToString( entry[3] ) );
                ctaMap.put( SMS_KEY, StringUtils.convertObjectToString( entry[4] ) );
                ctaMap.put( TOTAL_KEY, StringUtils.convertObjectToString( entry[5] ) );
                stageMap.put( StringUtils.convertObjectToString( entry[1] ).trim(), ctaMap );
                LOGGER.info( StringUtils.convertObjectToString( entry[0] ).trim() + " "
                        + StringUtils.convertObjectToString( entry[1] ).trim() + " "
                        + StringUtils.convertObjectToString( entry[2] ) + " "
                        + StringUtils.convertObjectToString( entry[3] ) + " " + StringUtils.convertObjectToString(
                                entry[4] + " " + StringUtils.convertObjectToString( entry[5] ) ) );
            } );
        }
        return resultMap;
    }
}
