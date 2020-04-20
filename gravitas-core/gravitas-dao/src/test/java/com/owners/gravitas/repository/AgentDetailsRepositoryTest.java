/**
 *
 */
package com.owners.gravitas.repository;

import org.testng.annotations.Test;

import com.owners.gravitas.config.RepositoryTestFactory;

/**
 * @author harshads
 *
 */
public class AgentDetailsRepositoryTest {

    /** The agent details repository. */
    private final AgentDetailsRepository agentDetailsRepository = RepositoryTestFactory
            .test( AgentDetailsRepository.class );

    /**
     * Find by user test.
     */
    @Test
    public void findByUserTest() {
        agentDetailsRepository.findByUser( null );
    }
}
