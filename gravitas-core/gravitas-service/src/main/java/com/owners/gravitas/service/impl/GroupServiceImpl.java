package com.owners.gravitas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.Group;
import com.owners.gravitas.repository.GroupRepository;
import com.owners.gravitas.service.GroupService;

/**
 * The Class GroupServiceImpl.
 *
 * @author raviz
 */
@Service
public class GroupServiceImpl implements GroupService {

    /** The group repository. */
    @Autowired
    private GroupRepository groupRepository;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.GroupService#findByName(java.lang.String)
     */
    @Override
    public Group findByName( final String name ) {
        return groupRepository.findByName( name );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.GroupService#findGroupsByNameAndDeleted(java.
     * lang.String, boolean)
     */
    @Override
    @Transactional( readOnly = true )
    public Group findGroupByNameAndDeleted( final String name, final boolean isDeleted ) {
        return groupRepository.findByNameAndDeleted( name, isDeleted );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.GroupService#findGroupById(java.lang.String)
     */
    @Override
    @Transactional( readOnly = true )
    public Group findGroupById( final String id ) {
        return groupRepository.findById( id );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.GroupService#save(com.owners.gravitas.domain.
     * entity.Group)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public Group save( final Group group ) {
        return groupRepository.save( group );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.GroupService#findByDeleted(boolean)
     */
    @Override
    @Transactional( readOnly = true )
    public List< Group > findByDeleted( boolean isDeleted ) {
        return groupRepository.findByDeleted( isDeleted );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.GroupService#findAll()
     */
    @Override
    public List< Group > findAll() {
        return groupRepository.findAll();
    }

}
