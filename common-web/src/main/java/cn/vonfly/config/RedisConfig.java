package cn.vonfly.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
	/**
	 * 此时存在两个RedisTemplate
	 * @Autowired
	 * private RedisTemplate stringRedisTemplate;//可以使用自增长.increment()
	 * private RedisTemplate redisTemplate;//不能使用自增长.increment()
	 * @param jedisConnectionFactory
	 * @return
	 */
	@Bean
	public RedisTemplate stringRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
		stringRedisTemplate.setValueSerializer(new StringRedisSerializer());
		stringRedisTemplate.setHashValueSerializer(new StringRedisSerializer());
		return stringRedisTemplate;
	}
}
