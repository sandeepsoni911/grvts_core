package com.owners.gravitas.business.builder.domain;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.builder.domain.RequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.Request;
import com.owners.gravitas.enums.LeadRequestType;

/**
 * The Class RequestBuilderTest.
 *
 * @author vishwanathm
 */
public class RequestBuilderTest extends AbstractBaseMockitoTest {

    /** The request builder. */
    @InjectMocks
    private RequestBuilder requestBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        OpportunitySource oppSrc = new OpportunitySource();
        oppSrc.setLeadRequestType( LeadRequestType.MAKE_OFFER.getType() );
        Request request = requestBuilder.convertTo( oppSrc );
        Assert.assertNotNull( request );

        oppSrc = new OpportunitySource();
        oppSrc.setLeadRequestType( LeadRequestType.REQUEST_INFORMATION.getType() );
        request = requestBuilder.convertTo( oppSrc );
        Assert.assertNotNull( request );

        oppSrc = new OpportunitySource();
        oppSrc.setLeadRequestType( LeadRequestType.SCHEDULE_TOUR.getType() );
        request = requestBuilder.convertTo( oppSrc );
        Assert.assertNotNull( request );

        oppSrc = new OpportunitySource();
        oppSrc.setLeadRequestType( "test" );
        request = requestBuilder.convertTo( oppSrc );
        Assert.assertNull( request );
    }

    /**
     * Test convert to null source.
     */
    @Test
    public void testConvertToNullSource() {
        Request request = requestBuilder.convertTo( null );
        Assert.assertNull( request );
    }

    /**
     * Test convert to null destination.
     */
    @Test
    public void testConvertToNullDestination() {
        OpportunitySource oppSrc = new OpportunitySource();
        Request request = requestBuilder.convertTo( oppSrc );
        Assert.assertNotNull( request );

        request = requestBuilder.convertTo( null, null );
        Assert.assertNull( request );

        request = requestBuilder.convertTo( oppSrc, new Request() );
        Assert.assertNotNull( request );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        requestBuilder.convertFrom( new Request() );
    }
}
