package me.screw.demobootredis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfiguration {
    @Autowired
    RedisProperties redisProperties;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();

        redisStandaloneConfiguration.setHostName(redisProperties.getRedisHost());
        redisStandaloneConfiguration.setPort(redisProperties.getRedisPort());
        redisStandaloneConfiguration.setPassword(redisProperties.getRedisPassword());

        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean(name="redisTemplate")
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        // key Serializer
        template.setKeySerializer(new StringRedisSerializer());
        // value Serializer
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }


}
