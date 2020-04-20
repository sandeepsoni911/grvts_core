package com.owners.gravitas.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ContactAttribute;
import com.owners.gravitas.domain.entity.LeadSyncUp;
import com.owners.gravitas.domain.entity.Opportunity;

/**
 * The Interface ContactEntityService.
 *
 * @author kushwaja
 */
public interface LeadSyncUpEntityService {

    /**
     * Save.
     *
     * @param prospect
     *            the contact
     * @return the contact
     */
	LeadSyncUp save( LeadSyncUp leadSyncUp );
    
    /**
     * Save.
     *
     * @param prospect
     *            the contact
     * @return the contact
     */
    List<LeadSyncUp> save( List<LeadSyncUp> LeadSyncUpEntities );
}
