package com.owners.gravitas.dao;

import java.util.List;
import java.util.Set;

public interface ContactDao {
	/**
	 * find SaveSearch Status
	 * @param contactId
	 * @return
	 */
	List< Object[] > findSaveSearchStatus( final String contactId );
	
	/**
	 * find All Contacts By Fb OpportunityId
	 * 
	 * @param contactId
	 * @return
	 */
	List< Object[] > findAllContactsByFbOpportunityId( Set<String> fbOpportunityId );
}
