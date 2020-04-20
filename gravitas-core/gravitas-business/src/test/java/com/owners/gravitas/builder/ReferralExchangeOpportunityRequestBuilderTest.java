package com.owners.gravitas.builder;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.builder.request.ReferralExchangeOpportunityRequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.PhoneNumber;
import com.owners.gravitas.dto.ReferralExchangeDetails;
import com.owners.gravitas.dto.request.ReferralExchangeRequest;

/**
 * The Class referralExchangeOpportunityRequestBuilderTest.
 *
 * @author amits
 */
public class ReferralExchangeOpportunityRequestBuilderTest extends AbstractBaseMockitoTest {

    /** The referral exchange opportunity request builder. */
    @InjectMocks
    private ReferralExchangeOpportunityRequestBuilder referralExchangeOpportunityRequestBuilder;

    /** The rest template. */
    @Mock
    private RestTemplate restTemplate;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        ReflectionTestUtils.setField( referralExchangeOpportunityRequestBuilder, "token", "test" );
        ReflectionTestUtils.setField( referralExchangeOpportunityRequestBuilder, "version", "test" );
        ReflectionTestUtils.setField( referralExchangeOpportunityRequestBuilder, "testFlag", true );
        ReflectionTestUtils.setField( referralExchangeOpportunityRequestBuilder, "apiName", "test" );

        OpportunitySource opportunitySource = new OpportunitySource();
        Contact contact = new Contact();
        contact.setFirstName( "test" );
        contact.setLastName( "test" );
        List< String > al = new ArrayList<>();
        al.add( "test" );
        contact.setEmails( al );
        PhoneNumber ph = new PhoneNumber();
        ph.setNumber( "test" );
        List< PhoneNumber > al1 = new ArrayList<>();
        al1.add( ph );
        contact.setPhoneNumbers( al1 );
        opportunitySource.setPropertyState( "test" );
        opportunitySource.setPropertyCity( "test" );
        opportunitySource.setPrimaryContact( contact );
        opportunitySource.setPriceRange( "100-200" );
        opportunitySource.setPriceRanges( "100" );
        ReferralExchangeRequest request = referralExchangeOpportunityRequestBuilder.convertTo( opportunitySource );

        Assert.assertNotNull( request );
    }

    /**
     * Test convert to for price range in thousands.
     */
    @Test
    public void testConvertToForPriceRangeInThousands() {
        ReflectionTestUtils.setField( referralExchangeOpportunityRequestBuilder, "token", "test" );
        ReflectionTestUtils.setField( referralExchangeOpportunityRequestBuilder, "version", "test" );
        ReflectionTestUtils.setField( referralExchangeOpportunityRequestBuilder, "testFlag", true );
        ReflectionTestUtils.setField( referralExchangeOpportunityRequestBuilder, "apiName", "test" );

        OpportunitySource opportunitySource = new OpportunitySource();
        Contact contact = new Contact();
        contact.setFirstName( "test" );
        contact.setLastName( "test" );
        List< String > al = new ArrayList<>();
        al.add( "test" );
        contact.setEmails( al );
        PhoneNumber ph = new PhoneNumber();
        ph.setNumber( "test" );
        List< PhoneNumber > al1 = new ArrayList<>();
        al1.add( ph );
        contact.setPhoneNumbers( al1 );
        opportunitySource.setPropertyState( "test" );
        opportunitySource.setPropertyCity( "test" );
        opportunitySource.setPrimaryContact( contact );
        opportunitySource.setPriceRange( "100K-200K" );
        opportunitySource.setPriceRanges( "100K-200K" );
        ReferralExchangeRequest request = referralExchangeOpportunityRequestBuilder.convertTo( opportunitySource );

        Assert.assertNotNull( request );
    }

    /**
     * Test convert to for price range in millions.
     */
    @Test
    public void testConvertToForPriceRangeInMillions() {
        ReflectionTestUtils.setField( referralExchangeOpportunityRequestBuilder, "token", "test" );
        ReflectionTestUtils.setField( referralExchangeOpportunityRequestBuilder, "version", "test" );
        ReflectionTestUtils.setField( referralExchangeOpportunityRequestBuilder, "testFlag", true );
        ReflectionTestUtils.setField( referralExchangeOpportunityRequestBuilder, "apiName", "test" );

        OpportunitySource opportunitySource = new OpportunitySource();
        Contact contact = new Contact();
        contact.setFirstName( "test" );
        contact.setLastName( "test" );
        List< String > al = new ArrayList<>();
        al.add( "test" );
        contact.setEmails( al );
        PhoneNumber ph = new PhoneNumber();
        ph.setNumber( "test" );
        List< PhoneNumber > al1 = new ArrayList<>();
        al1.add( ph );
        contact.setPhoneNumbers( al1 );
        opportunitySource.setPropertyState( "test" );
        opportunitySource.setPropertyCity( "test" );
        opportunitySource.setPrimaryContact( contact );
        opportunitySource.setPriceRange( "100M-200M" );
        opportunitySource.setPriceRanges( "100M-200M" );
        ReferralExchangeRequest request = referralExchangeOpportunityRequestBuilder.convertTo( opportunitySource );

        Assert.assertNotNull( request );
    }

    /**
     * Test convert to for comments with property address.
     *
     * @param source
     *            the source
     */
    @Test( dataProvider = "opportunitySourceDataProvider" )
    public void testConvertToForCommentsWithPropertyAddress( final OpportunitySource source ) {
        ReflectionTestUtils.setField( referralExchangeOpportunityRequestBuilder, "token", "testToken" );
        ReflectionTestUtils.setField( referralExchangeOpportunityRequestBuilder, "version", "testVersion" );
        ReflectionTestUtils.setField( referralExchangeOpportunityRequestBuilder, "testFlag", true );
        ReflectionTestUtils.setField( referralExchangeOpportunityRequestBuilder, "apiName", "testApiName" );

        ReferralExchangeRequest request = referralExchangeOpportunityRequestBuilder.convertTo( source );
        final ReferralExchangeDetails requestData = request.getData();

        assertEquals( "testToken", request.getToken() );
        assertEquals( "testVersion", request.getVersion() );
        assertEquals( true, request.isTestFlag() );
        assertEquals( "testApiName", request.getApiName() );
        assertEquals( "self_service", requestData.getSubmissionType() );
        assertEquals( source.getPrimaryContact().getFirstName(), requestData.getFirstName() );
        assertEquals( source.getPrimaryContact().getLastName(), requestData.getLastName() );
        assertEquals( source.getPrimaryContact().getEmails().iterator().next(), requestData.getEmail() );
        assertEquals( source.getPrimaryContact().getPhoneNumbers().iterator().next().getNumber(),
                requestData.getPhone() );
        assertEquals( source.getPropertyState(), requestData.getState() );
        assertEquals( source.getPropertyCity(), requestData.getCity() );
        assertEquals( "Inquiry Type:testLeadRequestType!! Property Address:testPropertyAddress",
                requestData.getComments() );
    }

    /**
     * Test convert to for comments without property address.
     *
     * @param source
     *            the source
     */
    @Test( dataProvider = "opportunitySourceDataProvider" )
    public void testConvertToForCommentsWithoutPropertyAddress( final OpportunitySource source ) {
        source.setPropertyAddress( null );
        ReflectionTestUtils.setField( referralExchangeOpportunityRequestBuilder, "token", "testToken" );
        ReflectionTestUtils.setField( referralExchangeOpportunityRequestBuilder, "version", "testVersion" );
        ReflectionTestUtils.setField( referralExchangeOpportunityRequestBuilder, "testFlag", true );
        ReflectionTestUtils.setField( referralExchangeOpportunityRequestBuilder, "apiName", "testApiName" );

        ReferralExchangeRequest request = referralExchangeOpportunityRequestBuilder.convertTo( source );
        final ReferralExchangeDetails requestData = request.getData();

        assertEquals( "testToken", request.getToken() );
        assertEquals( "testVersion", request.getVersion() );
        assertEquals( true, request.isTestFlag() );
        assertEquals( "testApiName", request.getApiName() );
        assertEquals( "self_service", requestData.getSubmissionType() );
        assertEquals( source.getPrimaryContact().getFirstName(), requestData.getFirstName() );
        assertEquals( source.getPrimaryContact().getLastName(), requestData.getLastName() );
        assertEquals( source.getPrimaryContact().getEmails().iterator().next(), requestData.getEmail() );
        assertEquals( source.getPrimaryContact().getPhoneNumbers().iterator().next().getNumber(),
                requestData.getPhone() );
        assertEquals( source.getPropertyState(), requestData.getState() );
        assertEquals( source.getPropertyCity(), requestData.getCity() );
        assertEquals( "Inquiry Type:testLeadRequestType!!", requestData.getComments() );
    }

    /**
     * Test Convert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFrom() {
        referralExchangeOpportunityRequestBuilder.convertFrom( new ReferralExchangeRequest() );
    }

    /**
     * Gets the opportunity source data.
     *
     * @return the opportunity source data
     */
    @DataProvider( name = "opportunitySourceDataProvider" )
    private Object[][] getOpportunitySourceData() {
        final OpportunitySource opportunitySource = new OpportunitySource();
        final Contact contact = new Contact();
        contact.setFirstName( "test" );
        contact.setLastName( "test" );
        List< String > al = new ArrayList<>();
        al.add( "test" );
        contact.setEmails( al );
        final PhoneNumber ph = new PhoneNumber();
        ph.setNumber( "test" );
        final List< PhoneNumber > al1 = new ArrayList<>();
        al1.add( ph );
        contact.setPhoneNumbers( al1 );
        opportunitySource.setPropertyState( "test" );
        opportunitySource.setPropertyCity( "test" );
        opportunitySource.setPrimaryContact( contact );
        opportunitySource.setPriceRange( "100-200" );
        opportunitySource.setPriceRanges( "100-200" );
        opportunitySource.setLeadRequestType( "testLeadRequestType" );
        opportunitySource.setPropertyAddress( "testPropertyAddress" );
        return new Object[][] { { opportunitySource } };
    }
}
