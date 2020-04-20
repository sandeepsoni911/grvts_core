package com.owners.gravitas.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.owners.gravitas.dao.dto.CbsaAgentDetail;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.User;

/**
 * The Interface AgentDetailsRepository.
 *
 * @author pabhishek
 */
public interface AgentDetailsRepository extends JpaRepository< AgentDetails, String > {

    /** The Constant FIND_ASSIGNED_AGENT_BY_CRM_OPP_ID. */
    public static final String FIND_ASSIGNED_AGENT_BY_CRM_OPP_ID = "select u.email from gr_agent_detail ad inner join gr_user u on u.id=ad.user_id"
            + " inner join GR_OPPORTUNITY o on u.email=o.assigned_agent_email"
            + " inner join gr_contact c on c.id=o.contact_id where o.deleted=false and c.crm_id=:crmOppId";

    /** The Constant FIND_AGENT_DETAILS_BY_EMAIL. */
    final static String FIND_AGENT_DETAILS_BY_EMAIL = "SELECT ad FROM GR_AGENT_DETAIL ad inner join ad.user u WHERE u.email=:email";

    /** The update agent score. */
    String UPDATE_AGENT_SCORE = "UPDATE gr_agent_detail gad, (SELECT gu.id, gu.email AS user_email, vdas.score AS agent_score "
            + "FROM gr_user gu, v_dim_agent_score vdas " + "WHERE vdas.email = gu.email) AS temp "
            + "SET gad.score = temp.agent_score " + "WHERE gad.user_id = temp.id";

    /** The Constant FIND_AGENTS_BY_USER_IDS. */
    final static String FIND_AGENTS_BY_USER_IDS = "select gad from GR_AGENT_DETAIL gad where gad.user IN (:users)";

    /** The Constant GET_CBSA_AGENT_DETAILS. */
    public static final String GET_CBSA_AGENT_DETAILS = "select mez.cbsa, ad.* from ME_ZIPCBSA mez inner join CUBE_AGENT_ELIGIBLE_ZIPS_V1 c on c.zip=mez.zip "
            + " inner join GR_USER u on u.email=c.email inner join GR_AGENT_DETAIL ad "
            + " on ad.user_id=u.id where u.email in :emails";

    /** The Constant FIND_ZIP_AND_AGENTS_BY_ZIPS. */
    public static final String FIND_ZIP_AND_AGENTS_BY_ZIPS = "select c.zip,u.email,dim.happy_agents_score from GR_AGENT_DETAIL ad inner join GR_USER u on u.id=ad.user_id "
            + " inner join CUBE_AGENT_ELIGIBLE_ZIPS_V1 c on u.email=c.email inner join V_DIM_AGENT_SCORE dim on dim.email=u.email "
            + " where c.zip = :zip";
    
    /** The Constant FIND_BY_ZIP_AND_EMAILS. */
    public static final String FIND_BY_ZIP_AND_EMAILS = "select c.zip,u.email,dim.happy_agents_score from GR_AGENT_DETAIL ad inner join GR_USER u on u.id=ad.user_id "
            + " inner join CUBE_AGENT_ELIGIBLE_ZIPS_V1 c on u.email=c.email inner join V_DIM_AGENT_SCORE dim on dim.email=u.email "
            + " where c.zip = :zip and u.email in :emails";

    /** The Constant FIND_BY_EMAILS_IN. */
    final static String FIND_BY_EMAILS_IN = "SELECT ad FROM GR_AGENT_DETAIL ad inner join ad.user u WHERE u.email in :emails";
    
    
    /** The Constant FIND_AVAILABLE_AGENTS_BY_EMAIL AGENTS. */
    final static String FIND_UNAVAILABLE_AGENTS_BY_EMAIL = "SELECT ad.user.email FROM GR_AGENT_DETAIL ad WHERE ad.user.email in :emails and ad.availability=0";

    /**
     * Find agent by id.
     *
     * @param email
     *            the email
     * @return the agent details
     */
    @Query( value = FIND_AGENT_DETAILS_BY_EMAIL )
    AgentDetails findAgentByEmail( @Param( "email" ) final String email );

    /**
     * Find by user.
     *
     * @param user
     *            the user
     * @return the agent details
     */
    AgentDetails findByUser( final User user );

    /**
     * Gets the users by managing broker and status.
     *
     * @param managingBroker
     *            the managing broker
     * @return the users by managing broker and status
     */
    List< AgentDetails > getAgentsByManagingBroker( com.owners.gravitas.domain.entity.User managingBroker );

    /**
     * Update agent score.
     */
    @Modifying
    @Query( value = UPDATE_AGENT_SCORE, nativeQuery = true )
    public void syncAgentScore();

    /**
     * Find agents by users.
     *
     * @param users
     *            the users
     * @return the list
     */
    @Query( value = FIND_AGENTS_BY_USER_IDS )
    List< AgentDetails > getAgentByUsers( @Param( "users" ) List< User > users );

    /**
     * Find agents by zips.
     *
     * @param zips
     *            the zips
     * @return the sets the
     */
    @Query( nativeQuery = true, value = FIND_ZIP_AND_AGENTS_BY_ZIPS )
    List< Object[] > findZipAndAgentsByZip( @Param( "zip" ) String zip );

    /**
     * Find zip and agents by zip.
     *
     * @param zip
     *            the zip
     * @param emails
     *            the emails
     * @return the list
     */
    @Query( nativeQuery = true, value = FIND_BY_ZIP_AND_EMAILS )
    List< Object[] > findByZipAndAgentEmails( @Param( "zip" ) String zip,
            @Param( "emails" ) Collection< String > emails );

    /**
     * Find agent by crm opportunity id.
     *
     * @param crmOppId
     *            the crm opp id
     * @return the string
     */
    @Query( nativeQuery = true, value = FIND_ASSIGNED_AGENT_BY_CRM_OPP_ID )
    String findAgentByCrmOpportunityId( @Param( "crmOppId" ) String crmOppId );

    /**
     * Gets the cbsa agent details.
     *
     * @param emails
     *            the emails
     * @return the cbsa agent details
     */
    @Query( nativeQuery = true, value = GET_CBSA_AGENT_DETAILS )
    List< CbsaAgentDetail > getCbsaAgentDetails( @Param( "emails" ) Collection< String > emails );

    /**
     * Find by emails in.
     *
     * @param emails
     *            the emails
     * @return the list
     */
    @Query( value = FIND_BY_EMAILS_IN )
    List< AgentDetails > findByEmailsIn( @Param( "emails" ) final Collection< String > emails );
    
    
    @Query(value = FIND_UNAVAILABLE_AGENTS_BY_EMAIL )
    List< String > findUnAvailableAgentsByEmailsIn( @Param( "emails" ) final Collection< String > emails );
}
