package com.melearning.mealplanapp.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
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
	
	//To prevent serialization for non fetched lazy objects
	@Bean
	public Module datatypeHibernateModule() {
	  return new Hibernate5Module();
	}
}
