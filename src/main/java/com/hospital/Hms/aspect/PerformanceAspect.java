package com.hospital.Hms.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {

    private static final Logger log = LoggerFactory.getLogger(PerformanceAspect.class);


    @Pointcut("execution(* com.hospital.Hms.graphql..*(..)) || execution(* com.hospital.Hms.controller..*(..))")
    public void controllerMethods() {}

    @Around("controllerMethods()")
    public Object measureExecutionTimeController(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();
        log.info("⏱ Method {} executed in {} ms", joinPoint.getSignature(), (end - start));

        return result;
    }

    @Pointcut("execution(* com.hospital.Hms.service..*(..))")
    public void serviceMethods() {}

    @Around("serviceMethods()")
    public Object measureExecutionTimeService(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();
        log.info("⏱️ Method {} executed in {} ms", joinPoint.getSignature(), (end - start));

        return result;
    }
}


