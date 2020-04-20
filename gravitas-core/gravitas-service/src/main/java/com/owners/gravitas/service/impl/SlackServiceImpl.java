package com.owners.gravitas.service.impl;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.owners.gravitas.dto.request.SlackRequest;
import com.owners.gravitas.error.log.ErrorLogExcelRequestUtil;
import com.owners.gravitas.service.HostService;
import com.owners.gravitas.service.SlackService;

import okhttp3.Response;

/**
 * The Class SlackServiceImpl.
 */
@Service( "slackService" )
public class SlackServiceImpl implements SlackService {

    /** restTemplate for making rest call to owners. */
    @Autowired
    private RestTemplate restTemplate;

    /** The host service. */
    @Autowired
    private HostService hostService;

    /** The token. */
    @Value( "${error.excel.log.token.value}" )
    private String token;

    /** The channel name. */
    @Value( "${error.excel.log.channel.name}" )
    private String channelName;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( SlackServiceImpl.class );

    /**
     * Publish listing to slack.
     *
     * @param request
     *            the request
     * @param url
     *            the url
     */
    @Override
    public void publishToSlack( final SlackRequest request, final String url ) {
        restTemplate.postForObject( url, request, String.class );
    }

    /**
     * Post excel to slack.
     *
     * @param request
     *            the request
     */
    /*
     * (non-Javadoc)
     */
    @Override
    public void postExcelToSlack( final File request ) {
        try {
            final Response response = new ErrorLogExcelRequestUtil.Builder().token( token ).uploadFile( request )
                    .channels( channelName ).title( "Error Log Excel" )
                    .initialComment( "Error Log Excel from host: " + hostService.getFullHost() ).build().post();
            LOGGER.debug( "File posted on slack the response is " + response + "now deleting the existing file" );
            request.delete();
        } catch ( final Exception e ) {
            LOGGER.error( "Something went wrong while posting Error log excel file on Slack " + request, e );
        }
    }
}
