package com.owners.gravitas.handler.lead.syncup.reader.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.dto.request.SyncUpRequest;
import com.owners.gravitas.handler.lead.syncup.reader.LeadReadSyncUpService;

/**
 * @author kushwaja
 */
@Service
public class UuidReadSyncUpServiceImpl extends LeadReadSyncUpService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( UuidReadSyncUpServiceImpl.class );
    
    
	public List<Contact> execute(SyncUpRequest request) {
		return syncUpAttribute(request);
	}

}
