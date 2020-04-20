package com.owners.gravitas.dao.impl;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * The Class OpportunityActionDaoImplTest.
 *
 * @author raviz
 */
public class OpportunityActionDaoImplTest extends AbstractBaseMockitoTest {

    /** The opportunity action dao impl. */
    @InjectMocks
    private OpportunityActionDaoImpl opportunityActionDaoImpl;

    /** The entity manager factory. */
    @Mock
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void testGetIncompleteActionFlowCount() {
        final String agentEmail = "test@test.com";
        final EntityManager entityManager = mock( EntityManager.class );
        final Query query = mock( Query.class );
        final Object expectedValue = 2;
        when( entityManagerFactory.createEntityManager() ).thenReturn( entityManager );
        when( entityManagerFactory.createEntityManager().createNativeQuery( anyString() ) ).thenReturn( query );
        when( query.getSingleResult() ).thenReturn( expectedValue );
        final Object actualValue = opportunityActionDaoImpl.getIncompleteActionFlowCount( agentEmail );
        assertEquals( actualValue, expectedValue );
    }

}
