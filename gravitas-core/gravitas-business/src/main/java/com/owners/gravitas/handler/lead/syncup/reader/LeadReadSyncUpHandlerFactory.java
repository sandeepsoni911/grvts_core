package com.owners.gravitas.handler.lead.syncup.reader;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.enums.LeadSyncUpAttributes;
import com.owners.gravitas.handler.lead.syncup.reader.impl.UuidReadSyncUpServiceImpl;

/**
 * @author kushwaja
 */
@Repository
public class LeadReadSyncUpHandlerFactory {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(LeadReadSyncUpHandlerFactory.class);

	/** The opportunityChangeHandlers Map. */
	private Map<String, LeadReadSyncUpService> leadReadSyncUpFactory = new HashMap<>();

	@Autowired
	UuidReadSyncUpServiceImpl UuidReadSyncUpService;
	
	/**
	 * Creates a Map of Lead Read Sync up types.
	 */
	@PostConstruct
	public void init() {
		leadReadSyncUpFactory.put(LeadSyncUpAttributes.UUID.getAttribute(),UuidReadSyncUpService);
	}

	/**
	 * Returns the Lead Sync up reader based on the attribute type
	 * @param readerType
	 *            The readerType.
	 * @return The LeadSyncUpService instance.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getReader(final String readerType) {
		final Object strategy = leadReadSyncUpFactory.get(readerType);
		return (T) strategy;
	}

}
