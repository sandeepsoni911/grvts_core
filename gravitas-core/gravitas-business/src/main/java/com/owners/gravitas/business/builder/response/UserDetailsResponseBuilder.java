package com.owners.gravitas.business.builder.response;

import static com.owners.gravitas.util.StringUtils.convertObjectToString;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.api.client.util.ArrayMap;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.UserName;
import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.dto.response.UserDetailsResponse;

/**
 * The Class UserDetailsResponseBuilder.
 *
 * @author harshads
 */
@Component
public class UserDetailsResponseBuilder extends AbstractBuilder< List< User >, UserDetailsResponse > {

    /**
     * Convert to.
     *
     * @param source
     *            the source
     * @param destination
     *            the destination
     * @return the user details response
     */
    @Override
    public UserDetailsResponse convertTo( List< User > source, final UserDetailsResponse destination ) {
        UserDetailsResponse userDetailsResponse = destination;
        if (source != null) {
            if (userDetailsResponse == null) {
                userDetailsResponse = new UserDetailsResponse();
            }
            for ( final User googleUser : source ) {
                final com.owners.gravitas.dto.User user = new com.owners.gravitas.dto.User();
                final UserName googleUserName = googleUser.getName();
                user.setFirstName( googleUserName.getGivenName() );
                user.setLastName( googleUserName.getFamilyName() );
                user.setEmail( googleUser.getPrimaryEmail() );
                user.setPhone( getUserPhone( googleUser ) );

                final String state = getAgentState( googleUser );
                com.owners.gravitas.dto.UserAddress address = new com.owners.gravitas.dto.UserAddress();
                address.setState( state );
                user.setAddress( address );

                userDetailsResponse.getUsers().add( user );
            }
        }
        return userDetailsResponse;
    }

    /**
     * Convert from.
     *
     * @param source
     *            the source
     * @param destination
     *            the destination
     * @return the list
     */
    @Override
    public List< User > convertFrom( UserDetailsResponse source, List< User > destination ) {
        throw new UnsupportedOperationException( "convertFrom operation is not supported" );
    }

    /**
     * Gets the user phone.
     *
     * @param googleUser
     *            the google user
     * @return the user phone
     */
    private String getUserPhone( final User googleUser ) {
        String phone = null;
        final List< ArrayMap< String, Object > > phones = ( List< ArrayMap< String, Object > > ) googleUser.getPhones();
        if (phones != null) {
            for ( ArrayMap< String, Object > phoneMap : phones ) {
                if ("work".equals( phoneMap.get( "type" ) )) {
                    phone = String.valueOf( phoneMap.get( "value" ) );
                    break;
                }
            }
        }
        return phone;
    }

    /**
     * Sets the agent state.
     *
     * @param source
     *            the source
     * @return the string
     */
    private String getAgentState( final User source ) {
        String state = "";
        final List< ArrayMap< String, Object > > addresses = ( List< ArrayMap< String, Object > > ) source
                .getAddresses();
        if (addresses != null) {
            for ( ArrayMap< String, Object > addressMap : addresses ) {
                if ("home".equals( addressMap.get( "type" ) )) {
                    state = convertObjectToString( addressMap.get( "region" ) );
                    break;
                }
            }
        }
        return state;
    }
}
