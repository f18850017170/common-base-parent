package cn.vonfly.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.util.List;
import java.util.Map;

/*1、通过配置文件来指定
#jackson config
spring.jackson.serialization.write_dates_as_timestamps=true
spring.jackson.default-property-inclusion=non_null

2、编码方式来指定*/
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
	@Autowired
	private ThymeleafViewResolver thymeleafViewResolver;
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		ObjectMapper webObjectMapper = objectMapper.copy();
		//配置ObjectMapper
		webObjectMapper.enable(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS);
		webObjectMapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		converters.add(new MappingJackson2HttpMessageConverter(webObjectMapper));
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//指定静态资源本地路径
		registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");

	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//注册拦截器
//		registry.addInterceptor(new MyInterceptor()).addPathPatterns("/app/**");
	}

	@Bean
	public ThymeleafViewResolver viewResolverConfig() {
		//指定试图解析时传递的参数 html中使用${scriptsPath}来获取指定的值
		Map<String, String> variables = Maps.newHashMap();
		variables.put("version", "1.0.0");
		variables.put("vendorsPath", "/assets/vendors");
		variables.put("imagesPath", "/assets/img");
		variables.put("stylesPath", "/assets/styles");
		variables.put("scriptsPath", "/assets/scripts");
		thymeleafViewResolver.setStaticVariables(variables);
		return thymeleafViewResolver;
	}
}
