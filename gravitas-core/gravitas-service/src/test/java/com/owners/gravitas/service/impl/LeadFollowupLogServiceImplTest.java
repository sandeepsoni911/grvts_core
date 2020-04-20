package com.owners.gravitas.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.MarketingEmailLog;
import com.owners.gravitas.repository.MarketingEmailLogRepositiry;

/**
 * The Class LeadFollowupLogServiceImplTest.
 *
 * @author vishwanathm
 */
public class LeadFollowupLogServiceImplTest extends AbstractBaseMockitoTest {

    /** The marketing email log service impl. */
    @InjectMocks
    private LeadFollowupLogServiceImpl leadFollowupLogServiceImpl;

    /** The marketing email log repositiry. */
    @Mock
    private MarketingEmailLogRepositiry marketingEmailLogRepositiry;

    /**
     * Test get marketing email log buy lead id.
     */
    @Test
    public void testGetMarketingEmailLogBuyLeadId() {
        Mockito.when( marketingEmailLogRepositiry.findByLeadId( Mockito.anyString() ) )
                .thenReturn( new MarketingEmailLog() );
        final MarketingEmailLog log = leadFollowupLogServiceImpl.getMarketingEmailLogBuyLeadId( "leadId" );
        Mockito.verify( marketingEmailLogRepositiry ).findByLeadId( Mockito.anyString() );
        Assert.assertNotNull( log );
    }

    /**
     * Test save marketing email log buy lead id.
     */
    @Test
    public void testSaveMarketingEmailLogBuyLeadId() {
        final MarketingEmailLog log = new MarketingEmailLog();
        Mockito.when( marketingEmailLogRepositiry.save( Mockito.any( MarketingEmailLog.class ) ) ).thenReturn( log );
        final MarketingEmailLog logSaved = leadFollowupLogServiceImpl.saveMarketingEmailLog( log );
        Mockito.verify( marketingEmailLogRepositiry ).save( Mockito.any( MarketingEmailLog.class ) );
        Assert.assertNotNull( logSaved );
    }

    /**
     * Test delete marketing email log buy lead id.
     */
    @Test
    public void testDeleteMarketingEmailLogBuyLeadId() {
        final MarketingEmailLog log = new MarketingEmailLog();
        Mockito.doNothing().when( marketingEmailLogRepositiry ).delete( Mockito.any( MarketingEmailLog.class ) );
        leadFollowupLogServiceImpl.deleteMarketingEmailLog( log );
        Mockito.verify( marketingEmailLogRepositiry ).delete( Mockito.any( MarketingEmailLog.class ) );
    }
}
