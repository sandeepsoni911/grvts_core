package com.owners.gravitas;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * Entry class for seller UI application in J2EE server.
 *
 * @author manishd
 */
public class ServletInitializer extends SpringBootServletInitializer {

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.boot.context.web.SpringBootServletInitializer#
     * configure
     * (org.springframework.boot.builder.SpringApplicationBuilder)
     */
    @Override
    protected SpringApplicationBuilder configure( SpringApplicationBuilder application ) {
        return application.sources( GravitasApplication.class );
    }

}
