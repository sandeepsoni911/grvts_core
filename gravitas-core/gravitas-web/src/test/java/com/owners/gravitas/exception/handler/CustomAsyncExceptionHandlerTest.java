package com.owners.gravitas.exception.handler;

import java.lang.reflect.Method;

import org.mockito.InjectMocks;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * The Class CustomAsyncExceptionHandlerTest.
 *
 * @author raviz
 */
public class CustomAsyncExceptionHandlerTest extends AbstractBaseMockitoTest {

    /** The custom async exception handler. */
    @InjectMocks
    private CustomAsyncExceptionHandler customAsyncExceptionHandler;

    /**
     * Test handle uncaught exception.
     */
    @Test
    public void testHandleUncaughtException() {
        final Method[] methods = customAsyncExceptionHandler.getClass().getMethods();
        customAsyncExceptionHandler.handleUncaughtException( new Throwable(), methods[0], new Object() );
    }
}
