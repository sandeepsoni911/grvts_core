package com.owners.gravitas.service.impl;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.TopicStatus;
import com.owners.gravitas.repository.TopicStatusRepository;
import com.owners.gravitas.service.TopicStatusService;

/**
 * The Class TopicStatusServiceImpl.
 *
 * @author raviz
 */
@Service
@Transactional( readOnly = true )
public class TopicStatusServiceImpl implements TopicStatusService {

    /** The topic status repository. */
    @Autowired
    private TopicStatusRepository topicStatusRepository;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.TopicStatusService#clearTopicStatusData()
     */
    @Override
    @Transactional
    public void clearTopicStatusData() {
        topicStatusRepository.deleteAll();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.TopicStatusService#findAll()
     */
    @Override
    public Set< String > findTopicNames() {
        return topicStatusRepository.findTopicNames();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.TopicStatusService#save(com.owners.gravitas.
     * domain.entity.TopicStatus)
     */
    @Override
    @Transactional
    public TopicStatus save( final TopicStatus topicStatus ) {
        return topicStatusRepository.save( topicStatus );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.TopicStatusService#findByName(java.lang.
     * String)
     */
    @Override
    public TopicStatus findByName( final String topicName ) {
        return topicStatusRepository.findByTopicName( topicName );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.TopicStatusService#updateTopicStatus(java.
     * lang.String, java.lang.String)
     */
    @Override
    @Transactional( propagation = REQUIRES_NEW )
    public void updateTopicStatus( final String name, final String status ) {
        final TopicStatus topicStatus = findByName( name );
        topicStatus.setStatus( status );
        topicStatusRepository.save( topicStatus );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.TopicStatusService#findAll()
     */
    @Override
    public List< TopicStatus > findAll() {
        return topicStatusRepository.findAll();
    }
}
