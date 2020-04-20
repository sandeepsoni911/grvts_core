package com.owners.gravitas.config;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.owners.gravitas.config.security.ApiAuthenticationFilter;
import com.owners.gravitas.config.security.CorsFilter;
import com.owners.gravitas.config.security.JmxAuthenticationProvider;
import com.owners.gravitas.config.security.TokenAuthenticationProvider;
import com.owners.gravitas.service.RolePermissionService;
import com.owners.gravitas.service.UserService;

/**
 * The Class SecurityConfig.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class SecurityConfig {

	/**
	 * The Class WebApiSecurityConfigurationAdapter.
	 */
	@Configuration
	@Order(1)
	public static class WebApiSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		/** The Constant API_SECURE_URL_PATTERN. */
		private static final String API_SECURE_URL_PATTERN = "/api/agents/**";

		/** The Constant WEBAPI_SECURE_URL_PATTERN. */
		private static final String WEBAPI_SECURE_URL_PATTERN = "/webapi/**";

		@Autowired
		private UserService userService;

		@Autowired
		private RolePermissionService rolePermissionService;

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.springframework.security.config.annotation.web.configuration.
		 * WebSecurityConfigurerAdapter#configure(org.springframework.security.
		 * config.annotation.web.builders.HttpSecurity)
		 */
        @Override
        protected void configure( final HttpSecurity http ) throws Exception {
            http.addFilterBefore( new ApiAuthenticationFilter( authenticationManager() ),
                    BasicAuthenticationFilter.class ).requestMatchers()
                    .antMatchers( API_SECURE_URL_PATTERN, WEBAPI_SECURE_URL_PATTERN );
            http.addFilterBefore( new CorsFilter(), ApiAuthenticationFilter.class ).requestMatchers()
                    .antMatchers( API_SECURE_URL_PATTERN, WEBAPI_SECURE_URL_PATTERN );
            http.csrf().disable().exceptionHandling().authenticationEntryPoint( unauthorizedEntryPoint() );
        }

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.springframework.security.config.annotation.web.configuration.
		 * WebSecurityConfigurerAdapter#configure(org.springframework.security.
		 * config.annotation.authentication.builders.
		 * AuthenticationManagerBuilder)
		 */
		@Override
		protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
			auth.authenticationProvider(tokenAuthenticationProvider());
		}

		/**
		 * Token authentication provider.
		 *
		 * @return the authentication provider
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
		@Bean
		public AuthenticationProvider tokenAuthenticationProvider() throws IOException {
			return new TokenAuthenticationProvider(userService, rolePermissionService);
		}

		/**
		 * Unauthorized entry point.
		 *
		 * @return the authentication entry point
		 */
		@Bean
		public AuthenticationEntryPoint unauthorizedEntryPoint() {
			return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

	}

	/**
	 * The Class JMXSecurityConfigurationAdapter.
	 */
	@Configuration
    public static class JMXSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        /** The Constant API_SECURE_URL_PATTERN. */
        private static final String JMX_SECURE_URL_PATTERN = "/jmx/**";
        
//        /** The Constant JMX_CONSOLE_SECURE_URL_PATTERN. */
//        private static final String JMX_CONSOLE_SECURE_URL_PATTERN = "/jmxconsole/user/**";

        /*
         * (non-Javadoc)
         * @see
         * org.springframework.security.config.annotation.web.configuration.
         * WebSecurityConfigurerAdapter#configure(org.springframework.security.
         * config.annotation.web.builders.HttpSecurity)
         */
        @Override
        protected void configure( final HttpSecurity http ) throws Exception {
            http.httpBasic().and().authorizeRequests()
                    .antMatchers( JMX_SECURE_URL_PATTERN/*, JMX_CONSOLE_SECURE_URL_PATTERN*/ ).authenticated();
            http.csrf().disable();
        }

        /*
         * (non-Javadoc)
         * @see
         * org.springframework.security.config.annotation.web.configuration.
         * WebSecurityConfigurerAdapter#configure(org.springframework.security.
         * config.annotation.authentication.builders.
         * AuthenticationManagerBuilder)
         */
        @Override
        protected void configure( final AuthenticationManagerBuilder auth ) throws Exception {
            auth.authenticationProvider( jmxAuthProvider() );
        }
        
        /**
         * Jmx auth provider.
         *
         * @return the authentication provider
         * @throws IOException
         *             Signals that an I/O exception has occurred.
         */
        @Bean
        public AuthenticationProvider jmxAuthProvider() throws IOException {
            return new JmxAuthenticationProvider();
        }
    }
}
