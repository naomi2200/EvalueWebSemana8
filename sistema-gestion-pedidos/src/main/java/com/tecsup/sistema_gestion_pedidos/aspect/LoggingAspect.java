package com.tecsup.sistema_gestion_pedidos.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.tecsup.sistema_gestion_pedidos.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("========================================");
        System.out.println("INICIO de ejecución de método: " + joinPoint.getSignature().getName());
        System.out.println("========================================");
    }

    @AfterReturning("execution(* com.tecsup.sistema_gestion_pedidos.service.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("========================================");
        System.out.println("FINALIZACIÓN de método: " + joinPoint.getSignature().getName());
        System.out.println("========================================");
    }
}