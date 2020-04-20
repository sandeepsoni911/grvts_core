package com.owners.gravitas.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.ResourceAccessException;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.dto.request.LiveVoxCampaignRecordRequest;
import com.owners.gravitas.dto.request.LiveVoxMatchingCampaignRequest;
import com.owners.gravitas.dto.request.LiveVoxSessionRequest;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.LiveVoxMatchingCampaignResponse;
import com.owners.gravitas.dto.response.LiveVoxSessionResponse;

public interface LiveVoxService {

    public String getSessionId();

    public LiveVoxSessionResponse createSession( LiveVoxSessionRequest liveVoxSessionRequest );

    public boolean isSessionValid( final String sessionId );

    @Retryable( maxAttempts = 3, backoff = @Backoff( delay = 1000 ), include = { ResourceAccessException.class } )
    public BaseResponse appendCampaignRecord( final int campaignId,
            LiveVoxCampaignRecordRequest liveVoxCampaignRecordRequest );

    public LiveVoxMatchingCampaignResponse findMatchingCampaigns(
            LiveVoxMatchingCampaignRequest liveVoxMatchingCampaignRequest );

    public Integer getMatchingCampaignId();

    boolean isLiveVoxEnabledState(String state);

    void handleLiveVoxSubmittedLeadAttributes(LeadSource leadSource, Contact contact, ObjectType objectType);

    boolean isLivevoxLeadSubmitted(LeadSource leadSource, Contact contact);

    void submitLeadToLiveVox(LeadSource leadSource);

    void checkAndSubmitToLiveVox(LeadSource leadSource);

    public boolean isLeadSubmittedToLiveVox(LeadSource leadSource);
}
