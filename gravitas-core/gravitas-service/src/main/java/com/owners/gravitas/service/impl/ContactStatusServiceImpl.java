package com.owners.gravitas.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.owners.gravitas.domain.entity.ContactStatus;
import com.owners.gravitas.repository.ContactStatusRepository;
import com.owners.gravitas.service.ContactStatusService;

/**
 * Implementation class for ContactStatusService
 * @author sandeepsoni
 *
 */
@Service
public class ContactStatusServiceImpl implements ContactStatusService {
	
	/** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( ContactStatusServiceImpl.class );

    @Autowired
    ContactStatusRepository contactStatusRepository;

	@Override
	public ContactStatus save(ContactStatus contactStatusRequest) {
		LOGGER.info("inside save method of ContacStatusServiceImpl for email : {}", contactStatusRequest.getEmail());
		return contactStatusRepository.save(contactStatusRequest);
	}
	
	public List< ContactStatus > getContactStatusUnderRetryCount(@Param( "retryCount" )int retryCount)
	{
		LOGGER.info("inside save method of ContacStatusServiceImpl for retryCount : {}", retryCount);
		return contactStatusRepository.getContactStatusUnderRetryCount(retryCount);
	}
	
	public ContactStatus findById(String id)
	{
		LOGGER.info("inside save method of ContacStatusServiceImpl for id : {}", id);
		return contactStatusRepository.findById(id);
	}
	
	public ContactStatus findByEmail(String email)
	{
		LOGGER.info("inside save method of ContacStatusServiceImpl for email : {}", email);
		return contactStatusRepository.findByEmail(email);
	}

}
