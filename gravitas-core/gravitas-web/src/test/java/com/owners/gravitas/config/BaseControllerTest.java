package com.owners.gravitas.config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Source;

import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ClassUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.owners.gravitas.exception.handler.GlobalExceptionHandler;

/**
 * This is base class for all the test classes that are written for Controllers.
 * This class adds GlobalExceptionHandler as Exception resolver in mockMvc, so
 * that negative test cases which throws exception can be tested.
 *
 * @author harshads
 *
 */
public class BaseControllerTest extends AbstractBaseMockitoTest {
    /** The mock mvc. */
    protected MockMvc mockMvc;

    /**
     * Sets the up.
     *
     * @throws Exception
     *             the exception
     */
    protected void setUpMockMVC( Object controller ) throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup( controller )
                .setHandlerExceptionResolvers( createExceptionResolver() ).setViewResolvers( createViewResolver() )
                .build();

    }

    /**
     * Creates the view resolver.
     *
     * @return the view resolver
     */
    private ViewResolver createViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix( "/WEB-INF/views/user/" );
        viewResolver.setSuffix( ".jsp" );
        return viewResolver;
    }

    /**
     * This method creates instance of ExceptionHandlerExceptionResolver to be
     * used by mockMVC to handle exceptions. Method also adds different message
     * converters to it, so that @ExceptionHandler method can use @ResponseBody
     * or return ModelAndView *
     *
     * @return instance of ExceptionHandlerExceptionResolver which has
     *         GlobalExceptionHandler instance configured to be used
     */
    private ExceptionHandlerExceptionResolver createExceptionResolver() {
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod( HandlerMethod handlerMethod,
                    Exception exception ) {
                Method method = new ExceptionHandlerMethodResolver( GlobalExceptionHandler.class )
                        .resolveMethod( exception );
                return new ServletInvocableHandlerMethod( new GlobalExceptionHandler(), method );
            }
        };
        boolean jaxb2Present = ClassUtils.isPresent( "javax.xml.bind.Binder",
                BaseControllerTest.class.getClassLoader() );

        boolean jackson2Present = ClassUtils.isPresent( "com.fasterxml.jackson.databind.ObjectMapper",
                BaseControllerTest.class.getClassLoader() )
                && ClassUtils.isPresent( "com.fasterxml.jackson.core.JsonGenerator",
                        BaseControllerTest.class.getClassLoader() );

        boolean jacksonPresent = ClassUtils.isPresent( "org.codehaus.jackson.map.ObjectMapper",
                BaseControllerTest.class.getClassLoader() )
                && ClassUtils.isPresent( "org.codehaus.jackson.JsonGenerator",
                        BaseControllerTest.class.getClassLoader() );

        List< HttpMessageConverter< ? > > messageConverters = new ArrayList< >();
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setWriteAcceptCharset( false );

        messageConverters.add( new ByteArrayHttpMessageConverter() );
        messageConverters.add( stringConverter );
        messageConverters.add( new ResourceHttpMessageConverter() );
        messageConverters.add( new SourceHttpMessageConverter< Source >() );
        messageConverters.add( new AllEncompassingFormHttpMessageConverter() );

        if (jaxb2Present) {
            messageConverters.add( new Jaxb2RootElementHttpMessageConverter() );
        }
        if (jackson2Present) {
            messageConverters.add( new MappingJackson2HttpMessageConverter() );
        } else if (jacksonPresent) {
            messageConverters.add( new org.springframework.http.converter.json.MappingJackson2HttpMessageConverter() );
        }
        exceptionResolver.setMessageConverters( messageConverters );
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }
}
