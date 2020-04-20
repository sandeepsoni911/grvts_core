package com.owners.gravitas.business.builder;

import static com.owners.gravitas.constants.Constants.SPACE;
import static com.owners.gravitas.util.ObjectUtil.isNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.api.client.util.ArrayMap;
import com.google.api.services.admin.directory.model.User;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.dto.Agent;
import com.owners.gravitas.dto.agentassgn.EligibleAgent;
import com.owners.gravitas.service.AgentDetailsService;
import com.owners.gravitas.service.UserService;

/**
 * The Class AgentDtoBuilder.
 * 
 * @author ankusht, abhishek
 */
@Component
public class AgentDtoBuilder extends AbstractBuilder< String, Agent > {

    /** The user service. */
    @Autowired
    private UserService userService;

    /** The agent details service. */
    @Autowired
    private AgentDetailsService agentDetailsService;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public Agent convertTo( final String source, final Agent destination ) {
        Agent agent = destination;
        if (source != null) {
            if (agent == null) {
                agent = new Agent();
            }
            final User googleUser = userService.getUser( source );
            final AgentDetails agentDetails = agentDetailsService.findAgentByEmail( source );
            agent.setFirstName( googleUser.getName().getFullName() );
            agent.setEmail( source );
            agent.setPhone( setAgentPhones( googleUser ) );
            agent.setStatus( agentDetails.getUser().getStatus() );
            agent.setLanguage( agentDetails.getLanguage() );
        }
        return agent;
    }

    /**
     * Convert to.
     *
     * @param eligibleAgent
     *            the eligible agent
     * @return the agent
     */
    public Agent convertTo( final EligibleAgent eligibleAgent ) {
        Agent agent = null;
        if (eligibleAgent != null) {
            agent = new Agent();
            agent.setEmail( eligibleAgent.getEmail() );
            agent.setScore( eligibleAgent.getScore() );
            agent.setPhone( eligibleAgent.getPhone() );
            final String[] names = eligibleAgent.getName().split( SPACE );
            final int length = names.length;
            agent.setFirstName( names[0] );
            if (length > 1) {
                agent.setLastName( names[1] );
            }
        }
        return agent;
    }

    /**
     * Sets the agent phones.
     *
     * @param source
     *            the source
     * @return the string
     */
    private String setAgentPhones( final User source ) {
        String phone = EMPTY;
        final List< ArrayMap< String, Object > > phones = ( List< ArrayMap< String, Object > > ) source.getPhones();
        if (phones != null) {
            for ( final ArrayMap< String, Object > phoneMap : phones ) {
                if ("work".equals( phoneMap.get( "type" ) )) {
                    phone = isNull( ( phoneMap.get( "value" ) ) );
                    break;
                }
            }
        }
        return phone;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public String convertFrom( final Agent source, final String destination ) {
        throw new UnsupportedOperationException( "convertFrom operation is not supported" );
    }

}
