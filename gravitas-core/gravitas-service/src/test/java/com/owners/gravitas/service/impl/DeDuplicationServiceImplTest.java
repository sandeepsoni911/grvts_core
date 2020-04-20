/**
 *
 */
package com.owners.gravitas.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.SearchDao;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.crm.response.CRMResponse;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.service.CRMQueryService;
import com.owners.gravitas.service.RecordTypeService;

/**
 * The Class DeDuplicationServiceImplTest.
 *
 * @author harshads
 */
public class DeDuplicationServiceImplTest extends AbstractBaseMockitoTest {

    /** The de duplication service impl. */
    @InjectMocks
    private DeDuplicationServiceImpl deDuplicationServiceImpl;

    /** The record type service. */
    @Mock
    private RecordTypeService recordTypeService;

    /** The crm query service. */
    @Mock
    private CRMQueryService crmQueryService;

    /** SearchDao service */
    @Mock
    private SearchDao searchDao;

    /**
     * Test de duplicate lead.
     */
    @Test
    public void testDeDuplicateLead() {
        Mockito.when( crmQueryService.findAll( Mockito.anyString(), Mockito.any( QueryParams.class ) ) )
                .thenReturn( new CRMResponse() );
        deDuplicationServiceImpl.deDuplicateLead( "value1" );
        Mockito.verify( crmQueryService ).findAll( Mockito.anyString(), Mockito.any( QueryParams.class ) );
    }

    /**
     * Test de duplicate lead based on RecordType.
     */
    @Test
    public void testDeDuplicateLeadRecordType() {
        Mockito.when( crmQueryService.findAll( Mockito.anyString(), Mockito.any( QueryParams.class ) ) )
                .thenReturn( new CRMResponse() );
        deDuplicationServiceImpl.getDeDuplicatedLead( "value1", RecordType.OWNERS_COM_LOANS );
        Mockito.verify( crmQueryService ).findAll( Mockito.anyString(), Mockito.any( QueryParams.class ) );
    }

    /**
     * Test search by contact email.
     */
    @Test
    public void testsearchByContactEmail() {
        Mockito.when( searchDao.searchByContactEmail( Mockito.anyString() ) ).thenReturn( new Search() );
        deDuplicationServiceImpl.getSearchByContactEmail( "value1" );
        Mockito.verify( searchDao ).searchByContactEmail( Mockito.anyString() );
    }

}
