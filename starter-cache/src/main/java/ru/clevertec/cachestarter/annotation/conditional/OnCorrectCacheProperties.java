package ru.clevertec.cachestarter.annotation.conditional;

import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import static org.springframework.context.annotation.ConfigurationCondition.ConfigurationPhase.PARSE_CONFIGURATION;

public class OnCorrectCacheProperties extends AnyNestedCondition {

    public OnCorrectCacheProperties() {
        super(PARSE_CONFIGURATION);
    }

    @ConditionalOnProperty(prefix = "cache", name = "type", havingValue = "LRU")
    static class ConditionForLruCache {

    }

    @ConditionalOnProperty(prefix = "cache", name = "type", havingValue = "LFU")
    static class ConditionForLfuCache {

    }
}