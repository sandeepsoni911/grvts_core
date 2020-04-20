package com.owners.gravitas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.Role;
import com.owners.gravitas.repository.RoleRepository;
import com.owners.gravitas.service.RoleService;

/**
 * The Class RoleServiceImpl.
 * 
 * @author ankusht
 */
@Service
public class RoleServiceImpl implements RoleService {

    /** The role repository. */
    @Autowired
    private RoleRepository roleRepository;

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.RoleService#findByName(java.lang.String)
     */
    @Override
    @Transactional( readOnly = true )
    public Role findByName( final String name ) {
        return roleRepository.findByName( name );
    }

}
