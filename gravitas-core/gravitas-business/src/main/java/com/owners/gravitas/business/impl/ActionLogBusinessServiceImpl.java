package com.owners.gravitas.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.business.ActionLogBusinessService;
import com.owners.gravitas.business.builder.ActionLogAuditBuilder;
import com.owners.gravitas.business.builder.JmxChangeEventBuilder;
import com.owners.gravitas.domain.entity.ActionLog;
import com.owners.gravitas.dto.ActionLogDto;
import com.owners.gravitas.dto.JmxChangeDto;
import com.owners.gravitas.service.ActionLogService;

/**
 * The Class ActionLogBusinessServiceImpl.
 *
 * @author vishwanathm
 */
@Service
@Transactional
public class ActionLogBusinessServiceImpl implements ActionLogBusinessService {

    /** The action log service. */
    @Autowired
    private ActionLogService actionLogService;

    /** The action log audit builder. */
    @Autowired
    private ActionLogAuditBuilder actionLogAuditBuilder;

    /** The jmx change event builder. */
    @Autowired
    private JmxChangeEventBuilder jmxChangeEventBuilder;

    /**
     * Save action log.
     *
     * @param actionLog
     *            the action log
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public ActionLog saveActionLog( final ActionLog actionLog ) {
        return actionLogService.save( actionLog );
    }

    /**
     * Creates the audit for action.
     *
     * @param actionLogDto
     *            the action log dto
     * @return the list
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public void auditForAction( final ActionLogDto actionLogDto ) {
        actionLogService.saveAll( actionLogAuditBuilder.convertAll( actionLogDto ) );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.ActionLogBusinessService#
     * createAgentAuditForAction(java.util.List)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public void agentAuditForAction( final List< ActionLogDto > actionLogDtos ) {
        actionLogDtos.forEach(
                actionLogDto -> actionLogService.saveAll( actionLogAuditBuilder.convertAll( actionLogDto ) ) );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.ActionLogBusinessService#getCTAAuditLogs(
     * java.lang.String, java.lang.String, java.util.List)
     */
    @Override
    @Transactional( readOnly = true )
    public List< ActionLog > getCTAAuditLogs( final String email, final String opportunityId,
            final List< String > ctaValues ) {
        return actionLogService.getCTAAuditLogs( email, opportunityId, ctaValues );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.ActionLogBusinessService#logJmxChange(com.
     * owners.gravitas.event.JmxChangeEvent)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public void logJmxChange( final JmxChangeDto jmxChangeDto ) {
        final ActionLog actionLog = jmxChangeEventBuilder.convertFrom( jmxChangeDto );
        saveActionLog( actionLog );
    }
}
