package ru.clevertec.news_service.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import ru.clevertec.news_service.mapper.BytesToDateMapper;

import java.time.Duration;
import java.util.Collections;

/**
 * Configuration class for Redis.
 *
 * @author Yurkova Anastasia
 */
@Configuration
@EnableCaching
@Profile("prod")
public class RedisConfig {

    /**
     * Creates a Redis template.
     *
     * @param redisConnectionFactory the Redis connection factory
     * @return the Redis template
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }

    /**
     * Creates a Redis cache configuration.
     *
     * @return the Redis cache configuration
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .disableCachingNullValues();
    }

    /**
     * Creates a Redis custom conversions.
     *
     * @param bytesToDateMapper the mapper for converting bytes to date
     * @return the Redis custom conversions
     */
    @Bean
    public RedisCustomConversions redisCustomConversions(BytesToDateMapper bytesToDateMapper) {
        return new RedisCustomConversions(Collections.singletonList(bytesToDateMapper));
    }

}
