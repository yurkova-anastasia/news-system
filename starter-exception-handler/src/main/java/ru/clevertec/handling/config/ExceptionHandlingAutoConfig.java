package ru.clevertec.handling.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.handling.handler.GlobalExceptionHandler;

@Configuration
@ConditionalOnProperty(
        prefix = "spring.exception-handling",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class ExceptionHandlingAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

}
