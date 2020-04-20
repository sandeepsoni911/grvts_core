package com.owners.gravitas.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.Opportunity;

/**
 * The Interface ContactRepository.
 *
 * @author shivamm
 */
public interface ContactRepository extends JpaRepository< Contact, String > {

    /** The Constant FIND_REVENUE_BY_AGENT_EMAILS. */
    final static String FIND_REVENUE_BY_AGENT_EMAILS = "SELECT sum(gct1.value) AS revenue, temp1.email AS email,temp1.score as score,temp1.status as status "
            + "FROM gr_contact gc1,(SELECT temp.id AS id, temp.email AS email,temp.score as score,temp.status as status FROM gr_contact gc,"
            + "(SELECT gov.contact_id AS id,gov.assigned_agent_email     AS email,gad.score as score,gr.status as status FROM GR_OPPORTUNITY gov "
            + "INNER JOIN gr_opportunity_stage_log gosl ON gov.id = gosl.opportunity_id INNER JOIN gr_user gr ON gov.assigned_agent_email = gr.email "
            + "INNER JOIN gr_agent_detail gad ON gr.id = gad.user_id WHERE gosl.stage = 'Sold' AND gov.deleted = 0 AND gr.status IN ('active', 'hold')) temp,"
            + "gr_contact_attr gct,gr_object_attr_conf goac WHERE gc.id = temp.id AND gct.contact_id = gc.id AND gct.object_attr_conf_id = goac.id "
            + "AND goac.attr_name = 'actualClosingDate' AND STR_TO_DATE(gct.value, '%Y-%m-%d') BETWEEN STR_TO_DATE( :fromDate,'%Y-%m-%d') AND STR_TO_DATE("
            + ":toDate,'%Y-%m-%d')) temp1,gr_contact_attr gct1,gr_object_attr_conf goac1 WHERE gc1.id = temp1.id AND gct1.contact_id = gc1.id "
            + "AND gct1.object_attr_conf_id = goac1.id AND goac1.attr_name = 'salesPrice' GROUP BY temp1.email ORDER BY revenue DESC limit :rankFilter";

    /** The Constant FIND_REVENUE_BY_AGENT_EMAILS_WITHOUT_FILTER. */
    final static String FIND_REVENUE_BY_AGENT_EMAILS_WITHOUT_FILTER = "SELECT sum(gct1.value) AS revenue, temp1.email AS email,temp1.score as score,temp1.status as status "
            + "FROM gr_contact gc1,(SELECT temp.id AS id, temp.email AS email,temp.score as score,temp.status as status FROM gr_contact gc,"
            + "(SELECT gov.contact_id AS id,gov.assigned_agent_email     AS email,gad.score as score,gr.status as status FROM GR_OPPORTUNITY gov "
            + "INNER JOIN gr_opportunity_stage_log gosl ON gov.id = gosl.opportunity_id INNER JOIN gr_user gr ON gov.assigned_agent_email = gr.email "
            + "INNER JOIN gr_agent_detail gad ON gr.id = gad.user_id WHERE gosl.stage = 'Sold' AND gov.deleted = 0 AND gr.status IN ('active', 'hold')) temp,"
            + "gr_contact_attr gct,gr_object_attr_conf goac WHERE gc.id = temp.id AND gct.contact_id = gc.id AND gct.object_attr_conf_id = goac.id "
            + "AND goac.attr_name = 'actualClosingDate' AND STR_TO_DATE(gct.value, '%Y-%m-%d') BETWEEN STR_TO_DATE( :fromDate,'%Y-%m-%d') AND STR_TO_DATE("
            + ":toDate,'%Y-%m-%d')) temp1,gr_contact_attr gct1,gr_object_attr_conf goac1 WHERE gc1.id = temp1.id AND gct1.contact_id = gc1.id "
            + "AND gct1.object_attr_conf_id = goac1.id AND goac1.attr_name = 'salesPrice' GROUP BY temp1.email ORDER BY revenue DESC";

    /** The Constant FIND_REVENUE_BY_AGENT_EMAIL. */
    final static String FIND_REVENUE_BY_AGENT_EMAIL = "SELECT sum(gct1.value) AS revenue, temp1.email AS email "
            + "FROM gr_contact gc1,(SELECT temp.id AS id, temp.email AS email FROM gr_contact gc,(SELECT gov.contact_id AS id,"
            + "gov.assigned_agent_email     AS email FROM GR_OPPORTUNITY gov INNER JOIN gr_opportunity_stage_log gosl "
            + "ON gov.id = gosl.opportunity_id WHERE     gosl.stage = 'Sold' AND gov.deleted = 0 AND gov.assigned_agent_email ="
            + ":agentEmail) temp,gr_contact_attr gct,gr_object_attr_conf goac WHERE gc.id = temp.id "
            + "AND gct.contact_id = gc.id AND gct.object_attr_conf_id = goac.id AND goac.attr_name = 'actualClosingDate' AND "
            + "STR_TO_DATE(gct.value, '%Y-%m-%d') BETWEEN STR_TO_DATE(:fromDate,'%Y-%m-%d')AND STR_TO_DATE("
            + ":toDate,'%Y-%m-%d'))temp1,gr_contact_attr        gct1,gr_object_attr_conf    goac1 WHERE     gc1.id = temp1.id "
            + "AND gct1.contact_id = gc1.id AND gct1.object_attr_conf_id = goac1.id AND goac1.attr_name = 'salesPrice' GROUP BY temp1.email "
            + "ORDER BY revenue DESC";

    /** The Constant FIND_CONTACT_BY_STAGE_AND_OBJECT_TYPE. */
    final static String FIND_CONTACT_BY_STAGE_AND_OBJECT_TYPE = "select * from gr_contact gc inner join gr_object go on gc.object_id=go.id "
            + "inner join gr_process gp on gp.email=gc.email where gp.process_code='LEAD_MANAGEMENT_PROCESS' and gp.status='active' "
            + "and gc.stage=:stage and go.name=:objectType";
    
	final static String FIND_CONTACT_BY_FB_OPPORTUNITY_ID = "select c.* from gr_contact c inner join GR_OPPORTUNITY op on c.id = op.contact_id "
			+ "where op.fb_opportunity_id=:fbOpportunityId";
	
	final static String FIND_ALL_CONTACT_BY_FB_OPPORTUNITY_ID = "select * from gr_contact c inner join GR_OPPORTUNITY op on c.id = op.contact_id "
			+ "where op.fb_opportunity_id in :fbOpportunityIds";
	
    String GET_ALL_PUBLIC_LEADS = "select * from GR_CONTACT gc join GR_OBJECT ot on gc.OBJECT_ID=ot.ID where ot.name='lead' and gc.type='Buyer' and gc.ASSIGN_INSIDE_SALES is null  ";
    String GET_ALL_PUBLIC_LEADS_COUNT= "select count(*) from GR_CONTACT gc join GR_OBJECT ot on gc.OBJECT_ID=ot.ID where ot.name='lead' and gc.type='Buyer' and gc.ASSIGN_INSIDE_SALES is null  ";
    
    String GET_MY_LEADS = "select * from GR_CONTACT gContact join GR_OBJECT gObject on gContact.OBJECT_ID=gObject.ID where gObject.name=:type and gContact.type='Buyer' and gContact.ASSIGN_INSIDE_SALES=:emailId ";
    String GET_MY_LEADS_COUNT = "select count(*) from GR_CONTACT gContact join GR_OBJECT gObject on gContact.OBJECT_ID=gObject.ID where gObject.name=:type and gContact.type='Buyer' and gContact.ASSIGN_INSIDE_SALES=:emailId ";
    
    String SORT_LEADS_BY_SCORE_ASC = "ORDER BY cast(buyer_lead_score as decimal(14,7)) asc \n#pageable\n";
    String SORT_LEADS_BY_SCORE_DESC = "ORDER BY cast(buyer_lead_score as decimal(14,7)) desc \n#pageable\n";
    
    
    /**
     * Find by crm id.
     *
     * @param crmId
     *            the crm id
     * @return the contact
     */
    Contact findByCrmId( final String crmId );

    /**
     * Find by email and type and stage not order by created date asc.
     *
     * @param email
     *            the email
     * @param recordType
     *            the record type
     * @param stage
     *            the stage
     * @return the list
     */
    List< Contact > findByEmailAndTypeAndStageNotOrderByCreatedDateAsc( final String email, final String recordType, final String stage );

    /**
     * Gets the revenue by agents.
     *
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @param rankFilter
     *            the rank filter
     * @return the revenue by agents
     */
    @Query( value = FIND_REVENUE_BY_AGENT_EMAILS, nativeQuery = true )
    List< Object[] > getRevenueByAgents( @Param( "fromDate" ) String fromDate, @Param( "toDate" ) String toDate,
            @Param( "rankFilter" ) int rankFilter );

    /**
     * Gets the revenue by agents.
     *
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @return the revenue by agents
     */
    @Query( value = FIND_REVENUE_BY_AGENT_EMAILS_WITHOUT_FILTER, nativeQuery = true )
    List< Object[] > getRevenueByAgents( @Param( "fromDate" ) String fromDate, @Param( "toDate" ) String toDate );

    /**
     * Gets the revenue by agent.
     *
     * @param agentEmail
     *            the agent email
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @return the revenue by agent
     */
    @Query( value = FIND_REVENUE_BY_AGENT_EMAIL, nativeQuery = true )
    List< Object[] > getRevenueByAgent( @Param( "agentEmail" ) String agentEmail, @Param( "fromDate" ) String fromDate,
            @Param( "toDate" ) String toDate );

    /**
     * Find by ownersComId and type.
     *
     * @param ownersComId
     *            the ownersComId
     * @param type
     *            type
     * @return the set of Contact
     */
    Set< Contact > findByOwnersComIdAndType( final String ownersComId, final String type );

    /**
     * Find by ownersCom id.
     *
     * @param ownersComId
     *            the ownersCom id
     * @return the set of Contact
     */
    Set< Contact > findByOwnersComId( final String ownersComId );

    /**
     * Gets the contact by stage and object type.
     *
     * @param stage
     *            the stage
     * @param objectType
     *            the object type
     * @return the contact by stage and object type
     */
    @Query( nativeQuery = true, value = FIND_CONTACT_BY_STAGE_AND_OBJECT_TYPE )
    List< Contact > getContactByStageAndObjectType( @Param( "stage" ) String stage,
            @Param( "objectType" ) String objectType );

    /**
     * Find by email.
     *
     * @param eamil
     *            the email id
     * @return the set of Contact
     */
    Set< Contact > findByEmail( final String email );

    /**
     * Find by opportunities in.
     * Set< Contact > findByEmail( final String email );
     *
     * /**
     * Find by opportunities.
     *
     * @param opportunities
     *            the opportunities
     * @return the contact
     */
    Contact findByOpportunitiesIn( Set< Opportunity > opportunities );

    /**
     * Find by opportunities.
     *
     * @param opportunities
     *            the opportunities
     * @return the contact
     */
    Contact findByOpportunities( Set< Opportunity > opportunities );

    /**
     * Find by email and type.
     *
     * @param email
     *            the email
     * @param type
     *            the type
     * @return the contact
     */
    Contact findByEmailAndType( final String email, final String type );
    
    /**
     * Find contact by fb opportunity id
     * 
     * @param fbOpportunityId
     * @return
     */
    @Query( nativeQuery = true, value = FIND_CONTACT_BY_FB_OPPORTUNITY_ID )
    Contact getContactByFbOpportunityId( @Param( "fbOpportunityId" ) String fbOpportunityId);
    
    @Query( nativeQuery = true, value = FIND_ALL_CONTACT_BY_FB_OPPORTUNITY_ID )
    List<Contact> getAllContactByFbOpportunityId(@Param( "fbOpportunityIds" ) Set<String> fbOpportunityIds);
    
    /**
     * Get all public available leads
     * @param pageable
     * @return
     */
    @Query("select contact from GR_CONTACT contact join contact.objectType objType where objType.name='lead' and contact.type='Buyer' and contact.assignInsideSales is null")
    Page< Contact > findAllPublicLeads(Pageable pageable);
    
    /**
     * Get all public available leads sorted by buyer lead score in ascending
     * order
     * 
     * Note: This query is written separately as the buyer_lead_score column is
     * of type varchar and sorting is not happening properly as values are
     * decimals
     * 
     * @param pageable
     * @return
     */
    @Query( nativeQuery = true, value = GET_ALL_PUBLIC_LEADS
            + SORT_LEADS_BY_SCORE_ASC, countQuery = GET_ALL_PUBLIC_LEADS_COUNT )
    Page< Contact > findAllPublicLeadsByScoreAsc( Pageable pageable );
    
    
    /**
     * Get all public available leads sorted by buyer lead score in descending
     * order
     * 
     * Note: This query is written separately as the buyer_lead_score column is
     * of type varchar and sorting is not happening properly as values are
     * decimals
     * 
     * @param pageable
     * @return
     */
    @Query( nativeQuery = true, value = GET_ALL_PUBLIC_LEADS
            + SORT_LEADS_BY_SCORE_DESC, countQuery = GET_ALL_PUBLIC_LEADS_COUNT )
    Page< Contact > findAllPublicLeadsByScoreDesc( Pageable pageable );
    
    @Query( "select new GR_CONTACT(contact.createdDate,contact.crmId,contact.email,contact.phone,contact.company,contact.source,contact.ownersComId,contact.stage,contact.type,contact.state,contact.buyerLeadScore,contact.assignInsideSales,CASE WHEN contact.firstName IS NULL THEN trim(concat(' ',contact.lastName)) ELSE trim(concat(contact.firstName,' ',contact.lastName)) END as name) from GR_CONTACT contact join contact.objectType objType where objType.name='lead' and contact.type='Buyer' and contact.assignInsideSales is null order by name asc" )
    Page< Contact > findAllPublicLeadsByLeadNameAsc( Pageable pageable );
    
    @Query( "select new GR_CONTACT(contact.createdDate,contact.crmId,contact.email,contact.phone,contact.company,contact.source,contact.ownersComId,contact.stage,contact.type,contact.state,contact.buyerLeadScore,contact.assignInsideSales,CASE WHEN contact.firstName IS NULL THEN trim(concat(' ',contact.lastName)) ELSE trim(concat(contact.firstName,' ',contact.lastName)) END as name) from GR_CONTACT contact join contact.objectType objType where objType.name='lead' and contact.type='Buyer' and contact.assignInsideSales is null order by name desc" )
    Page< Contact > findAllPublicLeadsByLeadNameDesc( Pageable pageable );
    
    @Query("select contact from GR_CONTACT contact join contact.objectType objType where objType.name=:type and contact.type='Buyer' and contact.assignInsideSales=:emailId")
    Page< Contact > findAllMyLeads(Pageable pageable, @Param( "emailId" ) String emailId, @Param( "type" ) String type);
    
    /**
     * Get all available leads for a given inside sales sorted by buyer lead score in ascending
     * order
     * 
     * Note: This query is written separately as the buyer_lead_score column is
     * of type varchar and sorting is not happening properly as values are
     * decimals
     * 
     * @param pageable
     * @return
     */
    @Query( nativeQuery = true, value = GET_MY_LEADS + SORT_LEADS_BY_SCORE_ASC, countQuery = GET_MY_LEADS_COUNT )
    Page< Contact > findAllMyLeadsByScoreAsc( Pageable pageable, @Param( "emailId" ) String emailId, @Param( "type" ) String type );
        
    /**
     * Get all available leads for a given inside sales sorted by buyer lead score in descending
     * order
     * 
     * Note: This query is written separately as the buyer_lead_score column is
     * of type varchar and sorting is not happening properly as values are
     * decimals
     * 
     * @param pageable
     * @return
     */
    @Query( nativeQuery = true, value = GET_MY_LEADS + SORT_LEADS_BY_SCORE_DESC, countQuery = GET_MY_LEADS_COUNT )
    Page< Contact > findAllMyLeadsByScoreDesc( Pageable pageable, @Param( "emailId" ) String emailId, @Param( "type" ) String type );
    
    @Query( "select new GR_CONTACT(contact.createdDate,contact.crmId,contact.email,contact.phone,contact.company,contact.source,contact.ownersComId,contact.stage,contact.type,contact.state,contact.buyerLeadScore,contact.assignInsideSales,CASE WHEN contact.firstName IS NULL THEN trim(concat(' ',contact.lastName)) ELSE trim(concat(contact.firstName,' ',contact.lastName)) END as name) from GR_CONTACT contact join contact.objectType objType where objType.name=:type and contact.type='Buyer' and contact.assignInsideSales=:emailId order by name asc" )
    Page< Contact > findAllMyLeadsByLeadNameAsc( Pageable pageable, @Param( "emailId" ) String emailId, @Param( "type" ) String type );
    
    @Query( "select new GR_CONTACT(contact.createdDate,contact.crmId,contact.email,contact.phone,contact.company,contact.source,contact.ownersComId,contact.stage,contact.type,contact.state,contact.buyerLeadScore,contact.assignInsideSales,CASE WHEN contact.firstName IS NULL THEN trim(concat(' ',contact.lastName)) ELSE trim(concat(contact.firstName,' ',contact.lastName)) END as name) from GR_CONTACT contact join contact.objectType objType where objType.name=:type and contact.type='Buyer' and contact.assignInsideSales=:emailId order by name desc" )
    Page< Contact > findAllMyLeadsByLeadNameDesc( Pageable pageable, @Param( "emailId" ) String emailId, @Param( "type" ) String type );

    @Query( "select c from GR_CONTACT c join c.objectType objType where objType.name= ?2 and c.crmId = ?1" )
    List< Contact > getCrmIdAndDeletedIsFalse( final String crmId, final String type );
    
    @Query( "select c from GR_CONTACT c join c.objectType objType where objType.name='opportunity' and c.crmId = ?1" )
    List< Contact > getOpportunityByCrmId( final String crmId );
    
    /**
     * Get the additional lead attributes from the gr_object_attr_conf &
     * gr_contact_attr table for given set of crm ids
     * 
     * @param crmIdList
     * @return
     */
    @Query( nativeQuery = true, value = "select oac.attr_name,ca.value, gc.crm_id from gr_contact_attr ca join gr_object_attr_conf oac on ca.object_attr_conf_id = oac.id join  gr_object o on o.id = oac.object_id join gr_contact gc on ca.contact_id = gc.id where  gc.crm_id in :crmIdList and o.name='lead' " )
    List< Object[] > getContactAttributesDataForCrmId( @Param( "crmIdList" ) Set< String > crmIdList );
    
    @Query( nativeQuery = true, value = "select oac.attr_name,ca.value, gc.crm_id from gr_contact_attr ca join gr_object_attr_conf oac on ca.object_attr_conf_id = oac.id join  gr_object o on o.id = oac.object_id join gr_contact gc on ca.contact_id = gc.id where  gc.crm_id in :crmIdList " )
    List< Object[] > getOpportunityContactAttributesDataForCrmId( @Param( "crmIdList" ) Set< String > crmIdList );
    
    Set<Contact> findTop2ByPhoneOrderByLastModifiedDateDesc(final String phone);
    
    Set< Contact > findTop2ByPhoneEndingWithOrderByCreatedDateDesc( final String phone );
    
    @Query( "select c from GR_CONTACT c join c.objectType objType where objType.name='lead' and c.type='Buyer' and c.ownersComId is null  and c.source not in ('Zillow Hotline') and c.createdDate BETWEEN STR_TO_DATE(:fromDate,'%Y-%m-%d') AND STR_TO_DATE(:toDate,'%Y-%m-%d') order by c.createdDate asc" )
    List< Contact > findLeadsByOwnersComIdIsNull( @Param( "fromDate" ) String fromDate, @Param( "toDate" ) String toDate );
    
    @Query( "select c from GR_CONTACT c join c.objectType objType where objType.name='lead' and c.type='Buyer' and c.ownersComId is null and c.source not in ('Zillow Hotline') order by c.createdDate asc" )
    List< Contact > findAllLeadsByOwnersComIdIsNull();
}
