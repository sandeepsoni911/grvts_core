package com.owners.gravitas.service;

import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.Pageable;

import com.owners.gravitas.domain.entity.AgentAssignmentLog;
import com.owners.gravitas.dto.AgentAssignmentLogDto;

/**
 * The Interface AgentAssignmentLogService.
 * 
 * @author ankusht, abhishek
 */
public interface AgentAssignmentLogService {

    /**
     * Save all.
     *
     * @param agentAssignmentLogs
     *            the agent assignment logs
     */
    void saveAll( Collection< AgentAssignmentLog > agentAssignmentLogs );

    /**
     * Find by property zips.
     *
     * @param propertyZip
     *            the property zips
     * @return the list
     */
    List< AgentAssignmentLogDto > findByPropertyZip( String propertyZip );

    /**
     * Find agent email by least opp assigned date.
     *
     * @param agentEmails
     *            the agent emails
     * @return the string
     */
    String findAgentEmailByLeastOppAssignedDate( Collection< String > agentEmails );

    /**
     * Save.
     *
     * @param log
     *            the log
     */
    void save( AgentAssignmentLog log );

    /**
     * Find by opportunity id.
     *
     * @param crmId
     *            the crm id
     * @param status
     *            the status
     * @param pageable
     *            the pageable
     * @return the agent assignment log
     */
    AgentAssignmentLog findByOpportunityIdAndStatusLike( String crmId, String status, Pageable pageable );

    /**
     * Find by property zip and emails in.
     *
     * @param zip
     *            the zip
     * @param hungryAgentBucketEmails
     *            the hungry agent bucket emails
     * @return the list
     */
    List< AgentAssignmentLogDto > findByPropertyZipAndEmailsIn( String zip,
            Collection< String > hungryAgentBucketEmails );

    /**
     * Check and get agent reject reason from enum AssignmentStatus.
     *
     * @param reason
     *            the reason
     * @return the reason
     */
    String getAgentRejectReason( String reason );

    /**
     * Gets the agent assignment log.
     *
     * @param crmOppId
     *            the crm opp id
     * @param agentEmail
     *            the agent email
     * @param status
     *            the status
     * @param page
     *            the page
     * @return the agent assignment log
     */
    AgentAssignmentLog getAgentAssignmentLog( String crmOppId, String agentEmail, String status, Pageable page );

    /**
     * Find by crm opportunity id and agent email and created date.
     *
     * @param crmOppId
     *            the crm opp id
     * @param agentEmail
     *            the agent email
     * @param createdDate
     *            the created date
     * @return the agent assignment log
     */
    AgentAssignmentLog findByCrmOpportunityIdAndAgentEmailAndCreatedDate( String crmOppId, String agentEmail,
            DateTime createdDate );
}
