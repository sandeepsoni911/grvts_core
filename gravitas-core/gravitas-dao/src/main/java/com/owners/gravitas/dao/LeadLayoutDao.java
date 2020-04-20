package com.owners.gravitas.dao;

public interface LeadLayoutDao {

	/**
	 * Get LeadLayout
	 * @param emailId
	 * @param type
	 * @param source
	 * @return layout
	 */
	String getLeadLayout(String emailId, String type, String source);
	
	/**
	 * Get LeadLayout
	 * @param emailId
	 * @param type
	 * @param source
	 * @param layout
	 * @return
	 */
	void saveOrUpdateLeadLayout(String emailId, String type, String source, String layout);
	
}
