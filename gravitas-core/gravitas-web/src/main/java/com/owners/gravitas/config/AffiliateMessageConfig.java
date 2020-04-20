package com.owners.gravitas.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.search.AndTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.OrTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.StringTerm;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.mail.SearchTermStrategy;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.owners.gravitas.business.AffiliateEmailBusinessService;
import com.owners.gravitas.business.EmailScrappingDetailBusinessService;
import com.owners.gravitas.business.OclEmailBusinessService;
import com.owners.gravitas.business.ZillowHotlineLeadBusinessService;
import com.owners.gravitas.exception.AffiliateEmailException;
import com.owners.gravitas.exception.AffiliateEmailParsingException;
import com.owners.gravitas.exception.AffiliateEmailValidationException;
import com.owners.gravitas.lock.AffiliatedMailLockHandler;

/**
 * The Class AffiliateMessageConfig a configuration class connects to mail
 * store with IMAP and put message to channel.
 *
 * @author vishwanathm
 */
@Configuration
@EnableIntegration
@EnableScheduling
@MessageEndpoint
@ImportResource( "classpath:META-INF/imap/gravitas-imap.xml" )
public class AffiliateMessageConfig {
    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AffiliateMessageConfig.class );

    /** The hubzu config. */
    @Autowired
    private NotificationEmailConfig hubzuConfig;

    /** The owners config. */
    @Autowired
    private NotificationEmailConfig ownersConfig;

    /** The valuations config. */
    @Autowired
    private NotificationEmailConfig valuationsConfig;

    /** The ocl config. */
    @Autowired
    private NotificationEmailConfig oclConfig;

    /** The owners seller config. */
    @Autowired
    private NotificationEmailConfig ownersSellerConfig;

    /** The validation error. */
    @Value( "${owners.affiliate.mail.subject.validate.owners}" )
    private String validationError;

    /** The parsing error. */
    @Value( "${owners.affiliate.mail.subject.parse.owners}" )
    private String parsingError;

    /** The owners subject. */
    @Value( "${owners.affiliate.mail.subject.owners}" )
    private String ownersSubject;

    /** The ocl subject. */
    @Value( "${ocl.affiliate.mail.subject}" )
    private String oclSubject;

    @Value( "${affiliate.mail.lead.failure.label}" )
    private String leadNotGeneratedLabel;

    /** The process failed. */
    @Value( "${affiliate.mail.process.failed.label}" )
    private String processingFailed;

    /** The owners seller lead subject. */
    @Value( "${owners.affiliate.sellerLeads.mail.subject}" )
    private String ownersSellerLeadSubject;

    /** The owners seller lead subject from request results. */
    @Value( "${owners.affiliate.sellerLeads.mail.requestResults.subject}" )
    private String ownersSellerLeadRequestResultSubject;

    /** The owners seller lead subject from homes. */
    @Value( "${owners.affiliate.sellerLeads.mail.homes.subject}" )
    private String ownersSellerLeadHomesSubject;

    /** The affiliate email business service. */
    @Autowired
    private AffiliateEmailBusinessService affiliateEmailBusinessService;

    /** The email error handler. */
    @Autowired
    private AffiliatedEmailErrorHandler emailErrorHandler;

    /** The lock handler. */
    @Autowired
    private AffiliatedMailLockHandler lockHandler;

    /** The ocl email business service. */
    @Autowired
    private OclEmailBusinessService oclEmailBusinessService;
    
    @Autowired
    private EmailScrappingDetailBusinessService emailScrappingDetailBusinessService;
    
    @Autowired
    ZillowHotlineLeadBusinessService zillowHotlineLeadBusinessService;

    /**
     * Creates the java mail properties for IMAP store.
     *
     * @return the properties
     */
    @Bean
    public Properties javaMailProperties() {
        final Properties properties = new Properties();
        properties.put( "mail.imap.socketFactory.class", javax.net.ssl.SSLSocketFactory.class );
        properties.put( "mail.imap.socketFactory.fallback", Boolean.FALSE );
        properties.put( "mail.store.protocol", "imaps" );
        properties.put( "mail.debug", Boolean.TRUE );
        return properties;
    }

    /**
     * Creates the message channel, holds the HUBZU email messages, which
     * needs
     * to be
     * scraped.
     *
     * @return the message channel
     */
    @Bean( name = "hubzuReceiveChannel" )
    public DirectChannel createMessageChannel() {
        return new DirectChannel();
    }
    
    /**
     * Creates the message channel, holds the gravitas marathon
     *  email messages, which needs
     * to be
     * scraped.
     *
     * @return the message channel
     */
    @Bean( name = "gravitasReceiveChannel" )
    public DirectChannel createGravitasMessageChannel() {
        return new DirectChannel();
    }

    
    /**
     * Creates the message channel, holds the gravitas zillow
     *  hotline messages, which needs
     * to be
     * scraped to generate leads.
     *
     * @return the message channel
     */
    @Bean( name = "zillowHotlineLeadReceiveChannel" )
    public DirectChannel createZillowHotlineLeadMessageChannel() {
        return new DirectChannel();
    }


    /**
     * Creates the message channel, holds the OWNERS email messages, which
     * needs
     * to be
     * scraped.
     *
     * @return the message channel
     */
    @Bean( name = "ownersReceiveChannel" )
    public DirectChannel createOwnersMessageChannel() {
        return new DirectChannel();
    }

    /**
     * Creates the message channel, holds the OWNERS Valuations email messages,
     * which needs
     * to be
     * scraped.
     *
     * @return the message channel
     */
    @Bean( name = "valuationsReceiveChannel" )
    public DirectChannel createOwnersValuationsMessageChannel() {
        return new DirectChannel();
    }

	/**
	 * Creates the message channel, holds the loans email messages which
	 * needs to be scraped.
	 *
	 * @return the message channel
	 */
	@Bean( name = "oclReceiveChannel" )
	public DirectChannel createOclMessageChannel() {
		return new DirectChannel();
	}

    /**
     * Creates the message channel, holds the owner email messages which needs
     * to be scraped
     *
     * @return the message channel
     */
    @Bean( name = "ownersSellerLeadRecieveChannel" )
    public DirectChannel createOwnersSellerLeadMessaseChannel() {
        return new DirectChannel();
    }

    /**
     * Hubzu search term strategy.
     *
     * @return the search term strategy
     */
    @Bean
    public SearchTermStrategy hubzuSearchTermStrategy() {
        return new HubzuAffiliateSearchStratergy();
    }

    /**
     * Owners affiliate search strategy.
     *
     * @return the search term strategy
     */
    @Bean
    public SearchTermStrategy ownersAffiliateSearchStratergy() {
        return new OwnersAffiliateSearchStratergy();
    }

    /**
     * Valuations affiliate search stratergy.
     *
     * @return the search term strategy
     */
    @Bean
    public SearchTermStrategy valuationsAffiliateSearchStratergy() {
        return new ValuationsAffiliateSearchStratergy();
    }

	/**
	 * OCL affiliate search stratergy.
	 *
	 * @return the search term strategy
	 */
	@Bean
	public SearchTermStrategy oclAffiliateSearchStratergy() {
		return new OclAffiliateSearchStratergy();
	}

    /**
     * Seller lead search term strategy
     *
     * @return the search term strategy
     */
    @Bean
    public SearchTermStrategy ownersSellerLeadSearchTermStartegy() {
        return new SellerLeadSearchTermStartegy();
    }

    /**
     * The service activator fetch the message from channel and place it to
     * process for scraping to lead generation.
     *
     * @param message
     *            the message
     */
    @ServiceActivator( inputChannel = "hubzuReceiveChannel" )
    public void processMessage( final Message message ) {
        final String lockName = getLockName( message );
        LOGGER.debug( "Hubzu Affiliate message scraping started" );
        if (lockHandler.acquireLock( lockName )) {
            try {
                affiliateEmailBusinessService.scrapeHubzuEmailMessage( message );
            } catch ( AffiliateEmailParsingException epe ) {
                emailErrorHandler.emailParsingErrorHandler( epe, hubzuConfig );
            } catch ( AffiliateEmailValidationException eve ) {
                emailErrorHandler.emailValidationErrorHandler( eve, hubzuConfig );
            } catch ( Exception exp ) {
                emailErrorHandler.affiliateEmailErrorHandler(
                        new AffiliateEmailException( message, exp.getLocalizedMessage(), exp ) );
            }
        }
    }
    
    /**
     * The service activator fetch the message from channel and place it to
     * process for scraping to lead generation.
     *
     * @param message
     *            the message
     */
    @ServiceActivator( inputChannel = "gravitasReceiveChannel" )
    public void processEmailDetails( final Message message ) {
        final String lockName = getLockName( message );
        LOGGER.debug( "Gravitas email details scrapping started");
        if (lockHandler.acquireLock( lockName )) {
            try {
            	emailScrappingDetailBusinessService.scrapeEmailDetails(message);
            } catch ( Exception exp ) {
                emailErrorHandler.affiliateEmailErrorHandler(
                        new AffiliateEmailException( message, exp.getLocalizedMessage(), exp ) );
            }
        }
    }
    
    
    /**
     * The service activator fetch the message from channel and place it to
     * process for scraping to lead generation.
     *
     * @param message
     *            the message
     */
    @ServiceActivator( inputChannel = "zillowHotlineLeadReceiveChannel" )
    public void processZillowHotlineMailBox( final Message message ) {
        final String lockName = getLockName( message );
        LOGGER.info( "zillow Hot line Lead ReceiveChannel scrapping started");
        if (lockHandler.acquireLock( lockName )) {
            try {
            	zillowHotlineLeadBusinessService.scrapeZillowHotlineLeadAccount(message);
            } catch ( Exception exp ) {
                emailErrorHandler.affiliateEmailErrorHandler(
                        new AffiliateEmailException( message, exp.getLocalizedMessage(), exp ) );
            }
        }
    }

    /**
     * The service activator fetch the message from channel and place it to
     * process for scraping to lead generation.
     *
     * @param message
     *            the message
     */
    @ServiceActivator( inputChannel = "ownersReceiveChannel" )
    public void processOwnersMessage( final Message message ) {
        final String lockName = getLockName( message );
        LOGGER.debug( "Owners Affiliate message scraping started" );
        if (lockHandler.acquireLock( lockName )) {
            try {
                affiliateEmailBusinessService.scrapeOwnersEmailMessage( message );
            } catch ( AffiliateEmailParsingException epe ) {
                emailErrorHandler.emailParsingErrorHandler( epe, ownersConfig );
            } catch ( AffiliateEmailValidationException eve ) {
                emailErrorHandler.emailValidationErrorHandler( eve, ownersConfig );
            } catch ( Exception exp ) {
                emailErrorHandler.affiliateEmailErrorHandler(
                        new AffiliateEmailException( message, exp.getLocalizedMessage(), exp ) );
            }
        }
    }

    /**
     * The service activator fetch the message from channel and place it to
     * process for scraping to lead generation.
     *
     * @param message
     *            the message
     */
    @ServiceActivator( inputChannel = "valuationsReceiveChannel" )
    public void processValuationsMessage( final Message message ) {
        LOGGER.debug( "Affiliate message scraping started for valuation mail box" );
        final String lockName = getLockName( message );
        if (lockHandler.acquireLock( lockName )) {
            try {
                affiliateEmailBusinessService.scrapeValuationsEmailMessage( message );
            } catch ( AffiliateEmailParsingException epe ) {
                emailErrorHandler.emailParsingErrorHandler( epe, valuationsConfig );
            } catch ( AffiliateEmailValidationException eve ) {
                emailErrorHandler.emailValidationErrorHandler( eve, valuationsConfig );
            } catch ( Exception exp ) {
                emailErrorHandler.affiliateEmailErrorHandler(
                        new AffiliateEmailException( message, exp.getLocalizedMessage(), exp ) );
            }
        }
    }

	/**
	 * This service activator fetches the message from oclReceiveChannel
	 * and processes it for updating the corresponding opportunity.
	 *
	 * @param message
	 *            the message
	 */
	@ServiceActivator( inputChannel = "oclReceiveChannel" )
	public void processOclMessage( final Message message ) {
		final String lockName = getLockName( message );
		LOGGER.debug( "OCL Affiliate message scraping started" );
		if (lockHandler.acquireLock( lockName )) {
		    try {
		    	oclEmailBusinessService.scrapeOclEmailMessage( message );
		    } catch ( AffiliateEmailParsingException epe ) {
                emailErrorHandler.emailParsingErrorHandler( epe, oclConfig, processingFailed );
            } catch ( AffiliateEmailValidationException eve ) {
                emailErrorHandler.emailValidationErrorHandler( eve, oclConfig, processingFailed );
            } catch ( Exception exp ) {
                emailErrorHandler.affiliateEmailErrorHandler(
                        new AffiliateEmailException( message, exp.getLocalizedMessage(), exp ), processingFailed );
            }
		}
	}

    /**
     * This service activator fetches the message from
     * ownersSellerLeadRecieveChannel and processes
     *
     * @param message
     */
    @ServiceActivator( inputChannel = "ownersSellerLeadRecieveChannel" )
    public void processOwnersSellerLeadMessage( final Message message ) {
        final String lockName = getLockName( message );
        LOGGER.debug( "Owners Affiliate seller lead message scraping started" );
        if (lockHandler.acquireLock( lockName )) {
            try {
                affiliateEmailBusinessService.scrapeOwnersSellerLeadEmailMessage( message );
            } catch ( AffiliateEmailParsingException epe ) {
                emailErrorHandler.emailParsingErrorHandler( epe, ownersSellerConfig );
            } catch ( AffiliateEmailValidationException eve ) {
                emailErrorHandler.emailValidationErrorHandler( eve, ownersSellerConfig );
            } catch ( Exception exp ) {
                emailErrorHandler.affiliateEmailErrorHandler(
                        new AffiliateEmailException( message, exp.getLocalizedMessage(), exp ) );
            }
        }
    }

    /**
     * Gets the lock name.
     *
     * @param message
     *            the message
     * @return the lock name
     */
    private String getLockName( final Message message ) {
        try {
            return String.valueOf( message.getHeader( "Message-Id" )[0].hashCode() );
        } catch ( Exception e ) {
            LOGGER.info( "Problem getting lock name", e );
            return UUID.randomUUID().toString();
        }
    }

    /**
     * The Class HubzuAffiliateSearchStratergy.
     */
    private class HubzuAffiliateSearchStratergy implements SearchTermStrategy {
        /**
         * Generate search term for given flags.
         *
         * @param supportedFlags
         *            the supported flags
         * @param folder
         *            the folder
         * @return the search term
         */
        @Override
        public SearchTerm generateSearchTerm( Flags supportedFlags, Folder folder ) {
            final List< SearchTerm > termsList = new ArrayList<>();
            affiliateEmailBusinessService.getHubzuSubjects()
                    .forEach( subject -> termsList.add( new IncludeSubjectTerm( subject.toLowerCase() ) ) );
            final SearchTerm[] subjectTermArr = new SearchTerm[termsList.size()];
            termsList.toArray( subjectTermArr );
            final OrTerm subjectTerm = new OrTerm( subjectTermArr );

            final FlagTerm unseenTerm = new FlagTerm( new Flags( Flags.Flag.SEEN ), Boolean.FALSE );
            SearchTerm[] searchTerms = { unseenTerm, subjectTerm };

            return new AndTerm( searchTerms );
        }
    }

    /**
     * The Class OwnersAffiliateSearchStratergy.
     */
    private class OwnersAffiliateSearchStratergy implements SearchTermStrategy {
        /**
         * Generate search term for given flags.
         *
         * @param supportedFlags
         *            the supported flags
         * @param folder
         *            the folder
         * @return the search term
         */
        @Override
        public SearchTerm generateSearchTerm( Flags supportedFlags, Folder folder ) {
            final List< SearchTerm > termsList = new ArrayList<>();
            affiliateEmailBusinessService.getOwnersSubjects()
                    .forEach( subject -> termsList.add( new IncludeSubjectTerm( subject.toLowerCase() ) ) );
            final SearchTerm[] subjectTermArr = new SearchTerm[termsList.size()];
            termsList.toArray( subjectTermArr );
            final OrTerm subjectTerm = new OrTerm( subjectTermArr );

            final FlagTerm unseenTerm = new FlagTerm( new Flags( Flags.Flag.SEEN ), Boolean.FALSE );
            SearchTerm[] searchTerms = { unseenTerm, subjectTerm };

            return new AndTerm( searchTerms );
        }
    }

    /**
     * The Class IncludeSubjectTerm, is search to which will only filter the
     * subject of the message, if it starts with defined
     * <code>leadBuilderFactory.getSubjects()<code>
     * affiliate subjects.
     */
    private class IncludeSubjectTerm extends StringTerm {
        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = -1104974711296207468L;

        /**
         * Instantiates a new exclude subject term.
         *
         * @param pattern
         *            the pattern
         * @param ignoreCase
         *            the ignore case
         */
        public IncludeSubjectTerm( final String pattern ) {
            super( pattern );
        }

        /**
         * Matches subject of message if it starts with defined subject from
         * set.
         *
         * @param msg
         *            the msg
         * @return true, if successful
         */
        public boolean match( Message msg ) {
            String subject = null;
            boolean isMatch = Boolean.FALSE;
            try {
                subject = msg.getSubject();
            } catch ( Exception e ) {
                LOGGER.error( "Problem in scraping email", e );
                return isMatch;
            }
            if (StringUtils.isNotBlank( subject ) && subject.toLowerCase().startsWith( getPattern().toLowerCase() )) {
                isMatch = Boolean.TRUE;
            }
            return isMatch;
        }
    }

    /**
     * The Class ValuationsAffiliateSearchStratergy.
     *
     */
    private class ValuationsAffiliateSearchStratergy implements SearchTermStrategy {
        /**
         * Generate search term for given flags.
         *
         * @param supportedFlags
         *            the supported flags
         * @param folder
         *            the folder
         * @return the search term
         */
        @Override
        public SearchTerm generateSearchTerm( Flags supportedFlags, Folder folder ) {
            final FlagTerm unseenTerm = new FlagTerm( new Flags( Flags.Flag.SEEN ), Boolean.FALSE );
            final IncludeSubjectTerm includeSubject = new IncludeSubjectTerm( ownersSubject.toLowerCase() );
            final SearchTerm[] searchTerms = { unseenTerm, includeSubject };
            return new AndTerm( searchTerms );
        }
    }

	/**
	 * The Class OclAffiliateSearchStratergy.
	 */
	private class OclAffiliateSearchStratergy implements SearchTermStrategy {
		/**
		 * Generate search term for given flags.
		 *
		 * @param supportedFlags
		 *            the supported flags
		 * @param folder
		 *            the folder
		 * @return the search term
		 */
		@Override
		public SearchTerm generateSearchTerm( final Flags supportedFlags, final Folder folder ) {
			final FlagTerm unseenTerm = new FlagTerm( new Flags( Flags.Flag.SEEN ), Boolean.FALSE );
			final IncludeSubjectTerm includeSubject = new IncludeSubjectTerm( oclSubject.toLowerCase() );
			final SearchTerm[] searchTerms = { unseenTerm, includeSubject };
			return new AndTerm( searchTerms );
		}
	}

    /**
     * The class SellerLeadSearchTermStartegy
     *
     * @author raviz
     *
     */
    private class SellerLeadSearchTermStartegy implements SearchTermStrategy {

        /**
         * Generate search term for given flags.
         *
         * @param supportedFlags
         *            the supported flags
         * @param folder
         *            the folder
         * @return the search term
         */
        @Override
        public SearchTerm generateSearchTerm( final Flags supportedFlags, final Folder folder ) {
            final FlagTerm unseenTerm = new FlagTerm( new Flags( Flags.Flag.SEEN ), Boolean.FALSE );
            final SearchTerm[] subjectTerms = { new IncludeSubjectTerm( ownersSellerLeadSubject.toLowerCase() ),
                    new IncludeSubjectTerm( ownersSellerLeadRequestResultSubject.toLowerCase() ),
                    new IncludeSubjectTerm( ownersSellerLeadHomesSubject.toLowerCase() ) };
            final SearchTerm[] searchTerm = { unseenTerm, new OrTerm( subjectTerms ) };
            return new AndTerm( searchTerm );
        }
    }
}
