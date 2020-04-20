package com.owners.gravitas.domain;

import static com.owners.gravitas.enums.TaskType.REQUEST_INFORMATION;
import static java.lang.Boolean.FALSE;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class VersionTest.
 *
 * @author amits
 */
public class VersionTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues testValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * Task.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "minVersion", null );
        defaultValues.put( "minMessage", null );
        defaultValues.put( "currentVersion", null );
        defaultValues.put( "currentMessage", null );
        defaultValues.put( "ios", new HashMap() );
        defaultValues.put( "android", new HashMap() );

    }

    /**
     * This method is to create the actual values.
     */
    private void createTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "minVersion", "test" );
        testValues.put( "minMessage", "test" );
        testValues.put( "currentVersion", "test" );
        testValues.put( "currentMessage", "test" );
        testValues.put( "ios", new HashMap() );
        testValues.put( "android", new HashMap()  );

    }

    /**
     * Tests the {@link Version} with default values. Tests the getters
     * and setters of Task.
     */
    @Test
    public final void testRequest() {
        final BeanLikeTester blt = new BeanLikeTester( Version.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
