package com.owners.gravitas.service.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.amazonaws.services.machinelearning.AmazonMachineLearning;
import com.amazonaws.services.machinelearning.model.PredictResult;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.BuyerFarmingConfig;
import com.owners.gravitas.config.LeadOpportunityBusinessConfig;
import com.owners.gravitas.dao.AgentOpportunityDao;
import com.owners.gravitas.dao.OpportunityActionDao;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ContactAttribute;
import com.owners.gravitas.domain.entity.ObjectAttributeConfig;
import com.owners.gravitas.domain.entity.OpportunityAction;
import com.owners.gravitas.domain.entity.RegisteredUser;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.enums.BuyerStage;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.exception.AgentOpportunityNotFoundException;
import com.owners.gravitas.repository.OpportunityActionRepository;
import com.owners.gravitas.repository.RegisteredUserRepository;
import com.owners.gravitas.service.AgentContactService;
import com.owners.gravitas.service.BuyerService;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.builder.OpportunityEntityBuilder;

/**
 * The Class AgentOpportunityServiceImplTest.
 *
 * @author vishwanathm
 */
public class AgentOpportunityServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent opportunity service impl. */
    @InjectMocks
    private AgentOpportunityServiceImpl agentOpportunityServiceImpl;

    /** The amc client. */
    @Mock
    private AmazonMachineLearning amlcClient;

    /** The opportunity dao. */
    @Mock
    private AgentOpportunityDao opportunityDao;

    /** The opportunity service. */
    @Mock
    private OpportunityService opportunityService;

    /** The entity opportunity builder. */
    /*
     * @Mock
     * private OpportunityBuilder entityOpportunityBuilder;
     */

    /** The lead opportunity business config. */
    @Mock
    private LeadOpportunityBusinessConfig leadOpportunityBusinessConfig;

    /** The contact service V 1. */
    @Mock
    private ContactEntityService contactServiceV1;

    /** The entity opportunity builder V 1. */
    @Mock
    private OpportunityEntityBuilder entityOpportunityBuilderV1;

    /** The entity registeredUser repository V 1. */
    @Mock
    private RegisteredUserRepository registeredUserRepository;

    /** The opportunity action dao. */
    @Mock
    private OpportunityActionDao opportunityActionDao;

    /** The opportunity action repository. */
    @Mock
    private OpportunityActionRepository opportunityActionRepository;

    /** The agent contact service. */
    @Mock
    private AgentContactService agentContactService;

    /** The buyer farming config. */
    @Mock
    private BuyerFarmingConfig buyerFarmingConfig;

    /** The buyer service. */
    @Mock
    private BuyerService buyerService;

    /**
     * Test get opportunity by id.
     */
    @Test
    public void testGetOpportunityById() {
        Mockito.when( opportunityDao.getOpportunityById( Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( new Opportunity() );
        final Opportunity opp = agentOpportunityServiceImpl.getOpportunityById( "agentId", "opportunityId" );
        Assert.assertNotNull( opp );
    }

    /**
     * Test get opportunity by id exception.
     */
    @Test( expectedExceptions = AgentOpportunityNotFoundException.class )
    public void testGetOpportunityByIdException() {
        Mockito.when( opportunityDao.getOpportunityById( Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( null );
        agentOpportunityServiceImpl.getOpportunityById( "agentId", "opportunityId" );
    }

    /**
     * Test save opportunity with contact registered.
     */
    @Test
    public void testSaveOpportunityWithRegisteredContact() {
        final String contactEmail = "email@email.com";
        final PostResponse opporunityRes = new PostResponse();
        opporunityRes.setName( "test" );
        final com.owners.gravitas.domain.entity.Contact contact = new com.owners.gravitas.domain.entity.Contact();
        contact.setOwnersComId( "12312313" );
        // final com.owners.gravitas.domain.entity.Opportunity dbOpp = new
        // com.owners.gravitas.domain.entity.Opportunity();
        final Opportunity opportunity = new Opportunity();
        opportunity.setOpportunityType( "Buyer" );
        opportunity.addContact( "test" );
        when( registeredUserRepository.findByEmail( Mockito.anyString() ) ).thenReturn( null );
        // when( entityOpportunityBuilder.convertFrom( opportunity )
        // ).thenReturn( dbOpp );
        when( opportunityDao.saveOpportunity( Mockito.anyString(), Mockito.any( Opportunity.class ) ) )
                .thenReturn( new PostResponse() );
        when( contactServiceV1.findByCrmId( Mockito.anyString() ) ).thenReturn( contact );
        final PostResponse res = agentOpportunityServiceImpl.saveOpportunity( "agentId", "agentEmail", opportunity,
                contactEmail );
        Assert.assertNotNull( res );

        // verify( entityOpportunityBuilder ).convertFrom( opportunity );
        verify( contactServiceV1 ).findByCrmId( ( Mockito.anyString() ) );
        // verify( opportunityService ).save( dbOpp );

        verifyZeroInteractions( registeredUserRepository );
    }

    /**
     * Test save opportunity.
     */
    @Test
    public void testSaveOpportunityWithoutRegisteredContact() {
        final String contactEmail = "email@email.com";
        final PostResponse opporunityRes = new PostResponse();
        opporunityRes.setName( "test" );
        final com.owners.gravitas.domain.entity.Contact contact = new com.owners.gravitas.domain.entity.Contact();
        contact.setOwnersComId( "123123" );
        // final com.owners.gravitas.domain.entity.Opportunity dbOpp = new
        // com.owners.gravitas.domain.entity.Opportunity();
        final Opportunity opportunity = new Opportunity();
        opportunity.setOpportunityType( "Buyer" );
        opportunity.addContact( "test" );
        final RegisteredUser user = new RegisteredUser();
        user.setUserId( "123123" );
        when( registeredUserRepository.findByEmail( Mockito.anyString() ) ).thenReturn( user );
        // when( entityOpportunityBuilder.convertFrom( opportunity )
        // ).thenReturn( dbOpp );
        when( opportunityDao.saveOpportunity( Mockito.anyString(), Mockito.any( Opportunity.class ) ) )
                .thenReturn( new PostResponse() );
        when( contactServiceV1.findByCrmId( Mockito.anyString() ) ).thenReturn( contact );
        final PostResponse res = agentOpportunityServiceImpl.saveOpportunity( "agentId", "agentEmail", opportunity,
                contactEmail );
        Assert.assertNotNull( res );
        // verify( entityOpportunityBuilder ).convertFrom( opportunity );
        verify( contactServiceV1 ).findByCrmId( ( Mockito.anyString() ) );
        // verify( opportunityService ).save( dbOpp );

        Assert.assertEquals( contact.getOwnersComId(), user.getUserId(), "contact uuid not matching!!" );
    }

    /**
     * Test save opportunity no registration at both systems.
     */
    @Test
    public void testSaveOpportunityNoRegistrationAtBothSystems() {
        ReflectionTestUtils.setField( buyerFarmingConfig, "buyerFarmingEnabled", true );
        ReflectionTestUtils.setField( buyerFarmingConfig, "buyerAutoRegistrationEnabled", true );
        final String contactEmail = "email@email.com";
        final PostResponse opporunityRes = new PostResponse();
        opporunityRes.setName( "test" );
        final com.owners.gravitas.domain.entity.Contact contact = new com.owners.gravitas.domain.entity.Contact();
        // final com.owners.gravitas.domain.entity.Opportunity dbOpp = new
        // com.owners.gravitas.domain.entity.Opportunity();
        final Opportunity opportunity = new Opportunity();
        final Set< ContactAttribute > contactAttributes = new HashSet< ContactAttribute >();
        final ContactAttribute attr = new ContactAttribute();
        final ObjectAttributeConfig conf = new ObjectAttributeConfig();
        conf.setAttributeName( "state" );
        attr.setObjectAttributeConfig( conf );
        contactAttributes.add( attr );
        contact.setContactAttributes( contactAttributes );
        opportunity.setOpportunityType( "Buyer" );
        when( registeredUserRepository.findByEmail( Mockito.anyString() ) ).thenReturn( null );
        when( buyerService.isBuyerAutoRegistrationEmail( Mockito.anyString() ) ).thenReturn( true );
        when( buyerService.isFarmLongTermState( Mockito.anyString() ) ).thenReturn( true );
        // when( entityOpportunityBuilder.convertFrom( opportunity )
        // ).thenReturn( dbOpp );
        when( opportunityDao.saveOpportunity( Mockito.anyString(), Mockito.any( Opportunity.class ) ) )
                .thenReturn( new PostResponse() );
        when( contactServiceV1.findByCrmId( Mockito.anyString() ) ).thenReturn( contact );
        final PostResponse res = agentOpportunityServiceImpl.saveOpportunity( "agentId", "agentEmail", opportunity,
                contactEmail );
        Assert.assertNotNull( res );
        // verify( entityOpportunityBuilder ).convertFrom( opportunity );
        verify( contactServiceV1 ).findByCrmId( ( Mockito.anyString() ) );
        // verify( opportunityService ).save( dbOpp );

        verify( registeredUserRepository ).findByEmail( Mockito.anyString() );
        Assert.assertEquals( contact.getOwnersComId(), null, "contact uuid not matching!!" );
    }

    /**
     * Test update opportunity.
     */
    /*
     * @Test
     * public void testUpdateOpportunity() {
     * final com.owners.gravitas.domain.entity.Opportunity dbOpp = new
     * com.owners.gravitas.domain.entity.Opportunity();
     * final Opportunity opportunity = new Opportunity();
     * Mockito.when( entityOpportunityBuilder.convertFrom( opportunity, dbOpp )
     * ).thenReturn( dbOpp );
     * Mockito.when( opportunityService.getOpportunityByOpportunityId(
     * "opportunityId" ) ).thenReturn( dbOpp );
     * Mockito.doNothing().when( opportunityDao ).updateOpportunity(
     * Mockito.anyString(), Mockito.anyString(),
     * Mockito.any( Opportunity.class ) );
     * agentOpportunityServiceImpl.updateOpportunity( "agentId", "agentEmail",
     * "opportunityId", opportunity );
     * Mockito.verify( opportunityDao ).updateOpportunity( Mockito.anyString(),
     * Mockito.anyString(),
     * Mockito.any( Opportunity.class ) );
     * Mockito.verify( opportunityService ).save( dbOpp );
     * }
     */

    /**
     * Test patch opportunity.
     */
    @Test
    public void testPatchOpportunity() {
        ReflectionTestUtils.invokeMethod( agentOpportunityServiceImpl, "initializeOpportunityFieldsMap", null );
        final Map< String, Object > params = new HashMap<>();
        params.put( "deleted", false );
        final Opportunity domainOpp = new Opportunity();
        Mockito.when( opportunityDao.patchOpportunity( "agentId", "opportunityId", params ) ).thenReturn( domainOpp );
        // final com.owners.gravitas.domain.entity.Opportunity dbOpp = new
        // com.owners.gravitas.domain.entity.Opportunity();
        // Mockito.when( opportunityService.getOpportunityByOpportunityId(
        // "opportunityId" ) ).thenReturn( dbOpp );
        // when( entityOpportunityBuilder.convertFrom( domainOpp, dbOpp )
        // ).thenReturn( dbOpp );
        final Opportunity opp = agentOpportunityServiceImpl.patchOpportunity( "agentId", "opportunityId", params,
                domainOpp, "test@test.com" );
        Assert.assertNotNull( opp );
        // Mockito.verify( opportunityService ).save( dbOpp );
    }

    /**
     * Test patch opportunity unassign.
     */
    @Test
    public void testPatchOpportunityUnassign() {
        ReflectionTestUtils.invokeMethod( agentOpportunityServiceImpl, "initializeOpportunityFieldsMap", null );
        final Map< String, Object > params = new HashMap<>();
        params.put( "deleted", true );
        final Opportunity domainOpp = new Opportunity();
        final List< String > actionFlowIds = new ArrayList();
        actionFlowIds.add( "test" );
        domainOpp.setActionFlowIds( actionFlowIds );
        final List< OpportunityAction > actionFlowList = new ArrayList();
        final OpportunityAction actFlow = new OpportunityAction();
        actionFlowList.add( actFlow );
        Mockito.when( opportunityActionRepository.findByActionFlowId( "test" ) ).thenReturn( actionFlowList );
        Mockito.when( opportunityDao.patchOpportunity( "agentId", "opportunityId", params ) ).thenReturn( domainOpp );
        // final com.owners.gravitas.domain.entity.Opportunity dbOpp = new
        // com.owners.gravitas.domain.entity.Opportunity();
        // Mockito.when( opportunityService.getOpportunityByOpportunityId(
        // "opportunityId" ) ).thenReturn( dbOpp );
        // when( entityOpportunityBuilder.convertFrom( domainOpp, dbOpp )
        // ).thenReturn( dbOpp );
        when( leadOpportunityBusinessConfig.isContactDatabaseConfig() ).thenReturn( true );
        final Opportunity opp = agentOpportunityServiceImpl.patchOpportunity( "agentId", "opportunityId", params,
                domainOpp, "test@test.com" );

        Assert.assertNotNull( opp );
        // Mockito.verify( opportunityService ).save( dbOpp );
    }

    /**
     * Test get opportunity score.
     */
    @Test
    public void testGetOpportunityScore() {
        Mockito.when( amlcClient.predict( Mockito.any() ) ).thenReturn( new PredictResult() );
        final PredictResult result = agentOpportunityServiceImpl.getOpportunityScore( new HashMap< String, String >() );
        Assert.assertNotNull( result );
    }

    /**
     * Test get agent new opportunities count.
     */
    @Test
    public void testGetAgentNewOpportunitiesCount() {
        Mockito.when( opportunityDao.getAgentNewOpportunitiesCount( Mockito.anyString() ) ).thenReturn( 10 );
        final int count = agentOpportunityServiceImpl.getAgentNewOpportunitiesCount( "agentId" );
        Mockito.verify( opportunityDao ).getAgentNewOpportunitiesCount( Mockito.anyString() );
        Assert.assertEquals( count, 10 );
    }

    /**
     * Test has agent claimed opportunity with open tasks.
     */
    @Test
    public void testHasAgentClaimedOpportunityWithOpenTasks() {
        Mockito.when( opportunityDao.hasAgentClaimedOpportunityWithOpenTasks( Mockito.anyString() ) )
                .thenReturn( Boolean.FALSE );
        final Boolean flag = agentOpportunityServiceImpl.hasAgentClaimedOpportunityWithOpenTasks( "agentId" );
        Mockito.verify( opportunityDao ).hasAgentClaimedOpportunityWithOpenTasks( "agentId" );
        Assert.assertEquals( flag, Boolean.FALSE );
    }

    /**
     * Test get opportunity count by days.
     */
    @Test
    public void testGetOpportunityCountByDays() {
        final String agentId = "testAgentId";
        final long fromDtm = 0L;
        final int actualCount = 1;
        when( opportunityDao.getOpportunityCountByDays( agentId, fromDtm ) ).thenReturn( actualCount );
        final int expectedCount = agentOpportunityServiceImpl.getOpportunityCountByDays( agentId, fromDtm );
        assertEquals( actualCount, expectedCount );
    }

    /**
     * Test has incomplete action flow should return true when action flows are
     * incomplete.
     */
    @Test
    public void testHasIncompleteActionFlowShouldReturnTrueWhenActionFlowsAreIncomplete() {
        final String agentEmail = "test@test.com";
        final Object expectedValue = 1;
        when( opportunityActionDao.getIncompleteActionFlowCount( agentEmail ) ).thenReturn( expectedValue );
        final boolean hasIncompleteActionFlow = agentOpportunityServiceImpl.hasIncompleteActionFlow( agentEmail );
        assertTrue( hasIncompleteActionFlow );
    }

    /**
     * Test has incomplete action flow should return false when action flows are
     * complete.
     */
    @Test
    public void testHasIncompleteActionFlowShouldReturnFalseWhenActionFlowsAreComplete() {
        final String agentEmail = "test@test.com";
        final Object expectedValue = 0;
        when( opportunityActionDao.getIncompleteActionFlowCount( agentEmail ) ).thenReturn( expectedValue );
        final boolean hasIncompleteActionFlow = agentOpportunityServiceImpl.hasIncompleteActionFlow( agentEmail );
        assertFalse( hasIncompleteActionFlow );
    }

    /**
     * Test get top price for hyphen sign.
     */
    @Test
    public void testGetTopPrice_ForHyphenSign() {
        final String priceRange = "100K - 500K";
        final int expected = 500000;
        final int actual = agentOpportunityServiceImpl.getTopPrice( priceRange );
        assertEquals( actual, expected );
    }

    /**
     * Test get top price for plus sign.
     */
    @Test
    public void testGetTopPrice_ForPlusSign() {
        final String priceRange = "1M+";
        final int expected = 1000000;
        final int actual = agentOpportunityServiceImpl.getTopPrice( priceRange );
        assertEquals( actual, expected );
    }

    /**
     * Test get top price should return zero for invalid range.
     */
    @Test
    public void testGetTopPrice_ShouldReturnZeroForInvalidRange() {
        final String priceRange = "100K  500K";
        final int expected = 0;
        final int actual = agentOpportunityServiceImpl.getTopPrice( priceRange );
        assertEquals( actual, expected );
    }

    /**
     * Test patch opportunity for opp data.
     */
    @Test
    public void testPatchOpportunityForOppData() {
        final String agentId = "agentId";
        final String opportunityId = "opportunityId";
        final Map< String, Object > opportunityData = new HashMap<>();
        final String agentEmail = "agentEmail";
        agentOpportunityServiceImpl.patchOpportunity( agentId, agentEmail, opportunityId, opportunityData );
        verify( opportunityDao ).patchOpportunity( agentId, opportunityId, opportunityData );
    }

    /**
     * Test patch opportunity for CT A for new buyer opportunity.
     */
    @Test
    public void testPatchOpportunityForCTA_ForNewBuyerOpportunity() {
        final String agentId = "agentId";
        final String fbOpportunityId = "fbOpportunityId";
        final Opportunity opportunity = new Opportunity();
        opportunity.setOpportunityType( RecordType.BUYER.getType() );
        opportunity.setStage( BuyerStage.NEW.getStage() );
        final Long assignedDtm = DateTime.now().minusDays( 2 ).getMillis();
        opportunity.setAssignedDtm( assignedDtm );

        final String contactEmail = "a@a.com";
        final Contact contact = new Contact();
        final Set< com.owners.gravitas.domain.entity.Opportunity > opportunities = new HashSet<>();
        final com.owners.gravitas.domain.entity.Opportunity opV1 = new com.owners.gravitas.domain.entity.Opportunity();
        opV1.setOpportunityId( fbOpportunityId );
        opportunities.add( opV1 );
        contact.setOpportunities( opportunities );
        final Opportunity expected = new Opportunity();
        final Map< String, Object > updateParams = null;

        when( contactServiceV1.findByCrmId( opportunity.getCrmId() ) ).thenReturn( contact );
        when( opportunityDao.patchOpportunity( anyString(), anyString(), anyMap() ) ).thenReturn( expected );

        final Opportunity actual = agentOpportunityServiceImpl.patchOpportunityForCTA( agentId, fbOpportunityId,
                opportunity, contactEmail );

        assertEquals( actual, expected );
        verify( entityOpportunityBuilderV1 ).convertFrom( contact, opportunity, fbOpportunityId, updateParams );
        verify( contactServiceV1 ).save( contact );
        verify( opportunityDao ).patchOpportunity( anyString(), anyString(), anyMap() );
    }

    /**
     * Test patch opportunity for CT A for claimed seller opportunity.
     */
    @Test
    public void testPatchOpportunityForCTA_ForClaimedSellerOpportunity() {
        final String agentId = "agentId";
        final String fbOpportunityId = "fbOpportunityId";
        final Opportunity opportunity = new Opportunity();
        opportunity.setOpportunityType( RecordType.SELLER.getType() );
        opportunity.setStage( BuyerStage.CLAIMED.getStage() );
        final Long assignedDtm = DateTime.now().minusDays( 2 ).getMillis();
        opportunity.setAssignedDtm( assignedDtm );

        final String contactEmail = "a@a.com";
        final Contact contact = new Contact();
        final Set< com.owners.gravitas.domain.entity.Opportunity > opportunities = new HashSet<>();
        final com.owners.gravitas.domain.entity.Opportunity opp = new com.owners.gravitas.domain.entity.Opportunity();
        opp.setOpportunityId( fbOpportunityId );
        opportunities.add( opp );
        contact.setOpportunities( opportunities );
        final Opportunity expected = new Opportunity();
        final Map< String, Object > updateParams = null;

        // when( opportunityService.getOpportunityByOpportunityId(
        // fbOpportunityId ) ).thenReturn( opportunityEntity );
        // when( entityOpportunityBuilder.convertFrom( opportunity,
        // opportunityEntity ) ).thenReturn( opportunityEntity );
        when( contactServiceV1.findByCrmId( opportunity.getCrmId() ) ).thenReturn( contact );
        when( opportunityDao.patchOpportunity( anyString(), anyString(), anyMap() ) ).thenReturn( expected );

        final Opportunity actual = agentOpportunityServiceImpl.patchOpportunityForCTA( agentId, fbOpportunityId,
                opportunity, contactEmail );

        assertEquals( actual, expected );
        // verify( opportunityService ).save( opportunityEntity );
        verify( entityOpportunityBuilderV1 ).convertFrom( contact, opportunity, fbOpportunityId, updateParams );
        verify( contactServiceV1 ).save( contact );
        verify( opportunityDao ).patchOpportunity( anyString(), anyString(), anyMap() );
    }

    /**
     * Test patch opportunity for CT A when opp update notequired.
     */
    @Test
    public void testPatchOpportunityForCTA_WhenOppUpdateNotequired() {
        final String agentId = "agentId";
        final String fbOpportunityId = "fbOpportunityId";
        final Opportunity opportunity = new Opportunity();
        opportunity.setOpportunityType( RecordType.SELLER.getType() );
        opportunity.setStage( BuyerStage.CLAIMED.getStage() );
        final Long assignedDtm = DateTime.now().minusDays( 2 ).getMillis();
        opportunity.setAssignedDtm( assignedDtm );
        opportunity.setFirstContactDtm( assignedDtm );

        final String contactEmail = "a@a.com";
        final Contact contact = new Contact();
        final Set< com.owners.gravitas.domain.entity.Opportunity > opportunities = new HashSet<>();
        final com.owners.gravitas.domain.entity.Opportunity opV1 = new com.owners.gravitas.domain.entity.Opportunity();
        opV1.setOpportunityId( fbOpportunityId );
        opportunities.add( opV1 );
        contact.setOpportunities( opportunities );
        final Opportunity expected = new Opportunity();

        when( contactServiceV1.findByCrmId( opportunity.getCrmId() ) ).thenReturn( contact );
        when( opportunityDao.patchOpportunity( anyString(), anyString(), anyMap() ) ).thenReturn( expected );

        final Opportunity actual = agentOpportunityServiceImpl.patchOpportunityForCTA( agentId, fbOpportunityId,
                opportunity, contactEmail );

        assertEquals( actual, expected );
        verify( contactServiceV1 ).save( contact );
        verify( opportunityDao ).patchOpportunity( anyString(), anyString(), anyMap() );
    }

    /**
     * Test patch opportunity for CT A when opp update notequired 2.
     */
    @Test
    public void testPatchOpportunityForCTA_WhenOppUpdateNotequired2() {
        final String agentId = "agentId";
        final String fbOpportunityId = "fbOpportunityId";
        final Opportunity opportunity = new Opportunity();
        opportunity.setOpportunityType( RecordType.BOTH.getType() );
        opportunity.setStage( BuyerStage.CLAIMED.getStage() );
        final Long assignedDtm = DateTime.now().minusDays( 2 ).getMillis();
        opportunity.setAssignedDtm( assignedDtm );

        final String contactEmail = "a@a.com";
        final Contact contact = new Contact();
        final Set< com.owners.gravitas.domain.entity.Opportunity > opportunities = new HashSet<>();
        final com.owners.gravitas.domain.entity.Opportunity opV1 = new com.owners.gravitas.domain.entity.Opportunity();
        opV1.setOpportunityId( fbOpportunityId );
        opportunities.add( opV1 );
        contact.setOpportunities( opportunities );
        final Opportunity expected = new Opportunity();

        when( contactServiceV1.findByCrmId( opportunity.getCrmId() ) ).thenReturn( contact );
        when( opportunityDao.patchOpportunity( anyString(), anyString(), anyMap() ) ).thenReturn( expected );

        final Opportunity actual = agentOpportunityServiceImpl.patchOpportunityForCTA( agentId, fbOpportunityId,
                opportunity, contactEmail );

        assertEquals( actual, expected );
        verify( contactServiceV1 ).save( contact );
        verify( opportunityDao ).patchOpportunity( anyString(), anyString(), anyMap() );
    }

    /**
     * Test patch opportunity for CT A when opp update notequired 3.
     */
    @Test
    public void testPatchOpportunityForCTA_WhenOppUpdateNotequired3() {
        final String agentId = "agentId";
        final String fbOpportunityId = "fbOpportunityId";
        final Opportunity opportunity = new Opportunity();
        opportunity.setOpportunityType( RecordType.BUYER.getType() );
        opportunity.setStage( BuyerStage.IN_CONTACT.getStage() );
        final Long assignedDtm = DateTime.now().minusDays( 2 ).getMillis();
        opportunity.setAssignedDtm( assignedDtm );

        final String contactEmail = "a@a.com";
        final Contact contact = new Contact();
        final Set< com.owners.gravitas.domain.entity.Opportunity > opportunities = new HashSet<>();
        final com.owners.gravitas.domain.entity.Opportunity opV1 = new com.owners.gravitas.domain.entity.Opportunity();
        opV1.setOpportunityId( fbOpportunityId );
        opportunities.add( opV1 );
        contact.setOpportunities( opportunities );
        final Opportunity expected = new Opportunity();

        when( contactServiceV1.findByCrmId( opportunity.getCrmId() ) ).thenReturn( contact );
        when( opportunityDao.patchOpportunity( anyString(), anyString(), anyMap() ) ).thenReturn( expected );

        final Opportunity actual = agentOpportunityServiceImpl.patchOpportunityForCTA( agentId, fbOpportunityId,
                opportunity, contactEmail );

        assertEquals( actual, expected );
        verify( contactServiceV1 ).save( contact );
        verify( opportunityDao ).patchOpportunity( anyString(), anyString(), anyMap() );
    }
}
