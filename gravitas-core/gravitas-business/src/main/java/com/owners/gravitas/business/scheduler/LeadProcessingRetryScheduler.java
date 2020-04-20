package com.owners.gravitas.business.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hubzu.common.daemon.service.DaemonScheduler;
import com.owners.gravitas.business.handler.LeadProcessingRetryHandler;
import com.owners.gravitas.lock.SyncCacheLockHandler;

@Component
@EnableScheduling
public class LeadProcessingRetryScheduler extends DaemonScheduler
{
	private static final Logger LOGGER = LoggerFactory.getLogger(LeadProcessingRetryScheduler.class);
	
	private static final String LOCKNAME = "LEAD_PROCESSING_RETRY_SCHEDULER_LOCK";
	
	@Autowired
	private LeadProcessingRetryHandler leadProcessingRetryHandler;
	
	@Autowired
    private SyncCacheLockHandler syncCacheLock;
	
	@Scheduled(fixedDelayString = "${failed.lead.retry.interval.in.seconds}000")
    public void retryFailedLeads() 
	{
		try
		{
			if(syncCacheLock.acquireLockBlocking(LOCKNAME))
			{
				LOGGER.info("Triggering failed leads processing handler");
				leadProcessingRetryHandler.retryFailedLeads();
			}
		}
		catch(Exception e)
		{
			LOGGER.error("Got exception in triggering the retry failed leads : ",e);
		}
		finally
		{
			syncCacheLock.releaseLockWithDelay(LOCKNAME);
		}
	}

}