package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value( "${swagger.api.title}" )
    private String swaggerApiTitle;

    @Value( "${swagger.api.description}" )
    private String swaggerApiDescription;

    @Value( "${swagger.api.version}" )
    private String swaggerApiVersion;

    @Value( "${swagger.contact.name}" )
    private String swaggerContactName;

    @Value( "${swagger.contact.url}" )
    private String swaggerContactUrl;

    @Value( "${swagger.contact.email}" )
    private String swaggerContactEmail;

    @Bean
    public Docket api() {
        final ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name("Authorization").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        final List<Parameter> aParameters = new ArrayList<>();
        aParameters.add(aParameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.owners.gravitas.controller"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .globalOperationParameters(aParameters);
    }


    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(swaggerApiTitle, swaggerApiDescription, swaggerApiVersion, "", new Contact(swaggerContactName, swaggerContactUrl, swaggerContactEmail), "", "");
        return apiInfo;
    }

}