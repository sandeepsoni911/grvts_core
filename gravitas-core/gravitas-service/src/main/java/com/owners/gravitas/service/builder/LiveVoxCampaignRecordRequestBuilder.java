package com.owners.gravitas.service.builder;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.dto.LiveVoxRecord;
import com.owners.gravitas.dto.request.LiveVoxCampaignRecordRequest;
import com.owners.gravitas.service.impl.AccountServiceImpl;
import com.owners.gravitas.util.StringUtils;

@Service
public class LiveVoxCampaignRecordRequestBuilder extends AbstractBuilder< LeadSource, LiveVoxCampaignRecordRequest > {

	 /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( LiveVoxCampaignRecordRequestBuilder.class );
    
    @Value( "${salesforce.lead.url}" )
    private String salesforceURL;
    
    @Value( "${livevox.practice.phone.alternate:7708022356}" )
    private String practicePhoneAlternate;
    
    @Override
    public LiveVoxCampaignRecordRequest convertTo( LeadSource source, LiveVoxCampaignRecordRequest destination ) {
        LiveVoxCampaignRecordRequest liveVoxCampaignRecordRequest = destination;
        if (source != null) {
            if (liveVoxCampaignRecordRequest == null) {
                liveVoxCampaignRecordRequest = new LiveVoxCampaignRecordRequest();
            }
            LiveVoxRecord liveVoxRecord = new LiveVoxRecord();
            liveVoxRecord.setFirstName( getFormatedName(source.getFirstName()) );
            liveVoxRecord.setLastName( getFormatedName(source.getLastName()) );
            
            liveVoxRecord.setPhone1( StringUtils.formatPhoneNumber(source.getPhone()) );
            liveVoxRecord.setExtra13( salesforceURL + source.getId() );
            liveVoxRecord.setAccount( source.getId() );
            liveVoxRecord.setPracticePhoneAlternate( practicePhoneAlternate );
            List< LiveVoxRecord > records = new ArrayList<>();
            records.add( liveVoxRecord );
            liveVoxCampaignRecordRequest.setRecords( records );
        }
        return liveVoxCampaignRecordRequest;
    }

    @Override
    public LeadSource convertFrom( LiveVoxCampaignRecordRequest source, LeadSource destination ) {
        throw new UnsupportedOperationException();
    }

    
    /**
     * This we have done as a part of Bug Resolution
     * https://jira.hubzu.com/browse/GRACOR-1160
     * @param name
     * @return
     */
    private String getFormatedName(String name) {
    	LOGGER.info("LiveVox name string before formating :{}",name);
    	if(!org.apache.commons.lang3.StringUtils.isEmpty(name)) {
    		name = org.apache.commons.lang3.StringUtils.stripAccents(name);
    		name = name.replaceAll("[^a-zA-Z0-9 ]", "" );
    	}
    	LOGGER.info("LiveVox name string after formating :{}",name);
        return name;
    }
}
