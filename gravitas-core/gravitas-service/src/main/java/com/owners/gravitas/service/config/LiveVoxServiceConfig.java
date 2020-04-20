package com.owners.gravitas.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.owners.gravitas.config.LiveVoxConfiguration;
import com.owners.gravitas.config.LiveVoxMatchingCampaignConfiguration;

@Configuration
public class LiveVoxServiceConfig {

    @Bean( name = "liveVoxConfig" )
    public LiveVoxConfiguration liveVoxConfig( @Value( "${livevox.clientname}" ) final String clientName,
            @Value( "${livevox.username}" ) final String userName,
            @Value( "${livevox.password}" ) final String password,
            @Value( "${livevox.accesstoken}" ) final String accessToken ) {
        return new LiveVoxConfiguration( clientName, userName, password, accessToken );
    }

    @Bean( name = "liveVoxMatchingCampaignConfig" )
    public LiveVoxMatchingCampaignConfiguration liveVoxMatchingCampaignConfig(
            @Value( "${livevox.serviceid}" ) final Integer serviceId,
            @Value( "${livevox.clientid}" ) final Integer clientId ) {
        return new LiveVoxMatchingCampaignConfiguration( serviceId, clientId );
    }
}
