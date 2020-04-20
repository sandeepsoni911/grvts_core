package com.owners.gravitas.service;

import com.owners.gravitas.domain.entity.Role;

public interface RoleService {

    /**
     * Find by name.
     *
     * @param name
     *            the name
     * @return the role
     */
    Role findByName( String name );
}
