package org.javaboy.vhr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * @ClassName SwaggerConfig
 * @Author 周某
 * @Date 2020-05-02 15:06
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())  // 过滤Controller层
                .paths(PathSelectors.any()) // 过滤Controller中的链接
                /*
                .apis(RequestHandlerSelectors.basePackage("cn.itweknow.sbswagger.controller"))
                .paths(Predicates.or(PathSelectors.ant("/user/add"),
                        PathSelectors.ant("/user/find/*")))
                 * */
                .build()
                // 调用自己的配置
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "SpringBoot AND Vue项目 微人事 接口文档",
                "我的博客网站：https://itweknow.cn，欢迎大家访问。",
                "API V1.0",
                "Terms of service",
                new Contact("ChangingTao", "https://www.ChangingTao", "Rocket@163.com"),
                "Apache", "http://www.apache.org/", Collections.emptyList());
    }
}
