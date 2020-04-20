package com.owners.gravitas.dao;

/**
 * The Interface OpportunityActionDao.
 *
 * @author raviz
 */
public interface OpportunityActionDao {

    /**
     * Gets the incomplete action flow count.
     *
     * @param agentEmail
     *            the agent email
     * @return the incomplete action flow count
     */
    Object getIncompleteActionFlowCount( String agentEmail );
}
