package com.owners.gravitas.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.SearchDao;
import com.owners.gravitas.domain.Search;

/**
 * The Class SearchServiceImplTest.
 *
 * @author vishwanathm
 */
public class SearchServiceImplTest extends AbstractBaseMockitoTest {

    /** The search service impl. */
    @InjectMocks
    private SearchServiceImpl searchServiceImpl;

    /** The contact search dao. */
    @Mock
    private SearchDao searchDao;

    /**
     * Test save.
     */
    @Test
    public void testSave() {
        Mockito.doNothing().when( searchDao ).saveSearch( Mockito.any( Search.class ) );
        searchServiceImpl.save( new Search() );
        Mockito.verify( searchDao ).saveSearch( Mockito.any( Search.class ) );
    }

    /**
     * Test save all.
     */
    @Test
    public void testSaveAll() {
        Mockito.doNothing().when( searchDao ).saveSearch( Mockito.any( Search.class ) );
        Map< String, Search > list = new HashMap< String, Search >();
        list.put( "1", new Search() );
        searchServiceImpl.saveAll( list );
        Mockito.verify( searchDao ).saveSearches( Mockito.any() );
    }

    /**
     * Test search by crm opportunity id.
     */
    @Test
    public void testSearchByCrmOpportunityId() {
        Mockito.when( searchDao.searchByCrmOpportunityId( Mockito.anyString() ) ).thenReturn( new Search() );
        final Search search = searchServiceImpl.searchByCrmOpportunityId( "opportunityId" );
        Assert.assertNotNull( search );
    }

    /**
     * Test search by agent id.
     */
    @Test
    public void testSearchByAgentId() {
        Mockito.when( searchDao.searchByAgentId( Mockito.anyString() ) ).thenReturn( new Search() );
        final Search search = searchServiceImpl.searchByAgentId( "opportunityId" );
        Assert.assertNotNull( search );
    }

    /**
     * Test search by agent email.
     */
    @Test
    public void testSearchByAgentEmail() {
        Mockito.when( searchDao.searchByAgentEmail( Mockito.anyString() ) ).thenReturn( new Search() );
        final Search search = searchServiceImpl.searchByAgentEmail( "opportunityId" );
        Assert.assertNotNull( search );
    }

    /**
     * Test search by contact email.
     */
    @Test
    public void testSearchByContactEmail() {
        Mockito.when( searchDao.searchByContactEmail( Mockito.anyString() ) ).thenReturn( new Search() );
        final Search search = searchServiceImpl.searchByContactEmail( "opportunityId" );
        Assert.assertNotNull( search );
    }

    /**
     * Test search by opportunity id.
     */
    @Test
    public void testSearchByOpportunityId() {
        Mockito.when( searchDao.searchByOpportunityId( Mockito.anyString() ) ).thenReturn( new Search() );
        final Search search = searchServiceImpl.searchByOpportunityId( "opportunityId" );
        Assert.assertNotNull( search );
    }

    /**
     * Testsearch by contact id.
     */
    @Test
    public void testSearchByContactId() {
        Mockito.when( searchDao.searchByContactId( Mockito.anyString() ) ).thenReturn( new Search() );
        final Search search = searchServiceImpl.searchByContactId( "contactId" );
        Assert.assertNotNull( search );
    }

    /**
     * Test update search.
     */
    @Test
    public void testUpdateSearch() {
        Mockito.doNothing().when( searchDao ).updateSearch( Mockito.any( Search.class ) );
        searchServiceImpl.updateSearch( new Search() );
        Mockito.verify( searchDao ).updateSearch( Mockito.any( Search.class ) );
    }

    /**
     * Test delete.
     */
    @Test
    public void testDelete() {
        Mockito.doNothing().when( searchDao ).deleteSearch( Mockito.anyString() );
        searchServiceImpl.delete( "searchId" );
        Mockito.verify( searchDao ).deleteSearch( Mockito.anyString() );
    }
}
