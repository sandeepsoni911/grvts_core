package com.owners.gravitas.service.builder;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ContactAttribute;
import com.owners.gravitas.domain.entity.ObjectAttributeConfig;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.service.ContactService;
import com.owners.gravitas.service.ListingIdDetailsService;
import com.owners.gravitas.service.ObjectAttributeConfigService;
import com.owners.gravitas.service.ObjectTypeService;
import com.owners.gravitas.service.SearchService;

// TODO: Auto-generated Javadoc
/**
 * The Class OpportunityBuilderV1Test.
 *
 * @author amits
 */
public class OpportunityEntityBuilderTest extends AbstractBaseMockitoTest {

    /** The opportunity builder. */
    @InjectMocks
    private OpportunityEntityBuilder opportunityBuilder;

    /** The object type service. */
    @Mock
    private ObjectTypeService objectTypeService;

    /** The object attribute config service. */
    @Mock
    private ObjectAttributeConfigService objectAttributeConfigService;

    /** The contact service. */
    @Mock
    private ContactService contactService;

    /** The search service. */
    @Mock
    private SearchService searchService;

    @Mock
    private ListingIdDetailsService listingIdDetailsService;

    /**
     * Test convert from.
     */
    @Test
    public void testConvertFrom() {
        final Opportunity opp = new Opportunity();
        opp.setStage( "New" );
        opp.setCrmId( "test" );
        opp.setAssignedDtm( 1L );
        opp.setDeleted( false );
        opp.setFirstContactDtm( 2L );
        final List< String > propertyList = new ArrayList();
        propertyList.add( "testListingId" );
        opp.setListingIds( propertyList );
        final Contact contact = new Contact();
        final ContactAttribute cAttr = new ContactAttribute();
        final Set< ContactAttribute > confSet = new HashSet<>();
        final ContactAttribute attr = new ContactAttribute();
        final ObjectType objectType = new ObjectType();
        objectType.setName( "test" );
        final ObjectAttributeConfig configDefault = new ObjectAttributeConfig();
        configDefault.setAttributeName( "test" );
        attr.setObjectAttributeConfig( configDefault );
        attr.setValue( "test" );
        confSet.add( attr );
        contact.setContactAttributes( confSet );
        final Search search = new Search();
        search.setAgentEmail( "testEmail" );
        final Map< String, Object > updateParams = null;
        Mockito.when( searchService.searchByOpportunityId( "testfbid" ) ).thenReturn( search );
        Mockito.when( objectTypeService.findByName( "lead" ) ).thenReturn( objectType );
        Mockito.when( objectAttributeConfigService.getObjectAttributeConfig( Mockito.anyString(),
                Mockito.any( ObjectType.class ) ) ).thenReturn( configDefault );

        final Contact result = opportunityBuilder.convertFrom( contact, opp, "testfbid", updateParams );
        Assert.assertNotNull( result );
    }

    /**
     * Test convert from as opportunity.
     */
    @Test
    public void testConvertFromAsOpportunity() {
        final Opportunity opp = new Opportunity();
        opp.setCrmId( "test" );
        opp.setAssignedDtm( 1L );
        opp.setDeleted( false );
        opp.setFirstContactDtm( 2L );
        final List< String > propertyList = new ArrayList();
        propertyList.add( "testListingId" );
        opp.setListingIds( propertyList );
        final Contact contact = new Contact();
        final ContactAttribute cAttr = new ContactAttribute();
        final Set< ContactAttribute > confSet = new HashSet<>();
        final ContactAttribute attr = new ContactAttribute();
        final ObjectType objectType = new ObjectType();
        objectType.setName( "test" );
        final ObjectAttributeConfig configDefault = new ObjectAttributeConfig();
        configDefault.setAttributeName( "test2" );
        attr.setObjectAttributeConfig( configDefault );
        attr.setValue( "test" );
        confSet.add( attr );
        contact.setContactAttributes( confSet );

        final com.owners.gravitas.domain.entity.Opportunity oppv = new com.owners.gravitas.domain.entity.Opportunity();
        oppv.setAssignedAgentId( "testAgent" );
        oppv.setAssignedDate( new DateTime() );
        oppv.setDeleted( false );
        oppv.setId( "testId" );
        oppv.setOpportunityId( "testopp" );
        final Set< com.owners.gravitas.domain.entity.Opportunity > set = new HashSet<>();
        set.add( oppv );
        contact.setOpportunities( set );
        final Map< String, Object > updateParams = null;
        Mockito.when( objectTypeService.findByName( "lead" ) ).thenReturn( objectType );
        Mockito.when( objectAttributeConfigService.getObjectAttributeConfig( Mockito.anyString(),
                Mockito.any( ObjectType.class ) ) ).thenReturn( new ObjectAttributeConfig() );

        final Contact result = opportunityBuilder.convertFrom( contact, opp, "testfbid", updateParams );
        Assert.assertNotNull( result );
    }

    /**
     * Test convert from with agent email.
     */
    @Test
    public void testConvertFromWithAgentEmail() {
        final Opportunity opp = new Opportunity();
        opp.setStage( "New" );
        opp.setCrmId( "test" );
        opp.setAssignedDtm( 1L );
        opp.setDeleted( false );
        opp.setFirstContactDtm( 2L );
        opp.setAgentOclNotes( "agentOclNotes" );
        final List< String > propertyList = new ArrayList();
        propertyList.add( "testListingId" );
        opp.setListingIds( propertyList );
        final Contact contact = new Contact();
        final ContactAttribute cAttr = new ContactAttribute();
        final Set< ContactAttribute > confSet = new HashSet<>();
        final ContactAttribute attr = new ContactAttribute();
        final ObjectType objectType = new ObjectType();
        objectType.setName( "test" );
        final ObjectAttributeConfig configDefault = new ObjectAttributeConfig();
        configDefault.setAttributeName( "test" );
        attr.setObjectAttributeConfig( configDefault );
        attr.setValue( "test" );
        confSet.add( attr );
        contact.setContactAttributes( confSet );

        Mockito.when( objectTypeService.findByName( "lead" ) ).thenReturn( objectType );
        Mockito.when( objectAttributeConfigService.getObjectAttributeConfig( Mockito.anyString(),
                Mockito.any( ObjectType.class ) ) ).thenReturn( configDefault );

        final Contact result = opportunityBuilder.convertFrom( contact, opp, "testfbid", "testAgent" );
        Assert.assertNotNull( result );
    }

    /**
     * Testconvert from unsupported.
     */

    /**
     * Test convert from with contact null.
     */
    @Test
    public void testConvertFromWithContactNull() {
        final Opportunity opportunity = new Opportunity();
        opportunity.setCrmId( "1234" );
        opportunity.setDeleted( false );
        opportunity.setListingIds( Arrays.asList( "listingid1", "listingid2" ) );
        final ObjectType objectType = new ObjectType();
        final String contactId = "testcontactid";

        final Map< String, Object > map1 = new HashMap< String, Object >();
        map1.put( "Email", "test.user@test1.com" );
        map1.put( "LastName", "test" );
        map1.put( "FirstName", "user" );
        map1.put( "Phone", "123456789" );
        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Contact", map1 );

        final ObjectAttributeConfig objectAttributeConfig = new ObjectAttributeConfig();
        final Search search = new Search();
        search.setAgentEmail( "testEmail" );
        final Map< String, Object > updateParams = null;
        Mockito.when( searchService.searchByOpportunityId( "fbOpportunityId" ) ).thenReturn( search );
        when( objectTypeService.findByName( "opportunity" ) ).thenReturn( objectType );
        when( contactService.findContactIdByOpportunityId( opportunity.getCrmId() ) ).thenReturn( contactId );
        when( contactService.findContactById( contactId, "contactId" ) ).thenReturn( map );
        when( objectAttributeConfigService.getObjectAttributeConfig( Mockito.anyString(), Mockito.eq( objectType ) ) )
                .thenReturn( objectAttributeConfig );

        final Contact contact = opportunityBuilder.convertFrom( null, opportunity, "fbOpportunityId", updateParams );

        Assert.assertNotNull( contact );
        verify( objectTypeService ).findByName( "opportunity" );
        verify( contactService ).findContactIdByOpportunityId( opportunity.getCrmId() );
        verify( contactService ).findContactById( contactId, "contactId" );
        verify( objectAttributeConfigService, atLeastOnce() ).getObjectAttributeConfig( Mockito.anyString(),
                Mockito.eq( objectType ) );
    }

    /**
     * Test convert from with agent email contact null.
     */
    @Test
    public void testConvertFromWithAgentEmailContactNull() {
        final Opportunity opportunity = new Opportunity();
        opportunity.setCrmId( "1234" );
        opportunity.setDeleted( false );
        opportunity.setAgentOclNotes( "agentOclNotes" );
        final ObjectType objectType = new ObjectType();
        final String contactId = "testcontactid";

        final Map< String, Object > map1 = new HashMap< String, Object >();
        map1.put( "Email", "test.user@test1.com" );
        map1.put( "LastName", "test" );
        map1.put( "FirstName", "user" );
        map1.put( "Phone", "123456789" );
        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Contact", map1 );

        final ObjectAttributeConfig objectAttributeConfig = new ObjectAttributeConfig();

        when( objectTypeService.findByName( "opportunity" ) ).thenReturn( objectType );
        when( contactService.findContactIdByOpportunityId( opportunity.getCrmId() ) ).thenReturn( contactId );
        when( contactService.findContactById( contactId, "contactId" ) ).thenReturn( map );
        when( objectAttributeConfigService.getObjectAttributeConfig( Mockito.anyString(), Mockito.eq( objectType ) ) )
                .thenReturn( objectAttributeConfig );

        final Contact contact = opportunityBuilder.convertFrom( null, opportunity, "OpportunityId",
                "test.user1@test1.com" );

        Assert.assertNotNull( contact );
        verify( objectTypeService ).findByName( "opportunity" );
        verify( contactService ).findContactIdByOpportunityId( opportunity.getCrmId() );
        verify( contactService ).findContactById( contactId, "contactId" );
        verify( objectAttributeConfigService, atLeastOnce() ).getObjectAttributeConfig( Mockito.anyString(),
                Mockito.eq( objectType ) );
    }
}
