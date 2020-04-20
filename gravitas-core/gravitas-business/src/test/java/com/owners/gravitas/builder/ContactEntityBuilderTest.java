package com.owners.gravitas.builder;

import static org.testng.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.ContactEntityBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ContactJsonAttribute;
import com.owners.gravitas.domain.entity.ObjectAttributeConfig;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.enums.ContactJsonAttributeType;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.ObjectAttributeConfigService;
import com.owners.gravitas.service.ObjectTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class ContactBuildertTest.
 *
 * @author amits
 */
public class ContactEntityBuilderTest extends AbstractBaseMockitoTest {

    /** The contact builder. */
    @InjectMocks
    private ContactEntityBuilder contactBuilder;

    /** The object type service. */
    @Mock
    private ObjectTypeService objectTypeService;

    /** The object attribute config service. */
    @Mock
    private ObjectAttributeConfigService objectAttributeConfigService;

    /** The contact service V 1. */
    @Mock
    private ContactEntityService contactServiceV1;

    /**
     * Test convert to should return contact when source is not null and
     * destination is null.
     */
    @Test
    public void testConvertToShouldReturnContactWhenSourceIsNotNullAndDestinationIsNull() {
        final LeadRequest source = new LeadRequest();
        source.setFirstName( "testFirstName" );
        source.setLastName( "testLastName" );
        source.setEmail( "testEmail1" );
        source.setPreferredContactMethod( "testPreferredContactMethod" );
        source.setPreferredContactTime( "testPreferredContactTime" );
        source.setPhone( "testPhone" );
        source.setSavedSearchValues( "test" );
        source.setPropertyTourInformation( "test" );
        source.setAdditionalPropertyData( "test" );
        source.setWebsiteSessionData( "test" );
        source.setOrderId( "test" );
        final ObjectType objectType = new ObjectType();
        objectType.setName( "test" );
        final ObjectAttributeConfig configDefault = new ObjectAttributeConfig();
        configDefault.setAttributeName( "test" );
        final ObjectAttributeConfig config1 = new ObjectAttributeConfig();
        config1.setAttributeName( ContactJsonAttributeType.SAVED_SEARCH_VALUES.getKey() );
        final ObjectAttributeConfig config2 = new ObjectAttributeConfig();
        config2.setAttributeName( ContactJsonAttributeType.PROPERTY_TOUR_INFORMATION.getKey() );
        final ObjectAttributeConfig config3 = new ObjectAttributeConfig();
        config3.setAttributeName( ContactJsonAttributeType.ADDITIONAL_PROPERTY_DATA.getKey() );
        final ObjectAttributeConfig config4 = new ObjectAttributeConfig();
        config4.setAttributeName( ContactJsonAttributeType.WEBSITE_SESSION_DATA.getKey() );
        final ObjectAttributeConfig config5 = new ObjectAttributeConfig();
        config5.setAttributeName( ContactJsonAttributeType.ORDER_ID.getKey() );
        Mockito.when( objectTypeService.findByName( "lead" ) ).thenReturn( objectType );
        Mockito.when( objectAttributeConfigService.getObjectAttributeConfig( Mockito.anyString(),
                Mockito.any( ObjectType.class ) ) ).thenReturn( configDefault );
        Mockito.when( objectAttributeConfigService
                .getObjectAttributeConfig( ContactJsonAttributeType.SAVED_SEARCH_VALUES.getKey(), objectType ) )
                .thenReturn( config1 );
        Mockito.when( objectAttributeConfigService
                .getObjectAttributeConfig( ContactJsonAttributeType.PROPERTY_TOUR_INFORMATION.getKey(), objectType ) )
                .thenReturn( config2 );
        Mockito.when( objectAttributeConfigService
                .getObjectAttributeConfig( ContactJsonAttributeType.ADDITIONAL_PROPERTY_DATA.getKey(), objectType ) )
                .thenReturn( config3 );
        Mockito.when( objectAttributeConfigService
                .getObjectAttributeConfig( ContactJsonAttributeType.WEBSITE_SESSION_DATA.getKey(), objectType ) )
                .thenReturn( config4 );
        Mockito.when( objectAttributeConfigService.getObjectAttributeConfig( ContactJsonAttributeType.ORDER_ID.getKey(),
                objectType ) ).thenReturn( config5 );
        final com.owners.gravitas.domain.entity.Contact destination = contactBuilder.convertTo( source );
        assertNotNull( destination );

    }

    /**
     * Test convert to should return contact when source is not null and
     * destination is null for multi value json.
     */
    @Test
    public void testConvertToShouldReturnContactWhenSourceIsNotNullAndDestinationIsNullForMultiValueJson() {
        final LeadRequest source = new LeadRequest();
        source.setFirstName( "testFirstName" );
        source.setLastName( "testLastName" );
        source.setEmail( "testEmail1" );
        source.setPreferredContactMethod( "testPreferredContactMethod" );
        source.setPreferredContactTime( "testPreferredContactTime" );
        source.setPhone( "testPhone" );
        source.setSavedSearchValues( "test" );
        source.setPropertyTourInformation( "test" );
        source.setAdditionalPropertyData( "test" );
        source.setWebsiteSessionData( "test" );
        source.setOrderId( "test" );
        final Contact destination = new Contact();
        final Set< ContactJsonAttribute > confSet = new HashSet<>();
        final ContactJsonAttribute attr = new ContactJsonAttribute();
        final ObjectType objectType = new ObjectType();
        objectType.setName( "test" );
        final ObjectAttributeConfig configDefault = new ObjectAttributeConfig();
        configDefault.setAttributeName( "test" );
        final ObjectAttributeConfig config1 = new ObjectAttributeConfig();
        config1.setAttributeName( ContactJsonAttributeType.SAVED_SEARCH_VALUES.getKey() );
        final ObjectAttributeConfig config2 = new ObjectAttributeConfig();
        config2.setAttributeName( ContactJsonAttributeType.PROPERTY_TOUR_INFORMATION.getKey() );
        final ObjectAttributeConfig config3 = new ObjectAttributeConfig();
        config3.setAttributeName( ContactJsonAttributeType.ADDITIONAL_PROPERTY_DATA.getKey() );
        final ObjectAttributeConfig config4 = new ObjectAttributeConfig();
        config4.setAttributeName( ContactJsonAttributeType.WEBSITE_SESSION_DATA.getKey() );
        final ObjectAttributeConfig config5 = new ObjectAttributeConfig();
        config5.setAttributeName( ContactJsonAttributeType.ORDER_ID.getKey() );

        attr.setObjectAttributeConfig( config2 );
        attr.setValue( "{\"propertyTourInformation\":[\"test44\"]}" );
        confSet.add( attr );
        destination.setContactJsonAttributes( confSet );

        Mockito.when( objectTypeService.findByName( "lead" ) ).thenReturn( objectType );
        Mockito.when( objectAttributeConfigService.getObjectAttributeConfig( Mockito.anyString(),
                Mockito.any( ObjectType.class ) ) ).thenReturn( configDefault );
        Mockito.when( objectAttributeConfigService
                .getObjectAttributeConfig( ContactJsonAttributeType.SAVED_SEARCH_VALUES.getKey(), objectType ) )
                .thenReturn( config1 );
        Mockito.when( objectAttributeConfigService
                .getObjectAttributeConfig( ContactJsonAttributeType.PROPERTY_TOUR_INFORMATION.getKey(), objectType ) )
                .thenReturn( config2 );
        Mockito.when( objectAttributeConfigService
                .getObjectAttributeConfig( ContactJsonAttributeType.ADDITIONAL_PROPERTY_DATA.getKey(), objectType ) )
                .thenReturn( config3 );
        Mockito.when( objectAttributeConfigService
                .getObjectAttributeConfig( ContactJsonAttributeType.WEBSITE_SESSION_DATA.getKey(), objectType ) )
                .thenReturn( config4 );
        Mockito.when( objectAttributeConfigService.getObjectAttributeConfig( ContactJsonAttributeType.ORDER_ID.getKey(),
                objectType ) ).thenReturn( config5 );
        final com.owners.gravitas.domain.entity.Contact result = contactBuilder.convertTo( source, destination );
        assertNotNull( result );

    }

    /**
     * Test convert to should return contact when source is not null and
     * destination is null for multi value json add null.
     */
    @Test
    public void testConvertToShouldReturnContactWhenSourceIsNotNullAndDestinationIsNullForMultiValueJsonAddNull() {
        final LeadRequest source = new LeadRequest();
        source.setFirstName( "testFirstName" );
        source.setLastName( "testLastName" );
        source.setEmail( "testEmail1" );
        source.setPreferredContactMethod( "testPreferredContactMethod" );
        source.setPreferredContactTime( "testPreferredContactTime" );
        source.setPhone( "testPhone" );
        source.setSavedSearchValues( "test" );
        source.setPropertyTourInformation( "test" );
        source.setAdditionalPropertyData( "test" );
        source.setWebsiteSessionData( "test" );
        source.setOrderId( "test" );
        final Contact destination = new Contact();
        final Set< ContactJsonAttribute > confSet = new HashSet<>();
        final ContactJsonAttribute attr = new ContactJsonAttribute();
        final ObjectType objectType = new ObjectType();
        objectType.setName( "test" );
        final ObjectAttributeConfig configDefault = new ObjectAttributeConfig();
        configDefault.setAttributeName( "test" );
        final ObjectAttributeConfig config1 = new ObjectAttributeConfig();
        config1.setAttributeName( ContactJsonAttributeType.SAVED_SEARCH_VALUES.getKey() );
        final ObjectAttributeConfig config2 = new ObjectAttributeConfig();
        config2.setAttributeName( ContactJsonAttributeType.PROPERTY_TOUR_INFORMATION.getKey() );
        final ObjectAttributeConfig config3 = new ObjectAttributeConfig();
        config3.setAttributeName( ContactJsonAttributeType.ADDITIONAL_PROPERTY_DATA.getKey() );
        final ObjectAttributeConfig config4 = new ObjectAttributeConfig();
        config4.setAttributeName( ContactJsonAttributeType.WEBSITE_SESSION_DATA.getKey() );
        final ObjectAttributeConfig config5 = new ObjectAttributeConfig();
        config5.setAttributeName( ContactJsonAttributeType.ORDER_ID.getKey() );

        attr.setObjectAttributeConfig( config2 );
        attr.setValue( null );
        confSet.add( attr );
        destination.setContactJsonAttributes( confSet );

        Mockito.when( objectTypeService.findByName( "lead" ) ).thenReturn( objectType );
        Mockito.when( objectAttributeConfigService.getObjectAttributeConfig( Mockito.anyString(),
                Mockito.any( ObjectType.class ) ) ).thenReturn( configDefault );
        Mockito.when( objectAttributeConfigService
                .getObjectAttributeConfig( ContactJsonAttributeType.SAVED_SEARCH_VALUES.getKey(), objectType ) )
                .thenReturn( config1 );
        Mockito.when( objectAttributeConfigService
                .getObjectAttributeConfig( ContactJsonAttributeType.PROPERTY_TOUR_INFORMATION.getKey(), objectType ) )
                .thenReturn( config2 );
        Mockito.when( objectAttributeConfigService
                .getObjectAttributeConfig( ContactJsonAttributeType.ADDITIONAL_PROPERTY_DATA.getKey(), objectType ) )
                .thenReturn( config3 );
        Mockito.when( objectAttributeConfigService
                .getObjectAttributeConfig( ContactJsonAttributeType.WEBSITE_SESSION_DATA.getKey(), objectType ) )
                .thenReturn( config4 );
        Mockito.when( objectAttributeConfigService.getObjectAttributeConfig( ContactJsonAttributeType.ORDER_ID.getKey(),
                objectType ) ).thenReturn( config5 );
        final com.owners.gravitas.domain.entity.Contact result = contactBuilder.convertTo( source, destination );
        assertNotNull( result );

    }

    /**
     * Testconvert from unsupported.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFromUnsupported() {
        contactBuilder.convertFrom( new Contact(), new LeadRequest() );
    }
}
