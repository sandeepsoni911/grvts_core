package com.owners.gravitas.service;

import com.owners.gravitas.domain.Note;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Interface AgentNoteService.
 *
 * @author vishwanathm
 */
public interface AgentNoteService {

    /**
     * Save agent note.
     *
     * @param agentNote
     *            the agent note
     * @param agentId
     *            the agent id
     * @return the post response
     */
    PostResponse saveAgentNote( final Note agentNote, final String agentId );

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
