package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.owners.gravitas.domain.entity.User;

/**
 * The Interface UserRepository.
 *
 * @author pabhishek
 */
public interface UserRepository extends JpaRepository< User, String > {

    /** The get users by role. */
    String GET_USERS_BY_ROLE = "SELECT user FROM GR_USER user,GR_ROLE_MEMBER roleMember,GR_ROLE role WHERE role.name =:role "
            + "AND role= roleMember.role AND roleMember.user =user";

    /** The get users by role and status. */
    String GET_USERS_BY_ROLE_AND_STATUS = "SELECT user FROM GR_USER user,GR_ROLE_MEMBER roleMember,GR_ROLE role WHERE role.name =:role "
            + "AND role= roleMember.role AND roleMember.user =user and user.status in (:statuses) ";

    /** The get agents by zipcode. */
    String GET_AGENTS_BY_ZIPCODE = "SELECT gu.* FROM gr_user gu INNER JOIN gr_agent_detail gad "
            + "ON gu.id = gad.user_id INNER JOIN gr_agent_coverage gac ON gad.id = gac.agent_detail_id "
            + "WHERE gu.status = 'active' AND gac.availability=1 AND gac.servable = TRUE AND gac.zip = :zipcode ORDER BY gad.score DESC";

    String GET_MANAGING_BROKER = "SELECT * FROM gr_user WHERE  id = (SELECT   managing_broker_id  FROM  gr_agent_detail ad    INNER JOIN"
            + "    gr_user u   WHERE  ad.user_id = u.ID   AND u.email = :email)";

    /**
     * Gets the users by role.
     *
     * @param role
     *            the role
     * @return the users by role
     */
    @Query( value = GET_USERS_BY_ROLE )
    public List< User > getUsersByRole( @Param( "role" ) final String role );

    /**
     * Gets the users by role.
     *
     * @param role
     *            the role
     * @param statuses
     *            the statuses
     * @return the users by role
     */
    @Query( value = GET_USERS_BY_ROLE_AND_STATUS )
    public List< User > getUsersByRole( @Param( "role" ) final String role,
            @Param( "statuses" ) final List< String > statuses );

    /**
     * Find by email.
     *
     * @param email
     *            the email
     * @return the user
     */
    public User findByEmail( final String email );

    /**
     * Find by email and status.
     *
     * @param email
     *            the email
     * @param status
     *            the status
     * @return the user
     */
    public User findByEmailAndStatus( final String email, final String status );

    /*
     * (non-Javadoc)
     * @see org.springframework.data.jpa.repository.JpaRepository#findAll()
     */
    public List< User > findAll();

    /**
     * Gets the active agents for zipcode.
     *
     * @param zipcode
     *            the zipcode
     * @return the active agents for zipcode
     */
    @Query( value = GET_AGENTS_BY_ZIPCODE, nativeQuery = true )
    public List< User > findActiveAgentsForZipcode( @Param( "zipcode" ) String zipcode );

    /**
     * Gets the managing broker.
     *
     * @param email
     *            the email
     * @return the managing broker
     */
    @Query( value = GET_MANAGING_BROKER, nativeQuery = true )
    public User getManagingBroker( @Param( "email" ) final String email );

    /**
     * Find by email in.
     *
     * @param emails
     *            the emails
     * @return the list
     */
    public List< User > findByEmailIn( List< String > emails );
}
