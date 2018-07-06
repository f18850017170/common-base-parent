package cn.vonfly.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	@Autowired
	private ObjectMapper objectMapper;
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic", "/single");//指定消息代理的前缀
		config.setApplicationDestinationPrefixes("/app");//指定注解@MessageMapping("")匹配路径的前缀，注解路径不需要该前缀
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		//绑定的web socket链接
		registry.addEndpoint("/ws/user").withSockJS();
	}
	@Override
	public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
		//消息发送序列化处理 解决诸如js 类型精度丢失，以及时间格式等
		DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
		resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		ObjectMapper myObjectMapper = objectMapper.copy();
		myObjectMapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
		converter.setObjectMapper(myObjectMapper);
		converter.setContentTypeResolver(resolver);
		messageConverters.add(converter);
		return false;
	}
}
