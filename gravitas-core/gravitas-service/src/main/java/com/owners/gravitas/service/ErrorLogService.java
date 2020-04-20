package com.owners.gravitas.service;

/**
 * The Interface ErrorLogService.
 *
 * @author shivamm
 */
public interface ErrorLogService {


    /**
     * Publish error report on slack.
     */
    void publishErrorReportOnSlack();

}
