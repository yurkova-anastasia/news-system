package ru.clevertec.news_service.util;

import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    String username() default "mockUser";
    String[] authorities() default
            {"WRITE_COMMENTS", "WRITE_NEWS", "UPDATE_COMMENTS", "UPDATE_NEWS", "DELETE_COMMENTS", "DELETE_NEWS"}; //admin authorities
}
