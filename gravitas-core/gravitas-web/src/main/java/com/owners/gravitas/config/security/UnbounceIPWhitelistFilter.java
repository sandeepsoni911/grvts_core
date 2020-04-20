package com.owners.gravitas.config.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.GenericFilterBean;

import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class UnbounceIPWhitelistFilter.
 *
 * @author vishwanathm
 */
@ManagedResource( objectName = "com.owners.gravitas:name=UnbounceIPWhitelistFilter" )
public class UnbounceIPWhitelistFilter extends GenericFilterBean {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( UnbounceIPWhitelistFilter.class );

    /** The Constant DEFAULT_IP. */
    private static final String DEFAULT_IP = "0.0.0.0";

    /** The enable json logging. */
    @Value( "${unbounce.whitelisted.ip:0.0.0.0}" )
    private String whitelistedIPs;

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /** The whitelisted ips. */
    private List< String > whitelistedIPList = new ArrayList<>();

    /*
     * (non-Javadoc)
     * @see org.springframework.web.filter.GenericFilterBean#initFilterBean()
     */
    @Override
    protected void initFilterBean() throws ServletException {
        whitelistedIPList = Arrays.asList( whitelistedIPs.split( Constants.COMMA_AND_SPACE ) );
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )
            throws IOException, ServletException {

        HttpServletRequest httpRequest = ( HttpServletRequest ) request;
        String ipAddress = httpRequest.getHeader( "X-Forwarded-For" );
        if (ipAddress == null) {
            LOGGER.debug( "Header attribute X-Forwarded-For is null" );
            ipAddress = httpRequest.getRemoteAddr();
        }

        if (!isIpWhitelisted( ipAddress )) {
            LOGGER.error( "Invalid access to unbounce API from IP " + ipAddress );
            throw new AccessDeniedException( "Invalid access to unbounce API from IP " + ipAddress );
        }
        chain.doFilter( request, response );
    }

    /**
     * Checks if is ip whitelisted.
     *
     * @param ipAddress
     *            the ip address
     * @return true, if ip is whitelisted
     */
    private boolean isIpWhitelisted( String ipAddress ) {
        if (whitelistedIPList.contains( ipAddress ) || DEFAULT_IP.equals( whitelistedIPs )) {
            return true;
        }
        return false;
    }

    /**
     * Gets the whitelisted i ps.
     *
     * @return the whitelisted i ps
     */
    @ManagedAttribute
    public String getWhitelistedIPs() {
        return whitelistedIPs;
    }

    /**
     * Sets the whitelisted i ps.
     *
     * @param whitelistedIPs
     *            the new whitelisted i ps
     */
    @ManagedAttribute
    public void setWhitelistedIPs( String whitelistedIPs ) {
        this.whitelistedIPs = whitelistedIPs;
        propertyWriter.saveJmxProperty( "unbounce.whitelisted.ip", whitelistedIPs );
        whitelistedIPList = Arrays.asList( whitelistedIPs.split( Constants.COMMA_AND_SPACE ) );
    }
}
