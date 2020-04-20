package com.owners.gravitas.builder;

import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.request.CRMLeadRequestBuilder;
import com.owners.gravitas.business.helper.DedupLeadSourcePrioirityHandler;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.crm.request.CRMLeadRequest;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.enums.LeadRequestType;
import com.owners.gravitas.service.CRMQueryService;
import com.owners.gravitas.service.HostService;
import com.owners.gravitas.service.RecordTypeService;

/**
 * The Class CRMLeadRequestBuilderTest.
 *
 * @author vishwanathm
 */
public class CRMLeadRequestBuilderTest extends AbstractBaseMockitoTest {

    /** The crm lead request builder. */
    @InjectMocks
    private CRMLeadRequestBuilder crmLeadRequestBuilder;

    @Mock
    private DedupLeadSourcePrioirityHandler dedupLeadSourcePrioirityHandler;

    /** The crm query service. */
    @Mock
    private CRMQueryService crmQueryService;

    /** The host service. */
    @Mock
    private HostService hostService;

    /** The record type service. */
    @Mock
    private RecordTypeService recordTypeService;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        GenericLeadRequest glr = new GenericLeadRequest();
        glr.setSource( "noReply@hubzu.com" );
        glr.setLeadType( "SELLER" );
        glr.setMlsPackageType( "FREE" );
        glr.setComments( "test" );

        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Id", "123456" );
        when( crmQueryService.findOne( Mockito.anyString(), Mockito.any( QueryParams.class ) ) ).thenReturn( map );
        when( dedupLeadSourcePrioirityHandler.isSourceUpdateRequired( Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( true );
        when(hostService.getHost()).thenReturn( "test" );
        when(recordTypeService.getRecordTypeIdByName(Mockito.any(),Mockito.any())).thenReturn( "test" );
        CRMLeadRequest leadRequest = crmLeadRequestBuilder.convertTo( glr );
        Assert.assertNotNull( leadRequest );

    }

    /**
     * Test convert to with destination.
     */
    @Test
    private void testConvertToWithDestination() {
        CRMLeadRequest lr = new CRMLeadRequest();
        lr.setSource( "Owners.com Saved Search" );
        lr.setRecordHistory( "recordHistory" );
        lr.setComments( "test" );

        GenericLeadRequest glr = new GenericLeadRequest();
        glr.setSource( "noReply@hubzu.com" );
        glr.setLeadType( "BUYER" );
        glr.setMlsPackageType( "FREE" );
        glr.setComments( "test" );

        when( dedupLeadSourcePrioirityHandler.isSourceUpdateRequired( Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( false );
        CRMLeadRequest leadRequest = crmLeadRequestBuilder.convertTo( glr, lr );
        Assert.assertNotNull( leadRequest );

        when( dedupLeadSourcePrioirityHandler.isSourceUpdateRequired( Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( true );
        leadRequest = crmLeadRequestBuilder.convertTo( glr, lr );
        Assert.assertNotNull( leadRequest );
    }

    /**
     * Test convert to not null company.
     */
    @Test
    public void testConvertToNotNullCompany() {
        GenericLeadRequest glr = new GenericLeadRequest();
        glr.setSource( "test" );
        glr.setLeadType( "BUYER" );
        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Id", "123456" );
        when( crmQueryService.findOne( Mockito.anyString(), Mockito.any( QueryParams.class ) ) ).thenReturn( map );
        CRMLeadRequest leadRequest = crmLeadRequestBuilder.convertTo( glr );
        Assert.assertNotNull( leadRequest );

        GenericLeadRequest request = new GenericLeadRequest();
        request.setCompany( "company" );
        request.setSource( "newSource" );
        request.setRequestType( LeadRequestType.MAKE_OFFER.toString() );
        request.setLeadType( "BUYER" );
        CRMLeadRequest lr = new CRMLeadRequest();
        lr.setSource( "oldSource" );
        leadRequest = crmLeadRequestBuilder.convertTo( request, lr );
        Assert.assertEquals( leadRequest.getCompany(), "company" );
    }

    /**
     * Test convert to source null.
     */
    @Test
    public void testConvertToSourceNull() {
        final CRMLeadRequest leadRequest = crmLeadRequestBuilder.convertTo( null );
        Assert.assertNull( leadRequest );
    }

    /**
     * Test convert to destination null.
     */
    @Test
    public void testConvertToDestinationNull() {
        GenericLeadRequest glr = new GenericLeadRequest();
        glr.setSource( "test" );
        glr.setLeadType( "BUYER" );
        CRMLeadRequest leadRequest = crmLeadRequestBuilder.convertTo( glr, null );
        Assert.assertNotNull( leadRequest );

        leadRequest = crmLeadRequestBuilder.convertTo( null, null );
        Assert.assertNull( leadRequest );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        crmLeadRequestBuilder.convertFrom( new CRMLeadRequest() );
    }

}
