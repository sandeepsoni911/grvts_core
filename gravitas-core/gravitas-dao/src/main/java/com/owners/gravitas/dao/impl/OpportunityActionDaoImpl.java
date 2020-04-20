package com.owners.gravitas.dao.impl;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.dao.OpportunityActionDao;

/**
 * The Class OpportunityActionDaoImpl.
 *
 * @author raviz
 */
@Repository
public class OpportunityActionDaoImpl implements OpportunityActionDao {

    /** The entity manager factory. */
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    /** The get incomplete action flow count. */
    private String GET_INCOMPLETE_ACTION_FLOW_COUNT = "SELECT COUNT(*)"
            + " FROM gr_opportunity_action oa INNER JOIN GR_OPPORTUNITY opp INNER JOIN gr_contact con"
            + " ON opp.contact_id=con.id AND oa.fb_opportunity_id=opp.fb_opportunity_id"
            + " WHERE opp.assigned_agent_email= :agentEmail AND con.type='BUYER' AND opp.deleted=false AND oa.deleted=false AND oa.completed=false";

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.OpportunityActionDao#getIncompleteActionFlowCount
     * (java.lang.String)
     */
    @Override
    public Object getIncompleteActionFlowCount( final String agentEmail ) {
        final Query query = entityManagerFactory.createEntityManager()
                .createNativeQuery( GET_INCOMPLETE_ACTION_FLOW_COUNT );
        query.setParameter( "agentEmail", agentEmail );
        return query.getSingleResult();
    }

}
