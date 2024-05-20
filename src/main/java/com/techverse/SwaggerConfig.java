package com.techverse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 

 
//@Configuration
//@EnableSwagger2
public class SwaggerConfig {
   /* @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.techverse.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "StudentSearchApplication",
                "An application to search Student from a Student repository by studentId",
                "StudentSearchApplication v1",
                "Terms of service",
                "hendisantika@gmail.com",
                "License of API",
                "https://swagger.io/docs/");
        return apiInfo;
    }
    */
}
