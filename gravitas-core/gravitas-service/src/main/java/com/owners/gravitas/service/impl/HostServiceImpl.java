package com.owners.gravitas.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.owners.gravitas.service.HostService;

/**
 * The Class HostServiceImpl.
 *
 * @author ankusht
 */
@Service
public class HostServiceImpl implements HostService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( HostServiceImpl.class );

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.HostService#getHost()
     */
    @Override
    public String getHost() {
        String hostName = StringUtils.EMPTY;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch ( UnknownHostException e ) {
            LOGGER.error( "Could not resolve the host", e );
        }
        return hostName;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.HostService#getFullHost()
     */
    @Override
    public String getFullHost() {
        String hostName = StringUtils.EMPTY;
        try {
            hostName = InetAddress.getLocalHost().toString();
        } catch ( Exception e ) {
            LOGGER.error( "Could not resolve the host", e );
        }
        return hostName;
    }

}
