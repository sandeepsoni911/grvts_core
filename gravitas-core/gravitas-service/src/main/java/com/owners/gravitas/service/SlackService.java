/**
 *
 */
package com.owners.gravitas.service;

import java.io.File;

import com.owners.gravitas.dto.request.SlackRequest;

/**
 * The Interface SlackService.
 *
 * @author harshads
 */
public interface SlackService {

    /**
     * Publish to slack.
     *
     * @param request
     *            the request
     * @param url
     *            the url
     */
    void publishToSlack( SlackRequest request, String url );

    /**
     * Post excel to slack.
     *
     * @param request
     *            the request
     */
    void postExcelToSlack( File request );
}
