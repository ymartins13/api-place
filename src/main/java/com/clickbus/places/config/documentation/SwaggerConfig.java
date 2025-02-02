package com.clickbus.places.config.documentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {     
	
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
                .select()                                  
				.apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)                                   
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Places REST API")
                .description("\"Test API with Sprint Boot\"")
                .contact(new Contact("Yuri Martins", "https://github.com/yurihm", "yurihm@hotmail.com"))
                .build();
    }
}
