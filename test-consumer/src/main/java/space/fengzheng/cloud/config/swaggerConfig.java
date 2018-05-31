package space.fengzheng.cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class swaggerConfig {
    @Bean
    public Docket config() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("space.fengzheng.cloud.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    // @Bean
    // public WebMvcConfigurerAdapter addResourceHandlers() {
    //     return new WebMvcConfigurerAdapter() {
    //         @Override
    //         public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //             registry.addResourceHandler("swagger-ui.html")
    //                     .addResourceLocations("classpath:/META-INF/resources/");
    //             registry.addResourceHandler("/webjars/**")
    //                     .addResourceLocations("classpath:/META-INF/resources/webjars/");
    //         }
    //     };
    // }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("springboot利用swagger构建api文档")
                .description("简单优雅的restfun风格，http://blog.csdn.net/saytime")
                .termsOfServiceUrl("http://blog.csdn.net/saytime")
                .version("1.0")
                .build();
    }
}
