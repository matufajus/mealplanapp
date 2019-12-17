package com.melearning.mealplanapp.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.melearning.mealplanapp.servlet.ImageServlet;

@Configuration
public class ApplicationConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public ServletRegistrationBean exampleServletBean() {
	    ServletRegistrationBean bean = new ServletRegistrationBean(
	      new ImageServlet(), "/recipeImages/*");
	    bean.setLoadOnStartup(1);
	    return bean;
	}
}
