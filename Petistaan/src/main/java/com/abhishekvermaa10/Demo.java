package com.abhishekvermaa10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;

/**
 * @author abhishekvermaa10
 */
@OpenAPIDefinition(info=@Info(title="petistaan",version="1.0.0",contact=@Contact(name="veena",email="work.veenag@gmail.com",url="localohost:8080")), 
					servers = {@Server(url="http://localhost:8080",description = "Dev server")})
@RequiredArgsConstructor
@PropertySource("classpath:messages.properties")
@SpringBootApplication
public class Demo  implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(Demo.class, args);
	}
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// registry.addMapping("/owners");
		registry.addMapping("/**")
		.allowedOrigins("*")
		.allowedHeaders("*")
		.allowedMethods("GET", "POST", "PUT", "DELETE");
	}
}