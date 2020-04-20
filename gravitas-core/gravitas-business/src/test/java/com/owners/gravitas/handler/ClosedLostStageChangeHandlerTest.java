package com.owners.gravitas.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.flowable.engine.RuntimeService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.BuyerFarmingBusinessService;
import com.owners.gravitas.business.OpportunityBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.enums.BuyerFarmType;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.service.OpportunityService;

/**
 * Test class for ClosedLostStageChangeHandlerTest.
 *
 * @author amits
 *
 */
public class ClosedLostStageChangeHandlerTest extends AbstractBaseMockitoTest {

    /** ClaimedStageChangeHandler. */
    @InjectMocks
    private ClosedLostStageChangeHandler closedLostStageChangeHandler;

    /** The opportunity service. */
    @Mock
    protected OpportunityService opportunityService;

    /** The opportunity business service. */
    @Mock
    private OpportunityBusinessService opportunityBusinessService;

    /** The buyer registration business service. */
    @Mock
    private BuyerFarmingBusinessService buyerFarmingBusinessService;

    /** The runtime service. */
    @Mock
    protected RuntimeService runtimeService;

    /**
     * Before test.
     */
    @BeforeMethod
    public void beforeTest() {
        ReflectionTestUtils.setField( closedLostStageChangeHandler, "lostReasonsStr", "test1,test2" );
        ReflectionTestUtils.invokeMethod( closedLostStageChangeHandler, "initDataBuilder" );
    }

    /**
     * Test handle change_lostfor buyer farming.
     */
    @Test
    public void testHandleChange_lostforBuyerFarming() {
        final Contact contact = new Contact();
        List< String > list = new ArrayList<>();
        list.add( "email1" );
        list.add( "email2" );
        contact.setEmails( list );
        String opportunityId = "opportunityId";
        com.owners.gravitas.domain.entity.Opportunity opportunity = new com.owners.gravitas.domain.entity.Opportunity();
        com.owners.gravitas.domain.entity.Contact contactEntity = new com.owners.gravitas.domain.entity.Contact();
        contactEntity.setCrmId( "test" );
        opportunity.setContact( contactEntity );
        OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setReasonLost( "test1" );
        opportunitySource.setRecordType( RecordType.BUYER.getType() );
        opportunitySource.setPrimaryContact( contact );
        Mockito.when( opportunityService.getOpportunityByFbId( opportunityId, Boolean.FALSE ) )
                .thenReturn( opportunity );
        Mockito.when( opportunityBusinessService.getOpportunity( Mockito.anyString() ) )
                .thenReturn( opportunitySource );
        closedLostStageChangeHandler.handleChange( "agentId", opportunityId, contact );
        Mockito.verify( runtimeService, Mockito.times( 2 ) ).startProcessInstanceByKey( Mockito.anyString(),
                Mockito.any( Map.class ) );
        Mockito.verify( buyerFarmingBusinessService ).updateFarmingStatus( Mockito.anyString(),
                Mockito.any( BuyerFarmType.class ), Mockito.any( boolean.class ) );
    }

    /**
     * Test handle change_lostfor other reason.
     */
    @Test
    public void testHandleChange_lostforOtherReason() {
        final Contact contact = new Contact();
        List< String > list = new ArrayList<>();
        list.add( "email1" );
        list.add( "email2" );
        contact.setEmails( list );
        String opportunityId = "opportunityId";
        com.owners.gravitas.domain.entity.Opportunity opportunity = new com.owners.gravitas.domain.entity.Opportunity();
        com.owners.gravitas.domain.entity.Contact contactEntity = new com.owners.gravitas.domain.entity.Contact();
        contactEntity.setCrmId( "test" );
        opportunity.setContact( contactEntity );
        OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setReasonLost( "test3" );
        opportunitySource.setRecordType( RecordType.BUYER.getType() );
        opportunitySource.setPrimaryContact( contact );
        Mockito.when( opportunityService.getOpportunityByFbId( opportunityId, Boolean.FALSE ) )
                .thenReturn( opportunity );
        Mockito.when( opportunityBusinessService.getOpportunity( Mockito.anyString() ) )
                .thenReturn( opportunitySource );
        closedLostStageChangeHandler.handleChange( "agentId", opportunityId, contact );
        Mockito.verify( runtimeService ).startProcessInstanceByKey( Mockito.anyString(), Mockito.any( Map.class ) );
        Mockito.verify( buyerFarmingBusinessService ).updateFarmingStatus( Mockito.anyString(),
                Mockito.any( BuyerFarmType.class ), Mockito.any( boolean.class ) );
    }

}
