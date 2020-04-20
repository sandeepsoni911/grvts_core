package com.owners.gravitas.service;

import java.util.List;

import com.owners.gravitas.domain.entity.ContactLog;

public interface ContactLogService {

    /**
     * Save.
     *
     * @param prospect
     *            the contact
     * @return the contact
     */
    ContactLog save( ContactLog contactLog );

    List<ContactLog> findByContactIdOrderByCreatedDateDesc(String contactId);

    ContactLog saveAsync(ContactLog contactLog);

}
