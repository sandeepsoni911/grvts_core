/**
 * 
 */
package com.owners.gravitas.business.impl;

import javax.mail.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.business.EmailScrappingDetailBusinessService;
import com.owners.gravitas.business.builder.EmailScrappingDetailBuilder;
import com.owners.gravitas.domain.entity.EmailScrappingDetails;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.EmailScrappingDetailService;

/**
 * Implementation call
 * @author sandeepsoni
 *
 */
@Service
public class EmailScrappingDetailBusinessServiceImpl implements EmailScrappingDetailBusinessService {
	
	 /** The  EmailScrappingDetailBuilder */
    @Autowired
    EmailScrappingDetailBuilder emailScrappingDetailBuilder;
    
    /** The  EmailScrappingDetailService */
    @Autowired
    EmailScrappingDetailService emailScrappingDetailService;
    
    
	@Override
	@Transactional
	public void scrapeEmailDetails(Message message) throws ApplicationException {
		EmailScrappingDetails emailScrappingDetails = emailScrappingDetailBuilder.convertTo(message);
		emailScrappingDetailService.save(emailScrappingDetails);
	}
	
}
