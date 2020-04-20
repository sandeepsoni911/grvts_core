package com.owners.gravitas.service;

import com.owners.gravitas.domain.entity.EmailScrappingDetails;

/**
 * Service for emailScrappingDetails
 * @author sandeepsoni
 *
 */
public interface EmailScrappingDetailService {
	
	/**
	 * To Save emailScrapping details
	 * @param emailScrappingDetails
	 * @return EmailScrappingDetails
	 */
	EmailScrappingDetails save(EmailScrappingDetails emailScrappingDetails);

}
