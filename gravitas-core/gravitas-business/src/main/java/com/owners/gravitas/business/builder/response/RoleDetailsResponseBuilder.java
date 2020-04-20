package com.owners.gravitas.business.builder.response;

import java.util.List;

import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.domain.entity.Role;
import com.owners.gravitas.dto.response.RoleDetailsResponse;

/**
 * The Class RoleDetailsResponseBuilder.
 * 
 * @author pabhishek
 */
@Component
public class RoleDetailsResponseBuilder extends AbstractBuilder< List< Role >, RoleDetailsResponse > {

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public RoleDetailsResponse convertTo( List< Role > source, final RoleDetailsResponse destination ) {
        RoleDetailsResponse roleDetailsResponse = destination;
        if (source != null) {
            if (roleDetailsResponse == null) {
                roleDetailsResponse = new RoleDetailsResponse();
            }
            for ( final Role fetchedRole : source ) {
                final com.owners.gravitas.dto.Role role = new com.owners.gravitas.dto.Role();
                role.setName( fetchedRole.getName() );
                role.setDescription( fetchedRole.getDescription() );
                roleDetailsResponse.getRoles().add( role );
            }
        }
        return roleDetailsResponse;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public List< Role > convertFrom( RoleDetailsResponse source, List< Role > destination ) {
        throw new UnsupportedOperationException( "convertFrom operation is not supported" );
    }

}
