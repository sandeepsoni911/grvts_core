package com.owners.gravitas.handler.lead.syncup.processor;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.enums.LeadSyncUpAttributes;
import com.owners.gravitas.enums.OpportunityChangeType;
import com.owners.gravitas.handler.lead.syncup.processor.impl.UuidProcessSyncUpServiceImpl;
import com.owners.gravitas.handler.lead.syncup.reader.LeadReadSyncUpHandlerFactory;
import com.owners.gravitas.handler.lead.syncup.reader.LeadReadSyncUpService;
import com.owners.gravitas.handler.lead.syncup.reader.impl.UuidReadSyncUpServiceImpl;

/**
 * Contains a map of Opportunity Change Handlers. The Opportunity Change
 * Handlers are populated at startup. Opportunity Change Handlers are annotated
 * with @OpportunityChange annotation.
 *
 * @author Khanujal
 */
@Repository
public class LeadProcessSyncUpHandlerFactory {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(LeadReadSyncUpHandlerFactory.class);

	/** The opportunityChangeHandlers Map. */
	private Map<String, Object> leadProcessSyncUpFactory = new HashMap<>();
	
	@Autowired
	UuidProcessSyncUpServiceImpl uuidProcessSyncUpService;

	/**
	 * Creates a Map of Lead Read Sync up types.
	 */
	@PostConstruct
	public void init() {
		leadProcessSyncUpFactory.put(LeadSyncUpAttributes.UUID.getAttribute(),uuidProcessSyncUpService);
	}

	/**
	 * Returns the Lead Sync up reader based on the attribute type
	 * @param readerType
	 *            The readerType.
	 * @return The LeadSyncUpService instance.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getProcessor(final String readerType) {
		final Object strategy = leadProcessSyncUpFactory.get(readerType);
		return (T) strategy;
	}

}
