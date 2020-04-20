package com.owners.gravitas.service.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hubzu.common.cache.service.CacheService;
import com.hubzu.common.logger.HubzuLog;
import com.owners.gravitas.dto.request.LiveVoxCampaignRecordRequest;
import com.owners.gravitas.enums.ErrorCode;
import com.owners.gravitas.exception.ApplicationException;
import static com.owners.gravitas.constants.Constants.HYPHEN;

@Service( "liveVoxLookupService" )
public class LiveVoxLookupService {

    private static final HubzuLog LOGGER = HubzuLog.getLogger( LiveVoxLookupService.class );
    private static final String LIVEVOX_SESSION_DATA_CACHE = "livevoxsessiondatacache";
    private static final String LIVEVOX_MATCHING_CAMPAIGN_DATA_CACHE = "livevoxmatchingcampaigndatacache";
    private static final String LIVEVOX_CAMPAIGN_RECORD_DATA_CACHE = "livevoxcampaignrecorddatacache";
    private static final String LIVEVOX_FAILURE_CAMPAIGN_RECORD_DATA_CACHE = "livevoxfailurecampaignrecorddatacache";

    @Autowired
    private CacheService gravitasCacheService;

    public void putSessionInfo( String key ) {
        if (isValid( key )) {
            LOGGER.info( "Put live vox session info data in Cache key : {}", key );
            try {
                gravitasCacheService.putString( LIVEVOX_SESSION_DATA_CACHE, key );
            } catch ( Exception ex ) {
                LOGGER.error( "Exception while putting data into cache, key:{} exception : ",
                        LIVEVOX_SESSION_DATA_CACHE, key, ex );
            }
        }
    }

    public String getSessionInfo() {
        LOGGER.info( "getting session info data from Cache key : {}", LIVEVOX_SESSION_DATA_CACHE );
        try {
            return gravitasCacheService.getString( LIVEVOX_SESSION_DATA_CACHE );
        } catch ( Exception ex ) {
            LOGGER.error( "Exception while getting data from cache, key:{}", LIVEVOX_SESSION_DATA_CACHE, ex );
        }
        return null;
    }
    
    public void enterValueIntoRedisCache(String champignId, String sessionId) {
        LOGGER.info( "getting session info data from Cache key : {}", LIVEVOX_SESSION_DATA_CACHE );
        try {
             gravitasCacheService.put(LIVEVOX_SESSION_DATA_CACHE, sessionId);
             gravitasCacheService.put(LIVEVOX_MATCHING_CAMPAIGN_DATA_CACHE, champignId);

        } catch ( Exception ex ) {
            LOGGER.error( "Exception while getting data from cache, key:{}", LIVEVOX_SESSION_DATA_CACHE, ex );
        }
    }
    
    
    public void removeSessionInfo() {
        LOGGER.info( "Delete livevox session data in Cache -for key : {}", LIVEVOX_SESSION_DATA_CACHE );
        try {
            gravitasCacheService.delete( LIVEVOX_SESSION_DATA_CACHE );
        } catch ( Exception ex ) {
            LOGGER.error( "Exception while deleting data from cache, key:{}", LIVEVOX_SESSION_DATA_CACHE, ex );
        }

    }

    public void putMatchingCampaignIdInfo( String key, long ttl ) {
        if (isValid( key, ttl )) {
            LOGGER.info( "Put matching campaign info data in Cache -for key {},  ttl : {}", key, ttl );
            try {
                gravitasCacheService.putString( LIVEVOX_MATCHING_CAMPAIGN_DATA_CACHE, key, ttl );
            } catch ( Exception ex ) {
                LOGGER.error( "Exception while putting data into cache, key:{} ttl : {}", key, ttl, ex );
            }
        }
    }

    public String getMatchingCampaignIdInfo() {
        LOGGER.info( "getting matching campaign info data from Cache key : {}", LIVEVOX_MATCHING_CAMPAIGN_DATA_CACHE );
        try {
            return gravitasCacheService.getString( LIVEVOX_MATCHING_CAMPAIGN_DATA_CACHE );
        } catch ( Exception ex ) {
            LOGGER.error( "Exception while getting data from cache, key:{}", LIVEVOX_MATCHING_CAMPAIGN_DATA_CACHE, ex );
        }
        return null;
    }

    public void putCampaignRecordsInQueue( LiveVoxCampaignRecordRequest liveVoxCampaignRecordRequest ) {
        if (liveVoxCampaignRecordRequest != null) {
            try {
                gravitasCacheService.rightPush( LIVEVOX_CAMPAIGN_RECORD_DATA_CACHE, liveVoxCampaignRecordRequest );
            } catch ( Exception ex ) {
                LOGGER.error( "Exception while putting data into cache, key:{}", LIVEVOX_CAMPAIGN_RECORD_DATA_CACHE,
                        ex );
            }
        }

    }

    public List< ? extends Object > getCampaignRecordsInfo( int start, int end ) {
        LOGGER.info( "Getting campaign records data in Cache -for key : {} start : {} and end : {}",
                LIVEVOX_CAMPAIGN_RECORD_DATA_CACHE, start, end );
        try {
            return gravitasCacheService.range( LIVEVOX_CAMPAIGN_RECORD_DATA_CACHE, start, end );
        } catch ( Exception ex ) {
            LOGGER.error( "Exception while getting records data into cache, key:{}", LIVEVOX_CAMPAIGN_RECORD_DATA_CACHE,
                    ex );
        }

        return null;
    }

    public Long getQueueSize() {
        LOGGER.info( "Getting queue size for key : {}", LIVEVOX_CAMPAIGN_RECORD_DATA_CACHE );
        return gravitasCacheService.getQueueSize( LIVEVOX_CAMPAIGN_RECORD_DATA_CACHE );
    }

    public void trim( int start, int end ) {
        LOGGER.info( "Trimming campaign records data in Cache -for key : {} start : {} and end : {}",
                LIVEVOX_CAMPAIGN_RECORD_DATA_CACHE, start, end );
        try {
            gravitasCacheService.trim( LIVEVOX_CAMPAIGN_RECORD_DATA_CACHE, start, end );
        } catch ( Exception ex ) {
            LOGGER.error( "Exception while deleting records data into cache, key:{}",
                    LIVEVOX_CAMPAIGN_RECORD_DATA_CACHE, ex );
        }
    }

    public void putCampaignRecordsInFailureQueue( String key,
            LiveVoxCampaignRecordRequest liveVoxCampaignRecordRequest ) {
        if (isValid( key, liveVoxCampaignRecordRequest )) {
            try {
                gravitasCacheService.rightPush( key + HYPHEN + LIVEVOX_FAILURE_CAMPAIGN_RECORD_DATA_CACHE,
                        liveVoxCampaignRecordRequest );
            } catch ( Exception ex ) {
                LOGGER.error( "Exception while putting data into cache, key:{}",
                        LIVEVOX_FAILURE_CAMPAIGN_RECORD_DATA_CACHE, ex );
            }
        }

    }

    public Object getCampaignRecord() {
        try {
            return gravitasCacheService.leftPop( LIVEVOX_CAMPAIGN_RECORD_DATA_CACHE );
        } catch ( Exception ex ) {
            LOGGER.error( "Exception while getting record data from cache, key:{}", LIVEVOX_CAMPAIGN_RECORD_DATA_CACHE,
                    ex );
        }
        return null;
    }

    protected boolean isValid( String confiKey ) {
        if (!StringUtils.hasText( confiKey )) {
            throw new ApplicationException( "sessionId cant be empty", ErrorCode.INVALID_PARAMETER );
        }
        return true;
    }

    protected boolean isValid( String configKey, Object configValue ) {
        if (!StringUtils.hasText( configKey ) || ( configValue == null )) {
            throw new ApplicationException( "configKey or configValue data cant be empty",
                    ErrorCode.INVALID_PARAMETER );
        }
        return true;
    }
}
