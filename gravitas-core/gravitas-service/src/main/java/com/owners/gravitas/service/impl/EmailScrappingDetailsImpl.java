package com.owners.gravitas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.domain.entity.EmailScrappingDetails;
import com.owners.gravitas.repository.EmailScrappingDetailsRepository;
import com.owners.gravitas.service.EmailScrappingDetailService;

/**
 * Implementation class for EmailScrappingDetails
 * @author sandeepsoni
 *
 */
@Service
public class EmailScrappingDetailsImpl implements EmailScrappingDetailService {
	
	@Autowired
	private EmailScrappingDetailsRepository emailScrappingDetailsRepository;

	@Override
	public EmailScrappingDetails save(EmailScrappingDetails emailScrappingDetails) {
		return emailScrappingDetailsRepository.save(emailScrappingDetails);
	}

}
