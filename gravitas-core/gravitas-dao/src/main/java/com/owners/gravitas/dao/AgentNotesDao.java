package com.owners.gravitas.dao;

import com.owners.gravitas.domain.Note;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Interface AgentNotesDao.
 *
 * @author amits
 */
public interface AgentNotesDao {

    /**
     * Save agent notes by id.
     *
     * @param agentNote
     *            the agent note
     * @param agentId
     *            the agent id
     * @return
     */
    public PostResponse saveAgentNote( Note agentNote, String agentId );

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
    PostResponse updateAgentNote( Note agentNote, String agentId, String noteId );

    /**
     * Gets the agent note.
     *
     * @param agentId
     *            the agent id
     * @param noteId
     *            the note id
     * @return the agent note
     */
    Note getAgentNote( String agentId, String noteId );
}
