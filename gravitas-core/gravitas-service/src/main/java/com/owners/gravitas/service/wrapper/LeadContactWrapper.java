package com.owners.gravitas.service.wrapper;

import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.dto.response.LeadResponse;

/**
 * The Class LeadContactWrapper.
 *
 * @author parakhsh
 */
public class LeadContactWrapper {

    private Contact contact;
    private LeadResponse leadResponse;

    public LeadResponse getLeadResponse() {
        return leadResponse;
    }

    public void setLeadResponse(LeadResponse leadResponse) {
        this.leadResponse = leadResponse;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
    
}
