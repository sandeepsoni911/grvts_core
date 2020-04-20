package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.integration.mail.ImapIdleChannelAdapter;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;


/**
 * The Class ApplicationInitListener.
 *
 * @see ApplicationInitEvent
 */
@ManagedResource( objectName = "com.owners.gravitas:name=ApplicationInitListener" )
@Component
public class ApplicationInitListener implements ApplicationListener< ContextRefreshedEvent > {

    /** The hubzu idle channel adapter. */
    @Autowired
    private ImapIdleChannelAdapter hubzuIdleChannelAdapter;

    /** The owners idle channel adapter. */
    @Autowired
    private ImapIdleChannelAdapter ownersIdleChannelAdapter;

    /** The owners idle channel adapter. */
    @Autowired
    private ImapIdleChannelAdapter valuationsIdleChannelAdapter;

    /** The mortgage idle channel adapter. */
    @Autowired
    private ImapIdleChannelAdapter oclIdleChannelAdapter;

    /** owners seller lead channel adapter. */
    @Autowired
    private ImapIdleChannelAdapter ownersSellerLeadChannelAdapter;
    
    /** gravitas email channel adapter */
    @Autowired
    private ImapIdleChannelAdapter gravitasIdleChannelAdapter;
    
    @Autowired
    private ImapIdleChannelAdapter zillowIdleChannelAdapter;
    
    /** The scraping enabled. */
    @Value( "${affiliate.mail.scraping.enabled: true}" )
    private boolean scrapingEnabled;
    

    /** The scraping enabled for zillow hotlin csv lead. */
    @Value( "${zillow.hotline.lead.mail.scraping.enabled: true}" )
    private boolean zillowHotLineLeadScrappingEnabled;


    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.context.ApplicationListener#onApplicationEvent(org.
     * springframework.context.ApplicationEvent)
     */
    @Override
    public void onApplicationEvent( final ContextRefreshedEvent event ) {
        if (scrapingEnabled) {
            startImapAdapters();
        }
        if(zillowHotLineLeadScrappingEnabled) {
        		startZillowHotlineLeadImapAdaptors();
        }
    }

    /**
     * Start imap adapters.
     */
    @ManagedOperation
    public void startImapAdapters() {
        hubzuIdleChannelAdapter.start();
        ownersIdleChannelAdapter.start();
        valuationsIdleChannelAdapter.start();
        oclIdleChannelAdapter.start();
        ownersSellerLeadChannelAdapter.start();
        gravitasIdleChannelAdapter.start();
    }

    /**
     * Stop imap adapters.
     */
    @ManagedOperation
    public void stopImapAdapters() {
        hubzuIdleChannelAdapter.stop();
        ownersIdleChannelAdapter.stop();
        valuationsIdleChannelAdapter.stop();
        oclIdleChannelAdapter.stop();
        ownersSellerLeadChannelAdapter.stop();
        gravitasIdleChannelAdapter.stop();
    }

    /**
     * Start imap adapters.
     */
    @ManagedOperation
    public void startZillowHotlineLeadImapAdaptors() {
    		zillowIdleChannelAdapter.start();
    }

    /**
     * Stop imap adapters.
     */
    @ManagedOperation
    public void stopZillowHotlineLeadImapAdaptors() {
    		zillowIdleChannelAdapter.stop();
    }
    
    /**
     * Checks if is scraping enabled.
     *
     * @return true, if is scraping enabled
     */
    @ManagedAttribute
    public boolean isScrapingEnabled() {
        return scrapingEnabled;
    }

    
    /**
	 * @return the zillowHotLineLeadScrappingEnabled
	 */
    @ManagedAttribute
	public boolean isZillowHotLineLeadScrappingEnabled() {
		return zillowHotLineLeadScrappingEnabled;
	}

	/**
	 * @param zillowHotLineLeadScrappingEnabled
	 *            the zillowHotLineLeadScrappingEnabled to set
	 */
    @ManagedAttribute
	public void setZillowHotLineLeadScrappingEnabled(boolean zillowHotLineLeadScrappingEnabled) {
		this.zillowHotLineLeadScrappingEnabled = zillowHotLineLeadScrappingEnabled;
		propertyWriter.saveJmxProperty("zillow.hotline.lead.mail.scraping.enabled"
				, zillowHotLineLeadScrappingEnabled);
	}

	/**
     * Sets the scraping enabled.
     *
     * @param scrapingEnabled
     *            the new scraping enabled
     */
    @ManagedAttribute
    public void setScrapingEnabled( final boolean scrapingEnabled ) {
        this.scrapingEnabled = scrapingEnabled;
        propertyWriter.saveJmxProperty( "affiliate.mail.scraping.enabled", scrapingEnabled );
    }
}
