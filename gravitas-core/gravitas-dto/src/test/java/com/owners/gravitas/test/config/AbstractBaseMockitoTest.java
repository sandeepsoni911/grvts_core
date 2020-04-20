/** AbstractBaseMockitoTest.java. */
package com.owners.gravitas.test.config;

import static org.mockito.MockitoAnnotations.initMocks;

import org.testng.annotations.BeforeClass;

/**
 * Base class for Mockito based unit tests. Just for calling initMocks(this);
 */
public abstract class AbstractBaseMockitoTest {

    /**
     * Setup base before the class loads.
     */
    @BeforeClass
    public void setUp() {
        initMocks( this );
    }


}
