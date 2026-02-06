package com.hospital.Hms.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log =
            LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.hospital.Hms.service..*(..))")
    public void serviceLayer() {}

    @Around("serviceLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        log.info("➡️ START {} with args = {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs());

        try {
            Object result = joinPoint.proceed();

            long duration = System.currentTimeMillis() - start;
            log.info("✅ SUCCESS {} ({} ms)", joinPoint.getSignature().toShortString(), duration);

            return result;

        } catch (Exception ex) {

            long duration = System.currentTimeMillis() - start;
            log.error("❌ ERROR {} ({} ms) : {}",
                    joinPoint.getSignature().toShortString(),
                    duration,
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }
}

