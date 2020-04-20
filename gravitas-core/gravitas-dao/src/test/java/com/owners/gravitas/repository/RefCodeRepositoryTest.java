package com.owners.gravitas.repository;

import org.testng.annotations.Test;

import com.owners.gravitas.config.RepositoryTestFactory;

/**
 * The Class RefCodeRepositoryTest.
 *
 * @author raviz
 */
public class RefCodeRepositoryTest {

    /** The ref code repository. */
    private final RefCodeRepository refCodeRepository = RepositoryTestFactory.test( RefCodeRepository.class );

    /**
     * Test find by code.
     */
    @Test
    public void testFindByCode() {
        refCodeRepository.findByCode( "refCode" );
    }

}
