package ru.clevertec.cachestarter.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import ru.clevertec.cachestarter.annotation.CustomCacheable;
import ru.clevertec.cachestarter.cache.Cache;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class CacheAspect {

    private final Cache cache;

    private static final String CACHE_CALLED = "cache was called";

    @Pointcut("execution(* *..service..*.findById(..))")
    public void findByIdServiceMethod() {
    }

    @Pointcut("execution(* *..service..*.create(..))")
    public void createServiceMethod() {
    }

    @Pointcut("execution(* *..service..*.update(..))")
    public void updateServiceMethod() {
    }

    @Pointcut("execution(* *..service..*.deleteById(..))")
    public void deleteByIdServiceMethod() {
    }

    @Around(value = "findByIdServiceMethod()")
    public Object cachingFindById(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if (checkAnnotation(proceedingJoinPoint)) {
            Long id = (Long) proceedingJoinPoint.getArgs()[0];
            Object cachedObject = cache.get(id);

            if (cachedObject != null) {
                log.info(CACHE_CALLED);
                return cachedObject;
            } else {
                Object returnObject = proceedingJoinPoint.proceed();
                cache.put(id, returnObject);
                return returnObject;
            }
        }

        return proceedingJoinPoint.proceed();
    }

    @Around(value = "updateServiceMethod()")
    public Object cachingUpdate(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if (checkAnnotation(proceedingJoinPoint)) {
            Long id = (Long) proceedingJoinPoint.getArgs()[0];
            Object returnObject = proceedingJoinPoint.proceed();
            cache.put(id, returnObject);
            System.out.println("cache was called");
            return returnObject;
        }

        return proceedingJoinPoint.proceed();
    }

    @Around(value = "createServiceMethod()")
    public Object cachingCreate(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if (checkAnnotation(proceedingJoinPoint)) {
            Object returnObject = proceedingJoinPoint.proceed();
            try {
                cache.put(findObjectId(returnObject), returnObject);
                System.out.println("cache was called");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            return returnObject;
        }

        return proceedingJoinPoint.proceed();
    }


    @Around("deleteByIdServiceMethod()")
    public void cachingDelete(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if (checkAnnotation(proceedingJoinPoint)) {
            Long id = (Long) proceedingJoinPoint.getArgs()[0];
            proceedingJoinPoint.proceed();
            cache.delete(id);
            log.info(CACHE_CALLED);
        } else {
            proceedingJoinPoint.proceed();
        }
    }

    private static boolean checkAnnotation(ProceedingJoinPoint proceedingJoinPoint) {
        String methodName = proceedingJoinPoint.getSignature().getName();
        for (Method method : proceedingJoinPoint.getTarget().getClass().getDeclaredMethods()) {
            if (method.getName().equals(methodName) && method.isAnnotationPresent(CustomCacheable.class)) {
                return true;
            }
        }
        return false;
    }

    private Long findObjectId(Object object) throws IllegalAccessException {
        Long id = null;

        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.getName().equals("id")) {
                field.setAccessible(true);
                id = (Long) field.get(object);
                field.setAccessible(false);
            }
        }
        return id;
    }

}