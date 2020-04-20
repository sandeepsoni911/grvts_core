package com.owners.gravitas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.owners.gravitas.repository.RolePermissionRepository;
import com.owners.gravitas.service.RolePermissionService;

/**
 * The Class RolePermissionServiceImpl.
 *
 * @author pabhishek
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    /** The role permission repository. */
    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.RolePermissionService#
     * getPermissionsByUserEmailId(java.lang.String)
     */
    @Override
    @Cacheable( value = "rolePermissionCache", cacheManager = "redisCacheManager" )
    public List< String > getPermissionsByUserEmailId( String email ) {
        return rolePermissionRepository.getPermissionsByUserEmailId( email );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.RolePermissionService#getPermissionsByRoles(
     * java.util.List)
     */
    @Override
    @Cacheable( value = "rolePermissionCache", cacheManager = "redisCacheManager" )
    public List< String > getPermissionsByRoles( List< String > roleNames ) {
        return rolePermissionRepository.getPermissionsByRoles( roleNames );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.RolePermissionService#getRolesByUserEmailId(
     * java.lang.String)
     */
    @Override
    @Cacheable( value = "roleCache", cacheManager = "redisCacheManager" )
    public List< String > getRolesByUserEmailId( String email ) {
        return rolePermissionRepository.getRolesyUserEmailId( email );
    }

}
