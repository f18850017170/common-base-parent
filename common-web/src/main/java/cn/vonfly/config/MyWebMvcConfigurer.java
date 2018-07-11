package cn.vonfly.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
	private static final DateTimeFormatter LOCAL_DATE = DateTimeFormatter.ofPattern("MM-dd-yyyy");
	private static final DateTimeFormatter LOCAL_TIME = DateTimeFormatter.ofPattern("HH:mm:ss");
	private static final DateTimeFormatter LOCAL_TIME_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS);
		objectMapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.registerModule(javaTimeModule());
		jsonConverter.setObjectMapper(objectMapper);
		return jsonConverter;
	}
	@Bean
	public JavaTimeModule javaTimeModule(){
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		javaTimeModule.addDeserializer(LocalDate.class, new JsonDeserializer<LocalDate>() {
			@Override
			public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
					throws IOException {
				return LocalDate.parse(jsonParser.getValueAsString(), LOCAL_DATE);
			}
		});
		javaTimeModule.addSerializer(LocalDate.class,new JsonSerializer<LocalDate>() {

			@Override
			public void serialize(LocalDate localDate, JsonGenerator jsonGenerator,
					SerializerProvider serializerProvider) throws IOException {
				jsonGenerator.writeString(localDate.format(LOCAL_DATE));
			}
		});
		javaTimeModule.addDeserializer(LocalTime.class, new JsonDeserializer<LocalTime>() {
			@Override
			public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
					throws IOException {
				return LocalTime.parse(jsonParser.getValueAsString(), LOCAL_TIME);
			}
		});
		javaTimeModule.addSerializer(LocalTime.class,new JsonSerializer<LocalTime>() {
			@Override
			public void serialize(LocalTime localTime, JsonGenerator jsonGenerator,
					SerializerProvider serializerProvider)
					throws IOException {
				jsonGenerator.writeString(localTime.format(LOCAL_TIME));
			}
		});
		javaTimeModule.addDeserializer(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
			@Override
			public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
					throws IOException {
				return LocalDateTime.parse(jsonParser.getValueAsString(),LOCAL_TIME_DATE);
			}
		});
		javaTimeModule.addSerializer(LocalDateTime.class,new JsonSerializer<LocalDateTime>() {
			@Override
			public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator,
					SerializerProvider serializerProvider) throws IOException {
				jsonGenerator.writeString(localDateTime.format(LOCAL_TIME_DATE));

			}
		});
		return javaTimeModule;
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
