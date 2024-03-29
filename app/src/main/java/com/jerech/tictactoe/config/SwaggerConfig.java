/**
 * @author jeremiaschaparro@gmail.com 
 *
 */
package com.jerech.tictactoe.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
          .host("31.220.62.157/api_tictactoe")
          .select()                                   
          .apis(RequestHandlerSelectors.any())
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(apiInfo());                                           
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfo(
          "REST API for Tic Tac Toe game", 
          "", 
          "API Tic Tac Toe", 
          "GNU GPL", 
          new Contact("Jeremias Chaparro", "https://github.com/jerech", "jeremiaschaparro@gmail.com"), 
          "GNU GPL", "", Collections.emptyList());
    }
}
