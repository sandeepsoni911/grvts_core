package com.owners.gravitas.business.builder.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.Opportunity;
/**
 * The Class OpportunityBuilderTest.
 *
 * @author shivamm
 */
public class OpportunityBuilderTest extends AbstractBaseMockitoTest {

    /** The opportunity builder. */
    @InjectMocks
    private OpportunityBuilder entityOpportunityBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        final OpportunitySource oppSrc = new OpportunitySource();
        oppSrc.setStage( "stage" );
        final List< String > list = new ArrayList<>();
        list.add( "12345" );
        oppSrc.setExpectedContractDate( new Date() );
        oppSrc.setOpportunityType( "Seller" );
        final Opportunity opp = entityOpportunityBuilder.convertTo( oppSrc );
        Assert.assertNotNull( opp );
    }

    /**
     * Test convert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        entityOpportunityBuilder.convertFrom( new Opportunity() );
    }
}
