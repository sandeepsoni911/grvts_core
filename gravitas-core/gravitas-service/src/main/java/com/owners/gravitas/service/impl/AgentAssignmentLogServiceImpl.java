package com.owners.gravitas.service.impl;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.AgentAssignmentLog;
import com.owners.gravitas.dto.AgentAssignmentLogDto;
import com.owners.gravitas.enums.AssignmentStatus;
import com.owners.gravitas.repository.AgentAssignmentLogRepository;
import com.owners.gravitas.service.AgentAssignmentLogService;

/**
 * The Class AgentAssignmentLogServiceImpl.
 * 
 * @author ankusht, abhishek
 */
@Service
public class AgentAssignmentLogServiceImpl implements AgentAssignmentLogService {

    /** The agent assignment log repository. */
    @Autowired
    private AgentAssignmentLogRepository agentAssignmentLogRepository;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentAssignmentLogService#saveAll(java.util.
     * Collection)
     */
    @Override
    @Transactional
    public void saveAll( final Collection< AgentAssignmentLog > agentAssignmentLogs ) {
        agentAssignmentLogRepository.save( agentAssignmentLogs );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentAssignmentLogService#findByPropertyZips(
     * java.util.Collection)
     */
    @Override
    @Transactional( readOnly = true )
    public List< AgentAssignmentLogDto > findByPropertyZip( final String propertyZip ) {
        final List< Object[] > result = agentAssignmentLogRepository.findByPropertyZip( propertyZip );
        final List< AgentAssignmentLogDto > retVal = new LinkedList<>();
        result.forEach( re -> {
            final BigDecimal score = ( BigDecimal ) re[1];
            retVal.add( new AgentAssignmentLogDto( re[0].toString(), score.doubleValue() * 100, re[2].toString(),
                    re[3].toString(), re[4].toString() ) );
        } );
        return retVal;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentAssignmentLogService#
     * findByPropertyZipAndEmailsIn(java.lang.String, java.util.List)
     */
    @Override
    @Transactional( readOnly = true )
    public List< AgentAssignmentLogDto > findByPropertyZipAndEmailsIn( final String propertyZip,
            final Collection< String > hungryAgentBucketEmails ) {
        final List< Object[] > result = agentAssignmentLogRepository.findByPropertyZipAndEmailsIn( propertyZip,
                hungryAgentBucketEmails );
        final List< AgentAssignmentLogDto > retVal = new LinkedList<>();
        result.forEach( re -> {
            final BigDecimal score = ( BigDecimal ) re[1];
            retVal.add( new AgentAssignmentLogDto( re[0].toString(), score.doubleValue() * 100, re[2].toString(),
                    re[3].toString(), re[4].toString() ) );
        } );
        return retVal;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentAssignmentLogService#
     * findAgentEmailByLeastOppAssignedDate(java.util.Collection)
     */
    @Override
    public String findAgentEmailByLeastOppAssignedDate( final Collection< String > agentEmails ) {
        return agentAssignmentLogRepository.findAgentEmailByLeastOppAssignedDate( agentEmails );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentAssignmentLogService#save(com.owners.
     * gravitas.domain.entity.AgentAssignmentLog)
     */
    @Override
    @Transactional
    public void save( final AgentAssignmentLog log ) {
        agentAssignmentLogRepository.save( log );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentAssignmentLogService#findByOpportunityId
     * (java.lang.String, java.lang.String,
     * org.springframework.data.domain.Pageable)
     */
    @Override
    @Transactional( readOnly = true )
    public AgentAssignmentLog findByOpportunityIdAndStatusLike( final String crmId, final String status,
            final Pageable pageable ) {
        final Page< AgentAssignmentLog > page = agentAssignmentLogRepository
                .findByCrmOpportunityIdAndAssignmentStatusContainingOrderByCreatedDateDesc( crmId, status, pageable );
        return getFirstFromPage( page );
    }

    /**
     * Gets the from page.
     *
     * @param page
     *            the page
     * @return the from page
     */
    private AgentAssignmentLog getFirstFromPage( final Page< AgentAssignmentLog > page ) {
        final List< AgentAssignmentLog > content = page.getContent();
        final AgentAssignmentLog agentAssignmentLog = isNotEmpty( content ) ? content.get( 0 ) : null;
        return agentAssignmentLog;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentAssignmentLogService#
     * getAgentRejectReason(java.lang.String)
     */
    @Override
    public String getAgentRejectReason( final String reason ) {
        if (reason != null) {
            if (reason.equalsIgnoreCase( AssignmentStatus.agent_rejected_opportunity.name() )) {
                return AssignmentStatus.agent_rejected_opportunity.name();
            } else if (reason.equalsIgnoreCase( AssignmentStatus.agent_did_not_respond_to_call.name() )) {
                return AssignmentStatus.agent_did_not_respond_to_call.name();
            } else if (reason.equalsIgnoreCase( AssignmentStatus.agent_disconnected.name() )) {
                return AssignmentStatus.agent_disconnected.name();
            } else if (reason.equalsIgnoreCase( AssignmentStatus.other.name() )) {
                return AssignmentStatus.other.name();
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentAssignmentLogService#
     * getAgentAssignmentLog(java.lang.String, java.lang.String,
     * java.lang.String, org.springframework.data.domain.Pageable)
     */
    @Override
    @Transactional( readOnly = true )
    public AgentAssignmentLog getAgentAssignmentLog( final String crmOppId, final String agentEmail,
            final String status, final Pageable pageable ) {
        final Page< AgentAssignmentLog > page = agentAssignmentLogRepository
                .findByCrmOpportunityIdAndAgentEmailAndAssignmentStatusContaining( crmOppId, agentEmail, status,
                        pageable );
        return getFirstFromPage( page );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentAssignmentLogService#
     * findByCrmOpportunityIdAndAgentEmailAndCreatedDate(java.lang.String,
     * java.lang.String, org.joda.time.DateTime)
     */
    @Override
    public AgentAssignmentLog findByCrmOpportunityIdAndAgentEmailAndCreatedDate( final String crmOppId, final String agentEmail,
            final DateTime createdDate ) {
        return agentAssignmentLogRepository.findByCrmOpportunityIdAndAgentEmailAndCreatedDate( crmOppId, agentEmail,
                createdDate );
    }
}
