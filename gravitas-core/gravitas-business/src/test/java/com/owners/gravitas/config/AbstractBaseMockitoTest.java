/** AbstractBaseMockitoTest.java. */
package com.owners.gravitas.config;

import org.mockito.MockitoAnnotations;
import org.powermock.modules.testng.PowerMockObjectFactory;
import org.testng.IObjectFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.ObjectFactory;

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
    
    /**
     * Gets the object factory.
     *
     * @return the object factory
     */
    @ObjectFactory
    public IObjectFactory getObjectFactory() {
        return new PowerMockObjectFactory();
    }
}
