package com.bh.live.common.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).build();
    }

 /*   @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).
                useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("^(?!auth).*$"))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                ;
    }
    private List<ApiKey> securitySchemes() {
        List<ApiKey> objects = new ArrayList<>();
        objects.add(new ApiKey("Authorization", "Authorization", "header"));
        return objects;
    }
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> objects = new ArrayList<>();
        SecurityContext build = SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("^(?!auth).*$")).build();
        objects.add(build);
        return objects;
    }
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        SecurityReference authorization = new SecurityReference("Authorization", authorizationScopes);
        List<SecurityReference> arrayList = new ArrayList();
        arrayList.add(authorization);
        return arrayList;
    }*/
}
