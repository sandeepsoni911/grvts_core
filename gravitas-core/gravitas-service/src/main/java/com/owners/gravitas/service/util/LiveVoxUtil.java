package com.owners.gravitas.service.util;

import java.util.Set;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.owners.gravitas.config.LiveVoxJmxConfig;
import com.owners.gravitas.domain.entity.ContactAttribute;
import com.owners.gravitas.domain.entity.ObjectAttributeConfig;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.service.ObjectAttributeConfigService;
import static com.owners.gravitas.constants.Constants.HYPHEN;

@Service
public class LiveVoxUtil {

    @Autowired
    private LiveVoxJmxConfig liveVoxJmxConfig;

    /** The object attribute config service. */
    @Autowired
    private ObjectAttributeConfigService objectAttributeConfigService;

    private static final Logger LOGGER = LoggerFactory.getLogger( LiveVoxUtil.class );

    public long getMatchingCampaignIdExpiryTime() {
        final DateTimeZone timeZone = DateTimeZone.forID( liveVoxJmxConfig.getTimeZoneId() );
        final LocalDateTime ofcAfterHourStart = getLocalDateTime( timeZone, liveVoxJmxConfig.getOfficeAfterHourStart(),
                liveVoxJmxConfig.getOfficeAfterMinsStart() );
        int now = new LocalDateTime( timeZone ).getMillisOfDay();
        int ofcAfterHourStartMillis = ofcAfterHourStart.getMillisOfDay();
        long expiry = ( long ) ( ofcAfterHourStartMillis - now ) / 1000;
        return expiry;
    }

    private LocalDateTime getLocalDateTime( final DateTimeZone timeZone, final int hours, final int mins ) {
        return new LocalDateTime( timeZone ).withHourOfDay( hours ).withMinuteOfHour( mins ).withSecondOfMinute( 0 );
    }

    public void addLiveVoxContactAttribute( final Set< ContactAttribute > attributes, final String key,
            final String value, final ObjectType objectType ) {
        boolean flag = false;
        String currentValue = value;
        if (currentValue != null) {
            currentValue = com.owners.gravitas.util.StringUtils.subStringForLength( currentValue, 1000 );
            final ObjectAttributeConfig config = objectAttributeConfigService.getObjectAttributeConfig( key,
                    objectType );
            for ( final ContactAttribute contactAttribute : attributes ) {
                if (contactAttribute.getObjectAttributeConfig().equals( config )) {
                    contactAttribute.setValue( currentValue );
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                final ContactAttribute contactAttribute = new ContactAttribute();
                contactAttribute.setValue( currentValue );
                contactAttribute.setObjectAttributeConfig( config );
                attributes.add( contactAttribute );
            }
        }
    }

    public boolean isLiveVoxAfterOfficeHours() {
        final DateTimeZone timeZone = DateTimeZone.forID( liveVoxJmxConfig.getTimeZoneId() );
        final LocalDateTime ofcAfterHourStart = getLocalDateTime( timeZone, liveVoxJmxConfig.getOfficeAfterHourStart(),
                liveVoxJmxConfig.getOfficeAfterMinsStart() );
        LocalDateTime ofcAfterHourEnd = getLocalDateTime( timeZone, liveVoxJmxConfig.getOfficeAfterHourEnd(),
                liveVoxJmxConfig.getOfficeAfterMinsEnd() );

        LocalDateTime now = new LocalDateTime( timeZone );

        if (ofcAfterHourStart.isAfter( ofcAfterHourEnd )) {
            ofcAfterHourEnd = ofcAfterHourEnd.plusDays( Integer.valueOf( 1 ) );
            if (ofcAfterHourStart.isAfter( now )) {
                now = now.plusDays( Integer.valueOf( 1 ) );
            }
        }
        LOGGER.debug( "LiveVox Afterhour start time: " + ofcAfterHourStart + " " + "LiveVox Afterhour end time: "
                + ofcAfterHourEnd + "For time zone: " + timeZone.getID() );
        return now.isAfter( ofcAfterHourStart ) && now.isBefore( ofcAfterHourEnd );
    }

    public String getLiveVoxFailureQueueKey() {
        final DateTimeZone timeZone = DateTimeZone.forID( liveVoxJmxConfig.getTimeZoneId() );
        final LocalDateTime now = new LocalDateTime( timeZone );
        int day = now.getDayOfMonth();
        int month = now.getMonthOfYear();
        int year = now.getYear();
        return year + HYPHEN + month + HYPHEN + day;
    }
}
