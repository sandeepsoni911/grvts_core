package com.owners.gravitas.handler;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.enums.OpportunityChangeType;

/**
 * Contains a map of Opportunity Change Handlers. The Opportunity Change
 * Handlers are populated at startup. Opportunity Change Handlers are annotated
 * with @OpportunityChange annotation.
 *
 * @author Khanujal
 */
@Repository
public class OpportunityChangeHandlerFactory {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(OpportunityChangeHandlerFactory.class);

	/** The ApplicationContext. */
	@Autowired
	private ApplicationContext applicationContext;

	/** The opportunityChangeHandlers Map. */
	private Map<OpportunityChangeType, Map<String, Object>> opportunityChangeHandlers = new HashMap<>();

	/**
	 * Creates a Map of OpportunityChangeHandler types.
	 */
	@PostConstruct
	public void init() {

		final Map<String, Object> annotatedBeanClasses = applicationContext
				.getBeansWithAnnotation(OpportunityChange.class);

		for (final Object bean : annotatedBeanClasses.values()) {
			final OpportunityChange strategyAnnotation = AnnotationUtils.findAnnotation(bean.getClass(),
					OpportunityChange.class);
			if (opportunityChangeHandlers.containsKey(strategyAnnotation.type())) {
				final Map<String, Object> handlers = opportunityChangeHandlers.get(strategyAnnotation.type());
				if (!handlers.containsKey(strategyAnnotation.value().toLowerCase())) {
					handlers.put(strategyAnnotation.value().toLowerCase(), bean);
				} else {
					LOGGER.error("More than one handler found for OpportunityChangeType: " + strategyAnnotation.type()
							+ "value: " + strategyAnnotation.value());
					throw new RuntimeException("More than one handler found for OpportunityChangeType: "
							+ strategyAnnotation.type() + "value: " + strategyAnnotation.value());
				}
			} else {
				final Map<String, Object> handlers = new HashMap<>();
				handlers.put(strategyAnnotation.value().toLowerCase(), bean);
				opportunityChangeHandlers.put(strategyAnnotation.type(), handlers);
			}
		}

	}

	/**
	 * Returns the opportunityChangeHandler based on the OpportunityChangeType
	 * and changed field value.
	 *
	 * @param opportunityChangeType
	 *            The OpportunityChangeType.
	 * @param handlerName
	 *            The handlerName.
	 * @return The OpportunityChangeHandler instance.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getChangeHandler(final OpportunityChangeType opportunityChangeType, final String handlerName) {
		final Object strategy = opportunityChangeHandlers.get(opportunityChangeType).get(handlerName.toLowerCase());
		return (T) strategy;
	}

}
