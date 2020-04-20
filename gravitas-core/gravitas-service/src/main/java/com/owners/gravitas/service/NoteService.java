package com.owners.gravitas.service;

import com.owners.gravitas.dto.CrmNote;

/**
 * Service for interacting with CRM Notes
 * 
 * @author ankusht
 *
 */
public interface NoteService {

    /**
     * Saves note in salesforce
     * 
     * @param crmNote
     */
    void saveNote( final CrmNote crmNote );

}
