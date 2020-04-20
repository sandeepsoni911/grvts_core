package com.owners.gravitas;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan( "com.owners" )
@SpringBootApplication( exclude = { RabbitAutoConfiguration.class, } )
public class GravitasApplication implements CommandLineRunner {

    public static void main( final String... args ) {
        SpringApplication.run( GravitasApplication.class, args );
    }

    @Override
    public void run( final String... args ) throws Exception {
        LoggerFactory.getLogger( getClass() ).info( "This application is running" );
    }

}
