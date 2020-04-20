package com.owners.gravitas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.GroupOkr;
import com.owners.gravitas.repository.GroupOkrRepository;
import com.owners.gravitas.service.GroupOkrService;

/**
 * The Class GroupOkrServiceImpl.
 *
 * @author raviz
 */
@Service
public class GroupOkrServiceImpl implements GroupOkrService {

    /** The group okr repository. */
    @Autowired
    private GroupOkrRepository groupOkrRepository;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.GroupOkrService#save(com.owners.gravitas.
     * domain.entity.GroupOkr)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public GroupOkr save( final GroupOkr groupOkr ) {
        return groupOkrRepository.save( groupOkr );
    }

}
