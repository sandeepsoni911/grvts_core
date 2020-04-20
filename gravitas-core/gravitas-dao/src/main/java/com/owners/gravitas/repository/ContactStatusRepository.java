package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.owners.gravitas.domain.entity.ContactStatus;
/**
 * ContactStatus repository
 * @author sandeepsoni
 *
 */
public interface ContactStatusRepository extends JpaRepository< ContactStatus, String > 
{
	final static String FIND_CONTACT_STATUS_UNDER_RETRY_COUNT = "select * from gr_contact_status where last_updated_by < (sysdate() - 1) and "
			+ "status = 'Failed' and retry_count < :retryCount order by created_on asc";
	
	@Query( value = FIND_CONTACT_STATUS_UNDER_RETRY_COUNT, nativeQuery = true )
	List< ContactStatus > getContactStatusUnderRetryCount(@Param( "retryCount" )int retryCount);
	
	ContactStatus findById(String id);
	
	ContactStatus findByEmail(String email);

}
