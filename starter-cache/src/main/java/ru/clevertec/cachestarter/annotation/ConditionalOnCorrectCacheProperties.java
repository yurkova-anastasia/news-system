package ru.clevertec.cachestarter.annotation;

import org.springframework.context.annotation.Conditional;
import ru.clevertec.cachestarter.annotation.conditional.OnCorrectCacheProperties;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Conditional(OnCorrectCacheProperties.class)
public @interface ConditionalOnCorrectCacheProperties {

}