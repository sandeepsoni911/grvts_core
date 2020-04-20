package com.owners.gravitas.handler;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.enums.OpportunityChangeType;

/**
 * The Class OpportunityChangeHandlerFactoryTest.
 * 
 * @author ankusht
 */
@PrepareForTest( { AnnotationUtils.class } )
public class OpportunityChangeHandlerFactoryTest extends AbstractBaseMockitoTest {

    /** The opportunity change handler factory. */
    @InjectMocks
    private OpportunityChangeHandlerFactory opportunityChangeHandlerFactory;

    /** The application context. */
    @Mock
    private ApplicationContext applicationContext;

    /**
     * Test get change handler.
     */
    @Test
    public void testGetChangeHandler() {
        final Map< OpportunityChangeType, Map< String, Object > > opportunityChangeHandlers = new HashMap<>();
        final Map< String, Object > value = new HashMap<>();
        final String testKey = "test";
        final String expected = "value";
        value.put( testKey, expected );
        ReflectionTestUtils.setField( opportunityChangeHandlerFactory, "opportunityChangeHandlers",
                opportunityChangeHandlers );

        final Map< String, Object > beanMap = new HashMap<>();
        beanMap.put( "beanKey", new Object() );
        when( applicationContext.getBeansWithAnnotation( OpportunityChange.class ) ).thenReturn( beanMap );
        final OpportunityChange strategyAnnotation = new OpportunityChange() {

            @Override
            public Class< ? extends Annotation > annotationType() {
                return null;
            }

            @Override
            public String value() {
                return testKey;
            }

            @Override
            public OpportunityChangeType type() {
                return OpportunityChangeType.Stage;
            }
        };
        mockStatic( AnnotationUtils.class );
        when( AnnotationUtils.findAnnotation( any( Class.class ), any( Class.class ) ) )
                .thenReturn( strategyAnnotation );
        ReflectionTestUtils.invokeMethod( opportunityChangeHandlerFactory, "init" );
        final Object actual = opportunityChangeHandlerFactory.getChangeHandler( OpportunityChangeType.Stage, testKey );
    }

    /**
     * Test get change handler should throw exception if multiple handlers are
     * found.
     */
    @Test( expectedExceptions = RuntimeException.class )
    public void testGetChangeHandlerShouldThrowExceptionIfMultipleHandlersAreFound() {
        final Map< OpportunityChangeType, Map< String, Object > > opportunityChangeHandlers = new HashMap<>();
        final Map< String, Object > value = new HashMap<>();
        final String testKey = "test";
        final String expected = "value";
        value.put( testKey, expected );
        opportunityChangeHandlers.put( OpportunityChangeType.Stage, value );
        ReflectionTestUtils.setField( opportunityChangeHandlerFactory, "opportunityChangeHandlers",
                opportunityChangeHandlers );

        final Map< String, Object > beanMap = new HashMap<>();
        beanMap.put( "beanKey", new Object() );
        when( applicationContext.getBeansWithAnnotation( OpportunityChange.class ) ).thenReturn( beanMap );
        final OpportunityChange strategyAnnotation = new OpportunityChange() {

            @Override
            public Class< ? extends Annotation > annotationType() {
                return null;
            }

            @Override
            public String value() {
                return testKey;
            }

            @Override
            public OpportunityChangeType type() {
                return OpportunityChangeType.Stage;
            }
        };
        mockStatic( AnnotationUtils.class );
        when( AnnotationUtils.findAnnotation( any( Class.class ), any( Class.class ) ) )
                .thenReturn( strategyAnnotation );
        ReflectionTestUtils.invokeMethod( opportunityChangeHandlerFactory, "init" );
        final Object actual = opportunityChangeHandlerFactory.getChangeHandler( OpportunityChangeType.Stage, testKey );
    }
}
