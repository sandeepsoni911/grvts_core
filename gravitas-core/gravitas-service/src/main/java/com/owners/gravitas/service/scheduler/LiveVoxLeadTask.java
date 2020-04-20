package com.owners.gravitas.service.scheduler;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.owners.gravitas.dto.request.LiveVoxCampaignRecordRequest;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.service.LiveVoxService;
import com.owners.gravitas.service.util.ApplicationContextProvider;

public class LiveVoxLeadTask implements Callable< BaseResponse > {

    private static final Logger LOGGER = LoggerFactory.getLogger( LiveVoxLeadTask.class );

    private LiveVoxCampaignRecordRequest liveVoxCampaignRecordRequest;

    private int campaignId;

    public LiveVoxLeadTask( int campaignId, LiveVoxCampaignRecordRequest liveVoxCampaignRecordRequest ) {
        super();
        this.campaignId = campaignId;
        this.liveVoxCampaignRecordRequest = liveVoxCampaignRecordRequest;
    }

    @Override
    public BaseResponse call() throws Exception {
        BaseResponse response = null;
        final String leadId = liveVoxCampaignRecordRequest.getRecords().get( 0 ).getAccount();
        try {
            LOGGER.debug( "Scheduler Appending campaign records to livevox for the crmId : {} ", leadId );
            LiveVoxService liveVoxService = ApplicationContextProvider.getApplicationContext()
                    .getBean( "liveVoxService", LiveVoxService.class );
            response = liveVoxService.appendCampaignRecord( campaignId, liveVoxCampaignRecordRequest );
            return response;
        } catch ( Exception e ) {
            LOGGER.error( "error while executing livevox lead submit task - for leadId : {}", leadId, e );
        }
        return response;
    }

}
