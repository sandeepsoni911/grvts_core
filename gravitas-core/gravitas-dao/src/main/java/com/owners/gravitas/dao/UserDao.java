package com.owners.gravitas.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.owners.gravitas.domain.entity.User;

/**
 * The Interface UserDao.
 */
public interface UserDao {

    /**
     * Gets the roles.
     *
     * @param email the email
     * @return the roles
     */
    Set< String > getRoles( String email );
    
    /**
     * Gets the Schedule Meetings details.
     *
     * @param emailId
     *            the emailId
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @param isAgent
     *            the isAgent
     * @return the Schedule Meetings details
     */
    List< Object[] > getScheduleMeetingDetails( String emailId, Date[] dates, Boolean isAgent );

    User findById(String id);

}
