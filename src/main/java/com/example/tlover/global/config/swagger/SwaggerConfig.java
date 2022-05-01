package com.example.tlover.global.config.swagger;

import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableSwagger2
public class SwaggerConfig {

    /**
     * http://localhost:8080/swagger-ui.html# < 스웨거 접속 url
     * PathSelectors.any() >>
     * 해당 package 하위에 있는 모든 url에 적용시킨다. /test/**로시작되는 부분만 적용해주고싶다면
     * PathSelectors.ant(String pattern) 메서드에 url parttern을 추가해주면된다.
     */
    private final TypeResolver typeResolver;

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
                .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Pageable.class), typeResolver.resolve(Page.class)))
                .globalOperationParameters(global)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.tlover"))
                .paths(PathSelectors.any())
                .build();
    }

    @Getter @Setter
    @ApiModel
    static class Page {
        @ApiModelProperty(value = "페이지 번호(0부터 시작)")
        private Integer page;

        @ApiModelProperty(value = "사이즈(한 페이지당 댓글 개수)", allowableValues="range[0, 100]")
        private Integer size;

        @ApiModelProperty(value = "작성 시간 정렬(사용법: 컬럼명,ASC|DESC / 사용예시: replyTime,DESC)")
        private List<String> sort;
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("tlover API sheet")
                .version("1.0.0")
                .description("화이팅!")
                .build();
    }

}
