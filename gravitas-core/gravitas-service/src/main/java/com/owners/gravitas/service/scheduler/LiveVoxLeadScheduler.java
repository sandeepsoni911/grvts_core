package com.owners.gravitas.service.scheduler;

import static com.owners.gravitas.constants.Constants.LEAD;
import static com.owners.gravitas.enums.JobType.LIVEVOX_LEAD_SUBMIT_JOB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hubzu.dsync.service.DistributedSynchronizationService;
import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.annotation.ScheduledJobLog;
import com.owners.gravitas.config.LiveVoxJmxConfig;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.dto.request.LiveVoxCampaignRecordRequest;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.enums.ProspectAttributeType;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.LiveVoxService;
import com.owners.gravitas.service.ObjectTypeService;
import com.owners.gravitas.service.builder.LiveVoxMatchingCampaignRequestBuilder;
import com.owners.gravitas.service.cache.LiveVoxLookupService;
import com.owners.gravitas.service.util.LiveVoxUtil;
import com.owners.gravitas.util.JsonUtil;;


@Component
@EnableScheduling
public class LiveVoxLeadScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger( LiveVoxLeadScheduler.class );

    @Autowired
    private LiveVoxLookupService liveVoxLookupService;

    @Autowired
    private DistributedSynchronizationService databaseDistributedSynchronizationService;

    @Autowired
    private LiveVoxService liveVoxService;

    @Autowired
    @Qualifier( value = "liveVoxMatchingCampaignRequestBuilder" )
    private LiveVoxMatchingCampaignRequestBuilder liveVoxMatchingCampaignRequestBuilder;

    /** The contact service V 1. */
    @Autowired
    private ContactEntityService contactServiceV1;

    @Autowired
    private LiveVoxUtil liveVoxUtil;

    /** The object type service. */
    @Autowired
    private ObjectTypeService objectTypeService;

    private final String LIVEVOX_LEAD_SCHEDULER_LOCK_PREFIX = "LIVEVOX_LEAD_SCHEDULER_LOCK";

    @Value( "${gravitas.bulk_send_lead.batch_size:100}" )
    private int batchSize;

    @Autowired
    private LiveVoxJmxConfig liveVoxJmxConfig;

    @Scheduled( initialDelay = 18000, fixedDelayString = "${gravitas_livevox_lead_scheduler_delay}" )
    @ScheduledJobLog( jobType = LIVEVOX_LEAD_SUBMIT_JOB )
    public void processLeads() {
        long t1 = System.currentTimeMillis();
        if (!liveVoxUtil.isLiveVoxAfterOfficeHours() && liveVoxJmxConfig.isLiveVoxServiceEnabled()
        		&& liveVoxJmxConfig.isLiveVoxCacheEnabled()) {
            if (databaseDistributedSynchronizationService.acquireLock( LIVEVOX_LEAD_SCHEDULER_LOCK_PREFIX )) {
                try {
                    Long queueSize = liveVoxLookupService.getQueueSize();
                    if (queueSize == 0) {
                        return;
                    }
                    LOGGER.error( "starting  processLeads -currentTime  : {} ", t1 );
                    Integer campaignId = liveVoxService.getMatchingCampaignId();
                    if (campaignId == null) {
                        LOGGER.error( "processLeads : Error no active campaign/more than one active campaign found" );
                        return;
                    }

                    int count = queueSize.intValue();
                    final ObjectType objectType = objectTypeService.findByName( LEAD.toLowerCase() );
                    final String key = liveVoxUtil.getLiveVoxFailureQueueKey();
                    for ( int i = 0; i < count; i++ ) {
                        LiveVoxCampaignRecordRequest liveVoxCampaignRecordRequest = ( LiveVoxCampaignRecordRequest ) liveVoxLookupService
                                .getCampaignRecord();
                        if (liveVoxCampaignRecordRequest != null) {
                            boolean isSuccess = true;
                            try {
								String leadId = liveVoxCampaignRecordRequest.getRecords().get(0).getAccount();
								LOGGER.info("Scheduler: fetching crmId : {} ", leadId);
								Contact contact = contactServiceV1.getContactByCrmId(leadId);
								if (contact != null) {
									LOGGER.debug("Scheduler: appending campaign records to livevox for the crmId : {} ",
											leadId);
									final BaseResponse response = liveVoxService.appendCampaignRecord(campaignId,
											liveVoxCampaignRecordRequest);
									LOGGER.info( "Lead with id :{} LiveVox submission status",leadId, response.getStatus());
									if (response != null && Status.SUCCESS.equals(response.getStatus())) {
										LOGGER.info( "Lead with id :{} successfully submitted to LIve-Vox from scheduler", leadId);
										LeadSource leadSource = new LeadSource();
										leadSource.setId( leadId );
										liveVoxService.handleLiveVoxSubmittedLeadAttributes( leadSource, contact, objectType );
									} else {
										LOGGER.debug(
												"Scheduler: putting campaign record in failure queue for the crmId : {} ",
												leadId);
										liveVoxLookupService.putCampaignRecordsInFailureQueue(key,
												liveVoxCampaignRecordRequest);
										liveVoxUtil.addLiveVoxContactAttribute(contact.getContactAttributes(),
												ProspectAttributeType.SUBMITTED_TO_LIVEVOX.getKey(),
												String.valueOf(false), objectType);
									}
								}
                            } catch ( Exception e ) {
                                LOGGER.error( "Scheduler: Error Occured while submitting lead to livevox request : {} ",
                                        JsonUtil.toJson( liveVoxCampaignRecordRequest ), e );
                                isSuccess = false;
                            } finally {
                                if (!isSuccess) {
                                    liveVoxLookupService.putCampaignRecordsInFailureQueue( key,
                                            liveVoxCampaignRecordRequest );
                                }
                            }
                        }
                    }
                } catch ( Exception e ) {
                    LOGGER.error( "Error in processing leads of LiveVoxLeadScheduler ", e );
                } finally {
                    databaseDistributedSynchronizationService.releaseLock( LIVEVOX_LEAD_SCHEDULER_LOCK_PREFIX );
                }
            }
            LOGGER.error( "Completed process leads - Time Consumed : {} ", ( System.currentTimeMillis() - t1 ) );
        }
    }

}
