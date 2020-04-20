package com.owners.gravitas.business.builder.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.domain.AgentHolder;
import com.owners.gravitas.domain.Contact;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.Search;

/**
 * The Class SearchBuilder.
 *
 * @author vishwanathm
 */
@Component( "searchBuilder" )
public class SearchBuilder extends AbstractBuilder< AgentHolder, Map< String, Search > > {

    /**
     * This method converts Agent object to List< SearchHolder >
     * object.
     *
     * @param source
     *            is the dto object to be converted to List< SearchHolder
     *            >.
     * @param destination
     *            the destination
     * @return AgentHolder object
     */
    @Override
    public Map< String, Search > convertTo( AgentHolder source, Map< String, Search > destination ) {
        Map< String, Search > searchMap = destination;
        if (source != null) {
            if (searchMap == null) {
                searchMap = new HashMap< String, Search >();
            }
            Search search = null;
            for ( Entry< String, Opportunity > entry : source.getAgent().getOpportunities().entrySet() ) {
                search = new Search();
                final Opportunity opportunity = entry.getValue();
                final String contactId = opportunity.getContacts().toArray()[0].toString();
                final Contact contact = source.getAgent().getContacts().get( contactId );

                search.setAgentId( source.getAgentId() );
                search.setAgentEmail( source.getAgent().getInfo().getEmail().toLowerCase() );
                search.setContactId( contactId );
                search.setContactEmail( contact.getEmails().get( 0 ).toLowerCase() );
                search.setOpportunityId( entry.getKey() );
                search.setCrmOpportunityId( opportunity.getCrmId() );
                searchMap.put( UUID.randomUUID().toString(), search );
            }
            if (search == null) {
                // if agent does not have any opportunities yet on CRM.
                search = new Search();
                search.setAgentId( source.getAgentId() );
                search.setAgentEmail( source.getAgent().getInfo().getEmail().toLowerCase() );
                searchMap.put( UUID.randomUUID().toString(), search );
            }
        }
        return searchMap;
    }

    /**
     * This method List< SearchHolder > object to Agent.
     * object.
     *
     * @param source
     *            is the dto object to be converted to domain.
     * @param destination
     *            the destination
     * @return AgentHolder object
     */
    @Override
    public AgentHolder convertFrom( Map< String, Search > source, AgentHolder destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }

}
