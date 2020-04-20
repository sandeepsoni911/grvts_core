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
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.owners.gravitas.annotation.ScheduledJobLog;
import com.owners.gravitas.business.BuyerFarmingBusinessService;
import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.business.ProcessBusinessService;
import com.owners.gravitas.config.BuyerFarmingConfig;
import com.owners.gravitas.config.SchedulerJobsJmxConfig;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.dto.request.SyncUpRequest;
import com.owners.gravitas.enums.LeadSyncUpAttributes;
import com.owners.gravitas.lock.AgentNewOppSchedulerLockHandler;
import com.owners.gravitas.service.ContactEntityService;

/**
 * The Class BuyerFarmingSchedular.
 *
 * @author kushwaja
 */
@Component
@ManagedResource( objectName = "com.owners.gravitas:name=LeadSyncUpSchedular" )
public class LeadSyncUpSchedular {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( LeadSyncUpSchedular.class );

    /** instance of {@link LeadBusinessService}. */
	@Autowired
	private LeadBusinessService buyerBusinessService;

    /**
	 * Execute agent performance analytics job.
	 */
	@Scheduled(cron = "${cron.job.lead.syncUp}", zone = "${cron.job.timezone}")
	@ManagedOperation
	public void executeAgentPerformanceAnalyticsJob() {
		SyncUpRequest syncUpRequest = new SyncUpRequest(LeadSyncUpAttributes.UUID.getAttribute());
		LOGGER.debug("Sync up activity has been started for column:{}", syncUpRequest.getAttribute());
		buyerBusinessService.syncUpLead(syncUpRequest, true);
		
	}
}
