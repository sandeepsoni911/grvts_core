package com.owners.gravitas.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.ContactActivity;
import com.owners.gravitas.repository.ContactActivityRepository;
import com.owners.gravitas.service.ContactActivityService;

/**
 * The Class ContactActivityServiceImpl.
 * 
 * @author pabhishek
 */
@Service
@Transactional( readOnly = true )
public class ContactActivityServiceImpl implements ContactActivityService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( ContactActivityServiceImpl.class );

    /** The contact activity repository. */
    @Autowired
    private ContactActivityRepository contactActivityRepository;

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.ContactActivityService#save(com.owners.
     * gravitas.domain.entity.ContactActivity)
     */
    @Override
    public ContactActivity save( final ContactActivity contactActivity ) {
        return contactActivityRepository.save( contactActivity );
    }


    @Override
    public List< ContactActivity > getContactActivityByUserId( String userId ) {
        return contactActivityRepository.findByOwnersComIdOrderByCreatedDateDesc( userId );
    }
}
