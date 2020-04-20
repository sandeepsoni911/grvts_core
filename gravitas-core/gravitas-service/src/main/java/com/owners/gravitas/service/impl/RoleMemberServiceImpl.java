package com.owners.gravitas.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.domain.entity.Role;
import com.owners.gravitas.repository.RoleRepository;
import com.owners.gravitas.repository.RoleMemberRepository;
import com.owners.gravitas.service.RoleMemberService;

/**
 * The Class RoleMemberServiceImpl.
 *
 * @author pabhishek
 */
@Service
public class RoleMemberServiceImpl implements RoleMemberService {

    /** The role repository. */
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private RoleMemberRepository roleMemberRepository;
    
    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.RoleMemberService#save(com.owners.gravitas.
     * domain.
     * entity.Role)
     */
    @Override
    public Role save( final Role role ) {
        return roleRepository.save( role );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.RoleMemberService#getRolesByUserEmailId(java.
     * lang.
     * String)
     */
    @Override
    public List< Role > getRolesByUserEmailId( final String email ) {
        return roleRepository.getRolesByUserEmailId( email );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.RoleMemberService#findById(java.lang.String)
     */
    @Override
    public Role findById( final String id ) {
        return roleRepository.findById( id );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.RoleMemberService#findByName(java.lang.
     * String)
     */
    @Override
    public Role findByName( final String name ) {
        return roleRepository.findByName( name );
    }
    
	@Override
	//@Cacheable( value = "roleCache", cacheManager = "rolePermissionCacheManager" )
	public Map<String, String> getRolesNameMap() {
		List<Role> roles = roleMemberRepository.findRoleIdAndRoleNameMap();
		return roles.stream().collect(Collectors.toMap( r -> r.getName(), r -> r.getId()));
	}

	@Override
	//@Cacheable( value = "roleCache", cacheManager = "rolePermissionCacheManager" )
	public Map<String, String> getRolesIdMap() {
		List<Role> roles = roleMemberRepository.findRoleIdAndRoleNameMap();
		return roles.stream().collect(Collectors.toMap( r -> r.getId(), r -> r.getName()));
	}

}
