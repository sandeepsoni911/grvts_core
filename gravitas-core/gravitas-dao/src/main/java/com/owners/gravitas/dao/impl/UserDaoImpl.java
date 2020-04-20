package com.owners.gravitas.dao.impl;

import static com.owners.gravitas.util.RestUtil.buildRequest;
import static com.owners.gravitas.util.RestUtil.createHttpHeader;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.dao.UserDao;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.repository.UserRepository;

/**
 * The Class UserDaoImpl provides methods for User data access.
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( UserDaoImpl.class );

    /** The replace string. */
    @Value( value = "${firebase.key.replace.string}" )
    private String replaceString;

    /** The entity manager factory. */
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    
    @Autowired
    private UserRepository userRepository;

    private String GET_SCHEDULED_MEETINGS_DETAILS_TOP = "select if(usr.email like 'disabled_%', REPLACE(usr.email, 'disabled_', '') , usr.email) as agentEmail,"
    		+ " concat(contact.first_name,' ',contact.last_name) as buyerName,"
    		+ " contact.email as buyerEmail, contact.phone as buyerPhoneNo, task.SCHEDULED_DTM AS sheduleDate,"
    		+ " task.LOCATION as location, task.STATUS as status, agent.home_state as agentState,"
            + " contact.state oppState, task.is_deleted as deleted, contact.stage as stage,"
            + " contact.crm_id as opportunityId, usr.status as userstatus, agent.availability as availability "
            + "from gr_user usr " 
            + "inner join gr_agent_detail agent on usr.id = agent.user_id "
            + "inner join GR_OPPORTUNITY opp on opp.assigned_agent_email = if(usr.email like 'disabled_%', REPLACE(usr.email, 'disabled_', '') , usr.email) "
            + "inner join gr_agent_task task on opp.id = task.opportunity_id "
            + "inner join gr_contact contact on contact.id = opp.contact_id " + "where task.SCHEDULED_DTM is not null ";

    private String GET_SCHEDULED_MEETINGS_DETAILS_AGENT_MIDDLE = "and if(usr.email like 'disabled_%', REPLACE(usr.email, 'disabled_', '') , usr.email) = :agentEmail ";

    private String GET_SCHEDULED_MEETINGS_DETAILS_BUYER_MIDDLE = "and contact.email = :buyerEmail ";

    private String DATE_RAGNE = "and task.SCHEDULED_DTM BETWEEN :startDate AND :endDate ";

    // private String DATE_RAGNE_IF_NOT_GIVEN = "and task.SCHEDULED_DTM >=
    // DATE(NOW()) - INTERVAL :dateRange DAY ";
    
    private String GET_SCHEDULED_MEETINGS_DETAILS_BOTTOME = " order by if(usr.email like 'disabled_%', REPLACE(usr.email, 'disabled_', '') , usr.email), task.LAST_UPDATED_ON desc";

    @Override
    public User findById( final String id ){
        return userRepository.findOne(id);
    }
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dao.impl.UserDao#getRoles(java.lang.String)
     */
    @Override
    public Set< String > getRoles( final String email ) {
        final Set< String > roles = new HashSet<>();
        final String reqUrl = buildFirebaseURL( "users/" + email.replace( ".", replaceString ) + "/roles" );
        LOGGER.info("getting roles for email : {} from firebase : {}", email, reqUrl);
        @SuppressWarnings("unchecked")
		final Map< String, Boolean > reposne = getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                Map.class ).getBody();
        if (reposne != null) {
            roles.addAll( reposne.keySet() );
        }
        return roles;
    }

    /**
     * Gets the Schedule Meetings details.
     *
     * @param emailId
     *            the emailId
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @param isAgent
     *            the isAgent
     * @return the Schedule Meetings details
     */
    @Override
    public List< Object[] > getScheduleMeetingDetails( final String emailId, final Date[] dates, final Boolean isAgent ) {
        LOGGER.info( "StartDto: Schedule Meeting Details for emailId : {} and isAgent : {}", emailId, isAgent );
        final Query query = getScheduleMeetingsQuery( emailId, dates, isAgent );
        final List< Object[] > objList = query.getResultList();
        LOGGER.info( "EndDto: Schedule Meeting Details for emailId : {} and isAgent : {} ", emailId, isAgent );
        return objList;
    }

    private Query getScheduleMeetingsQuery( final String emailId, final Date[] dates, final Boolean isAgent ) {

        String appendQuery = new String();
        Query query = null;
        if (isAgent == null) {
            appendQuery = GET_SCHEDULED_MEETINGS_DETAILS_TOP + DATE_RAGNE + GET_SCHEDULED_MEETINGS_DETAILS_BOTTOME;
            query = entityManagerFactory.createEntityManager().createNativeQuery( appendQuery );
            query = setDates( dates, query );
        } else if (isAgent) {
            appendQuery = GET_SCHEDULED_MEETINGS_DETAILS_TOP + GET_SCHEDULED_MEETINGS_DETAILS_AGENT_MIDDLE + DATE_RAGNE
                    + GET_SCHEDULED_MEETINGS_DETAILS_BOTTOME;
            query = entityManagerFactory.createEntityManager().createNativeQuery( appendQuery );
            query = setDates( dates, query );
            query.setParameter( "agentEmail", emailId );
        } else {
            appendQuery = GET_SCHEDULED_MEETINGS_DETAILS_TOP + GET_SCHEDULED_MEETINGS_DETAILS_BUYER_MIDDLE + DATE_RAGNE
                    + GET_SCHEDULED_MEETINGS_DETAILS_BOTTOME;
            query = entityManagerFactory.createEntityManager().createNativeQuery( appendQuery );
            query = setDates( dates, query );
            query.setParameter( "buyerEmail", emailId );
        }
        return query;
    }

    private Query setDates( final Date[] dates, final Query query ) {
        if (dates != null) {
            LOGGER.info( "Dates are startDate :{} and endDate: {}", dates[0], dates[1] );
            query.setParameter( "startDate", dates[0] );
            query.setParameter( "endDate", dates[1] );
        }
        return query;
    }
}

