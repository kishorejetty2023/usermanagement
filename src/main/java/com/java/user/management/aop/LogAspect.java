package com.java.user.management.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getMethod().getName();
        Instant startTime = Instant.now();
        Object result = proceedingJoinPoint.proceed();
        String additionalMessage = methodSignature.getMethod().getAnnotation(LogExecutionTime.class).additionalMessage();
        long elapsedTime = Duration.between(startTime, Instant.now()).toMillis();
        log.info("Class Name: {}, Method Name: {}, Additional Message: {}, Elapsed Time: {}ms",
                className, methodName, additionalMessage, elapsedTime);

        Duration elapsed = Duration.ofMillis(elapsedTime);
        String humanReadableElapsedTime = String.format(
                "%d hours, %d mins, %d seconds, %d millieSecs",
                elapsed.toHours(),
                elapsed.toMinutesPart(),
                elapsed.toSecondsPart(),
                elapsed.toMillis());
        log.info("Readable time format --> {} : {} ",additionalMessage,humanReadableElapsedTime);
        log.info("Result: {}", result);
        return result;
    }
}