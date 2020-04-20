package com.owners.gravitas.builder;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.ContactAttributeBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.ContactAttribute;
import com.owners.gravitas.domain.entity.ObjectAttributeConfig;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.service.ObjectAttributeConfigService;
import com.owners.gravitas.service.ObjectTypeService;

/**
 * The Class ContactAttributeBuilderTest.
 * 
 * @author pabhishek
 */
public class ContactAttributeBuilderTest extends AbstractBaseMockitoTest {

    /** The contact attribute builder. */
    @InjectMocks
    private ContactAttributeBuilder contactAttributeBuilder;

    /** The object type service. */
    @Mock
    private ObjectTypeService objectTypeService;

    /** The object attribute config service. */
    @Mock
    private ObjectAttributeConfigService objectAttributeConfigService;

    /** The object type. */
    @Mock
    private ObjectType objectType;

    /** The object attribute config. */
    @Mock
    private ObjectAttributeConfig objectAttributeConfig;

    /**
     * Test convert from.
     */
    @Test
    public void testConvertFrom() {
        final LeadRequest request = new LeadRequest();
        request.setFirstName( "test1" );
        request.setLastName( "user1" );
        request.setEmail( "test1.user1@gmail.com" );
        request.setPhone( "1234567890" );
        request.setCompany( "abcd" );
        request.setLeadType( "buyer" );
        request.setAdditionalPropertyData( "additionalPropertyData" );
        request.setAlId( "alId" );
        request.setBuyerLeadQuality( "buyerLeadQuality" );
        request.setBuyerReadinessTimeline( "buyerReadinessTimeline" );
        request.setComments( "comments" );
        request.setCompany( "company" );
        request.setDownPayment( "downPayment" );
        request.setEarnestMoneyDeposit( "earnestMoneyDeposit" );
        request.setEmail( "email" );
        request.setFinancing( "financing" );
        request.setGclId( "gclId" );
        request.setGoogleAnalyticsCampaign( "googleAnalyticsCampaign" );
        request.setGoogleAnalyticsContent( "googleAnalyticsContent" );
        request.setGoogleAnalyticsMedium( "googleAnalyticsTerm" );
        request.setGoogleAnalyticsSource( "googleAnalyticsSource" );
        request.setGoogleAnalyticsTerm( "googleAnalyticsTerm" );
        request.setInterestedZipcodes( "interestedZipcodes" );
        request.setLastName( "lastName" );
        request.setLastVisitDateTime( "lastVisitDateTime" );
        request.setLeadSourceUrl( "leadSourceUrl" );
        request.setLeadStatus( "leadStatus" );
        request.setLeadType( "leadType" );
        request.setListingCreationDate( "listingCreationDate" );
        request.setListingId( "listingId" );
        request.setMarketingOptIn( true );
        request.setMedianPrice( "medianPrice" );
        request.setMessage( "message" );
        request.setMlsId( "mlsId" );
        request.setMlsPackageType( "mlsPackageType" );
        request.setOfferAmount( "offerAmount" );
        request.setOwnersComIdentifier( "ownersComIdentifier" );
        request.setOwnersVisitorId( "ownersVisitorId" );
        request.setOwnsHome( true );
        request.setPhone( "phone" );
        request.setPreApprovedForMortgage( "preApprovedForMortgage" );
        request.setPreferredContactMethod( "preferredContactMethod" );
        request.setPreferredContactTime( "preferredContactTime" );
        request.setPreferredLanguage( "preferredLanguage" );
        request.setPriceRange( "priceRange" );
        request.setPropertyAddress( "propertyAddress" );
        request.setPropertyTourInformation( "propertyTourInformation" );
        request.setPurchaseMethod( "purchaseMethod" );
        request.setRequestType( "requestType" );
        request.setSavedSearchValues( "savedSearchValues" );
        request.setSource( "source" );
        request.setState( "state" );
        request.setUnbouncePageVariant( "unbouncePageVariant" );
        request.setWebsite( "website" );
        request.setWebsiteSessionData( "websiteSessionData" );
        request.setWorkingWithRealtor( "workingWithRealtor" );
        request.setInterestedInFinancing( true );

        when( objectTypeService.findByName( "lead" ) ).thenReturn( objectType );
        when( objectAttributeConfigService.getObjectAttributeConfig( Mockito.anyString(), Mockito.eq( objectType ) ) )
                .thenReturn( objectAttributeConfig );

        final Set< ContactAttribute > actualContactAttributes = contactAttributeBuilder.convertFrom( request );

        Assert.assertNotNull( actualContactAttributes );
        verify( objectTypeService, Mockito.times( 1 ) ).findByName( "lead" );
        verify( objectAttributeConfigService, Mockito.times( 40 ) ).getObjectAttributeConfig( Mockito.anyString(),
                Mockito.eq( objectType ) );
    }

    /**
     * Test convert to.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertTo() {
        contactAttributeBuilder.convertTo( new HashSet< ContactAttribute >() );
    }

}
