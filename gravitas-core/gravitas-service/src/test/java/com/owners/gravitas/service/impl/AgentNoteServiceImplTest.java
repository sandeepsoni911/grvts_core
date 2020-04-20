package com.owners.gravitas.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.AgentNotesDao;
import com.owners.gravitas.domain.Note;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.exception.ApplicationException;

/**
 * The Class AgentNoteServiceImplTest.
 *
 * @author vishwanathm
 */
public class AgentNoteServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent note service impl. */
    @InjectMocks
    private AgentNoteServiceImpl agentNoteServiceImpl;

    /** The agent notes dao. */
    @Mock
    private AgentNotesDao agentNotesDao;

    /**
     * Test save agent.
     */
    @Test
    public void testSaveAgentNote() {
        Mockito.when( agentNotesDao.saveAgentNote( Mockito.any( Note.class ), Mockito.anyString() ) )
                .thenReturn( new PostResponse() );
        PostResponse res = agentNoteServiceImpl.saveAgentNote( new Note(), "agentId" );
        Assert.assertNotNull( res );
    }

    /**
     * Test update agent.
     */
    @Test
    public void testUpdateAgentNote() {
        Mockito.when(
                agentNotesDao.updateAgentNote( Mockito.any( Note.class ), Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( new PostResponse() );
        PostResponse res = agentNoteServiceImpl.updateAgentNote( new Note(), "agentId", "noteId" );
        Assert.assertNotNull( res );
    }

    /**
     * Test get agent note not null.
     */
    @Test
    public void testGetAgentNoteNotNull() {
        Mockito.when( agentNotesDao.getAgentNote( Mockito.anyString(), Mockito.anyString() ) ).thenReturn( new Note() );
        Note res = agentNoteServiceImpl.getAgentNote( "agentId", "noteId" );
        Assert.assertNotNull( res );
    }

    /**
     * Test get agent note null.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testGetAgentNoteNull() {
        Mockito.when( agentNotesDao.getAgentNote( Mockito.anyString(), Mockito.anyString() ) ).thenReturn( null );
        Note res = agentNoteServiceImpl.getAgentNote( "agentId", "noteId" );
        Assert.assertNull( res );
    }

}
