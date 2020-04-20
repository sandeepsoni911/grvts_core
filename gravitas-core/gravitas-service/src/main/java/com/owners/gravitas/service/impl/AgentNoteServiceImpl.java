package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.Constants.ACTION_OBJ;
import static com.owners.gravitas.constants.Constants.AGENT_ID;
import static com.owners.gravitas.constants.Constants.ENTITY_ID;
import static com.owners.gravitas.enums.ActionEntity.AGENT_NOTE;
import static com.owners.gravitas.enums.ActionType.CREATE;
import static com.owners.gravitas.enums.ActionType.UPDATE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.annotation.Audit;
import com.owners.gravitas.dao.AgentNotesDao;
import com.owners.gravitas.domain.Note;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.enums.ErrorCode;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.AgentNoteService;

/**
 * The Class AgentNoteServiceImpl.
 *
 * @author vishwanathm
 */
@Service
public class AgentNoteServiceImpl implements AgentNoteService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentNoteServiceImpl.class );

    /** The agent notes dao. */
    @Autowired
    private AgentNotesDao agentNotesDao;

    /**
     * Save agent note.
     *
     * @param agentNote
     *            the agent note
     * @param agentId
     *            the agent id
     * @return the post response
     */
    @Override
    @Audit( type = CREATE, entity = AGENT_NOTE, args = { ACTION_OBJ, AGENT_ID } )
    public PostResponse saveAgentNote( final Note agentNote, final String agentId ) {
        return agentNotesDao.saveAgentNote( agentNote, agentId );
    }

    /**
     * Update agent note.
     *
     * @param agentNote
     *            the agent note
     * @param agentId
     *            the agent id
     * @param noteId
     *            the note id
     * @return the post response
     */
    @Override
    @Audit( type = UPDATE, entity = AGENT_NOTE, args = { ACTION_OBJ, AGENT_ID, ENTITY_ID } )
    public PostResponse updateAgentNote( final Note agentNote, final String agentId, final String noteId ) {
        return agentNotesDao.updateAgentNote( agentNote, agentId, noteId );
    }

    /**
     * Gets the agent note.
     *
     * @param agentId
     *            the agent id
     * @param noteId
     *            the note id
     * @return the agent note
     */
    @Override
    public Note getAgentNote( final String agentId, final String noteId ) {
        final Note note = agentNotesDao.getAgentNote( agentId, noteId );
        if (null == note) {
            LOGGER.error( "Agent note ID: " + noteId + " does not exists.", ErrorCode.NOTE_NOT_FOUND_ERROR );
            throw new ApplicationException( "Agent note ID: " + noteId + " does not exists.",
                    ErrorCode.NOTE_NOT_FOUND_ERROR );
        }
        return note;
    }
}
