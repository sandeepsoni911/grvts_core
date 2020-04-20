package com.owners.gravitas.business;

import com.owners.gravitas.amqp.LeadSource;

/**
 * The Interface LeadFollowupBusinessService.
 *
 * @author vishwanathm
 */
public interface LeadFollowupBusinessService {

    /**
     * Start marketing email process, only in case if the buyer lead status is
     * 'Outbound Attempt', belong to defined lead sources and the lead source
     * should not be 'Unbounce landing page'.
     *
     * @param String
     *            dataNode
     */
    void startLeadFollowupEmailProcess( LeadSource leadSource );

    /**
     * Send marketing email to buyer email with respective lead owner details by
     * retrieving configured email template from notification engine.
     *
     * @param executionId
     *            the execution id
     * @param leadSource
     *            the lead source
     */
    void sendLeadFollowupEmails( String executionId, LeadSource leadSource );

    /**
     * Clean marketing email log.
     *
     * @param id
     *            the id
     */
    void cleanLeadFollowupLog( String id );

}
