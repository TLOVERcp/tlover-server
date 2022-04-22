package com.example.tlover.global.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * http://localhost:8080/swagger-ui.html# < 스웨거 접속 url
     * PathSelectors.any() >>
     * 해당 package 하위에 있는 모든 url에 적용시킨다. /test/**로시작되는 부분만 적용해주고싶다면
     * PathSelectors.ant(String pattern) 메서드에 url parttern을 추가해주면된다.
     */


    @Bean
    public Docket restAPI() {
        List<Parameter> global = new ArrayList<>();
        global.add(new ParameterBuilder().
                name("X-ACCESS-TOKEN").
                description("X-ACCESS-TOKEN")
                .parameterType("header").
                required(false)
                .modelRef(new ModelRef("string")).build());
        global.add(new ParameterBuilder().
                name("X-REFRESH-TOKEN").
                description("X-REFRESH-TOKEN")
                .parameterType("header").
                required(false)
                .modelRef(new ModelRef("long")).build());

        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(global)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.tlover"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("tlover API sheet")
                .version("1.0.0")
                .description("화이팅!")
                .build();
    }

}
