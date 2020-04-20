package com.owners.gravitas.business.scheduler;

import static com.owners.gravitas.constants.Constants.LEAD;
import static com.owners.gravitas.enums.BuyerFarmType.LONG_TERM_BUYER;
import static com.owners.gravitas.enums.GravitasProcess.LEAD_MANAGEMENT_PROCESS;
import static com.owners.gravitas.enums.JobType.OUTBOUND_LEAD_CHECK_FARMING_JOB;
import static com.owners.gravitas.enums.LeadStatus.OUTBOUND_ATTEMPT;
import static java.lang.Boolean.FALSE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.owners.gravitas.annotation.ScheduledJobLog;
import com.owners.gravitas.business.BuyerFarmingBusinessService;
import com.owners.gravitas.business.ProcessBusinessService;
import com.owners.gravitas.config.BuyerFarmingConfig;
import com.owners.gravitas.config.SchedulerJobsJmxConfig;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.lock.AgentNewOppSchedulerLockHandler;
import com.owners.gravitas.service.ContactEntityService;

/**
 * The Class BuyerFarmingSchedular.
 *
 * @author harshads
 */
@Component
@ManagedResource( objectName = "com.owners.gravitas:name=BuyerFarmingSchedular" )
public class BuyerFarmingSchedular {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( BuyerFarmingSchedular.class );

    /** The Constant OUTBOUND_LEAD_CHECK_FARMING_LOCK_KEY. */
    private static final String OUTBOUND_LEAD_CHECK_FARMING_LOCK_KEY = "OUTBOUND_LEAD_CHECK_SCHEDULAR_RUN";

    /** The contact service V 1. */
    @Autowired
    private ContactEntityService contactServiceV1;

    /** The process management business service. */
    @Autowired
    private ProcessBusinessService processBusinessService;

    /** The buyer registration business service. */
    @Autowired
    private BuyerFarmingBusinessService buyerFarmingBusinessService;

    /** The buyer farming config. */
    @Autowired
    private BuyerFarmingConfig buyerFarmingConfig;

    /** The scheduler jobs jmx config. */
    @Autowired
    private SchedulerJobsJmxConfig schedulerJobsJmxConfig;

    /** The lock handler. */
    @Autowired
    private AgentNewOppSchedulerLockHandler lockHandler;

    /**
     * Check for outbound leads.
     */
    @Scheduled( cron = "${cron.job.buyer.farming.outbound.leads}", zone = "${cron.job.timezone}" )
    @ScheduledJobLog( jobType = OUTBOUND_LEAD_CHECK_FARMING_JOB )
    @ManagedOperation( description = "Test: Check for outbound lead" )
    public void checkForOutboundLeads() {
        if (schedulerJobsJmxConfig.isEnableAllJobs() && schedulerJobsJmxConfig.isEnableOutboundAttemptCheckSchedular()
                && buyerFarmingConfig.isBuyerFarmingEnabled()	
                && lockHandler.acquireLock( OUTBOUND_LEAD_CHECK_FARMING_LOCK_KEY )) {
            final List< Contact > leads = contactServiceV1.getOutboundAttemptContacts( OUTBOUND_ATTEMPT.getStatus(),
                    LEAD );
            for ( final Contact lead : leads ) {
                final DateTime createdDateTime = lead.getCreatedDate();
                if (null != createdDateTime && createdDateTime
                        .isBefore( DateTime.now().minusDays( buyerFarmingConfig.getLongTermLeadGracePeriod() ) )) {
                    final String email = lead.getEmail();
                    LOGGER.info( "lets start lead farming on outbound attempt lead:" + email );
                    final com.owners.gravitas.domain.entity.Process haltedProcess = processBusinessService
                            .getProcess( email, LEAD_MANAGEMENT_PROCESS, "active" );
                    if (haltedProcess != null) {
                        try {
                            final Map< String, Object > params = new HashMap<>();
                            params.put( "buyerFarmType", LONG_TERM_BUYER );
                            processBusinessService.deActivateAndSignal( email, LEAD_MANAGEMENT_PROCESS, params );
                            buyerFarmingBusinessService.updateFarmingStatus( lead.getCrmId(), LONG_TERM_BUYER, FALSE );
                        } catch ( final Exception e ) {
                            LOGGER.debug( "Unable to process lead farming for buyer " + email + " execution Id "
                                    + haltedProcess.getExecutionId(), e );
                        } finally {
                            lockHandler.releaseLock( OUTBOUND_LEAD_CHECK_FARMING_LOCK_KEY );
                        }
                    }
                }
            }
        }
    }
}
