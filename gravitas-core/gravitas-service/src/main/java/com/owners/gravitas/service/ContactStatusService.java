package com.owners.gravitas.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.owners.gravitas.domain.entity.ContactStatus;

/**
 * The Interface ContactStatusService
 * @author sandeepsoni
 *
 */
public interface ContactStatusService {
	
	/**
	 * To save contact status object
	 * @param contactStatusRequest
	 * @return
	 */
	ContactStatus save(ContactStatus contactStatusRequest);
	
	List< ContactStatus > getContactStatusUnderRetryCount(@Param( "retryCount" )int retryCount);
	
	ContactStatus findById(String id);
	
	ContactStatus findByEmail(String email);

}
