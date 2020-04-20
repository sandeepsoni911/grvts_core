package com.owners.gravitas.service.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.AgentPreferenceDao;

/**
 * The Class AgentPreferenceServiceImplTest.
 *
 * @author javeedsy
 */
public class AgentPreferenceServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent report service impl. */
    @InjectMocks
    private AgentPreferenceServiceImpl agentPreferenceServiceImpl;

    /** The report repository. */
    @Mock
    private AgentPreferenceDao agentPreferenceDao;

    @Test
    public void testSaveAgentPreferencesData() {
        final String fbPath = "data/pref/sms";
        final Map< String, Object > agentPreferenceMap = new HashMap< String, Object >();
        when( agentPreferenceDao.saveAgentPreferencesData( fbPath, agentPreferenceMap ) ).thenReturn( new Object() );
        final Object objectResponse = agentPreferenceServiceImpl.saveAgentPreferencesData( fbPath, agentPreferenceMap );
        assertNotNull( objectResponse );
        verify( agentPreferenceDao ).saveAgentPreferencesData( fbPath, agentPreferenceMap );
    }

    @Test
    public void testGetFbPreferencePath_agentSpecific_true() {
        final String response = agentPreferenceServiceImpl.getFbPreferencePath( "agentId", "path.test.template", true );
        assertNotNull( response );
    }

    @Test
    public void testGetFbPreferencePath_agentSpecific_false() {
        final String response = agentPreferenceServiceImpl.getFbPreferencePath( "agentId", "", false );
        assertNotNull( response );
    }
}
