package com.owners.gravitas.builder;

import java.util.HashMap;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.builder.LeadFollowupNotificationBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.service.impl.CRMQueryServiceImpl;

/**
 * The Class LeadFollowupNotificationBuilderTest.
 *
 * @author amits
 */
public class LeadFollowupNotificationBuilderTest extends AbstractBaseMockitoTest {

    /** The marketing email notification builder. */
    @InjectMocks
    LeadFollowupNotificationBuilder leadFollowupNotificationBuilder;

    @Mock
    CRMQueryServiceImpl crmQueryServiceImpl;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        Mockito.when( crmQueryServiceImpl.findOne( Mockito.anyString(), Mockito.any( QueryParams.class ) ) )
                .thenReturn( new HashMap< String, Object >() );
        final LeadSource source = new LeadSource();
        source.setFirstName( "test" );
        leadFollowupNotificationBuilder.convertTo( source );
        Mockito.verify( crmQueryServiceImpl ).findOne( Mockito.anyString(), Mockito.any( QueryParams.class ) );
    }

    /**
     * Test convert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFrom() {
        leadFollowupNotificationBuilder.convertFrom( new EmailNotification() );
    }

}
