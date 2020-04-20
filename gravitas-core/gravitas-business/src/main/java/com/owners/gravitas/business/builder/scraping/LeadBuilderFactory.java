package com.owners.gravitas.business.builder.scraping;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.scraping.hubzu.HomeBidzEmailLeadBuilder;
import com.owners.gravitas.business.builder.scraping.hubzu.HomesEmailLeadBuilder;
import com.owners.gravitas.business.builder.scraping.hubzu.HubzuEmailLeadBuilder;
import com.owners.gravitas.business.builder.scraping.hubzu.RealtorEmailLeadBuilder;
import com.owners.gravitas.business.builder.scraping.hubzu.TruliaEmailLeadBuilder;
import com.owners.gravitas.business.builder.scraping.hubzu.UltraForcesEmailLeadBuilder;
import com.owners.gravitas.business.builder.scraping.hubzu.ZillowEmailLeadBuilder;
import com.owners.gravitas.business.builder.scraping.owners.OwnersEmailLeadBuilder;
import com.owners.gravitas.business.builder.scraping.owners.RealtorOwnersEmailLeadBuilder;
import com.owners.gravitas.config.LeadSubject;
import com.owners.gravitas.exception.ApplicationException;

// TODO: Auto-generated Javadoc
/**
 * The Class LeadBuilderFactory.
 *
 * @author vishwanathm
 */
@Component
public class LeadBuilderFactory {
    /** The Constant SOURCE_HUBZU. */
    private static final String SOURCE_HUBZU = "Hubzu";

    /** The Constant SOURCE_OWNERS. */
    private static final String SOURCE_OWNERS = "Owners";

    /** The hubzu email builder. */
    @Autowired
    private HubzuEmailLeadBuilder hubzuEmailLeadBuilder;

    /** The trulia email builder. */
    @Autowired
    private TruliaEmailLeadBuilder truliaEmailLeadBuilder;

    /** The homes email lead builder. */
    @Autowired
    private HomesEmailLeadBuilder homesEmailLeadBuilder;

    /** The realtor email lead builder. */
    @Autowired
    private RealtorEmailLeadBuilder realtorEmailLeadBuilder;

    /** The zillow email lead builder. */
    @Autowired
    private ZillowEmailLeadBuilder zillowEmailLeadBuilder;

    /** The home bidz email lead builder. */
    @Autowired
    private HomeBidzEmailLeadBuilder homeBidzEmailLeadBuilder;

    /** The ultra forces email lead builder. */
    @Autowired
    private UltraForcesEmailLeadBuilder ultraForcesEmailLeadBuilder;

    /** The owners email lead builder. */
    @Autowired
    private OwnersEmailLeadBuilder ownersEmailLeadBuilder;

    /** The owners realtor email lead builder. */
    @Autowired
    private RealtorOwnersEmailLeadBuilder realtorOwnersEmailLeadBuilder;

    /** The builders source. */
    private static Map< String, Map< String, EmailLeadBuilder > > buildersSource = new HashMap<>();
    /** The email lead builders. */
    private static Map< String, EmailLeadBuilder > hubzuEmailLeadBuilders = new HashMap<>();

    /** The owners email lead builders. */
    private static Map< String, EmailLeadBuilder > ownersEmailLeadBuilders = new HashMap<>();

    /** The lead subject. */
    @Autowired
    private LeadSubject leadSubject;

    /**
     * Initializes the all spring instantiated email builders and add them to
     * map.
     */
    @PostConstruct
    private void initialize() {
        // Hubzu lead builders
        hubzuEmailLeadBuilders.put( leadSubject.getHubzuSubject(), hubzuEmailLeadBuilder );
        hubzuEmailLeadBuilders.put( leadSubject.getHomesSubject(), homesEmailLeadBuilder );
        hubzuEmailLeadBuilders.put( leadSubject.getRealtorSubject(), realtorEmailLeadBuilder );
        hubzuEmailLeadBuilders.put( leadSubject.getZillowSubject(), zillowEmailLeadBuilder );
        hubzuEmailLeadBuilders.put( leadSubject.getHomeBidzSubject(), homeBidzEmailLeadBuilder );
        hubzuEmailLeadBuilders.put( leadSubject.getUltraforceSubject(), ultraForcesEmailLeadBuilder );
        hubzuEmailLeadBuilders.put( leadSubject.getTruliaSubject(), truliaEmailLeadBuilder );

        // Owners lead builders
        ownersEmailLeadBuilders.put( leadSubject.getOwnersRealtorSubject(), realtorOwnersEmailLeadBuilder );
        ownersEmailLeadBuilders.put( leadSubject.getOwnersSubject(), ownersEmailLeadBuilder );
        ownersEmailLeadBuilders.put( leadSubject.getOwnersSellerLeadSubject(), ownersEmailLeadBuilder );
        ownersEmailLeadBuilders.put( leadSubject.getOwnersSellerLeadRequestResultSubject(), ownersEmailLeadBuilder );
        ownersEmailLeadBuilders.put( leadSubject.getOwnersSellerLeadHomesSubject(), ownersEmailLeadBuilder );

        buildersSource.put( SOURCE_HUBZU, hubzuEmailLeadBuilders );
        buildersSource.put( SOURCE_OWNERS, ownersEmailLeadBuilders );
    }

    /**
     * Gets the email lead builder for provided subject as key.
     *
     * @param mailSource
     *            the mail source
     * @param message
     *            the message
     * @return the email lead builder
     */
    public EmailLeadBuilder getEmailLeadBuilder( final String mailSource, final Message message ) {
        try {
            for ( String subjectStr : buildersSource.get( mailSource ).keySet() ) {
                if (message.getSubject().toLowerCase().contains( subjectStr )) {
                    return buildersSource.get( mailSource ).get( subjectStr );
                }
            }
            return null;
        } catch ( MessagingException e ) {
            throw new ApplicationException( e.getLocalizedMessage(), e );
        }
    }

    /**
     * Gets the subjects.
     *
     * @param mailSource
     *            the mail source
     * @return the subjects
     */
    public Set< String > getSubjects( final String mailSource ) {
        return buildersSource.get( mailSource ).keySet();
    }
}
