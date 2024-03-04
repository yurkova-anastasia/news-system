package ru.clevertec.logging.aspect;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.clevertec.logging.util.ExampleService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {
        LoggingAspect.class,
        AnnotationAwareAspectJAutoProxyCreator.class,
        ExampleService.ExampleServiceImpl.class
})
class LoggingAspectTest {


    @Autowired
    private ExampleService service;

    @SpyBean
    private LoggingAspect aspect;

    @Test
    @SneakyThrows
    void logAfterReturningMethod() {
        service.exampleMethod(1L);

        verify(aspect, times(1)).logAfterReturning(any(), any());
    }
}