package com.owners.gravitas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.ActionLog;
import com.owners.gravitas.repository.ActionLogRepository;
import com.owners.gravitas.service.ActionLogService;

/**
 * The Class ActionLogServiceImpl.
 *
 * @author vishwanathm
 */
@Service
public class ActionLogServiceImpl implements ActionLogService {

    /** The action log repository. */
    @Autowired
    private ActionLogRepository actionLogRepository;

    /**
     * Save.
     *
     * @param actionLog
     *            the action log
     * @return the action log
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public ActionLog save( final ActionLog actionLog ) {
        return actionLogRepository.save( actionLog );
    }

    /**
     * Gets the CTA audit logs.
     *
     * @param email
     *            the email
     * @param ctaValues
     *            the cta values
     * @param opportunityId
     *            the opportunity id
     * @return the CTA audit logs
     */
    @Override
    public List< ActionLog > getCTAAuditLogs( final String email, final String opportunityId,
            final List< String > ctaValues ) {
        return actionLogRepository.getActionLogByActionByAndActionEntityIdAndActionTypeIn( email, opportunityId,
                ctaValues );
    }

    /**
     * Save all.
     *
     * @param actionLogs
     *            the log list
     * @return the list
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public void saveAll( final List< ActionLog > actionLogs ) {
        actionLogRepository.save( actionLogs );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ActionLogService#getActionLogs(java.lang.
     * String, java.util.List)
     */
    @Override
    @Transactional( readOnly = true )
    public List< ActionLog > getActionLogs( final String actionEntity, final List< String > ctaValues ) {
        return actionLogRepository.getActionLogByActionEntityAndActionTypeInOrderByCreatedDate( actionEntity,
                ctaValues );
    }
}
