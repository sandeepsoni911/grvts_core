package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.util.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.RepositoryTestFactory;
import com.owners.gravitas.domain.entity.ContactStatus;

public class ContactStatusRepositoryTest 
{
	/** The contact status repository. */
    private final ContactStatusRepository contactStatusRepository = RepositoryTestFactory
            .test( ContactStatusRepository.class );

    /**
     * Find by id test.
     */
    @Test
    public void findByIdTest() {
    	contactStatusRepository.findById( null );
    }
    
    @Test
    public void findByEmailTest() {
    	contactStatusRepository.findByEmail( null );
    }
    
    /*@Test
    public void getContactStatusUnderRetryCountTest()
    {
    	List< ContactStatus > cs = contactStatusRepository.getContactStatusUnderRetryCount(1);
    	Assert.notEmpty(cs);
    }*/
}