package com.owners.gravitas.builder;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.math.BigDecimal;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.builder.request.ReferralExchangeRequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.PropertyAddress;
import com.owners.gravitas.dto.PropertyData;
import com.owners.gravitas.dto.ReferralExchangeDetails;
import com.owners.gravitas.dto.request.ReferralExchangeRequest;
import com.owners.gravitas.dto.response.PropertyDetailsResponse;
import com.owners.gravitas.service.PropertyService;

/**
 * The Class ReferralExchangeRequestBuilderTest.
 *
 * @author amits
 */
public class ReferralExchangeRequestBuilderTest extends AbstractBaseMockitoTest {

    /** The referral exchange request builder. */
    @InjectMocks
    private ReferralExchangeRequestBuilder referralExchangeRequestBuilder;

    /** The rest template. */
    @Mock
    private RestTemplate restTemplate;

    /** The property details service. */
    @Mock
    private PropertyService propertyDetailsService;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "token", "test" );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "version", "test" );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "testFlag", true );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "apiName", "test" );
        LeadSource leadSource = new LeadSource();
        PropertyDetailsResponse propResp = new PropertyDetailsResponse();
        PropertyData proData = new PropertyData();
        PropertyAddress proAddress = new PropertyAddress();
        proAddress.setCity( "city" );
        proData.setPrice( "test" );
        proData.setBedRooms( "test" );
        proData.setBathRooms( new BigDecimal( "34" ) );
        proData.setPropertyAddress( proAddress );
        propResp.setData( proData );
        leadSource.setListingId( "test" );
        Mockito.when( propertyDetailsService.getPropertyDetails( "test" ) ).thenReturn( propResp );
        ReferralExchangeRequest request = referralExchangeRequestBuilder.convertTo( leadSource );

        assertNotNull( request );
    }

    /**
     * Test convert to without property details.
     */
    @Test
    public void testConvertToWithoutPropertyDetails() {
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "token", "test" );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "version", "test" );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "testFlag", true );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "apiName", "test" );
        LeadSource leadSource = new LeadSource();
        leadSource.setListingId( "test" );
        Mockito.when( propertyDetailsService.getPropertyDetails( "test" ) ).thenReturn( null );
        ReferralExchangeRequest request = referralExchangeRequestBuilder.convertTo( leadSource );

        assertNotNull( request );
    }

    /**
     * Test convert to without property details.
     */
    @Test
    public void testConvertToWithInvalidPropertyDetails() {
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "token", "test" );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "version", "test" );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "testFlag", true );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "apiName", "test" );
        LeadSource leadSource = new LeadSource();
        leadSource.setListingId( "test" );
        ReferralExchangeRequest request = referralExchangeRequestBuilder.convertTo( leadSource );

        assertNotNull( request );
    }

    /**
     * Test convert to with prics ranges.
     */
    @Test
    public void testConvertToWithPricsRanges() {
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "token", "test" );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "version", "test" );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "testFlag", true );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "apiName", "test" );
        LeadSource leadSource = new LeadSource();

        leadSource.setPriceRanges( "test-test" );
        // leadSource.setListingId( "test" );
        Mockito.when( propertyDetailsService.getPropertyDetails( "test" ) ).thenReturn( null );
        ReferralExchangeRequest request = referralExchangeRequestBuilder.convertTo( leadSource );

        assertNotNull( request );
    }

    /**
     * Test convert to with prics ranges having thousand constant.
     */
    @Test
    public void testConvertToWithPricsRangesHavingThousandConstant() {
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "token", "test" );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "version", "test" );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "testFlag", true );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "apiName", "test" );
        LeadSource leadSource = new LeadSource();

        leadSource.setPriceRanges( "10-20k" );
        // leadSource.setListingId( "test" );
        Mockito.when( propertyDetailsService.getPropertyDetails( "test" ) ).thenReturn( null );
        ReferralExchangeRequest request = referralExchangeRequestBuilder.convertTo( leadSource );

        assertNotNull( request );
    }

    /**
     * Test convert to with prics ranges having million constant.
     */
    @Test
    public void testConvertToWithPricsRangesHavingMillionConstant() {
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "token", "test" );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "version", "test" );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "testFlag", true );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "apiName", "test" );
        LeadSource leadSource = new LeadSource();

        leadSource.setPriceRanges( "10-20m" );
        // leadSource.setListingId( "test" );
        Mockito.when( propertyDetailsService.getPropertyDetails( "test" ) ).thenReturn( null );
        ReferralExchangeRequest request = referralExchangeRequestBuilder.convertTo( leadSource );

        assertNotNull( request );
    }

    /**
     * Test convert to with prics ranges having without hypen.
     */
    @Test
    public void testConvertToWithPricsRangesHavingWithoutHypen() {
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "token", "test" );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "version", "test" );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "testFlag", true );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "apiName", "test" );
        LeadSource leadSource = new LeadSource();

        leadSource.setPriceRanges( "20m" );
        // leadSource.setListingId( "test" );
        Mockito.when( propertyDetailsService.getPropertyDetails( "test" ) ).thenReturn( null );
        ReferralExchangeRequest request = referralExchangeRequestBuilder.convertTo( leadSource );

        assertNotNull( request );
    }

    /**
     * Test convert to without price.
     */
    @Test
    public void testConvertToWithoutPrice() {
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "token", "test" );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "version", "test" );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "testFlag", true );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "apiName", "test" );
        LeadSource leadSource = new LeadSource();

        ReferralExchangeRequest request = referralExchangeRequestBuilder.convertTo( leadSource );

        assertNotNull( request );
    }

    /**
     * Test convert to for comments with property address.
     *
     * @param leadSource
     *            the lead source
     */
    @Test( dataProvider = "leadSourceDataProvider" )
    public void testConvertToForCommentsWithPropertyAddress( final LeadSource leadSource ) {
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "token", "testToken" );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "version", "testVersion" );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "testFlag", true );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "apiName", "testApiName" );

        final ReferralExchangeRequest request = referralExchangeRequestBuilder.convertTo( leadSource );
        final ReferralExchangeDetails requestData = request.getData();

        assertNotNull( request );
        assertNotNull( requestData );
        assertEquals( "testToken", request.getToken() );
        assertEquals( "testVersion", request.getVersion() );
        assertEquals( true, request.isTestFlag() );
        assertEquals( "testApiName", request.getApiName() );
        assertEquals( "self_service", requestData.getSubmissionType() );
        assertEquals( leadSource.getFirstName(), requestData.getFirstName() );
        assertEquals( leadSource.getLastName(), requestData.getLastName() );
        assertEquals( leadSource.getEmail(), requestData.getEmail() );
        assertEquals( leadSource.getPhone(), requestData.getPhone() );
        assertEquals( leadSource.getState(), requestData.getState() );
        assertEquals( "Inquiry Type:testRequestType!! Property Address:testPropertyAddress",
                requestData.getComments() );
    }

    /**
     * Test convert to for comments without property address.
     *
     * @param leadSource
     *            the lead source
     */
    @Test( dataProvider = "leadSourceDataProvider" )
    public void testConvertToForCommentsWithoutPropertyAddress( final LeadSource leadSource ) {
        leadSource.setPropertyAddress( null );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "token", "testToken" );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "version", "testVersion" );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "testFlag", true );
        ReflectionTestUtils.setField( referralExchangeRequestBuilder, "apiName", "testApiName" );

        final ReferralExchangeRequest request = referralExchangeRequestBuilder.convertTo( leadSource );
        final ReferralExchangeDetails requestData = request.getData();

        assertNotNull( request );
        assertNotNull( requestData );
        assertEquals( "testToken", request.getToken() );
        assertEquals( "testVersion", request.getVersion() );
        assertEquals( true, request.isTestFlag() );
        assertEquals( "testApiName", request.getApiName() );
        assertEquals( "self_service", requestData.getSubmissionType() );
        assertEquals( leadSource.getFirstName(), requestData.getFirstName() );
        assertEquals( leadSource.getLastName(), requestData.getLastName() );
        assertEquals( leadSource.getEmail(), requestData.getEmail() );
        assertEquals( leadSource.getPhone(), requestData.getPhone() );
        assertEquals( leadSource.getState(), requestData.getState() );
        assertEquals( "Inquiry Type:testRequestType!!", requestData.getComments() );
    }

    /**
     * Test Convert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFrom() {
        referralExchangeRequestBuilder.convertFrom( new ReferralExchangeRequest() );
    }

    /**
     * Gets the lead source data.
     *
     * @return the lead source data
     */
    @DataProvider( name = "leadSourceDataProvider" )
    private Object[][] getLeadSourceData() {
        final LeadSource leadSource = new LeadSource();
        leadSource.setFirstName( "firstName" );
        leadSource.setLastName( "lastName" );
        leadSource.setEmail( "test@test.com" );
        leadSource.setPhone( "testPhone" );
        leadSource.setState( "testState" );
        leadSource.setRequestType( "testRequestType" );
        leadSource.setPropertyAddress( "testPropertyAddress" );
        return new Object[][] { { leadSource } };
    }
}
