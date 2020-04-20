package com.owners.gravitas.service.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.owners.gravitas.config.LiveVoxMatchingCampaignConfiguration;
import com.owners.gravitas.dto.LiveVoxMatchingCampaignDateRange;
import com.owners.gravitas.dto.LiveVoxServiceId;
import com.owners.gravitas.dto.LiveVoxServiceIdList;
import com.owners.gravitas.dto.request.LiveVoxMatchingCampaignRequest;
import com.owners.gravitas.enums.LiveVoxCampaignStatus;
import com.owners.gravitas.util.DateUtil;

@Service( "liveVoxMatchingCampaignRequestBuilder" )
public class LiveVoxMatchingCampaignRequestBuilder {

    @Autowired
    @Qualifier( value = "liveVoxMatchingCampaignConfig" )
    private LiveVoxMatchingCampaignConfiguration liveVoxMatchingCampaignConfig;

    public LiveVoxMatchingCampaignRequest createLiveVoxMatchingCampaignRequest() {
        LiveVoxMatchingCampaignRequest liveVoxMatchingCampaignRequest = new LiveVoxMatchingCampaignRequest();
        LiveVoxMatchingCampaignDateRange liveVoxMatchingCampaignDateRange = new LiveVoxMatchingCampaignDateRange();
        liveVoxMatchingCampaignDateRange.setFrom( DateUtil.getLiveVoxStartOfDay() );
        liveVoxMatchingCampaignDateRange.setTo( DateUtil.getLiveVoxEndOfDay() );
        LiveVoxServiceId liveVoxServiceId = new LiveVoxServiceId();
        LiveVoxServiceIdList liveVoxServiceIdList = new LiveVoxServiceIdList();
        List< LiveVoxCampaignStatus > state = new ArrayList<>();
        state.add( LiveVoxCampaignStatus.ACTIVE );
        state.add( LiveVoxCampaignStatus.PLAYING );
        liveVoxServiceIdList.setId( liveVoxMatchingCampaignConfig.getServiceId() );
        List< LiveVoxServiceIdList > liveVoxServiceId1 = new ArrayList<>();
        liveVoxServiceId1.add( liveVoxServiceIdList );
        liveVoxServiceId.setService( liveVoxServiceId1 );
        liveVoxMatchingCampaignRequest.setService( liveVoxServiceId );
        liveVoxMatchingCampaignRequest.setState( state );
        liveVoxMatchingCampaignRequest.setDateRange( liveVoxMatchingCampaignDateRange );
        return liveVoxMatchingCampaignRequest;
    }
}
