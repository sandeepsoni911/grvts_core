package com.owners.gravitas.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * The Class CorsFilter allows the preflight request to be verified directly.
 */
public class CorsFilter extends OncePerRequestFilter {

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(
     * javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
     */
    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain )
            throws ServletException, IOException {
        response.setHeader( "Access-Control-Allow-Origin", "*" );
        response.setHeader( "Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS" );
        response.setHeader( "Access-Control-Max-Age", "3600" );
        response.setHeader( "Access-Control-Allow-Headers", "authorization, content-type, xsrf-token" );
        response.addHeader( "Access-Control-Expose-Headers", "xsrf-token" );
        if (CorsUtils.isPreFlightRequest( request )) {
            response.setStatus( HttpServletResponse.SC_OK );
        } else {
            filterChain.doFilter( request, response );
        }
    }

}
