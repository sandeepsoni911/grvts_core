/** AbstractBaseMockitoTest.java. */
package com.owners.gravitas.config;

import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;

/**
 * Base class for Mockito based unit tests. Just for calling initMocks(this);
 */
public abstract class AbstractBaseMockitoTest {

    /**
     * Creates the user profile response builder.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks( this );
    }
}
