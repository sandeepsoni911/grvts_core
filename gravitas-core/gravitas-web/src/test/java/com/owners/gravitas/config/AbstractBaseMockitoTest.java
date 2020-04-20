package com.owners.gravitas.config;

import static org.mockito.MockitoAnnotations.initMocks;

import java.lang.reflect.Field;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

/**
 * This is base class for test classes which uses mockito. This class has
 * methods to initialize mock objects. And resetting them before each method.
 *
 * @author harshads
 *
 */
public class AbstractBaseMockitoTest {
    /**
     * Setup base before the class loads.
     */
    @BeforeClass
    public void setUp() {
        initMocks( this );
    }

    /**
     * Reset.
     *
     * @throws IllegalAccessException
     *             the illegal access exception
     */
    @BeforeMethod
    public void reset() throws IllegalAccessException {

        final Field[] fields = this.getClass().getDeclaredFields();
        for ( Field currentField : fields ) {
            if (currentField.isAnnotationPresent( Mock.class )) {
                currentField.setAccessible( true );
                final Object value = currentField.get( this );
                Mockito.reset( value );
            }
        }

    }

    /**
     * Test private field.
     *
     * @param object
     *            the object
     * @param fieldName
     *            the field name
     * @param objectToSet
     *            the object to set
     */
    protected void testPrivateField( Object object, String fieldName, Object objectToSet ) {
        try {
            Field field = object.getClass().getDeclaredField( fieldName );
            field.setAccessible( true );
            field.set( object, objectToSet );
        } catch ( NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e ) {
            e.printStackTrace();
        }
    }
}
