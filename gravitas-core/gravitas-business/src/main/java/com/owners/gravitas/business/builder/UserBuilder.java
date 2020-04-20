package com.owners.gravitas.business.builder;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.entity.Role;
import com.owners.gravitas.domain.entity.RoleMember;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.dto.request.AgentOnboardRequest;
import com.owners.gravitas.service.RoleMemberService;

/**
 * The Class GoogleUserBuilder.
 *
 * @author pabhishek
 */
@Component( "userBuilder" )
public class UserBuilder extends AbstractBuilder< User, AgentOnboardRequest > {

    /** The role service. */
    @Autowired
    private RoleMemberService roleMemberService;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public User convertFrom( AgentOnboardRequest source, User destination ) {
        User user = destination;
        final List< RoleMember > roles = new ArrayList< RoleMember >();
        if (source != null) {
            if (user == null) {
                user = new User();
                user.setEmail( source.getEmail() );
                user.setRoleMember( roles );
            } else {
                roles.addAll( user.getRoleMember() );
            }
            user.setStatus( source.getStatus().toLowerCase() );
            final Role role = roleMemberService.findByName( source.getRoleId() );
            if (isEmpty( user.getRoleMember() ) && role != null) {
                final RoleMember roleMember = new RoleMember();
                roleMember.setUser( user );
                roleMember.setRole( role );
                roles.add( roleMember );
            }
        }
        return user;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public AgentOnboardRequest convertTo( User source, AgentOnboardRequest destination ) {
        throw new UnsupportedOperationException();
    }

}
