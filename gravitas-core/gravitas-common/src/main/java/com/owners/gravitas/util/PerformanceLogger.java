package com.owners.gravitas.util;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;

/**
 * The Class PerformanceLogger.
 */
public class PerformanceLogger {

    /** The sw. */
    private StopWatch sw = new StopWatch();

    /**
     * Creates the and start.
     *
     * @return the performace logger
     */
    public static PerformanceLogger createAndStart() {
        PerformanceLogger logger = new PerformanceLogger();
        logger.start();
        return logger;
    }

    /**
     * Millis to short dhms.
     *
     * @param duration
     *            the duration
     * @return the string
     */
    public String millisToShortDHMS( final long duration ) {
        String res = StringUtils.EMPTY;
        long days = TimeUnit.MILLISECONDS.toDays( duration );
        long hours = TimeUnit.MILLISECONDS.toHours( duration )
                - TimeUnit.DAYS.toHours( TimeUnit.MILLISECONDS.toDays( duration ) );
        long minutes = TimeUnit.MILLISECONDS.toMinutes( duration )
                - TimeUnit.HOURS.toMinutes( TimeUnit.MILLISECONDS.toHours( duration ) );
        long seconds = TimeUnit.MILLISECONDS.toSeconds( duration )
                - TimeUnit.MINUTES.toSeconds( TimeUnit.MILLISECONDS.toMinutes( duration ) );
        long millis = TimeUnit.MILLISECONDS.toMillis( duration )
                - TimeUnit.SECONDS.toMillis( TimeUnit.MILLISECONDS.toSeconds( duration ) );

        if (days == 0)
            res = String.format( "%02d:%02d:%02d.%04d", hours, minutes, seconds, millis );
        else
            res = String.format( "%dd %02d:%02d:%02d.%04d", days, hours, minutes, seconds, millis );
        return res;
    }

    /**
     * Start.
     */
    public void start() {
        sw.start();
    }

    /**
     * Stop.
     *
     * @return the string
     */
    public String stop() {
        sw.stop();
        return millisToShortDHMS( sw.getTime() );
    }

    /**
     * Gets the time.
     *
     * @return the time
     */
    public long getTime() {
        return sw.getTime();
    }

}
