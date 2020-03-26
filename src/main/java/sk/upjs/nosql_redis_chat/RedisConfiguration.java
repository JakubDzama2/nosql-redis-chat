package sk.upjs.nosql_redis_chat;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {
	
	static String HOSTNAME = "nosql.gursky.sk";
	static String PASSWORD = "dh38fhw0238923df89djkd93la9fjs0mq9gjflv9jkddj934df90rj";
	static String[] CLUSTER_NODES = { "nosql.gursky.sk:6380", "nosql2.gursky.sk:6380", "nosql3.gursky.sk:6380" };
	
	@Bean
	public RedisClusterConfiguration redisClusterConfiguration() {
		return new RedisClusterConfiguration(Arrays.asList(CLUSTER_NODES));
	}
	
	@Bean
	public RedisConnectionFactory connectionFactory(RedisClusterConfiguration configuration) {
		LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration);
//		factory.afterPropertiesSet();
		return factory;
	}
	
	@Bean
	public RedisConnection redisConnection(RedisConnectionFactory factory) {
		return factory.getConnection();
	}
	
	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		template.setDefaultSerializer(new StringRedisSerializer());
//		template.afterPropertiesSet();
		return template;
	}
	
}
