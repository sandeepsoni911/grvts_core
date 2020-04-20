package com.owners.gravitas.service;

import java.util.List;

import com.owners.gravitas.domain.entity.ContactActivity;

/**
 * The Interface ContactActivityService.
 * 
 * @author pabhishek
 */
public interface ContactActivityService {

    /**
     * Save.
     *
     * @param contactActivity
     *            the contact activity
     * @return the contact activity
     */
    ContactActivity save( ContactActivity contactActivity );
    
    /**
     * Gets the contact activity.
     *
     * @param userId
     *            the userId
     * @return the contact activity
     */
    List<ContactActivity> getContactActivityByUserId( String userId );

}
