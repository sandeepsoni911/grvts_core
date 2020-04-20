package com.owners.gravitas.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.Group;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.domain.entity.UserGroup;
import com.owners.gravitas.repository.UserGroupRepository;
import com.owners.gravitas.service.UserGroupService;

/**
 * The Class UserGroupServiceImpl.
 *
 * @author raviz
 */
@Service
public class UserGroupServiceImpl implements UserGroupService {

    /** The user group repository. */
    @Autowired
    private UserGroupRepository userGroupRepository;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.UserGroupService#save(com.owners.gravitas.
     * domain.entity.UserGroup)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public UserGroup save( final UserGroup userGroup ) {
        return userGroupRepository.save( userGroup );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.UserGroupService#save(java.util.Collection)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public List< UserGroup > save( final Collection< UserGroup > userGroups ) {
        return userGroupRepository.save( userGroups );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.UserGroupService#findByGroup(com.owners.
     * gravitas.domain.entity.Group)
     */
    @Override
    @Transactional( readOnly = true )
    public Set< UserGroup > findByGroup( final Group group ) {
        return userGroupRepository.findByGroup( group );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.UserGroupService#findByUser(com.owners.
     * gravitas.domain.entity.User)
     */
    @Override
    @Transactional( readOnly = true )
    public Set< UserGroup > findByUser( final User user ) {
        return userGroupRepository.findByUser( user );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.UserGroupService#deleteByGroupAndFlush(com.
     * owners.
     * gravitas.domain.entity.Group)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public void deleteByGroupAndFlush( final Group group ) {
        userGroupRepository.deleteByGroup( group );
        userGroupRepository.flush();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.UserGroupService#deleteByGroupAndFlush(com.
     * owners.
     * gravitas.domain.entity.Group)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public void deleteByUserAndFlush( final User user ) {
        userGroupRepository.deleteByUser( user );
        userGroupRepository.flush();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.UserGroupService#findByUserAndGroup(com.
     * owners.gravitas.domain.entity.User,
     * com.owners.gravitas.domain.entity.Group)
     */
    @Override
    public UserGroup findByUserAndGroup( final User user, final Group group ) {
        return userGroupRepository.findByUserAndGroup( user, group );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.UserGroupService#deleteByUserAndGroup(com.
     * owners.gravitas.domain.entity.User,
     * com.owners.gravitas.domain.entity.Group)
     */
    @Override
    public void deleteByUserAndGroup( final User user, final Group group ) {
        userGroupRepository.deleteByUserAndGroup( user, group );
        userGroupRepository.flush();
    }

}
