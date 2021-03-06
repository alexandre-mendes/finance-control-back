package br.com.financecontrol.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.financecontrol"))
                .paths((PathSelectors.regex("/.*")))
                .build()
                .securitySchemes(securitySchemes());
    }

    private static ArrayList<? extends SecurityScheme> securitySchemes() {
        ArrayList<ApiKey> list = new ArrayList<>();
        list.add(new ApiKey("Bearer", "Authorization", "header"));
        return list;
    }
}
