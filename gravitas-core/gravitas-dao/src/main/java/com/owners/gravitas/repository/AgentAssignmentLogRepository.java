package com.owners.gravitas.repository;

import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.owners.gravitas.domain.entity.AgentAssignmentLog;

/**
 * The Interface AgentAssignmentLogRepository.
 * 
 * @author ankusht, abhishek
 */
public interface AgentAssignmentLogRepository extends JpaRepository< AgentAssignmentLog, String > {

    /** The find by zips. */
    String FIND_BY_ZIP = "select u.email,vdim.happy_agents_score, mez.zip, mez.cbsa, omcl.owners_market_label "
            + " from GR_USER u inner join GR_AGENT_DETAIL ad on ad.user_id=u.id inner join CUBE_AGENT_ELIGIBLE_ZIPS_V1 c "
            + " on c.email=u.email inner join V_DIM_AGENT_SCORE vdim on c.email=vdim.email inner join ME_ZIPCBSA mez on mez.zip=c.zip "
            + " inner join GR_OWNERS_MARKET_CBSA_LABEL omcl on omcl.id=mez.cbsa where mez.zip = :propertyZip";

    /** The find by zip and emails in. */
    String FIND_BY_ZIP_AND_EMAILS_IN = "select u.email,vdim.happy_agents_score, mez.zip, mez.cbsa, omcl.owners_market_label "
            + " from GR_USER u inner join GR_AGENT_DETAIL ad on ad.user_id=u.id inner join CUBE_AGENT_ELIGIBLE_ZIPS_V1 c "
            + " on c.email=u.email inner join V_DIM_AGENT_SCORE vdim on c.email=vdim.email inner join ME_ZIPCBSA mez on mez.zip=c.zip "
            + " inner join GR_OWNERS_MARKET_CBSA_LABEL omcl on omcl.id=mez.cbsa where mez.zip = :propertyZip and u.email in :emails";

    /** The Constant FIND_AGENT_EMAIL_BY_LEAST_OPP_ASSIGNED_DATE. */
    final static String FIND_AGENT_EMAIL_BY_LEAST_OPP_ASSIGNED_DATE = "select o.assigned_agent_email from GR_OPPORTUNITY o "
            + "where o.assigned_agent_email in :agentEmails "
            + "and o.deleted=false order by o.assigned_date ASC limit 1";

    /**
     * Find by property zips.
     *
     * @param propertyZips
     *            the property zips
     * @return the list
     */
    @Query( nativeQuery = true, value = FIND_BY_ZIP )
    List< Object[] > findByPropertyZip( @Param( "propertyZip" ) String propertyZips );

    /**
     * Find by property zip and emails in.
     *
     * @param zip
     *            the zip
     * @param hungryAgentBucketEmails
     *            the hungry agent bucket emails
     * @return the list
     */
    @Query( nativeQuery = true, value = FIND_BY_ZIP_AND_EMAILS_IN )
    List< Object[] > findByPropertyZipAndEmailsIn( @Param( "propertyZip" ) String zip,
            @Param( "emails" ) Collection< String > hungryAgentBucketEmails );

    /**
     * Find agent email by least opp assigned date.
     *
     * @param agentEmails
     *            the agent emails
     * @return the string
     */
    @Query( nativeQuery = true, value = FIND_AGENT_EMAIL_BY_LEAST_OPP_ASSIGNED_DATE )
    String findAgentEmailByLeastOppAssignedDate( @Param( "agentEmails" ) Collection< String > agentEmails );

    /**
     * Find by crm opportunity id and assignment status.
     *
     * @param crmId
     *            the crm id
     * @param status
     *            the status
     * @param page
     *            the pageable
     * @return the agent assignment log
     */
    Page< AgentAssignmentLog > findByCrmOpportunityIdAndAssignmentStatusContainingOrderByCreatedDateDesc( String crmId,
            String status, Pageable pageable );

    /**
     * Find by crm opportunity id and agent email and assignment status
     * containing.
     *
     * @param crmOppId
     *            the crm opp id
     * @param agentEmail
     *            the agent email
     * @param status
     *            the status
     * @param page
     *            the pageable
     * @return the agent assignment log
     */
    Page< AgentAssignmentLog > findByCrmOpportunityIdAndAgentEmailAndAssignmentStatusContaining( String crmOppId,
            String agentEmail, String status, Pageable pageable );

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
