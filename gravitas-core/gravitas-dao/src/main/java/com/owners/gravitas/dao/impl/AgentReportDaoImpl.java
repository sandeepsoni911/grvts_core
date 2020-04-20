package com.owners.gravitas.dao.impl;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.config.AgentOpportunityBusinessConfig;
import com.owners.gravitas.dao.AgentReportDao;

// TODO: Auto-generated Javadoc
/**
 * The Class AgentReportDaoImpl.
 *
 * @author amits
 */
@Repository
public class AgentReportDaoImpl implements AgentReportDao {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentReportDaoImpl.class );

    /** The entity manager factory. */
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private AgentOpportunityBusinessConfig agentOpportunityBusinessConfig;

    /** The opportunity stage merge log. */
    private String OPPORTUNITY_STAGE_MERGE_LOG = "SELECT t1.opp AS opp, t1.stage AS from_stage,"
            + " t2.stage AS to_stage, t1.stamp AS from_time," + " ifnull(t2.stamp,utc_timestamp()) AS to_time FROM"
            + " (SELECT opp, stage, stamp, @curRank\\:=@curRank + 1 AS rank"
            + " FROM  (SELECT  log.opportunity_id AS opp," + " log.stage AS stage, log.created_on AS stamp"
            + " FROM  gr_opportunity_stage_log log ) AS tab, (SELECT @curRank\\:=0) r" + " ORDER BY opp , stamp) T1 "
            + " left join (SELECT opp, stage, stamp, @curRank1\\:=@curRank1 + 1 AS rank"
            + " FROM  (SELECT  log.opportunity_id AS opp," + " log.stage AS stage, log.created_on AS stamp"
            + " FROM  gr_opportunity_stage_log log ) AS tab, (SELECT @curRank1\\:=0) r" + " ORDER BY opp , stamp) T2"
            + " on t1.rank + 1 = t2.rank AND t1.opp = t2.opp";

    /** The get cta by opportunity stage. */
    private String GET_CTA_BY_OPPORTUNITY_STAGE = " SELECT  opp_type, stage, ROUND(call_sum / opp_count, 0) AS `call`, ROUND(email_sum / opp_count, 0) AS `email`, ROUND(sms_sum / opp_count, 0) AS `sms`, (ROUND(call_sum / opp_count, 0) + ROUND(email_sum / opp_count, 0) + ROUND(sms_sum / opp_count, 0)) AS total FROM (SELECT  opp_type, stage, IFNULL(SUM(CASE WHEN action_type = 'CALL' THEN count END), 0) CALL_SUM, IFNULL(SUM(CASE WHEN action_type = 'EMAIL' THEN count END), 0) EMAIL_SUM, IFNULL(SUM(CASE WHEN action_type = 'SMS' THEN count END), 0) SMS_SUM, COUNT(DISTINCT opp) AS opp_count FROM (  SELECT  opp_type, from_stage AS stage, opp, action_type, COUNT(*) AS count FROM (SELECT  opportunity_stage_merge_log.opp AS opp, op.fb_opportunity_id AS fb_id, con.type AS opp_type, op.assigned_date, opportunity_stage_merge_log.from_time AS from_time, opportunity_stage_merge_log.to_time AS to_time, opportunity_stage_merge_log.from_stage AS from_stage, opportunity_stage_merge_log.to_stage AS to_stage, COUNT(DISTINCT op.id) FROM GR_OPPORTUNITY op INNER JOIN gr_contact con ON op.contact_id = con.id INNER JOIN (SELECT  t1.opp AS opp, t1.stage AS from_stage, t2.stage AS to_stage, t1.stamp AS from_time, IFNULL(t2.stamp, UTC_TIMESTAMP()) AS to_time FROM (SELECT opp, stage, stamp, @curRank\\:=@curRank + 1 AS rank FROM (SELECT  log.opportunity_id AS opp, log.stage AS stage, log.created_on AS stamp FROM gr_opportunity_stage_log log ) AS tab, (SELECT @curRank\\:=0) r ORDER BY opp , stamp ) T1 LEFT JOIN (SELECT opp, stage, stamp, @curRank1\\:=@curRank1 + 1 AS rank FROM (SELECT  log.opportunity_id AS opp, log.stage AS stage, log.created_on AS stamp FROM gr_opportunity_stage_log log) AS tab, (SELECT @curRank1\\:=0) r ORDER BY opp , stamp ) T2 ON t1.rank + 1 = t2.rank AND t1.opp = t2.opp ) AS opportunity_stage_merge_log ON opportunity_stage_merge_log.opp = op.id WHERE op.deleted = FALSE AND op.assigned_agent_email = :agentEmail AND CAST( op.assigned_date as DATE ) BETWEEN :startDate AND :endDate AND opportunity_stage_merge_log.from_time >= op.assigned_date AND  ( CASE WHEN con.type = 'Buyer' THEN (opportunity_stage_merge_log.from_stage IN ('New' , 'Claimed', 'In Contact', 'Face To Face Meeting', 'Securing Financing', 'Showing Homes', 'Writing Offer')) ELSE  CASE WHEN con.type = 'Seller' THEN (opportunity_stage_merge_log.from_stage IN ('New' , 'Claimed', 'In Contact', 'Listing Appointment', 'Listing Agreement Signed', 'Active - Unlisted', 'Active - MLS', 'Active - Rental')) END END) GROUP BY op.id , opportunity_stage_merge_log.from_time ORDER BY op.id , opportunity_stage_merge_log.from_time) AS tes INNER JOIN gr_action_log al ON tes.fb_id = al.action_entity_id AND al.CREATED_ON BETWEEN tes.from_time AND tes.to_time AND al.ACTION_TYPE IN ('EMAIL', 'CALL', 'SMS') GROUP BY opp_type , opp , from_stage , action_type  UNION  SELECT  opp_type, from_stage AS stage, opp, NULL, 0 AS count FROM (SELECT  opportunity_stage_merge_log.opp AS opp, op.fb_opportunity_id AS fb_id, con.type AS opp_type, op.assigned_date, opportunity_stage_merge_log.from_time AS from_time, opportunity_stage_merge_log.to_time AS to_time, opportunity_stage_merge_log.from_stage AS from_stage, opportunity_stage_merge_log.to_stage AS to_stage, COUNT(DISTINCT op.id) FROM GR_OPPORTUNITY op INNER JOIN gr_contact con ON op.contact_id = con.id INNER JOIN (SELECT  t1.opp AS opp, t1.stage AS from_stage, t2.stage AS to_stage, t1.stamp AS from_time, IFNULL(t2.stamp, UTC_TIMESTAMP()) AS to_time FROM (SELECT opp, stage, stamp, @curRank\\:=@curRank + 1 AS rank FROM (SELECT  log.opportunity_id AS opp, log.stage AS stage, log.created_on AS stamp FROM gr_opportunity_stage_log log ) AS tab, (SELECT @curRank\\:=0) r ORDER BY opp , stamp ) T1 LEFT JOIN (SELECT opp, stage, stamp, @curRank1\\:=@curRank1 + 1 AS rank FROM (SELECT  log.opportunity_id AS opp, log.stage AS stage, log.created_on AS stamp FROM gr_opportunity_stage_log log) AS tab, (SELECT @curRank1\\:=0) r ORDER BY opp , stamp ) T2 ON t1.rank + 1 = t2.rank AND t1.opp = t2.opp ) AS opportunity_stage_merge_log ON opportunity_stage_merge_log.opp = op.id WHERE op.deleted = FALSE AND op.assigned_agent_email = :agentEmail AND CAST( op.assigned_date as DATE ) BETWEEN :startDate AND :endDate AND opportunity_stage_merge_log.from_time >= op.assigned_date AND  ( CASE WHEN con.type = 'Buyer' THEN (opportunity_stage_merge_log.from_stage IN ('New' , 'Claimed', 'In Contact', 'Face To Face Meeting', 'Securing Financing', 'Showing Homes', 'Writing Offer')) ELSE  CASE WHEN con.type = 'Seller' THEN (opportunity_stage_merge_log.from_stage IN ('New' , 'Claimed', 'In Contact', 'Listing Appointment', 'Listing Agreement Signed', 'Active - Unlisted', 'Active - MLS', 'Active - Rental')) END END) GROUP BY op.id , opportunity_stage_merge_log.from_time ORDER BY op.id , opportunity_stage_merge_log.from_time) AS tes  GROUP BY opp_type , opp , from_stage ) AS temp GROUP BY opp_type , stage) stats";

    /** The get assigend opportunity count. */
    private String GET_ASSIGEND_BUYER_OPPORTUNITY_COUNT = "SELECT count(op.id) from GR_OPPORTUNITY op inner join gr_contact con on op.contact_id=con.id AND con.type in (:type) where op.deleted = FALSE "
            + " AND op.assigned_agent_email = :agentEmail"
            + " AND cast(op.assigned_date as date) BETWEEN :startDate AND :endDate";

    /** The get opportunity contains stage count. */
    private String GET_OPPORTUNITY_COUNT_BY_STAGE = "SELECT  COUNT(DISTINCT op.id) AS count FROM"
            + " gr_contact con INNER JOIN GR_OPPORTUNITY op INNER JOIN"
            + " gr_opportunity_stage_log sl ON con.ID = op.contact_id AND sl.opportunity_id = op.id"
            + " AND con.type in (:type) WHERE sl.stage IN (:stages)"
            + " AND op.deleted = FALSE AND op.assigned_agent_email = :agentEmail"
            + " AND cast(op.assigned_date as date) BETWEEN :startDate AND :endDate";

    /** The get face to face count. */
    private String GET_FACE_TO_FACE_COUNT = "SELECT  F2F_SCORE FROM v_dim_agent_score WHERE email = :agentEmail";

    /** The Constant GET_STAGE_DETAILS. */
    private String GET_STAGE_DETAILS = "SELECT ww.t1stage,sum(diff),ww.type" + " FROM" + " (SELECT "
            + " opp_id,con.type AS type,first, second,TIMESTAMPDIFF(SECOND,first,second) as diff,t1stage" + " FROM "
            + " GR_OPPORTUNITY op INNER JOIN" + "  gr_contact con INNER JOIN" + " (  SELECT "
            + " t1.opportunity_id AS opp_id, t1.created_on AS first , ifnull(MIN(t2.created_on),UTC_TIMESTAMP()) AS second, t1.stage AS t1stage, t2.stage AS t2stage"
            + " FROM" + "  gr_opportunity_stage_log t1 LEFT JOIN"
            + " gr_opportunity_stage_log t2 ON t2.OPPORTUNITY_ID = t1.OPPORTUNITY_ID AND t2.created_on > t1.created_on"
            + " GROUP BY" + " opp_id,t1.created_on ORDER BY opp_id,t1.created_on" + " ) AS  t1" + " ON"
            + " t1.opp_id = op.ID AND op.contact_id = con.id" + " WHERE" + " op.ASSIGNED_AGENT_EMAIL = :agentEmail"
            + " AND cast(op.ASSIGNED_DATE as date) BETWEEN :fromDate AND :toDate" + " AND ( CASE"
            + " WHEN con.type = 'buyer' THEN (t1stage IN ('New' , 'Claimed', 'In Contact', 'Face To Face Meeting', 'Securing Financing', 'Showing Homes', 'Writing Offer'))"
            + " ELSE CASE"
            + " WHEN con.type = 'seller' THEN (t1stage IN ('New' , 'Claimed', 'In Contact', 'Listing Appointment', 'Listing Agreement Signed', 'Active - Unlisted', 'Active - MLS', 'Active - Rental'))"
            + " END" + " END" + " )" + " AND op.deleted=false" + " AND first >= op.assigned_date"
            + " GROUP BY opp_id, first" + " ORDER BY opp_id,first) as ww" + " GROUP BY ww.opp_id,ww.t1stage"
            + " ORDER BY ww.opp_id,ww.first";

    /** The get lost opportunity count. */
    private String GET_LOST_OPPORTUNITY_COUNT = "SELECT COUNT(DISTINCT op.id) AS count FROM ("
            + OPPORTUNITY_STAGE_MERGE_LOG
            + ") opp_stage INNER JOIN GR_OPPORTUNITY op ON op.id = opp_stage.opp INNER JOIN"
            + "            gr_contact con ON con.id = op.contact_id WHERE from_stage IN ('New' , 'Claimed', 'In Contact')"
            + " AND to_stage = 'Closed Lost' AND op.deleted = FALSE AND op.assigned_agent_email = :agentEmail"
            + " AND CAST(op.assigned_date AS DATE) BETWEEN :startDate AND :endDate" + "        AND con.type = :type";

    /** The Constant GET_FIRST_RESPONSE_TIME. */
    private String GET_FIRST_RESPONSE_TIME = "SELECT result.type,timestampdiff(SECOND,result.assigned_date,result.created_on) "
            + " FROM "
            + " (SELECT con.type as type, opp_v1.assigned_date AS assigned_date,MIN(act_log.CREATED_ON) AS created_on "
            + " FROM GR_OPPORTUNITY opp_v1,gr_contact con,gr_action_log act_log "
            + " WHERE  opp_v1.assigned_agent_email= :agentEmail "
            + " AND cast(opp_v1.assigned_date as date) BETWEEN :fromDate AND :toDate "
            + " AND opp_v1.contact_id=con.id " + " AND (con.type='Buyer' OR con.type='Seller') "
            + " AND opp_v1.deleted=false " + " AND act_log.action_entity_id=opp_v1.fb_opportunity_id "
            + " AND act_log.action_type in ( 'email','call','sms') " + " GROUP BY opp_v1.id) AS result";

    /** The statistics for agent. */
    private String STATISTICS_FOR_AGENT = "select usr.id as agent_id, oppLog.opportunity_id,contact.first_name, contact.LAST_NAME, DATE_FORMAT(oppLog.created_on,'%m/%d/%Y') AS created_on,contact.crm_id,usr.email as agent_email,oppLog.stage, DATE_FORMAT(opportunity.created_on,'%m/%d/%Y') as opportunity_created_on from gr_user usr"
            + " inner join gr_agent_detail agent on usr.ID = agent.user_id"
            + " inner join GR_OPPORTUNITY opportunity on usr.email = opportunity.assigned_agent_email"
            + " inner join gr_contact contact on opportunity.contact_id = contact.id" + " inner join"
            + " (select distinct (oppLog1.opportunity_id) , oppLog1.stage, oppLog1.CREATED_ON from (select * from gr_opportunity_stage_log  oppLog1 where oppLog1.stage in ('Face To Face Meeting','Showing Homes','Securing Financing','Writing Offer') order by oppLog1.OPPORTUNITY_ID, oppLog1.CREATED_ON ) oppLog1   group by opportunity_id)"
            + " as oppLog  on opportunity.id = oppLog.opportunity_id  where usr.email = :agentEmail";

    /** Get F2F Count Report for given Agent. */
    private String CUMULATIVE_FOR_AGENT = "select count(*), DATE_FORMAT(oppLog.created_on,'%d/%m/%Y') AS created_on, usr.email as agent_email from gr_user usr"
            + " inner join gr_agent_detail agent on usr.ID = agent.user_id"
            + " inner join GR_OPPORTUNITY opportunity on usr.email = opportunity.assigned_agent_email"
            + " inner join gr_contact contact on opportunity.contact_id = contact.id" + " inner join"
            + " (select distinct (oppLog1.opportunity_id) , oppLog1.stage, oppLog1.CREATED_ON from (select * from gr_opportunity_stage_log  oppLog1 where oppLog1.stage in ('Face To Face Meeting','Showing Homes','Securing Financing','Writing Offer') order by oppLog1.OPPORTUNITY_ID, oppLog1.CREATED_ON ) oppLog1   group by opportunity_id)"
            + " as oppLog  on opportunity.id = oppLog.opportunity_id  where usr.email = :email and opportunity.deleted='0' ";

    /** Get F2F Count Report for given Managing Brocker. */
    private String STATISTICS_FOR_MB = "select usr1.id as agent_id, oppLog.opportunity_id,contact.first_name, contact.LAST_NAME, DATE_FORMAT(oppLog.created_on,'%m/%d/%Y') AS created_on,contact.crm_id,usr1.email as agent_email,  oppLog.stage,usr.id as managing_brocker_id, DATE_FORMAT(opportunity.created_on,'%m/%d/%Y') as opportunity_created_on from gr_user usr"
            + " inner join gr_agent_detail agent on usr.ID = agent.managing_broker_id"
            + " inner join gr_user usr1 on agent.user_id = usr1.id"
            + " inner join GR_OPPORTUNITY opportunity on usr1.email = opportunity.assigned_agent_email"
            + " inner join gr_contact contact on opportunity.contact_id = contact.id" + " inner join"
            + " (select distinct (oppLog1.opportunity_id) , oppLog1.stage, oppLog1.CREATED_ON from (select * from gr_opportunity_stage_log  oppLog1 where oppLog1.stage in ('Face To Face Meeting','Showing Homes','Securing Financing','Writing Offer') order by oppLog1.OPPORTUNITY_ID, oppLog1.CREATED_ON ) oppLog1   group by opportunity_id)"
            + " as oppLog  on opportunity.id = oppLog.opportunity_id  where usr.email = :mbEmail";

    /** Get F2F Count Report for given Managing Brocker. */
    private String CUMULATIVE_FOR_MB = "select count(*) , DATE_FORMAT(oppLog.created_on,'%d/%m/%Y') AS created_on, usr1.email as agent_email  from gr_user usr"
            + " inner join gr_agent_detail agent on usr.ID = agent.managing_broker_id"
            + " inner join gr_user usr1 on agent.user_id = usr1.id"
            + " inner join GR_OPPORTUNITY opportunity on usr1.email = opportunity.assigned_agent_email"
            + " inner join gr_contact contact on opportunity.contact_id = contact.id" + " inner join"
            + " (select distinct (oppLog1.opportunity_id) , oppLog1.stage, oppLog1.CREATED_ON from (select * from gr_opportunity_stage_log  oppLog1 where oppLog1.stage in ('Face To Face Meeting','Showing Homes','Securing Financing','Writing Offer') order by oppLog1.OPPORTUNITY_ID, oppLog1.CREATED_ON ) oppLog1   group by opportunity_id)"
            + " as oppLog  on opportunity.id = oppLog.opportunity_id  where usr.email = :email and opportunity.deleted='0' ";

    /** Get Agents & their Managing Broker email ids */
    private String AGENTS_AND_THEIR_MANAGING_BROKER_EMAIL_ID = "select usr.email as agentEmailId, usr1.email as managingBrokerEmailId from gr_user usr "
            + "inner join gr_agent_detail agent on agent.user_id = usr.id "
            + "inner join gr_user usr1 on agent.managing_broker_id = usr1.id " + "where usr.email in (:emailIdList)";

    /** Get F2F Count Report for given Executive. */
    private String CUMULATIVE_FOR_EXECUTIVE = "select count(*) , DATE_FORMAT(oppLog.created_on,'%d/%m/%Y') AS created_on, usr.email as managingBrokeriD "
            + "from gr_agent_detail agent inner join gr_user usr on  agent.managing_broker_id = usr.id "
            + "inner join gr_user usr1 on agent.user_id = usr1.id "
            + "inner join GR_OPPORTUNITY opportunity on usr1.email = opportunity.assigned_agent_email "
            + "inner join gr_contact contact on opportunity.contact_id = contact.id inner join "
            + "(select distinct (oppLog1.opportunity_id) , oppLog1.stage, oppLog1.CREATED_ON from (select * from gr_opportunity_stage_log  oppLog1 where oppLog1.stage in ('Face To Face Meeting','Showing Homes','Securing Financing','Writing Offer') order by oppLog1.OPPORTUNITY_ID, oppLog1.CREATED_ON ) oppLog1 group by opportunity_id) "
            + "as oppLog  on opportunity.id = oppLog.opportunity_id and opportunity.deleted='0'  ";

    /** Get Statistic Report for given Executive. */
    private String STATISTICS_FOR_EXECUTIVE = "select usr1.id as agent_id, oppLog.opportunity_id,contact.first_name, contact.LAST_NAME, DATE_FORMAT(oppLog.created_on,'%m/%d/%Y') AS created_on,contact.crm_id,usr1.email as agent_email,  oppLog.stage,usr.id as managing_brocker_id, DATE_FORMAT(opportunity.created_on,'%m/%d/%Y') as opportunity_created_on from gr_user usr "
            + "             inner join gr_agent_detail agent on usr.ID = agent.managing_broker_id "
            + "             inner join gr_user usr1 on agent.user_id = usr1.id "
            + "             inner join GR_OPPORTUNITY opportunity on usr1.email = opportunity.assigned_agent_email "
            + "             inner join gr_contact contact on opportunity.contact_id = contact.id "
            + "             inner join "
            + "             (select distinct (oppLog1.opportunity_id) , oppLog1.stage, oppLog1.CREATED_ON from (select * from gr_opportunity_stage_log  oppLog1 where oppLog1.stage in ('Face To Face Meeting','Showing Homes','Securing Financing','Writing Offer') order by oppLog1.OPPORTUNITY_ID, oppLog1.CREATED_ON ) oppLog1   group by opportunity_id) "
            + "             as oppLog  on opportunity.id = oppLog.opportunity_id ";

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.AgentReportDao#getAgentsStagewiseCta(java.sql.
     * Date, java.sql.Date, java.lang.String)
     */
    @Override
    public List< Object[] > getAgentsStagewiseCta( final Date startDate, final Date endDate, final String agentEmail ) {
        final Query query = entityManagerFactory.createEntityManager()
                .createNativeQuery( GET_CTA_BY_OPPORTUNITY_STAGE );
        query.setParameter( "startDate", startDate );
        query.setParameter( "endDate", endDate );
        query.setParameter( "agentEmail", agentEmail );
        return query.getResultList();
    }

    /**
     * Gets the lost opportunity count.
     *
     * @param startDate
     *            the start date
     * @param endDate
     *            the end date
     * @param agentEmail
     *            the agent email
     * @param type
     *            the type
     * @return the lost opportunity count
     */
    @Override
    public Object getClosedLostOpportunityCount( @Param( "startDate" ) final Date startDate,
            @Param( "endDate" ) final Date endDate, @Param( "agentEmail" ) final String agentEmail,
            @Param( "type" ) final List< String > type ) {
        final Query query = entityManagerFactory.createEntityManager().createNativeQuery( GET_LOST_OPPORTUNITY_COUNT );
        query.setParameter( "startDate", startDate );
        query.setParameter( "endDate", endDate );
        query.setParameter( "agentEmail", agentEmail );
        query.setParameter( "type", type );
        return query.getSingleResult();
    }

    /**
     * Gets the assigned opportunity count.
     *
     * @param startDate
     *            the start date
     * @param endDate
     *            the end date
     * @param agentEmail
     *            the agent email
     * @param type
     *            the type
     * @return the assigned opportunity count
     */
    @Override
    public Object getAssignedOpportunityCount( @Param( "startDate" ) final Date startDate,
            @Param( "endDate" ) final Date endDate, @Param( "agentEmail" ) final String agentEmail,
            @Param( "type" ) final List< String > type ) {
        final Query query = entityManagerFactory.createEntityManager()
                .createNativeQuery( GET_ASSIGEND_BUYER_OPPORTUNITY_COUNT );
        query.setParameter( "startDate", startDate );
        query.setParameter( "endDate", endDate );
        query.setParameter( "agentEmail", agentEmail );
        query.setParameter( "type", type );
        return query.getSingleResult();
    }

    /**
     * Gets the opportunity contains stage count.
     *
     * @param startDate
     *            the start date
     * @param endDate
     *            the end date
     * @param agentEmail
     *            the agent email
     * @param stages
     *            the stages
     * @param type
     *            the type
     * @return the opportunity contains stage count
     */
    @Override
    public Object getOpportunityCountByStage( @Param( "startDate" ) final Date startDate,
            @Param( "endDate" ) final Date endDate, @Param( "agentEmail" ) final String agentEmail,
            @Param( "stages" ) final List< String > stages, @Param( "type" ) final List< String > type ) {
        final Query query = entityManagerFactory.createEntityManager()
                .createNativeQuery( GET_OPPORTUNITY_COUNT_BY_STAGE );
        query.setParameter( "startDate", startDate );
        query.setParameter( "endDate", endDate );
        query.setParameter( "agentEmail", agentEmail );
        query.setParameter( "stages", stages );
        query.setParameter( "type", type );
        return query.getSingleResult();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dao.AgentReportDao#getFaceToFaceCount(java.lang.
     * String)
     */
    @Override
    public Object getFaceToFaceCount( @Param( "agentEmail" ) final String agentEmail ) {
        final Query query = entityManagerFactory.createEntityManager().createNativeQuery( GET_FACE_TO_FACE_COUNT );
        query.setParameter( "agentEmail", agentEmail );
        Object result = null;
        try {
            result = query.getSingleResult();
        } catch ( final NoResultException nre ) {
            LOGGER.error( "No result exception as there is no record for the email {} in v_dim_agent_score table ",
                    agentEmail, nre );
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dao.AgentReportDao#
     * getOpportunitiesStageDetailsByDateRange(java.lang.String, java.sql.Date,
     * java.sql.Date)
     */
    @Override
    public List< Object[] > getOpportunitiesStageDetailsByDateRange( final String agentEmail, final Date fromDate,
            final Date toDate ) {
        final Query query = entityManagerFactory.createEntityManager().createNativeQuery( GET_STAGE_DETAILS );
        query.setParameter( "fromDate", fromDate );
        query.setParameter( "toDate", toDate );
        query.setParameter( "agentEmail", agentEmail );
        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.AgentReportDao#getOpportunitiesFirstResponseTime(
     * java.lang.String, java.sql.Date, java.sql.Date)
     */
    @Override
    public List< Object[] > getOpportunitiesFirstResponseTime( final String agentEmail, final Date fromDate,
            final Date toDate ) {
        final Query query = entityManagerFactory.createEntityManager().createNativeQuery( GET_FIRST_RESPONSE_TIME );
        query.setParameter( "fromDate", fromDate );
        query.setParameter( "toDate", toDate );
        query.setParameter( "agentEmail", agentEmail );
        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.AgentReportDao#getManagingBrokerStatistics(java.
     * lang.String)
     */
    @Override
    public List< Object[] > getManagingBrokerStatistics( final String email ) {

        final String appendQuery = " and opportunity.deleted='0' order by oppLog.created_on ";
        final Query query = entityManagerFactory.createEntityManager()
                .createNativeQuery( STATISTICS_FOR_MB + appendQuery );
        query.setParameter( "mbEmail", email );

        final List< Object[] > objList = query.getResultList();
        return objList;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.AgentReportDao#getManagingBrokerGridStatistics(
     * java.lang.String)
     */
    @Override
    public List< Object[] > getManagingBrokerGridStatistics( final String email ) {

        final String appendQuery = " and oppLog.created_on >= DATE(NOW()) - INTERVAL :dateRange DAY and opportunity.deleted='0'"
                + " order by oppLog.created_on";
        final Query query = entityManagerFactory.createEntityManager()
                .createNativeQuery( STATISTICS_FOR_MB + appendQuery );
        query.setParameter( "mbEmail", email );
        query.setParameter( "dateRange", agentOpportunityBusinessConfig.getFaceToFaceopportunityRange() );
        final List< Object[] > objList = query.getResultList();
        return objList;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dao.AgentReportDao#getAgentStatistics(java.lang.
     * String)
     */
    @Override
    public List< Object[] > getAgentStatistics( final String email ) {

        final String appendQuery = " and  opportunity.deleted='0' order by oppLog.created_on";
        final Query query = entityManagerFactory.createEntityManager()
                .createNativeQuery( STATISTICS_FOR_AGENT + appendQuery );
        query.setParameter( "agentEmail", email );

        final List< Object[] > objList = query.getResultList();
        return objList;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.AgentReportDao#getAgentGridStatistics(java.lang.
     * String)
     */
    @Override
    public List< Object[] > getAgentGridStatistics( final String email ) {

        final String appendQuery = " and oppLog.created_on >= DATE(NOW()) - INTERVAL :dateRange DAY and opportunity.deleted='0'"
                + " order by oppLog.created_on";
        final Query query = entityManagerFactory.createEntityManager()
                .createNativeQuery( STATISTICS_FOR_AGENT + appendQuery );
        query.setParameter( "agentEmail", email );
        query.setParameter( "dateRange", agentOpportunityBusinessConfig.getFaceToFaceopportunityRange() );
        final List< Object[] > objList = query.getResultList();
        return objList;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.AgentReportDao#getFaceToFaceStatistics(java.lang.
     * String)
     */
    @Override
    public List< Object[] > getFaceToFaceStatistics( final String email, final boolean isForAgent ) {

        final Query query;

        final String appendQuery = " group by created_on order by oppLog.created_on";
        if (isForAgent) {
            query = entityManagerFactory.createEntityManager().createNativeQuery( CUMULATIVE_FOR_AGENT + appendQuery );
        } else {
            query = entityManagerFactory.createEntityManager().createNativeQuery( CUMULATIVE_FOR_MB + appendQuery );
        }
        query.setParameter( "email", email );

        final List< Object[] > objList = query.getResultList();
        return objList;
    }

    /**
     * @param email
     * @return
     */
    @Override
    public List< Object[] > downloadFaceToFaceCount( final String email ) {

        final String appendQuery = " group by usr1.email order by oppLog.created_on";
        final Query query;
        query = entityManagerFactory.createEntityManager().createNativeQuery( CUMULATIVE_FOR_MB + appendQuery );
        query.setParameter( "email", email );

        final List< Object[] > objList = query.getResultList();
        return objList;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.AgentReportDao#getFaceToFaceGridStatistics(java.
     * lang.String)
     */
    @Override
    public List< Object[] > getFaceToFaceGridStatistics( final String email, final boolean isForAgent ) {

        String appendQuery = " and oppLog.created_on >= DATE(NOW()) - INTERVAL :dateRange DAY";
        final Query query;
        if (isForAgent) {
            appendQuery = appendQuery + " group by created_on order by oppLog.created_on";
            query = entityManagerFactory.createEntityManager().createNativeQuery( CUMULATIVE_FOR_AGENT + appendQuery );
        } else {
            appendQuery = appendQuery + " group by agent_email order by oppLog.created_on";
            query = entityManagerFactory.createEntityManager().createNativeQuery( CUMULATIVE_FOR_MB + appendQuery );
        }
        query.setParameter( "email", email );
        query.setParameter( "dateRange", agentOpportunityBusinessConfig.getFaceToFaceopportunityRange() );
        final List< Object[] > objList = query.getResultList();
        return objList;
    }

    @Override
    public List< Object[] > getAgentsAndTheirManagingBrokerEmailID( final List< String > agentsEmailList ) {
        final Query query = entityManagerFactory.createEntityManager()
                .createNativeQuery( AGENTS_AND_THEIR_MANAGING_BROKER_EMAIL_ID );
        query.setParameter( "emailIdList", agentsEmailList );
        final List< Object[] > objList = query.getResultList();
        return objList;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.AgentReportDao#getManagingBrokerStatistics(java.
     * lang.String)
     */
    @Override
    public List< Object[] > getExecutiveStatistics( final String email ) {

        final String appendQuery = " where opportunity.deleted='0' order by oppLog.created_on ";
        final Query query = entityManagerFactory.createEntityManager()
                .createNativeQuery( STATISTICS_FOR_EXECUTIVE + appendQuery );

        final List< Object[] > objList = query.getResultList();
        return objList;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.AgentReportDao#getManagingBrokerGridStatistics(
     * java.lang.String)
     */
    @Override
    public List< Object[] > getExecutiveGridStatistics( final String email ) {

        final String appendQuery = " where oppLog.created_on >= DATE(NOW()) - INTERVAL :dateRange DAY and opportunity.deleted='0' "
                + " order by oppLog.created_on ";
        final Query query = entityManagerFactory.createEntityManager()
                .createNativeQuery( STATISTICS_FOR_EXECUTIVE + appendQuery );
        query.setParameter( "dateRange", agentOpportunityBusinessConfig.getFaceToFaceopportunityRange() );
        final List< Object[] > objList = query.getResultList();
        return objList;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.AgentReportDao#getFaceToFaceGridStatistics(java.
     * lang.String)
     */
    @Override
    public List< Object[] > getExecutiveFaceToFaceGrid( final String email ) {

        final String appendQuery = " and oppLog.created_on >= DATE(NOW()) - INTERVAL :dateRange DAY"
                + " group by managingBrokeriD ";
        final Query query = entityManagerFactory.createEntityManager()
                .createNativeQuery( CUMULATIVE_FOR_EXECUTIVE + appendQuery );
        query.setParameter( "dateRange", agentOpportunityBusinessConfig.getFaceToFaceopportunityRange() );
        final List< Object[] > objList = query.getResultList();
        return objList;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.AgentReportDao#getFaceToFaceStatistics(java.lang.
     * String)
     */
    @Override
    public List< Object[] > getExecutiveFaceToFace( final String email ) {

        final String appendQuery = " group by created_on, managingBrokeriD order by oppLog.created_on ";
        final Query query = entityManagerFactory.createEntityManager()
                .createNativeQuery( CUMULATIVE_FOR_EXECUTIVE + appendQuery );

        final List< Object[] > objList = query.getResultList();
        return objList;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.AgentReportDao#getExecutiveFaceToFaceDownload(
     * java.lang.
     * String)
     */
    @Override
    public List< Object[] > getExecutiveFaceToFaceDownload( final String email ) {

        final String appendQuery = " group by managingBrokeriD order by oppLog.created_on ";
        final Query query = entityManagerFactory.createEntityManager()
                .createNativeQuery( CUMULATIVE_FOR_EXECUTIVE + appendQuery );

        final List< Object[] > objList = query.getResultList();
        return objList;
    }

}
