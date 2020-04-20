package com.owners.gravitas.service.impl;

import java.util.HashMap;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.AgentContactDao;
import com.owners.gravitas.domain.Contact;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Class AgentContactServiceImplTest.
 *
 * @author vishwanathm
 */
public class AgentContactServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent contact service impl. */
    @InjectMocks
    private AgentContactServiceImpl agentContactServiceImpl;

    /** The opportunity dao. */
    @Mock
    private AgentContactDao agentContactDao;

    /**
     * Test save contact.
     */
    @Test
    public void testSaveContact() {
        Mockito.when( agentContactDao.saveContact( Mockito.anyString(), Mockito.any( Contact.class ) ) )
                .thenReturn( new PostResponse() );
        final PostResponse res = agentContactServiceImpl.saveContact( "123123", new Contact() );
        Assert.assertNotNull( res );
    }

    /**
     * Test update contact for map.
     */
    @Test
    public void testUpdateContactForMap() {
        Mockito.doNothing().when( agentContactDao ).patchContact( Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMap() );
        agentContactServiceImpl.patchContact( "contactId", "agentId", new HashMap< String, Object >() );
        Mockito.verify( agentContactDao ).patchContact( Mockito.anyString(), Mockito.anyString(), Mockito.anyMap() );
    }

    /**
     * Test get contact by id.
     */
    @Test
    public void testGetContactById() {
        Mockito.when( agentContactDao.getContactById( Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( new Contact() );
        final Contact contact = agentContactServiceImpl.getContactById( "agentId", "contactId" );
        Mockito.verify( agentContactDao ).getContactById( Mockito.anyString(), Mockito.anyString() );
        Assert.assertNotNull( contact );
    }

}
