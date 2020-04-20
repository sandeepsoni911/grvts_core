package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.Constants.ACTION_OBJ;
import static com.owners.gravitas.constants.Constants.AGENT_ID;
import static com.owners.gravitas.enums.ActionEntity.AGENT_PREFERENCES;
import static com.owners.gravitas.enums.ActionType.UPDATE;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.annotation.Audit;
import com.owners.gravitas.dao.AgentPreferenceDao;
import com.owners.gravitas.domain.AgentPreference;
import com.owners.gravitas.service.AgentPreferenceService;

/**
 * 
 * @author gururasm
 *
 */
@Service
public class AgentPreferenceServiceImpl implements AgentPreferenceService {

    @Autowired
    private AgentPreferenceDao agentPreferenceDao;

    @Override
    @Audit( type = UPDATE, entity = AGENT_PREFERENCES, args = { AGENT_ID, ACTION_OBJ } )
    public AgentPreference saveAgentPreferences( final String agentId, final AgentPreference agentPreference ) {
        return agentPreferenceDao.saveAgentPreferences( agentId, agentPreference );
    }

    @Override
    @Audit( type = UPDATE, entity = AGENT_PREFERENCES, args = { AGENT_ID, ACTION_OBJ } )
    public Object saveAgentPreferencesData( final String fbPath, final Map< String, Object > agentPreference ) {
        return agentPreferenceDao.saveAgentPreferencesData( fbPath, agentPreference );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentPreferenceService#getFbPreferencePath(
     * java.lang.String, java.lang.String, boolean)
     */
    @Override
    public String getFbPreferencePath( final String agentId, final String path, final boolean agentSpecific ) {
        final StringBuilder fbPath = new StringBuilder( agentSpecific ? "agents/" + agentId : "" );
        if (!StringUtils.isEmpty( path )) {
            final String[] pathParts = path.split( "\\." );
            for ( final String node : pathParts ) {
                fbPath.append( "/" + node );
            }
        }
        return fbPath.toString();
    }
}
