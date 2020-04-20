package com.owners.gravitas.repository;

import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.Opportunity;

/**
 * The Interface OpportunityV1Repository.
 *
 * @author ankusht, shivam, abhishek
 */
public interface OpportunityRepository extends JpaRepository< Opportunity, String > {

    /** The Constant GET_RESPONSE_TIME_BY_ASSINGED_DATE. */
    public static final String GET_RESPONSE_TIME_BY_ASSINGED_DATE = "select o.responseTime from GR_OPPORTUNITY o where o.opportunityId in :fbOpportunityIds and o.deleted = false and o.assignedDate > :fbAssignedDate";

    /** The Constant FIND_ASSIGNED_AGENT_EMAILS_BY_SELECTED_STAGES_COUNT. */
    public static final String FIND_ASSIGNED_AGENT_EMAILS_BY_SELECTED_STAGES_COUNT = " SELECT distinct(a.email) FROM "
            + "   (SELECT COUNT(gc.id) AS stgCount, gad.id AS agentId, gu.email AS email, "
            + "       go.assigned_date AS assignedDate FROM GR_AGENT_DETAIL gad "
            + "       INNER JOIN GR_USER gu ON gad.user_id = gu.id "
            + "       INNER JOIN GR_OPPORTUNITY go ON gu.email = go.assigned_agent_email "
            + "       INNER JOIN GR_CONTACT gc ON gc.ID = go.CONTACT_ID  WHERE go.deleted = FALSE "
            + "       AND gu.email IN :emailsIn AND gc.stage IN :selStages "
            + "       AND gc.TYPE <> 'Seller' GROUP BY gu.email) a "
            + " INNER JOIN CUBE_AGENT_ELIGIBLE_ZIPS_V1 cube ON cube.email = a.email "
            + " INNER JOIN ME_ZIPCBSA mez ON cube.zip = mez.zip "
            + " INNER JOIN GR_CBSA_MARKET_LEVEL gcml ON gcml.cbsa_code_id = mez.cbsa "
            + " INNER JOIN GR_AGENT_DETAIL grad ON grad.id = a.agentId "
            + " WHERE a.stgCount >= gcml.max_opps_in_selected_stage  "
            + "   AND DATEDIFF(SYSDATE(), a.assignedDate) <= gcml.threshold_period ";

    /**
     * The Constant
     * FIND_ASSIGNED_AGENT_EMAILS_BY_SELECTED_STAGES_COUNT_FOR_DEFAULT.
     */
    public static final String FIND_ASSIGNED_AGENT_EMAILS_BY_SELECTED_STAGES_COUNT_FOR_DEFAULT = "SELECT distinct a.agentEmail FROM "
            + " (SELECT gu.email AS agentEmail, count(gc.id) as actualCount, "
            + "     gcml.MAX_OPPS_IN_SELECTED_STAGE as maxCount FROM GR_AGENT_DETAIL gad "
            + "     INNER JOIN GR_USER gu ON gad.user_id = gu.id "
            + "     INNER JOIN GR_OPPORTUNITY go ON gu.email = go.assigned_agent_email "
            + "     INNER JOIN GR_CONTACT gc ON gc.ID = go.CONTACT_ID "
            + "     INNER JOIN GR_CBSA_MARKET_LEVEL gcml ON gcml.cbsa_code_id = 'default' WHERE go.deleted = FALSE "
            + "         AND gu.email IN :emailsIn "
            + "         AND gc.stage IN :selStages "
            + "         AND gc.TYPE <> 'Seller' "
            + "         AND DATEDIFF(SYSDATE(), go.assigned_Date) <= gcml.threshold_period "
            + " GROUP BY agentEmail) a where a.actualCount >= a.maxCount";

    /** The Constant FIND_OPP_COUNT_BY_AGENT_EMAIL_AND_ASSIGNED_DATE. */
    public static final String FIND_OPP_COUNT_BY_AGENT_EMAIL_AND_ASSIGNED_DATE = "select count(go) from GR_CONTACT gc inner join gc.contactAttributes gca inner join gc.opportunities go inner join "
            + " gca.objectAttributeConfig goac where goac.attributeName ='leadSource' and gca.value not in ('BRS','Self Generated') "
            + " and gc.type !='Seller' and go.deleted=0 and go.assignedAgentId =:agentEmail and go.assignedDate >= :assignedDate";

    /** The Constant FIND_RESPONSE_TIME_BY_ASSIGNEDAGENT_AND_OPPTYPE. */
    public static final String FIND_RESPONSE_TIME_BY_ASSIGNEDAGENT_AND_OPPTYPE = "select o.responseTime from GR_OPPORTUNITY o INNER JOIN o.contact c"
            + " WHERE o.assignedAgentId=:agentEmail AND o.deleted=false AND c.type=:type AND o.responseTime IS NOT NULL"
            + " AND o.assignedDate BETWEEN :from AND :to";

    /** The Constant FIND_OPPORTUNITY_BY_CRM_ID. */
    public static final String FIND_OPPORTUNITY_BY_CRM_ID = "select o from GR_OPPORTUNITY o inner join o.contact c"
            + " WHERE c.crmId = :crmId and o.deleted=false";

    /** The Constant FIND_AGENT_EMAIL_BY_LISTING_ID. */
    final static String FIND_AGENT_EMAIL_BY_LISTING_ID = "select opp.assignedAgentId from GR_OPPORTUNITY opp inner join "
            + " opp.listingIdDetails ld inner join opp.contact c where opp.deleted = false and ld.listingId =:listingId and c.type = 'Seller'";

    /**
     * Find agent by email.
     *
     * @param listingId
     *            the listing id
     * @return the agent details
     */
    @Query( value = FIND_AGENT_EMAIL_BY_LISTING_ID )
    String findAgentEmailByListingId( @Param( "listingId" ) final String listingId );

    /**
     * Find by opportunity id and deleted.
     *
     * @param opportunityId
     *            the opportunity id
     * @param deleted
     *            the deleted
     * @return the opportunity v1
     */
    public Opportunity findByOpportunityIdAndDeleted( final String opportunityId, final boolean deleted );

    /**
     * Find assigned agent email by selected stages count.
     *
     * @param selectedStages
     *            the selected stages
     * @param emailsIn
     *            the emails in
     * @return the list
     */
    @Query( value = FIND_ASSIGNED_AGENT_EMAILS_BY_SELECTED_STAGES_COUNT, nativeQuery = true )
    List< String > findAssignedAgentEmailsBySelectedStagesCount(
            @Param( "selStages" ) Collection< String > selectedStages,
            @Param( "emailsIn" ) Collection< String > emailsIn );

    /**
     * Find assigned agent emails by selected stages count for default CBSA.
     *
     * @param selectedStages
     *            the selected stages
     * @param emailsIn
     *            the emails in
     * @return the list
     */
    @Query( value = FIND_ASSIGNED_AGENT_EMAILS_BY_SELECTED_STAGES_COUNT_FOR_DEFAULT, nativeQuery = true )
    List< String > findAssignedAgentEmailsBySelectedStagesCountForDefault(
            @Param( "selStages" ) Collection< String > selectedStages,
            @Param( "emailsIn" ) Collection< String > emailsIn );

    /**
     * Find by assigned date after and agent emails in.
     *
     * @param thresholdPeriod
     *            the threshold period
     * @param agentEmails
     *            the agent emails
     * @return the list
     */
    @Query( nativeQuery = true, value = "select ad.* from GR_AGENT_DETAIL ad inner join GR_USER u on u.id=ad.user_id"
            + " inner join GR_OPPORTUNITY o on o.ASSIGNED_AGENT_EMAIL=u.email "
            + " where u.email in :emails and o.ASSIGNED_DATE > :threshold" )
    List< AgentDetails > findByAssignedDateAfterAndAgentEmailsIn( @Param( "threshold" ) DateTime thresholdPeriod,
            @Param( "emails" ) Collection< String > agentEmails );

    /**
     * Count by assigned agent id and assigned date after and deleted false.
     *
     * @param agentEmail
     *            the agent email
     * @param assignedDate
     *            the assigned date
     * @return the int
     */
    @Query( value = FIND_OPP_COUNT_BY_AGENT_EMAIL_AND_ASSIGNED_DATE )
    int countByAssignedAgentIdAndAssignedDateAfterAndDeletedFalse( @Param( "agentEmail" ) String agentEmail,
            @Param( "assignedDate" ) DateTime assignedDate );

    /**
     * Find by opportunity id.
     *
     * @param fbId
     *            the fb id
     * @return the opportunity v1
     */
    Opportunity findByOpportunityId( String fbId );

    /**
     * Find response time by assigned agent and opportunity type.
     *
     * @param agentEmail
     *            the agent email
     * @param opportunityType
     *            the opportunity type
     * @param from
     *            the from
     * @param to
     *            the to
     * @return the list
     */
    @Query( value = FIND_RESPONSE_TIME_BY_ASSIGNEDAGENT_AND_OPPTYPE )
    List< Long > findResponseTimeByAssignedAgentAndOpportunityType( @Param( "agentEmail" ) String agentEmail,
            @Param( "type" ) String opportunityType, @Param( "from" ) DateTime from, @Param( "to" ) DateTime to );

    /**
     * Find by crm id.
     *
     * @param crmId
     *            the crm id
     * @return the opportunity v1
     */
    @Query( value = FIND_OPPORTUNITY_BY_CRM_ID )
    Opportunity findByCrmId( @Param( "crmId" ) final String crmId );

    /**
     * Find by assigned agent id and deleted.
     *
     * @param assignedAgentId
     *            the assigned agent id
     * @param deleted
     *            the deleted
     * @return the list
     */
    List< Opportunity > findByAssignedAgentIdAndDeleted( String assignedAgentId, boolean deleted );

    /**
     * Find response time by opportunity ids.
     *
     * @param fbOpportunityIds
     *            the fb opportunity ids
     * @param fbAssignedDate
     *            the fb assigned date
     * @return the list
     */
    @Query( GET_RESPONSE_TIME_BY_ASSINGED_DATE )
    List< Long > findResponseTimeByOpportunityIds(
            @Param( "fbOpportunityIds" ) final Collection< String > fbOpportunityIds,
            @Param( "fbAssignedDate" ) final DateTime fbAssignedDate );

}
