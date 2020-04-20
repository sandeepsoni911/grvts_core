package com.owners.gravitas.dao.impl;

import static com.owners.gravitas.util.RestUtil.buildRequest;
import static com.owners.gravitas.util.RestUtil.createHttpHeader;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.dao.AgentNotesDao;
import com.owners.gravitas.domain.Note;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Class AgentNotesDaoImpl.
 *
 * @author vishwanathm
 */
@Repository
public class AgentNotesDaoImpl extends BaseDaoImpl implements AgentNotesDao {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentNotesDaoImpl.class );

    /**
     * Save agent notes by id.
     *
     * @param agentNote
     *            the agent note
     * @param agentId
     *            the agent id
     * @return the post response
     */
    @Override
    public PostResponse saveAgentNote( final Note agentNote, final String agentId ) {
        LOGGER.debug( "Create agent note for agent id " + agentId );
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/agentNotes" );
        return getRestTemplate().exchange( reqUrl, HttpMethod.POST,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), agentNote ),
                PostResponse.class ).getBody();
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
    public PostResponse updateAgentNote( final Note agentNote, final String agentId, final String noteId ) {
        LOGGER.debug( "Agent note updated for agent id " + agentId );
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/agentNotes/" + noteId );
        return getRestTemplate().exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), agentNote ),
                PostResponse.class ).getBody();
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
        LOGGER.debug( "Agent note updated for agent id " + agentId );
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/agentNotes/" + noteId );
        final Map< String, Object > noteMap = getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                Map.class ).getBody();
        return ( noteMap != null && !noteMap.isEmpty() ) ? new Note( noteMap.get( "details" ).toString(),
                Boolean.parseBoolean( noteMap.get( "deleted" ).toString().trim() ),
                noteMap.get( "opportunityId" ).toString(), Long.valueOf( noteMap.get( "createdDtm" ).toString() ) )
                : null;

    }
}
