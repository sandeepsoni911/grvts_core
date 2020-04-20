package com.owners.gravitas.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.AgentHolder;
import com.owners.gravitas.repository.ListingIdDetailsRepository;
import com.owners.gravitas.service.ListingIdDetailsService;

/**
 * The Class ListingIdDetailsServiceImplTest.
 *
 * @author shivamm
 */
public class ListingIdDetailsServiceImplTest extends AbstractBaseMockitoTest {

    /** The listing id details service impl. */
    @InjectMocks
    private ListingIdDetailsServiceImpl listingIdDetailsServiceImpl;

    /** The listing id details repository. */
    @Mock
    private ListingIdDetailsRepository listingIdDetailsRepository;

    /**
     * Test asave agent.
     */
    @Test
    public void testAsaveAgent() {
        String opportunityId = "test";
        listingIdDetailsServiceImpl.delete( opportunityId );
        Mockito.verify( listingIdDetailsRepository ).deleteInBulkByOpportunityId( opportunityId );
    }
}
