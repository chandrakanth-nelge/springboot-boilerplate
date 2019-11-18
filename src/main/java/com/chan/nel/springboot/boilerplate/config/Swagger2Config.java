package com.chan.nel.springboot.boilerplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

	private static final String GROUP_NAME = "USERMANAGEMENT-v%s";

	/**
	 * @return Docket
	 */
	@Bean
	public Docket swaggerApi1() {
		final String version = "1";
		return buildDocket(version);
	}

	private Docket buildDocket(String version) {
		return new Docket(DocumentationType.SWAGGER_2).groupName(String.format(GROUP_NAME, version)).select()
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).paths(PathSelectors.any()).build()
				.apiInfo(apiEndPointsInfo(version));
	}

	private ApiInfo apiEndPointsInfo(String version) {
		return new ApiInfoBuilder().title("USERMANAGEMENT API")
				.description("Documentation USERMANAGEMENT API v" + version)
				.contact(
						new Contact("Chandrakanth Nelge", "https://www.google.com", "chandrakanth.nelge@gmail.com"))
				.license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").version(version)
				.build();
	}
}