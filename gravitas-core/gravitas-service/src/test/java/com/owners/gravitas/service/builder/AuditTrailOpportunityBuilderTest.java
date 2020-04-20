package com.owners.gravitas.service.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.Opportunity;

/**
 * The Class AuditTrailOpportunityBuilderTest.
 *
 * @author shivamm
 */
public class AuditTrailOpportunityBuilderTest extends AbstractBaseMockitoTest {

    /** The audit trail opportunity builder. */
    @InjectMocks
    private AuditTrailOpportunityBuilder auditTrailOpportunityBuilder;

    /**
     * Test convert to with opportunity as null.
     */
    //@Test
    public void testConvertToWithOpportunityAsNull() {
        final OpportunitySource source = new OpportunitySource();
        final Opportunity opportunity = null;
        final Map< String, Object > map = auditTrailOpportunityBuilder.convertTo( source, opportunity );
        assertNotNull( map );
    }

    /**
     * Test convert to with opportunity not null and opportunity type buyer.
     */
    @Test
    public void testConvertToWithOpportunityNotNullAndOpportunityTypeBuyer() {
        final OpportunitySource source = new OpportunitySource();
        final Opportunity opportunity = new Opportunity();
        source.setOpportunityType( "Buyer" );
        source.setStage( "test1" );
        opportunity.setStage( "test2" );
        ReflectionTestUtils.setField( auditTrailOpportunityBuilder, "defaultResponseTime", 60L );
        final Map< String, Object > map = auditTrailOpportunityBuilder.convertTo( source, opportunity );
        assertEquals( ( String ) map.get( "opportunityType" ), "Buyer" );
        assertEquals( ( String ) map.get( "stage" ), "test1" );

    }

    /**
     * Test convert to with opportunity not null and opportunity type seller.
     */
    @Test
    public void testConvertToWithOpportunityNotNullAndOpportunityTypeSeller() {
        final OpportunitySource source = new OpportunitySource();
        final Opportunity opportunity = new Opportunity();
        source.setOpportunityType( "Seller" );
        source.setStage( "test1" );
        opportunity.setStage( "test2" );

        ReflectionTestUtils.setField( auditTrailOpportunityBuilder, "defaultResponseTime", 60L );
        final Map< String, Object > map = auditTrailOpportunityBuilder.convertTo( source, opportunity );
        assertEquals( ( String ) map.get( "opportunityType" ), "Seller" );
        assertEquals( ( String ) map.get( "stage" ), "test1" );

    }
}
