package com.ForMonk2;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.ForMonk2.dao.IMCRepositoryDao;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
 * http://localhost:5000/docApi/swagger-ui.html
 * */

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = IMCRepositoryDao.class)
@EnableSwagger2
@EnableWebMvc
@EnableScheduling
@EnableCaching
@EnableDiscoveryClient
@EnableAutoConfiguration

public class ForMonk2Application implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(ForMonk2Application.class, args);
	}


	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addRedirectViewController("/docApi/v2/api-docs", "/v2/api-docs");
	    registry.addRedirectViewController("/docApi/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
	    registry.addRedirectViewController("/docApi/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
	    registry.addRedirectViewController("/docApi/swagger-resources", "/swagger-resources");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/docApi/swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
	    registry.addResourceHandler("/docApi/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
