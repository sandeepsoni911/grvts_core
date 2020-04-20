package com.owners.gravitas.buisness.builder.entity;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.builder.domain.OpportunityBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.Stage;
import com.owners.gravitas.enums.RecordType;

/**
 * The Class OpportunityBuilderTest.
 *
 * @author vishwanathm
 */
public class OpportunityBuilderTest extends AbstractBaseMockitoTest {

    /** The opportunity builder. */
    @InjectMocks
    private OpportunityBuilder opportunityBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        final OpportunitySource oppSrc = new OpportunitySource();
        oppSrc.setStage( "stage" );
        oppSrc.setListingId( "propertyIds" );
        oppSrc.setExpectedContractDate( new Date() );
        oppSrc.setOpportunityType(  RecordType.BUYER.getType() );
        final Opportunity opp = opportunityBuilder.convertTo( oppSrc );
        Assert.assertNotNull( opp );
    }

    /**
     * Test null source.
     */
    @Test
    public void testNullSource() {
        final Opportunity opp = opportunityBuilder.convertTo( null );
        Assert.assertNull( opp );
    }

    /**
     * Test null destination.
     */
    @Test
    public void testNullDestination() {
        final OpportunitySource oppSrc = new OpportunitySource();
        oppSrc.setStage( "stage" );
        oppSrc.setExpectedContractDate( new Date() );
        oppSrc.setOpportunityType( "Buyer" );
        Opportunity opp = opportunityBuilder.convertTo( oppSrc );
        Assert.assertNotNull( opp );

        opp = opportunityBuilder.convertTo( oppSrc, null );
        Assert.assertNotNull( opp );
    }

    /**
     * Test not null destination with buyer opportunity.
     */
    @Test
    public void testNotNullDestinationWithBuyerOpportunity() {
        final OpportunitySource oppSrc = new OpportunitySource();
        oppSrc.setStage( "stage" );
        oppSrc.setExpectedContractDate( new Date() );
        oppSrc.setStageChanged( true );
        oppSrc.setOpportunityType( "Buyer" );
        final Stage stage2 = new Stage();
        stage2.setStage( "CLAIMED" );
        stage2.setTimestamp( 1200L );
        final List< Stage > stageHistory = new ArrayList<>();
        stageHistory.add( stage2 );
        final Opportunity opportunity = new Opportunity();
        opportunity.setOpportunityType( "Buyer" );
        opportunity.setStageHistory( stageHistory );
        opportunity.setStage( "stage" );
        final Opportunity opp = opportunityBuilder.convertTo( oppSrc, opportunity );
        assertNotNull( opp );
    }

    /**
     * Test not null destination with seller opportunity.
     */
    @Test
    public void testNotNullDestinationWithSellerOpportunity() {
        final OpportunitySource oppSrc = new OpportunitySource();
        oppSrc.setStage( "stage" );
        oppSrc.setExpectedContractDate( new Date() );
        oppSrc.setStageChanged( true );
        oppSrc.setOpportunityType( "Seller" );
        final Stage stage2 = new Stage();
        stage2.setStage( "CLAIMED" );
        stage2.setTimestamp( 1200L );
        final List< Stage > stageHistory = new ArrayList<>();
        stageHistory.add( stage2 );
        final Opportunity opportunity = new Opportunity();
        opportunity.setOpportunityType( "Seller" );
        opportunity.setStageHistory( stageHistory );
        opportunity.setStage( "stage" );
        final Opportunity opp = opportunityBuilder.convertTo( oppSrc, opportunity );
        assertNotNull( opp );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        opportunityBuilder.convertFrom( new Opportunity() );
    }

}
