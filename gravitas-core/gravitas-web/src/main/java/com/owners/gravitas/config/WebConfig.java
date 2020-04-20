package com.owners.gravitas.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.jminix.console.servlet.MiniConsoleServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.owners.gravitas.config.security.UnbounceIPWhitelistFilter;

/**
 * The Class WebConfig manages the web configurations.
 */
@Configuration
public class WebConfig {

    /**
     * Add Jminix servlet.
     *
     * @return the servlet registration bean
     */
    @Bean
    public ServletRegistrationBean jminixServlet() {
        ServletRegistrationBean servletBean = new ServletRegistrationBean();
        servletBean.addUrlMappings( "/jmx/*" );
        servletBean.setServlet( new MiniConsoleServlet() );
        return servletBean;
    }

    /**
     * Exclude hidden http method filter.
     *
     * @param filter
     *            the filter
     * @return the filter registration bean
     */
    @Bean
    public FilterRegistrationBean excludeHiddenHttpMethodFilter( HiddenHttpMethodFilter filter ) {
        FilterRegistrationBean registration = new FilterRegistrationBean( filter );
        registration.setEnabled( false );
        return registration;
    }

    /**
     * Unbounce whitelisted ip filter.
     *
     * @return the filter registration bean
     */
    @Bean
    public FilterRegistrationBean unbounceWhitelistedIPFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter( unbounceIPWhitelistFilter() );
        registration.setName( "UnbounceIPWhitelistFilter" );
        registration.setDispatcherTypes( EnumSet.of( DispatcherType.REQUEST ) );
        registration.addUrlPatterns( "/api/unbounce/prospect" );
        registration.setOrder( Ordered.HIGHEST_PRECEDENCE + 1 );
        return registration;
    }

    /**
     * Unbounce ip whitelist filter.
     *
     * @return the unbounce ip whitelist filter
     */
    @Bean
    public UnbounceIPWhitelistFilter unbounceIPWhitelistFilter() {
        UnbounceIPWhitelistFilter filter = new UnbounceIPWhitelistFilter();
        return filter;
    }

    /**
     * Method validation post processor.
     *
     * @return the method validation post processor
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    /**
     * Device resolver handler interceptor.
     *
     * @return the device resolver handler interceptor
     */
    @Bean
    public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() {
        return new DeviceResolverHandlerInterceptor();
    }

    /**
     * The Class WebMvcConfig.
     */
    @Configuration
    public static class WebMvcConfig extends WebMvcConfigurerAdapter {

        /** The device resolver handler interceptor. */
        @Autowired
        private DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor;

        /*
         * (non-Javadoc)
         * @see org.springframework.web.servlet.config.annotation.
         * WebMvcConfigurerAdapter#configurePathMatch(org.springframework.web.
         * servlet.config.annotation.PathMatchConfigurer)
         */
        @Override
        public void configurePathMatch( PathMatchConfigurer matcher ) {
            matcher.setUseRegisteredSuffixPatternMatch( true );
        }

        /*
         * (non-Javadoc)
         * @see org.springframework.web.servlet.config.annotation.
         * WebMvcConfigurerAdapter#addInterceptors(org.springframework.web.
         * servlet.config.annotation.InterceptorRegistry)
         */
        @Override
        public void addInterceptors( InterceptorRegistry registry ) {
            registry.addInterceptor( deviceResolverHandlerInterceptor );
        }
    }
}
