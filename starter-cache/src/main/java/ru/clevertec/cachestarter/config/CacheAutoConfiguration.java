package ru.clevertec.cachestarter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.cachestarter.annotation.ConditionalOnCorrectCacheProperties;
import ru.clevertec.cachestarter.aspect.CacheAspect;
import ru.clevertec.cachestarter.cache.Cache;
import ru.clevertec.cachestarter.cache.impl.LFUCache;
import ru.clevertec.cachestarter.cache.impl.LRUCache;

@Configuration
@ConditionalOnCorrectCacheProperties
public class CacheAutoConfiguration {

    @Value("${cache.type}")
    private String type;

    @Value("${cache.size}")
    private Integer size;


    @Bean
    @ConditionalOnMissingBean
    public Cache cache() {
        return switch (type) {
            case "LFU" -> new LFUCache(size);
            case "LRU" -> new LRUCache(size);
            default -> null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public CacheAspect cacheAspect(Cache cache) {
        return new CacheAspect(cache);
    }

}